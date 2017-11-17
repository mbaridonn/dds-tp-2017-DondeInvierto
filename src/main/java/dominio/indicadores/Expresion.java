package dominio.indicadores;

import java.time.Year;
import java.util.List;

import dominio.empresas.Empresa;

public interface Expresion {
	
	public abstract int evaluarEn(Empresa empresa, Year anio);

	public abstract List<String> dependencias();

}
