package io.github.joaogouveia89.core.numericbase;

import java.util.HashMap;

public class NumericBase {
	public static final int BINARY = 2;
	public static final int OCTAL = 8;
	public static final int DECIMAL = 10;
	public static final int HEXADECIMAL = 16;
	
	public static final HashMap<Integer, String> VALIDATION_REGEX = new HashMap<Integer, String>(){
		{
			put(BINARY, "^[0-1]{1,}$");
			put(OCTAL, "^^[0-7]{1,}$");
			put(DECIMAL, "^[0-9]{1,}$");
			put(HEXADECIMAL, "^[0-9A-F]{1,}$");
		}
	};
	
	private String number;
	private int base;
	
	public NumericBase(String number, int base) {
		this.number = number;
		this.base = base;
	}
	
	public int getBase() {
		return base;
	}
	
	public String getNumber() {
		return number;
	}
}
