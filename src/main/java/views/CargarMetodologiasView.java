package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import viewmodels.CargarMetodologiasViewModel;

public class CargarMetodologiasView extends Dialog<CargarMetodologiasViewModel>{
	public CargarMetodologiasView(WindowOwner owner) {
		super(owner, new CargarMetodologiasViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Coming Soon");
		mainPanel.setLayout(new VerticalLayout());

	}
	
}