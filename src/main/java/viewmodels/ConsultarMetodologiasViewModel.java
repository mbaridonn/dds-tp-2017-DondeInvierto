package viewmodels;

import java.util.ArrayList;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import dominio.Empresa;
import dominio.metodologias.Metodologia;

@Observable
public class ConsultarMetodologiasViewModel {
	private Metodologia metodologiaSeleccionada; // Guarda la metodología seleccionada en el Selector
	
	public ArrayList<Metodologia> getMetodologias(){
		return RepositorioMetodologias.getInstance().getMetodologias();
	}
	
	public Metodologia getMetodologiaSeleccionada() {
		return metodologiaSeleccionada;
	}
	
	public void setMetodologiaSeleccionada(Metodologia metodologiaSeleccionada) {
		this.metodologiaSeleccionada = metodologiaSeleccionada;
	}
	
	@Dependencies({ "metodologiaSeleccionada" })
	public ArrayList<Empresa> getEmpresasOrdenadas(){
		if (metodologiaSeleccionada==null) return null;//Necesario para cuando se abre la view (todavía no se eligió ninguna metodología)
		return metodologiaSeleccionada.evaluarPara(this.getEmpresas());
	}
	
	@Dependencies({ "metodologiaSeleccionada" })
	public ArrayList<Empresa> getEmpresasQueNoCumplen() {
		if (metodologiaSeleccionada==null) return null;
		return metodologiaSeleccionada.empresasQueNoCumplenTaxativas(this.getEmpresas());
	}
	
	@Dependencies({ "metodologiaSeleccionada" })
	public ArrayList<Empresa> getEmpresasSinDatos() {
		if (metodologiaSeleccionada==null) return null;
		return metodologiaSeleccionada.empresasConDatosFaltantes(this.getEmpresas());
	}
	
	private ArrayList<Empresa> getEmpresas(){
		return RepositorioEmpresas.getInstance().getEmpresas();
	}
	
}
