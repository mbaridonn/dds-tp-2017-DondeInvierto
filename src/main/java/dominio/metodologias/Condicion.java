package dominio.metodologias;

public abstract class Condicion {
	protected OperandoCondicion operandoCondicion;
	protected OperacionRelacional operacionRelacional;
	
	public OperandoCondicion getOperandoCondicion() {
		return operandoCondicion;
	}
}
