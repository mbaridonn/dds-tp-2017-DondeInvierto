package viewmodels;

import org.uqbar.commons.utils.Observable;

import dominio.metodologias.MetodologiaBuilder;

@Observable
public class CargarMetodologiasViewModel {
	private String nombreMetodologia = "";
	private String resultadoOperacion;
	private MetodologiaBuilder metodologiaBuilder;

	public void crearMetodologia() {
		metodologiaBuilder.crearMetodologia(nombreMetodologia);
		if (metodologiaBuilder.contieneMetodologiaValida()) {
			//Cargar la metodología donde corresponda
			resultadoOperacion = "Metodología creada";
			//ABRIR PANEL CONDICIONES. HAY QUE PASAR EL BUILDER DE UNA VISTA A OTRA PARA CONTINUAR CON LA CREACIÓN DE LA METODOLOGÍA (!!)
		} else {
			resultadoOperacion = "Ingrese un nombre para la Metodología";
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
