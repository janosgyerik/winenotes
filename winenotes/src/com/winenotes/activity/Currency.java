package com.winenotes.activity;

import java.text.DecimalFormat;

public class Currency {

    final String key;
    final String symbol;
    final boolean prefixed;

    public Currency(String key, String symbol, boolean prefixed) {
        this.key = key;
        this.symbol = symbol;
        this.prefixed = prefixed;
    }

    public Currency(String key, String symbol) {
        this(key, symbol, true);
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Currency && ((Currency) other).key.equals(key);
    }

    public String stringValue(double value) {
        String stringValue = new DecimalFormat("#.##").format(value);
        return stringValue.equals("0") ? null : stringValue;
    }

    public String format(double value) {
        String stringValue = stringValue(value);
        if (stringValue == null) {
            return null;
        }

        if (prefixed) {
            return String.format("%s%s", symbol, stringValue);
        } else {
            return String.format("%s %s", stringValue, symbol);
        }
    }

    public static Currency fromKey(String key) {
        if (key == null) {
            return fromKey("USD");
        }
        if (key.equals("EUR")) {
            return new Currency(key, "€");
        }
        if (key.equals("GBP")) {
            return new Currency(key, "£");
        }
        if (key.equals("JPY") || key.equals("CNY")) {
            return new Currency(key, "¥");
        }
        if (key.equals("HKD")) {
            return new Currency(key, "HK$");
        }
        if (key.equals("BRL")) {
            return new Currency(key, "R$");
        }
        if (key.equals("HUF")) {
            return new Currency(key, "Ft", false);
        }
        return new Currency(key, "$");
    }
}
