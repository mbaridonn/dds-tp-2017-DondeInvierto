package dominio;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import dominio.indicadores.Indicador;

@Entity
public class EntradaRegistroModificacionesListener {
	private String observado;
	
	@OneToMany
	@JoinColumn(name="entrada_id")
	private Set<Indicador> interesados;
	
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
