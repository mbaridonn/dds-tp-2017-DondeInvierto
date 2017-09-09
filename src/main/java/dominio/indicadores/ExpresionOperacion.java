package dominio.indicadores;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import dominio.empresas.Empresa;

@Entity
public class ExpresionOperacion extends Expresion{
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)//Cuando necesite la expresión, quiero que me la traiga completa de una
	private Expresion operandoIzq;
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Expresion operandoDer;
	@Enumerated
	private OperacionAritmetica operadorAritmetico;
	
	private ExpresionOperacion(){} //Necesario para persistir la clase
	
	public ExpresionOperacion(Expresion operandoIzq, Expresion operandoDer, String operadorBinario){
		this.operandoIzq=operandoIzq;
		this.operandoDer=operandoDer;
		if(operadorBinario.equals("+")) this.operadorAritmetico = OperacionAritmetica.Mas;
		else if (operadorBinario.equals("-")) this.operadorAritmetico = OperacionAritmetica.Menos;
		else if (operadorBinario.equals("*")) this.operadorAritmetico = OperacionAritmetica.Por;
		else if (operadorBinario.equals("/")) this.operadorAritmetico = OperacionAritmetica.Dividido;
	}
	
	@Override
	public int evaluarEn(Empresa empresa, String anio) {
		return operadorAritmetico.applyAsInt(operandoIzq.evaluarEn(empresa, anio), operandoDer.evaluarEn(empresa, anio));
	}
}
