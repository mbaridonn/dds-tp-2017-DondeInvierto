package dominio.indicadores;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.uqbar.commons.utils.Observable;

import dominio.empresas.Empresa;
import dominio.metodologias.Cuantificador;
import excepciones.NoExisteCuentaError;

@Observable
@Entity
public class Indicador extends Cuantificador{
	
	private String nombre;
	
	private String equivalencia;
	
	@Transient
	private Expresion expresion;

	private Indicador(){}
	
	public Indicador(String nombre){
		this.nombre = nombre;
	}
	
	public int evaluarEn(Empresa empresa, Year anio){
		return expresion.evaluarEn(empresa,anio);
	}
	
	public boolean esAplicableA(Empresa empresa, Year anio){
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
	
	public void setEquivalencia(String equivalencia){
		this.equivalencia = equivalencia;
	}
	
	public String getEquivalencia() {
		return equivalencia;
	}
	
	public String getNombre() {
		return nombre;
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
