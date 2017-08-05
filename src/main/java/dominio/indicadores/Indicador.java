package dominio.indicadores;

import org.uqbar.commons.utils.Observable;

import dominio.Empresa;
import dominio.metodologias.EvaluableEnCondicion;

@Observable
public class Indicador implements EvaluableEnCondicion{

	private String nombre;
	private String equivalencia; //Provisorio, estaria bueno que en realidad la clase Expresion la tenga.//Volar?
	private Expresion expresion;

	public Indicador(String nombre){
		this.nombre = nombre;
	}
	
	public int evaluarEn(Empresa empresa, String anio){
		return expresion.evaluarEn(empresa,anio);
	}
	
	public boolean esAplicableA(Empresa empresa, String anio){
		try{
			this.evaluarEn(empresa, anio);
			return true;
		} catch (RuntimeException e){ //Revisar que error catchea exactamente.
			return false;
		}
	}
	
	public boolean seLlama(String nombre){
		return this.nombre.equalsIgnoreCase(nombre);
	}
	
	public String formulaIndicador(){//Volar?
		return nombre + " = " + equivalencia;
	}
	
	public void registrarseEn(ArchivoIndicadores archivo){//Volar?
		archivo.escribirIndicador(this.formulaIndicador());
	}
	
	public void sobreEscribirseEn(ArchivoIndicadores archivo){//Volar?
		archivo.borrarIndicador(this.nombre);
		this.registrarseEn(archivo);
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public void setEquivalencia(String equivalencia){//Volar?
		this.equivalencia = equivalencia;
	}
	
	public Expresion getExpresion() {
		return expresion;
	}
	
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
	
}
