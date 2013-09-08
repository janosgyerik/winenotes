package com.winenotes.tools;

import java.io.File;
import java.io.FileFilter;

public class PatternFileFilter implements FileFilter {

    private final String pattern;

    public PatternFileFilter(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public boolean accept(File file) {
        return file.getName().matches(pattern);
    }
}


