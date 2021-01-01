/*
 * Copyright (c) 2021 Aaron Coburn and individual contributors
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
package org.trellisldp.camel;

import static java.util.Collections.unmodifiableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * An object representing an HTTP Link header.
 *
 * @author acoburn
 */
public class Link {

    private final String uri;

    private final Map<String, String> params = new HashMap<>();

    /**
     * Create a Link object from an HTTP header.
     * @param value the value of the link header
     */
    public Link(final String value) {
        final String[] parts = value.split(";");
        final String val = parts[0].trim();
        this.uri = val.startsWith("<") && val.endsWith(">") ? val.substring(1, val.length() - 1) : null;
        for (int i = 1; i < parts.length; i++) {
            final String[] p = parts[i].trim().split("=");
            if (p.length == 2 && !params.containsKey(p[0])) {
                if (p[1].startsWith("\"") && p[1].endsWith("\"")) {
                    params.put(p[0], p[1].substring(1, p[1].length() - 1));
                } else {
                    params.put(p[0], p[1]);
                }
            }
        }
    }

    /**
     * Get the URI portion of the link header.
     * @return the URI
     */
    public String getUri() {
        return uri;
    }

    /**
     * Get the type portion of the link header, if one exists.
     * @return the type or null if one doesn not exist
     */
    public String getType() {
        return params.get("type");
    }

    /**
     * Get the title portion of the link header, if one exists.
     * @return the title or null if one doesn not exist
     */
    public String getTitle() {
        return params.get("title");
    }

    /**
     * Get the rel portion of the link header, if one exists.
     * @return the rel or null if one doesn not exist
     */
    public String getRel() {
        return params.get("rel");
    }

    /**
     * Get all defined parameters from the link header, including type, rel and title if they exist.
     * @return the header parameters
     */
    public Map<String, String> getParams() {
        return unmodifiableMap(params);
    }
}
