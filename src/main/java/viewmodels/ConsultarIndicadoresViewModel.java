package viewmodels;

import java.util.ArrayList;

import org.uqbar.commons.utils.Dependencies;

import dominio.indicadores.ArchivoIndicadores;
import dominio.indicadores.Indicador;

public class ConsultarIndicadoresViewModel {
	
	public void leerArchivoIndicadores(){
		//Hay que ver bien cuándo se carga el archivo
		ArchivoIndicadores archivo = ArchivoIndicadores.getInstance();
		archivo.leerIndicadores();
		archivo.getIndicadores().forEach(ind -> archivo.cargarIndicador(ind));
	}
	
	@Dependencies({})
	public ArrayList<Indicador> getIndicadores() {
		return ArchivoIndicadores.getInstance().getIndicadores();
	}

}
