package controllers;

import java.util.Map;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class HomeController {
	public static ModelAndView home(Request req, Response res) {
		Map<String, String> model = req.cookies();
		if (model.get("idUsuario") == null) { //HACER LA VALIDACION EN BEFORE DE SPARK (!!!)
			res.redirect("/");
			return null;
		}
		return new ModelAndView(model, "home/home.hbs");
	}

}