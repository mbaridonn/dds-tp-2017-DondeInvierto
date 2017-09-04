package dominio.indicadores.operadores_binarios;

import javax.persistence.Entity;

@Entity
public class Menos extends IntBinaryOperator{
	
	public Menos(){} //Necesario para persistir la clase
	
	@Override
	public int applyAsInt(int operandoIzq, int operandoDer) {
		return operandoIzq - operandoDer;
	}
}