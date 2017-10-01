package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import viewmodels.CargarIndicadoresViewModel;

public class CargarIndicadoresView extends Dialog<CargarIndicadoresViewModel>{
	public CargarIndicadoresView(WindowOwner owner) {
		super(owner, new CargarIndicadoresViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Cargar Indicadores");
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Ingrese un indicador");
		
		new TextBox(mainPanel).bindValueToProperty("indicador");
		
		new Button(mainPanel)
			.setCaption("Guardar")
			.onClick(() -> this.getModelObject().guardarIndicador());
		
		new Label(mainPanel)
			.setText("")
			.bindValueToProperty("resultadoOperacion");
	}
	
}
