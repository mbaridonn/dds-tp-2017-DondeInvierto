package dominio.metodologias;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import dominio.empresas.Empresa;

@Entity
public class Metodologia {
	@Id 
	@GeneratedValue
	private Long id;
	
	private String nombre = "";
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name="metodologia_id")
	private List<CondicionTaxativa> condicionesTaxativas = new ArrayList<CondicionTaxativa>();
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name="metodologia_id")
	private List<CondicionPrioritaria> condicionesPrioritarias = new ArrayList<CondicionPrioritaria>();
	
	private Metodologia(){} //Necesario para persistir la clase
	
	public Metodologia(String nombre){
		this.nombre = nombre;
	}
	
	public List<Empresa> evaluarPara(List<Empresa> empresas){
		List<Empresa> empresasSinDatosFaltantes = (List<Empresa>) ((ArrayList<Empresa>) empresas).clone();
		//clone me obliga a acoplarme a una implementacion concreta de List, ya que List no implementa Cloneable
		empresasSinDatosFaltantes.removeAll(this.empresasConDatosFaltantes(empresas));
		List<Empresa> empresasQueCumplenTaxativas = empresasSinDatosFaltantes.stream()
				.filter(emp -> this.cumpleCondicionesTaxativas(emp))
				.collect(Collectors.toCollection(ArrayList::new));
		empresasQueCumplenTaxativas.sort((emp1, emp2)-> this.puntaje(emp2, empresasQueCumplenTaxativas).compareTo(this.puntaje(emp1, empresasQueCumplenTaxativas)));//Sort modifica la misma lista
		return empresasQueCumplenTaxativas;
	}
	
	public List<Empresa> empresasConDatosFaltantes(List<Empresa> empresas){
		List<Empresa> empresasConDatosFaltantes = new ArrayList<Empresa>();
		List<Condicion> condiciones = new ArrayList<Condicion>();
		condiciones.addAll(condicionesTaxativas);
		condiciones.addAll(condicionesPrioritarias);
		for (Empresa emp: empresas){
			for(Condicion cond: condiciones){
				if (!cond.getOperandoCondicion().sePuedeEvaluarPara(emp)) empresasConDatosFaltantes.add(emp);
			}
		}
		return empresasConDatosFaltantes;
	}
	
	public List<Empresa> empresasQueNoCumplenTaxativas(List<Empresa> empresas){ //Devuelve sólo las que no cumplen (no las que faltan datos)
		List<Empresa> empresasQueNoCumplenTaxativas = (List<Empresa>) ((ArrayList<Empresa>) empresas).clone();
		empresasQueNoCumplenTaxativas.removeAll(this.empresasConDatosFaltantes(empresas));
		empresasQueNoCumplenTaxativas.removeAll(this.evaluarPara(empresas));
		return empresasQueNoCumplenTaxativas;
	}
	
	private boolean cumpleCondicionesTaxativas(Empresa empr){
		return condicionesTaxativas.stream().allMatch(cond -> cond.laCumple(empr));
	}
	
	private Integer puntaje(Empresa empr, List<Empresa> empresas){
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
