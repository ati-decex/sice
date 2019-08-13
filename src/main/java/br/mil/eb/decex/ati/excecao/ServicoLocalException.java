package br.mil.eb.decex.ati.excecao;

/**
 * Execeção para camada de negócios na execução de transações locais
 * 
 * @author William Moreira de Pinho - 1º Ten QCO
 * @version 1.0
 */
public class ServicoLocalException extends Exception {

	private static final long serialVersionUID = 1L;

	public ServicoLocalException(String message) {
		super(message);
	}	
}