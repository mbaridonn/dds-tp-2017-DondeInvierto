package dominio.metodologias;

import dominio.Empresa;

public interface EvaluableEnCondicion {

	public abstract int evaluarEn(Empresa empresa, String anio);
	
}
