package dominio;

import java.time.Year;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import dominio.empresas.Empresa;
import dominio.indicadores.Indicador;

public class ModificacionListener {//SINGLETON?
	Map<String, Set<Indicador>> coleccion = new HashMap<>();
	
	public void registrar(Indicador interesado, String observado){//VER EN QUÉ MOMENTO SE REGISTRA EL INDICADOR
		Set<Indicador> interesados = coleccion.get(observado);
		if (interesados == null) interesados = new HashSet<>();
		interesados.add(interesado);
	}
	
	public void seActualizo(String entidad, Empresa empresa, Year anio){//ES EL MENSAJE QUE TENDRÍA QUE MANDAR LA CUENTA/INDICADOR QUE SE MODIFIQUE
		Set<Indicador> interesados = coleccion.get(entidad);
		if (interesados != null) interesados.forEach(interesado -> interesado.eliminarResultadosDe(empresa, anio));
	}
}
