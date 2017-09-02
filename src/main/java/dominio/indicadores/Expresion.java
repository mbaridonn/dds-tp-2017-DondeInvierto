package dominio.indicadores;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import dominio.empresas.Empresa;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Expresion {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	public abstract int evaluarEn(Empresa empresa, String anio);

}
