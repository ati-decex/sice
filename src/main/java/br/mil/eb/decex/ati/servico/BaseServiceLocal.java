package br.mil.eb.decex.ati.servico;

import java.io.Serializable;
import javax.ejb.Stateless;
import br.mil.eb.decex.ati.excecao.ServicoLocalException;

/**
 * Para transações mais simples e que não se repetem ao restante 
 * do sistema
 * 
 * @author William <b>Moreira</br> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@Stateless
public class BaseServiceLocal implements Serializable {

	private static final long serialVersionUID = 1L; 
	
	/**
	 * Executa uma transação local dentro de contexto transacional
	 * @param local transação a ser executada
	 * @throws ServicoLocalException exeção específica da camada de negócios
	 */
	public void execute(TransacaoLocal local) throws ServicoLocalException {

		try {
			local.execute();
		} catch (Exception e) {
			throw new ServicoLocalException(e.getMessage());
		}		
	}	
}