package io.github.joaogouveia89.routes;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.github.joaogouveia89.core.NumericBase;
import io.github.joaogouveia89.core.NumericBase.Builder.NumericBaseException;

@RestController
@RequestMapping("/numericbase")
public class NumericBaseController {
	@RequestMapping(method = RequestMethod.GET)
	public HashMap<Integer, String> convert(
			@RequestParam(value = "number", required = true) String number,
			@RequestParam(value = "outputBases", required = false) List<Integer> bases) throws NumericBaseException {
		NumericBase numericBase = new NumericBase.Builder()
				.setNumber(number)
				.setInputBase(2)
				.addOutputBase(NumericBase.BINARY)
				.addOutputBase(NumericBase.DECIMAL)
				.addOutputBase(NumericBase.OCTAL)
				.addOutputBase(NumericBase.HEXADECIMAL)
				.build();
		return numericBase.getAllConversions();
	}
}
