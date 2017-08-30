package views;
import viewmodels.*;

import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.FileSelector;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.MainWindow;

import dominio.indicadores.ArchivoIndicadores;

public class MenuView extends MainWindow<MenuViewModel> {

	public MenuView() {
		super(new MenuViewModel());
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		
		ArchivoIndicadores.getInstance().leerIndicadores();
		
		this.setTitle("Menu Principal");
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).bindValueToProperty("resultadoOperacion");
		
		new Button(mainPanel)
			.setCaption("Cargar Cuentas desde BD")
			.onClick(() -> this.getModelObject().cargarCuentasDesdeBD());
		
		new FileSelector(mainPanel).setCaption("Cargar Cuentas desde Archivo").bindValueToProperty("ruta");
		
		new Label(mainPanel).bindValueToProperty("ruta");

		new Button(mainPanel)
			.setCaption("Cargar Indicadores")
			.onClick(() -> this.mostrarCargarIndicadoresView());
		
		new Button(mainPanel)
			.setCaption("Cargar Metodologias")
			.onClick(() -> this.mostrarCargarMetodologiasView());
		
		new Button(mainPanel)
			.setCaption("Consultar Cuentas")
			.onClick(() -> this.mostrarConsultarCuentasView())
			.bindEnabledToProperty("cargado");
		
		new Button(mainPanel)
			.setCaption("Consultar Metodologias")
			.onClick(() -> this.mostrarConsultarMetodologiasView());
	}
	
	public void mostrarCargarIndicadoresView(){
		Dialog<?> dialog = new CargarIndicadoresView(this);
		dialog.open();
	}
	
	public void mostrarCargarMetodologiasView(){
		Dialog<?> dialog = new CargarMetodologiasView(this);
		dialog.open();
	}
	
	public void mostrarConsultarCuentasView(){
		Dialog<?> dialog = new ConsultarCuentasView(this);
		dialog.open();
	}
	
	public void mostrarConsultarMetodologiasView(){
		Dialog<?> dialog = new ConsultarMetodologiasView(this);
		dialog.open();
	}
	
	public static void main(String[] args) {
		new MenuView().startApplication();
	}
	
}