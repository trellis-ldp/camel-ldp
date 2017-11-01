/*
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

import static java.util.Objects.nonNull;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

/**
 * @author acoburn
 */
public class ActivityStreamProcessor implements Processor {

    public static final String ACTIVITY_STREAM_ID = "ActivityStreamId";
    public static final String ACTIVITY_STREAM_TYPE = "ActivityStreamType";
    public static final String ACTIVITY_STREAM_NAME = "ActivityStreamName";
    public static final String ACTIVITY_STREAM_ACTOR = "ActivityStreamActor";
    public static final String ACTIVITY_STREAM_INBOX = "ActivityStreamInbox";
    public static final String ACTIVITY_STREAM_OBJECT_ID = "ActivityStreamObjectId";
    public static final String ACTIVITY_STREAM_OBJECT_TYPE = "ActivityStreamObjectType";

    /**
     * Process an incoming ActivityStream message
     * @param exchange the Camel exchange
     * @throws IOException in the event of an error
     */
    public void process(final Exchange exchange) throws IOException {
        final Map body = exchange.getIn().getBody(Map.class);

        setHeader(exchange, ACTIVITY_STREAM_ID, body.get("id"));
        setHeader(exchange, ACTIVITY_STREAM_TYPE, body.get("type"));
        setHeader(exchange, ACTIVITY_STREAM_NAME, body.get("name"));
        setHeader(exchange, ACTIVITY_STREAM_ACTOR, body.get("actor"));
        setHeader(exchange, ACTIVITY_STREAM_INBOX, body.get("inbox"));

        if (body.containsKey("object") && body.get("object") instanceof Map) {
            final Map object = (Map) body.get("object");
            setHeader(exchange, ACTIVITY_STREAM_OBJECT_ID, object.get("id"));
            setHeader(exchange, ACTIVITY_STREAM_OBJECT_TYPE, object.get("type"));
        }
    }

    private void setHeader(final Exchange exchange, final String header, final Object value) {
        if (nonNull(value) && (value instanceof String || value instanceof List)) {
            exchange.getIn().setHeader(header, value);
        }
    }
}
