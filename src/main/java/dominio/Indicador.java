package dominio;

import org.uqbar.commons.utils.Observable;

@Observable
public class Indicador {

	private String indicador;

	public Indicador(String indicador){
		this.indicador = indicador;
	}
	
	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
}
