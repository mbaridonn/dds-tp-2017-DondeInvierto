package dominio;

import java.time.Year;
import java.util.Set;

import org.uqbarproject.jpa.java8.extras.transaction.TransactionalOps;

import dominio.empresas.Empresa;
import dominio.indicadores.Indicador;

public class ModificacionListener extends AbstractRepository<EntradaRegistroModificacionesListener> implements TransactionalOps{
	
	public void registrar(Indicador interesado, String observado){
		Set<Indicador> interesados = getInteresadosDe(observado);
		interesados.add(interesado);
		withTransaction(() -> setInteresadosDe(observado, interesados));
	}

	public void seActualizo(String entidad, Empresa empresa, Year anio){
		Set<Indicador> interesados = getInteresadosDe(entidad);
		interesados.forEach(interesado -> interesado.eliminarResultadosDe(empresa, anio));
	}
	
	public Set<Indicador> getInteresadosDe(String nombreObservado){
		return getEntrada(nombreObservado).getInteresados();
	}

	private EntradaRegistroModificacionesListener getEntrada(String nombreObservado) {
		EntradaRegistroModificacionesListener entradaBuscada = obtenerTodos().stream().filter(entrada -> entrada.esDe(nombreObservado)).findFirst()
				.orElse(crearEntrada(nombreObservado));
		return entradaBuscada;
	}

	private EntradaRegistroModificacionesListener crearEntrada(String nombreObservado) {
		EntradaRegistroModificacionesListener nuevaEntrada = new EntradaRegistroModificacionesListener(nombreObservado);
		withTransaction(() -> entityManager().persist(nuevaEntrada));
		return nuevaEntrada;
	}
	
	private void setInteresadosDe(String observado, Set<Indicador> interesados) {
		getEntrada(observado).setInteresados(interesados);
	}

	@Override
	protected Class<EntradaRegistroModificacionesListener> tipoEntidad() {
		return EntradaRegistroModificacionesListener.class;
	}

	@Override
	protected String mensajeEntidadExistenteError(EntradaRegistroModificacionesListener elemento) {
		// TODO Auto-generated method stub
		return null;
	}
}
