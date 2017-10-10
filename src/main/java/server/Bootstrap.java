package server;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;
import dominio.empresas.RepositorioEmpresas;
import dominio.indicadores.RepositorioIndicadores;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

	public void init() {
		withTransaction(() -> {
			cargarEmpresasPredefinidas();
			cargarIndicadoresPredefinidos();
		});
	}

	private void cargarEmpresasPredefinidas() {
		List<Cuenta> cuentas = Arrays.asList(new Cuenta(Year.parse("2017"), "EBITDA", 10000),
				new Cuenta(Year.parse("2017"), "FDS ", 10000));
		Empresa empresa1 = new Empresa("Empresa1,", cuentas);
		Empresa empresa2 = new Empresa("Empresa2,", cuentas);
		Empresa empresa3 = new Empresa("Empresa3,", cuentas);
		new RepositorioEmpresas().agregarMultiplesEmpresas(Arrays.asList(empresa1, empresa2, empresa3));
	}

	private void cargarIndicadoresPredefinidos() {
		new RepositorioIndicadores().agregarMultiplesIndicadores(
				Arrays.asList(new String[] { "INGRESONETO = netooperacionescontinuas + netooperacionesdiscontinuas",
						"INDICADORDOS = cuentarara + fds", "INDICADORTRES = INGRESONETO * 10 + ebitda", "A = 5 / 3",
						"PRUEBA = ebitda + 5" }));
	}
}
