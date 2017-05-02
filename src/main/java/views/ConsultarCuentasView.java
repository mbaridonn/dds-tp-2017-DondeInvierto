package views;
import viewmodels.*;
import dominio.*;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.*;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.widgets.tables.Column;
import org.uqbar.arena.widgets.tables.Table;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;


public class ConsultarCuentasView extends Dialog<ConsultarCuentasViewModel>{
	public ConsultarCuentasView(WindowOwner owner) {
		super(owner, ConsultarCuentasViewModel.getInstance());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Consultar Cuentas");
		mainPanel.setLayout(new VerticalLayout());
		
		//new Label(mainPanel).bindValueToProperty("nombreEmpresa");//Borrar Despues, se uso para probar.
		//new Label(mainPanel).bindValueToProperty("nombrePrimeraCuentaPrimeraEmpresa");//Borrar despues, se uso para probar.
		
		
		/*Selector<Empresa> selector = new Selector<Empresa>(mainPanel);
		selector.bindValueToProperty("empresaSeleccionada");
		selector.bindItemsToProperty("empresas");*/
		
		
		Table<Cuenta> tabla = new Table<Cuenta>(mainPanel, Cuenta.class);
		tabla.bindItemsToProperty("cuentasEmpresa");
		//tabla.bindValueToProperty("cuentaSeleccionada");
		
		new Column<Cuenta>(tabla) //
	    .setTitle("Anio")
	    .setFixedSize(150)
	    .bindContentsToProperty("anio");
		
		new Column<Cuenta>(tabla) //
	    .setTitle("TipoCuenta")
	    .setFixedSize(150)
	    .bindContentsToProperty("tipoCuenta");
		
		new Column<Cuenta>(tabla) //
	    .setTitle("Valor")
	    .setFixedSize(150)
	    .bindContentsToProperty("valor");
		
		//ConsultarCuentasViewModel.getInstance().obtenerPrimerosValores(); //Borrar despues, se uso para probar.
		
		/* 
		 * Ya se pudo pasar la lista de empresas que se genera al cargar el archivo al ViewModel de Consultar.
		 * Habria que poner un textbox para escribir el nombre de la empresa para consultar los datos o un selector (combo box).
		 * Faltaria agregar una tabla aca para las cuentas de las empresas, y desde el viewModel poder poner los datos de la lista en tabla.
		 */
	}
}
