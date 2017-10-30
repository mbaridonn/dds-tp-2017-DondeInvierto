package viewmodels;

import java.util.List;

import org.uqbar.commons.utils.Dependencies;
import org.uqbar.commons.utils.Observable;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;
import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.empresas.Empresa;
import dominio.empresas.LectorArchivos;
import dominio.empresas.RepositorioEmpresas;
import excepciones.NoSePudoLeerEseTipoDeArchivoError;

@Observable
public class MenuViewModel implements WithGlobalEntityManager, TransactionalOps{
	private String resultadoOperacion = "";
	private String ruta = "";
	
	@Dependencies({ "ruta" })
	public boolean getHayCuentas(){
		return (new RepositorioEmpresas().hayEmpresas()) || ruta != "";
		//Hay que poner la cond de la ruta xq, si no hay empresas, al cargar un archivo se dispara la propiedad antes de que se agreguen las empresas a la BD
	}
	
	public void cargarArchivo(){
		try{
			List<Empresa> empresas = new LectorArchivos(ruta).getEmpresas();
			withTransaction(() -> new RepositorioEmpresas().agregarMultiplesEmpresas(empresas));
			resultadoOperacion = "Archivo cargado";
		}
		catch(NoSePudoLeerEseTipoDeArchivoError e){
			this.ruta = "";
			resultadoOperacion = e.getMessage();
		}
	}
	
	public String getResultadoOperacion() {
		return resultadoOperacion;
	}
	
	public void setResultadoOperacion(String resultadoOperacion) {
		this.resultadoOperacion = resultadoOperacion;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
		this.cargarArchivo();
	}

}
