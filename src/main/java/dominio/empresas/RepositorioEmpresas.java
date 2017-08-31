package dominio.empresas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioEmpresas implements WithGlobalEntityManager{
	private List<Empresa> empresas = new ArrayList<Empresa>();//TIENE SENTIDO SEGUIR TENIENDO UNA COPIA LOCAL??
	
	private static RepositorioEmpresas singleton = new RepositorioEmpresas();
	
	private RepositorioEmpresas(){}
	
	public static RepositorioEmpresas getInstance(){
		return singleton;
	}
	
	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
		this.agregarEmpresas(empresas);//LO AGREGO TAMBIÉN A LA LISTA EN CUANTO LO CARGO DEL ARCHIVO? QUÉ PASA CON LOS REPETIDOS??
	}
	
	public List<Empresa> getEmpresas() {
		return empresas;
	}
	
	public void getEmpresasDeBD() {
		List<Empresa> empleadosEnBD = (List<Empresa>) this.entityManager().createQuery("SELECT e FROM Empresa e").getResultList();
		//NO ESTÁ BUENO TENER QUE ESCRIBIR LA CONSULTA, HAY ALGUNA OTRA FORMA PARA HACERLO??
		this.setEmpresas(empleadosEnBD);
	}
	
	public Empresa findById(Long id) {
		return this.entityManager().find(Empresa.class, id);
	}

	public void agregarEmpresas(List<Empresa> empresas) {
		EntityManager entityManager = this.entityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		empresas.forEach(emp -> entityManager.persist(emp));
		tx.commit();
	}
}
