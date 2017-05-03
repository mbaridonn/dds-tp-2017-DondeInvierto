package viewmodels;
import dominio.*;

import java.util.ArrayList;
import java.util.stream.Stream;
import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

@Observable
public class ConsultarCuentasViewModel {
	private Empresa empresaSeleccionada/* = this.getPrimerEmpresa()*/; //Guarda la empresa seleccionada en el Selector
	private ArrayList<Empresa> empresas;
	private ArrayList<Cuenta> cuentasEmpresa;
	
	private static ConsultarCuentasViewModel singleton = new ConsultarCuentasViewModel();
	
	public static ConsultarCuentasViewModel getInstance(){
		return singleton;
	}
	
	public void obtenerCuentasCargadas(ArrayList<Empresa> empresas){
		this.empresas = empresas;
		this.cuentasEmpresa = this.empresas.get(0).getCuentas();
	}
	
	/*public void getCuentasSeleccionadas(){
		this.cuentasEmpresa = this.getEmpresaSeleccionada().getCuentas();
	}*/
	
	public Empresa getPrimerEmpresa(){
		return empresas.get(0);
	}
	
	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}
	
	public ArrayList<Cuenta> getCuentasEmpresa(){
		return cuentasEmpresa;
	}
	
	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}
	
	/*@Dependencies({"empresaSeleccionada"})
	public ArrayList<Cuenta> getCuentasEmpresa(){//ES NECESARIO PARA LA TABLA
		return empresaSeleccionada.getCuentas();
	}*/
	
}
