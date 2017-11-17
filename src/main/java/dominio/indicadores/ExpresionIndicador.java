package dominio.indicadores;

import java.time.Year;
import java.util.Arrays;
import java.util.List;

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
	
	@Override
	public List<String> dependencias() {
		return Arrays.asList(nombreIndicador);
	}
	
}
