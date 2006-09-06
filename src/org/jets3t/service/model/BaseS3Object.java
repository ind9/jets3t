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

import java.util.HashMap;
import java.util.Map;

public abstract class BaseS3Object {
	private Map metadata = new HashMap();

	public Map getMetadata() {
		return metadata;
	}
	
	public void addMetadata(String key, String value) {
		metadata.put(key, value);
	}
	
	public void addAllMetadata(Map metadata) {
		this.metadata.putAll(metadata);
	}
	
	public void replaceAllMetadata(Map metadata) {
		this.metadata.clear();
		addAllMetadata(metadata);
	}
	
}