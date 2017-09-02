package dominio.indicadores;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class ExpresionCuenta extends Expresion{
	
	private String cuenta;
	
	public ExpresionCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public int evaluarEn(Empresa empresa, String anio) {
		return empresa.getValorCuenta(cuenta,anio);
	}
}
