package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.enumerado.PostoGraduacao;
import br.mil.eb.decex.ati.jpa.OrganizacaoMilitarRepository;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Usuario;

/**
 * Realiza a solicitação de acesso de usuariao para manupulação no sistema  </p>
 * 
 * @author Luiz Augusto Lourenço do Amparo <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("acesso")
public class AcessoController extends BaseController {
 
	private static final long serialVersionUID = 1L;
		
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";
	
    private Usuario usuario;
    private OrganizacaoMilitar superiorSelected;

    private List<OrganizacaoMilitar> organizacoesMilitares;
    private List<PostoGraduacao> postosGraduacoes;
    private List<OrganizacaoMilitar> superiores;
    
    @Inject
    private OrganizacaoMilitarRepository omRepository;
    
    /**
	 * Guarda as informações do usuario
	 * @return Usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	/**
	 * Carrega lista de postos e graduações
	 * @return lista de postos e graduações
	 */
	public List<PostoGraduacao> getPostosGraduacoes() {
		return postosGraduacoes;
	}
	
	/**
	 * Carrega lista de Organizações Militares 
	 * @return lista de Organizações Militares
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}	
	
	/**
	 * Lista de superiores	
	 * @return OrganizacaoMilitar
	 */
	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}
		
	/**
	 * Guarda as informações do superior selecionado	
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getSuperiorSelected() {
		return superiorSelected;
	}
	public void setSuperiorSelected(OrganizacaoMilitar superiorSelected) {
		this.superiorSelected = superiorSelected;
	}
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
		
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		
		superiores = omRepository.encontrarSuperiores();		

		postosGraduacoes= new ArrayList<PostoGraduacao>();
		
		postosGraduacoes.addAll(Arrays.asList(PostoGraduacao.values()));
		postosGraduacoes.remove(PostoGraduacao.ALUNO);
		postosGraduacoes.remove(PostoGraduacao.T1);
		postosGraduacoes.remove(PostoGraduacao.T2);
		postosGraduacoes.remove(PostoGraduacao.TM);
		postosGraduacoes.remove(PostoGraduacao.PRF_CIV);
		postosGraduacoes.remove(PostoGraduacao.SD);		
	}
	
	/**
	 * Lista apenas as organizações com o superior informado
	 */
	public void listarSubordinados() {
		organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
		
		//adiciona o superior OM na posição 0
		organizacoesMilitares.add(0, repository.encontrar(OrganizacaoMilitar.class, "sigla", superiorSelected + " OM").get(0));
	}
	
	/**
	 * Salva uma nova solicitação de acesso
	 */
	public void salvar() {
		super.executar(() -> {repository.adicionarOuMudar(usuario); init();}, SUCESSO, FALHA);
	}		
}