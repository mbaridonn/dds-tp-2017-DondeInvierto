package server;

import controllers.HomeController;
import controllers.CuentasController;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder
				.create()
				.withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue)
				.build();

		Spark.staticFiles.location("/public");
		
		CuentasController cuentasController = new CuentasController();
		
		Spark.get("/", HomeController::home, engine);
		Spark.get("/cuentas", CuentasController::listar, engine);
		Spark.get("/cuentas/:id", CuentasController::mostrar, engine);
	}

}