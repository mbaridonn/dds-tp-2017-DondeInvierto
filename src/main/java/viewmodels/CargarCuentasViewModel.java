package viewmodels;
import org.uqbar.commons.utils.Observable;
import dominio.*;

@Observable
public class CargarCuentasViewModel {
	String ruta;
	public void cargarArchivo(){
		LectorXLS.ruta = ruta;
	}
	
	public String getRuta(){
		return ruta;
	}
}
