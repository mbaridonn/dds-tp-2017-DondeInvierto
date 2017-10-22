package controllers;

import java.util.HashMap;
import java.util.Map;

import dominio.usuarios.RepositorioUsuarios;
import dominio.usuarios.Usuario;
import excepciones.NoExisteUsuarioError;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController{
	public ModelAndView login(Request req, Response res) {
		String mensajeError = req.cookie("mensajeError");
		if (mensajeError == null) mensajeError = "";
		res.cookie("mensajeError", "");
		Map<String, String> model = new HashMap<>();
		model.put("mensajeError", mensajeError);
		return new ModelAndView(model, "login/login.hbs");
	}

	public Void validate(Request req, Response res) {
		String email = req.queryParams("email");
		String password = req.queryParams("password");
		try{
			Long idUsuario = new RepositorioUsuarios().obtenerId(email, password);
			Usuario.instance(new RepositorioUsuarios().obtenerPorId(idUsuario));
			res.cookie("email", email);
			res.cookie("idUsuario", Long.toString(idUsuario));
			res.redirect("/home");
		} catch (NoExisteUsuarioError e){
			res.cookie("mensajeError", e.getMessage());
			res.redirect("/");
		}
		return null;
	}
}