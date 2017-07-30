package views;
import viewmodels.*;
import org.uqbar.arena.layout.VerticalLayout;
import org.uqbar.arena.widgets.Panel;
import org.uqbar.arena.windows.Dialog;
import org.uqbar.arena.windows.WindowOwner;

public class ConsultarMetodologiasView extends Dialog<ConsultarMetodologiasViewModel>{
	public ConsultarMetodologiasView(WindowOwner owner) {
		super(owner, new ConsultarMetodologiasViewModel());
	}

	@Override
	protected void createFormPanel(Panel mainPanel) {
		this.setTitle("Coming soon");
		mainPanel.setLayout(new VerticalLayout());
		
	}
	
}