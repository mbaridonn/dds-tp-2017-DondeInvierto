package dominio;

import java.util.ArrayList;
import java.util.List;

import excepciones.EntidadExistenteError;

public abstract class AbstractLocalRepository<T>{
	
	List<T> elementos = new ArrayList<T>();
	
	public List<T> obtenerTodos() {
		return elementos;
	}
	
	public void agregar(T elemento){
		if (existe(elemento)){
			throw new EntidadExistenteError(mensajeEntidadExistenteError(elemento));
		}
		elementos.add(elemento);//La transacción se tiene que agregar donde se envíe el mensaje
	}
	
	protected boolean existe(T elemento){
		return this.obtenerTodos().contains(elemento);//Contains chequea con equals
	}
	
	protected abstract String mensajeEntidadExistenteError(T elemento);
}
