package controllers;

import dominio.usuarios.RepositorioUsuarios;
import dominio.usuarios.Usuario;
import excepciones.NoExisteUsuarioError;
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
		try{
			Long idUsuario = new RepositorioUsuarios().obtenerId(email, password);
			Usuario.instance(new RepositorioUsuarios().obtenerPorId(idUsuario));
			res.cookie("email", email);
			res.cookie("idUsuario", Long.toString(idUsuario));
			res.redirect("/home");
		} catch (NoExisteUsuarioError e){
			res.redirect("/");//CÓMO INDICO QUE EL USUARIO NO EXISTE, SI NO PUEDO DEVOLVER NADA AL CLIENTE ???
		}
		return null;
	}
}