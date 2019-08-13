package br.mil.eb.decex.ati.servico;

import javax.ejb.Stateless;

/**
 * Interface funcional para transações simples. Normalmente 
 * utilizar para transações de até uma linha de implementação
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@Stateless
@FunctionalInterface
public interface TransacaoLocal {
	
	/**
	 * Implementação para transação local
	 */
	public void execute();	
}