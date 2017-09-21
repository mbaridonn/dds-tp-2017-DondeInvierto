package dominio.metodologias;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class CondicionTaxativa extends Condicion{
	
	private int valor;
	
	private CondicionTaxativa() {} //Necesario para persistir la clase
	
	public CondicionTaxativa(OperandoCondicion operando, OperacionRelacional operacionRelacional, int valor) {
		this.operando = operando;
		this.operacionRelacional = operacionRelacional;
		this.valor = valor;
	}
	
	public boolean laCumple(Empresa empresa){
		return operacionRelacional.aplicarA(operando.valorPara(empresa), valor);
	}

}
