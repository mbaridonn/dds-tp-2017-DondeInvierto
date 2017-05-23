package viewmodels;

import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import dominio.Indicador;

@Observable
public class CargarIndicadoresViewModel {
	private static ArrayList<Indicador> indicadores = new ArrayList<Indicador>();

	private String indicador;
	
	private static CargarIndicadoresViewModel singleton = new CargarIndicadoresViewModel();
	
	public static CargarIndicadoresViewModel getInstance(){
		return singleton;
	}

	public void cargarIndicador(){
		indicadores.add(new Indicador(indicador));
	}
	
	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	
	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}


}
