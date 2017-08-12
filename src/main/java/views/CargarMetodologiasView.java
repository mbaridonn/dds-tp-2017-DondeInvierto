package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import viewmodels.CargarMetodologiasViewModel;

public class CargarMetodologiasView extends Dialog<CargarMetodologiasViewModel>{
	public CargarMetodologiasView(WindowOwner owner) {
		super(owner, CargarMetodologiasViewModel.getInstance());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Cargar Metodologias");
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Ingrese una metodologia");
		
		new TextBox(mainPanel).bindValueToProperty("nombreMetodologia");
		
		new Button(mainPanel)
			.setCaption("Crear metodologia")
			.onClick(() -> this.mostrarCargarDatosMetodologiaView());
		
	}
	
	public void mostrarCargarDatosMetodologiaView(){
		this.getModelObject().crearMetodologia();
		Dialog<?> dialog = new CargarDatosMetodologiaView(this);
		dialog.open();
	}
	
}