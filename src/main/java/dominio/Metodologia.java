package dominio;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.stream.Collectors;

public class Metodologia {
	private ArrayList<CondicionTaxativa> condicionesTaxativas = new ArrayList<CondicionTaxativa>();
	private ArrayList<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<CondicionPrioritaria>();
	
	public ArrayList<Empresa> evaluarPara(ArrayList<Empresa> empresas){//FALTA CATCHEAR LAS QUE NO CUMPLEN TAXATIVAS Y LAS QUE NO TIENEN DATOS
		ArrayList<Empresa> empresasQueCumplenTaxativas = empresas.stream()
				.filter(emp -> this.cumpleCondicionesTaxativas(emp))
				.collect(Collectors.toCollection(ArrayList::new));
		empresasQueCumplenTaxativas.sort((emp1, emp2)-> this.puntaje(emp1, empresas).compareTo(this.puntaje(emp2, empresas)));//Sort modifica la misma lista
		return empresasQueCumplenTaxativas;
	}
	
	private boolean cumpleCondicionesTaxativas(Empresa empr){
		return condicionesTaxativas.stream().allMatch(cond -> cond.laCumple(empr));
	}
	
	private Integer puntaje(Empresa empr, ArrayList<Empresa> empresas){//ASQUEROSAMENTE PROCEDURAL, PERO ES LA IDEA DE LO QUE TIENE QUE HACER
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
}
