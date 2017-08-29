package dominio.empresas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import dominio.indicadores.Indicador;
import excepciones.NoExisteCuentaError;

@Entity
public class Empresa {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	private String nombre;
	
	/*@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name="cuenta_id")*/
	@Transient//VER CÓMO PERSISTIR CUENTAS
	private ArrayList<Cuenta> cuentas;
	
	private Empresa(){} //Necesario para persistir la clase
	
	public Empresa(String nombre,ArrayList<Cuenta> cuentas){
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public int cantidadDeCuentas(){
		return cuentas.size();
	}
	
	public boolean seLlama(String nombre){
		return this.nombre.equals(nombre);
	}
	
	public void registrarCuenta(Cuenta cuenta){
		cuentas.add(cuenta);
	}
	
	public Set<String> aniosDeLosQueTieneCuentas(){
		Set<String> anios = new HashSet<String>();
		cuentas.forEach(cuenta -> anios.add(cuenta.getAnio()));
		return anios;
	}
	
	public ArrayList<Cuenta> resultadosIndicadoresTotales(Set<Indicador> indicadores){
		Set<String> anios = this.aniosDeLosQueTieneCuentas();
		ArrayList<Cuenta> resultadosTotales = new ArrayList<Cuenta>();
		anios.forEach(anio -> resultadosTotales.addAll(this.resultadosIndicadoresSegunAnio(indicadores,anio)));
		return resultadosTotales;
	}
	
	public ArrayList<Cuenta> resultadosIndicadoresSegunAnio(Set<Indicador> indicadores, String anio){
		ArrayList<Cuenta> resultados = new ArrayList<Cuenta>();
		ArrayList<Indicador> indicadoresAplicables = new ArrayList<Indicador>();
		indicadores.stream().filter(ind -> ind.esAplicableA(this, anio)).forEach(ind -> indicadoresAplicables.add(ind));
		indicadoresAplicables.forEach(ind -> resultados.add(new Cuenta(anio,ind.getNombre(),ind.evaluarEn(this, anio))));
		return resultados;
	}
	
	public int getAnioDeCreacion(){//El anio de creación se obtiene a partir de la cuenta más antigua
		return cuentas.stream().mapToInt(cuenta-> Integer.parseInt(cuenta.getAnio())).min()
				.orElseThrow(() -> new NoExisteCuentaError("La empresa no tiene ninguna cuenta, por lo que no se puede calcular el año de creación."));
	}
	
	public String getNombre(){
		return nombre;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public int getValorCuenta(String tipoCuenta, String anio){
		Cuenta cuentaBuscada = cuentas.stream().filter(cuenta -> cuenta.esDeTipo(tipoCuenta) && cuenta.esDeAnio(anio)).findFirst().orElseThrow(() -> new NoExisteCuentaError("No se pudo encontrar la cuenta " + tipoCuenta + " en el año " + anio + " para la empresa " + this.getNombre() + "."));
		//El findFirst podría enmascarar el caso erróneo en el que haya dos cuentas del mismo tipo con valores distintos en el mismo año
		return cuentaBuscada.getValor();
	}
	
	@Override
	public String toString(){ //Es necesario para que el Selector muestre solo el nombre de la empresa
		return nombre;
	}
	
}