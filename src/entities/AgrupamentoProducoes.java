package entities;

import java.util.ArrayList;

public class AgrupamentoProducoes {
	ArrayList<Producao> elementos = new ArrayList<>();
	Producao lastUsed;
	public AgrupamentoProducoes(ArrayList<Producao> elementos) {
		this.elementos = elementos;
	}
	// aqui eu vou "puxando as producoes e dando flag como testadas"
	public Producao encontrarProducaoNaoTestada() {
		for (Producao producao : elementos) {
			if (!producao.isTestada()) {
				producao.setTestada(true); 
				System.out.println("Achou o caractere desejado");

				producao.imprimirProducao();// DEBUG RETIRAR DEPOIS
				lastUsed = producao;
				return producao; 
			}
		}
		System.out.println("Retornou null, se tudo ocorreu bem é porque não tem mais produções não testadas.");
		for (Producao producao : elementos) {
			producao.imprimirProducao();
		}
		return null; 
	}

	public int getTamanho() {
		return elementos.size();
	}

}