package dominio.indicadores;

import java.time.Year;

import dominio.Usuario;
import dominio.empresas.Empresa;

public class ExpresionIndicador implements Expresion{
	
	private String nombreIndicador;
	
	public ExpresionIndicador(String nombreIndicador){
		this.nombreIndicador=nombreIndicador;		
	}
	
	public int evaluarEn(Empresa empresa, Year anio){
		Indicador indicador = Usuario.instance().obtenerIndicadorLlamado(nombreIndicador); //new RepositorioIndicadores().buscarIndicador(nombreIndicador);
		return indicador.evaluarEn(empresa,anio);
	}
}
