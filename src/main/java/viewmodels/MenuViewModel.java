package viewmodels;

import java.util.ArrayList;
import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;

import dominio.Archivo;
import dominio.Empresa;
import dominio.LectorArchivos;

@Observable
public class MenuViewModel {
	String ruta = "";

	public void cargarArchivo(){
		ArrayList<Empresa> empresas;
		LectorArchivos lectorArchivos = new LectorArchivos(ruta);
		Archivo archivo = lectorArchivos.obtenerLectorApropiado();
		archivo.leerEmpresas();
		empresas = archivo.getEmpresas();
		ConsultarCuentasViewModel.getInstance().setEmpresas(empresas);
	}
	
	public String getRuta(){
		return ruta;
	}
	
	public void setRuta(String ruta){
		this.ruta = ruta;
		this.cargarArchivo();
	}
	
	@Dependencies({"ruta"})
	public boolean getCargado(){
		return ruta.length()>0;
	}
}
