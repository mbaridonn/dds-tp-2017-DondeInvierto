package controllers;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
	public static ModelAndView home(Request req, Response res) {
		Map<String, String> model = req.cookies();
		return new ModelAndView(model, "home/home.hbs");
	}

}