package dominio.indicadores;

import java.util.function.IntBinaryOperator;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class ExpresionOperacion implements Expresion{
	private Expresion operandoIzq;
	private Expresion operandoDer;
	private IntBinaryOperator operadorBinario;
	
	public ExpresionOperacion(Expresion operandoIzq, Expresion operandoDer, /*IntBinaryOperator*/ String operadorBinario){
		this.operandoIzq=operandoIzq;
		this.operandoDer=operandoDer;
		if(operadorBinario.equals("+"))this.operadorBinario=(x,y)->x + y;
		else if (operadorBinario.equals("-"))this.operadorBinario=(x,y)->x - y;
		else if (operadorBinario.equals("*"))this.operadorBinario=(x,y)->x * y;
		else if (operadorBinario.equals("/"))this.operadorBinario=(x,y)->x / y;
	}
	
	@Override
	public int evaluarEn(Empresa empresa, String anio) {//Chequear (y testear) como funciona el paso de operadores
		return operadorBinario.applyAsInt(operandoIzq.evaluarEn(empresa, anio), operandoDer.evaluarEn(empresa, anio));
	}
}
