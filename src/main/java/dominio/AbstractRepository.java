package dominio;

import java.util.List;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public abstract class AbstractRepository<T> implements WithGlobalEntityManager{
	
	public List<T> obtenerTodos() { //VER NOMBRE
		return (List<T>) this.entityManager().createQuery("FROM " + this.tipoEntidad()).getResultList();
	}
	
	public void agregar(T elemento){
		if (existe(elemento)){
			throw new EntidadExistenteError("Ya existe esa entidad en la base de datos");
		}
		this.entityManager().persist(elemento);//La transacción se tiene que agregar donde se envíe el mensaje (!)
	}
	
	private boolean existe(T elemento){
		return this.obtenerTodos().contains(elemento);//Contains chequea con equals (!)
	}
	
	private String tipoEntidad(){
		return this.getClass().toString();//HABRÍA QUE PARSEARLO PARA OBTENER SÓLO EL NOMBRE DE LA CLASE, ASUMIENDO QUE TODOS LOS REPOSITORIOS SE LLAMAN RespositorioXX
	}
}

class EntidadExistenteError extends RuntimeException{public EntidadExistenteError(String e){super(e);}}