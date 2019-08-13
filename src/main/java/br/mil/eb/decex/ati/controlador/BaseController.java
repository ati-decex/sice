package br.mil.eb.decex.ati.controlador;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import br.mil.eb.decex.ati.jpa.Repository;
import br.mil.eb.decex.ati.servico.BaseServiceLocal;
import br.mil.eb.decex.ati.servico.TransacaoLocal;

/**
 * Controlador base. Expõe o repositorio e acesso ao pacote de 
 * mensagens para classes filhas
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
public class BaseController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	protected Repository repository;
	
	@Inject
	private BaseServiceLocal serviceLocal;
	
	public static final String SUCESSO = "sucesso_na_operacao";
	public static final String FALHA = "operacao_nao_realizada";
	
	protected FacesContext getContext() {
		return FacesContext.getCurrentInstance();
	}
	
	/**
	  * Recupera mensagem do arquivo de mensagens
	  * @param key chave da mensagem
	  * @return mensagem solicitada
	  */
	protected String getResourceMessage(String key) {
	  
		ResourceBundle rb = ResourceBundle.getBundle("br.mil.eb.decex.ati.messages");  
		return MessageFormat.format(rb.getString(key), new Locale("pt-BR"));
		
	}
	
	/**
	  * Adiciona mensagem ao contexto para exibição
	  * @param keyTitulo chave do titulo
	  * @param keyMessage chave da mensagem
	  * @param severity nivel da mensage
	  */
	protected void addMessage(String keyTitulo, String keyMessage, FacesMessage.Severity severity) {

		getContext().addMessage(null, new FacesMessage(severity,
		getResourceMessage(keyTitulo), getResourceMessage(keyMessage)));  
	  
	}	
	
	/**
	 * Executa uma transação simples, onde não é necessário a 
	 * criação de um serviço para sua execução. Normalmente transações 
	 * que envolvam apenas uma linha de implementação e que não se repitam em 
	 * muitos locais do sistema
	 * 
	 * @param t transacao local
	 */
	public boolean executar(TransacaoLocal t, String sucesso, String falha) {
		
		try {
			serviceLocal.execute(t);
			addMessage("confirmacao_operacao", sucesso, FacesMessage.SEVERITY_INFO);
			return true;
		}catch(Exception e) {
			addMessage("erro_operacao", falha, FacesMessage.SEVERITY_ERROR);
			return false;
		}						
	}
	
	public boolean executar(TransacaoLocal t) {
		try {
			serviceLocal.execute(t);
			return true;
		}catch(Exception e) {
			return false;
		}						
	}
	
}