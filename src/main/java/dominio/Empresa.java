package dominio;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Empresa {
	private String nombre;
	private ArrayList<Cuenta> cuentas;
	
	public Empresa(String nombre,ArrayList<Cuenta> cuentas){
		this.nombre = nombre;
		this.cuentas = cuentas;
	}
	
	public int cantidadDeCuentasQuePosee(){
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
		resultadosTotales.forEach(c -> c.mostrarDatos());
		return resultadosTotales;
	}
	
	public ArrayList<Cuenta> resultadosIndicadoresSegunAnio(Set<Indicador> indicadores, String anio){
		ArrayList<Cuenta> resultados = new ArrayList<Cuenta>();
		ArrayList<Indicador> indicadoresAplicables = new ArrayList<Indicador>();
		indicadores.stream().filter(ind -> ind.esAplicableA(this, anio)).forEach(ind -> indicadoresAplicables.add(ind));
		indicadoresAplicables.forEach(ind -> resultados.add(new Cuenta(anio,ind.getNombre(),ind.evaluarEn(this, anio))));
		return resultados;
	}
	
	public String getNombre(){
		return nombre;
	}

	public ArrayList<Cuenta> getCuentas() {
		return cuentas;
	}
	
	public int getValorCuenta(String tipoCuenta, String anio){
		Cuenta cuentaBuscada = cuentas.stream().filter(cuenta -> cuenta.esDeTipo(tipoCuenta) && cuenta.esDeAnio(anio)).findFirst().orElseThrow(() -> new NoExisteCuentaError("No se pudo encontrar una cuenta en ese año para esta empresa."));
		//El findFirst podría enmascarar el caso erróneo en el que haya dos cuentas del mismo tipo con valores distintos en el mismo año
		return cuentaBuscada.getValor();
	}
	
	@Override
	public String toString(){ //Es necesario para que el Selector muestre solo el nombre de la empresa
		return nombre;
	}
	
}

class NoExisteCuentaError extends RuntimeException{NoExisteCuentaError(String e){super(e);}}
