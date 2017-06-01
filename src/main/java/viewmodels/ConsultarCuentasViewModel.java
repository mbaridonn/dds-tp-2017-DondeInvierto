package viewmodels;
import dominio.*;

import java.util.ArrayList;
import java.util.Set;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

@Observable
public class ConsultarCuentasViewModel {
	private ArrayList<Empresa> empresas;
	private Empresa empresaSeleccionada; //Guarda la empresa seleccionada en el Selector
	
	private static ConsultarCuentasViewModel singleton = new ConsultarCuentasViewModel();
	
	public static ConsultarCuentasViewModel getInstance(){
		return singleton;
	}
	
	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}
	
	public void setEmpresas(ArrayList<Empresa> empresas){
		this.empresas = empresas;
	}
	
	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}
	
	public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}
	
	@Dependencies({"empresaSeleccionada"})
	public ArrayList<Cuenta> getCuentasEmpresa(){//cuentasEmpresa se muestra en la tabla
		ArrayList<Cuenta> cuentasSeleccionadas;
		if (empresaSeleccionada==null){
			cuentasSeleccionadas = null;
		} else {
			cuentasSeleccionadas = this.empresaSeleccionada.getCuentas();
			Set<Indicador> indicadoresAplicables = ArchivoIndicadores.getInstance().todosLosIndicadoresAplicablesA(empresaSeleccionada);
			//cuentasSeleccionadas.addAll(empresaSeleccionada.resultadosIndicadoresTotales(indicadoresAplicables));
		}
		
		return cuentasSeleccionadas;
	}
	
}
