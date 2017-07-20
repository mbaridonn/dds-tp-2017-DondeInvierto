package dominio.metodologias;

import dominio.Empresa;

public class CondPriAntiguedad implements CondicionPrioritaria{
	private OperacionRelacional operacionRelacional;
	
	public CondPriAntiguedad(OperacionRelacional operacionRelacional) {
		this.operacionRelacional = operacionRelacional;
	}

	@Override
	public boolean esMejorQue(Empresa empresa1, Empresa empresa2) {
		return operacionRelacional.aplicarA(empresa1.getAntiguedad(), empresa2.getAntiguedad());
	}
}
