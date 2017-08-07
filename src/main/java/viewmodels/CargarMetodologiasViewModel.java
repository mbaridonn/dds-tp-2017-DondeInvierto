package viewmodels;

import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import dominio.metodologias.CondicionPrioritaria;
import dominio.metodologias.CondicionTaxativa;
import dominio.metodologias.Metodologia;

@Observable
public class CargarMetodologiasViewModel {
	private String nombreMetodologia = "";
	private String resultadoOperacion;
	private ArrayList<CondicionTaxativa> condicionesTaxativas = new ArrayList<CondicionTaxativa>();
	private ArrayList<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<CondicionPrioritaria>();

	public void crearMetodologia() {
		Metodologia metodologia = new Metodologia(nombreMetodologia);
		//forEach condTax in condicionesTaxativas : metodologia.agregarCondicionTaxativa(cond);
		//forEach condPri in condicionesPrioritarias : metodologia.agregarCondicionPrioritaria(cond);
		if (metodologia.esMetodologiaValida()) {
			//Cargar la metodología donde corresponda
			resultadoOperacion = "Metodología cargada";
		} else {
			resultadoOperacion = "Metodología incorrecta";
			//Alternativamente, se podría hacer que cada campo incorrecto lance una excepción por separado (de tipo MetodologiaMalInicializadaError)
			//y catchearlas acá y mostrar el mensaje de c/u (!)
		}
	}

	public String getNombreMetodologia() {
		return nombreMetodologia;
	}
	
	public void setNombreMetodologia(String nombreMetodologia) {
		this.nombreMetodologia = nombreMetodologia;
	}
	
	public String getResultadoOperacion() {
		return resultadoOperacion;
	}
	
	public void setResultadoOperacion(String resultadoOperacion) {
		this.resultadoOperacion = resultadoOperacion;
	}
}
