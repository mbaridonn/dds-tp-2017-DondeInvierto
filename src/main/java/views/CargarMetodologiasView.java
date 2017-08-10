package views;

import org.uqbar.arena.layout.HorizontalLayout;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Button;
import org.uqbar.arena.widgets.Label;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.TextBox;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

import viewmodels.CargarMetodologiasViewModel;

public class CargarMetodologiasView extends Dialog<CargarMetodologiasViewModel>{
	public CargarMetodologiasView(WindowOwner owner) {
		super(owner, new CargarMetodologiasViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Cargar Metodologias");
		mainPanel.setLayout(new VerticalLayout());
		
		new Label(mainPanel).setText("Ingrese una metodologia");
		
		new TextBox(mainPanel).bindValueToProperty("nombreMetodologia");
		
		new Button(mainPanel)
			.setCaption("Crear metodologia")
			.onClick(() -> this.mostrarCargarDatosMetodologiaView());
		
		//DEBERÃ�A SER UNA VISTA APARTE, QUE SE ABRA DESPUÃ‰S DE QUE SE INGRESA EL NOMBRE
		/*Panel condicionesPanel = new Panel(mainPanel);
		condicionesPanel.setLayout(new HorizontalLayout());
		new Label(condicionesPanel).setText("OpAgregacion");//DEBERIA SER COMBO
		new Label(condicionesPanel).setText("Indicador");//DEBERIA SER COMBO
		new Label(condicionesPanel).setText("Años");//DEBERIA SER TEXTBOX
		new Label(condicionesPanel).setText("OpRelacional");//DEBERIA SER COMBO
		Panel botonesCondicionesPanel = new Panel(condicionesPanel);
		botonesCondicionesPanel.setLayout(new VerticalLayout());
		Panel condicionesPrioritariasPanel = new Panel(botonesCondicionesPanel);
		condicionesPrioritariasPanel.setLayout(new HorizontalLayout());
		new Button(condicionesPrioritariasPanel)
			.setCaption("Agregar condicion prioritaria");
			//.onClick(() -> this.getModelObject().crearMetodologia());
		Panel condicionesTaxativasPanel = new Panel(botonesCondicionesPanel);
		condicionesTaxativasPanel.setLayout(new HorizontalLayout());
		new Label(condicionesTaxativasPanel).setText("Valor");//DEBERIA SER TEXTBOX
		new Button(condicionesTaxativasPanel)
			.setCaption("Agregar condicion taxativa");
			//.onClick(() -> this.getModelObject().crearMetodologia());
		new Button(mainPanel)
			.setCaption("Guardar metodologia")
			.onClick(() -> this.getModelObject().crearMetodologia());
		//FIN VISTA APARTE
		
		new Label(mainPanel)
			.setText("")
			.bindValueToProperty("resultadoOperacion");*/
	}
	
	public void mostrarCargarDatosMetodologiaView(){
		Dialog<?> dialog = new CargarDatosMetodologiaView(this);
		dialog.open();
	}
	
}