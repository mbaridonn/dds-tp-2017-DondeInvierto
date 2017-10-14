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
import excepciones.IndicadorExistenteError;

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
	
	public Usuario(String email, String password){
		this.email = email;
		this.password = password;
	}
	
	public void crearIndicador(String formulaIndicador){
		Indicador indicador = ParserIndicadores.parse(formulaIndicador);
		if(this.yaCreeEsteIndicador(indicador)){
			throw new IndicadorExistenteError("El indicador que se intento guardar ya existe!");
		}
		indicadoresCreados.add(indicador);
		new RepositorioUsuarios().actualizar(this);
	}
	
	private boolean yaCreeEsteIndicador(Indicador indicador){
		return indicadoresCreados.contains(indicador);
	}
	
	public Indicador obtenerIndicadorLlamado(String nombreIndicador){
		return indicadoresCreados.stream().filter(ind -> ind.seLlama(nombreIndicador)).findFirst()
				.orElseThrow(() -> new NoExisteIndicadorError("No se pudo encontrar un indicador con ese nombre."));
	}
	
	
	public boolean validar(String email, String password){
		return email.equals(this.email) && password.equals(this.password);
	}
	
	
	public void setIndicadoresCreados(List<Indicador> indicadoresCreados) {
		this.indicadoresCreados = indicadoresCreados;
	}

	public void setMetodologiasCreadas(List<Metodologia> metodologiasCreadas) {
		this.metodologiasCreadas = metodologiasCreadas;
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
	@Override
	public String toString(){//CAPAZ CONVENGA GUARDAR UN NOMBRE DE USUARIO (!!!)
		return email;
	}
	public List<Indicador> getIndicadoresCreados() {
		return indicadoresCreados;
	}
}

class NoExisteIndicadorError extends RuntimeException {NoExisteIndicadorError(String e) {super(e);}}
