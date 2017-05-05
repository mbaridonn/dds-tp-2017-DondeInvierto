package viewmodels;

import java.util.ArrayList;
import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;
import dominio.Empresa;
import dominio.LectorArchivos;

@Observable
public class MenuViewModel {
	String ruta = "";

	public void cargarArchivo(){
		ArrayList<Empresa> empresas;
		LectorArchivos archivo = new LectorArchivos(ruta);
		empresas = archivo.obtenerLectorApropiado().leerEmpresas();
		ConsultarCuentasViewModel.getInstance().setEmpresas(empresas);
	}
	
	public String getRuta(){
		return ruta;
	}
	
	public void setRuta(String ruta){
		this.ruta = ruta;
	}
	
	@Dependencies({"ruta"})
	public boolean getCargado(){
		return ruta.length()>0;
	}
}
