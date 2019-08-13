package br.mil.eb.decex.ati.controlador;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.jpa.Identity;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.modelo.jaas.Users;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.servico.TransacaoLocal;
import br.mil.eb.decex.ati.util.EncodingSHA256;

/**
 * Realiza a troca de senha do Usuário logado</p>
 * 
 * @author Luiz Augusto Lourenço do Amparo <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@Named("trocaSenha")
@RequestScoped
public class TrocarSenhaController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
	@Autenticado
	private Usuario usuario;
	
	@Inject
	private Logout logout;
	
	@Inject
	private Identity identity;
	
	private String senha;
	private String confirmaSenha;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";		
	
	/**
	 * Nova senha do usuário 
	 * @return nova senha do usuário
	 */
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Confirmação da nova senha do usuário 
	 * @return confirmação da nova senha do usuário
	 */
	public String getConfirmaSenha() {
		return confirmaSenha;
	}
	public void setConfirmaSenha(String confirmaSenha) {
		this.confirmaSenha = confirmaSenha;
	}

	/**
	 * Estado inicial da tela
	 */		
	@PostConstruct
	public void init() {
		senha = "";
		confirmaSenha = "";
	}
	
	/**
	 * Valida a senha digitada pelo usuário
	 */
	public void  validarSenha() {
		if (getSenha().isEmpty()) {
			addMessage("erro_operacao", FALHA, FacesMessage.SEVERITY_ERROR);
		}else {
			alterarSenha();
		}
	}
	
	/**
	 * Altera a senha do usuário
	 */
	public String alterarSenha() {
		TransacaoLocal t = () -> identity.merge(new Users(usuario.getIdentidade(), EncodingSHA256.encodingBase64(senha)));		
		String outcome = (super.executar(t, SUCESSO, FALHA) ? logout.efetuarLogout() : "");
		return outcome;
	}		
}