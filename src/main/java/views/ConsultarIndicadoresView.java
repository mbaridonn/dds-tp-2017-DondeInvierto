package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import dominio.Indicador;
import viewmodels.CargarIndicadoresViewModel;

public class ConsultarIndicadoresView extends Dialog<CargarIndicadoresViewModel>{
	public ConsultarIndicadoresView(WindowOwner owner) {
		super(owner, CargarIndicadoresViewModel.getInstance());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Consultar Indicadores");
		mainPanel.setLayout(new VerticalLayout());
		
		Table<Indicador> tabla = new Table<Indicador>(mainPanel, Indicador.class);
		tabla.bindItemsToProperty("indicadores");
		
		new Column<Indicador>(tabla)
			.setTitle("Indicador")
			.setFixedSize(150)
			.bindContentsToProperty("indicador");
	}
}
