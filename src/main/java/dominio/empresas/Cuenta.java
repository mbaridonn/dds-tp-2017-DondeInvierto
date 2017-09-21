package dominio.empresas;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.uqbar.commons.utils.Observable;

@Observable
@Entity
public class Cuenta {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	private Year anio;
	private String tipoCuenta;
	private int valor;
	
	private Cuenta(){} //Necesario para persistir la clase
	
	public Cuenta(Year anio, String tipoCuenta, int valor){
		this.anio = anio;
		this.tipoCuenta = tipoCuenta;
		this.valor = valor;
	}
	
	public boolean esDeTipo(String tipo){
		return this.tipoCuenta.equalsIgnoreCase(tipo);
	}
	
	public boolean esDeAnio(Year anio){
		return this.anio.equals(anio);
	}

	public Year getAnio() {
		return anio;
	}

	public String getTipoCuenta() {
		return tipoCuenta;
	}

	public int getValor() {
		return valor;
	}

	public void mostrarDatos() { //despues se borra
		System.out.println(anio + "\t" + tipoCuenta + "\t" + valor);
	}
	
}
