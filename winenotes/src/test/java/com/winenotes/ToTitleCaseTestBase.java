package com.winenotes;

import com.winenotes.tools.StringTools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public abstract class ToTitleCaseTestBase {

    abstract String toTitle(String input);

    @Test
    public void testNullInput() {
        assertNull(toTitle(null));
    }

    @Test
    public void testSingleWord() {
        assertEquals("Hello", toTitle("hello"));
        assertEquals('H', toTitle("HELLO").charAt(0));
        assertEquals('H', toTitle("HeLLO").charAt(0));
        assertEquals("4ello", toTitle("4ello"));
        assertEquals("123", toTitle("123"));
    }

    @Test
    public void testSimpleWords() {
        assertEquals("Le Figaro", toTitle("le figaro"));
    }

    @Test
    public void testDashedWords() {
        assertEquals("Mont Saint-Michel", toTitle("mont saint-michel"));
    }

    @Test
    public void testFrench() {
        assertEquals("Rue De L'Armorique", toTitle("rue de l'armorique"));
    }
}