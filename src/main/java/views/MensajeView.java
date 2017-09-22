package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import viewmodels.CargarMetodologiasViewModel;

public class MensajeView extends Dialog<CargarMetodologiasViewModel> {
	String mensaje;
	
	public MensajeView(WindowOwner owner, String mensaje) {
		super(owner, CargarMetodologiasViewModel.getInstance());
		this.mensaje = mensaje;
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Cargar Metodologias");
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText(mensaje);
	}

}
