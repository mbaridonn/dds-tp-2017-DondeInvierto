package controllers;

import dominio.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndicadoresController {
	public static ModelAndView show(Request req, Response res) {
		return new ModelAndView(null, "indicadores/indicadores.hbs");
	}

	public static Void createIndicador(Request req, Response res) {
		String formulaIndicador = req.queryParams("indicador");
		Usuario.instance().crearIndicador(formulaIndicador);
		res.redirect("/indicadores/new");
		return null;
	}
}
