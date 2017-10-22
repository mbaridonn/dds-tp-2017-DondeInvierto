package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.indicadores.Indicador;
import dominio.usuarios.Usuario;
import excepciones.EntidadExistenteError;
import excepciones.ParserError;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndicadoresController implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView listar(Request req, Response res) {
		Map<String, List<Indicador>> model = new HashMap<>();
		List<Indicador> indicadores = Usuario.instance().getIndicadores();
		model.put("indicadores", indicadores);
		return new ModelAndView(model, "indicadores/indicadores.hbs");
	}
	
	public ModelAndView newForm(Request req, Response res) {
		String mensajeError = req.cookie("mensajeError");
		if (mensajeError == null) mensajeError = "";
		res.cookie("mensajeError", "");
		Map<String, String> model = new HashMap<>();
		model.put("mensajeError", mensajeError);
		return new ModelAndView(model, "indicadores/crearIndicador.hbs");
	}

	public Void create(Request req, Response res) {
		String formulaIndicador = req.queryParams("indicador");
		try {
			withTransaction(()-> {
				Usuario.instance().crearIndicador(formulaIndicador);
				entityManager().merge(Usuario.instance());
			});
			res.redirect("/indicadores");
		} catch (EntidadExistenteError | ParserError e){
			res.cookie("mensajeError", e.getMessage());
			res.redirect("/indicadores/new");
		}
		return null;
	}
}
