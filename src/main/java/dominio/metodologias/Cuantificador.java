package dominio.metodologias;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import dominio.empresas.Empresa;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Cuantificador {

	@Id 
	@GeneratedValue
	private Long id;
	
	public abstract int evaluarEn(Empresa empresa, Year anio);
	
}
