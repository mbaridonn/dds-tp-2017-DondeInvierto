package dominio.empresas;

import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioEmpresas implements WithGlobalEntityManager{
	private ArrayList<Empresa> empresas = new ArrayList<Empresa>();
	
	private static RepositorioEmpresas singleton = new RepositorioEmpresas();
	
	private RepositorioEmpresas(){}
	
	public static RepositorioEmpresas getInstance(){
		return singleton;
	}
	
	public void setEmpresas(ArrayList<Empresa> empresas) {
		this.empresas = empresas;
		this.agregarEmpresas(empresas);//LO AGREGO TAMBIÉN A LA LISTA EN CUANTO LO CARGO DEL ARCHIVO? QUÉ PASA CON LOS REPETIDOS?
	}
	
	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}
	
	public void getEmpresasDeBD() {
		EntityManager entityManager = this.entityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		ArrayList<Empresa> empleadosEnBD = (ArrayList<Empresa>) entityManager.createQuery("SELECT e FROM Empresa e").getResultList();
		//NO ESTÁ BUENO TENER QUE ESCRIBIR LA CONSULTA, HAY ALGUNA OTRA FORMA PARA HACERLO??
		//TRAE SOLO LAS EMPRESAS, NO LAS CUENTAS!!
		tx.commit();
		this.setEmpresas(empleadosEnBD);
	}
	
	public Empresa findById(Long id) {
		return this.entityManager().find(Empresa.class, id);
	}

	public void agregarEmpresas(ArrayList<Empresa> empresas) {
		EntityManager entityManager = this.entityManager();
		EntityTransaction tx = entityManager.getTransaction();
		tx.begin();
		empresas.forEach(emp -> entityManager.persist(emp));
		tx.commit();
	}
}
