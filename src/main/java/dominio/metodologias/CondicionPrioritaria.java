package dominio.metodologias;

import dominio.empresas.Empresa;

public class CondicionPrioritaria extends Condicion{
	
	public CondicionPrioritaria(OperandoCondicion operandoCondicion, OperacionRelacional operacionRelacional) {
		this.operandoCondicion = operandoCondicion;
		this.operacionRelacional = operacionRelacional;
	}
	
	public boolean esMejorQue(Empresa empresa1, Empresa empresa2){
		return operacionRelacional.aplicarA(operandoCondicion.valorPara(empresa1), operandoCondicion.valorPara(empresa2));
	}
	
}
