package dominio;

import org.uqbar.commons.utils.Observable;

@Observable
public class Indicador extends Expresion{

	private String nombre; //No deberia ser solo el nombre? En lugar de la expresion completa.
	private Expresion expresion;

	public Indicador(String nombre){
		this.nombre = nombre;
	}
	
	@Override
	public int evaluarEn(Empresa empresa, String anio){
		if (expresion==null) return 0;//De prueba mientras no se creen expresiones, después borrar.
		return expresion.evaluarEn(empresa,anio);
	}
	
	public boolean seLlama(String nombre){
		return this.nombre.equals(nombre);
	}
	
	public void registrarseEn(ArchivoIndicadores archivo){
		archivo.escribirIndicador(nombre);
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
