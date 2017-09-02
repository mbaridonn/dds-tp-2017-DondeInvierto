package defaultPackage;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class PersistenciaTest extends AbstractPersistenceTest implements WithGlobalEntityManager {
	
	EntityManager entityManager;
	EntityTransaction tx;
	
	@Before
	public void startTransaction() {
		entityManager = this.entityManager();
		tx = entityManager.getTransaction();
		tx.begin(); 
	}
	
	@After
	public void rollbackTransaction() {
		tx.rollback();
	}
	
	@Test
	public void alAgregarDosEmpresasAlRepositorioEmpresasEstasSePersistenCorrectamente(){
		//CREAR EMPRESAS CON CUENTAS Y PERSISTIRLAS
	}
}
