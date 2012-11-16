package com.winenotes;

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
		if (otherValue instanceof Integer) {
			return refId.equals(otherValue);
		}
		return false;
	}
}
