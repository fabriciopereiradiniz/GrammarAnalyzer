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
				// System.out.println("Achou o caractere desejado");

				// producao.imprimirProducao();// DEBUG RETIRAR DEPOIS
				lastUsed = producao;
				return producao;
			}
		}
		// for (Producao producao : elementos) {
		// producao.imprimirProducao();
		// }
		// System.out.println("Retornou null, se tudo ocorreu bem é porque não tem mais
		// produções não testadas de ");
		// for (Producao producao : elementos) {
		// producao.imprimirProducao();
		// }
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