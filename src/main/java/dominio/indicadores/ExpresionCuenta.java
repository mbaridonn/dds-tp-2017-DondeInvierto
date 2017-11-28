package dominio.indicadores;

import java.time.Year;

import dominio.empresas.Empresa;

public class ExpresionCuenta implements Expresion{
	
	private String cuenta;
	
	public ExpresionCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	public int evaluarEn(Empresa empresa, Year anio) {
		return empresa.getValorCuenta(cuenta,anio);
	}
	
}
