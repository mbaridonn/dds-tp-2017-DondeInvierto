package dominio.empresas;

import java.util.ArrayList;

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
	}
	
	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}
	
	public Empresa findById(Long id) {
		return entityManager().find(Empresa.class, id);
	}

	public void agregar(Empresa empresa) {//LO AGREGO TAMBIÉN A LA LISTA?
		entityManager().persist(empresa);
	}
}
