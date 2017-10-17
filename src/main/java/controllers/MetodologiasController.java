package controllers;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MetodologiasController{
	public static ModelAndView show (Request req, Response res) {
		return new ModelAndView(null, "metodologias/metodologias.hbs");
	}

}
