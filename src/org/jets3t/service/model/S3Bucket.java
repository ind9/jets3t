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

import java.util.Date;

import org.jets3t.service.acl.AccessControlList;

public class S3Bucket extends BaseS3Object {
	public static final String METADATA_HEADER_CREATION_DATE = "Date";
	public static final String METADATA_HEADER_OWNER = "Owner";
	
	private String name = null;
	private AccessControlList acl = null;
    
    public S3Bucket() {        
    }
    
    public S3Bucket(String name) {
        this.name = name;
    }
	
	public String toString() {
		return "S3Bucket [name=" + getName() + ",creationDate=" + getCreationDate() + ",owner=" + getOwner() + "] Metadata=" + getMetadata();
	}
	
	public S3Owner getOwner() {
		return (S3Owner) getMetadata().get(METADATA_HEADER_OWNER);
	}

	public void setOwner(S3Owner owner) {
		getMetadata().put(METADATA_HEADER_OWNER, owner);
	}	

	public Date getCreationDate() {
		return (Date) getMetadata().get(METADATA_HEADER_CREATION_DATE);
	}
	
	public void setCreationDate(Date creationDate) {
		getMetadata().put(METADATA_HEADER_CREATION_DATE, creationDate);
	}

	public AccessControlList getAcl() {
		return acl;
	}

	public void setAcl(AccessControlList acl) {
		this.acl = acl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}