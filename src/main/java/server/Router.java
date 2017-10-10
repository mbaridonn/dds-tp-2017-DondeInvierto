package server;

import controllers.EmpresasController;
import controllers.HomeController;
import controllers.IndicadoresController;
import controllers.LoginController;
import spark.Request;
import spark.Response;
import spark.Spark;
import spark.template.handlebars.HandlebarsTemplateEngine;
import spark.utils.BooleanHelper;
import spark.utils.HandlebarsTemplateEngineBuilder;

public class Router {

	public static void configure() {
		HandlebarsTemplateEngine engine = HandlebarsTemplateEngineBuilder.create().withDefaultHelpers()
				.withHelper("isTrue", BooleanHelper.isTrue).build();

		Spark.staticFiles.location("/public");

		Spark.before("/*", (Request req, Response res) -> {
			if(req.cookie("idUsuario") == null){
				//Spark.halt(401, "Error de autenticacion"); OPCION 1
				//res.redirect("/"); OPCION 2
			}
		});
		Spark.get("/", LoginController::login, engine);
		Spark.post("/", LoginController::validate);
		Spark.get("/home", HomeController::home, engine);
		Spark.get("/empresas", EmpresasController::listar, engine);
		Spark.get("/empresas/:id", EmpresasController::mostrar, engine);
		Spark.get("/crear-indicador", IndicadoresController::show, engine);
		Spark.post("/crear-indicador", IndicadoresController::createIndicador);
	}

}