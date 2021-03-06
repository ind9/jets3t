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
package org.jets3t.service.model;

import org.jets3t.service.Constants;
import org.jets3t.service.S3Service;

/**
 * Represents Bucket Logging Status settings used to control bucket-based Server Access Logging in S3.
 * <p>
 * For logging to be enabled for a bucket both the targetBucketName and logfilePrefix must be
 * non-null, and the named bucket must exist. When both variables are non-null, this object
 * represents an <b>enabled</b> logging status (as indicated by {@link #isLoggingEnabled()}) and
 * the XML document generated by {@link #toXml()} will enable logging for the named bucket when
 * provided to {@link S3Service#setBucketLoggingStatus(String, S3BucketLoggingStatus, boolean)}.
 * <p>
 * If either the targetBucketName or logfilePrefix are null, this object will represent a 
 * <b>disabled</b> logging status (as indicated by {@link #isLoggingEnabled()}) and 
 * the XML document generated by {@link #toXml()} will disable logging for the named bucket when
 * provided to {@link S3Service#setBucketLoggingStatus(String, S3BucketLoggingStatus, boolean)}.
 * 
 * @author James Murty
 *
 */
public class S3BucketLoggingStatus {
    private String targetBucketName = null;
    private String logfilePrefix = null;

    public S3BucketLoggingStatus() {        
    }
    
    public S3BucketLoggingStatus(String targetBucketName, String logfilePrefix) {
        this.targetBucketName = targetBucketName;
        this.logfilePrefix = logfilePrefix;
    }
    
    public boolean isLoggingEnabled() {
        return targetBucketName != null
            && logfilePrefix != null;
    }

    public String getLogfilePrefix() {
        return logfilePrefix;
    }

    public void setLogfilePrefix(String logfilePrefix) {
        this.logfilePrefix = logfilePrefix;
    }

    public String getTargetBucketName() {
        return targetBucketName;
    }

    public void setTargetBucketName(String targetBucketName) {
        this.targetBucketName = targetBucketName;
    }
    
    public String toString() {
        String result = "LoggingStatus enabled=" + isLoggingEnabled();
        if (isLoggingEnabled()) {
            result += ", targetBucketName=" + getTargetBucketName()
                + ", logfilePrefix=" + getLogfilePrefix();
        }            
        return result;
    }
    
    /**
     * 
     * @return
     * An XML representation of the object suitable for use as an input to the REST/HTTP interface.
     */
    public String toXml() {
        StringBuffer sb = new StringBuffer();        
        sb.append(
            "<BucketLoggingStatus xmlns=\"" + Constants.XML_NAMESPACE + "\">" +
            (!isLoggingEnabled()? "" : 
                "<LoggingEnabled>" +
                    "<TargetBucket>" + getTargetBucketName() + "</TargetBucket>" +
                    "<TargetPrefix>" + getLogfilePrefix() + "</TargetPrefix>" +
                "</LoggingEnabled>"
            ) +
          "</BucketLoggingStatus>");
        return sb.toString();
    }
        
}
