package dominio.metodologias;

import javax.persistence.Entity;

import dominio.empresas.Empresa;
import excepciones.AntiguedadMenorACeroError;

@Entity
public class Antiguedad extends EvaluableEnCondicion {

	public Antiguedad() {
	} // PROBLEMA: SE VAN A ESTAR CREANDO MÚLTIPLES INSTANCIAS DE ANTIGUEDAD, CUANDO
		// UNA SERÍA SUFICIENTE (!!!)

	@Override
	public int evaluarEn(Empresa empresa, String anio) {
		int antiguedad = Integer.parseInt(anio) - empresa.getAnioDeCreacion();
		if (antiguedad < 0) {
			throw new AntiguedadMenorACeroError("La empresa " + empresa.getNombre() + " no existía en el año " + anio);
		}
		return antiguedad;
	}
}