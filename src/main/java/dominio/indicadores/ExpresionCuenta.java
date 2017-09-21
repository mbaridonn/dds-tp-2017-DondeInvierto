package dominio.indicadores;

import java.time.Year;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class ExpresionCuenta extends Expresion{
	
	private String cuenta;
	
	private ExpresionCuenta(){} //Necesario para persistir la clase
	
	public ExpresionCuenta(String cuenta) {
		this.cuenta = cuenta;
	}

	@Override
	public int evaluarEn(Empresa empresa, Year anio) {
		return empresa.getValorCuenta(cuenta,anio);
	}
}
