package dominio;

import java.util.function.IntBinaryOperator;

import javaCC.ParserIndicadores;

public class ExpresionBuilder {//Intento de builder. Puede estar mal
	
	private Expresion expresion;
	
	public Expresion build(){
		//Se podrían agregar validaciones
		return expresion;
	}
	
	public void agregarExpresion(Expresion expresion){
		this.expresion = expresion;
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
	
	public void agregarOperacion(Expresion operandoDer, /*IntBinaryOperator*/String operadorBinario){//La expresion operandoDer habría que crearla con otro Builder
		expresion = new ExpresionOperacion(expresion, operandoDer, operadorBinario);
	}
	
	public static void main(String[] args) {
		System.out.println(ParserIndicadores.parse("a=5+8").evaluarEn(null, null));
	}
	
}
