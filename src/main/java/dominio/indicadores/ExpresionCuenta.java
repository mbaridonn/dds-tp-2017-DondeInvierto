package dominio.indicadores;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

import dominio.empresas.Empresa;

public class ExpresionCuenta implements Expresion{
	
	private String cuenta;
	
	public ExpresionCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public int evaluarEn(Empresa empresa, Year anio) {
		return empresa.getValorCuenta(cuenta,anio);
	}
	
	@Override
	public List<String> dependencias() {
		return Arrays.asList(cuenta);
	}
	
}
