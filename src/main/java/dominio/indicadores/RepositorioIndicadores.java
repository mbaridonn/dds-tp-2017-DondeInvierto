package dominio.indicadores;

import java.time.Year;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.uqbar.commons.utils.Observable;
import org.uqbarproject.jpa.java8.extras.WithGlobalEntityManager;

import dominio.empresas.Empresa;
import dominio.parser.ParserIndicadores;
import excepciones.EntidadExistenteError;

@Observable
public class RepositorioIndicadores implements WithGlobalEntityManager{
	
	private static Set<String> indicadoresPredefinidos = new HashSet(Arrays.asList(new String[] { "INGRESONETO = netooperacionescontinuas + netooperacionesdiscontinuas",
			"INDICADORDOS = cuentarara + fds", "INDICADORTRES = INGRESONETO * 10 + ebitda" , "A = 5 / 3", "PRUEBA = ebitda + 5" }));
	//POR AHORA VOY A DEJAR LOS INDICADORES PREDEFINIDOS HARDCODEADOS
	
	public static void setIndicadoresPredefinidos(Set<String> indicadoresPredefinidos) {//NECESARIO PARA CAMBIAR LOS INDICADORES PREDEFINIDOS
		RepositorioIndicadores.indicadoresPredefinidos = indicadoresPredefinidos;
	}
	
	private Set<Indicador> indicadores = new HashSet<Indicador>();

	public RepositorioIndicadores() {
		this.cargarIndicadoresPredefinidos();
		this.cargarIndicadoresEnBD();
	}
	private static RepositorioIndicadores singleton = new RepositorioIndicadores();
	public static RepositorioIndicadores getInstance() {
		return singleton;
	}
	
	private void cargarIndicadoresPredefinidos(){
		indicadoresPredefinidos.forEach(strInd -> this.agregarIndicador(strInd));//No se persisten (!)
	}
	
	private void cargarIndicadoresEnBD(){
		List<Indicador> indicadoresEnBD = (List<Indicador>) this.entityManager().createQuery("FROM Indicador").getResultList();
		indicadoresEnBD.forEach(ind -> indicadores.add(ParserIndicadores.parse(ind.getEquivalencia())));
		//En realidad estoy descartando el indicador que recupero y creando uno nuevo a partir de su representación como string
	}
	
	public void agregarIndicador(String strIndicador) {//No se persiste (!)
		Indicador nuevoIndicador = ParserIndicadores.parse(strIndicador);
		indicadores.add(nuevoIndicador);
	}
	
	public Set<Indicador> getIndicadores() {
		return indicadores;
	}
	
	public void setIndicadores(Set<Indicador> indicadores) {
		this.indicadores = indicadores;
	}
	
	public void guardarIndicador(String strIndicador){
		Indicador nuevoIndicador = ParserIndicadores.parse(strIndicador);
		if(this.existeIndicador(nuevoIndicador.getNombre())){
			throw new EntidadExistenteError("Ya existe un indicador con el nombre " + strIndicador);
		}
		this.entityManager().persist(nuevoIndicador);//La transacción se tiene que agregar donde se envíe el mensaje (!)
		agregarIndicador(strIndicador);//Cuando se guarda un indicador también se carga
	}
	
	public Set<Indicador> todosLosIndicadoresAplicablesA(Empresa empresa){
		Set<Indicador> indicadoresAplicables = new HashSet<Indicador>();
		Set<Year> aniosDeCuentas = empresa.aniosDeLosQueTieneCuentas();
		aniosDeCuentas.forEach(anio -> indicadoresAplicables.addAll(this.indicadoresAplicablesA(empresa, anio)));
		return indicadoresAplicables;
	}
	
	public Set<Indicador> indicadoresAplicablesA(Empresa empresa, Year anio){
		Set<Indicador> indicadoresAplicables = new HashSet<Indicador>();
		indicadores.stream().filter(ind -> ind.esAplicableA(empresa, anio)).forEach(ind -> indicadoresAplicables.add(ind));
		return indicadoresAplicables;
	}

	public Indicador buscarIndicador(String nombreIndicador) {
		return indicadores.stream().filter(ind -> ind.seLlama(nombreIndicador)).findFirst()
				.orElseThrow(() -> new NoExisteIndicadorError("No se pudo encontrar una indicador con ese nombre."));
	}
	
	public void guardarIndicadores(List<String> indicadores){
		indicadores.forEach(strIndicador -> this.guardarIndicador(strIndicador));
	}
	
	public boolean existeIndicador(String nombreIndicador){
		List<Indicador> indicadoresConMismoNombre = this.entityManager().createQuery("FROM Indicador where nombre = '" + nombreIndicador + "'").getResultList();
		return !indicadoresConMismoNombre.isEmpty() || this.indicadores.stream().anyMatch(ind -> ind.getNombre().equalsIgnoreCase(nombreIndicador));
	}
}

class NoExisteIndicadorError extends RuntimeException {NoExisteIndicadorError(String e) {super(e);}}