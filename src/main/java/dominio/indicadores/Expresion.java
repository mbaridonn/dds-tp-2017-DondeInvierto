package dominio.indicadores;

import java.time.Year;

import dominio.empresas.Empresa;

public interface Expresion {
	
	public abstract int evaluarEn(Empresa empresa, Year anio);

}
