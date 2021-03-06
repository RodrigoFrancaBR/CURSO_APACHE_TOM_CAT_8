package br.com.franca.modelo;

import java.util.Arrays;

public enum Status {

	// Usado para Turmas e Unidades

	DESATIVADA(0, "Desativada"), ATIVA(1, "Ativa"), INVALIDA(100, "Status inválido");

	private int chave;
	private String valor;

	private Status(int chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}

	/**
	 * @param Alguma
	 *            chave v�lida 0,1,2,3 etc..
	 * @return Um Status ou null caso n�o encontre um status existente
	 */
	public static Status getStatus(String valor) {		
		return Arrays.asList(Status.values()).parallelStream().filter(e -> e.getValor() ==valor).findFirst()
				.orElse(Status.INVALIDA);
	}

	public int getChave() {
		return chave;
	}

	public String getValor() {
		return valor;
	}

}
