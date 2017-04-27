package views;
import viewmodels.*;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;


public class ConsultarCuentasView extends Dialog<ConsultarCuentasViewModel>{
	public ConsultarCuentasView(WindowOwner owner) {
		super(owner, new ConsultarCuentasViewModel());
	}

	@Override
	protected void createFormPanel(Panel arg0) {
		// TODO Auto-generated method stub
		
	}
}
