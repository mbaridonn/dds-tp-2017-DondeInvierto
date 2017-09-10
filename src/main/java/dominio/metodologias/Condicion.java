package dominio.metodologias;

import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;

@MappedSuperclass
public abstract class Condicion {
	@Id 
	@GeneratedValue
	private Long id;
	
	@OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	protected OperandoCondicion operandoCondicion;
	@Enumerated
	protected OperacionRelacional operacionRelacional;
	
	public OperandoCondicion getOperandoCondicion() {
		return operandoCondicion;
	}
}
