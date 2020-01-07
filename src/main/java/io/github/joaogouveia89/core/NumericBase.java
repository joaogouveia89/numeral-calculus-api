package io.github.joaogouveia89.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.joaogouveia89.core.NumericBase.Builder.NumericBaseException;

public class NumericBase {
	
	public static final int BINARY = 2;
	public static final int OCTAL = 8;
	public static final int DECIMAL = 10;
	public static final int HEXADECIMAL = 16;
	
	private HashMap<Integer, String> conversions = new HashMap();
	private boolean validNumber = false;
	
	private NumericBase() {}
	
	public String getConversion(int base) throws NumericBaseException{
		if(!validNumber)
			throw new NumericBaseException(NumericBaseException.MALFORMED_OBJECT_REBUILD);
		return conversions.get(base);
	}
	
	public HashMap<Integer, String> getAllConversions() throws NumericBaseException{
		if(!validNumber)
			throw new NumericBaseException(NumericBaseException.MALFORMED_OBJECT_REBUILD);
		return conversions;
	}
	
	public static class Builder {
		private int base = DECIMAL;
		private String input;
		/* Decimal is added on build logic */
		private List<Integer> outputBases = new ArrayList<Integer>() {
			{
				add(BINARY);
				add(OCTAL);
				add(HEXADECIMAL);
			}		
		};
		private NumericBase nb = new NumericBase();
		
		public Builder setInputBase(int base) {
			this.base = base;
			return this;
		}
		
		public Builder setNumber(String input){
			this.input = input;
			return this;
		}
		
		public Builder addOutputBase(int base) {
			outputBases.add(base);
			return this;
		}
		
		private char getNumberChar(int n) {
			char c;
			if(n >= 0 && n < 10) {
				c = (char)(n + 48);
			}else {
				n -= 10;
				c = (char)(n + 65);
			}			
			return c;
		}
		
		private boolean ownsToPossibleCharactersSet(char c) {
			int characterInt = (int) c;
			return (base < 11 && characterInt > 47 && characterInt < (48 + base)) ||
					(base > 10 && characterInt > 47 && characterInt < 58 || base > 10 && characterInt > 64 && characterInt < (65 + base - 10));
		}
		
		private boolean isValid() {
			for(int i = 0; i < input.length(); i++) {
				if(!ownsToPossibleCharactersSet(input.charAt(i)))
					return false;
			}
			return true;
		}
		
		private String toDecimal() {
			int decimal = 0;
			for(int i = (input.length()-1); i >= 0 ; i--) {
				decimal += Math.pow(base, i) * Character.getNumericValue(input.charAt(input.length() - i - 1));
			}
			return Integer.toString(decimal);
		}
		
		private String fromDecimal(int desiredBase, String dec) {
			int num = Integer.parseInt(dec);
			String result = "";
			while(num != 0) {
				result = getNumberChar((num%desiredBase)) + result;
				num /= desiredBase;
			}
			return result;
		}
		
		public NumericBase build() throws NumericBaseException {
			if(!isValid()) {
				throw new NumericBaseException(NumericBaseException.INVALID_INPUT_FOR_BASE);
			}
			nb.validNumber = true;
			nb.conversions.put(this.base, this.input);
			if(base != DECIMAL) {
				nb.conversions.put(DECIMAL, toDecimal());
			}
			for(int i = 0; i < outputBases.size(); i++) {
				if(outputBases.get(i) != base) {
					nb.conversions.put(outputBases.get(i), fromDecimal(outputBases.get(i), nb.conversions.get(DECIMAL)));
				}
			}
			return nb;
		}
		
		public static class NumericBaseException extends Exception{
			public static final String INVALID_INPUT_FOR_BASE = "Invalid input number for base";
			public static final String MALFORMED_OBJECT_REBUILD = "Malformed object, please rebuild it";
			public NumericBaseException(String error) {
				super(error);
			}
		}
	}
}