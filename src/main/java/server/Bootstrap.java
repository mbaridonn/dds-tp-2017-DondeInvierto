package server;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;

import org.uqbarproject.jpa.java8.extras.EntityManagerOps;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;

public class Bootstrap implements WithGlobalEntityManager, EntityManagerOps, TransactionalOps {

	public void init() {
		withTransaction(() -> {
			//Descomentar para usar Empresas de prueba y no usar la BD
			/*Cuenta unaCuenta = new Cuenta(Year.parse("2017"), "EBITDA", 10000);
			Cuenta otraCuenta = new Cuenta(Year.parse("2017"), "FDS ", 10000);
			List<Cuenta> cuentas = new ArrayList<Cuenta>();
			cuentas.add(unaCuenta);
			cuentas.add(otraCuenta);

			Empresa empresa = new Empresa("EmpresaPrueba1,", cuentas);
			persist(empresa);

			Empresa empresa2 = new Empresa("EmpresaPrueba2,", cuentas);
			persist(empresa2);

			Empresa empresa3 = new Empresa("EmpresaPrueba3,", cuentas);
			persist(empresa3);*/
		});
	}
}
