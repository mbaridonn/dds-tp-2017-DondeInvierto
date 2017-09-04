package dominio.indicadores.operadores_binarios;

import javax.persistence.Entity;

@Entity
public class Por extends IntBinaryOperator{
	
	public Por(){} //Necesario para persistir la clase
	
	@Override
	public int applyAsInt(int operandoIzq, int operandoDer) {
		return operandoIzq * operandoDer;
	}
}