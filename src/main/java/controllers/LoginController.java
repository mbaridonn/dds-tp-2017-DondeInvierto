package controllers;

import java.util.HashMap;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.usuarios.RepositorioUsuarios;
import dominio.usuarios.Usuario;
import excepciones.NoExisteUsuarioError;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class LoginController implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView login(Request req, Response res) {
		String mensajeOperacion = "";
		String codigoOperacion = req.queryParams("err");
		if (codigoOperacion!= null && codigoOperacion.equals("1")) mensajeOperacion = "Email o password incorrecta";
		Map<String, String> model = new HashMap<>();
		model.put("mensajeOperacion", mensajeOperacion);
		return new ModelAndView(model, "login/login.hbs");
	}

	public Void validate(Request req, Response res) {
		String email = req.queryParams("email");
		String password = req.queryParams("password");
		try{
			Long idUsuario = new RepositorioUsuarios().obtenerId(email, password);
			withTransaction(()->Usuario.instance(new RepositorioUsuarios().obtenerPorId(idUsuario)));
			res.cookie("email", email);
			res.cookie("idUsuario", Long.toString(idUsuario));
			res.redirect("/home");
		} catch (NoExisteUsuarioError e){
			res.redirect("/?err=1");
		}
		return null;
	}
}