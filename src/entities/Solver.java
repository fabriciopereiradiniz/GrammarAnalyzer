package entities;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {
	ArrayList<Character> terminais = new ArrayList<Character>();// usados pra controle
	ArrayList<Character> geradores = new ArrayList<Character>();// usados pra controle
	ArrayList<Producao> producoesList = new ArrayList<>();// lista de producoes
	ArrayList<Producao> prodVazias = new ArrayList<>();// lista de producoes do tipo A>**
	ArrayList<Producao> prodFinalista = new ArrayList<>(); // lista de producoes do tipo S>a#
	char esquerda;
	char direita;
	boolean verificador = false;// usado pra dar break
	int glueOrGlud;
	String palavraSerTestada; // essa eh a palavra final, ela eh quebrada em partes compondo a
								// palavraSerTestadaArray, assim posso verificar facilmente.
	private char[] palavraSerTestadaArray;
	char SimboloPartida;

	public Solver(ArrayList<Producao> producoesList, char SimboloPartida, char[] palavraSerTestadaArray,
			String palavraSerTestada, ArrayList<Producao> prodVazias, int glueOrGlud,
			ArrayList<Producao> prodFinalista) {
		this.producoesList = producoesList;
		this.SimboloPartida = SimboloPartida;
		this.palavraSerTestada = palavraSerTestada;
		this.palavraSerTestadaArray = palavraSerTestadaArray;
		this.prodVazias = prodVazias;
		this.glueOrGlud = glueOrGlud;
		this.prodFinalista = prodFinalista;
	}

	public char obterUltimaLetra(String palavraSerTestada) {
		if (palavraSerTestada != null && !palavraSerTestada.isEmpty()) {
			return palavraSerTestada.charAt(palavraSerTestada.length() - 1);
		} else {
			return ' ';
		}
	}

	public ArrayList<Producao> encontrarProducoesParaNo(char no) {// nome autoexplicativo
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

	public Producao encontrarProducaoPorGeradorETerminal(char gerador, char terminalEsquerda, // nome autoexplicativo
			ArrayList<Producao> listaProducoes) {
		for (Producao producao : listaProducoes) {
			if (producao.getGerador() == gerador && producao.getTerminalEsquerda() == terminalEsquerda) {
				return producao; // Encontrou uma produção que corresponde aos parâmetros
			}
		}
		return null; // Não encontrou correspondência
	}

	public void doIt() {// aqui que a magica acontece
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

			palavraSendoProduzida = new String(charArray);// vai atualizando a palavra( tem varios desse no loop pra
															// evitar bugs )

			if (glueOrGlud == 1) { // aqui eu vejo se já consigo terminar a palavra "antecipadamente" utilizando
									// producoes do tipo S>a, ou seja, sem variaveis.
				if (auxPalavra == palavraSerTestada.length() - 1) {
					char auxiliar1 = geradores.get(geradores.size() - 1);
					char ultimaLetra = obterUltimaLetra(palavraSerTestada);
					Producao prodaux = encontrarProducaoPorGeradorETerminal(auxiliar1, ultimaLetra, prodFinalista);
					if (prodaux != null) {
						terminais.add(prodaux.getTerminalEsquerda());
						charArray = new char[terminais.size()];
						for (int i = 0; i < terminais.size(); i++) {
							charArray[i] = terminais.get(i);
						}
						palavraSendoProduzida = new String(charArray);
						System.out.println("A palavra foi produzida com sucesso!");
						if (glueOrGlud == 1) {
							int aux = 0;
							for (int i = 0; i < geradores.size() - 1; i++) {
								if (aux < palavraSup.size()) {

									if (palavraSup.get(aux) == '$') {
										System.out.println(geradores.get(i) + ">" + '*' + geradores.get(i + 1));
										aux++;
									} else {
										System.out.println(
												geradores.get(i) + ">" + palavraSup.get(aux) + geradores.get(i + 1));
										aux++;
									}

								}

							}
							System.out.println(geradores.get(geradores.size() - 1) + ">" + ultimaLetra);
						} else {

							int aux = 0;
							for (int i = 0; i < geradores.size() - 1; i++) {
								if (aux < palavraSup.size()) {

									if (palavraSup.get(aux) == '$') {
										System.out.println(geradores.get(i) + ">" + geradores.get(i + 1) + '*');
										aux++;
									} else {
										System.out.println(
												geradores.get(i) + ">" + geradores.get(i + 1) + palavraSup.get(aux));
										aux++;
									}

								}

							}
							System.out.println(geradores.get(geradores.size() - 1) + ">" + ultimaLetra);
						}
						verificador = true;
						break;
					}

				}

			} else {
				if (auxPalavra == 0) {
					char auxiliar1 = geradores.get(geradores.size() - 1);
					char ultimaLetra = obterUltimaLetra(palavraSerTestada);
					Producao prodaux = encontrarProducaoPorGeradorETerminal(auxiliar1, ultimaLetra, prodFinalista);
					if (prodaux != null) {
						terminais.add(prodaux.getTerminalEsquerda());
						charArray = new char[terminais.size()];
						for (int i = 0; i < terminais.size(); i++) {
							charArray[i] = terminais.get(i);
						}
						palavraSendoProduzida = new String(charArray);
						System.out.println("A palavra foi produzida com sucesso!");
						if (glueOrGlud == 1) {
							int aux = 0;
							for (int i = 0; i < geradores.size() - 1; i++) {
								if (aux < palavraSup.size()) {

									if (palavraSup.get(aux) == '$') {
										System.out.println(geradores.get(i) + ">" + '*' + geradores.get(i + 1));
										aux++;
									} else {
										System.out.println(
												geradores.get(i) + ">" + palavraSup.get(aux) + geradores.get(i + 1));
										aux++;
									}

								}

							}
							System.out.println(geradores.get(geradores.size() - 1) + ">" + ultimaLetra);
						} else {

							int aux = 0;
							for (int i = 0; i < geradores.size() - 1; i++) {
								if (aux < palavraSup.size()) {

									if (palavraSup.get(aux) == '$') {
										System.out.println(geradores.get(i) + ">" + geradores.get(i + 1) + '*');
										aux++;
									} else {
										System.out.println(
												geradores.get(i) + ">" + geradores.get(i + 1) + palavraSup.get(aux));
										aux++;
									}

								}

							}
							System.out.println(geradores.get(geradores.size() - 1) + ">" + ultimaLetra);
						}
						verificador = true;
						break;
					}

				}
			}

			if (producaoNaoTestada != null) {// Nesse bloco eu manipulo adicionando e tirando os terminais e variaveis
												// dos meus arraylists que formam a palavra

				direita = producaoNaoTestada.getTerminalDireita();
				esquerda = producaoNaoTestada.getTerminalEsquerda();

				if (esquerda == '$') {

					geradores.add(direita);
					palavraSup.add(esquerda);
					stackProducoes.push(agrupamento);

					auxTestezada = encontrarProducoesParaNo(geradores.get(geradores.size() - 1));

					agrupamento = new AgrupamentoProducoes(auxTestezada);

					producaoNaoTestada = agrupamento.encontrarProducaoNaoTestada();
					direita = producaoNaoTestada.getTerminalDireita();
					esquerda = producaoNaoTestada.getTerminalEsquerda();

					if (esquerda == '$') {
						geradores.add(direita);

						palavraSup.add(esquerda);
					} else {
						geradores.add(direita);
						terminais.add(esquerda);
						palavraSup.add(esquerda);
					}

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
							auxPalavra--;
						} else {
							auxPalavra++;

						}
					}

					auxTestezada = encontrarProducoesParaNo(geradores.get(geradores.size() - 1));

					agrupamento = new AgrupamentoProducoes(auxTestezada);

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

			} else { // aqui quando esgota todas possibilidades ele remove 1 da pilha e trabalha com
						// o peek da pilha

				if (esquerda == '$') {
					geradores.remove(geradores.size() - 1);

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

			String producaoComAsteriscos = encontrarProducaoComTerminaisAsteriscoEspecifico(prodVazias,
					geradores.get(geradores.size() - 1));// isso aqui verifica pra palavra nao terminar sem ser fechada!

			if (producaoComAsteriscos != null) {

				System.out.println("A palavra foi produzida com sucesso!");
				if (glueOrGlud == 1) {
					int aux = 0;
					for (int i = 0; i < geradores.size() - 1; i++) {
						if (aux < palavraSup.size()) {

							if (palavraSup.get(aux) == '$') {
								System.out.println(geradores.get(i) + ">" + '*' + geradores.get(i + 1));
								aux++;
							} else {
								System.out.println(geradores.get(i) + ">" + palavraSup.get(aux) + geradores.get(i + 1));
								aux++;
							}

						}

					}
				} else {

					int aux = 0;
					for (int i = 0; i < geradores.size() - 1; i++) {
						if (aux < palavraSup.size()) {

							if (palavraSup.get(aux) == '$') {
								System.out.println(geradores.get(i) + ">" + geradores.get(i + 1) + '*');
								aux++;
							} else {
								System.out.println(geradores.get(i) + ">" + geradores.get(i + 1) + palavraSup.get(aux));
								aux++;
							}

						}

					}

				}

				System.out.println(producaoComAsteriscos);
			} else {
				if (verificador != true) {
					System.out.println("deu algum b.o pprt");
				}
			}
		} else {
			if (verificador != true) {
				System.out.println("deu algum b.o pprt");
			}

		}

	}
}
