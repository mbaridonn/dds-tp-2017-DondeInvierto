package dominio.indicadores;

import java.time.Year;

import dominio.empresas.Empresa;
import dominio.usuarios.Usuario;

public class ExpresionIndicador implements Expresion{
	
	private String nombreIndicador;
	
	public ExpresionIndicador(String nombreIndicador){
		this.nombreIndicador=nombreIndicador;		
	}
	
	public int evaluarEn(Empresa empresa, Year anio){
		Indicador indicador = Usuario.activo().buscarIndicador(nombreIndicador);
		return indicador.evaluarEn(empresa,anio);
	}
	
}
