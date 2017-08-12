package viewmodels;

import java.util.ArrayList;

import dominio.Empresa;

public class RepositorioEmpresas {
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
}
