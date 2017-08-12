package dominio.metodologias;

import dominio.Empresa;
import excepciones.AntiguedadMenorACeroError;

public class Antiguedad implements EvaluableEnCondicion {
	public int evaluarEn(Empresa empresa, String anio){
		int antiguedad = Integer.parseInt(anio) - empresa.getAnioDeCreacion();
		if(antiguedad < 0){
			throw new AntiguedadMenorACeroError("La empresa " + empresa.getNombre() + " no existía en el año " + anio);
		}
		return antiguedad;
	}
}