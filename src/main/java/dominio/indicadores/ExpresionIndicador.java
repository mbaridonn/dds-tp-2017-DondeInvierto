package dominio.indicadores;

import java.time.Year;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class ExpresionIndicador extends Expresion{
	
	private String nombreIndicador;
	
	private ExpresionIndicador(){} //Necesario para persistir la clase
	
	public ExpresionIndicador(String nombreIndicador){
		this.nombreIndicador=nombreIndicador;		
	}
	
	public int evaluarEn(Empresa empresa, Year anio){
		Indicador indicador = RepositorioIndicadores.getInstance().buscarIndicador(nombreIndicador);
		return indicador.evaluarEn(empresa,anio);
	}
}
