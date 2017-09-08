package dominio.metodologias;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import dominio.empresas.Empresa;

@Entity
public class CondicionPrioritaria extends Condicion{
	@Id 
	@GeneratedValue
	private Long id;
	
	private CondicionPrioritaria() {} //Necesario para persistir la clase
	
	public CondicionPrioritaria(OperandoCondicion operandoCondicion, OperacionRelacional operacionRelacional) {
		this.operandoCondicion = operandoCondicion;
		this.operacionRelacional = operacionRelacional;
	}
	
	public boolean esMejorQue(Empresa empresa1, Empresa empresa2){
		return operacionRelacional.aplicarA(operandoCondicion.valorPara(empresa1), operandoCondicion.valorPara(empresa2));
	}
	
}
