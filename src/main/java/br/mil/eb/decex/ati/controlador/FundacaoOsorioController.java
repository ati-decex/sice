package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.enumerado.SerieFundacaoOsorio;
import br.mil.eb.decex.ati.modelo.FundacaoOsorio;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro dos alunos dos colegios militares</p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("fundacaoOsorioController")
public class FundacaoOsorioController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
	private List<FundacaoOsorio> listaAlunosFundacao;
	private List<FundacaoOsorio> listaAlunos;
	private List<SerieFundacaoOsorio> serie;
	
	private FundacaoOsorio alunoFundacao;
	private boolean habilitaBotao;
	
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
	 * Lista de AlunoFundacao usada para armazenar os valores do banco de dados
	 * @return lista de AlunoFundacao
	 */
	public List<FundacaoOsorio> getListaAlunosFundacao() {
		return listaAlunosFundacao;
	}
	public void setListaAlunosFundacao(List<FundacaoOsorio> listaAlunosFundacao) {
		this.listaAlunosFundacao = listaAlunosFundacao;
	}
	
	/**
	 * carrega a lista de FundacaoOsorio  usada para armazenar os valores do banco de dados
	 * @return lista de FundacaoOsorio
	 */
	public List<FundacaoOsorio> getListaAlunos() {
		return listaAlunos;
	}
	public void setListaAlunos(List<FundacaoOsorio> listaAlunos) {
		this.listaAlunos = listaAlunos;
	}
	
	/**
	 * Armazena as informações de Alunos da Fundação Osorio
	 * @return AlunoFundacao
	 */
	public FundacaoOsorio getAlunoFundacao() {
		return alunoFundacao;
	}
	public void setAlunoFundacao(FundacaoOsorio alunoFundacao) {
		this.alunoFundacao = alunoFundacao;
	}

	/**
	 * Carrega a lista Serie com valores do Enum Serie
	 * @see br.mil.eb.decex.ati.enumerado.Serie
	 * @return Serie
	 */
	public List<SerieFundacaoOsorio> getSerie() {
		return serie;
	}
	
	@PostConstruct
	public void init() {
		alunoFundacao = new FundacaoOsorio();
		
		alunoFundacao.setPeriodo(periodo);
		listaAlunos = repository.encontrarComQueryNomeada(FundacaoOsorio.class, "fundacaoOsorio.listarPorPeriodo",
				new Object[]{"periodo", periodo});
		
		serie= new ArrayList<SerieFundacaoOsorio>();
		
		habilitaBotao=true;
		
		carregarSerie();
		
	}

	/**
	 * Carrega as series dos alunos da Funcação Osorio
	 */
	public void carregarSerie() {
		serie.addAll(Arrays.asList(SerieFundacaoOsorio.values()));
	}
	
	/**
	 * Limpa os valores de Id, Masculino e Feminino de FundacaoOsorio
	 */
	public void limpar() {
		alunoFundacao.setId(null);
		alunoFundacao.setMasculino(null);
		alunoFundacao.setFeminino(null);
	}

	/**
	 * Verifica se já existe um valor de alunosColegiosMilitares no banco de dados
	 */
	public void verificarRegistro() {
		limpar();
		
		listaAlunosFundacao = repository.encontrarComQueryNomeada(FundacaoOsorio.class, "fundacaoOsorio.listarPorSerie",
				new Object[]{"periodo", periodo},
				new Object[]{"serie", alunoFundacao.getSerieFundacao()});
		
		if (listaAlunosFundacao.size() > 0) {
			habilitaBotao=false;
			setAlunoFundacao(listaAlunosFundacao.get(0));
		}
	}
	
	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(FundacaoOsorio editarEfetivo) {		
		setAlunoFundacao(editarEfetivo);
		
		salvarOuAtualizar();
	}
	
	/**
	 * Exclui a instancia de OmVinculada da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	public void excluir(FundacaoOsorio excluirEfetivo) {
		super.executar(() -> {repository.apagar(FundacaoOsorio.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Salva um novo cadastro de OmVinculada no Banco de Dados
	 */
	public void salvarOuAtualizar() {		
		super.executar(() -> {repository.adicionarOuMudar(alunoFundacao); init();}, SUCESSO, FALHA);
		habilitaBotao = false;
	}
}
