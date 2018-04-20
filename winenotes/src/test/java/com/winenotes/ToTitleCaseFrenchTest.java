package com.winenotes;

import com.winenotes.tools.StringTools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToTitleCaseFrenchTest extends ToTitleCaseTest {

    @Override
    String toTitle(String input) {
        return StringTools.toTitleCaseFrench(input);
    }

    @Test
    public void testMissingApostropheAtStart() {
        assertEquals("L'Armorique", toTitle("l armorique"));
        assertEquals("D'Armorique", toTitle("d armorique"));
    }

    @Test
    public void testMissingApostropheInMiddle() {
        assertEquals("Rue De L'Armorique", toTitle("rue de l armorique"));
        assertEquals("Rue D'Armorique", toTitle("rue d armorique"));
    }

    @Test
    public void testFalsePositives() {
        assertEquals("El Armorique", toTitle("el armorique"));
        assertEquals("Ad Armorique", toTitle("ad armorique"));
        assertEquals("Z Armorique", toTitle("z armorique"));
    }
}