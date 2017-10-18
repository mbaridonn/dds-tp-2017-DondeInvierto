package dominio;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import excepciones.EntidadExistenteError;

@MappedSuperclass
public abstract class AbstractLocalRepository<T>{
	
	@Id 
	@GeneratedValue
	private Long id;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name="usuario_id")
	protected List<T> elementos = new ArrayList<T>();
	
	public List<T> obtenerTodos() {
		return elementos;
	}
	
	public void agregar(T elemento){
		if (existe(elemento)){
			throw new EntidadExistenteError(mensajeEntidadExistenteError(elemento));
		}
		elementos.add(elemento);
	}
	
	protected boolean existe(T elemento){
		return this.obtenerTodos().contains(elemento);//Contains chequea con equals
	}
	
	protected abstract String mensajeEntidadExistenteError(T elemento);
}
