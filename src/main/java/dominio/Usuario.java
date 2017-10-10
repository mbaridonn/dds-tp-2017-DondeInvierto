package dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dominio.indicadores.Indicador;
import dominio.metodologias.Metodologia;
import dominio.parser.ParserIndicadores;


import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

@Entity
@Table(name = "usuarios")
public class Usuario implements WithGlobalEntityManager {
	@Id 
	@GeneratedValue
	private Long id;
	private String email;
	private String password;
	
	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name="usuario_id")
	private List<Indicador> indicadoresCreados = new ArrayList<Indicador>();
	
	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name="usuario_id")
	private List<Metodologia> metodologiasCreadas = new ArrayList<Metodologia>();
	
	public static Usuario instance;
	public static Usuario instance(){
		return instance;
	}
	public static void instance(Usuario instance) {
		Usuario.instance = instance;
	}
	private Usuario(){}//Necesario para persistir la clase
	
	public void setIndicadoresCreados(List<Indicador> indicadoresCreados) {
		this.indicadoresCreados = indicadoresCreados;
	}

	public void setMetodologiasCreadas(List<Metodologia> metodologiasCreadas) {
		this.metodologiasCreadas = metodologiasCreadas;
	}

	public Usuario(String email, String password){
		this.email = email;
		this.password = password;
	}
	
	public boolean validar(String email, String password){
		return email.equals(this.email) && password.equals(this.password);
	}
	
	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}
	
	public boolean equals(Object otroObjeto) {
	    return (otroObjeto instanceof Usuario) && this.email.equals(((Usuario) otroObjeto).getEmail());
	}
	
	public int hashCode() {
		return email.hashCode();
	}
	
	public void crearIndicador(String formulaIndicador){
		Indicador indicador = ParserIndicadores.parse(formulaIndicador);
		indicadoresCreados.add(indicador);
		new RepositorioUsuarios().actualizar(this);
	}
	
	@Override
	public String toString(){//CAPAZ CONVENGA GUARDAR UN NOMBRE DE USUARIO (!!!)
		return email;
	}
}
