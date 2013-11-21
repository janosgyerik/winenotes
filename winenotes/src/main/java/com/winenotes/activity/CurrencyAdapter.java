package com.winenotes.activity;

import android.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CurrencyAdapter extends ArrayAdapter<Currency> {

    public static final Currency[] CURRENCY_CHOICES = new Currency[]{
            Currency.fromKey("USD"),
            Currency.fromKey("EUR"),
            Currency.fromKey("GBP"),
            Currency.fromKey("JPY"),
            Currency.fromKey("HKD"),
            Currency.fromKey("BRL"),
            Currency.fromKey("HUF"),
            Currency.fromKey("RUB"),
    };
    private final LayoutInflater layoutInflater;

    public CurrencyAdapter(Context context) {
        super(context, android.R.layout.simple_spinner_item, CURRENCY_CHOICES);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = layoutInflater.inflate(R.layout.simple_spinner_item, parent, false);
        TextView label = (TextView) row.findViewById(android.R.id.text1);
        label.setText(CURRENCY_CHOICES[position].symbol);

        return row;
    }
}
