package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.modelo.ComboTela;
import br.mil.eb.decex.ati.modelo.EnsinoADistancia;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Controlador de cadastro de tala de ensino a distancia de cada OM</p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("ensinoADistancia")
public class EnsinoADistanciaController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
	@Inject
	private TelasController tela;
	
	private List<EnsinoADistancia> ensinos;
	private List<EnsinoADistancia> ensinosSelect;
	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<ComboTela> comboTela;
	
	private EnsinoADistancia ensino;
	private boolean habilitaBotao = true;	

	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";
	
	/**
	 * Guarda os registros de EAD 
	 * @return ensinosSelect List<EnsinoADistancia>
	 */
	public List<EnsinoADistancia> getEnsinosSelect() {
		return ensinosSelect;
	}
	public void setEnsinosSelect(List<EnsinoADistancia> ensinosSelect) {
		this.ensinosSelect = ensinosSelect;
	}

	/**
	 * habilita a função de abilitar o botão apartir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
	}
	
	/**
	 * Carrega lista de OrganizacaoMilitar
	 * @return lista de OrganizacaoMilitar
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}
	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}
	
	/**
	 * Lista de EnsinoADistancia usada para armazenar os valores do banco de dados
	 * @return lista de EnsinoADistancia
	 */
	public List<EnsinoADistancia> getEnsinos() {
		return ensinos;
	}
	public void setEnsinos(List<EnsinoADistancia> ensinos) {
		this.ensinos = ensinos;
	}	

	/**
	 * Guarda as informações do ensino a distancia
	 * @return EnsinoADistantancia
	 */
	public EnsinoADistancia getEnsino() {
		return ensino;
	}
	public void setEnsino(EnsinoADistancia ensino) {
		this.ensino = ensino;
	}
	
	@PostConstruct
	public void init() {	
		ensino = new EnsinoADistancia();
		
		ensinos = new ArrayList<EnsinoADistancia>();
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		
		ensino.setPeriodo(periodo);
		
		comboTela = repository.encontrarComQueryNomeada(ComboTela.class, "ComboTela.porOmETela", 
				new Object[]{"omUsuario", usuario.getOrganizacaoMilitar()},
				new Object[]{"tela",tela.getOrganizacaoMilitar().getTelas().get(tela.getIndexTelaAtual())});
		
		comboTela.forEach(tela->{
			organizacoesMilitares.add(tela.getOrganizacaoVinculadaParaTela());
		});
		
		//Lista os registros de nações amigas de acordo com a OM
		listarEAD();
	}
	
	/**
	 * Recupera os registros de acordo com a OM selecionada e seta em seu respectivo lugar
	 */
	public void carregarRegistro() {
		limpar();
		
		ensinosSelect = repository.encontrarComQueryNomeada(EnsinoADistancia.class, "ensinoADistancia.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", ensino.getOrganizacaoMilitar()});
		
		if (ensinosSelect.size() > 0)
			setEnsino(ensinosSelect.get(0));
	}
	
	/**
	 * Busca o registro de EAD e recupera as OMs Vinculadas caso seja DETMIL
	 */
	public void listarEAD() {
		organizacoesMilitares.forEach(listaOm->{
			List<EnsinoADistancia> lista = repository.encontrarComQueryNomeada(EnsinoADistancia.class, "ensinoADistancia.listarPorOMEPeriodo",
					new Object[]{"periodo", periodo},
					new Object[]{"organizacao", listaOm});
		
			ensinos.addAll(lista);
		});
		
		if (ensinos.size()>0)
			habilitaBotao=false;
	}
	
	/**
	 * Limpa Id, TotalAlunos
	 */
	public void limpar() {
		ensino.setId(null);
		ensino.setAlunoOf(null);
		ensino.setAlunoPr(null);
	}
	
	/**
	 * Exclui a instancia de EAD da base de dados de acordo com o id selecionado
	 * @param excluirEAD
	 */
	public void excluir(EnsinoADistancia excluirEAD) {
		super.executar(() -> {repository.apagar(EnsinoADistancia.class, excluirEAD.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEAD
	 */
	public void editar(EnsinoADistancia editarEAD) {		
		setEnsino(editarEAD);
		
		salvarOuAtualizar();
	}
	
	/**
	 * Salva no Banco de Dados as informações preenchidas pelo usuário.
	 */	
	public void salvarOuAtualizar() {
		super.executar(() -> {repository.adicionarOuMudar(ensino); init();}, SUCESSO, FALHA);	
		habilitaBotao=false;
	}
	
}