/*
 * jets3t : Java Extra-Tasty S3 Toolkit (for Amazon S3 online storage service)
 * This is a java.net project, see https://jets3t.dev.java.net/
 * 
 * Copyright 2006 James Murty
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */
package org.jets3t.service.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.SimpleTimeZone;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jets3t.service.Constants;
import org.jets3t.service.S3ServiceException;
import org.jets3t.service.model.S3Object;

public class ServiceUtils {
    private static final Log log = LogFactory.getLog(ServiceUtils.class);

    protected static SimpleDateFormat iso8601DateParser = new SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    protected static SimpleDateFormat rfc822DateParser = new SimpleDateFormat(
        "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);

    static {
        iso8601DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
        rfc822DateParser.setTimeZone(new SimpleTimeZone(0, "GMT"));
    }

    public static Date parseIso8601Date(String dateString) throws ParseException {
        synchronized (iso8601DateParser) {
            return iso8601DateParser.parse(dateString);
        }
    }

    public static String formatIso8601Date(Date date) {
        synchronized (iso8601DateParser) {
            return iso8601DateParser.format(date);
        }
    }

    public static Date parseRfc822Date(String dateString) throws ParseException {
        synchronized (rfc822DateParser) {
            return rfc822DateParser.parse(dateString);
        }
    }

    public static String formatRfc822Date(Date date) {
        synchronized (rfc822DateParser) {
            return rfc822DateParser.format(date);
        }
    }

    /**
     * Calculate the HMAC/SHA1 on a string.
     * 
     * (c) 2006 Amazon Digital Services, Inc. or its affiliates.
     * 
     * @param data
     * Data to sign
     * @param passcode
     * Passcode to sign it with
     * @return Signature
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     * If the algorithm does not exist. Unlikely
     * @throws InvalidKeyException
     * If the key is invalid.
     */
    public static String signWithHmacSha1(String awsSecretKey, String canonicalString)
        throws S3ServiceException
    {
        // The following HMAC/SHA1 code for the signature is taken from the
        // AWS Platform's implementation of RFC2104 (amazon.webservices.common.Signature)
        //
        // Acquire an HMAC/SHA1 from the raw key bytes.
        SecretKeySpec signingKey = null;
        try {
            signingKey = new SecretKeySpec(awsSecretKey.getBytes(Constants.DEFAULT_ENCODING),
                Constants.HMAC_SHA1_ALGORITHM);
        } catch (UnsupportedEncodingException e) {
            throw new S3ServiceException("Unable to get bytes from secret string", e);
        }

        // Acquire the MAC instance and initialize with the signing key.
        Mac mac = null;
        try {
            mac = Mac.getInstance(Constants.HMAC_SHA1_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            // should not happen
            throw new RuntimeException("Could not find sha1 algorithm", e);
        }
        try {
            mac.init(signingKey);
        } catch (InvalidKeyException e) {
            // also should not happen
            throw new RuntimeException("Could not initialize the MAC algorithm", e);
        }

        // Compute the HMAC on the digest, and set it.
        byte[] b64 = Base64.encodeBase64(mac.doFinal(canonicalString.getBytes())); 
        return new String(b64);
    }

    public static String readInputStreamToString(InputStream is) throws IOException {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String line = null;
        try {
            while ((line = br.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (Exception e) {
            log.warn("Unable to read String from Input Stream", e);
        }
        return sb.toString();
    }
    
    public static long countBytesInObjects(S3Object[] objects) {
        long byteTotal = 0;
        for (int i = 0; objects != null && i < objects.length; i++) {
            byteTotal += objects[i].getContentLength();
        }
        return byteTotal;
    }
            
    public static Map cleanRestMetadataMap(Map metadata) {
        log.debug("Cleaning up REST metadata items");
        HashMap cleanMap = new HashMap();
        if (metadata != null) {
            Iterator keysIter = metadata.keySet().iterator();
            while (keysIter.hasNext()) {
                Object key = keysIter.next();
                Object value = metadata.get(key);

                // Trim prefixes from keys.
                String keyStr = (key != null ? key.toString() : "");
                if (keyStr.startsWith(Constants.REST_METADATA_PREFIX)) {
                    key = keyStr
                        .substring(Constants.REST_METADATA_PREFIX.length(), keyStr.length());
                    log.debug("Removed Amazon meatadata header prefix from key: " + keyStr
                        + "=>" + key);
                } else if (keyStr.startsWith(Constants.REST_HEADER_PREFIX)) {
                    key = keyStr.substring(Constants.REST_HEADER_PREFIX.length(), keyStr.length());
                    log.debug("Removed Amazon header prefix from key: " + keyStr + "=>" + key);
                }

                // Convert connection header string Collections into simple strings (where
                // appropriate)
                if (value instanceof Collection) {
                    if (((Collection) value).size() == 1) {
                        log.debug("Converted metadata single-item Collection "
                            + value.getClass() + " " + value + " for key: " + key);
                        value = ((Collection) value).iterator().next();
                    } else {
                        log.warn("Collection " + value
                            + " has too many items to convert to a single string");
                    }
                }

                // Parse dates.
                if ("Date".equals(key) || "Last-Modified".equals(key)) {
                    try {
                        log.debug("Parsing date string '" + value
                            + "' into Date object for key: " + key);
                        value = parseRfc822Date(value.toString());
                    } catch (ParseException pe) {
                        log.warn("Unable to parse S3 date for metadata field " + key, pe);
                        value = null;
                    }
                }

                // Ignore/remove x-amz-id-2 and x-amz-request-id AWS debugging headers.
                if ("id-2".equals(key) || "request-id".equals(key)) {
                    log.debug("Ignoring AWS debugging header: " + key + "=" + value);
                } else {
                    cleanMap.put(key, value);
                }
            }
        }
        return cleanMap;
    }

}