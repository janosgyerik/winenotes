package com.winenotes;

import com.winenotes.tools.StringTools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TitleCaseTest {

    static String toTitleCase(String input) {
        return StringTools.toTitleCase(input);
    }

    @Test
    public void testSingleWord() {
        assertEquals("Hello", toTitleCase("hello"));
        assertEquals("HELLO", toTitleCase("HELLO"));
        assertEquals("HeLLO", toTitleCase("heLLO"));
        assertEquals("4ello", toTitleCase("4ello"));
        assertEquals("123", toTitleCase("123"));
    }

    @Test
    public void testSimpleWords() {
        assertEquals("Le Figaro", toTitleCase("le figaro"));
    }

    @Test
    public void testDashedWords() {
        assertEquals("Mont Saint-Michel", toTitleCase("mont saint-michel"));
    }

    @Test
    public void testFrench() {
        assertEquals("Rue De L'Armorique", toTitleCase("rue de l'armorique"));
    }
}