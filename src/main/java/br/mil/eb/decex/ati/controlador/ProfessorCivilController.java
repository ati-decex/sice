package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sun.xml.ws.util.StringUtils;

import br.mil.eb.decex.ati.enumerado.Carreira;
import br.mil.eb.decex.ati.enumerado.Escolaridade;
import br.mil.eb.decex.ati.enumerado.RegimeDeTrabalho;
import br.mil.eb.decex.ati.modelo.Disciplina;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.ProfessorCivil;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro dos professores civis </p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("professorCi")
public class ProfessorCivilController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
	@Autenticado
	private Usuario usuario;
		
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;

	private List<ProfessorCivil> listaProfessoresCivis;
	private List<ProfessorCivil> professoresCivis;
 	private List<OrganizacaoMilitar> organizacoesMilitares;
 	private List<Carreira> carreiras;
 	private List<Escolaridade> escolaridades;
 	private List<Disciplina> disciplinas;
 	private List<RegimeDeTrabalho> regimeTrabalhos;
 	 	
 	private ProfessorCivil professorCivil;
	private boolean habilitaBotao;
	
	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";

	/**
	 * Guarda os registros de professor civil 
	 * @return listaProfessoresCivis List<ProfessorCivil>
	 */
	public List<ProfessorCivil> getListaProfessoresCivis() {
		return listaProfessoresCivis;
	}
	public void setListaProfessoresCivis(List<ProfessorCivil> listaProfessoresCivis) {
		this.listaProfessoresCivis = listaProfessoresCivis;
	}

	/**
	 * habilita a função de abilitar o botão apartir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
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
	 * Lista de ProfessorCivil usada para armazenar os valores do banco de dados
	 * @return lista de ProfessorCivil
	 */		
	public List<ProfessorCivil> getProfessoresCivis() {
		return professoresCivis;
	}
	public void setProfessoresCivis(List<ProfessorCivil> professoresCivis) {
		this.professoresCivis = professoresCivis;
	}
	
	/**
	 * Armazena informações do ProfessorCivil para saulvar no banco de dados 
	 * @return ProfessorCivil
	 */
	public ProfessorCivil getProfessorCivil() {
		return professorCivil;
	}
	public void setProfessorCivil(ProfessorCivil professorCivil) {
		this.professorCivil = professorCivil;
	}
			
	/**
	 * Carrega a lista da tabela disciplina
	 * @returnf Disciplina
	 */
	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}
	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}
	
	
	/**
	 * Carrega a lista forca com valores do Enum forca
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Forca
	 */
	public List<Escolaridade> getEscolaridades(){
		return escolaridades;
	}
	
	/**
	 * Carrega a lista forca com valores do Enum forca
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Forca
	 */
	public List<RegimeDeTrabalho> getRegimesDeTrabalhos()  {
		return regimeTrabalhos;
	}
	
	/**
	 * Carrega a lista forca com valores do Enum forca
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Forca
	 */
	public List<Carreira> getCarreiras()  {
		return carreiras;
	}
	
	@PostConstruct
	public void init() {
		habilitaBotao=true;
		
		professorCivil = new ProfessorCivil();
		
		carreiras = new ArrayList<Carreira>();
		escolaridades = new ArrayList<Escolaridade>();
		regimeTrabalhos = new ArrayList<RegimeDeTrabalho>();
		disciplinas = new ArrayList<Disciplina>();
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		
		professorCivil.setPeriodo(periodo);
		
		organizacoesMilitares.add(usuario.getOrganizacaoMilitar());
		
		/*recupera informações cadastradas do banco de dados*/
		professoresCivis= repository.encontrarComQueryNomeada(ProfessorCivil.class, "professorCivil.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (professoresCivis.size() > 0)
			habilitaBotao=false;
	}
	
	/**
	 * Carrega a lista de Carreiras
	 */
	public void carregarCarreiras() {
		carreiras.clear();
		carreiras.addAll(Arrays.asList(Carreira.values()));
	}
	
	/**
	 * Carrega a lista de Escolaridades
	 */
	public void carregarEscolaridade() {
		escolaridades.clear();
		escolaridades.addAll(Arrays.asList(Escolaridade.values()));
	}
	
	/**
	 * Carrega a lista de RegimeDeTrabalho
	 */
	public void carregarRegimeDeTrabalho() {
		regimeTrabalhos.clear();
		regimeTrabalhos.addAll(Arrays.asList(RegimeDeTrabalho.values()));
	}
	
	/**
	 * Carrega a lista de Disciplinas 
	 */
	public void carregarDisciplina() {
		disciplinas=repository.encontrar(Disciplina.class);
		for (Disciplina d : disciplinas) {
			d.setNome(StringUtils.capitalize(d.getNome()));
		}
		
	}
	
	/**
	 * Recupera os registros de acordo com a OM selecionada e seta em seu respectivo lugar
	 */
	public void carregarRegistro() {
		limpar();
		
		listaProfessoresCivis = repository.encontrarComQueryNomeada(ProfessorCivil.class, "professorCivil.listarPorTodos", 
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()},				 
				new Object[]{"disciplina", professorCivil.getDisciplina()},
				new Object[]{"escolaridade", professorCivil.getEscolaridade()},
				new Object[]{"regimeDeTrabalho", professorCivil.getRegimeDeTrabalho()},
				new Object[]{"carreira", professorCivil.getCarreira()});
		
		if (listaProfessoresCivis.size() > 0) {
			setProfessorCivil(listaProfessoresCivis.get(0));
			habilitaBotao=false;
		}
	}
	
	/**
	 * Limpa Id, Masculino e Feminino
	 */
	public void limpar() {
		professorCivil.setId(null);
		professorCivil.setMasculino(null);
		professorCivil.setFeminino(null);
	}
	
	/**
	 * Exclui a instancia de EfetivoRealizado da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	public void excluir(ProfessorCivil excluirEfetivo) {
		super.executar(() -> {repository.apagar(ProfessorCivil.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}

	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(ProfessorCivil editarEfetivo) {		
		setProfessorCivil(editarEfetivo);
		salvarOuAtualizar();
	}
	
	/**
	 * Salva um novo cadastro de professores civis no Banco de Dados
	 */
	public void salvarOuAtualizar() {
		super.executar(() -> {repository.adicionarOuMudar(professorCivil); init();}, SUCESSO, FALHA);
		habilitaBotao = false;
	}
}