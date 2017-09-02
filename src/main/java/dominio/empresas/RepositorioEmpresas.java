package dominio.empresas;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioEmpresas implements WithGlobalEntityManager{
	
	public List<Empresa> getEmpresas() {
		return (List<Empresa>) this.entityManager().createQuery("FROM Empresa").getResultList();
	}
	
	public boolean hayEmpresas(){
		return this.getEmpresas().size() > 0;
	}
	
	/*public Empresa findById(Long id) { NECESARIO?
		return this.entityManager().find(Empresa.class, id);
	}*/

	public void agregarEmpresas(List<Empresa> empresas) {
		Stream<Empresa> empresasNuevas = empresas.stream().filter(emp -> !this.existeEmpresa(emp));//Sólo persisto las empresas que no están ya en la BD
		EntityManager entityManager = this.entityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		empresasNuevas.forEach(empresa -> this.persistirSiNoExiste(empresa));
		tx.commit();
	}
	
	private void persistirSiNoExiste(Empresa empresa) {
		if(!this.entityManager().contains(empresa)) {
			this.entityManager().persist(empresa);
		}
	}

	private boolean existeEmpresa(Empresa empresa){ //PROBLEMA GENERAL: CÓMO EVITO DUPLICADOS?? (!!!)
		return this.getEmpresas().contains(empresa); //NO FUNCIONA, LAS EMPRESAS TIENEN DISTINTA IDENTIDAD !!
	}
}
