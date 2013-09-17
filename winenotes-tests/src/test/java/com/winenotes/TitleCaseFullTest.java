package com.winenotes;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TitleCaseFullTest {

    /**
     * Convert to title case. Also fix non-title characters: convert to lowercase.
     *
     * rue de l'armorique => Rue De L'Armorique
     * mont saint-michel => Mont Saint-Michel
     * HELLO => Hello
     *
     * @param input to convert
     * @return input converted to "title case" like in python
     */
    static String toTitleCaseFull(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                if (nextTitleCase) {
                    c = Character.toTitleCase(c);
                    nextTitleCase = false;
                } else {
                    c = Character.toLowerCase(c);
                }
            } else {
                nextTitleCase = !Character.isDigit(c);
            }

            titleCase.append(c);
        }

        return titleCase.toString();
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