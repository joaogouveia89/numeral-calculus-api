package io.github.joaogouveia89;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NumeralCalculusAPIMainController {

	@RequestMapping("/")
	@ResponseBody
	public String hello() {
		return "Hello";
	}
}
