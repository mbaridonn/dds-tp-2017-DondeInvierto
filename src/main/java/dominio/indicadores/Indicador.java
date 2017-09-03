package dominio.indicadores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
	
	//ES NECESARIO QUE SIGA ESTANDO?? YA NO VAMOS A TRABAJAR CON ARCHIVOS DE TEXTO
	private String equivalencia; //Provisorio, estaria bueno que en realidad la clase Expresion la tenga.//Volar?
	
	@OneToOne//CONVENDRÁ EMBEBER LA EXPRESIÓN?? (!!!)
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
	
	public boolean seLlama(String nombre){//VER DONDE SE USA (PROBABLEMENTE SE ESTÁ REPITIENDO LÓGICA CON EL EQUALS) (!!!)
		return this.nombre.equals/*IgnoreCase*/(nombre);
	}
	
	public String formulaIndicador(){//Volar?
		return nombre + " = " + equivalencia;
	}
	
	public void setEquivalencia(String equivalencia){//Volar?
		this.equivalencia = equivalencia;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Expresion getExpresion() {
		return expresion;
	}
	
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
	
	public boolean equals(Object otroObjeto) {
	    return (otroObjeto instanceof Indicador) && this.seLlama(((Indicador) otroObjeto).getNombre());
	}
	
	public int hashCode() {
		return nombre.hashCode();
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	
}
