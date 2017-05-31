package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;
import org.uqbar.commons.utils.Observable;

import dominio.Indicador;
import viewmodels.ConsultarIndicadoresViewModel;

@Observable
public class ConsultarIndicadoresView extends Dialog<ConsultarIndicadoresViewModel>{
	public ConsultarIndicadoresView(WindowOwner owner) {
		super(owner, new ConsultarIndicadoresViewModel());
		this.getModelObject().leerArchivoIndicadores();//Acá NO se tendría que hacer la carga del archivo
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
			.bindContentsToProperty("nombre");
	}
}
