package viewmodels;

import java.util.ArrayList;

import dominio.indicadores.Indicador;

public class CargarDatosMetodologiaViewModel {
	private ArrayList<Indicador> indicadores;
	
	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}

	private static CargarDatosMetodologiaViewModel singleton = new CargarDatosMetodologiaViewModel();
	
	public static CargarDatosMetodologiaViewModel getInstance() {
		return singleton;
	}

}
