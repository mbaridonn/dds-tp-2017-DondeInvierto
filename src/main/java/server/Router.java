package server;

import controllers.EmpresasController;
import controllers.HomeController;
import controllers.IndicadoresController;
import controllers.LoginController;
import controllers.MetodologiasController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue).build();

		Spark.staticFiles.location("/public");

		Spark.before(FiltroAutenticacion::validarLogueo);
		Spark.get("/", LoginController::login, engine);
		Spark.post("/", LoginController::validate);
		Spark.get("/home", HomeController::home, engine);
		Spark.get("/empresas", EmpresasController::listar, engine);
		Spark.get("/empresas/:id", EmpresasController::mostrar, engine);
		Spark.get("/indicadores/new", new IndicadoresController()::show, engine);
		Spark.post("/indicadores/new", new IndicadoresController()::createIndicador);
		Spark.get("/metodologias", MetodologiasController::listar, engine);
		Spark.get("/metodologias/:id", MetodologiasController::mostrar, engine);
	}

}