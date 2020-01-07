package io.github.joaogouveia89.core.numericbase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.joaogouveia89.core.numericbase.NumericBaseConversion.Builder.NumericBaseException;

public class NumericBaseConversion {
	
	private List<NumericBase> conversions = new ArrayList();
	private boolean validNumber = false;
	
	private NumericBaseConversion() {}
	
	public List<NumericBase> getAllConversions() throws NumericBaseException{
		if(!validNumber)
			throw new NumericBaseException(NumericBaseException.MALFORMED_OBJECT_REBUILD);
		return conversions;
	}
	
	public static class Builder {
		private NumericBase nb = null;
		private NumericBase baseTen = null;
		/* Decimal is added on build logic */
		private List<Integer> outputBases = new ArrayList<Integer>() {
			{
				add(NumericBase.BINARY);
				add(NumericBase.OCTAL);
				add(NumericBase.HEXADECIMAL);
			}		
		};
		private NumericBaseConversion nbc = new NumericBaseConversion();
		
		public Builder setInput(NumericBase nb) {
			this.nb = nb;
			if(nb.getBase() == NumericBase.DECIMAL) {
				this.baseTen = nb;
			}
			return this;
		}
		
		public Builder addOutputBases(int[] bases) {
			for(int n = 0; n < bases.length; n++) {
				outputBases.add(bases[n]);
			}
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
			return (nb.getBase() < 11 && characterInt > 47 && characterInt < (48 + nb.getBase())) ||
					(nb.getBase() > 10 && characterInt > 47 && characterInt < 58 || nb.getBase() > 10 && characterInt > 64 && characterInt < (65 + nb.getBase() - 10));
		}
		
		private boolean isValid() {
			for(int i = 0; i < nb.getNumber().length(); i++) {
				if(!ownsToPossibleCharactersSet(nb.getNumber().charAt(i)))
					return false;
			}
			return true;
		}
		
		private NumericBase toDecimal() {
			int decimal = 0;
			for(int i = (nb.getNumber().length()-1); i >= 0 ; i--) {
				decimal += Math.pow(nb.getBase(), i) * Character.getNumericValue(nb.getNumber().charAt(nb.getNumber().length() - i - 1));
			}
			NumericBase bt = new NumericBase(Integer.toString(decimal), NumericBase.DECIMAL);
			this.baseTen = bt;
			return bt;
		}
		
		private NumericBase fromDecimal(int desiredBase, NumericBase nb) {
			int num = Integer.parseInt(nb.getNumber());
			String result = "";
			while(num != 0) {
				result = getNumberChar((num%desiredBase)) + result;
				num /= desiredBase;
			}
			return new NumericBase(result, desiredBase);
		}
		
		public NumericBaseConversion build() throws NumericBaseException {
			if(!isValid()) {
				throw new NumericBaseException(NumericBaseException.INVALID_INPUT_FOR_BASE);
			}
			nbc.validNumber = true;
			nbc.conversions.add(nb);
			if(nb.getBase() != NumericBase.DECIMAL) {
				nbc.conversions.add(toDecimal());
			}
			for(int i = 0; i < outputBases.size(); i++) {
				if(outputBases.get(i) != nb.getBase()) {
					nbc.conversions.add(fromDecimal(outputBases.get(i),this.baseTen));
				}
			}
			return nbc;
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