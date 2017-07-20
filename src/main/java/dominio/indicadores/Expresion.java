package dominio.indicadores;

import dominio.Empresa;

public interface Expresion {
	
	public abstract int evaluarEn(Empresa empresa, String anio);

}
