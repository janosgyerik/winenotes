package com.winenotes.activity;

import java.text.DecimalFormat;

public class Currency {

    // Important: must be the same value as R.string.default_currency
    protected static final String DEFAULT_CURRENCY_KEY = "USD";

    final String key;
    final String symbol;
    final String longName;
    final boolean prefixed;

    public Currency(String key, String symbol, String longName, boolean prefixed) {
        this.key = key;
        this.symbol = symbol;
        this.longName = longName;
        this.prefixed = prefixed;
    }

    public Currency(String key, String symbol, String longName) {
        this(key, symbol, longName, true);
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %s)", symbol, key, longName);
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
            return fromKey(DEFAULT_CURRENCY_KEY);
        }
        if (key.equals("EUR")) {
            return new Currency(key, "€", "Euro", false);
        }
        if (key.equals("GBP")) {
            return new Currency(key, "£", "Pound sterling");
        }
        if (key.equals("JPY") || key.equals("CNY")) {
            return new Currency(key, "¥", "CNY, Yen");
        }
        if (key.equals("HKD")) {
            return new Currency(key, "HK$", "Hong Kong dollar");
        }
        if (key.equals("BRL")) {
            return new Currency(key, "R$", "Brazilian real");
        }
        if (key.equals("HUF")) {
            return new Currency(key, "Ft", "Hungarian forint", false);
        }
        if (key.equals("RUB")) {
            return new Currency(key, "руб", "Russian rouble", false);
        }
        return new Currency(key, "$", "Dollar");
    }
}
