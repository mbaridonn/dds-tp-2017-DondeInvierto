package controllers;

import java.math.BigDecimal;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.*;
import dominio.empresas.Cuenta;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class CuentasController implements WithGlobalEntityManager, TransactionalOps{
	public static ModelAndView listar(Request req, Response res){
		Map<String, List<Cuenta>> model = new HashMap<>();
		Cuenta unaCuenta = new Cuenta(Year.parse("2017"),"EBITDA",10000); // La idea es que las saque de la base de datos!
		Cuenta otraCuenta = new Cuenta(Year.parse("2017"),"FDS ",10000);
		List<Cuenta> cuentas = new ArrayList<Cuenta>();
		cuentas.add(unaCuenta);
		cuentas.add(otraCuenta);
		model.put("cuentas", cuentas);
		return new ModelAndView(model, "cuentas/cuentas.hbs");
	}
	
	public static ModelAndView mostrar(Request req, Response res){
		Map<String, Cuenta> model = new HashMap<>();
		Cuenta unaCuenta = new Cuenta(Year.parse("2017"),"EBITDA",10000); // String id = req.params("id"); Y bucar por id en la base de datos!
		model.put("cuenta",unaCuenta);
		return new ModelAndView(model, "cuentas/show.hbs");
	}
}
