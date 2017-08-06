package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import dominio.metodologias.CondicionPrioritaria;
import dominio.metodologias.CondicionTaxativa;
import viewmodels.CargarMetodologiasViewModel;

public class CargarMetodologiasView extends Dialog<CargarMetodologiasViewModel>{
	public CargarMetodologiasView(WindowOwner owner) {
		super(owner, new CargarMetodologiasViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Cargar Metodologías");
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Ingrese una metodología");
		
		new TextBox(mainPanel).bindValueToProperty("nombreMetodologia");
		
		Table<CondicionTaxativa> tablaCondicionesTaxativas = new Table<CondicionTaxativa>(mainPanel, CondicionTaxativa.class);
		tablaCondicionesTaxativas.bindItemsToProperty("condicionesTaxativas");//CÓMO MUESTRO LA COND EN LA TABLA??
		
		//BOTÓN PARA CARGAR TAXATIVA. OTRO FORMULARIO?
		
		Table<CondicionPrioritaria> tablaCondicionesPrioritarias = new Table<CondicionPrioritaria>(mainPanel, CondicionPrioritaria.class);
		tablaCondicionesPrioritarias.bindItemsToProperty("condicionesPrioritarias");
		
		//BOTÓN PARA CARGAR PRIORITARIA. OTRO FORMULARIO?
		
		new Button(mainPanel)
			.setCaption("Crear metodología")
			.onClick(() -> this.getModelObject().crearMetodologia());
		
		new Label(mainPanel)
			.setText("")
			.bindValueToProperty("resultadoOperacion");
	}
	
}