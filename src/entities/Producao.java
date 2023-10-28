package entities;

public class Producao {
	private char gerador;
	private char terminalEsquerda;
	private char terminalDireita;
	private boolean testada;
	private boolean transicao;

	public char getGerador() {
		return gerador;
	}

	public Producao(char gerador, char terminalEsquerda, char terminalDireita,boolean transicao) {
		this.gerador = gerador;
		this.terminalEsquerda = terminalEsquerda;
		this.terminalDireita = terminalDireita;
		this.testada = false;
		this.transicao = transicao;
	}

	public void setGerador(char gerador) {
		this.gerador = gerador;
	}

	public char getTerminalEsquerda() {
		return terminalEsquerda;
	}

	public void setTerminalEsquerda(char terminalEsquerda) {
		this.terminalEsquerda = terminalEsquerda;
	}

	public char getTerminalDireita() {
		return terminalDireita;
	}

	public void setTerminalDireita(char terminalDireita) {
		this.terminalDireita = terminalDireita;
	}

	public boolean isTestada() {
		return testada;
	}

	public void setTestada(boolean testada) {
		this.testada = testada;
	}

	public void imprimirProducao() {
		System.out.println("Gerador: " + gerador);
		System.out.println("Terminal Esquerda: " + terminalEsquerda);
		System.out.println("Terminal Direita: " + terminalDireita);
		System.out.println("É transicao?: "+ transicao);
		System.out.println("====================================");
	}

	public boolean isTransicao() {
		return transicao;
	}

	public void setTransicao(boolean transicao) {
		this.transicao = transicao;
	}

}
