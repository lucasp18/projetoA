package classificador;

import java.util.LinkedHashMap;
import java.util.Map;

public class Ponto {
	
	String classe;
	Map<String,Float> atributos; //conhecido em algumas literaturas como vetor x ou vetor de features
	static Integer maiorNumeroDeCoordenadasVetorAtributo;
	
	public Ponto(){
		atributos = new LinkedHashMap<String,Float>();
	}
		
	public void setClasse(String vClasse){
		this.classe = vClasse;
	}
	
	public void addValorCoordenada(String eixo,Float valor){
		atributos.put(eixo.toUpperCase(), valor);
	}

	public Map<String, Float> getAtributos() {
		return atributos;
	}

	public void setAtributos(Map<String, Float> atributos) {
		this.atributos = atributos;
	}

	public String getClasse() {
		return classe;
	}
	
	public float valorDaCoordenada(String coordenada){
		return this.atributos.get(coordenada.toUpperCase());		
	}
	
	
}
