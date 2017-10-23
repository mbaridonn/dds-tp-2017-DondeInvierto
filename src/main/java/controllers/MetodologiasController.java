package controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dominio.empresas.Empresa;
import dominio.empresas.RepositorioEmpresas;
import dominio.metodologias.Metodologia;
import dominio.usuarios.RepositorioUsuarios;
import dominio.usuarios.Usuario;
import spark.ModelAndView;
import spark.Request;
import spark.Response;

public class MetodologiasController{
	public static ModelAndView listar (Request req, Response res) {
		Map<String, List<Metodologia>> model = new HashMap<>();
		Usuario usuarioActivo = new RepositorioUsuarios().obtenerPorId(Long.parseLong(req.cookie("idUsuario")));
		List<Metodologia> metodologias = usuarioActivo.getMetodologias();
		model.put("metodologias", metodologias);
		return new ModelAndView(model, "metodologias/metodologias.hbs");
	}
	
	public static ModelAndView mostrar (Request req, Response res) {
		Map<String, List<Empresa>> model = new HashMap<>();
		Usuario usuarioActivo = new RepositorioUsuarios().obtenerPorId(Long.parseLong(req.cookie("idUsuario")));
		String id = req.params("id");
		Metodologia metodologiaAEvaluar = usuarioActivo.obtenerMetodologiaPorId(Long.parseLong(id));
		List<Empresa> empresas = new RepositorioEmpresas().obtenerTodos();
		List<Empresa> metodologia = Arrays.asList(new Empresa(metodologiaAEvaluar.getNombre(),  null));
		model.put("empresasOrdenadas",metodologiaAEvaluar.evaluarPara(empresas));
		model.put("empresasQueNoCumplen",metodologiaAEvaluar.empresasQueNoCumplenTaxativas(empresas));
		model.put("empresasSinDatos",metodologiaAEvaluar.empresasConDatosFaltantes(empresas));
		model.put("nombreMetodologia", metodologia);
		explicitarListasVacias(model);
		return new ModelAndView(model, "metodologias/metodologiaEvaluada.hbs");
	}

	private static void explicitarListasVacias(Map<String, List<Empresa>> model) {
		for (Map.Entry<String, List<Empresa>> entry : model.entrySet()){
			List<Empresa> listaEmpresas = entry.getValue();
			if (listaEmpresas.isEmpty()){
				listaEmpresas.add(new Empresa("No hay empresas en esta categoria", null));
				entry.setValue(listaEmpresas);
			}
		}
	}

}
