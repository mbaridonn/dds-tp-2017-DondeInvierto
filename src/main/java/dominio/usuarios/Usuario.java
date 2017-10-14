package dominio.usuarios;

import java.time.Year;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import dominio.empresas.Empresa;
import dominio.indicadores.Indicador;
import dominio.indicadores.RepositorioIndicadores;
import dominio.parser.ParserIndicadores;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id 
	@GeneratedValue
	private Long id;
	private String email;
	private String password;
	
	@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name="usuario_id")
	private RepositorioIndicadores repositorioIndicadores = new RepositorioIndicadores();
	
	/*@OneToMany(cascade = CascadeType.MERGE)
	@JoinColumn(name="usuario_id")
	private List<Metodologia> metodologias = new ArrayList<Metodologia>();*/
	
	public static Usuario instance;
	public static Usuario instance(){
		return instance;
	}
	public static void instance(Usuario instance) {
		Usuario.instance = instance;
	}
	private Usuario(){}//Necesario para persistir la clase
	
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
	
	public void crearIndicador(String formulaIndicador){
		repositorioIndicadores.agregar(ParserIndicadores.parse(formulaIndicador));
		new RepositorioUsuarios().actualizar(this);
	}
	
	public List<Indicador> getIndicadores() {
		return repositorioIndicadores.obtenerTodos();
	}
	
	public Indicador buscarIndicador(String nombreIndicador) {
		return repositorioIndicadores.buscarIndicador(nombreIndicador);
	}
	
	public void agregarIndicadores(List<String> indicadoresCreados) {
		this.repositorioIndicadores.agregarMultiplesIndicadores(indicadoresCreados);
	}
	
	public List<Indicador> todosLosIndicadoresAplicablesA(Empresa empresa) {
		return repositorioIndicadores.todosLosIndicadoresAplicablesA(empresa);
	}
	
	public Set<Indicador> indicadoresAplicablesA(Empresa empresa, Year anio) {
		return repositorioIndicadores.indicadoresAplicablesA(empresa, anio);
	}

	/*public void setMetodologias(List<Metodologia> metodologiasCreadas) {
		this.metodologias = metodologiasCreadas;
	}*/
	
	public boolean equals(Object otroObjeto) {
	    return (otroObjeto instanceof Usuario) && this.email.equals(((Usuario) otroObjeto).getEmail());
	}
	
	public int hashCode() {
		return email.hashCode();
	}
	
	@Override
	public String toString(){
		return email;
	}
	
}