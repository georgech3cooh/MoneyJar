package com.khominhvi.moneyjar;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.khominhvi.moneyjar.reports.CategoryTotal;
import com.khominhvi.moneyjar.hibernate.ReportsDao;

@Controller
public class ReportsController {

	@Autowired
	private ReportsDao reportsDao;
	private static Logger logger = Logger.getLogger(ReportsController.class);

	@RequestMapping(value = "/reports/category-totals", method = RequestMethod.GET, produces={"application/json"})
	public @ResponseBody List<CategoryTotal> getCategoryTotals() {
		
		logger.debug("Retrieving category total reports");
		
		List<CategoryTotal> categoryTotals = reportsDao.getCategoryTotals();
		logger.debug("Retrieved category totals: " + categoryTotals.size());

		return categoryTotals;
	}

}
