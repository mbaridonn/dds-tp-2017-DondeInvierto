package dominio.metodologias;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import dominio.empresas.Empresa;

@Entity
public class CondicionTaxativa extends Condicion{
	@Id 
	@GeneratedValue
	private Long id;
	
	private int valor;
	
	private CondicionTaxativa() {} //Necesario para persistir la clase
	
	public CondicionTaxativa(OperandoCondicion operandoCondicion, OperacionRelacional operacionRelacional, int valor) {
		this.operandoCondicion = operandoCondicion;
		this.operacionRelacional = operacionRelacional;
		this.valor = valor;
	}
	
	public boolean laCumple(Empresa empresa){
		return operacionRelacional.aplicarA(operandoCondicion.valorPara(empresa), valor);
	}

}
