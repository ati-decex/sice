package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.enumerado.TipoMilitaresNacoesAmigas;
import br.mil.eb.decex.ati.modelo.ComboTela;
import br.mil.eb.decex.ati.modelo.MilitaresNacoesAmigas;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro dos militares das Nações Amigas</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("nacoesAmigas")
public class MilitaresNacoesAmigasController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
	@Inject
	private TelasController tela;

	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<MilitaresNacoesAmigas> nacoesAmigasSelect;
	private List<MilitaresNacoesAmigas> nacoesAmigas;
	private List<ComboTela> comboTela;
	
	private OrganizacaoMilitar organizacaoMilitar;
	private MilitaresNacoesAmigas MNAInstrutor;
	private MilitaresNacoesAmigas MNAInstrutorAluno;
	private MilitaresNacoesAmigas MNAMonitor;
	private MilitaresNacoesAmigas MNAMonitorAluno;
	private MilitaresNacoesAmigas MNAEditar;
	private boolean habilitaBotao = true;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";

	/**
	 * Guarda os registros de Nações Amigas
	 * @return nacoesAmigasSelect List<MilitaresNacoesAmigas>
	 */
	public List<MilitaresNacoesAmigas> getNacoesAmigasSelect() {
		return nacoesAmigasSelect;
	}
	public void setNacoesAmigasSelect(List<MilitaresNacoesAmigas> nacoesAmigasSelect) {
		this.nacoesAmigasSelect = nacoesAmigasSelect;
	}

	/**
	 * Habilita a função de abilitar o botão apartir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
	}
	
	/**
	 * Usado para armazenar os valores selecionados para edição
	 * @return MilitaresNacoesAmigas
	 */
	public MilitaresNacoesAmigas getMNAEditar() {
		return MNAEditar;
	}
	public void setMNAEditar(MilitaresNacoesAmigas mNAEditar) {
		MNAEditar = mNAEditar;
	}

	/**
	 * Lista de MilitaresNacoesAmigas usada para armazenar os valores do banco de dados
	 * @return lista de MilitaresNacoesAmigas
	 */
	public List<MilitaresNacoesAmigas> getNacoesAmigas() {
		return nacoesAmigas;
	}
	public void setNacoesAmigas(List<MilitaresNacoesAmigas> nacoesAmigas) {
		this.nacoesAmigas = nacoesAmigas;
	}

	/**
	 * Carrega a lista de Organizações Militares Subordinadas 
	 * @return lista de Organizações Militares
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}
	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}
	
	/**
	 * Armazena as informações de MilitaresNacoesAmigas Intrutores
	 * @return MilitaresNacoesAmigas
	 */
	public MilitaresNacoesAmigas getMNAInstrutor() {
		return MNAInstrutor;
	}
	public void setMNAInstrutor(MilitaresNacoesAmigas mNAInstrutor) {
		MNAInstrutor = mNAInstrutor;
	}
	
	/**
	 * Armazena as informações de MilitaresNacoesAmigas Alunos de Intrutores
	 * @return MilitaresNacoesAmigas
	 */
	public MilitaresNacoesAmigas getMNAInstrutorAluno() {
		return MNAInstrutorAluno;
	}
	public void setMNAInstrutorAluno(MilitaresNacoesAmigas mNAInstrutorAluno) {
		MNAInstrutorAluno = mNAInstrutorAluno;
	}
	
	/**
	 * Armazena as informações de MilitaresNacoesAmigas Monitores
	 * @return MilitaresNacoesAmigas
	 */
	public MilitaresNacoesAmigas getMNAMonitor() {
		return MNAMonitor;
	}
	public void setMNAMonitor(MilitaresNacoesAmigas mNAMonitor) {
		MNAMonitor = mNAMonitor;
	}
	
	/**
	 * Armazena as informações de MilitaresNacoesAmigas Alunos de Monitores
	 * @return MilitaresNacoesAmigas
	 */
	public MilitaresNacoesAmigas getMNAMonitorAluno() {
		return MNAMonitorAluno;
	}
	public void setMNAMonitorAluno(MilitaresNacoesAmigas mNAMonitorAluno) {
		MNAMonitorAluno = mNAMonitorAluno;
	}
	
	/**
	 * carrega informações das organizações militares cadastradas
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}
	
	@PostConstruct
	public void init() {
		nacoesAmigas = new ArrayList<MilitaresNacoesAmigas>();
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		
		organizacaoMilitar = new OrganizacaoMilitar();
		MNAInstrutor = new MilitaresNacoesAmigas();
		MNAInstrutorAluno = new MilitaresNacoesAmigas();
		MNAMonitor = new MilitaresNacoesAmigas();
		MNAMonitorAluno = new MilitaresNacoesAmigas();
		
		MNAInstrutor.setPeriodo(periodo);
		MNAInstrutorAluno.setPeriodo(periodo);
		MNAMonitor.setPeriodo(periodo);
		MNAMonitorAluno.setPeriodo(periodo);
		
		comboTela = repository.encontrarComQueryNomeada(ComboTela.class, "ComboTela.porOmETela", 
				new Object[]{"omUsuario", usuario.getOrganizacaoMilitar()},
				new Object[]{"tela",tela.getOrganizacaoMilitar().getTelas().get(tela.getIndexTelaAtual())});
		
		comboTela.forEach(tela->{
			organizacoesMilitares.add(tela.getOrganizacaoVinculadaParaTela());
		});
		
		//Lista os registros de nações amigas de acordo com a OM
		listarNacoesAmigas();
		//Seta valores em registros de soltos de nações amigas
		organizarValores();
		//Zera os objetos, MNAInstrutor, MNAInstrutorAluno, MNAMonitor, MNAMonitorAluno
		zerarValores();
	}
	
	public void calcularFemininoMasculino(MilitaresNacoesAmigas nacoesAmigas) {
		MilitaresNacoesAmigas total = new MilitaresNacoesAmigas();
		
		total.setTotal(nacoesAmigas.getMasculino() + nacoesAmigas.getFeminino());
		
		MNAInstrutor.setTotal(nacoesAmigas.getTipoMilitarNacoesAmigas().equals(TipoMilitaresNacoesAmigas.OFICIAL) ? total.getTotal() : 0);
		MNAInstrutorAluno.setTotal(nacoesAmigas.getTipoMilitarNacoesAmigas().equals(TipoMilitaresNacoesAmigas.ALUNO_OF) ? total.getTotal() : 0);
		MNAMonitor.setTotal(nacoesAmigas.getTipoMilitarNacoesAmigas().equals(TipoMilitaresNacoesAmigas.PRACA) ? total.getTotal() : 0);
		MNAMonitorAluno.setTotal(nacoesAmigas.getTipoMilitarNacoesAmigas().equals(TipoMilitaresNacoesAmigas.ALUNO_PR) ? total.getTotal() : 0);
	}
	
	/**
	 * Seta os valores da Organização Militar e o tipo(Intrutor, Aluno de intrutor, Monitor ou Aluno de Monitor)
	 */
	public void organizarValores() {
		MNAInstrutor.setOrganizacaoMilitar(organizacaoMilitar);
		MNAInstrutor.setTipoMilitarNacoesAmigas(TipoMilitaresNacoesAmigas.OFICIAL);
		
		MNAInstrutorAluno.setOrganizacaoMilitar(organizacaoMilitar);
		MNAInstrutorAluno.setTipoMilitarNacoesAmigas(TipoMilitaresNacoesAmigas.ALUNO_OF);
		
		MNAMonitor.setOrganizacaoMilitar(organizacaoMilitar);
		MNAMonitor.setTipoMilitarNacoesAmigas(TipoMilitaresNacoesAmigas.PRACA);
		
		MNAMonitorAluno.setOrganizacaoMilitar(organizacaoMilitar);
		MNAMonitorAluno.setTipoMilitarNacoesAmigas(TipoMilitaresNacoesAmigas.ALUNO_PR);
	}
	
	/**
	 * Zera os objetos, MNAInstrutor, MNAInstrutorAluno, MNAMonitor, MNAMonitorAluno
	 */
	public void zerarValores() {
		MNAInstrutor.setFeminino(0);
		MNAInstrutor.setMasculino(0);
		MNAInstrutor.setTotal(0);
		MNAInstrutorAluno.setFeminino(0);
		MNAInstrutorAluno.setMasculino(0);
		MNAInstrutorAluno.setTotal(0);
		MNAMonitor.setFeminino(0);
		MNAMonitor.setMasculino(0);
		MNAMonitor.setTotal(0);
		MNAMonitorAluno.setFeminino(0);
		MNAMonitorAluno.setMasculino(0);
		MNAMonitorAluno.setTotal(0);
	}
	
	/**
	 * Busca o registro de EAD e recupera as OMs Vinculadas caso seja DETMIL 
	 */
	public void listarNacoesAmigas() {
		organizacoesMilitares.forEach(listaOm->{
			List<MilitaresNacoesAmigas> lista = repository.encontrarComQueryNomeada(MilitaresNacoesAmigas.class, "militaresNacoesAmigas.listarPorOM",
					new Object[]{"periodo", periodo},
					new Object[]{"organizacao", listaOm});
		
			nacoesAmigas.addAll(lista);
		});
		
		if (nacoesAmigas.size()>0)
			habilitaBotao=false;
	}
	
	/**
	 * Recupera os registros de acordo com a OM selecionada e seta em seu respectivo lugar
	 */
	public void carregarRegistro() {
		limpar();
		
		nacoesAmigasSelect = repository.encontrarComQueryNomeada(MilitaresNacoesAmigas.class, "militaresNacoesAmigas.listarPorOM",
				new Object[]{"organizacao", getOrganizacaoMilitar()},
				new Object[]{"periodo", periodo});
		
		if (nacoesAmigasSelect.size() > 0) {
			nacoesAmigasSelect.forEach(militar->{
				if (militar.getTipoMilitarNacoesAmigas().equals(TipoMilitaresNacoesAmigas.OFICIAL))
					setMNAInstrutor(militar);
				if (militar.getTipoMilitarNacoesAmigas().equals(TipoMilitaresNacoesAmigas.ALUNO_OF))
					setMNAInstrutorAluno(militar);
				if (militar.getTipoMilitarNacoesAmigas().equals(TipoMilitaresNacoesAmigas.PRACA))
					setMNAMonitor(militar);
				if (militar.getTipoMilitarNacoesAmigas().equals(TipoMilitaresNacoesAmigas.ALUNO_PR))
					setMNAMonitorAluno(militar);
			});
		}
			
		organizarValores();
	}
	
	/**
	 * Limpa Id, Masculino, Feminino e Total de MilitaresNacoesAmigas
	 */
	public void limpar() {
		MNAInstrutor.setId(null);
		MNAInstrutor.setTotal(null);
		MNAInstrutor.setMasculino(null);
		MNAInstrutor.setFeminino(null);
		
		MNAInstrutorAluno.setId(null);
		MNAInstrutorAluno.setTotal(null);
		MNAInstrutorAluno.setMasculino(null);
		MNAInstrutorAluno.setFeminino(null);
		
		MNAMonitor.setId(null);
		MNAMonitor.setTotal(null);
		MNAMonitor.setMasculino(null);
		MNAMonitor.setFeminino(null);
		
		MNAMonitorAluno.setId(null);
		MNAMonitorAluno.setTotal(null);
		MNAMonitorAluno.setMasculino(null);
		MNAMonitorAluno.setFeminino(null);
	}
	
	/**
	 * Prepara o salvar ou atualizar
	 */
	public void prepararDados() {		
		salvarOuAtualizar(MNAInstrutor);
		salvarOuAtualizar(MNAInstrutorAluno);
		salvarOuAtualizar(MNAMonitor);
		salvarOuAtualizar(MNAMonitorAluno);
		
		init();
		habilitaBotao=false;
	}
	
	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(MilitaresNacoesAmigas editarEfetivo) {		
		setMNAEditar(editarEfetivo);
		
		salvarOuAtualizar(MNAEditar);
		init();
	}
	
	/**
	 * Exclui a instancia de OmVinculada da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	public void excluir(MilitaresNacoesAmigas excluirEfetivo) {
		super.executar(() -> {repository.apagar(MilitaresNacoesAmigas.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Salva um novo cadastro de MilitaresNacoesAmigas no Banco de Dados
	 */
	public void salvarOuAtualizar(MilitaresNacoesAmigas MNA) {
		super.executar(() -> {repository.adicionarOuMudar(MNA);}, SUCESSO, FALHA);
	}

}
