package viewmodels;

import java.util.ArrayList;

import org.uqbar.commons.utils.Observable;

import dominio.indicadores.ArchivoIndicadores;
import dominio.indicadores.Indicador;
import dominio.metodologias.Igual;
import dominio.metodologias.Mayor;
import dominio.metodologias.Mediana;
import dominio.metodologias.Menor;
import dominio.metodologias.MetodologiaBuilder;
import dominio.metodologias.OperacionAgregacion;
import dominio.metodologias.OperacionRelacional;
import dominio.metodologias.Promedio;
import dominio.metodologias.Sumatoria;
import dominio.metodologias.Ultimo;
import dominio.metodologias.Variacion;

@Observable
public class CargarMetodologiasViewModel {
	private String nombreMetodologia = "";
	private String resultadoOperacion;
	private MetodologiaBuilder metodologiaBuilder;
	
	private int añosSeleccionados;
	
	private int valorSeleccionado;

	private Indicador indicadorSeleccionado;
	private ArrayList<Indicador> indicadores;
	
	private OperacionAgregacion operacionAgregacionSeleccionada;
	private static ArrayList<OperacionAgregacion> operacionesAgregacion = new ArrayList<OperacionAgregacion>();
	
	private OperacionRelacional operacionRelacionalSeleccionada;
	private static ArrayList<OperacionRelacional> operacionesRelacionales = new ArrayList<OperacionRelacional>();

	private static CargarMetodologiasViewModel singleton = new CargarMetodologiasViewModel();
	
	public int getValorSeleccionado() {
		return valorSeleccionado;
	}

	public void setValorSeleccionado(int valorSeleccionado) {
		this.valorSeleccionado = valorSeleccionado;
	}

	public int getAñosSeleccionados() {
		return añosSeleccionados;
	}

	public void setAñosSeleccionados(int añosSeleccionados) {
		this.añosSeleccionados = añosSeleccionados;
	}
	
	public OperacionAgregacion getOperacionAgregacionSeleccionada() {
		return operacionAgregacionSeleccionada;
	}

	public void setOperacionAgregacionSeleccionada(OperacionAgregacion operacionAgregacionSeleccionada) {
		this.operacionAgregacionSeleccionada = operacionAgregacionSeleccionada;
	}

	public ArrayList<OperacionAgregacion> getOperacionesAgregacion() {
		return operacionesAgregacion;
	}

	public void setOperacionesAgregacion(ArrayList<OperacionAgregacion> operacionesAgregacion) {
		this.operacionesAgregacion = operacionesAgregacion;
	}
	
	public Indicador getIndicadorSeleccionado() {
		return indicadorSeleccionado;
	}

	public void setIndicadorSeleccionado(Indicador indicadorSeleccionado) {
		this.indicadorSeleccionado = indicadorSeleccionado;
	}

	public ArrayList<Indicador> getIndicadores() {
		return indicadores;
	}

	public void setIndicadores(ArrayList<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	
	public static CargarMetodologiasViewModel getInstance() {
		return singleton;
	}
	
	public OperacionRelacional getOperacionRelacionalSeleccionada() {
		return operacionRelacionalSeleccionada;
	}

	public void setOperacionRelacionalSeleccionada(OperacionRelacional operacionRelacional) {
		this.operacionRelacionalSeleccionada = operacionRelacional;
	}

	public void crearMetodologia() {
		metodologiaBuilder = new MetodologiaBuilder();
		metodologiaBuilder.crearMetodologia(nombreMetodologia);
		if (metodologiaBuilder.contieneMetodologiaValida()) {
			//Cargar la metodologÃ­a donde corresponda
			resultadoOperacion = "MetodologÃ­a creada";
			//ABRIR PANEL CONDICIONES. HAY QUE PASAR EL BUILDER DE UNA VISTA A OTRA PARA CONTINUAR CON LA CREACIÃ“N DE LA METODOLOGÃ�A (!!)
		} else {
			resultadoOperacion = "Ingrese un nombre para la MetodologÃ­a";
			//Alternativamente, se podrÃ­a hacer que cada campo incorrecto lance una excepciÃ³n por separado (de tipo MetodologiaMalInicializadaError)
			//y catchearlas acÃ¡ y mostrar el mensaje de c/u (!)
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

	public ArrayList<OperacionRelacional> getOperacionesRelacionales() {
		return operacionesRelacionales;
	}

	public void cargarOperacionesRelacionales() {
		operacionesRelacionales.add(new Mayor());
		operacionesRelacionales.add(new Menor());
		operacionesRelacionales.add(new Igual());
	}
	
	public void cargarIndicadores() {
		indicadores = ArchivoIndicadores.getInstance().getIndicadores();
	}

	public void cargarOperacionesAgregacion() {
		operacionesAgregacion.add(new Mediana());
		operacionesAgregacion.add(new Promedio());
		operacionesAgregacion.add(new Sumatoria());
		operacionesAgregacion.add(new Ultimo());
		operacionesAgregacion.add(new Variacion());
	}

	public void agregarCondicionProritaria() {
		metodologiaBuilder = metodologiaBuilder.agregarCondicionPrioritaria(operacionAgregacionSeleccionada, indicadorSeleccionado, añosSeleccionados, operacionRelacionalSeleccionada);
	}
	
	public void agregarCondicionTaxativa() {
		metodologiaBuilder = metodologiaBuilder.agregarCondicionTaxativa(operacionAgregacionSeleccionada, indicadorSeleccionado, añosSeleccionados, operacionRelacionalSeleccionada, valorSeleccionado);
	}

	public void guardarMetodologia() {
		System.out.println(operacionAgregacionSeleccionada);
		System.out.println(indicadorSeleccionado);
		System.out.println(añosSeleccionados);
		System.out.println(operacionRelacionalSeleccionada);
		System.out.println(valorSeleccionado);
	}

}
