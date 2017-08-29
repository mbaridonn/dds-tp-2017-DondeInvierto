package dominio.indicadores;

import dominio.empresas.Empresa;

public class ExpresionCuenta implements Expresion{
	
	private String cuenta;
	
	public ExpresionCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public int evaluarEn(Empresa empresa, String anio) {
		return empresa.getValorCuenta(cuenta,anio);
	}
}
