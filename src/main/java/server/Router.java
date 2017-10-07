package server;

import controllers.EmpresasController;
import controllers.HomeController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue).build();

		Spark.staticFiles.location("/public");

		Spark.get("/", LoginController::login, engine);
		Spark.post("/", LoginController::validate);// falta ruta de post (logueo usuario)
		Spark.get("/home", HomeController::home, engine);
		Spark.get("/empresas", EmpresasController::listar, engine);
		Spark.get("/empresas/:id", EmpresasController::mostrar, engine);
	}

}