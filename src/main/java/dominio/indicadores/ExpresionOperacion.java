package dominio.indicadores;

import java.util.function.IntBinaryOperator;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import dominio.empresas.Empresa;

//@Entity
public class ExpresionOperacion extends Expresion{
//	@OneToOne
	private Expresion operandoIzq;
//	@OneToOne
	private Expresion operandoDer;
//	CONVENDRÁ USAR UN CONVERTER O CREAR OBJETOS MÁS/MENOS/POR/DIVIDIDO??
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
