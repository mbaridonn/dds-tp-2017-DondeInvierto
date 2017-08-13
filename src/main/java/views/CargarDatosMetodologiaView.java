package views;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.NumericField;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
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
		
		new Label(condicionesPanel).setText("Operacion Agregacion:");
		Selector<OperacionAgregacion> selectorOperacionAgregacion = new Selector<OperacionAgregacion>(condicionesPanel);
		selectorOperacionAgregacion.bindValueToProperty("operacionAgregacionSeleccionada");
		selectorOperacionAgregacion.bindItemsToProperty("operacionesAgregacion");
		
		new Label(condicionesPanel).setText("Indicador:");
		Selector<Indicador> selectorIndicador = new Selector<Indicador>(condicionesPanel);
		selectorIndicador.bindValueToProperty("indicadorSeleccionado");
		selectorIndicador.bindItemsToProperty("indicadores");
		
		new Label(condicionesPanel).setText("Anios:");
		new NumericField(condicionesPanel).bindValueToProperty("aniosSeleccionados");
		
		new Label(condicionesPanel).setText("Operacion Relacional:");
		Selector<OperacionRelacional> selectorOpRelacional = new Selector<OperacionRelacional>(condicionesPanel);
		selectorOpRelacional.bindValueToProperty("operacionRelacionalSeleccionada");
		selectorOpRelacional.bindItemsToProperty("operacionesRelacionales");
		
		Panel botonesCondicionesPanel = new Panel(condicionesPanel);
		botonesCondicionesPanel.setLayout(new VerticalLayout());
		
		Panel condicionesPrioritariasPanel = new Panel(botonesCondicionesPanel);
		condicionesPrioritariasPanel.setLayout(new HorizontalLayout());
		new Button(condicionesPrioritariasPanel).setCaption("Agregar condicion prioritaria")
												.onClick(() -> this.getModelObject().agregarCondicionProritaria());
		
		Panel condicionesTaxativasPanel = new Panel(botonesCondicionesPanel);
		condicionesTaxativasPanel.setLayout(new HorizontalLayout());
		
		new Button(condicionesTaxativasPanel).setCaption("Agregar condicion taxativa")
											 .onClick(() -> this.getModelObject().agregarCondicionTaxativa());
		
			new Label(condicionesTaxativasPanel).setText("Valor:");
		new NumericField(condicionesTaxativasPanel).bindValueToProperty("valorSeleccionado");//VALOR
		
		new Button(mainPanel).setCaption("Guardar metodologia")
							 .onClick(() -> this.getModelObject().guardarMetodologia());
	}
}
