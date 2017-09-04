package dominio.indicadores.operadores_binarios;

import javax.persistence.Entity;

@Entity
public class Mas extends IntBinaryOperator{
	
	public Mas(){} //Necesario para persistir la clase
	
	@Override
	public int applyAsInt(int operandoIzq, int operandoDer) {
		return operandoIzq + operandoDer;
	}
}
