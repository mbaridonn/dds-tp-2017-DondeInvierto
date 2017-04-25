package dominio;

public class Empresa {
	private String nombre;
	public Lista<Cuenta> cuentas;
	
	public Empresa(String nombre){
		this.nombre = nombre;
		cuentas = new Lista<Cuenta>();
	}
	
	public void cargarCuentas(){
		cuentas = LectorXLS.leer();
	}
}
