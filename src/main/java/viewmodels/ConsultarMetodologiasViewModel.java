package viewmodels;

import java.util.ArrayList;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import dominio.Empresa;
import dominio.metodologias.Metodologia;

@Observable
public class ConsultarMetodologiasViewModel {
	private Metodologia metodologiaSeleccionada; // Guarda la metodología seleccionada en el Selector
	/*private ArrayList<Empresa> empresasQueNoCumplen = new ArrayList<Empresa>();
	private ArrayList<Empresa> empresasSinDatos = new ArrayList<Empresa>();*/
	
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
		/*empresasQueNoCumplen.clear();
		empresasSinDatos.clear();*/
		return metodologiaSeleccionada.evaluarPara(RepositorioEmpresas.getInstance().getEmpresas());
		//FALTA CATCHEAR LAS QUE NO CUMPLEN TAXATIVAS Y LAS QUE NO TIENEN DATOS (NoExisteCuentaError)
	}
	
	/*public ArrayList<Empresa> getEmpresasQueNoCumplen() {
		return empresasQueNoCumplen;
	}
	
	public void setEmpresasQueNoCumplen(ArrayList<Empresa> empresasQueNoCumplen) {
		this.empresasQueNoCumplen = empresasQueNoCumplen;
	}
	
	public ArrayList<Empresa> getEmpresasSinDatos() {
		return empresasSinDatos;
	}
	
	public void setEmpresasSinDatos(ArrayList<Empresa> empresasSinDatos) {
		this.empresasSinDatos = empresasSinDatos;
	}*/
}
