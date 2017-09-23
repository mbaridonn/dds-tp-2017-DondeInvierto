package viewmodels;

import java.util.List;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import dominio.empresas.Empresa;
import dominio.empresas.RepositorioEmpresas;
import dominio.metodologias.Metodologia;
import dominio.metodologias.RepositorioMetodologias;

@Observable
public class ConsultarMetodologiasViewModel {
	private Metodologia metodologiaSeleccionada; // Guarda la metodología seleccionada en el Selector
	
	public List<Metodologia> getMetodologias(){
		return new RepositorioMetodologias().obtenerTodos();
	}
	
	public Metodologia getMetodologiaSeleccionada() {
		return metodologiaSeleccionada;
	}
	
	public void setMetodologiaSeleccionada(Metodologia metodologiaSeleccionada) {
		this.metodologiaSeleccionada = metodologiaSeleccionada;
	}
	
	@Dependencies({ "metodologiaSeleccionada" })
	public List<Empresa> getEmpresasOrdenadas(){
		if (metodologiaSeleccionada==null) return null;//Necesario para cuando se abre la view (todavía no se eligió ninguna metodología)
		return metodologiaSeleccionada.evaluarPara(this.getEmpresas());
	}
	
	@Dependencies({ "metodologiaSeleccionada" })
	public List<Empresa> getEmpresasQueNoCumplen() {
		if (metodologiaSeleccionada==null) return null;
		return metodologiaSeleccionada.empresasQueNoCumplenTaxativas(this.getEmpresas());
	}
	
	@Dependencies({ "metodologiaSeleccionada" })
	public List<Empresa> getEmpresasSinDatos() {
		if (metodologiaSeleccionada==null) return null;
		return metodologiaSeleccionada.empresasConDatosFaltantes(this.getEmpresas());
	}
	
	private List<Empresa> getEmpresas(){
		return new RepositorioEmpresas().obtenerTodos();
	}
	
}
