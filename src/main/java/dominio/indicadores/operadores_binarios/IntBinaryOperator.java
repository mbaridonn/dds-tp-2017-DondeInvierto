package dominio.indicadores.operadores_binarios;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class IntBinaryOperator {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	public abstract int applyAsInt(int operandoIzq, int operandoDer);
}
