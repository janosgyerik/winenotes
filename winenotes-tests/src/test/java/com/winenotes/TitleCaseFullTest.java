package com.winenotes;

import com.winenotes.tools.StringTools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TitleCaseFullTest {

    static String toTitleCaseFull(String input) {
        return StringTools.toTitleCaseFull(input);
    }

    @Test
    public void testSingleWord() {
        assertEquals("Hello", toTitleCaseFull("hello"));
        assertEquals("Hello", toTitleCaseFull("HELLO"));
        assertEquals("Hello", toTitleCaseFull("heLLO"));
        assertEquals("4ello", toTitleCaseFull("4ello"));
        assertEquals("123", toTitleCaseFull("123"));
    }

    @Test
    public void testSimpleWords() {
        assertEquals("Le Figaro", toTitleCaseFull("le figaro"));
    }

    @Test
    public void testDashedWords() {
        assertEquals("Mont Saint-Michel", toTitleCaseFull("mont saint-michel"));
    }

    @Test
    public void testFrench() {
        assertEquals("Rue De L'Armorique", toTitleCaseFull("rue de l'armorique"));
    }
}