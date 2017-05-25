package dominio;

import org.uqbar.commons.utils.Observable;

@Observable
public class Indicador extends Expresion{

	private String indicador;
	//private Expresion indicador; (expresion podria ser un mejor nombre)

	public Indicador(String indicador){
		this.indicador = indicador;
	}
	
	@Override
	public int evaluarEn(Empresa empresa, String anio){
		return 0; //expresion.evaluarEn(empresa,anio); !!!
	}
	
	public String getIndicador() {
		return indicador;
	}

	public void setIndicador(String indicador) {
		this.indicador = indicador;
	}
}
