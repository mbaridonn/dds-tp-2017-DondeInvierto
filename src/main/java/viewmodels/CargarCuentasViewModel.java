package viewmodels;
import org.uqbar.commons.utils.Observable;
import dominio.*;

@Observable
public class CargarCuentasViewModel {
	String ruta;
	public void cargarArchivo(){
		Lista<Empresa> empresas;
		ArchivoXLS archivo = new ArchivoXLS(ruta);
		empresas = archivo.leerEmpresas();
	}
	
	public String getRuta(){
		return ruta;
	}
}
