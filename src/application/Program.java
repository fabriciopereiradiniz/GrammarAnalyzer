package application;

import java.util.ArrayList;
import java.util.Scanner;

import entities.Producao;
import entities.Solver;

public class Program {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		ArrayList<Producao> producoesList = new ArrayList<>(); // recebe as producoes que dps sao passadas por parametro
		ArrayList<Character> variaveis = new ArrayList<>(); // usado pra verificacao ( contains )
		ArrayList<Character> alfabeto = new ArrayList<>(); // usado pra verificacao ( contains )
		ArrayList<Producao> prodVazias = new ArrayList<>(); // usado pra logica de completar e n deixar o programa aceitar sem fechar todas variaveis
		ArrayList<Producao> prodFinalista = new ArrayList<>(); // usado pra logica de completar e n deixar o programa aceitar sem fechar todas variaveis
		boolean desvio = false; // usado pra controlar a logica de fluxo do $$ pq tava tendo alguns probleminh
		boolean desvio2 = false;//usado pro fluxo dos finalistas
		System.out.println("Variavel sem terminal eh no modelo S>$A, variavel pro vazio eh no modelo S>**, variavel gerando apenas terminal é no modelo S>a#");
		System.out.println("Digite 0 para GLUE ou 1 para GLUD");
		int glueOrGlud = sc.nextInt();

		System.out.println("Digite a quantidade de variáveis <V>");
		int quantv = sc.nextInt();
		variaveis.add('*');
		alfabeto.add('$');
		alfabeto.add('*');
		variaveis.add('$');
		alfabeto.add('#');
		variaveis.add('#');
		for (int i = 0; i < quantv; i++) {
			System.out.println("Digite a " + (i + 1) + "ª variável:");
			variaveis.add(sc.next().charAt(0));
		}

		System.out.println("Digite a quantidade de caracteres no alfabeto <T>");
		int quantt = sc.nextInt();
		alfabeto.add('*');
		for (int i = 0; i < quantt; i++) {
			System.out.println("Digite o " + (i + 1) + " caractere do alfabeto:");
			alfabeto.add(sc.next().charAt(0));
		}

		System.out.println("Digite o símbolo de partida <S>");
		char SimboloPartida = sc.next().charAt(0);

		if (!variaveis.contains(SimboloPartida)) {
			System.out.println("O símbolo de partida não está na lista de variáveis.");
			sc.close();
			return;
		}

		System.out.println("Digite a quantidade de ordens de produção");
		int quantO = sc.nextInt();
		System.out.println("======================================================");

		for (int i = 0; i < quantO; i++) {
			System.out.println("Digite a " + (i + 1) + " ordem de produção:");
			String entrada = sc.next();

			String[] partes = entrada.split(">");
			char gerador = partes[0].charAt(0);
			String direita = partes[1];
			char terminalEsquerda = 0, terminalDireita = 0;

			if (!direita.equals("**")) {

				if (glueOrGlud == 1) {
					terminalEsquerda = direita.charAt(0);
					terminalDireita = direita.charAt(1);
					// verifica se eh uma "transicao" que é quando eu jogo de variavel pra variavel

					if (terminalDireita == '#') {
						desvio2 = true;
					} else {
						desvio2 = false;
					}

					if (terminalEsquerda == '$' || terminalDireita == '$') {
						desvio = true;
					} else {
						desvio = false;
					}

				} else {
					terminalEsquerda = direita.charAt(1);
					terminalDireita = direita.charAt(0);
					if (terminalDireita == '#') {//verifica se eh um "finalista", ou seja pode fechar a palavra 
						desvio2 = true;
					} else {
						desvio2 = false;
					}

					// verifica se eh uma "transicao" que é quando eu jogo de variavel pra variavel
					if (terminalEsquerda == '$' || terminalDireita == '$') {
						desvio = true;
					} else {
						desvio = false;
					}

				}

				if (desvio == false) {

					if (desvio2 == true) {
						Producao producao = new Producao(gerador, terminalEsquerda, terminalDireita, false, true);
						prodFinalista.add(producao);
					} else {
						Producao producao = new Producao(gerador, terminalEsquerda, terminalDireita, false, false);
						producoesList.add(producao);
					}

				} else {
					Producao producao = new Producao(gerador, terminalEsquerda, terminalDireita, true, false);
					producoesList.add(producao);
				}

			} else {
				// cria pros **
				terminalEsquerda = direita.charAt(0);
				terminalDireita = direita.charAt(1);
				Producao producao2 = new Producao(gerador, terminalEsquerda, terminalDireita, false, false);
				prodVazias.add(producao2);

			}

			if (!variaveis.contains(gerador)) {
				System.out.println("O símbolo gerador na ordem de produção não está na lista de variáveis.");
				sc.close();
				return;
			}

			if (glueOrGlud == 0) {
				if (!alfabeto.contains(terminalEsquerda)) {
					System.out.println("O terminal da esquerda na ordem de produção não está na lista de alfabeto.");
					sc.close();
					return;
				}
				if (!variaveis.contains(terminalDireita)) {
					System.out.println("O terminal da direita na ordem de produção não está na lista de variáveis.");
					sc.close();
					return;
				}
			}
			if (glueOrGlud != 0 && glueOrGlud != 1) {
				System.out.println("Valor inválido para glueOrGlud. Deve ser 0 ou 1.");
				sc.close();
				return;
			}

		}

		System.out.println("Digite a palavra a ser testada:");
		String palavraSerTestada = sc.next();
		char[] palavraSerTestadaArray = palavraSerTestada.toCharArray();

		Solver solver = new Solver(producoesList, SimboloPartida, palavraSerTestadaArray, palavraSerTestada, prodVazias,
				glueOrGlud, prodFinalista);

		solver.doIt();

		sc.close();
	}
}
