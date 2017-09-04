package dominio.indicadores.operadores_binarios;

import javax.persistence.Entity;

@Entity
public class Dividido extends IntBinaryOperator{
	
	public Dividido(){} //Necesario para persistir la clase
	
	@Override
	public int applyAsInt(int operandoIzq, int operandoDer) {
		return operandoIzq / operandoDer;
	}
}