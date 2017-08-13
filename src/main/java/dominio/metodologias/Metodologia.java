package dominio.metodologias;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.stream.Collectors;

import dominio.Empresa;

public class Metodologia {
	private String nombre = "";
	private ArrayList<CondicionTaxativa> condicionesTaxativas = new ArrayList<CondicionTaxativa>();
	private ArrayList<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<CondicionPrioritaria>();
	
	public Metodologia(String nombre){
		this.nombre = nombre;
	}
	
	public ArrayList<Empresa> evaluarPara(ArrayList<Empresa> empresas){
		ArrayList<Empresa> empresasSinDatosFaltantes = (ArrayList<Empresa>) empresas.clone();
		empresasSinDatosFaltantes.removeAll(this.empresasConDatosFaltantes(empresas));
		ArrayList<Empresa> empresasQueCumplenTaxativas = empresasSinDatosFaltantes.stream()
				.filter(emp -> this.cumpleCondicionesTaxativas(emp))
				.collect(Collectors.toCollection(ArrayList::new));
		empresasQueCumplenTaxativas.sort((emp1, emp2)-> this.puntaje(emp1, empresas).compareTo(this.puntaje(emp2, empresas)));//Sort modifica la misma lista
		return empresasQueCumplenTaxativas;
	}
	
	public ArrayList<Empresa> empresasConDatosFaltantes(ArrayList<Empresa> empresas){
		ArrayList<Empresa> empresasConDatosFaltantes = new ArrayList<Empresa>();
		ArrayList<Condicion> condiciones = new ArrayList<Condicion>();
		condiciones.addAll(condicionesTaxativas);
		condiciones.addAll(condicionesPrioritarias);
		for (Empresa emp: empresas){
			for(Condicion cond: condiciones){
				if (!cond.getOperandoCondicion().sePuedeEvaluarPara(emp)) empresasConDatosFaltantes.add(emp);
			}
		}
		return empresasConDatosFaltantes;
	}
	
	public ArrayList<Empresa> empresasQueNoCumplenTaxativas(ArrayList<Empresa> empresas){ //Devuelve sólo las que no cumplen (no las que faltan datos)
		ArrayList<Empresa> empresasQueNoCumplenTaxativas = (ArrayList<Empresa>) empresas.clone();
		empresasQueNoCumplenTaxativas.removeAll(this.empresasConDatosFaltantes(empresas));
		empresasQueNoCumplenTaxativas.removeAll(this.evaluarPara(empresas));
		return empresasQueNoCumplenTaxativas;
	}
	
	private boolean cumpleCondicionesTaxativas(Empresa empr){
		return condicionesTaxativas.stream().allMatch(cond -> cond.laCumple(empr));
	}
	
	private Integer puntaje(Empresa empr, ArrayList<Empresa> empresas){
		int puntaje = 0;
		ListIterator<CondicionPrioritaria> iteradorCond = condicionesPrioritarias.listIterator();
		ListIterator<Empresa> iteradorEmpr = empresas.listIterator();
		while(iteradorCond.hasNext()){
			CondicionPrioritaria cond = iteradorCond.next();
			while(iteradorEmpr.hasNext()){
				Empresa empr2 = iteradorEmpr.next();
				if(cond.esMejorQue(empr, empr2)) puntaje++;
			}
		}
		return puntaje;
	}
	
	public void agregarCondicionTaxativa(CondicionTaxativa cond){
		condicionesTaxativas.add(cond);
	}
	
	public void agregarCondicionPrioritaria(CondicionPrioritaria cond){
		condicionesPrioritarias.add(cond);
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public boolean esMetodologiaValida(){
		return !nombre.isEmpty() && !condicionesTaxativas.isEmpty() && !condicionesPrioritarias.isEmpty();
	}
	
	@Override
	public String toString(){
		return nombre;
	}
	
}
