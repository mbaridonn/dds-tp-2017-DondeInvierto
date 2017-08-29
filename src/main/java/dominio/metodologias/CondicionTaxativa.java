package dominio.metodologias;

import dominio.empresas.Empresa;

public class CondicionTaxativa extends Condicion{
	private int valor;
	
	public CondicionTaxativa(OperandoCondicion operandoCondicion, OperacionRelacional operacionRelacional, int valor) {
		this.operandoCondicion = operandoCondicion;
		this.operacionRelacional = operacionRelacional;
		this.valor = valor;
	}
	
	public boolean laCumple(Empresa empresa){
		return operacionRelacional.aplicarA(operandoCondicion.valorPara(empresa), valor);
	}

}
