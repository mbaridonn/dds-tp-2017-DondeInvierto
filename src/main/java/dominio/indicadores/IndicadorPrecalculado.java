package dominio.indicadores;

import java.time.Year;

import javax.persistence.Entity;

import dominio.empresas.Empresa;

@Entity
public class IndicadorPrecalculado {
	//@OneToOne o @ManyToOne ???
	private Empresa empresa;
	private Year anio;
	private int valor;
	
	public IndicadorPrecalculado(Empresa empresa, Year anio, int valor) {
		this.empresa = empresa;
		this.anio = anio;
		this.valor = valor;
	}
	
	public boolean esDe(Empresa empresa, Year anio) {
		return this.empresa.equals(empresa) && this.anio.equals(anio);
	}
	
	public int getValor() {
		return valor;
	}
}
