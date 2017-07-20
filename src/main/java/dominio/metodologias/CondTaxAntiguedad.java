package dominio.metodologias;

import dominio.Empresa;

public class CondTaxAntiguedad implements CondicionTaxativa{
	private OperacionRelacional operacionRelacional;
	private int valor;
	
	public CondTaxAntiguedad(OperacionRelacional operacionRelacional, int valor) {
		this.operacionRelacional = operacionRelacional;
		this.valor = valor;
	}
	
	@Override
	public boolean laCumple(Empresa empresa) {
		return operacionRelacional.aplicarA(empresa.getAntiguedad(), valor);
	}
}
