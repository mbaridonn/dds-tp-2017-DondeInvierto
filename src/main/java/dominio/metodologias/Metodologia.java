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
	
	public ArrayList<Empresa> evaluarPara(ArrayList<Empresa> empresas){//FALTA CATCHEAR LAS QUE NO CUMPLEN TAXATIVAS Y LAS QUE NO TIENEN DATOS (NoExisteCuentaError)
		ArrayList<Empresa> empresasQueCumplenTaxativas = empresas.stream()
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
	
	//IDEA: YA TENGO LISTA DE EMP CON DATOS FALTANTES. OBTENER LAS QUE NO CUMPLEN TAXATIVAS (EXCLUYENDO LAS DE DATOS FALTANTES)
	//DESPUÉS EVALUAR LAS QUE NO ESTÁN EN LAS DOS LISTAS ANTERIORES
	
	//IDEA 2: YA TENGO LISTA DE EMP CON DATOS FALTANTES. OBTENER LAS QUE 'SÍ' CUMPLEN TAXATIVAS (EXCLUYENDO LAS DE DATOS FALTANTES)
	//DESPUÉS OBTENER LAS QUE NO CUMPLEN (SON LAS QUE NO ESTÁN EN LAS DOS LISTAS ANTERIORES)
	
	/*public ArrayList<Empresa> empresasQueNoCumplenTaxativas(ArrayList<Empresa> empresas){ DEJÉ ACÁ. PROBLEMA: REMOVEALL NO DEVUELVE OTRA LISTA
		ArrayList<Empresa> empresasSinDatosFaltantes = empresas.removeAll(this.empresasConDatosFaltantes(empresas));
		return empresasSinDatosFaltantes.stream()
				.filter(emp -> !this.cumpleCondicionesTaxativas(emp))
				.collect(Collectors.toCollection(ArrayList::new));
	}*/
	
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
