package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.PostoGraduacao;
import br.mil.eb.decex.ati.enumerado.TipoAcesso;
import br.mil.eb.decex.ati.enumerado.TipoOrganizacaoMilitar;
import br.mil.eb.decex.ati.jpa.Identity;
import br.mil.eb.decex.ati.jpa.OrganizacaoMilitarRepository;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
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
@Named("usuarioController")
@SessionScoped
public class UsuarioController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	private Usuario usuarioSelecionado;
	private OrganizacaoMilitar superiorSelected;
	
	private List<Usuario> usuarios;
	private List<Usuario> usuariosKeyword;
	private List<PostoGraduacao> postos;
	private List<OrganizacaoMilitar> superiores;
	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<Usuario> usuariosVisives;
	
	private boolean perfilUsuario;
	private boolean perfilAdministrador;
	private boolean perfilDiretoria;
	private boolean perfilAuditoria;
	
	@Inject
	private Identity identity;

	@Inject
	private OrganizacaoMilitarRepository omRepository;
	
	@Inject
	@Autenticado
	private Usuario usuario;
	private String keyword;
	
	private static final String LIBERADO_SUCESSO = "usuario_liberado";
	private static final String BLOQUEADO_SUCESSO = "usuario_bloqueado";
	private static final String RESET_SUCESSO = "usuario_resetado";
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "falha_na_operacao";		
	
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
	 * oraganizacaoMilitar do usuario selecionado
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getSuperiorSelected() {
		return superiorSelected;
	}
	public void setSuperiorSelected(OrganizacaoMilitar superiorSelected) {
		this.superiorSelected = superiorSelected;
	}
	
	/**
	 * Lista das oraganizacaoMilitares superiors do usuario selecionado
	 * @return OrganizacaoMilitar
	 */
	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}
	public void setSuperiores(List<OrganizacaoMilitar> superiores) {
		this.superiores = superiores;
	}
	
	/**
	 * Lista de oraganizacaoMilitar do usuario selecionado
	 * @return OrganizacaoMilitar
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}
	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}
	
	/**
	 * Lista de postograduacao, para exibição no combo
	 * @return PostoGraduacao
	 */
	public List<PostoGraduacao> getPostos() {
		return Arrays.asList(PostoGraduacao.values());
	}
	/**
	 * Lista de usuários que solicitaram liberação
	 * @return usuários que solicitaram liberação
	 */
	public List<Usuario> getUsuarios() {
		return usuarios;
	}	
	

	public List<Usuario> getUsuariosVisives() {
		return usuariosVisives;
	}
	public void setUsuariosVisives(List<Usuario> usuariosVisives) {
		this.usuariosVisives = usuariosVisives;
	}
	
	/**
	 * Estado inicial da tela
	 */	
	@PostConstruct
	public void init() {
		keyword = "";
		buscarUsuarios();
		superiores = omRepository.encontrarSuperiores();
		
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
	

	
	private void reconhecerPerfisDoUsuario(Usuario usuario) {
		for (TipoAcesso tipoAcesso : usuario.getListaTipoAcesso()) {
			if (tipoAcesso.equals(TipoAcesso.ADMINISTRADOR))
				this.setPerfilAdministrador(true);
			else if (tipoAcesso.equals(TipoAcesso.USUARIO))
				this.setPerfilUsuario(true);
			else if (tipoAcesso.equals(TipoAcesso.DIRETORIA))
				this.setPerfilDiretoria(true);
			else if (tipoAcesso.equals(TipoAcesso.AUDITORIA))
				this.setPerfilAuditoria(true);
		}

	}
	
	public void passagem(Usuario user) {
		if (usuarioSelecionado == null){
			usuarioSelecionado = new Usuario();
		}
		usuarioSelecionado = user;
		List<UserRoles> userR = repository.encontrarComQueryNomeada(UserRoles.class, "userRoles.poruserName", new Object[]{"users", user.getIdentidade()});
		userR.forEach(roles -> {
			usuarioSelecionado.getListaTipoAcesso().add(roles.getRoleName());
		});
		reconhecerPerfisDoUsuario(user);
		if (usuarioSelecionado.getOrganizacaoMilitar().getTipo().equals(TipoOrganizacaoMilitar.DEPARTAMENTO)){
			if (perfilAuditoria){
				superiorSelected= usuarioSelecionado.getOrganizacaoMilitar();
			}
		}
		else{
			superiorSelected= usuarioSelecionado.getOrganizacaoMilitar().getSuperiores().get(0);
		}
 		listarSubordinados();
	}
	
	/**
	 * busca a lista de usuarios cadastrados para poder exibir 
	 * 
	 */
	public void buscarUsuarios(){
		if (this.getKeyword().isEmpty()) {
			usuariosKeyword = repository.encontrar(Usuario.class);
		} else if (this.getKeyword().length() > 2) {
			usuariosKeyword = repository.encontrarComQueryNomeada(Usuario.class, "Usuario.Keyword",
					new Object[] { "keyword", this.getKeyword() });
		}
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	
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

	

	private boolean omDepartamento(OrganizacaoMilitar organizacaomilitar) {
		if (organizacaomilitar != null) {
			if (organizacaomilitar.getTipo() != null) {
				return organizacaomilitar.getTipo().equals(TipoOrganizacaoMilitar.DEPARTAMENTO);
			}
		}
		return false;
	}
	
	private void adicionarOpcaoRelatorioDepartamento(int indice) {
		OrganizacaoMilitar departamento = new OrganizacaoMilitar();
		departamento.setSigla(superiorSelected.getSigla() + " Sistema");
		departamento.setId(superiorSelected.getId());
		departamento.setTipo(TipoOrganizacaoMilitar.DEPARTAMENTO);
		if (organizacoesMilitares.size() > 0) {
			organizacoesMilitares.add(indice, departamento);
		}
	}
	
	private void adicionarDiretoriaOM(int indice) {
		if (organizacoesMilitares.size() > 0) {
			organizacoesMilitares.add(indice,
					repository.encontrar(OrganizacaoMilitar.class, "sigla", superiorSelected + " OM").get(0));
		}
	}

	public void listarSubordinados() {
		organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
		//ajustarNomenclaturaDuplaSubordinacao(superiorSelected, organizacoesMilitares);
		if (omDepartamento(superiorSelected)) {
			adicionarOpcaoRelatorioDepartamento(0);
			adicionarDiretoriaOM(1);
		} else {
			adicionarDiretoriaOM(0);
		}
	}
	
	/**
	 * Resetar a senha do usuário para identidade
	 */
	public void resetar() {
		if(usuarioSelecionado.getIdentidade() != null) {		 
		
		TransacaoLocal t = () -> {
			
			Users users = new Users(
					usuarioSelecionado.getIdentidade(), 
					EncodingSHA256.encodingBase64(usuarioSelecionado.getIdentidade()));				
			
			identity.merge(users);
			init();
		 }; 
			
		super.executar(t, RESET_SUCESSO, FALHA);		
	}
	}
	/**
	 * Apaga todos os perfis de determinado usuário e os cria novamente
	 * @param usuario
	 */
	public void salvarUserRoles() {
		
		UserRoles userRoles = new UserRoles(new Users(usuarioSelecionado.getIdentidade()), TipoAcesso.USUARIO);
		
		apagarTodosPerfisDoUsuario();
		
		usuarioSelecionado.getListaTipoAcesso().forEach(tipoAcesso -> {
			userRoles.setRoleName(tipoAcesso); 
					
			super.executar(() -> {repository.adicionarOuMudar(userRoles);}, SUCESSO, FALHA);
		});
	}
	
	/**
	 * Apaga todos os perfis de determinado usuário
	 * @param usuario
	 */
	public void apagarTodosPerfisDoUsuario() {
		List<UserRoles> listaUserRoles = repository.encontrarComQueryNomeada(UserRoles.class, "userRoles.poruserName", 
				new Object[]{"users", usuarioSelecionado.getIdentidade()});

		listaUserRoles.forEach(tipoAcesso -> {
			TransacaoLocal t = () -> {
				identity.removeRole(usuarioSelecionado.getIdentidade(), TipoAcesso.ADMINISTRADOR);
				identity.removeRole(usuarioSelecionado.getIdentidade(), TipoAcesso.DIRETORIA);
				identity.removeRole(usuarioSelecionado.getIdentidade(), TipoAcesso.AUDITORIA);
			};
			
			super.executar(t, SUCESSO, FALHA);
		});
		
	}

	/**
	 * Salva ou atualiza um registro no Banco de Dados
	 */
	public void salvar()  {
		salvarUserRoles();
		super.executar(() -> {repository.adicionarOuMudar(usuarioSelecionado); init();}, SUCESSO, FALHA);
		
	}
	public boolean isPerfilUsuario() {
		return perfilUsuario;
	}
	public void setPerfilUsuario(boolean perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}
	public boolean isPerfilAdministrador() {
		return perfilAdministrador;
	}
	public void setPerfilAdministrador(boolean perfilAdministrador) {
		this.perfilAdministrador = perfilAdministrador;
	}
	public boolean isPerfilDiretoria() {
		return perfilDiretoria;
	}
	public void setPerfilDiretoria(boolean perfilDiretoria) {
		this.perfilDiretoria = perfilDiretoria;
	}
	public boolean isPerfilAuditoria() {
		return perfilAuditoria;
	}
	public void setPerfilAuditoria(boolean perfilAuditoria) {
		this.perfilAuditoria = perfilAuditoria;
	}
	public List<Usuario> getUsuariosKeyword() {
		return usuariosKeyword;
	}
	public void setUsuariosKeyword(List<Usuario> usuariosKeyword) {
		this.usuariosKeyword = usuariosKeyword;
	}
	
	
}

