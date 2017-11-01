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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * @author acoburn
 */
@RunWith(JUnitPlatform.class)
public class LinkTest {

    @Test
    public void testLinkWithParams() {
        final Link link = new Link("<http://www.w3.org/ns/ldp#Container>; rel=\"type\";" +
                "title=\"some title\"; type=\"text/turtle\"; other=\"param\"");
        assertEquals("http://www.w3.org/ns/ldp#Container", link.getUri());
        assertEquals("type", link.getRel());
        assertEquals("some title", link.getTitle());
        assertEquals("text/turtle", link.getType());
        assertEquals("param", link.getParams().get("other"));
        assertEquals(4L, link.getParams().size());
    }

    @Test
    public void testLinkRelQuotes() {
        final Link link = new Link("<http://www.w3.org/ns/ldp#Container>; rel=\"type\"");
        assertEquals("http://www.w3.org/ns/ldp#Container", link.getUri());
        assertEquals("type", link.getRel());
        assertNull(link.getTitle());
        assertNull(link.getType());
        assertEquals(1L, link.getParams().size());
    }

    @Test
    public void testLinkRelNoQuotes() {
        final Link link = new Link("<http://www.w3.org/ns/ldp#Container>;rel=type");
        assertEquals("http://www.w3.org/ns/ldp#Container", link.getUri());
        assertEquals("type", link.getRel());
        assertNull(link.getTitle());
        assertNull(link.getType());
        assertEquals(1L, link.getParams().size());
    }

    @Test
    public void testNoURI() {
        final Link link = new Link("");
        assertNull(link.getUri());
    }

    @Test
    public void testBadURI() {
        final Link link = new Link("<blah");
        assertNull(link.getUri());
    }

    @Test
    public void testBadParam() {
        final Link link = new Link("<uri>; rel");
        assertNull(link.getRel());
    }

    @Test
    public void testMultipleParams() {
        final Link link = new Link("<uri>; rel=one; rel=two; rel=three");
        assertEquals("one", link.getRel());
    }
}
