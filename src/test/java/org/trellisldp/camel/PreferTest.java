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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;

/**
 * @author acoburn
 */
@RunWith(JUnitPlatform.class)
class PreferTest {

    @Test
    void testPrefer1() {
        final Prefer prefer = Prefer.valueOf("return=representation; include=\"http://example.org/test\"");
        assertEquals("representation", prefer.getPreference());
        assertEquals(1L, prefer.getInclude().size());
        assertTrue(prefer.getInclude().contains("http://example.org/test"));
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPreferSingleQuote() {
        final Prefer prefer = Prefer.valueOf("return=representation; include=\"");
        assertEquals("representation", prefer.getPreference());
        assertEquals(1L, prefer.getInclude().size());
        assertTrue(prefer.getInclude().contains("\""));
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPreferBadQuotes() {
        final Prefer prefer = Prefer.valueOf("return=representation; include=\"http://example.org/test");
        assertEquals("representation", prefer.getPreference());
        assertEquals(1L, prefer.getInclude().size());
        assertTrue(prefer.getInclude().contains("\"http://example.org/test"));
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer1b() {
        final Prefer prefer = Prefer.ofInclude("http://example.org/test");
        assertEquals("representation", prefer.getPreference());
        assertEquals(1L, prefer.getInclude().size());
        assertTrue(prefer.getInclude().contains("http://example.org/test"));
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer1c() {
        final Prefer prefer = Prefer.valueOf("return=representation; include=http://example.org/test");
        assertEquals("representation", prefer.getPreference());
        assertEquals(1L, prefer.getInclude().size());
        assertTrue(prefer.getInclude().contains("http://example.org/test"));
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer2() {
        final Prefer prefer = Prefer.valueOf("return  =  representation;   include =  \"http://example.org/test\"");
        assertEquals("representation", prefer.getPreference());
        assertEquals(1L, prefer.getInclude().size());
        assertTrue(prefer.getInclude().contains("http://example.org/test"));
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer3() {
        final Prefer prefer = Prefer.valueOf("return=minimal");
        assertEquals("minimal", prefer.getPreference());
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer4() {
        final Prefer prefer = Prefer.valueOf("return=other");
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getPreference());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer5() {
        final Prefer prefer = Prefer.valueOf("return=representation; omit=\"http://example.org/test\"");
        assertEquals("representation", prefer.getPreference());
        assertTrue(prefer.getInclude().isEmpty());
        assertFalse(prefer.getOmit().isEmpty());
        assertTrue(prefer.getOmit().contains("http://example.org/test"));
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer5b() {
        final Prefer prefer = Prefer.ofOmit("http://example.org/test");
        assertEquals("representation", prefer.getPreference());
        assertTrue(prefer.getInclude().isEmpty());
        assertFalse(prefer.getOmit().isEmpty());
        assertTrue(prefer.getOmit().contains("http://example.org/test"));
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer6() {
        final Prefer prefer = Prefer.valueOf("handling=lenient; return=minimal");
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
        assertEquals("minimal", prefer.getPreference());
        assertEquals("lenient", prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer7() {
        final Prefer prefer = Prefer.valueOf("respond-async; depth-noroot");
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getPreference());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertTrue(prefer.getRespondAsync());
        assertTrue(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer8() {
        final Prefer prefer = Prefer.valueOf("handling=strict; return=minimal");
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
        assertEquals("minimal", prefer.getPreference());
        assertEquals("strict", prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPrefer9() {
        final Prefer prefer = Prefer.valueOf("handling=blah; return=minimal");
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
        assertEquals("minimal", prefer.getPreference());
        assertNull(prefer.getHandling());
        assertNull(prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testPreferInvalidWait() {
        final Prefer prefer = Prefer.valueOf("wait=blah");
        assertNull(prefer);
    }

    @Test
    void testPrefer10() {
        final Prefer prefer = Prefer.valueOf("wait=4");
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
        assertNull(prefer.getPreference());
        assertNull(prefer.getHandling());
        assertEquals((Integer)4, prefer.getWait());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testStaticInclude() {
        final Prefer prefer = Prefer.ofInclude();
        assertEquals("representation", prefer.getPreference());
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
    }

    @Test
    void testStaticOmit() {
        final Prefer prefer = Prefer.ofOmit();
        assertEquals("representation", prefer.getPreference());
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
    }

    @Test
    void testPreferWithNullValues() {
        final Prefer prefer = new Prefer(null, null, null, null, null, 0);
        assertNull(prefer.getPreference());
        assertNull(prefer.getHandling());
        assertEquals(0, prefer.getWait());
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
        assertFalse(prefer.getRespondAsync());
        assertFalse(prefer.getDepthNoroot());
    }

    @Test
    void testNullPrefer() {
        assertNull(Prefer.valueOf(null));
    }
}
