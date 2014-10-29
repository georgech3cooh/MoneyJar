package com.moneyjar;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MoneyJar {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		Logger logger = Logger.getLogger(MoneyJar.class);
		logger.info(">> MoneyJar Controller called");
		
		return "index";
	}

}
