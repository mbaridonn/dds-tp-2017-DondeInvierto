package views;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.List;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.Selector;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import dominio.empresas.Empresa;
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
		
		new Label(mainPanel).setText("Empresas ordenadas:");
		
		List<Empresa> empresasOrdenadas = new List<Empresa>(mainPanel);
		empresasOrdenadas.bindItemsToProperty("empresasOrdenadas");
		
		new Label(mainPanel).setText("Empresas que no cumplen condiciones taxativas:");
		
		List<Empresa> empresasQueNoCumplen = new List<Empresa>(mainPanel);
		empresasOrdenadas.bindItemsToProperty("empresasQueNoCumplen");
		
		new Label(mainPanel).setText("Empresas con datos faltantes:");
		
		List<Empresa> empresasSinDatos = new List<Empresa>(mainPanel);
		empresasOrdenadas.bindItemsToProperty("empresasSinDatos");
		
	}
	
}