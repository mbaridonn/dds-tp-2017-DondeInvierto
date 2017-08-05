package dominio.metodologias;

import dominio.Empresa;

public class Antiguedad implements EvaluableEnCondicion {
	public int evaluarEn(Empresa empresa, String anio){
		int antiguedad = Integer.parseInt(anio) - empresa.getAnioDeCreacion();
		if(antiguedad < 0){
			throw new AntiguedadMenorACeroError("La empresa " + empresa.getNombre() + " no existía en el año " + anio);
		}
		return antiguedad;
	}
}

class AntiguedadMenorACeroError extends RuntimeException{AntiguedadMenorACeroError(String e){super(e);}}