package classificador;

import java.util.ArrayList;
import java.util.List;

public class Matematica {


	public float calcularDistanciaEntreDoisPontos(Ponto ponto1, Ponto ponto2){
		System.out.println("calcularDistanciaEntreDoisPontos");
		double resultado = 0;		
		resultado = Math.pow(ponto1.getAtributos().get("X") - ponto2.getAtributos().get("X"),2) + Math.pow(ponto1.getAtributos().get("Y") - ponto2.getAtributos().get("Y"),2) + Math.pow(ponto1.getAtributos().get("Z") - ponto2.getAtributos().get("Z"),2);		
		System.out.println("valorrr do calculoo="+(float)resultado);
		return (float)Math.sqrt(resultado); // retornar a raiz quadrada da soma das coordenadas.
	}
	
	/*
	public List<Ponto> normalizarPontos(List<Ponto> pontosNaoNormalizados){
		List<Ponto> pontosNormalizados = new ArrayList<Ponto>();
		for (Ponto ponto : pontosNaoNormalizados) {
			if(ponto.getVetorAtributos().size() < Ponto.maiorNumeroDeCoordenadasVetorAtributo){				
				for (int i = ponto.getVetorAtributos().size(); i < Ponto.maiorNumeroDeCoordenadasVetorAtributo ; i++) {
					ponto.vetorAtributos.add(0.0); // completando os vetores
				}					
				pontosNormalizados.add(ponto);
			}
		}
		return pontosNormalizados;	
	}
	*/
}
