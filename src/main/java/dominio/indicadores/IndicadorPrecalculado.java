package dominio.indicadores;

import java.time.Year;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import dominio.empresas.Empresa;

@Entity
@Table(name = "indicadoresPrecalculados")
public class IndicadorPrecalculado {
	//@OneToOne o @ManyToOne ???
	
	@Id
	@GeneratedValue
	private Long id;
	
	@OneToOne
	private Empresa empresa;
	private Year anio;
	private int valor;
	
	private IndicadorPrecalculado() {}
	
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
