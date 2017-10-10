package controllers;

import dominio.RepositorioUsuarios;
import dominio.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController {
	public static ModelAndView login(Request req, Response res) {
		return new ModelAndView(null, "login/login.hbs");
	}

	public static Void validate(Request req, Response res) {
		String email = req.queryParams("email");
		String password = req.queryParams("password");
		Long idUsuario = new RepositorioUsuarios().obtenerId(email, password);//NO SE ESTA CATCHEANDO NoExisteUsuarioError (!!!)
		Usuario.instance = new RepositorioUsuarios().obtenerPorId(idUsuario);
		res.cookie("email", email);
		res.cookie("idUsuario", Long.toString(idUsuario));
		res.redirect("/home");
		return null;
	}
}