package viewmodels;

import org.uqbar.commons.utils.Observable;

import dominio.indicadores.ArchivoIndicadores;
import dominio.parser.ParserIndicadores;
import excepciones.ParserError;

@Observable
public class CargarIndicadoresViewModel {

	private String indicador;
	private String resultadoOperacion;

	public void cargarIndicador() {
		try {
			ArchivoIndicadores.getInstance().cargarIndicador(ParserIndicadores.parse(indicador));
			resultadoOperacion = "Indicador cargado";
		} catch (ParserError e) {
			resultadoOperacion = "Sintaxis incorrecta";
		}
	}

	public void guardarIndicador() {
		try {
			ParserIndicadores.parse(indicador);//Se usa sólo para validar. Si lanza excepción, va al catch
			ArchivoIndicadores.getInstance().escribirIndicador(indicador);
			resultadoOperacion = "Indicador guardado";
		} catch (ParserError e) {
			resultadoOperacion = "Sintaxis incorrecta";
		}
	}

	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
	
	public String getResultadoOperacion() {
		return resultadoOperacion;
	}
	
	public void setResultadoOperacion(String resultadoOperacion) {
		this.resultadoOperacion = resultadoOperacion;
	}

}