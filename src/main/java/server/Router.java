package server;

import controllers.CuentasController;
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

		CuentasController cuentasController = new CuentasController();

		Spark.get("/", HomeController::home, engine);
		Spark.get("/cuentas", CuentasController::listar, engine);
		Spark.get("/cuentas/:id", CuentasController::mostrar, engine);
		Spark.get("/empresas", EmpresasController::listar, engine);
		Spark.get("/empresas/:id", EmpresasController::mostrar, engine);
	}

}