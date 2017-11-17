package dominio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import dominio.indicadores.Indicador;

@Entity
public class EntradaRegistroModificacionesListener {
	private String observado;
	
	@GeneratedValue
	@Id
	Long id;
	
	@ManyToMany
	@JoinTable(name="entrada_intersado", joinColumns=@JoinColumn(name="entrada_id"), inverseJoinColumns=@JoinColumn(name="indicador_id"))
	private Set<Indicador> interesados;
	
	private EntradaRegistroModificacionesListener() {}
	
	public EntradaRegistroModificacionesListener(String observado){
		this.observado = observado;
		this.interesados = new HashSet<>();
	}
	
	public boolean esDe(String observado){
		return this.observado.equals(observado);
	}
	
	public Set<Indicador> getInteresados() {
		return interesados;
	}
	
	public void setInteresados(Set<Indicador> interesados) {
		this.interesados = interesados;
	}
	
}
