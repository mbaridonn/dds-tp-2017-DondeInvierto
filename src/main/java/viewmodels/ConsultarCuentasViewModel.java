package viewmodels;
import dominio.*;
import java.util.stream.Stream;
import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

@Observable
public class ConsultarCuentasViewModel {
	private String nombreEmpresa = "h";//Atributo provisional
	private String nombrePrimeraCuentaPrimeraEmpresa = "o";//Atributo provisional
	/*private Empresa empresaSeleccionada; //ES NECESARIO PARA EL SELECTOR
	private Cuenta cuentaSeleccionada; //ES NECESARIO PARA LA TABLA*/
	private Lista<Empresa> empresas;
	
	private static ConsultarCuentasViewModel singleton = new ConsultarCuentasViewModel();
	
	public static ConsultarCuentasViewModel getInstance(){
		return singleton;
	}
	
	public void obtenerCuentasCargadas(Lista<Empresa> empresas){
		this.reiniciarValores();//??
		this.empresas = empresas;
	}
	
	/*@Dependencies({"empresas"})
	public Stream<String> getNombreEmpresas(){//Tendría que devolver una lista//ES NECESARIO PARA EL SELECTOR
		return empresas.stream().map(emp -> emp.getNombre());
	}
	
	@Dependencies({"empresaSeleccionada"})
	public Lista<Cuenta> getCuentasEmpresa(){//ES NECESARIO PARA LA TABLA
		return empresaSeleccionada.getCuentas();
	}*/
	
	//Métodos provisionales
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
