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
		List<Indicador> indicadoresAplicables = Usuario.instance().todosLosIndicadoresAplicablesA(empresa);
		cuentasSeleccionadas.addAll(empresa.resultadosParaEstosIndicadores((List<Indicador>) indicadoresAplicables));			
		cuentasSeleccionadas = obtenerCuentasSinRepetidos(cuentasSeleccionadas);
		return cuentasSeleccionadas;
	}
	
	private static List<Cuenta> obtenerCuentasSinRepetidos(List<Cuenta> cuentasSinRepetidos){
		for (int i = 0; i < cuentasSinRepetidos.size(); i++) {
			for (int j = i + 1; j < cuentasSinRepetidos.size(); j++) {
				if(cuentasSinRepetidos.get(i).getAnio().equals(cuentasSinRepetidos.get(j).getAnio())
						&& cuentasSinRepetidos.get(i).getTipoCuenta().equals(cuentasSinRepetidos.get(j).getTipoCuenta())
						&& cuentasSinRepetidos.get(i).getValor() == cuentasSinRepetidos.get(j).getValor()) {
					cuentasSinRepetidos.remove(cuentasSinRepetidos.get(i));
					j--;
				}
			}
		}
		return cuentasSinRepetidos;
	}
}
