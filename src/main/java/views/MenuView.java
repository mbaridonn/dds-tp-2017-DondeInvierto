package views;
import viewmodels.*;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
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
		
		new Button(mainPanel)
			.setCaption("Cargar Cuentas")
			.onClick(() -> this.mostrarCargarCuentasView());

		new Button(mainPanel)
			.setCaption("Consultar Cuentas")
			.onClick(() -> this.mostrarConsultarCuentasView());
	}

	public void mostrarCargarCuentasView(){
		Dialog<?> dialog = new CargarCuentasView(this);
		dialog.open();
	}
	
	public void mostrarConsultarCuentasView(){
		Dialog<?> dialog = new ConsultarCuentasView(this);
		dialog.open();
	}
	
	public static void main(String[] args) {
		
		new MenuView().startApplication();
	}


	
}