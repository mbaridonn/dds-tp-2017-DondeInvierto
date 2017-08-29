package dominio.indicadores;

import dominio.empresas.Empresa;

public interface Expresion {
	
	public abstract int evaluarEn(Empresa empresa, String anio);

}
