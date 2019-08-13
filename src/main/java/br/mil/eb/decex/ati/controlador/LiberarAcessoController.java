package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.TipoAcesso;
import br.mil.eb.decex.ati.jpa.Identity;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.modelo.jaas.UserRoles;
import br.mil.eb.decex.ati.modelo.jaas.Users;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.servico.TransacaoLocal;
import br.mil.eb.decex.ati.util.EncodingSHA256;

/**
 * Realiza a liberação do usiário para acessar o sistema</p>
 * 
 * @author Luiz Augusto Lourenço do Amparo <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@Named("liberarAcesso")
@SessionScoped
public class LiberarAcessoController extends BaseController {

	private static final long serialVersionUID = 1L;

	private List<Usuario> usuarios;
	private List<Usuario> usuariosFiltrados;

	private Usuario usuarioSelecionado;
	

	
	@Inject
	private Identity identity;
	
	@Inject
	@Autenticado
	private Usuario usuario;
	
	private String keyword;
	
	
	
	private static final String LIBERADO_SUCESSO = "usuario_liberado";
	private static final String BLOQUEADO_SUCESSO = "usuario_bloqueado";
	private static final String EXCLUIDO_SUCESSO = "usuario_excluido";
	private static final String RESET_SUCESSO = "usuario_resetado";
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "falha_na_operacao";		

	/**
	 * Lista com os tipoAcesso para seleção
	 * @return TipoAcesso
	 */
	public List<TipoAcesso> getListaTipoAcesso() {
		List<TipoAcesso> listaTipoAcesso = new ArrayList<TipoAcesso>();
		for (TipoAcesso tipoAcesso : TipoAcesso.values()) {
			if (!tipoAcesso.equals(TipoAcesso.USUARIO)){
				listaTipoAcesso.add(tipoAcesso);
			}
		}
		return listaTipoAcesso;
	}
	
	/**
	 * Usuário que solicitou acesso ao sistema
	 * @return usuario solicitante
	 */	
	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}
	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}
	
	/**
	 * Lista de usuários que solicitaram liberação
	 * @return usuários que solicitaram liberação
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}	
	
	/**
	 * Recebe resultado do filtro aplicado a datatable	
	 * @return usuários filtrados
	 */
	public List<Usuario> getUsuariosFiltrados() {
		return usuariosFiltrados;
	}
	
	public void setUsuariosFiltrados(List<Usuario> usuariosFiltrados) {
		this.usuariosFiltrados = usuariosFiltrados;
	}


	/**
	 * Estado inicial da tela
	 */	
	@PostConstruct
	public void init() {
		
//		usuarios = repository.encontrar(Usuario.class);
		
		usuarios = repository.encontrarComQueryNomeada(Usuario.class, "Usuario.ativos");
		
		usuarios.forEach(usuario -> {
			
			List<UserRoles> listaDePapeis = repository.encontrarComQueryNomeada(UserRoles.class, "userRoles.poruserName", 
					new Object[]{"users", usuario.getIdentidade()});
			
			List<TipoAcesso> listaTipoAcesso = new ArrayList<TipoAcesso>(); //criar converter para tipoAcesso
			
			listaDePapeis.forEach(perfil -> {
				listaTipoAcesso.add(perfil.getRoleName());
			});
			
			usuario.getListaTipoAcesso().addAll(listaTipoAcesso);
		});
		
	}
	
	/**
	 * excluir acesso do usuário solicitante ao sistema
	 */	
	public void excluirUsuario() {
		TransacaoLocal t = () -> {
			usuarioSelecionado.setExcluido(Boolean.TRUE);
			repository.mudar(usuarioSelecionado);
			init();
		}; 
	
		super.executar(t, EXCLUIDO_SUCESSO, FALHA);	
		bloquear();
	}
	
	/**
	 * Libera acesso do usuário solicitante ao sistema
	 */	
	public void liberar() {
		TransacaoLocal t = () -> {
			usuarioSelecionado.setLiberado(Boolean.TRUE);
			identity.persist(usuarioSelecionado.parseUsers());
			repository.mudar(usuarioSelecionado);
			init();
		}; 
	
		super.executar(t, LIBERADO_SUCESSO, FALHA);			
	}
	
	/**
	 * Resetar a senha do usuário para identidade
	 */
	public void resetar() {
		if(usuario.equals(usuarioSelecionado)) return;		 
		
		TransacaoLocal t = () -> {
			
			Users users = new Users(
					usuarioSelecionado.getIdentidade(), 
					EncodingSHA256.encodingBase64(usuarioSelecionado.getIdentidade()));				
			
			identity.merge(users);
			init();
		}; 
			
		super.executar(t, RESET_SUCESSO, FALHA);		
	}
	
	/**
	 *bloquear usuario selecionado 
	 */
	public void bloquear() {
		if(usuario.equals(usuarioSelecionado)) return;		
		
		TransacaoLocal t = () -> {
			usuarioSelecionado.setLiberado(Boolean.FALSE);
			identity.remove(usuarioSelecionado.getIdentidade());
			repository.mudar(usuarioSelecionado);
			init();
		}; 
			
		super.executar(t, BLOQUEADO_SUCESSO, FALHA);		
	}
	
	/**
	 * Apaga todos os perfis de determinado usuário e os cria novamente
	 * @param usuario
	 */
	public void salvarUserRoles(Usuario usuario) {
		
		UserRoles userRoles = new UserRoles(new Users(usuario.getIdentidade()), TipoAcesso.USUARIO);
		
		apagarTodosPerfisDoUsuario(usuario);
		
		usuario.getListaTipoAcesso().forEach(tipoAcesso -> {
			userRoles.setRoleName(tipoAcesso); 
					
			super.executar(() -> {repository.adicionarOuMudar(userRoles);}, SUCESSO, FALHA);
		});
	}
	
	/**
	 * Apaga todos os perfis de determinado usuário
	 * @param usuario
	 */
	public void apagarTodosPerfisDoUsuario(Usuario usuario) {
		List<UserRoles> listaUserRoles = repository.encontrarComQueryNomeada(UserRoles.class, "userRoles.poruserName", 
				new Object[]{"users", usuario.getIdentidade()});

		listaUserRoles.forEach(tipoAcesso -> {
			TransacaoLocal t = () -> {
				identity.removeRole(usuario.getIdentidade(), TipoAcesso.ADMINISTRADOR);
				identity.removeRole(usuario.getIdentidade(), TipoAcesso.DIRETORIA);
				identity.removeRole(usuario.getIdentidade(), TipoAcesso.USUARIO);
				identity.removeRole(usuario.getIdentidade(), TipoAcesso.AUDITORIA);
			};
			
			super.executar(t, SUCESSO, FALHA);
		});
	}
	
	public void buscarUsuarios(){
		if (this.getKeyword().isEmpty()) {
			usuarios = repository.encontrar(Usuario.class);
		} else if (this.getKeyword().length() > 2) {
			usuarios = repository.encontrarComQueryNomeada(Usuario.class, "Usuario.Keyword",
					new Object[] { "keyword", this.getKeyword() });
		}
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
	
}