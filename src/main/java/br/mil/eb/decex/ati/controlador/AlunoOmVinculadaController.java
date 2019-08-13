package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.enumerado.TipoAlunoOmVinculada;
import br.mil.eb.decex.ati.modelo.AlunoOmVinculada;
import br.mil.eb.decex.ati.modelo.ComboTela;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro das OMs Vinculadas de cada OM</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("omVinculada")
public class AlunoOmVinculadaController extends BaseController {

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
	private List<AlunoOmVinculada> organizacoesVinculadas;
	private List<AlunoOmVinculada> listaAlunoOmVinculada;
	private List<AlunoOmVinculada> listaAlunos;
	private List<ComboTela> comboTela;
	
	private OrganizacaoMilitar subordinado;
	private AlunoOmVinculada alunosMilitarPraca;
	private AlunoOmVinculada alunosMilitarOficial;
	
	private Boolean habilitaBotao = true;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";

	/**
	 * Guarda os registros de OMDS 
	 * @return listaAlunos List<AlunosMilitaresOMDS>
	 */
	public List<AlunoOmVinculada> getListaAlunos() {
		return listaAlunos;
	}
	public void setListaAlunos(List<AlunoOmVinculada> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}
	
	/**
	 * Carrega informações de alunosMilitarPraca
	 * @return AlunoOmVinculada
	 */
	public AlunoOmVinculada getAlunosMilitarPraca() {
		return alunosMilitarPraca;
	}
	public void setAlunosMilitarPraca(AlunoOmVinculada alunosMilitarPraca) {
		this.alunosMilitarPraca = alunosMilitarPraca;
	}
	
	/**
	 * Carrega informações de alunosMilitarOficial
	 * @return AlunoOmVinculada
	 */
	public AlunoOmVinculada getAlunosMilitarOficial() {
		return alunosMilitarOficial;
	}
	public void setAlunosMilitarOficial(AlunoOmVinculada alunosMilitarOficial) {
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
	 * Recupera a lista de OmVinculada usada para armazenar os valores do banco de dados
	 * @return lista de OmVinculada
	 */
	public List<AlunoOmVinculada> getListaAlunoOmVinculada() {
		return listaAlunoOmVinculada;
	}

	/**
	 * Lista de OmVinculada usada para armazenar os valores do banco de dados
	 * @return lista de OmVinculada
	 */
	public List<AlunoOmVinculada> getOrganizacoesVinculadas() {
		return organizacoesVinculadas;
	}
	public void setOrganizacoesVinculadas(List<AlunoOmVinculada> organizacoesVinculadas) {
		this.organizacoesVinculadas = organizacoesVinculadas;
	}
	
	/**
	 * Carrega a lista de Organizações Militares Subordinadas 
	 * @return lista de Organizações Militares
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		return organizacoesMilitares;
	}
	public void setOrganizacaoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}
	
	/**
	 * habilita a função de abilitar o botão apartir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public Boolean getHabilitaBotao() {
		return habilitaBotao;
	}
	
	
	@PostConstruct
	public void init() {
		alunosMilitarOficial = new AlunoOmVinculada();
		alunosMilitarPraca = new AlunoOmVinculada();
		
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		listaAlunoOmVinculada = new ArrayList<AlunoOmVinculada>();
		
		alunosMilitarOficial.setPeriodo(periodo);
		alunosMilitarPraca.setPeriodo(periodo);
		
		comboTela = repository.encontrarComQueryNomeada(ComboTela.class, "ComboTela.porOmETela", 
				new Object[]{"omUsuario", usuario.getOrganizacaoMilitar()},
				new Object[]{"tela", tela.getOrganizacaoMilitar().getTelas().get(tela.getIndexTelaAtual())});
		
		comboTela.forEach(tela->{
			organizacoesMilitares.add(tela.getOrganizacaoVinculadaParaTela());
		});
		
		listarAlunosOMVinculadas();
	}
	
	/**
	 * Busca os registros de AlunoOmVinculada de cada Organização vinculada 
	 * @param List<OrganizacaoMilitar> organizacoes
	 */
	public void listarAlunosOMVinculadas() {
		organizacoesMilitares.forEach(listaOm->{
			List<AlunoOmVinculada> lista = repository.encontrarComQueryNomeada(AlunoOmVinculada.class, "alunoOmVinculada.listarPorOMEPeriodo",
					new Object[]{"periodo", periodo},
					new Object[]{"organizacao", listaOm});
			
			listaAlunoOmVinculada.addAll(lista);
		});
		
		if (listaAlunoOmVinculada.size() > 0)
			habilitaBotao=false;
		
		organizarValores();
	}
	
	public List<AlunoOmVinculada> listarAlunosOMVinculadas(OrganizacaoMilitar om,Periodo periodo) {
		listaAlunoOmVinculada = repository.encontrarComQueryNomeada(AlunoOmVinculada.class, "alunoOmVinculada.listarPorOMEPeriodo",
					new Object[]{"periodo", periodo},
					new Object[]{"organizacao", om});
		return listaAlunoOmVinculada;
		
	}
	
	/**
	 * Recupera os registros de acordo com a OM selecionada e seta em seu respectivo lugar
	 */
	public void carregarRegistro() {
		limpar();
		
		listaAlunos = repository.encontrarComQueryNomeada(AlunoOmVinculada.class, "alunoOmVinculada.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", subordinado});
		
		if (listaAlunos.size() > 0) {
			listaAlunos.forEach(efetivo ->{
				if (efetivo.getTipoAlunoOmVinculada().equals(TipoAlunoOmVinculada.OFICIAL))
					setAlunosMilitarOficial(efetivo);
				if(efetivo.getTipoAlunoOmVinculada().equals(TipoAlunoOmVinculada.PRACA))
					setAlunosMilitarPraca(efetivo);
			});
			habilitaBotao=false;
		}
		organizarValores();
	}
	
	/**
	 * Seta os valores da Organização Militar e o tipo(Intrutor, Aluno de intrutor, Monitor ou Aluno de Monitor)
	 */
	public void organizarValores() {
		alunosMilitarPraca.setOrganizacaoMilitar(subordinado);
		alunosMilitarPraca.setTipoAlunoOmVinculada(TipoAlunoOmVinculada.PRACA);
		
		alunosMilitarOficial.setOrganizacaoMilitar(subordinado);
		alunosMilitarOficial.setTipoAlunoOmVinculada(TipoAlunoOmVinculada.OFICIAL);
	}
	
	/**
	 * Limpa Id, Masculino e Feminino
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
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(AlunoOmVinculada editarEfetivo) {		
		salvarOuAtualizar(editarEfetivo);
	}
	
	/**
	 * Exclui a instancia de OmVinculada da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	public void excluir(AlunoOmVinculada excluirEfetivo) {
		super.executar(() -> {repository.apagar(AlunoOmVinculada.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Prepara o salvar ou atualizar
	 */
	public void prepararDados() {
		salvarOuAtualizar(alunosMilitarPraca);
		salvarOuAtualizar(alunosMilitarOficial);
		
		init();
		habilitaBotao=false;
	}
	
	/**
	 * Salva um novo cadastro de OmVinculada no Banco de Dados
	 */
	public void salvarOuAtualizar(AlunoOmVinculada AOMV) {		
		super.executar(() -> {repository.adicionarOuMudar(AOMV);}, SUCESSO, FALHA);
	}
}
