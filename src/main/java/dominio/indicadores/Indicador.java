package dominio.indicadores;

import java.time.Year;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.uqbar.commons.utils.Observable;

import dominio.empresas.Empresa;
import dominio.metodologias.Cuantificador;
import dominio.parser.ParserIndicadores;
import excepciones.NoExisteCuentaError;

@Observable
@Entity
public class Indicador extends Cuantificador{
	
	private String nombre;
	
	private String equivalencia;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<IndicadorPrecalculado> resultados;
	
	@Transient
	private Expresion expresion;

	private Indicador(){}
	
	public Indicador(String nombre){
		this.nombre = nombre;
	}
	
	public int evaluarEn(Empresa empresa, Year anio){
		return resultados.stream().filter(resultados -> resultados.esDe(empresa, anio)).findFirst().orElse(precalcularIndicador(empresa, anio)).getValor();
	}
	
	private IndicadorPrecalculado precalcularIndicador(Empresa empresa, Year anio){
		if(expresion == null){
			this.inicializarExpresion();
		}
		int resultado = expresion.evaluarEn(empresa,anio);
		IndicadorPrecalculado ind = new IndicadorPrecalculado(empresa, anio, resultado);
		resultados.add(ind);
		return ind; 
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
	
	private void inicializarExpresion(){
		Indicador indicador = ParserIndicadores.parse(this.getEquivalencia());
		expresion = indicador.getExpresion();
	}
	
	private Expresion getExpresion() {
		return expresion;
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
