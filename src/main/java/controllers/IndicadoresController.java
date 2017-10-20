package controllers;

import java.util.HashMap;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.usuarios.Usuario;
import excepciones.EntidadExistenteError;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class IndicadoresController implements WithGlobalEntityManager, TransactionalOps{
	public ModelAndView show(Request req, Response res) {
		String mensajeOperacion = "";
		String codigoOperacion = req.queryParams("codOp");
		if (codigoOperacion!= null && codigoOperacion.equals("1")) mensajeOperacion = "Ya existe un indicador con ese nombre";
		else if (codigoOperacion!= null && codigoOperacion.equals("2")) mensajeOperacion = "Indicador cargado!";
		Map<String, String> model = new HashMap<>();
		model.put("mensajeOperacion", mensajeOperacion);
		return new ModelAndView(model, "indicadores/indicadores.hbs");
	}

	public Void createIndicador(Request req, Response res) {
		String formulaIndicador = req.queryParams("indicador");
		try {
			withTransaction(()-> {
				Usuario.instance().crearIndicador(formulaIndicador);
				entityManager().merge(Usuario.instance());
			});
			res.redirect("/indicadores/new?codOp=2");
		} catch (EntidadExistenteError e){
			res.redirect("/indicadores/new?codOp=1");
		}
		return null;
	}
}
