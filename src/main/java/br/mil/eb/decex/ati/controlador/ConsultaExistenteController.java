package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.ConsultaEfetivo;
import br.mil.eb.decex.ati.modelo.AlunoOmVinculada;
import br.mil.eb.decex.ati.modelo.AlunosColegioMilitar;
import br.mil.eb.decex.ati.modelo.AlunosMilitarOutrasForcas;
import br.mil.eb.decex.ati.modelo.AlunosMilitaresOMDS;
import br.mil.eb.decex.ati.modelo.EfetivoInstrutorMonitor;
import br.mil.eb.decex.ati.modelo.EfetivoRealizado;
import br.mil.eb.decex.ati.modelo.EnsinoADistancia;
import br.mil.eb.decex.ati.modelo.FundacaoOsorio;
import br.mil.eb.decex.ati.modelo.MilitaresNacoesAmigas;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.ProfessorCivil;
import br.mil.eb.decex.ati.modelo.ProfessorMilitar;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.servico.PeriodoService;

/**
 * Permite a consulta do efetivo Existente de cada OM</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("consulta")
public class ConsultaExistenteController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	private PeriodoService service;
	
	private List<OrganizacaoMilitar> oms;
	private List<Periodo> periodos;
	private List<EfetivoRealizado> efetivoExistentes;
	private List<AlunosColegioMilitar> colegiosMilitares;
	private List<EnsinoADistancia> ensinoADistancia;
	private List<ProfessorCivil> professorCivil;
	private List<EfetivoInstrutorMonitor> efetivoInstrutorMonitor;
	private List<ProfessorMilitar> professorMilitar;
	private List<AlunoOmVinculada> alunoOmVinculada;
	private List<MilitaresNacoesAmigas> militaresNacoesAmigas;
	private List<AlunosMilitaresOMDS> militaresOmds;
	private List<AlunosMilitarOutrasForcas> alunosMilitarOutrasForcas;
	private List<FundacaoOsorio> fundacaoOsorio;	
	
	private OrganizacaoMilitar omSelecionada;
	private Periodo periodoSelecionado;
	private ConsultaEfetivo telaSelecionada;
	private AlunosColegioMilitar colegioMilitarSelecionado;
	private EnsinoADistancia ensinoADistanciaSelecionado;
	private ProfessorCivil professorCivilSelecionado;
	private EfetivoInstrutorMonitor efetivoInstrutorMonitorSelecionado;
	private ProfessorMilitar professorMilitarSelecionado;
	private AlunoOmVinculada alunoOmVinculadaSelecionado;
	private MilitaresNacoesAmigas militaresNacoesAmigasSelecionado;
	private AlunosMilitaresOMDS militaresOmdsSelecionado;
	private AlunosMilitarOutrasForcas alunosMilitarOutrasForcasSelecionado;
	private FundacaoOsorio fundacaoOsorioSelecionado;
		
	private boolean visibleExistente = false;
	private boolean visibleColegioMilitar = false;
	private boolean visibleEnsinoADistancia = false;
	private boolean visibleProfessorCivil = false;	
	private boolean visibleEfetivoInstrutorMonitor = false;
	private boolean visibleProfessorMilitar = false;
	private boolean visibleAlunoOmVinculada = false;
	private boolean visibleMilitaresNacoesAmigas = false;
	private boolean visibleMilitaresOmds = false;
	private boolean visibleAlunosMilitarOutrasForcas=false;
	private boolean visibleFundacaoOsorio=false;
		
	
	/**
	 * Atribui true ou false para visualização do painel de Alunos de Educação a Distância
	 * @author ASP MARQUES	
	 * @return visible
	 */
	public boolean isVisibleEnsinoADistancia() {
		return visibleEnsinoADistancia;
	}
	public void setVisibleEnsinoADistancia(boolean visibleEnsinoADistancia) {
		this.visibleEnsinoADistancia = visibleEnsinoADistancia;
	}
	
	/**
	 * Atribui true ou false para visualização do painel de Professores Civis
	 * @author ASP MARQUES	
	 * @return visible
	 */
	public boolean isVisibleProfessorCivil() {
		return visibleProfessorCivil;
	}
	public void setVisibleProfessorCivil(boolean visibleProfessorCivil) {
		this.visibleProfessorCivil = visibleProfessorCivil;
	}
	/**
	 * Atribui true ou false para visualização do painel de Instrutores / Monitores
	 * @author ASP MARQUES	
	 * @return visible
	 */
	public boolean isVisibleEfetivoInstrutorMonitor() {
		return visibleEfetivoInstrutorMonitor;
	}
	public void setVisibleEfetivoInstrutorMonitor(boolean visibleEfetivoInstrutorMonitor) {
		this.visibleEfetivoInstrutorMonitor = visibleEfetivoInstrutorMonitor;
	}
		
	
	/**
	 * Atribui true ou false para visualização do painel de Professores Militares
	 * @author ASP MARQUES	
	 * @return visible
	 */
	public boolean isVisibleProfessorMilitar() {
		return visibleProfessorMilitar;
	}
	public void setVisibleProfesorMilitar(boolean visibleProfessorMilitar) {
		this.visibleProfessorMilitar = visibleProfessorMilitar;
	}
	
	/**
	 * Atribui true ou false para visualização do painel de Alunos de OM Vinculada
	 * @author ASP MARQUES	
	 * @return visible
	 */
	public boolean isVisibleAlunoOmVinculada() {
		return visibleAlunoOmVinculada;
	}
	public void setVisibleAlunoOmVinculada(boolean visibleAlunoOmVinculada) {
		this.visibleAlunoOmVinculada = visibleAlunoOmVinculada;
	}
	/**
	 * Atribui true ou false para visualização do painel de Alunos de Nações Amigas
	 * @author ASP MARQUES	
	 * @return visible
	 */	
	public boolean isVisibleMilitaresNacoesAmigas() {
		return visibleMilitaresNacoesAmigas;
	}
	public void setVisibleMilitaresNacoesAmigas(boolean visibleMilitaresNacoesAmigas) {
		this.visibleMilitaresNacoesAmigas = visibleMilitaresNacoesAmigas;
	}
	/**
	 * Atribui true ou false para visualização do painel de Alunos Militares (OMDS)
	 * @author ASP MARQUES	
	 * @return visible
	 */	
	public boolean isVisibleMilitaresOmds() {
		return visibleMilitaresOmds;
	}
	public void setVisibleMilitaresOmds(boolean visibleMilitaresOmds) {
		this.visibleMilitaresOmds = visibleMilitaresOmds;
	}
	/**
	 * Atribui true ou false para visualização do painel de Alunos de Outras Forças
	 * @author ASP MARQUES	
	 * @return visible
	 */	
	public boolean isVisibleAlunosMilitarOutrasForcas() {
		return visibleAlunosMilitarOutrasForcas;
	}
	public void setVisibleAlunosMilitarOutrasForcas(boolean visibleAlunosMilitarOutrasForcas) {
		this.visibleAlunosMilitarOutrasForcas = visibleAlunosMilitarOutrasForcas;
	}
	public void setVisibleExistente(boolean visibleExistente) {
		this.visibleExistente = visibleExistente;
	}
	/**
	 * Atribui true ou false para visualização do painel de Alunos da Fundação Osorio
	 * @author ASP MARQUES	
	 * @return visible
	 */	
	public boolean isVisibleFundacaoOsorio() {
		return visibleFundacaoOsorio;
	}
	public void setVisibleFundacaoOsorio(boolean visibleFundacaoOsorio) {
		this.visibleFundacaoOsorio = visibleFundacaoOsorio;
	}
	/**
	 * Seta true ou false para visualização do panel	
	 * @return visible
	 */
	public boolean isVisibleExistente() {
		return visibleExistente;
	}	
	
	/**
	 * Seta true ou false para visualização do panel	
	 * @return visible
	 */
	public boolean isVisibleColegioMilitar() {
		return visibleColegioMilitar;
	}
	public void setVisibleColegioMilitar(boolean visibleColegioMilitar) {
		this.visibleColegioMilitar = visibleColegioMilitar;
	}

	/**visibleAlunosMilitarOutrasForcas
	 * Lista de telas para escolha do usuário solicitante
	 * @return Efetivo de Tela para consulta
	 */	
	public List<ConsultaEfetivo> getTelas() {
		return Arrays.asList(ConsultaEfetivo.values());
	}

	/**
	 * Guarda o periodo selecionado
	 * @return periodoSelecionado
	 */
	public Periodo getPeriodoSelecionado() {
		return periodoSelecionado;
	}
	public void setPeriodoSelecionado(Periodo periodoSelecionado) {
		this.periodoSelecionado = periodoSelecionado;
	}
	
	/**
	 * Guarda a organização militar selecionada
	 * @return omSelecionada
	 */
	public OrganizacaoMilitar getOmSelecionada() {
		return omSelecionada;
	}
	public void setOmSelecionada(OrganizacaoMilitar omSelecionada) {
		this.omSelecionada = omSelecionada;
	}

	/**
	 * Armazena a tela selecionada
	 * @return telaSelecionada
	 */
	public ConsultaEfetivo getTelaSelecionada() {
		return telaSelecionada;
	}
	public void setTelaSelecionada(ConsultaEfetivo telaSelecionada) {
		this.telaSelecionada = telaSelecionada;
	}

	/**
	 * Guarda a lista de organização militar
	 * @return oms
	 */
	public List<OrganizacaoMilitar> getOms() {
		return oms;
	}

	/**
	 * Guarda a lista de periodos
	 * @return periodos
	 */
	public List<Periodo> getPeriodos() {
		return periodos;
	}

	/**
	 * Guarda a lista de efetivo existente
	 * @return efetivoExistentes
	 */
	public List<EfetivoRealizado> getEfetivoExistentes() {
		return efetivoExistentes;
	}
	/**
	 * Armazena o Aluno de EAD Selecionado
	 * @author ASP MARQUES
	 * @return ensinoADistanciaSelecionado
	 */
	public EnsinoADistancia getEnsinoADistanciaSelecionado() {
		return ensinoADistanciaSelecionado;
	}
	public void setEnsinoADistanciaSelecionado(EnsinoADistancia ensinoADistanciaSelecionado) {
		this.ensinoADistanciaSelecionado = ensinoADistanciaSelecionado;
	}

	/**
	 * Guarda a lista de Alunos de EAD
	 * @author ASP MARQUES
	 * @return Alunos de EAD
	 */
	public List<EnsinoADistancia> getEnsinoADistancia() {
		return ensinoADistancia;
	}
		
	/**
	 * Armazena Professor Civil Selecionado
	 * @author ASP MARQUES
	 * @return ensinoProfessorCivilSelecionado
	 */
	public ProfessorCivil getProfessorCivilSelecionado() {
		return professorCivilSelecionado;
	}
	public void setProfessorCivil(ProfessorCivil professorCivilSelecionado) {
		this.professorCivilSelecionado = professorCivilSelecionado;
	}
	/**
	 * Guarda a lista de Professores Civis
	 * @author ASP MARQUES
	 * @return professorCivil
	 */
	public List<ProfessorCivil> getProfessorCivil() {
		return professorCivil;
	}
	
	/**
	 * Armazena Monitor / Instrutor Selecionado
	 * @author ASP MARQUES
	 * @return efetivoInstrutorMonitorSelecionado
	 */
	public EfetivoInstrutorMonitor getEfetivoInstrutorMonitorSelecionado() {
		return efetivoInstrutorMonitorSelecionado;
	}
	public void setEfetivoInstrutorMonitor(EfetivoInstrutorMonitor efetivoInstrutorMonitorSelecionado) {
		this.efetivoInstrutorMonitorSelecionado = efetivoInstrutorMonitorSelecionado;
	}
	/**
	 * Guarda a lista de Monitor / Instrutor
	 * @author ASP MARQUES
	 * @return efetivoInstrutorMonitor
	 */
	public List<EfetivoInstrutorMonitor> getEfetivoInstrutorMonitor() {
		return efetivoInstrutorMonitor;
	}
	
	/**
	 * Armazena Professor Militar Selecionado
	 * @author ASP MARQUES
	 * @return professorMilitarSelecionado
	 */
	public ProfessorMilitar getProfessorMilitarSelecionado() {
		return professorMilitarSelecionado;
	}
	public void setProfessorMilitar(ProfessorMilitar professorMilitarSelecionado) {
		this.professorMilitarSelecionado = professorMilitarSelecionado;
	}
	/**
	 * Guarda a lista de Professores Militar
	 * @author ASP MARQUES
	 * @return Professor Militar
	 */
	public List<ProfessorMilitar> getProfessorMilitar() {
		return professorMilitar;
	}
	/*---------------------------------------------------------------------*/
	/**
	 * Armazena Alunos de OM Vinculada Selecionado
	 * @author ASP MARQUES
	 * @return alunoOmVinculadaSelecionado
	 */
	public AlunoOmVinculada getAlunoOmVinculadaSelecionado() {
		return alunoOmVinculadaSelecionado;
	}
	public void setAlunoOmVinculada(AlunoOmVinculada alunoOmVinculadaSelecionado) {
		this.alunoOmVinculadaSelecionado = alunoOmVinculadaSelecionado;
	}
	/**
	 * Guarda a lista de Professores Militar
	 * @author ASP MARQUES
	 * @return alunoOmVinculada
	 */
	public List<AlunoOmVinculada> getAlunoOmVinculada() {
		return alunoOmVinculada;
	}
	/*---------------------------------------------------------------------*/
	/**
	 * Armazena Alunos de Nações Militares Selecionado
	 * @author ASP MARQUES
	 * @return militaresNacoesAmigasSelecionado
	 */
	public MilitaresNacoesAmigas getMilitaresNacoesAmigasSelecionado() {
		return militaresNacoesAmigasSelecionado;
	}
	public void setMilitaresNacoesAmigas(MilitaresNacoesAmigas militaresNacoesAmigasSelecionado) {
		this.militaresNacoesAmigasSelecionado = militaresNacoesAmigasSelecionado;
	}
	/**
	 * Guarda a lista de Alunos de Nações Militares
	 * @author ASP MARQUES
	 * @return militaresNacoesAmigas
	 */
	public List<MilitaresNacoesAmigas> getMilitaresNacoesAmigas() {
		return militaresNacoesAmigas;
	}
	/*---------------------------------------------------------------------*/
	/**
	 * Armazena Alunos Militares (OMDS)
	 * @author ASP MARQUES
	 * @return militaresOmdsSelecionado
	 */
	public AlunosMilitaresOMDS getAlunosMilitaresOMDSSelecionado() {
		return militaresOmdsSelecionado;
	}
	public void setAlunosMilitaresOMDS(AlunosMilitaresOMDS militaresOmdsSelecionado) {
		this.militaresOmdsSelecionado = militaresOmdsSelecionado;
	}
	/**
	 * Guarda a lista de Alunos Militares (OMDS)
	 * @author ASP MARQUES
	 * @return militaresOmds
	 */
	public List<AlunosMilitaresOMDS> getAlunosMilitaresOMDS() {
		return militaresOmds;
	}
	/*---------------------------------------------------------------------*/
	/**
	 * Armazena Alunos de Outras Forças
	 * @author ASP MARQUES
	 * @return alunosMilitarOutrasForcasSelecionado
	 */
	public AlunosMilitarOutrasForcas getAlunosMilitarOutrasForcasSelecionado() {
		return alunosMilitarOutrasForcasSelecionado;
	}
	public void setAlunosMilitarOutrasForcas(AlunosMilitarOutrasForcas alunosMilitarOutrasForcasSelecionado) {
		this.alunosMilitarOutrasForcasSelecionado = alunosMilitarOutrasForcasSelecionado;
	}
	/**
	 * Guarda a lista de Alunos de Outras Forças
	 * @author ASP MARQUES
	 * @return alunosMilitarOutrasForcas
	 */
	public List<AlunosMilitarOutrasForcas> getAlunosMilitarOutrasForcas() {
		return alunosMilitarOutrasForcas;
	}
	/*---------------------------------------------------------------------*/
	/**
	 * Armazena Alunos da Fundação Osorio
	 * @author ASP MARQUES
	 * @return fundacaoOsorioSelecionado
	 */
	public FundacaoOsorio getFundacaoOsorioSelecionado() {
		return fundacaoOsorioSelecionado;
	}
	public void setFundacaoOsorio(FundacaoOsorio fundacaoOsorioSelecionado) {
		this.fundacaoOsorioSelecionado = fundacaoOsorioSelecionado;
	}
	/**
	 * Guarda a lista de Alunos da Fundação Osorio
	 * @author ASP MARQUES
	 * @return alunosMilitarOutrasForcas
	 */
	public List<FundacaoOsorio> getFundacaoOsorio() {
		return fundacaoOsorio;
	}
	/*---------------------------------------------------------------------*/
	/**
	 * Armazena o Colégio Militar Selecionado
	 * @return colegioMilitarSelecionado
	 */
	
	public AlunosColegioMilitar getColegioMilitarSelecionado() {
		return colegioMilitarSelecionado;
	}
	public void setColegioMilitarSelecionado(AlunosColegioMilitar colegioMilitarSelecionado) {
		this.colegioMilitarSelecionado = colegioMilitarSelecionado;
	}

	/**
	 * Guarda a lista do efetivo de Colégio Militar
	 * @return colegiosMilitares
	 */
	public List<AlunosColegioMilitar> getColegiosMilitares() {
		return colegiosMilitares;
	}

	@PostConstruct
	public void init() {
		oms = new ArrayList<OrganizacaoMilitar>();
		periodos = new ArrayList<Periodo>();
		efetivoExistentes = new ArrayList<EfetivoRealizado>();
				
		zerarPainel();
	}
	
	/**
	 * Carrega as Oms
	 */
	public void consultarOm() {
		zerarPainel();
		limparPeriodo();
		limparOm();
		efetivoExistentes.clear();		
		oms = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class, "OrganizacaoMilitar.consultaOms");
	}
	
	/**
	 * Carrega os Periodos
	 */
	public void consultarPeriodo() {
		limparPeriodo();
		efetivoExistentes.clear();
		
		periodos.addAll(service.carregarTodosOsPeriodos());
	}
	
	/**
	 * Limpa a OM selecionado e a OM
	 */
	public void limparOm() {
		omSelecionada = new OrganizacaoMilitar();
		oms.clear();		
	}
	
	/**
	 * Limpa o periodo selecionado e o periodo
	 */
	public void limparPeriodo() {
		periodoSelecionado = new Periodo();
		periodos.clear();		
	}
	
	/**
	 * Seta todos os booleans para false
	 */
	public void zerarPainel() {
		visibleExistente = false;
		visibleColegioMilitar = false;
		visibleEnsinoADistancia=false;
		visibleProfessorCivil=false;
		visibleEfetivoInstrutorMonitor=false;
		visibleProfessorMilitar=false;
		visibleAlunoOmVinculada=false;
		visibleMilitaresNacoesAmigas=false;
		visibleMilitaresOmds=false;
		visibleAlunosMilitarOutrasForcas=false;
		visibleFundacaoOsorio=false;
	}
	
	/**
	 * Verifica qual tela foi escolhida e executa seu método
	 */
	public void verificar() {
		zerarPainel();
		
		if (telaSelecionada.equals(ConsultaEfetivo.EXISTENTE)) {
			visibleExistente = true;
			consultarExistente();
		}
		
		if (telaSelecionada.equals(ConsultaEfetivo.ALUNOSCOLEGIOMILITAR)) {
			visibleColegioMilitar = true;
			consultarColegioMilitar();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.ENSINOADISTANCIA)) {
			visibleEnsinoADistancia = true;
			consultarEnsinoEAD();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.PROFESSORESCIVIS)) {
			visibleProfessorCivil = true;
			consultarProfessorCivil();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.INSTRUTORESMONITORES)) {
			visibleEfetivoInstrutorMonitor = true;
			consultarEfetivoInstrutorMonitor();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.PROFESSORESMILITARES)) {
			visibleProfessorMilitar = true;
			consultarProfessorMilitar();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.ALUNOSOMVINCULADAS)) {
			visibleAlunoOmVinculada = true;
			consultarAlunoOmVinculada();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.ALUNOSNACOESAMIGAS)) {
			visibleMilitaresNacoesAmigas = true;
			consultarMilitaresNacoesAmigas();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.ALUNOSMILITARES)) {
			visibleMilitaresOmds = true;
			consultarMilitaresOmds();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.ALUNOSOUTRASFORCAS)) {
			visibleAlunosMilitarOutrasForcas = true;
			consultarAlunosMilitarOutrasForcas();
		}
		if (telaSelecionada.equals(ConsultaEfetivo.ALUNOSFUNDACAOOSORIO)) {
			visibleFundacaoOsorio = true;
			consultarFundacaoOsorio();
		}
	}
	
	/**
	 * Realiza a consulta de efetivo existente passando o periodo e a organização militar
	 */
	public void consultarExistente() {
		if (efetivoExistentes != null)
			efetivoExistentes.clear();
		
		/* Recupera uma lista com todo o efetivo Existente cadastrado */
		efetivoExistentes = repository.encontrarComQueryNomeada(EfetivoRealizado.class, "EfetivoRealizado.linhaConfiguracaoPorPeriodo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"om", omSelecionada});
	}
	
	/**
	 * Realiza a consulta de efetivo de Colégio Militar passando o periodo e a organização militar
	 */
	public void consultarColegioMilitar() {
		if (colegiosMilitares != null)
			colegiosMilitares.clear();
		
		/* Recupera uma lista com todo o efetivo de Colégio Militar cadastrado */
		colegiosMilitares = repository.encontrarComQueryNomeada(AlunosColegioMilitar.class, "alunosColegioMilitar.listarPorPeriodo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	/**
	 * Realiza a consulta de Alunos de EAD passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarEnsinoEAD() {
		if (ensinoADistancia != null)
			ensinoADistancia.clear();
		
		/* Recupera uma lista com todo os alunos de EAD cadastrados 
		 * @author ASP MARQUES	
		 * */
		ensinoADistancia = repository.encontrarComQueryNomeada(EnsinoADistancia.class, "ensinoADistancia.listarPorOMEPeriodo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	
	/**
	 * Realiza a consulta de Professor Civil passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarProfessorCivil() {
		if (professorCivil != null)
			professorCivil.clear();
		
		/* Recupera uma lista com todo os professores civis
		 * @author ASP MARQUES	
		 * */
		professorCivil = repository.encontrarComQueryNomeada(ProfessorCivil.class, "professorCivil.listarPorOMEPeriodo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	
	/**
	 * Realiza a consulta de Monitor/Instrutor passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarEfetivoInstrutorMonitor() {
		if (efetivoInstrutorMonitor != null)
			efetivoInstrutorMonitor.clear();
		
		/* Recupera uma lista com todo os Monitores / Instrutores
		 * @author ASP MARQUES	
		 * */
		efetivoInstrutorMonitor = repository.encontrarComQueryNomeada(EfetivoInstrutorMonitor.class, "efetivoInstrutorMonitor.listarPorOMSuperiorEPeriodo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	/**
	 * Realiza a consulta de Professores Militares passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarProfessorMilitar() {
		if (professorMilitar != null)
			professorMilitar.clear();
		
		/* Recupera uma lista com todo os Professores Militares
		 * @author ASP MARQUES	
		 * */
		professorMilitar = repository.encontrarComQueryNomeada(ProfessorMilitar.class, "professorMilitar.listarPorOMEPeriodoSemTipo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	/**
	 * Realiza a consulta de Alunos de OM Vinculadas passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarAlunoOmVinculada() {
		if (alunoOmVinculada != null)
			alunoOmVinculada.clear();
		
		/* Recupera uma lista com todo os Alunos de OM Vinculadas
		 * @author ASP MARQUES	
		 * */
		alunoOmVinculada = repository.encontrarComQueryNomeada(AlunoOmVinculada.class, "alunoOmVinculada.listarPorOMEPeriodo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	 /* Realiza a consulta de Alunos Nações Amigas passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarMilitaresNacoesAmigas() {
		if (militaresNacoesAmigas != null)
			militaresNacoesAmigas.clear();
		
		/* Recupera uma lista com todo os alunos de nações amigas
		 * @author ASP MARQUES	
		 * */
		militaresNacoesAmigas = repository.encontrarComQueryNomeada(MilitaresNacoesAmigas.class, "militaresNacoesAmigas.listarPorOM", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	/* Realiza a consulta de Alunos Militares (OMDS) passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarMilitaresOmds() {
		if (militaresOmds != null)
			militaresOmds.clear();
		
		/* Recupera uma lista com todo os alunos de nações amigas
		 * @author ASP MARQUES	
		 * */
		militaresOmds = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class, "alunosMilitaresOMDS.listarPorPeriodo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	/* Realiza a consulta de Alunos Outras Forças passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarAlunosMilitarOutrasForcas() {
		if (alunosMilitarOutrasForcas != null)
			alunosMilitarOutrasForcas.clear();
		
		/* Recupera uma lista com todo os alunos de outras forças
		 * @author ASP MARQUES	
		 * */
		alunosMilitarOutrasForcas = repository.encontrarComQueryNomeada(AlunosMilitarOutrasForcas.class, "alunosMilitarOutrasForcas.listarPorPeriodo", 
				new Object[]{"periodo", periodoSelecionado},
				new Object[]{"organizacao", omSelecionada});
	}
	/* Realiza a consulta de Alunos da Fundasção Osorio passando o periodo e a organização militar
	 * @author ASP MARQUES
	 */
	public void consultarFundacaoOsorio() {
		if (fundacaoOsorio != null)
			fundacaoOsorio.clear();
		
		/* Recupera uma lista com todo os alunos de outras forças
		 * @author ASP MARQUES	
		 * */
		fundacaoOsorio = repository.encontrarComQueryNomeada(FundacaoOsorio.class, "fundacaoOsorio.listarPorPeriodo", 
				new Object[]{"periodo", periodoSelecionado});
	}
	
}