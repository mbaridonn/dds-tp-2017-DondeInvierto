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
	
	public boolean esDeTipo(String tipo){
		return this.tipoCuenta.equals(tipo);
	}
	
	public boolean esDeAnio(String anio){
		return this.anio.equals(anio);
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
