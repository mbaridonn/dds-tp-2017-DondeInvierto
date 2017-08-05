package dominio.metodologias;

import dominio.Empresa;

public class CondicionPrioritaria {
	private OperandoCondicion operandoCondicion;
	private OperacionRelacional operacionRelacional;
	
	public CondicionPrioritaria(OperandoCondicion operandoCondicion, OperacionRelacional operacionRelacional) {
		this.operandoCondicion = operandoCondicion;
		this.operacionRelacional = operacionRelacional;
	}
	
	public boolean esMejorQue(Empresa empresa1, Empresa empresa2){
		return operacionRelacional.aplicarA(operandoCondicion.valorPara(empresa1), operandoCondicion.valorPara(empresa2));
	}
	
}
