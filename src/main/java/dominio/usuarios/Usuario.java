package dominio.usuarios;

import java.time.Year;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import dominio.empresas.Empresa;
import dominio.indicadores.Indicador;
import dominio.indicadores.RepositorioIndicadores;
import dominio.metodologias.Metodologia;
import dominio.metodologias.RepositorioMetodologias;
import dominio.parser.ParserIndicadores;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id 
	@GeneratedValue
	private Long id;
	private String email;
	private String password;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private RepositorioIndicadores repositorioIndicadores;
	
	@OneToOne(cascade = CascadeType.MERGE)
	private RepositorioMetodologias repositorioMetodologias;
	
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
	
	public RepositorioIndicadores getRepositorioIndicadores() {
		if (repositorioIndicadores == null){
			repositorioIndicadores = new RepositorioIndicadores();
		}
		return repositorioIndicadores;
	}
	
	public RepositorioMetodologias getRepositorioMetodologias() {
		if (repositorioMetodologias == null){
			repositorioMetodologias = new RepositorioMetodologias();
		}
		return repositorioMetodologias;
	}
	
	public void crearIndicador(String formulaIndicador){
		getRepositorioIndicadores().agregar(ParserIndicadores.parse(formulaIndicador));
		new RepositorioUsuarios().actualizar(this);
	}
	
	public List<Indicador> getIndicadores() {
		return getRepositorioIndicadores().obtenerTodos();
	}
	
	public Indicador buscarIndicador(String nombreIndicador) {
		return getRepositorioIndicadores().buscarIndicador(nombreIndicador);
	}
	
	public void agregarIndicadores(List<String> indicadoresCreados) {
		getRepositorioIndicadores().agregarMultiplesIndicadores(indicadoresCreados);
	}
	
	public List<Indicador> todosLosIndicadoresAplicablesA(Empresa empresa) {
		return getRepositorioIndicadores().todosLosIndicadoresAplicablesA(empresa);
	}
	
	public Set<Indicador> indicadoresAplicablesA(Empresa empresa, Year anio) {
		return getRepositorioIndicadores().indicadoresAplicablesA(empresa, anio);
	}
	
	public void eliminarIndicadores(){
		getRepositorioIndicadores().eliminarIndicadores();
	}

	public void agregarMetodologia(Metodologia metodologia) {
		getRepositorioMetodologias().agregar(metodologia);
	}
	
	public List<Metodologia> getMetodologias() {
		return getRepositorioMetodologias().obtenerTodos();
	}
	
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