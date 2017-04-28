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
		ConsultarCuentasViewModel.getInstance().obtenerCuentasCargadas(empresas);
	}
	
	public String getRuta(){
		return ruta;
	}
	
	public void setRuta(String ruta){
		this.ruta = ruta;
	}
}
