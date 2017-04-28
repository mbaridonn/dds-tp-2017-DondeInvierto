package dominio;

public class Empresa {
	private String nombre;
	private Lista<Cuenta> cuentas;
	
	public Empresa(String nombre,Lista<Cuenta> cuentas){
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public int cantidadDeCuentasQuePosee(){
		return cuentas.size();
	}
	
	public String getNombre(){
		return nombre;
	}

	public Lista<Cuenta> getCuentas() {
		return cuentas;
	}
}
