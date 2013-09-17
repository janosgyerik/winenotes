package com.winenotes;

import com.winenotes.tools.StringTools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FrenchTitleCaseFullTest {

    static String toFrenchTitleCaseFull(String input) {
        return StringTools.toFrenchTitleCaseFull(input);
    }

    @Test
    public void testSingleWord() {
        assertEquals("Hello", toFrenchTitleCaseFull("hello"));
        assertEquals("Hello", toFrenchTitleCaseFull("HELLO"));
        assertEquals("Hello", toFrenchTitleCaseFull("heLLO"));
        assertEquals("4ello", toFrenchTitleCaseFull("4ello"));
        assertEquals("123", toFrenchTitleCaseFull("123"));
    }

    @Test
    public void testSimpleWords() {
        assertEquals("Le Figaro", toFrenchTitleCaseFull("le figaro"));
    }

    @Test
    public void testDashedWords() {
        assertEquals("Mont Saint-Michel", toFrenchTitleCaseFull("mont saint-michel"));
    }

    @Test
    public void testFrench() {
        assertEquals("Rue De L'Armorique", toFrenchTitleCaseFull("rue de l'armorique"));
    }

    @Test
    public void testMissingApostrophes() {
        assertEquals("Rue De L'Armorique", toFrenchTitleCaseFull("rue de l armorique"));
        assertEquals("Rue D'Armorique", toFrenchTitleCaseFull("rue d armorique"));
        assertEquals("L'Armorique", toFrenchTitleCaseFull("l armorique"));
    }
}