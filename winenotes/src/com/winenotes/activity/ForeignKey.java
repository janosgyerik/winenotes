package com.winenotes.activity;

public class ForeignKey {

    Integer refId;
    String value;

    public ForeignKey(Integer refId, String value) {
        this.refId = refId;
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object otherValue) {
        return otherValue instanceof Integer && refId.equals(otherValue);
    }
}
