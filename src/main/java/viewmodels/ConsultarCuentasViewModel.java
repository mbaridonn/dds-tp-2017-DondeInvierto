package viewmodels;
import dominio.*;
import org.uqbar.commons.utils.Observable;

@Observable
public class ConsultarCuentasViewModel {
	private String nombreEmpresa = "h";
	private String nombrePrimeraCuentaPrimeraEmpresa = "o";
	private Lista<Empresa> empresas;
	
	private static ConsultarCuentasViewModel singleton = new ConsultarCuentasViewModel();
	
	public static ConsultarCuentasViewModel getInstance(){
		return singleton;
	}
	
	public void obtenerCuentasCargadas(Lista<Empresa> empresas){
		this.reiniciarValores();
		this.empresas = empresas;
	}
	
	public void obtenerPrimerosValores(){//Lo use Para probar si funcaba todo bien. Lo hace, al final de todo borrar este metodo
		Empresa primerEmpresa = empresas.head();
		String nombreEmpresa = primerEmpresa.getNombre();
		this.setNombreEmpresa(nombreEmpresa);
		
		Lista<Cuenta> cuentasPrimeraEmpresa = primerEmpresa.getCuentas();
		String nombrePrimeraCuentaPrimeraEmpresa = cuentasPrimeraEmpresa.head().getTipoCuenta();
		this.setNombrePrimeraCuentaPrimeraEmpresa(nombrePrimeraCuentaPrimeraEmpresa);
	}
	
	private void reiniciarValores(){
		nombreEmpresa = "";
		nombrePrimeraCuentaPrimeraEmpresa = "";
	}
	
	public String getNombreEmpresa(){
		return nombreEmpresa;
	}
	
	public String getNombrePrimeraCuentaPrimeraEmpresa(){
		return nombrePrimeraCuentaPrimeraEmpresa;
	}

	public void setNombreEmpresa(String nombreEmpresa) {
		this.nombreEmpresa = nombreEmpresa;
	}

	public void setNombrePrimeraCuentaPrimeraEmpresa(String nombrePrimeraCuentaPrimeraEmpresa) {
		this.nombrePrimeraCuentaPrimeraEmpresa = nombrePrimeraCuentaPrimeraEmpresa;
	}
}
