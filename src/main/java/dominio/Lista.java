package dominio;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lista<Object> extends ArrayList<Object> {
	public Object anyOne(){
		Random randomGenerator = new Random();
		int index = randomGenerator.nextInt(this.size());
		return this.get(index);
	}
	public Lista<Object> reverse(){
		Lista<Object> listaRetornar = new Lista<Object>();
		for(int i = this.size()-1 ; i >= 0 ; i--){
			listaRetornar.add(this.get(i));
		}
		return listaRetornar;
	}
	public Object head(){
		return this.get(0);
	}
	public Object last(){
		int indexUltimo = this.size()-1;
		return this.get(indexUltimo);
	}
	public Set<Object> asSet(){
		Set<Object> setARetornar = new HashSet<Object>();
		this.forEach(elem->setARetornar.add(elem));
		return setARetornar;
	}
	public Lista<Object> take(int cantidad){
		Lista<Object> listaARetornar = new Lista<Object>();
		if(cantidad>this.size()){
			return this;
		}
		for(int i=0;i<cantidad;i++){
			listaARetornar.add(this.get(i));
		}
		return listaARetornar;
	}
	public int count(Predicate<Object> condicion){
		int cantidadQueCumple = 0;
		for(int i = 0; i < this.size() ; i++){
			if(condicion.test(this.get(i))){
				cantidadQueCumple += 1;
			}
		}
		return cantidadQueCumple;
	}
	public int sum(Function<Object,Integer> consumer){
		int sumaTotal = 0;
		for(int i = 0; i < this.size(); i++){
			sumaTotal += consumer.apply(this.get(i));
		}
		return sumaTotal;
	}
	public Lista<Object> union(Lista<Object> otraLista){
		Lista<Object> listaRetornar = new Lista<Object>();
		this.forEach(e -> listaRetornar.add(e));
		otraLista.forEach(e-> listaRetornar.add(e));
		return listaRetornar;
	}
	
}
