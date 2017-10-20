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
		Empresa empresa1 = new Empresa("Sony", Arrays.asList(new Cuenta(Year.parse("2017"), "freecashflow", 10000),
				new Cuenta(Year.parse("2017"), "fds", 10000)));
		Empresa empresa2 = new Empresa("Google", Arrays.asList(new Cuenta(Year.parse("2017"), "ebitda", 12500),
				new Cuenta(Year.parse("2017"), "freecashflow", 25000)));
		Empresa empresa3 = new Empresa("Apple", Arrays.asList(new Cuenta(Year.parse("2017"), "ebitda", 15500),
				new Cuenta(Year.parse("2017"), "fds", 38000)));
		new RepositorioEmpresas().agregarMultiplesEmpresas(Arrays.asList(empresa1, empresa2, empresa3));
	}

	private void cargarIndicadoresPredefinidos() {
		new RepositorioIndicadores().agregarMultiplesIndicadores(
				Arrays.asList(new String[] { "INGRESONETO = netooperacionescontinuas + netooperacionesdiscontinuas",
						"INDICADORDOS = cuentarara + fds", "INDICADORTRES = INGRESONETO * 10 + ebitda", "A = 5 / 3",
						"PRUEBA = ebitda + 5" }));
	}
	
	private void cargarMetodologiasPredefinidas() {
		Metodologia metodologia = new Metodologia("Pay-back");
		metodologia.agregarCondicionPrioritaria(new CondicionPrioritaria(new OperandoCondicion(OperacionAgregacion.Ultimo, new Antiguedad(), 1), OperacionRelacional.Mayor));
		metodologia.agregarCondicionTaxativa(new CondicionTaxativa(new OperandoCondicion(OperacionAgregacion.Ultimo, new Antiguedad(), 1), OperacionRelacional.Menor, 10));
		Metodologia metodologia2 = new Metodologia("VAN");
		metodologia2.agregarCondicionPrioritaria(new CondicionPrioritaria(new OperandoCondicion(OperacionAgregacion.Ultimo, new Antiguedad(), 1), OperacionRelacional.Mayor));
		metodologia2.agregarCondicionTaxativa(new CondicionTaxativa(new OperandoCondicion(OperacionAgregacion.Ultimo, new Antiguedad(), 1), OperacionRelacional.Menor, 10));
		
		Long id = new RepositorioUsuarios().obtenerId("admin", "admin");
		Usuario usuario = new RepositorioUsuarios().obtenerPorId(id);
		
		try{
			usuario.agregarMetodologia(metodologia);
			usuario.agregarMetodologia(metodologia2);
		} catch(EntidadExistenteError e) {
			//Si ya existen las metodologías, no hago nada
		}
	}
}
