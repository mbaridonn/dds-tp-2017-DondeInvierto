package dominio;

import java.util.function.IntBinaryOperator;

public class ExpresionOperacion extends Expresion{
	private Expresion operandoIzq;
	private Expresion operandoDer;
	private IntBinaryOperator operadorBinario;
	
	public ExpresionOperacion(Expresion operandoIzq, Expresion operandoDer, IntBinaryOperator operadorBinario){
		this.operandoIzq=operandoIzq;
		this.operandoDer=operandoDer;
		this.operadorBinario=operadorBinario;
	}
	
	@Override
	public int evaluarEn(Empresa empresa, String anio) {//Chequear (y testear) como funciona el paso de operadores
		return operadorBinario.applyAsInt(operandoIzq.evaluarEn(empresa, anio), operandoDer.evaluarEn(empresa, anio));
	}
}
