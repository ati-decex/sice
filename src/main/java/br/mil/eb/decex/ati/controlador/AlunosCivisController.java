package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.TipoEscolaridade;
import br.mil.eb.decex.ati.modelo.AlunosCivis;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro dos alunos civis dos colegios militares</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT STT
 * @version 1.0
 */
@ViewScoped
@Named("alunosCivisCM")
public class AlunosCivisController  extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<AlunosCivis> alunosCivis;
	
	private AlunosCivis alunoCivil;
	private boolean habilitaBotao;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";
	
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
	 * habilita a função de abilitar o botão a partir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
	}
	
	/**
	 * Armazena as informações de AlunoCivil
	 * @return AlunosCivis
	 */
	public AlunosCivis getAlunoCivil() {
		return alunoCivil;
	}
	public void setAlunoCivil(AlunosCivis alunoCivil) {
		this.alunoCivil = alunoCivil;
	}
	
	/**
	 * Armazena uma lista de informações de AlunoCivil
	 * @return List<AlunosCivis>
	 */
	public List<AlunosCivis> getAlunosCivis() {
		return alunosCivis;
	}
	public void setAlunosCivis(List<AlunosCivis> alunosCivis) {
		this.alunosCivis = alunosCivis;
	}
	
	/**
	 * Carrega a lista de tipos de Escolaridades
	 * @see br.mil.eb.decex.ati.enumerado.TipoEscolaridade
	 * @return TipoEscolaridade
	 */
	public List<TipoEscolaridade> getTipoEscolaridade() {
		return Arrays.asList(TipoEscolaridade.values());
	}
	
	@PostConstruct
	public void init() {
		limpar();
		
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		organizacoesMilitares.add(usuario.getOrganizacaoMilitar());
		
		//habilita o botão avançar
		habilitaBotao=true;
		
		recuperarRegistros();
	}
	
	/**
	 * Limpa o objeto alunoCivil
	 */
	public void limpar() {
		alunoCivil = new AlunosCivis();
		alunoCivil.setPeriodo(periodo);
	}
	
	/**
	 * Recupera os registro no banco de dados de acordo com o periodo e OM do usuário
	 */
	public void recuperarRegistros() {
		alunosCivis = repository.encontrarComQueryNomeada(AlunosCivis.class, "alunosCivis.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
	}
	
	/**
	 * Recupera os registro no banco de dados de acordo com os dados selecionados
	 */
	public void verificarRegistro() {
		List<AlunosCivis> listaAlunosCivis = repository.encontrarComQueryNomeada(AlunosCivis.class, "alunosCivis.listarPorOMEPeriodoETipoEscolaridade",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", alunoCivil.getOrganizacaoMilitar()},
				new Object[]{"tipoEscolaridade",alunoCivil.getTipoEscolaridade()});
		
		if (listaAlunosCivis.size() > 0) 
			setAlunoCivil(listaAlunosCivis.get(0));
	}
	
	/**
	 * Exclui a instancia de AlunosCivis da base de dados de acordo com o id selecionado
	 * @param alunos
	 */
	public void excluir(AlunosCivis alunos) {
		super.executar(() -> {repository.apagar(AlunosCivis.class, alunos.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Edita as informações ja cadastrada no Banco de Dados pelo usuário.
	 * @param alunos
	 */
	public void editar(AlunosCivis alunos) {
		salvarOuAtualizar(alunos);
		
		recuperarRegistros();
	}
	
	/**
	 * Prepara o salvar
	 */
	public void salvar() {
		salvarOuAtualizar(alunoCivil);
	}
	
	/**
	 * Salva ou atualiza um registro no Banco de Dados
	 */
	public void salvarOuAtualizar(AlunosCivis alunos) {
		super.executar(() -> {repository.adicionarOuMudar(alunos); init();}, SUCESSO, FALHA);
		habilitaBotao=false;
	}
	
}
