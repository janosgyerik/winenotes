package com.winenotes.tools;

public class StringTools {

    /**
     * Convert to title case. Also fix non-title characters: convert to lowercase.
     * <p/>
     * rue de l'armorique => Rue De L'Armorique
     * mont saint-michel => Mont Saint-Michel
     * HELLO => Hello
     *
     * @param input to convert
     * @return input converted to "title case" like in python
     */
    public static String toTitleCaseFull(String input) {
        if (input == null || input.trim().length() < 1) return null;
        input = input.trim();

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
     * <p/>
     * rue de l'armorique => Rue De L'Armorique
     * mont saint-michel => Mont Saint-Michel
     * HELLO => Hello
     *
     * @param input to convert
     * @return input converted to "title case" like in python
     */
    public static String toTitleCase(String input) {
        if (input == null || input.trim().length() < 1) return null;
        input = input.trim();

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

    public static String capitalize(String input) {
        if (input == null || input.trim().length() < 1) return null;
        input = input.trim();
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }

    public static String toFrenchTitleCaseFull(String input) {
        String input2 = toTitleCaseFull(input);
        if (input2 == null) {
            return null;
        }
        return input2.replaceAll("(?<=\\b[DL]) ", "'");
    }
}
