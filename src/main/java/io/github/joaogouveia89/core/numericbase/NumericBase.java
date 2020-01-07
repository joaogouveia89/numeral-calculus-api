package io.github.joaogouveia89.core.numericbase;

public class NumericBase {
	public static final int BINARY = 2;
	public static final int OCTAL = 8;
	public static final int DECIMAL = 10;
	public static final int HEXADECIMAL = 16;
	
	public static final String BINARY_REGEX_VALIDATION = "DASA";
	
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
