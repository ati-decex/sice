package br.mil.eb.decex.ati.controlador;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;

/**
 * Controlador para logout no sistema.<br/>
 *   
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 */
@Named("logout")
@ApplicationScoped
public class Logout extends BaseController {

	private static final long serialVersionUID = 1L;

	@Inject
	@Autenticado
	private Usuario usuario;
	
	/**
	 * Invalida sessão e redireciona para página inicial do sistema
	 * @return outcome para página inicial do sistema
	 */
	public String efetuarLogout() {
		
		HttpSession session = (HttpSession)getContext().getExternalContext().getSession(false);		
		session.invalidate();
				
		return "/principal.xhtml?faces-redirect=true";		
	}	
}