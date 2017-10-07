package server;

import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {
	public static ModelAndView login(Request req, Response res) {
		return new ModelAndView(null, "login/login.hbs");
	}

	public static Void validate(Request req, Response res) {
		String email = req.queryParams("email");
		res.cookie("email", email);
		res.redirect("/home");
		return null;
	}
}