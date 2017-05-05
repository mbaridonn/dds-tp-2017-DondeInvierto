package views;
import viewmodels.*;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.FileSelector;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.MainWindow;

public class MenuView extends MainWindow<MenuViewModel> {

	public MenuView() {
		super(new MenuViewModel());
	}
	
	@Override
	public void createContents(Panel mainPanel) {
		this.setTitle("Menu Principal");
		mainPanel.setLayout(new VerticalLayout());
		
		new FileSelector(mainPanel).setCaption("Cargar Cuentas").bindValueToProperty("ruta");
		new Label(mainPanel).bindValueToProperty("ruta");

		new Button(mainPanel)
			.setCaption("Consultar Cuentas")
			.onClick(() -> this.mostrarConsultarCuentasView())
			.bindEnabledToProperty("cargado");
	}
	
	public void mostrarConsultarCuentasView(){
		Dialog<?> dialog = new ConsultarCuentasView(this);
		dialog.open();
	}
	
	public static void main(String[] args) {
		new MenuView().startApplication();
	}


	
}