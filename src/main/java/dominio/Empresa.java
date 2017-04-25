package dominio;

public class Empresa {
	private String nombre;
	private Lista<Cuenta> cuentas;
	
	public Empresa(String nombre){
		this.nombre = nombre;
		cuentas = new Lista<Cuenta>();
	}
	
	public void cargarCuentas(){
		
		
	}
}
