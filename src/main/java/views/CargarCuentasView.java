package views;
import viewmodels.*;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

public class CargarCuentasView extends Dialog<CargarCuentasViewModel>{
	
	public CargarCuentasView(WindowOwner owner) {
		super(owner, new CargarCuentasViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Cargar Cuentas");
		mainPanel.setLayout(new VerticalLayout());
		
		new TextBox(mainPanel).bindValueToProperty("ruta");
		new Button(mainPanel)
			.setCaption("Cargar Archivo")
			.onClick(() -> this.cargarArchivo());
		
	}
	
	private void cargarArchivo(){
		this.getModelObject().cargarArchivo();
	}
}
