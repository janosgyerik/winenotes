package com.winenotes;

import com.winenotes.tools.StringTools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToTitleCaseFullFrenchTest extends ToTitleCaseFullTest {

    @Override
    String toTitle(String input) {
        return StringTools.toTitleCaseFullFrench(input);
    }

    @Test
    public void testMissingApostrophes() {
        assertEquals("Rue De L'Armorique", toTitle("rue de l armorique"));
        assertEquals("Rue D'Armorique", toTitle("rue d armorique"));
        assertEquals("L'Armorique", toTitle("l armorique"));
    }
}