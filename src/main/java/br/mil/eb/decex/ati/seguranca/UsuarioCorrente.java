package br.mil.eb.decex.ati.seguranca;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import br.mil.eb.decex.ati.controlador.BaseController;
import br.mil.eb.decex.ati.enumerado.TipoAcesso;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.modelo.jaas.UserRoles;
import br.mil.eb.decex.ati.modelo.jaas.Users;

/**
 * Disponibiliza o usuário logado para o restante da  
 * aplicação.
 * 
 * @author William <b>Moreira</b> de Pinho - 1° Ten QCO
 * @version 1.0
 */
@Named
@SessionScoped
public class UsuarioCorrente extends BaseController  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Usuario usuarioLogado;
	
	@Produces
	@Autenticado
	@Named("usuarioLogado")
	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}
	
	private boolean administrador = false;
	private boolean diretoria = false;
	private boolean auditoria = false;
	private boolean exibirCadastroExistente = false;
	
	
	public boolean isAdministrador() {
		return administrador;
	}
	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}
	
	/**
	 * Nome qualificado do usuário
	 * @return posto/graduação - nome guerra
	 */
	public String getNomeQualificado() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(usuarioLogado.getPostoGraduacao().getValue());
		sb.append("  ");		
		sb.append(usuarioLogado.getNomeGuerra());
		sb.append("  |  ");		
		sb.append(usuarioLogado.getOrganizacaoMilitar());	
		
		return sb.toString();
	}
	
	/**
	 * Listener de evento. Quando um usuário loga com sucesso este 
	 * listener disponibiliza o usuário em nível de aplicação e estabelece 
	 * tempo de expiração da sessão.
	 * @param user usuário autenticado
	 * @param request requisicao
	 */
	public void onLogin(@Observes @Autenticado Usuario user, HttpServletRequest request){
		usuarioLogado = user;
		user.setUsers(repository.encontrar(Users.class,"name",usuarioLogado.getIdentidade()).get(0));
		verificarSeAdministrador();
		if (!administrador){
			verificarSePerfilDiretoria();
			verificarSePerfilAuditoria();
		}
		
//		setExibirCadastroExistente(!diretoria);
		
		setExibirCadastroExistente(!auditoria);
		
		request.getSession().setAttribute("usuarioLogado", user);
        request.getSession().setMaxInactiveInterval(3600);		
	}

	private void verificarSeAdministrador() {
		this.setAdministrador(false);
		for (UserRoles role  : this.getUsuarioLogado().getUsers().getRoles()) {
			if (role.getRoleName().equals(TipoAcesso.ADMINISTRADOR))
				this.setAdministrador(true);
		}
	}
	
	
	private void verificarSePerfilDiretoria() {
		this.setDiretoria(false);
		for (UserRoles role  : this.getUsuarioLogado().getUsers().getRoles()) {
			if (role.getRoleName().equals(TipoAcesso.DIRETORIA))
				this.setDiretoria(true);
		}
	}
	
	private void verificarSePerfilAuditoria() {
		this.setAuditoria(false);
		for (UserRoles role  : this.getUsuarioLogado().getUsers().getRoles()) {
			if (role.getRoleName().equals(TipoAcesso.AUDITORIA))
				this.setAuditoria(true);
		}
	}
	
	public boolean isDiretoria() {
		return diretoria;
	}
	public void setDiretoria(boolean diretoria) {
		this.diretoria = diretoria;
	}
	public boolean isExibirCadastroExistente() {
		return exibirCadastroExistente;
	}
	public void setExibirCadastroExistente(boolean exibirCadastroExistente) {
		this.exibirCadastroExistente = exibirCadastroExistente;
	}
	public boolean isAuditoria() {
		return auditoria;
	}
	public void setAuditoria(boolean auditoria) {
		this.auditoria = auditoria;
	}
	

	
}