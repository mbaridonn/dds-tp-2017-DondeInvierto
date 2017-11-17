package dominio.indicadores;

import java.time.Year;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import dominio.empresas.Empresa;

public class ExpresionOperacion implements Expresion{
	private Expresion operandoIzq;
	private Expresion operandoDer;
	private OperacionAritmetica operadorAritmetico;
	
	public ExpresionOperacion(Expresion operandoIzq, Expresion operandoDer, String operadorBinario){
		this.operandoIzq=operandoIzq;
		this.operandoDer=operandoDer;
		if(operadorBinario.equals("+")) this.operadorAritmetico = OperacionAritmetica.Mas;
		else if (operadorBinario.equals("-")) this.operadorAritmetico = OperacionAritmetica.Menos;
		else if (operadorBinario.equals("*")) this.operadorAritmetico = OperacionAritmetica.Por;
		else if (operadorBinario.equals("/")) this.operadorAritmetico = OperacionAritmetica.Dividido;
	}
	
	public int evaluarEn(Empresa empresa, Year anio) {
		return operadorAritmetico.applyAsInt(operandoIzq.evaluarEn(empresa, anio), operandoDer.evaluarEn(empresa, anio));
	}
	
	@Override
	public List<String> dependencias() {
		return Stream.of(operandoDer.dependencias(),operandoDer.dependencias()).flatMap(Collection::stream).collect(Collectors.toList());
	}
	
}
