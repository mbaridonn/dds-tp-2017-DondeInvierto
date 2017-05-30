package viewmodels;

import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import dominio.ArchivoIndicadores;
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
	
	public void guardarIndicador(){
		ArchivoIndicadores archivo = new ArchivoIndicadores("src/main/resources/Indicadores.txt");
		archivo.escribirIndicador(indicador);
	}
	
	public void leerArchivoIndicadores(){
		//Hay que ver bien cuándo se carga el archivo
		ArchivoIndicadores archivo = new ArchivoIndicadores("src/main/resources/Indicadores.txt");
		archivo.leerIndicadores();
		archivo.getIndicadores().forEach(ind -> indicadores.add(ind));
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
