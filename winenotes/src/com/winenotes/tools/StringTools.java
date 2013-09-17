package com.winenotes.tools;

public class StringTools {

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
    public static String toTitleCaseFull(String input) {
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

    /**
     * Convert to title case. Do not touch non-title characters.
     *
     * rue de l'armorique => Rue De L'Armorique
     * mont saint-michel => Mont Saint-Michel
     * HELLO => Hello
     *
     * @param input to convert
     * @return input converted to "title case" like in python
     */
    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isLetter(c)) {
                if (nextTitleCase) {
                    c = Character.toTitleCase(c);
                    nextTitleCase = false;
                }
            } else {
                nextTitleCase = !Character.isDigit(c);
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
