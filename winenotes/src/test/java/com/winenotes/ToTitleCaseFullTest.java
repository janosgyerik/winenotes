package com.winenotes;

import com.winenotes.tools.StringTools;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ToTitleCaseFullTest extends ToTitleCaseTestBase {

    @Override
    String toTitle(String input) {
        return StringTools.toTitleCaseFull(input);
    }

    @Test
    public void testSingleWord() {
        super.testSingleWord();
        assertEquals("Hello", toTitle("HELLO"));
        assertEquals("Hello", toTitle("heLLO"));
    }
}