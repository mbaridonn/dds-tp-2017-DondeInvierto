package dominio;
import java.util.*;

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
	
}
