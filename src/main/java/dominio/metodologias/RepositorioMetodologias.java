package dominio.metodologias;

import java.util.ArrayList;

public class RepositorioMetodologias {
	private ArrayList<Metodologia> metodologias = new ArrayList<Metodologia>();
	
	private static RepositorioMetodologias singleton = new RepositorioMetodologias();
	
	private RepositorioMetodologias(){}
	
	public static RepositorioMetodologias getInstance(){
		return singleton;
	}
	
	public void agregarMetodologia(Metodologia metodologia){
		metodologias.add(metodologia);
	}
	
	public ArrayList<Metodologia> getMetodologias() {
		return metodologias;
	}
	
}
