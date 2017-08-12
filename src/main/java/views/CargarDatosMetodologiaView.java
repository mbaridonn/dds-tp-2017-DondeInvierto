package views;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.NumericField;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import dominio.Empresa;
import dominio.indicadores.Indicador;
import dominio.metodologias.OperacionAgregacion;
import dominio.metodologias.OperacionRelacional;
import viewmodels.CargarMetodologiasViewModel;

public class CargarDatosMetodologiaView extends Dialog<CargarMetodologiasViewModel> {
	public CargarDatosMetodologiaView(WindowOwner owner) {
		super(owner, CargarMetodologiasViewModel.getInstance());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Cargar Metodologias");
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).bindValueToProperty("nombreMetodologia");

		Panel condicionesPanel = new Panel(mainPanel);
		condicionesPanel.setLayout(new HorizontalLayout());
		
		Selector<OperacionAgregacion> selectorOperacionAgregacion = new Selector<OperacionAgregacion>(condicionesPanel);// opAgregacion COMBOBOX
		this.getModelObject().cargarOperacionesAgregacion();
		selectorOperacionAgregacion.bindValueToProperty("operacionAgregacionSeleccionada");
		selectorOperacionAgregacion.bindItemsToProperty("operacionesAgregacion");
		
		Selector<Indicador> selectorIndicador = new Selector<Indicador>(condicionesPanel);//Indicador COMBOBOX
		this.getModelObject().cargarIndicadores();
		selectorIndicador.bindValueToProperty("indicadorSeleccionado");
		selectorIndicador.bindItemsToProperty("indicadores");
		
		new NumericField(condicionesPanel); //AÑOS
		
		Selector<OperacionRelacional> selectorOpRelacional = new Selector<OperacionRelacional>(condicionesPanel);//opRelacional COMBOBOX
		this.getModelObject().cargarOperacionesRelacionales();
		selectorOpRelacional.bindValueToProperty("operacionRelacionalSeleccionada");
		selectorOpRelacional.bindItemsToProperty("operacionesRelacionales");
		
		Panel botonesCondicionesPanel = new Panel(condicionesPanel);
		botonesCondicionesPanel.setLayout(new VerticalLayout());
		
		Panel condicionesPrioritariasPanel = new Panel(botonesCondicionesPanel);
		condicionesPrioritariasPanel.setLayout(new HorizontalLayout());
		new Button(condicionesPrioritariasPanel).setCaption("Agregar condicion prioritaria");
		// .onClick(() -> this.getModelObject().crearMetodologia());
		
		Panel condicionesTaxativasPanel = new Panel(botonesCondicionesPanel);
		condicionesTaxativasPanel.setLayout(new HorizontalLayout());
		new NumericField(condicionesTaxativasPanel);//VALOR
		new Button(condicionesTaxativasPanel).setCaption("Agregar condicion taxativa");
		// .onClick(() -> this.getModelObject().crearMetodologia());
		new Button(mainPanel).setCaption("Guardar metodologia");

	}
}
