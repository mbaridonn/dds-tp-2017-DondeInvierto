package dominio.empresas;

import java.util.List;
import java.util.stream.Stream;

import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

public class RepositorioEmpresas implements WithGlobalEntityManager{
	
	public List<Empresa> getEmpresas() {
		return (List<Empresa>) this.entityManager().createQuery("FROM Empresa").getResultList();
	}
	
	public boolean hayEmpresas(){
		return this.getEmpresas().size() > 0;
	}

	public void agregarEmpresas(List<Empresa> empresas) {
		Stream<Empresa> empresasNuevas = empresas.stream().filter(emp -> !this.existeEmpresa(emp));//Sólo persisto las empresas que no están ya en la BD
		empresasNuevas.forEach(empresa -> this.entityManager().persist(empresa));//La transacción se tiene que agregar donde se envíe el mensaje (!)
	}

	private boolean existeEmpresa(Empresa empresa){
		List<Empresa> empresasConMismoNombre = this.entityManager().createQuery("FROM Empresa where nombre = '" + empresa.getNombre() + "'").getResultList();
		return !empresasConMismoNombre.isEmpty();
	}
}
