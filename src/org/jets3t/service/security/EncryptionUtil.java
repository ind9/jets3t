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
package org.jets3t.service.security;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class EncryptionUtil {
    private static final String KEY_BASE = "ǠȾ�r=�Q� y�S4C.$S��t���I�[�O腀u@��dFT��ڇNh�v�ѣl�^u���+t�:�K7Q��H>�:i�u��Q�#ݴ1�zj���)1o��M�5DF���.#c;�����B��v�";
    public static final String DEFAULT_ENCRYPTION_SCHEME = "DESede";
    public static final String DEFAULT_BLOCK_MODE = "CBC";
    public static final String DEFAULT_PADDING_MODE = "PKCS5Padding";
    public static final String UNICODE_FORMAT = "UTF8";

    private String algorithm = null;
    private SecretKey key = null;
    private IvParameterSpec ivSpec = null;

    public EncryptionUtil(String encryptionKey, String encryptionScheme, String blockMode,
        String paddingMode) throws InvalidKeyException, NoSuchAlgorithmException,
        NoSuchPaddingException, InvalidKeySpecException {
        encryptionKey = encryptionKey + KEY_BASE;

        int keyOffset = 0;
        byte spec[] = new byte[8];
        for (int specOffset = 0; specOffset < spec.length; specOffset++) {
            keyOffset = (keyOffset + 7) % encryptionKey.length();
            spec[specOffset] = encryptionKey.getBytes()[keyOffset];
        }

        KeySpec keySpec = new DESedeKeySpec(encryptionKey.getBytes());
        ivSpec = new IvParameterSpec(spec);
        key = SecretKeyFactory.getInstance(encryptionScheme).generateSecret(keySpec);
        algorithm = encryptionScheme + "/" + blockMode + "/" + paddingMode;
    }

    public EncryptionUtil(String encryptionKey) throws InvalidKeyException,
        NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException {
        this(encryptionKey, DEFAULT_ENCRYPTION_SCHEME, DEFAULT_BLOCK_MODE, DEFAULT_PADDING_MODE);
    }

    public byte[] encrypt(String data) throws IllegalStateException, IllegalBlockSizeException,
        BadPaddingException, UnsupportedEncodingException, InvalidKeySpecException,
        InvalidKeyException, InvalidAlgorithmParameterException, 
        NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        return cipher.doFinal(data.getBytes(UNICODE_FORMAT));
    }

    public String decryptString(byte[] data) throws InvalidKeyException,
        InvalidAlgorithmParameterException, UnsupportedEncodingException, IllegalStateException,
        IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return new String(cipher.doFinal(data), UNICODE_FORMAT);
    }

    public String decryptString(byte[] data, int startIndex, int endIndex)
        throws InvalidKeyException, InvalidAlgorithmParameterException,
        UnsupportedEncodingException, IllegalStateException, IllegalBlockSizeException,
        BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return new String(cipher.doFinal(data, startIndex, endIndex), UNICODE_FORMAT);
    }

    public byte[] encrypt(byte[] data) throws IllegalStateException, IllegalBlockSizeException,
        BadPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, 
        NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        return cipher.doFinal(data);
    }

    public byte[] decrypt(byte[] data) throws InvalidKeyException,
        InvalidAlgorithmParameterException, IllegalStateException, IllegalBlockSizeException,
        BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return cipher.doFinal(data);
    }

    public byte[] decrypt(byte[] data, int startIndex, int endIndex) throws InvalidKeyException,
        InvalidAlgorithmParameterException, IllegalStateException, IllegalBlockSizeException,
        BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return cipher.doFinal(data, startIndex, endIndex);
    }

    public CipherInputStream encrypt(InputStream is) throws InvalidKeyException,
        InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        return new CipherInputStream(is, cipher);
    }

    public CipherInputStream decrypt(InputStream is) throws InvalidKeyException,
        InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return new CipherInputStream(is, cipher);
    }

    public CipherOutputStream encrypt(OutputStream os) throws InvalidKeyException,
        InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        return new CipherOutputStream(os, cipher);
    }

    public CipherOutputStream decrypt(OutputStream os) throws InvalidKeyException,
        InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchPaddingException
    {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key, ivSpec);
        return new CipherOutputStream(os, cipher);
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public static String generateRandomKeyBase(int length) {
        Random random = new Random();
        byte keyBaseBytes[] = new byte[length];
        random.nextBytes(keyBaseBytes);
        String keyBase = new String(keyBaseBytes);
        // Replace troublesome characters.
        keyBase.replace('\n', '-');
        keyBase.replace('\\', '/');
        return keyBase;
    }

}