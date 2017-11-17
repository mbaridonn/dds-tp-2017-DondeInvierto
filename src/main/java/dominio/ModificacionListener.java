package dominio;

import java.time.Year;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dominio.empresas.Empresa;
import dominio.indicadores.Indicador;

public class ModificacionListener {
	Map<String, Set<Indicador>> registro = new HashMap<>();
	
	private static ModificacionListener singleton;
	
	public static ModificacionListener getInstance() {
		if(singleton == null) singleton = new ModificacionListener();
		return singleton;
	}
	
	private ModificacionListener() {}
	
	public void registrar(Indicador interesado, String observado){
		Set<Indicador> interesados = registro.get(observado);
		if (interesados == null) interesados = new HashSet<>();
		interesados.add(interesado);
		//registro.put(observado, interesados);//NECESARIO???
	}
	
	public void seActualizo(String entidad, Empresa empresa, Year anio){
		Set<Indicador> interesados = registro.get(entidad);
		if (interesados != null) interesados.forEach(interesado -> interesado.eliminarResultadosDe(empresa, anio));
	}
}
