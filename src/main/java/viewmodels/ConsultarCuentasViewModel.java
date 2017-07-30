package viewmodels;

import dominio.*;
import dominio.indicadores.ArchivoIndicadores;
import dominio.indicadores.Indicador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

@Observable
public class ConsultarCuentasViewModel {
	private ArrayList<Empresa> empresas;
	private Empresa empresaSeleccionada; // Guarda la empresa seleccionada en el Selector

	private static ConsultarCuentasViewModel singleton = new ConsultarCuentasViewModel();

	public static ConsultarCuentasViewModel getInstance() {
		return singleton;
	}

	public ArrayList<Empresa> getEmpresas() {
		return empresas;
	}

	public void setEmpresas(ArrayList<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}

	public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}
	
	ArrayList<Cuenta> obtenerCuentasSinRepetidos(ArrayList<Cuenta> cuentasSinRepetidos){
		for (int i = 0; i < cuentasSinRepetidos.size(); i++) {
			for (int j = i + 1; j < cuentasSinRepetidos.size(); j++) {
				if(cuentasSinRepetidos.get(i).getAnio().equals(cuentasSinRepetidos.get(j).getAnio())
						&& cuentasSinRepetidos.get(i).getTipoCuenta().equals(cuentasSinRepetidos.get(j).getTipoCuenta())
						&& cuentasSinRepetidos.get(i).getValor() == cuentasSinRepetidos.get(j).getValor()) {
					cuentasSinRepetidos.remove(cuentasSinRepetidos.get(i));
					j--;
				}
			}
		}
		return cuentasSinRepetidos;
	}

	@Dependencies({ "empresaSeleccionada" })
	public ArrayList<Cuenta> getCuentasEmpresa() {// cuentasEmpresa se muestra en la tabla
		ArrayList<Cuenta> cuentasSeleccionadas;
		if (empresaSeleccionada == null) {
			cuentasSeleccionadas = null;
		} else {
			cuentasSeleccionadas = this.empresaSeleccionada.getCuentas();
			Set<Indicador> indicadoresAplicables = ArchivoIndicadores.getInstance().todosLosIndicadoresAplicablesA(empresaSeleccionada);
			cuentasSeleccionadas.addAll(empresaSeleccionada.resultadosIndicadoresTotales(indicadoresAplicables));			
			cuentasSeleccionadas = obtenerCuentasSinRepetidos(cuentasSeleccionadas);
		}
		return cuentasSeleccionadas;
	}

}
