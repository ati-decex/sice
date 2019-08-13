package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.enumerado.Forca;
import br.mil.eb.decex.ati.enumerado.TipoAlunosMilitarOutrasForcas;
import br.mil.eb.decex.ati.modelo.AlunosMilitarOutrasForcas;
import br.mil.eb.decex.ati.modelo.ComboTela;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro dos alunos militares de outras Forças </p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("alunosOutrasForcas")
public class AlunosMilitaresOutrasForcasController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
	@Inject
	private TelasController tela;

	private List<AlunosMilitarOutrasForcas> alunosMilitarOutrasForcas;
 	private List<OrganizacaoMilitar> organizacoesMilitares;
 	private List<AlunosMilitarOutrasForcas> alunos;
 	private List<AlunosMilitarOutrasForcas> listaAlunos;
 	private List<ComboTela> comboTela;
 	
 	private OrganizacaoMilitar subordinado;
	private AlunosMilitarOutrasForcas alunosMilitarPraca;
	private AlunosMilitarOutrasForcas alunosMilitarOficial;
	private Forca alunosOutraForca;
	private List<Forca> forcas;

	private boolean habilitaBotao = true;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";

	/**
	 * habilita a função de abilitar o botão apartir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
	}
	
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
	 * Carrega uma Lista de AlunosMilitarOutrasForcas usada para armazenar os valores do banco de dados
	 * @return lista de AlunosMilitarOutrasForcas
	 */
	public List<AlunosMilitarOutrasForcas> getListaAlunos() {
		return listaAlunos;
	}
	public void setListaAlunos(List<AlunosMilitarOutrasForcas> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}

	/**
	 * Lista de AlunosMilitarOutrasForcas usada para armazenar os valores do banco de dados
	 * @return lista de AlunosMilitarOutrasForcas
	 */
	public List<AlunosMilitarOutrasForcas> getAlunosMilitarOutrasForcas() {
		return alunosMilitarOutrasForcas;
	}
	public void setAlunosMilitarOutrasForcas(List<AlunosMilitarOutrasForcas> alunosMilitarOutrasForcas) {
		this.alunosMilitarOutrasForcas = alunosMilitarOutrasForcas;
	}
	
	/**
	 * Lista de OrganizacaoMilitar usada para armazenar os valores do banco de dados
	 * @return lista de OrganizacaoMilitar
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}
	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}
	
	/**
	 * Armazena as informações de AlunosMilitarOutrasForcas do tipo praça 
	 * @return AlunosMilitarOutrasForcas
	 */
	public AlunosMilitarOutrasForcas getAlunosMilitarPraca() {
		return alunosMilitarPraca;
	}
	public void setAlunosMilitarPraca(AlunosMilitarOutrasForcas alunosMilitarPraca) {
		this.alunosMilitarPraca = alunosMilitarPraca;
	}
	
	/**
	 * Armazena as informações de AlunosMilitarOutrasForcas do tipo oficial
	 * @return AlunosMilitarOutrasForcas
	 */
	public AlunosMilitarOutrasForcas getAlunosMilitarOficial() {
		return alunosMilitarOficial;
	}
	public void setAlunosMilitarOficial(AlunosMilitarOutrasForcas alunosMilitarOficial) {
		this.alunosMilitarOficial = alunosMilitarOficial;
	}

	/**
	 * Armazena as informações das subordinações de  organização militar  
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getSubordinado() {
		return subordinado;
	}
	public void setSubordinado(OrganizacaoMilitar subordinado) {
		this.subordinado = subordinado;
	}

	/**
	 * Carrega valores de Forca com valores do Enum forca
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Forca
	 */
	public Forca getAlunosOutraForca() {
		return alunosOutraForca;
	}
	public void setAlunosOutraForca(Forca alunosOutraForca) {
		this.alunosOutraForca = alunosOutraForca;
	}
	
	/**
	 * Carrega a lista forca com valores do Enum forca
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Forca
	 */
	public List<Forca> getForcas() {
		return forcas;
	}
	
	/**
	 * Lista de Alunos militares de outras forças  usada para armazenar os valores do banco de dados
	 * @return lista de AlunosMilitaresOutrasForcas
	 */
	public List<AlunosMilitarOutrasForcas> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<AlunosMilitarOutrasForcas> alunos) {
		this.alunos = alunos;
	}

	@PostConstruct
	public void init() {
		alunosMilitarPraca = new AlunosMilitarOutrasForcas();
		alunosMilitarOficial = new AlunosMilitarOutrasForcas();
		subordinado = new OrganizacaoMilitar();
		forcas= new ArrayList<Forca>();
		
		setAlunosOutraForca(null);
		
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		alunos = new ArrayList<AlunosMilitarOutrasForcas>();
	
		alunosMilitarPraca.setPeriodo(periodo);
		alunosMilitarOficial.setPeriodo(periodo);
		
		comboTela = repository.encontrarComQueryNomeada(ComboTela.class, "ComboTela.porOmETela", 
				new Object[]{"omUsuario", usuario.getOrganizacaoMilitar()},
				new Object[]{"tela",tela.getOrganizacaoMilitar().getTelas().get(tela.getIndexTelaAtual())});
		
		comboTela.forEach(tela->{
			organizacoesMilitares.add(tela.getOrganizacaoVinculadaParaTela());
		});
		
		listarAlunos();
	}
	
	/**
	 * carrega valores de uma lista de um enun de Forca
	 */
	public void carregarForca() {
		forcas.clear();
		forcas.addAll(Arrays.asList(Forca.values()));
	}
	
	/**
	 * Busca os registros de AlunoMilitaresOutrasForcas de cada Organização vinculada 
	 * @param List<OrganizacaoMilitar> organizacoes
	 */
	public void listarAlunos() {
		organizacoesMilitares.forEach(listaOm->{
			List<AlunosMilitarOutrasForcas> lista = repository.encontrarComQueryNomeada(AlunosMilitarOutrasForcas.class, "alunosMilitarOutrasForcas.listarPorPeriodo",
					new Object[]{"periodo", periodo},
					new Object[]{"organizacao", listaOm});
			
			if (lista.size()>0) {
				alunos.addAll(lista);
				habilitaBotao = false;
			}
		});
	}
	
	/**
	 * Seta os valores da Organização Militar e o tipo(Intrutor, Aluno de intrutor, Monitor ou Aluno de Monitor)
	 */
	public void organizarValores() {
		alunosMilitarPraca.setOrganizacaoMilitar(subordinado);
		alunosMilitarPraca.setTipoAlunosMilitarOutrasForcas(TipoAlunosMilitarOutrasForcas.PRACA);
		alunosMilitarPraca.setForca(alunosOutraForca);
		
		alunosMilitarOficial.setOrganizacaoMilitar(subordinado);
		alunosMilitarOficial.setTipoAlunosMilitarOutrasForcas(TipoAlunosMilitarOutrasForcas.OFICIAL);
		alunosMilitarOficial.setForca(alunosOutraForca);
	}

	/**
	 * Limpa Id, Masculino e Feminino de alunosMilitarOutrasForca
	 */
	public void limpar() {
		alunosMilitarPraca.setId(null);
		alunosMilitarPraca.setMasculino(null);
		alunosMilitarPraca.setFeminino(null);
		
		alunosMilitarOficial.setId(null);
		alunosMilitarOficial.setMasculino(null);
		alunosMilitarOficial.setFeminino(null);		
	}
	
	/**
	 * Verifica se já existe um valor de alunosMilitarOutrasForca no banco de dados e retornar as informaços na tela 
	 */
	public void carregarRegistro() {
		limpar();
		listaAlunos= repository.encontrarComQueryNomeada(AlunosMilitarOutrasForcas.class, "alunosMilitarOutrasForcas.listarPorFoca",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", subordinado},
				new Object[]{"forca", alunosOutraForca});
		
		if(listaAlunos.size() > 0) {
			listaAlunos.forEach(efetivo ->{
				if (efetivo.getTipoAlunosMilitarOutrasForcas().equals(TipoAlunosMilitarOutrasForcas.OFICIAL))
					setAlunosMilitarOficial(efetivo);
				if(efetivo.getTipoAlunosMilitarOutrasForcas().equals(TipoAlunosMilitarOutrasForcas.PRACA))
					setAlunosMilitarPraca(efetivo);;
			});
			habilitaBotao=false;
		}
		organizarValores();
	}
	
	/**
	 * Exclui a instancia de EfetivoRealizado da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	
	public void excluir(AlunosMilitarOutrasForcas excluirEfetivo) {
		super.executar(() -> {repository.apagar(AlunosMilitarOutrasForcas.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(AlunosMilitarOutrasForcas editarEfetivo) {		
		salvarOuAtualizar(editarEfetivo);
	}
	
	/**
	 * Prepara o salvar ou atualizar
	 */
	public void prepararDados() {
		organizarValores();
		salvarOuAtualizar(alunosMilitarPraca);
		salvarOuAtualizar(alunosMilitarOficial);
		init();
		habilitaBotao=false;
	}

	/**
	 * Salva um novo cadastro de alunos militares de outras forças no Banco de Dados
	 */
	public void salvarOuAtualizar(AlunosMilitarOutrasForcas AMOF) {
		super.executar(() -> {repository.adicionarOuMudar(AMOF);}, SUCESSO, FALHA);
	}
}
