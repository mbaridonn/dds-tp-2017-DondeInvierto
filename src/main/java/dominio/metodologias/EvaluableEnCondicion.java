package dominio.metodologias;

import dominio.empresas.Empresa;

public interface EvaluableEnCondicion {

	public abstract int evaluarEn(Empresa empresa, String anio);
	
}
