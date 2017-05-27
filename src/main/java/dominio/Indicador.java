package dominio;

import org.uqbar.commons.utils.Observable;

@Observable
public class Indicador extends Expresion{

	private String nombre;
	private Expresion expresion;

	public Indicador(String nombre){
		this.nombre = nombre;
	}
	
	@Override
	public int evaluarEn(Empresa empresa, String anio){
		if (expresion==null) return 0;//De prueba mientras no se creen expresiones, después borrar.
		return expresion.evaluarEn(empresa,anio);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Expresion getExpresion() {
		return expresion;
	}
	
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
}
