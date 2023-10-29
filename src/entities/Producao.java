package entities;

public class Producao {
	private char gerador;
	private char terminalEsquerda;
	private char terminalDireita;
	private boolean testada;
	private boolean transicao;
	private boolean finalista;

	public char getGerador() {
		return gerador;
	}

	public Producao(char gerador, char terminalEsquerda, char terminalDireita, boolean transicao,boolean finalista) {
		this.gerador = gerador;
		this.terminalEsquerda = terminalEsquerda;
		this.terminalDireita = terminalDireita;
		this.testada = false;
		this.transicao = transicao;
		this.finalista = finalista;
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
		System.out.println(gerador + ">" + terminalEsquerda + terminalDireita);
		System.out.println("eh transicao:"+transicao);
		System.out.println("eh finalista:"+finalista);
	}

	public boolean isTransicao() {
		return transicao;
	}

	public void setTransicao(boolean transicao) {
		this.transicao = transicao;
	}

	public boolean isFinalista() {
		return finalista;
	}

	public void setFinalista(boolean finalista) {
		this.finalista = finalista;
	}

}
