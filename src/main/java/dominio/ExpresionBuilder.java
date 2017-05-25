package dominio;

import java.util.function.IntBinaryOperator;

public class ExpresionBuilder {//Intento de builder. Puede estar mal
	
	private Expresion expresion;
	
	public Expresion build(){
		//Se podrían agregar validaciones
		return expresion;
	}
	
	public void agregarValor(int valor){
		expresion = new ExpresionValor(valor);
	}
	
	public void agregarCuenta(String cuenta){
		expresion = new ExpresionCuenta(cuenta);
	}
	
	public void agregarIndicador(Indicador indicador){//Recibe un indicador YA CREADO (el parser lo tendría que buscar en la lista de indicadores a partir del nombre)
		expresion = indicador;
	}
	
	public void agregarOperacion(Expresion operandoDer, IntBinaryOperator operadorBinario){//La expresion operandoDer habría que crearla con otro Builder
		expresion = new ExpresionOperacion(expresion, operandoDer, operadorBinario);
	}
	
}
