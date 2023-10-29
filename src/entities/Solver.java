package entities;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {
	ArrayList<Character> terminais = new ArrayList<Character>();
	ArrayList<Character> geradores = new ArrayList<Character>();
	ArrayList<Producao> producoesList = new ArrayList<>();
	ArrayList<Producao> prodVazias = new ArrayList<>();
	char esquerda;
	char direita;
	int glueOrGlud;
	String palavraSerTestada; // essa eh a palavra final, ela eh quebrada em partes compondo a
								// palavraSerTestadaArray, assim posso verificar facilmente.
	private char[] palavraSerTestadaArray;
	char SimboloPartida;

	public Solver(ArrayList<Producao> producoesList, char SimboloPartida, char[] palavraSerTestadaArray,
			String palavraSerTestada, ArrayList<Producao> prodVazias, int glueOrGlud) {
		this.producoesList = producoesList;
		this.SimboloPartida = SimboloPartida;
		this.palavraSerTestada = palavraSerTestada;
		this.palavraSerTestadaArray = palavraSerTestadaArray;
		this.prodVazias = prodVazias;
		this.glueOrGlud = glueOrGlud;
	}

	public ArrayList<Producao> encontrarProducoesParaNo(char no) {
		ArrayList<Producao> producoesCorrespondentes = new ArrayList<>();

		for (Producao producao : producoesList) {
			if (producao.getGerador() == no) {
				producoesCorrespondentes.add(producao);
			}
		}

		// Marca todas as produções correspondentes como não testadas
		for (Producao producao : producoesCorrespondentes) {
			producao.setTestada(false);
		}

		return producoesCorrespondentes;
	}

	public String encontrarProducaoComTerminaisAsteriscoEspecifico(ArrayList<Producao> producoesList,
			char geradorDesejado) {
		for (Producao producao : producoesList) {
			if (producao.getGerador() == geradorDesejado && producao.getTerminalEsquerda() == '*'
					&& producao.getTerminalDireita() == '*') {
				return producao.getGerador() + ">" + "**"; // Retorna a produção no formato desejado
			}
		}
		return null; // Se nenhuma produção corresponder, retorna null
	}

	public void doIt() {
		Stack<AgrupamentoProducoes> stackProducoes = new Stack<>();
		geradores.add(SimboloPartida);
		int auxPalavra = 0;
		if (glueOrGlud == 0) {
			auxPalavra = palavraSerTestadaArray.length - 1;
		}
		String palavraSendoProduzida = null;
		AgrupamentoProducoes agrupamento;
		Producao producaoNaoTestada;
		ArrayList<Producao> auxTestezada = encontrarProducoesParaNo(geradores.get(geradores.size() - 1));
		agrupamento = new AgrupamentoProducoes(auxTestezada); // Criar uma nova instância de AgrupamentoProducoes

		ArrayList<Character> palavraSup = new ArrayList<>();

		producaoNaoTestada = agrupamento.encontrarProducaoNaoTestada();

		while (!palavraSerTestada.equals(palavraSendoProduzida)) {
			// producaoNaoTestada.imprimirProducao();
			char[] charArray = new char[terminais.size()];
			for (int i = 0; i < terminais.size(); i++) {
				charArray[i] = terminais.get(i);
			}
			palavraSendoProduzida = new String(charArray);

			if (producaoNaoTestada != null) {

				direita = producaoNaoTestada.getTerminalDireita();
				esquerda = producaoNaoTestada.getTerminalEsquerda();

				if (esquerda == '$') {
					System.out.println("detectou que eh pulavel essa desgraça");
					System.out.println("vai olhar p qual: " + geradores.get(geradores.size() - 1));
					geradores.add(direita);

					stackProducoes.push(agrupamento);

					auxTestezada = encontrarProducoesParaNo(geradores.get(geradores.size() - 1));
					System.out.println("ta pegando?" + geradores.get(geradores.size() - 1));
					agrupamento = new AgrupamentoProducoes(auxTestezada);
					// agrupamento.imprimirProducaoDeElementos();// remover isto dps
					producaoNaoTestada = agrupamento.encontrarProducaoNaoTestada();
					direita = producaoNaoTestada.getTerminalDireita();
					esquerda = producaoNaoTestada.getTerminalEsquerda();
					geradores.add(direita);
					terminais.add(esquerda); // tem que colocar o negocio do $$$$ aqui pra evitar recursividade
					palavraSup.add(esquerda);
					// se pa ta aq o PROBLEMAO
					// producaoNaoTestada = agrupamento.encontrarProducaoNaoTestada();

				} else {

					geradores.add(direita);
					terminais.add(esquerda);

					palavraSup.add(esquerda);
					stackProducoes.push(agrupamento);

				}

				if (palavraSerTestadaArray[auxPalavra] != terminais.get(terminais.size() - 1)) {

					geradores.remove(geradores.size() - 1);
					terminais.remove(terminais.size() - 1);
					palavraSup.remove(palavraSup.size() - 1);
					agrupamento = stackProducoes.peek();
					producaoNaoTestada = agrupamento.encontrarProducaoNaoTestada();

				} else {
					if (esquerda != '$') {
						if (glueOrGlud == 0) {
							auxPalavra--; // ele ta indo + 1 aq
						} else {
							auxPalavra++;
						}
					}

					auxTestezada = encontrarProducoesParaNo(geradores.get(geradores.size() - 1));

					agrupamento = new AgrupamentoProducoes(auxTestezada);
					// agrupamento.imprimirProducaoDeElementos();// remover isto dps

					// se pa ta aq o PROBLEMAO
					producaoNaoTestada = agrupamento.encontrarProducaoNaoTestada();
				}

				if (glueOrGlud == 0) {
					charArray = new char[terminais.size()];
					for (int i = terminais.size() - 1, j = 0; i >= 0; i--, j++) {
						charArray[j] = terminais.get(i);
					}

				} else {
					charArray = new char[terminais.size()];
					for (int i = 0; i < terminais.size(); i++) {
						charArray[i] = terminais.get(i);
					}
				}

				palavraSendoProduzida = new String(charArray);

			} else {

				// MENOS UM DESSE PRA VOLTAR AO ORIGINAL

				if (esquerda == '$') {
					geradores.remove(geradores.size() - 1);

					// mudar para o array mais facil

					System.out.println("removeu uma producao na pilha pois ja tinha testado todas possibilidades");
					stackProducoes.pop();

					agrupamento = stackProducoes.peek();
				} else {
					geradores.remove(geradores.size() - 1);
					terminais.remove(terminais.size() - 1);
					System.out.println("removeu uma producao na pilha pois ja tinha testado todas possibilidades");
					palavraSup.remove(palavraSup.size() - 1);
					stackProducoes.pop();

					if (glueOrGlud == 0) {
						auxPalavra++;
					} else {
						auxPalavra--;
					}

					agrupamento = stackProducoes.peek();
				}

			}

		}

		char[] charArray;
		if (glueOrGlud == 0) {
			charArray = new char[terminais.size()];
			for (int i = terminais.size() - 1, j = 0; i >= 0; i--, j++) {
				charArray[j] = terminais.get(i);
			}

		} else {
			charArray = new char[terminais.size()];
			for (int i = 0; i < terminais.size(); i++) {
				charArray[i] = terminais.get(i);
			}
		}

		palavraSendoProduzida = new String(charArray);

		if (palavraSerTestada.equals(palavraSendoProduzida)) {
			// palavraSup.add(terminais.get(terminais.size() - 1)); ATIVAR DPS SE PRECISAR
			String producaoComAsteriscos = encontrarProducaoComTerminaisAsteriscoEspecifico(prodVazias,
					geradores.get(geradores.size() - 1));
			// System.out.println(geradores.get(geradores.size() - 1));

			if (producaoComAsteriscos != null) {

				System.out.println("A palavra foi produzida com sucesso!");
				if (glueOrGlud == 1) {
					int aux = 0;
					for (int i = 0; i < geradores.size() - 1; i++) {
						if (aux < palavraSup.size()) {
							System.out.println(geradores.get(i) + ">" + palavraSup.get(aux) + geradores.get(i + 1));
							aux++;
						}

					}
				} else {

					int aux = 0;
					for (int i = 0; i < geradores.size() - 1; i++) {
						if (aux < palavraSup.size()) {
							System.out.println(geradores.get(i) + ">" + geradores.get(i + 1) + palavraSup.get(aux));
							aux++;
						}

					}

				}

				System.out.println(producaoComAsteriscos);
			} else {
				System.out.println("deu algum b.o pprt");
			}
		} else {
			System.out.println("deu algum b.o pprt");
		}

	}
}
