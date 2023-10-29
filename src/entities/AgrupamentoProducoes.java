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

				lastUsed = producao;
				return producao;
			}
		}

		return null;
	}

	public int getTamanho() {
		return elementos.size();
	}

	// Dentro da classe AgrupamentoProducoes
	public void imprimirProducaoDeElementos() {
		System.out.print("printando um dos agrupamentos");
		for (Producao producao : elementos) {
			producao.imprimirProducao();
		}
	}

}