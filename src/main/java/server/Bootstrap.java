package server;

import java.time.Year;
import java.util.Arrays;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;
import dominio.empresas.RepositorioEmpresas;
import dominio.indicadores.RepositorioIndicadores;
import dominio.metodologias.Antiguedad;
import dominio.metodologias.CondicionPrioritaria;
import dominio.metodologias.CondicionTaxativa;
import dominio.metodologias.Metodologia;
import dominio.metodologias.OperacionAgregacion;
import dominio.metodologias.OperacionRelacional;
import dominio.metodologias.OperandoCondicion;
import dominio.metodologias.RepositorioMetodologias;
import dominio.usuarios.RepositorioUsuarios;
import dominio.usuarios.Usuario;
import excepciones.EntidadExistenteError;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

	public void init() {
		withTransaction(() -> {
			cargarUsuarioAdministrador();
			cargarEmpresasPredefinidas();
			cargarIndicadoresPredefinidos();
			cargarMetodologiasPredefinidas();
		});
	}

	private void cargarUsuarioAdministrador() {
		try{
			new RepositorioUsuarios().agregar(new Usuario("admin", "admin"));
		}
		catch (EntidadExistenteError e){
			//Si ya existe el administrador, no hago nada
		}
	}

	private void cargarEmpresasPredefinidas() {
		Empresa empresa1 = new Empresa("Empresa1", Arrays.asList(new Cuenta(Year.parse("2017"), "EBITDA", 10000),
				new Cuenta(Year.parse("2017"), "FDS ", 10000)));
		Empresa empresa2 = new Empresa("Empresa2", Arrays.asList(new Cuenta(Year.parse("2017"), "EBITDA", 12500),
				new Cuenta(Year.parse("2017"), "FDS ", 25000)));
		Empresa empresa3 = new Empresa("Empresa3", Arrays.asList(new Cuenta(Year.parse("2017"), "EBITDA", 15500),
				new Cuenta(Year.parse("2017"), "FDS ", 38000)));
		new RepositorioEmpresas().agregarMultiplesEmpresas(Arrays.asList(empresa1, empresa2, empresa3));
	}

	private void cargarIndicadoresPredefinidos() {
		new RepositorioIndicadores().agregarMultiplesIndicadores(
				Arrays.asList(new String[] { "INGRESONETO = netooperacionescontinuas + netooperacionesdiscontinuas",
						"INDICADORDOS = cuentarara + fds", "INDICADORTRES = INGRESONETO * 10 + ebitda", "A = 5 / 3",
						"PRUEBA = ebitda + 5" }));
	}
	
	private void cargarMetodologiasPredefinidas() {
		Metodologia metodologia = new Metodologia("Metodologia");
		metodologia.agregarCondicionPrioritaria(new CondicionPrioritaria(new OperandoCondicion(OperacionAgregacion.Ultimo, new Antiguedad(), 1), OperacionRelacional.Mayor));
		metodologia.agregarCondicionTaxativa(new CondicionTaxativa(new OperandoCondicion(OperacionAgregacion.Ultimo, new Antiguedad(), 1), OperacionRelacional.Menor, 10));
		Metodologia metodologia2 = new Metodologia("Metodologia2");
		metodologia2.agregarCondicionPrioritaria(new CondicionPrioritaria(new OperandoCondicion(OperacionAgregacion.Ultimo, new Antiguedad(), 1), OperacionRelacional.Mayor));
		metodologia2.agregarCondicionTaxativa(new CondicionTaxativa(new OperandoCondicion(OperacionAgregacion.Ultimo, new Antiguedad(), 1), OperacionRelacional.Menor, 10));
		
		new RepositorioMetodologias().agregarMetodologias(Arrays.asList(metodologia,metodologia2));
	}
}
