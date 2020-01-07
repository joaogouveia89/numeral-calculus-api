package io.github.joaogouveia89.routes;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.joaogouveia89.core.numericbase.NumericBase;
import io.github.joaogouveia89.core.numericbase.NumericBaseConversion;
import io.github.joaogouveia89.core.numericbase.NumericBaseConversion.Builder.NumericBaseException;

@RestController
@RequestMapping("/numericbase")
public class NumericBaseController {
	@RequestMapping(method = RequestMethod.GET, path = "/convert/{number}/{base}")
	public List<NumericBase> convert(
			@PathVariable String number,
			@PathVariable int base) throws NumericBaseException {
		NumericBase nb = new NumericBase(number, base);
		NumericBaseConversion numericBase = new NumericBaseConversion.Builder()
				.setInput(nb)
				.build();
		return numericBase.getAllConversions();
	}
}
