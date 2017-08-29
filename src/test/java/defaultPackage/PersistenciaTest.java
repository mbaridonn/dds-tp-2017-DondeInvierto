package defaultPackage;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.junit.Test;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.test.AbstractPersistenceTest;

public class PersistenciaTest extends AbstractPersistenceTest implements WithGlobalEntityManager {

	@Test
	public void contextUp() {
		assertNotNull(entityManager());
	}

	@Test
	public void contextUpWithTransaction() throws Exception {
		withTransaction(() -> {});
	}
	
	/*@Test
	public void sePuedePersistirUnaEmpresa(){
		EntityManager entityManager = this.entityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		//CREAR EMPRESA CON CUENTAS Y PERSISTIRLA
		tx.commit();
	}*/
}
