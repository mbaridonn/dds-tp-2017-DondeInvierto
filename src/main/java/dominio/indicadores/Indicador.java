package dominio.indicadores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.uqbar.commons.utils.Observable;

import dominio.empresas.Empresa;
import dominio.metodologias.EvaluableEnCondicion;
import excepciones.NoExisteCuentaError;

@Observable
@Entity
public class Indicador implements EvaluableEnCondicion{

	@Id 
	@GeneratedValue
	private Long id;
	
	private String nombre;
	@Transient //ES NECESARIO QUE SIGA ESTANDO?? YA NO VAMOS A TRABAJAR CON ARCHIVOS DE TEXTO
	private String equivalencia; //Provisorio, estaria bueno que en realidad la clase Expresion la tenga.//Volar?
	@OneToOne
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
		} catch (NoExisteCuentaError e){
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
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
