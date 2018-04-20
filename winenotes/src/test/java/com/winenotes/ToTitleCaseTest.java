package com.winenotes;

import com.winenotes.tools.StringTools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToTitleCaseTest extends ToTitleCaseTestBase {

    @Override
    String toTitle(String input) {
        return StringTools.toTitleCase(input);
    }

    @Test
    public void testSingleWord() {
        super.testSingleWord();
        assertEquals("HELLO", toTitle("HELLO"));
        assertEquals("HeLLO", toTitle("heLLO"));
    }
}