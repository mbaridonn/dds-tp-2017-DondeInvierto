package viewmodels;

import dominio.empresas.Cuenta;
import dominio.empresas.Empresa;
import dominio.empresas.RepositorioEmpresas;
import dominio.indicadores.ArchivoIndicadores;
import dominio.indicadores.Indicador;

import java.util.List;
import java.util.Set;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

@Observable
public class ConsultarCuentasViewModel {
	private Empresa empresaSeleccionada; // Guarda la empresa seleccionada en el Selector

	private static ConsultarCuentasViewModel singleton = new ConsultarCuentasViewModel();

	public static ConsultarCuentasViewModel getInstance() {
		return singleton;
	}

	public List<Empresa> getEmpresas() {
		return new RepositorioEmpresas().getEmpresas();
	}

	public Empresa getEmpresaSeleccionada() {
		return empresaSeleccionada;
	}

	public void setEmpresaSeleccionada(Empresa empresaSeleccionada) {
		this.empresaSeleccionada = empresaSeleccionada;
	}
	
	public List<Cuenta> obtenerCuentasSinRepetidos(List<Cuenta> cuentasSinRepetidos){
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
	public List<Cuenta> getCuentasEmpresa() {// cuentasEmpresa se muestra en la tabla
		List<Cuenta> cuentasSeleccionadas;
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
