package dominio;

import org.uqbar.commons.utils.Observable;

@Observable
public class Cuenta {
	private String anio;
	private String tipoCuenta;
	private int valor;
	
	public Cuenta(String anio, String tipoCuenta, int valor){
		this.anio = anio;
		this.tipoCuenta = tipoCuenta;
		this.valor = valor;
	}

	public String getAnio() {
		return anio;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public int getValor() {
		return valor;
	}
	
	
}
