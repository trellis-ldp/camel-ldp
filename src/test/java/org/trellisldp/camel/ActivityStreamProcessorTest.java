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

import static java.util.Arrays.asList;
import static org.trellisldp.camel.ActivityStreamProcessor.ACTIVITY_STREAM_ACTOR;
import static org.trellisldp.camel.ActivityStreamProcessor.ACTIVITY_STREAM_ID;
import static org.trellisldp.camel.ActivityStreamProcessor.ACTIVITY_STREAM_INBOX;
import static org.trellisldp.camel.ActivityStreamProcessor.ACTIVITY_STREAM_NAME;
import static org.trellisldp.camel.ActivityStreamProcessor.ACTIVITY_STREAM_TYPE;
import static org.trellisldp.camel.ActivityStreamProcessor.ACTIVITY_STREAM_OBJECT_ID;
import static org.trellisldp.camel.ActivityStreamProcessor.ACTIVITY_STREAM_OBJECT_TYPE;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * @author acoburn
 */
public class ActivityStreamProcessorTest extends CamelTestSupport {

    private static final String PROV_ACTIVITY = "http://www.w3.org/ns/prov#Activity";

    private static final String LDP_CONTAINER = "ldp:Container";

    private static final String LDP_RDF_SOURCE = "ldp:RDFSource";

    @EndpointInject(uri = "mock:result")
    private MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    private ProducerTemplate template;

    @Test
    public void testActivityStreamProcessor() throws IOException, InterruptedException {

        final String id = "unique-identifier";
        final String resource = "http://localhost/repository/resource";
        final String agent = "http://example.org/user1";
        final String inbox = "http://example.org/inbox";
        final String name = "user1 created a resource";

        final Map<String, Object> obj = new HashMap<>();

        obj.put("id", resource);
        obj.put("type", asList(LDP_CONTAINER, LDP_RDF_SOURCE));

        final Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("type", asList("Create", PROV_ACTIVITY));
        data.put("actor", agent);
        data.put("name", name);
        data.put("inbox", inbox);
        data.put("object", obj);

        template.sendBody("direct:start", data);

        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_ID, id);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_TYPE, asList("Create", PROV_ACTIVITY));
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_NAME, name);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_INBOX, inbox);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_ACTOR, agent);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_OBJECT_ID, resource);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_OBJECT_TYPE,
                asList(LDP_CONTAINER, LDP_RDF_SOURCE));
        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testActivityStreamProcessorMissingSomeValues() throws IOException, InterruptedException {

        final String id = "unique-id";

        final Map<String, Object> data = new HashMap<>();
        data.put("id", id);
        data.put("type", asList("Create", PROV_ACTIVITY));

        template.sendBody("direct:start", data);

        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_ID, id);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_TYPE, asList("Create", PROV_ACTIVITY));
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_NAME, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_INBOX, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_ACTOR, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_OBJECT_ID, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_OBJECT_TYPE, null);
        resultEndpoint.assertIsSatisfied();
    }

    @Test
    public void testActivityStreamProcessorMissingAllValues() throws IOException, InterruptedException {

        final Map<String, Object> data = new HashMap<>();
        template.sendBody("direct:start", data);

        resultEndpoint.expectedMessageCount(1);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_ID, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_TYPE, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_NAME, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_INBOX, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_ACTOR, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_OBJECT_ID, null);
        resultEndpoint.expectedHeaderReceived(ACTIVITY_STREAM_OBJECT_TYPE, null);
        resultEndpoint.assertIsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws IOException {
                from("direct:start").process(new ActivityStreamProcessor()).to("mock:result");
            }
        };
    }
}