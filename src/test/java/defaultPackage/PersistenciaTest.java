package defaultPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;
import dominio.empresas.RepositorioEmpresas;

public class PersistenciaTest /*extends AbstractPersistenceTest implements WithGlobalEntityManager*/ {
	
//	EntityManager entityManager;
//	EntityTransaction tx;
	
	@Before
	public void startTransaction() {
	//	entityManager = this.entityManager();
	//	tx = entityManager.getTransaction();
	//	tx.begin();
	}
	
	@After
	public void rollbackTransaction() {
	//	tx.rollback();
	}
	
	@Test
	public void alAgregarDosEmpresasAlRepositorioEmpresasEstasSePersistenCorrectamente(){//PROBLEMA: EL TEST ESTÁ TENIENDO EFECTO EN LA BD (!!!)
		RepositorioEmpresas repoEmpresas = new RepositorioEmpresas();
		List<Cuenta> listaCuentas1 = new ArrayList<Cuenta> (Arrays.asList(new Cuenta("2015","EBITDA",2000), new Cuenta("2014","FDS",3000)));
		List<Cuenta> listaCuentas2 = new ArrayList<Cuenta> (Arrays.asList(new Cuenta("2013","EBITDA",6000), new Cuenta("2010","FDS",8000)));
		Empresa empresa1 = new Empresa("empresa1",listaCuentas1);
		Empresa empresa2 = new Empresa("empresa2",listaCuentas2);
		List<Empresa> listaEmpresas = new ArrayList<Empresa>(Arrays.asList(empresa1,empresa2));
		
		repoEmpresas.agregarEmpresas(listaEmpresas);		
		
		assertTrue(repoEmpresas.getEmpresas().containsAll(listaEmpresas));
	}
}
