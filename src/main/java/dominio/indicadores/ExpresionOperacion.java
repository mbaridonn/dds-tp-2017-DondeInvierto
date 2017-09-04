package dominio.indicadores;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import dominio.empresas.Empresa;
import dominio.indicadores.operadores_binarios.*;

@Entity
public class ExpresionOperacion extends Expresion{
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)//Cuando necesite expresión, quiero que me la traiga completa de una
	private Expresion operandoIzq;
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Expresion operandoDer;
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)//TIENE SENTIDO PERSISTIRLOS? LOS OPERADORES NO TIENEN ESTADO (!!!)
	//SE ESTÁN PERSISTIENDO MÚLTIPLES INSTANCIAS DE CADA OPERANDO (!!!!!!!!!)
	private IntBinaryOperator operadorBinario;
	
	private ExpresionOperacion(){} //Necesario para persistir la clase
	
	public ExpresionOperacion(Expresion operandoIzq, Expresion operandoDer, /*IntBinaryOperator*/ String operadorBinario){
		this.operandoIzq=operandoIzq;
		this.operandoDer=operandoDer;
		if(operadorBinario.equals("+")) this.operadorBinario = new Mas();
		else if (operadorBinario.equals("-")) this.operadorBinario = new Menos();
		else if (operadorBinario.equals("*")) this.operadorBinario = new Por();
		else if (operadorBinario.equals("/")) this.operadorBinario = new Dividido();
	}
	
	@Override
	public int evaluarEn(Empresa empresa, String anio) {
		return operadorBinario.applyAsInt(operandoIzq.evaluarEn(empresa, anio), operandoDer.evaluarEn(empresa, anio));
	}
}
