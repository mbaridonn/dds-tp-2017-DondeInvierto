package dominio.indicadores;

import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.uqbar.commons.utils.Observable;

import dominio.AbstractRepository;
import dominio.empresas.Empresa;
import dominio.parser.ParserIndicadores;

@Observable
public class RepositorioIndicadores extends AbstractRepository<Indicador> {
	
	public void agregarMultiplesIndicadores(List<String> strIndicadores) {
		List<Indicador> indicadoresNuevos = obtenerIndicadoresParseados(strIndicadores);//NO ESTOY CATCHEANDO ParserError (!!!)
		indicadoresNuevos.forEach(ind -> agregar(ind));
	}
	
	@Override
	public List<Indicador> obtenerTodos() {
		return super.obtenerTodos().stream().map(protoInd -> ParserIndicadores.parse(protoInd.getEquivalencia()))
				.collect(Collectors.toList());
		//Estoy utilizando la equivalencia del indicador que guardo en la BD para generar un indicador que tenga la expresion
	}
	
	@Override
	protected Class<Indicador> tipoEntidad() {
		return Indicador.class;
	}

	@Override
	protected String mensajeEntidadExistenteError(Indicador elemento) {
		return "Ya existe un indicador con el nombre " + elemento.getNombre();
	}

	public Set<Indicador> todosLosIndicadoresAplicablesA(Empresa empresa) {
		Set<Indicador> indicadoresAplicables = new HashSet<Indicador>();
		Set<Year> aniosDeCuentas = empresa.aniosDeLosQueTieneCuentas();
		aniosDeCuentas.forEach(anio -> indicadoresAplicables.addAll(this.indicadoresAplicablesA(empresa, anio)));
		return indicadoresAplicables;
	}

	public Set<Indicador> indicadoresAplicablesA(Empresa empresa, Year anio) {
		Set<Indicador> indicadoresAplicables = new HashSet<Indicador>();
		obtenerTodos().stream().filter(ind -> ind.esAplicableA(empresa, anio))
				.forEach(ind -> indicadoresAplicables.add(ind));
		return indicadoresAplicables;
	}

	public Indicador buscarIndicador(String nombreIndicador) {
		return obtenerTodos().stream().filter(ind -> ind.seLlama(nombreIndicador)).findFirst()
				.orElseThrow(() -> new NoExisteIndicadorError("No se pudo encontrar un indicador con ese nombre."));
	}
	
	private List<Indicador> obtenerIndicadoresParseados(List<String> strIndicadores){
		Stream<Indicador> indicadores = strIndicadores.stream().map(strInd -> ParserIndicadores.parse(strInd)).filter(ind -> !existe(ind));
		return indicadores.collect(Collectors.toList());
	}

}

class NoExisteIndicadorError extends RuntimeException {NoExisteIndicadorError(String e) {super(e);}}