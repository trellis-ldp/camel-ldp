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
public class PreferTest {

    @Test
    public void testPrefer1() {
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
    public void testPrefer1b() {
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
    public void testPrefer1c() {
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
    public void testPrefer2() {
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
    public void testPrefer3() {
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
    public void testPrefer4() {
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
    public void testPrefer5() {
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
    public void testPrefer5b() {
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
    public void testPrefer6() {
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
    public void testPrefer7() {
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
    public void testPrefer8() {
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
    public void testPrefer9() {
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
    public void testPreferInvalidWait() {
        final Prefer prefer = Prefer.valueOf("wait=blah");
        assertNull(prefer);
    }

    @Test
    public void testPrefer10() {
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
    public void testStaticInclude() {
        final Prefer prefer = Prefer.ofInclude();
        assertEquals("representation", prefer.getPreference());
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
    }

    @Test
    public void testStaticOmit() {
        final Prefer prefer = Prefer.ofOmit();
        assertEquals("representation", prefer.getPreference());
        assertTrue(prefer.getInclude().isEmpty());
        assertTrue(prefer.getOmit().isEmpty());
    }

    @Test
    public void testNullPrefer() {
        assertNull(Prefer.valueOf(null));
    }
}
