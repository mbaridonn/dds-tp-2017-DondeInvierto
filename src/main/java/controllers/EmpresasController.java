package controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;
import dominio.empresas.RepositorioEmpresas;
import dominio.indicadores.Indicador;
import dominio.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class EmpresasController implements WithGlobalEntityManager, TransactionalOps {
	public static ModelAndView listar(Request req, Response res) {
		Map<String, List<Empresa>> model = new HashMap<>();
		List<Empresa> empresas = new RepositorioEmpresas().obtenerTodos();
		model.put("empresas", empresas);
		return new ModelAndView(model, "empresas/empresas.hbs");
	}

	public static ModelAndView mostrar(Request req, Response res) {
		Map<String, List<Cuenta>> model = new HashMap<>();
		String id = req.params("id");
		Empresa empresa = new RepositorioEmpresas().obtenerPorId(Long.parseLong(id));
		List<Cuenta> cuentas = getCuentasEmpresa(empresa);
		model.put("cuentas",cuentas);
		return new ModelAndView(model, "cuentas/cuentas.hbs");
	}
	
	private static List<Cuenta> getCuentasEmpresa(Empresa empresa) {
		List<Cuenta> cuentasSeleccionadas = empresa.getCuentas();
		cuentasSeleccionadas.addAll(empresa.resultadosParaEstosIndicadores(Usuario.instance().getIndicadores()));			
		cuentasSeleccionadas.sort((unaCuenta, otraCuenta) -> otraCuenta.getAnio().compareTo(unaCuenta.getAnio()));
		return cuentasSeleccionadas;
	}
}
