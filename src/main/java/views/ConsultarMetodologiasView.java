package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import dominio.Empresa;
import dominio.metodologias.Metodologia;
import viewmodels.ConsultarMetodologiasViewModel;

public class ConsultarMetodologiasView extends Dialog<ConsultarMetodologiasViewModel>{
	public ConsultarMetodologiasView(WindowOwner owner) {
		super(owner, new ConsultarMetodologiasViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Consultar Metodologias");
		mainPanel.setLayout(new VerticalLayout());
		
		Selector<Metodologia> selectorMetodologia = new Selector<Metodologia>(mainPanel);
		selectorMetodologia.bindValueToProperty("metodologiaSeleccionada");
		selectorMetodologia.bindItemsToProperty("metodologias");
		
		Table<Empresa> empresasOrdenadas = new Table<Empresa>(mainPanel, Empresa.class);
		empresasOrdenadas.bindItemsToProperty("empresasOrdenadas");
		new Column<Empresa>(empresasOrdenadas)
	    .setTitle("Nombre")
	    .setFixedSize(150)
	    .bindContentsToProperty("nombre");
		
		/*Table<Empresa> empresasQueNoCumplen = new Table<Empresa>(mainPanel, Empresa.class);
		empresasQueNoCumplen.bindItemsToProperty("empresasQueNoCumplen");
		new Column<Empresa>(empresasOrdenadas)
	    .setTitle("Nombre")
	    .setFixedSize(150)
	    .bindContentsToProperty("nombre");
		
		Table<Empresa> empresasSinDatos = new Table<Empresa>(mainPanel, Empresa.class);
		empresasSinDatos.bindItemsToProperty("empresasSinDatos");
		new Column<Empresa>(empresasOrdenadas)
	    .setTitle("Nombre")
	    .setFixedSize(150)
	    .bindContentsToProperty("nombre");*/
	}
	
}