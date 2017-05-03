package viewmodels;
import java.util.ArrayList;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;
import dominio.*;

@Observable
public class CargarCuentasViewModel {
	String ruta = "";

	
	public void cargarArchivo(){
		
		ArrayList<Empresa> empresas;
		ArchivoXLS archivo = new ArchivoXLS(ruta);
		empresas = archivo.leerEmpresas();
		ConsultarCuentasViewModel.getInstance().obtenerCuentasCargadas(empresas);
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
