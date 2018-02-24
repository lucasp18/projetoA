package classificador;

import java.util.ArrayList;
import java.util.List;

public class Svm {
	
	private Matematica matematica;
	private List<Ponto> pontosDaClasseSim;
	private List<Ponto> pontosDaClasseNao;
	private List<Ponto> pontosDaClasseDesconhecidos;
	
	public Svm(Matematica matematica) {		
		this.matematica = matematica;
	}
	
	public float encontrarAhMenorDistancia(){
		Ponto p1 = null;
		float menorDistanciaP1P2 = 0, distanciaP1P2 = 0;
		for (int i = 0; i < pontosDaClasseDesconhecidos.size(); i++) {
			p1 = pontosDaClasseDesconhecidos.get(i);			
			for (int j = i+1; j < pontosDaClasseDesconhecidos.size(); j++) {
				distanciaP1P2 = this.matematica.calcularDistanciaEntreDoisPontos(p1, pontosDaClasseDesconhecidos.get(j));
				if( distanciaP1P2 <= menorDistanciaP1P2 || menorDistanciaP1P2 == 0 ){
					menorDistanciaP1P2 = distanciaP1P2;					
				}
			}							
		}
		return menorDistanciaP1P2;
	}
		
	public List<Integer> encontrarAhMenorDistanciaLista(){
		Ponto p1 = null;
		double menorDistanciaP1P2 = 0, distanciaP1P2 = 0;
		List<Integer> posMenoresPontos = new ArrayList<Integer>(); 
		for (int i = 0; i < pontosDaClasseDesconhecidos.size(); i++) {
			p1 = pontosDaClasseDesconhecidos.get(i);
			for (int j = i+1; j < pontosDaClasseDesconhecidos.size(); j++) {
				distanciaP1P2 = this.matematica.calcularDistanciaEntreDoisPontos(p1, pontosDaClasseDesconhecidos.get(j));
				if( distanciaP1P2 <= menorDistanciaP1P2 || menorDistanciaP1P2 == 0 ){
					menorDistanciaP1P2 = distanciaP1P2; 					
					posMenoresPontos.clear();
					posMenoresPontos.add(i);
					posMenoresPontos.add(j);
				}
			}							
		}
		return posMenoresPontos;
	}
		
	public void calcularEquacaoDaReta(Ponto vetorDirecao, Ponto ponto){
		
	}
	
	private Ponto calcularVetorDirecaoPerpendicular(Ponto vetorDirecao){
		Ponto vetorPerpendicular =  new Ponto();
		vetorPerpendicular.addValorCoordenada("X", vetorDirecao.valorDaCoordenada("Z"));
		vetorPerpendicular.addValorCoordenada("Y", Float.valueOf("0"));
		vetorPerpendicular.addValorCoordenada("Z", (-1)*vetorDirecao.valorDaCoordenada("X"));		
		return vetorPerpendicular; 
	}
	
	
	private Ponto calculoVetorDirecao(Ponto p1, Ponto p2){
		Ponto vetorDirecao = new Ponto();  
		vetorDirecao.addValorCoordenada("X",p2.getAtributos().get("X") - p1.getAtributos().get("X"));
		vetorDirecao.addValorCoordenada("Y",p2.getAtributos().get("Y") - p1.getAtributos().get("Y"));
		vetorDirecao.addValorCoordenada("Z",p2.getAtributos().get("Z") - p1.getAtributos().get("Z"));
		return vetorDirecao;
	}
	
	private Ponto calculoPontoMedio(Ponto p1, Ponto p2){
		Ponto pontoMedio = new Ponto();
		pontoMedio.addValorCoordenada("X", (p1.getAtributos().get("X")-p2.getAtributos().get("X"))/2);
		pontoMedio.addValorCoordenada("Y", (p1.getAtributos().get("Y")-p2.getAtributos().get("Y"))/2);
		pontoMedio.addValorCoordenada("Z", (p1.getAtributos().get("Z")-p2.getAtributos().get("Z"))/2);
		return pontoMedio;
	}
		
	public void adicionarPontoClasseSim(Ponto ponto){
		this.pontosDaClasseSim.add(ponto);
	}
	
	public void adicionarPontoClasseNao(Ponto ponto){
		this.pontosDaClasseNao.add(ponto);
	}

	public List<Ponto> getPontosDaClasseDesconhecidos() {
		return pontosDaClasseDesconhecidos;
	}

	public void setPontosDaClasseDesconhecidos(List<Ponto> pontosDaClasseDesconhecidos) {
		this.pontosDaClasseDesconhecidos = pontosDaClasseDesconhecidos;
	}
	
	
}
