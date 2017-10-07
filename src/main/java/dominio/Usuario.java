package dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id 
	@GeneratedValue
	private Long id;
	private String email;
	private String password;
	
	private Usuario(){}; //Necesario para persistir la clase
	
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
	
	@Override
	public String toString(){//CAPAZ CONVENGA GUARDAR UN NOMBRE DE USUARIO (!!!)
		return email;
	}
}
