package entities;

import java.util.ArrayList;
import java.util.Stack;

public class Solver {
	ArrayList<Character> terminais = new ArrayList<Character>();
	ArrayList<Character> geradores = new ArrayList<Character>();
	ArrayList<Producao> producoesList = new ArrayList<>();
	ArrayList<Producao> prodVazias = new ArrayList<>();
	String palavraSerTestada; // essa eh a palavra final, ela eh quebrada em partes compondo a palavraSerTestadaArray, assim posso verificar facilmente.
	private char[] palavraSerTestadaArray;
	char SimboloPartida;

	public Solver(ArrayList<Producao> producoesList, char SimboloPartida, char[] palavraSerTestadaArray,
			String palavraSerTestada, ArrayList<Producao> prodVazias) {
		this.producoesList = producoesList;
		this.SimboloPartida = SimboloPartida;
		this.palavraSerTestada = palavraSerTestada;
		this.palavraSerTestadaArray = palavraSerTestadaArray;
		this.prodVazias = prodVazias;
	}

	public ArrayList<Producao> encontrarProducoesParaNo(char no) {
		ArrayList<Producao> producoesCorrespondentes = new ArrayList<>();
		for (Producao producao : producoesList) {
			if (producao.getGerador() == no) {
				producoesCorrespondentes.add(producao); // Adiciona a produção à lista de produções correspondentes
			}
		}
		return producoesCorrespondentes; // Retorna a lista de produções correspondentes
	}
	
	public String encontrarProducaoComTerminaisAsteriscoEspecifico(ArrayList<Producao> producoesList, char geradorDesejado) {
	    for (Producao producao : producoesList) {
	        if (producao.getGerador() == geradorDesejado && producao.getTerminalEsquerda() == '*' && producao.getTerminalDireita() == '*') {
	            return producao.getGerador() + ">" + "**"; // Retorna a produção no formato desejado
	        }
	    }
	    return null; // Se nenhuma produção corresponder, retorna null
	}




	public boolean doIt() {
		Stack<AgrupamentoProducoes> stackProducoes = new Stack<>();
		geradores.add(SimboloPartida);
		int auxPalavra = 0;
		String palavraSendoProduzida = null;
		AgrupamentoProducoes agrupamento;
		Producao producaoNaoTestada;
		ArrayList<Producao> auxTestezada = encontrarProducoesParaNo(geradores.get(geradores.size() - 1));
		agrupamento = new AgrupamentoProducoes(auxTestezada); // Criar uma nova instância de AgrupamentoProducoes
		producaoNaoTestada = agrupamento.encontrarProducaoNaoTestada();

		stackProducoes.push(agrupamento);
		while (!palavraSerTestada.equals(palavraSendoProduzida)) {
			char[] charArray = new char[terminais.size()];
			for (int i = 0; i < terminais.size(); i++) {
				charArray[i] = terminais.get(i);
			}
			palavraSendoProduzida = new String(charArray);
			if (producaoNaoTestada != null) {

				char direita = producaoNaoTestada.getTerminalDireita();
				char esquerda = producaoNaoTestada.getTerminalEsquerda();
				geradores.add(direita);
				terminais.add(esquerda);
				auxTestezada = encontrarProducoesParaNo(geradores.get(geradores.size() - 1));

				agrupamento = new AgrupamentoProducoes(auxTestezada);

				producaoNaoTestada = agrupamento.encontrarProducaoNaoTestada();

				stackProducoes.push(agrupamento);

				 charArray = new char[terminais.size()];
				for (int i = 0; i < terminais.size(); i++) {
					charArray[i] = terminais.get(i);
				}
				palavraSendoProduzida = new String(charArray);

				if (palavraSerTestadaArray[auxPalavra] != terminais.get(terminais.size() - 1)) {
					geradores.remove(geradores.size() - 1);
					terminais.remove(terminais.size() - 1);

					agrupamento = stackProducoes.peek();

				} else {
					auxPalavra++;
				}

			} else {

				if (agrupamento.getTamanho() == 1) {

					 charArray = new char[terminais.size()];
					for (int i = 0; i < terminais.size(); i++) {
						charArray[i] = terminais.get(i);
					}
					geradores.add(geradores.get(geradores.size() - 1));
					terminais.add(terminais.get(terminais.size() - 1));
					stackProducoes.push(stackProducoes.peek());
					palavraSendoProduzida = new String(charArray);

				} else {
					geradores.remove(geradores.size() - 1);
					terminais.remove(terminais.size() - 1);
					System.out.println("removeu uma producao na pilha pois ja tinha testado todas possibilidades");
					stackProducoes.pop();
					auxPalavra--;
					agrupamento = stackProducoes.peek();

				}

			}

		}
		
		if (!palavraSerTestada.equals(palavraSendoProduzida)) {
			System.out.println("Deu ruim!");
			return false;
		} else {


		    
		    String producaoComAsteriscos = encontrarProducaoComTerminaisAsteriscoEspecifico(prodVazias,geradores.get(geradores.size()-2));

		    if (producaoComAsteriscos != null) {
			    System.out.println("A palavra foi produzida com sucesso!");

			    for(int i=0;i<(geradores.size()-2);i++) {//tirar esse -2 dps  VERFICIAR SE TA IMPRIMINDO O **
			    	//VERIFICAR SE IMPRIME O **
			    	
			    	// aqui ele ta duplicando a ultima producao, sendo assim eu verifico se o penultimo gerador tem um C>** ou algo assim pra fazer o aceite
			    	// se nao eu n dou aceite e retorno este problema
			    	System.out.println(geradores.get(i)+">"+terminais.get(i)+geradores.get(i+1));
			    }
		        System.out.println(producaoComAsteriscos);; // Método para imprimir informações da produção
		    } else {
		        return false; // DEIXAR OU TIRAR ISTO AQUI
		    }
			return true;
		}
	}

}
