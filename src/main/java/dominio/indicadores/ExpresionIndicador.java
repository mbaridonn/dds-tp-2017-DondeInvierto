package dominio.indicadores;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class ExpresionIndicador extends Expresion{
	
	private String nombreIndicador;
	
	public ExpresionIndicador(String nombreIndicador){
		this.nombreIndicador=nombreIndicador;		
	}
	
	public int evaluarEn(Empresa empresa, String anio){
		Indicador indicador = RepositorioIndicadores.getInstance().buscarIndicador(nombreIndicador);
		return indicador.evaluarEn(empresa,anio);
	}
}
