package controllers;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndicadoresController implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView show(Request req, Response res) {
		return new ModelAndView(null, "indicadores/indicadores.hbs");
	}

	public Void createIndicador(Request req, Response res) {
		String formulaIndicador = req.queryParams("indicador");
		withTransaction(()-> {
			Usuario.instance().crearIndicador(formulaIndicador);
			entityManager().merge(Usuario.instance());
		});
		res.redirect("/indicadores/new");
		return null;
	}
}
