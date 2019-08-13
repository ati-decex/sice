package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.mil.eb.decex.ati.enumerado.TipoProfessorMilitar;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.ProfessorMilitar;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro dos professores militar</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("professorMilitar")
public class ProfessorMilitarController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;

	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<ProfessorMilitar> professoresMilitar;
	private List<ProfessorMilitar> professores;
	
	private ProfessorMilitar professorMilitar;
	private boolean habilitaBotao=true;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";
	
	/**
	 * realiza a função de habilitar o botão 
	 * @return habilitaBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
	}

	/**
	 * Recupera lista de ProfessorMilitar usada para armazenar os valores do banco de dados
	 * @return lista de ProfessorMilitar
	 */
	public List<ProfessorMilitar> getProfessores() {
		return professores;
	}
	public void setProfessores(List<ProfessorMilitar> professores) {
		this.professores = professores;
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
	 * Lista de ProfessorMilitar usada para armazenar os valores do banco de dados
	 * @return lista de ProfessorMilitar
	 */
	public List<ProfessorMilitar> getProfessoresMilitar() {
		return professoresMilitar;
	}
	public void setProfessoresMilitar(List<ProfessorMilitar> professoresMilitar) {
		this.professoresMilitar = professoresMilitar;
	}
	
	/**
	 * Armazena as informações de ProfessorMilitar
	 * @return ProfessorMilitar
	 */
	public ProfessorMilitar getProfessorMilitar() {
		return professorMilitar;
	}
	public void setProfessorMilitar(ProfessorMilitar professorMilitar) {
		this.professorMilitar = professorMilitar;
	}

	/**
	 * Carrega a lista tipoProfessorMilitar com valores do Enum TipoProfessorMilitar
	 * @see br.mil.eb.decex.ati.enumerado.TipoProfessorMilitar
	 * @return TipoProfessorMilitar
	 */
	public List<TipoProfessorMilitar> getTipoProfessorMilitar() {
		return Arrays.asList(TipoProfessorMilitar.values());
	}
	
	@PostConstruct
	public void init() {
		professorMilitar = new ProfessorMilitar();
		
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		
		professorMilitar.setPeriodo(periodo);
		
		professores = repository.encontrarComQueryNomeada(ProfessorMilitar.class,"professorMilitar.listarPorPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		organizacoesMilitares.add(usuario.getOrganizacaoMilitar());
		
		if (professores.size()>0)
			habilitaBotao=false;
	}
	
	/**
	 * Limpa Id, Masculino e Feminino de professorMilitar
	 */
	public void limpar() {
		professorMilitar.setId(null);
		professorMilitar.setMasculino(null);
		professorMilitar.setFeminino(null);
	}

	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(ProfessorMilitar editarEfetivo) {		
		setProfessorMilitar(editarEfetivo);
		
		salvarOuAtualizar();
	}
	
	/**
	 * Exclui a instancia de EfetivoRealizado da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	public void excluir(ProfessorMilitar excluirEfetivo) {
		super.executar(() -> {repository.apagar(ProfessorMilitar.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Verifica se já existe um valor de ProfessorMilitar no banco de dados
	 */
	public void verificarRegistro() {
		limpar();
		
		professoresMilitar = repository.encontrarComQueryNomeada(ProfessorMilitar.class, "professorMilitar.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", professorMilitar.getOrganizacaoMilitar()},
				new Object[]{"tipoProfessorMilitar", professorMilitar.getTipoProfessorMilitar()});
		
		if (professoresMilitar.size() > 0) {
			habilitaBotao=false;
			setProfessorMilitar(professoresMilitar.get(0));
		}
	}
	
	/**
	 * Salva um novo cadastro de ProfessorMilitar no Banco de Dados
	 */
	public void salvarOuAtualizar() {
		super.executar(() -> {repository.adicionarOuMudar(professorMilitar); init();}, SUCESSO, FALHA);
		habilitaBotao=false;
	}
	
}