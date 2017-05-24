package dominio;

import org.uqbar.commons.utils.Observable;

@Observable
public class Indicador {

	private String indicador;

	public Indicador(String indicador){
		this.indicador = indicador;
	}
	
	/*public int evaluarEn(Empresa empresa,int anio){
		return 0;
	}*/
	
	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
}
