package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.StreamedContent;

import com.sun.xml.ws.util.UtilException;

import br.mil.eb.decex.ati.enumerado.EnumRelatorio;
import br.mil.eb.decex.ati.enumerado.Linha;
import br.mil.eb.decex.ati.enumerado.PostoGraduacao;
import br.mil.eb.decex.ati.enumerado.Tipo;
import br.mil.eb.decex.ati.enumerado.TipoAcesso;
import br.mil.eb.decex.ati.enumerado.TipoOrganizacaoMilitar;
import br.mil.eb.decex.ati.jpa.OrganizacaoMilitarRepository;
import br.mil.eb.decex.ati.jpa.Repository;
import br.mil.eb.decex.ati.modelo.EfetivoPrevisto;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.QCP;
import br.mil.eb.decex.ati.modelo.Tela;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.modelo.jaas.UserRoles;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;
import br.mil.eb.decex.ati.servico.PeriodoService;
import br.mil.eb.decex.ati.util.RelatorioUtil;
//teste

/**
 * Realiza o cadastro do efetivo Previsto de cada OM
 * </p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("relatorioGeral")
public class RelatorioGeralController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Inject
	@Autenticado
	private Usuario usuario;

	@Inject
	@PeriodoSelecionado
	private Periodo periodo;

	@Inject
	private OrganizacaoMilitarRepository omRepository;
	
	@Inject
	private Repository repository;

	@Inject
	private PeriodoService service;
	private List<Periodo> periodos;
	private StreamedContent arquivoRetorno;
	private int tipoSelected;
	private OrganizacaoMilitar organizacaoMilitar;
	private List<EnumRelatorio> enumRelatorio;
	private List<Tela> relatoriosDisponiveis;
	// private EnumRelatorio relatorioEscolhido;
	private Tela relatorioEscolhido;

	/*
	 * @Inject private QcpService qcpService;
	 */

	private EfetivoPrevisto efetivo;
	private OrganizacaoMilitar superiorSelected;
	private OrganizacaoMilitar docSuperiorSelected;
	private QCP documento;
	private List<OrganizacaoMilitar> organizacoesPorEfetivo;
	private List<OrganizacaoMilitar> superiores;
	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<OrganizacaoMilitar> organizacoesVinculadas;
	private List<OrganizacaoMilitar> docOrganizacoesMilitares;
	private List<EfetivoPrevisto> listaEfetivoPrevisto;
	private List<Linha> linhas;
	private List<Tipo> tipos;

	private boolean desabilitarComboOrganizacoesSubordinadas;
	private boolean desabilitarComboDepartamentoDiretorias;
	private boolean perfilUsuario;
	private boolean perfilAdministrador;
	private boolean perfilDiretoria;
	private boolean perfilAuditoria;
	private boolean omUsuarioDiretoria;
	private boolean omUsuarioDepartamento;
	private boolean omUsuarioPossuiVinculadas;
	private static String SUBREPORT_DIR;

	/**
	 * Guarda as informações de documentoReferencia
	 * 
	 * @return DocumentoReferendia
	 */
	public QCP getDocumento() {
		return documento;
	}

	public void setDocumento(QCP documento) {
		this.documento = documento;
	}

	/**
	 * Carrega uma lista de documentosReferencias
	 * 
	 * @return lista de DocumentoReferencia
	 *//*
		 * public List<QCP> getDocumentos() { return documentos; }
		 * 
		 * public void setDocumentos(List<QCP> documentos) { this.documentos =
		 * documentos; }
		 */

	/**
	 * Carrega uma lista de organização com todo o seu efetivo
	 * 
	 * @return lista de Organizações Militares
	 */
	public List<OrganizacaoMilitar> getOrganizacoesPorEfetivo() {
		return organizacoesPorEfetivo;
	}

	/**
	 * Carrega lista de Organizações Militares de documentos
	 * 
	 * @return lista de Organizações Militares
	 */
	public List<OrganizacaoMilitar> getDocOrganizacoesMilitares() {
		return docOrganizacoesMilitares;
	}

	/**
	 * Carrega lista de Organizações Militares
	 * 
	 * @return lista de Organizações Militares
	 */
	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		if (organizacoesMilitares == null) {
			organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		}
		return organizacoesMilitares;
	}

	/**
	 * Carrega lista de postos e graduações
	 * 
	 * @return lista de postos e graduações
	 */
	public List<PostoGraduacao> getPostosGraduacoes() {
		return Arrays.asList(PostoGraduacao.values());
	}

	/**
	 * Lista de linhas que serão cadastradas
	 * 
	 * @see br.mil.eb.decex.ati.enumerado.Linha
	 * @return Linhas
	 */
	public List<Linha> getLinhas() {
		return linhas;
	}

	/**
	 * Lista de tipos que serão cadastradas
	 * 
	 * @see br.mil.eb.decex.ati.enumerado.Tipo
	 * @return Tipos
	 */
	public List<Tipo> getTipos() {
		return tipos;
	}

	/**
	 * Guarda as informações do efetivo previsto
	 * 
	 * @return EfetivoPrevisto
	 */
	public EfetivoPrevisto getEfetivo() {
		return efetivo;
	}

	public void setEfetivo(EfetivoPrevisto efetivo) {
		this.efetivo = efetivo;
	}

	/**
	 * Lista de efetivoExistente cadastrado
	 * 
	 * @return EfetivoExistente
	 */
	public List<EfetivoPrevisto> getListaEfetivoPrevisto() {
		return listaEfetivoPrevisto;
	}

	/**
	 * Lista de superiores
	 * 
	 * @return OrganizacaoMilitar
	 */
	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}

	/**
	 * Guarda as informações do superior selecionado de documento
	 * 
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getDocSuperiorSelected() {
		return docSuperiorSelected;
	}

	public void setDocSuperiorSelected(OrganizacaoMilitar docSuperiorSelected) {
		this.docSuperiorSelected = docSuperiorSelected;
	}

	/**
	 * Guarda as informações do superior selecionado
	 * 
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getSuperiorSelected() {
		return superiorSelected;
	}

	public void setSuperiorSelected(OrganizacaoMilitar superiorSelected) {
		this.superiorSelected = superiorSelected;
	}

	/**
	 * Carrega a lista forca com valores do Enum forca
	 * 
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Forca
	 */
	public List<EnumRelatorio> getEnumRelatorio() {
		return enumRelatorio;
	}

	@PostConstruct
	public void init() {
		tipoSelected = 1;
		superiorSelected = new OrganizacaoMilitar();
		organizacaoMilitar = new OrganizacaoMilitar();
		perfilUsuario = false;
		perfilAdministrador = false;
		perfilDiretoria = false;
		perfilAuditoria = false;
		desabilitarComboOrganizacoesSubordinadas = false;
		desabilitarComboDepartamentoDiretorias = false;
		omUsuarioDiretoria = false;
		setOmUsuarioDepartamento(false);
		reconhecerPerfisDoUsuario();
		verificaSeOMDoUsuarioEDiretoriaOuDepartamento();
		verificaSeOMDoUsuarioPossuiVinculadas();
		setarCombosSuperiorESubordinado();
		setPeriodos(service.carregarTodosOsPeriodos());
		SUBREPORT_DIR = FacesContext.getCurrentInstance().getExternalContext().getRealPath("") + "/relatorio/";

		carregarRelatoriosDisponiveis();
	}
	
	/**
	 * buscar o periodo disponivel durante o ano
	 */
	public void carregarPeriodosDoAno() {
		
		periodos = new ArrayList<Periodo>();
		periodos = repository.encontrarComQueryNomeada(Periodo.class,"Periodo.doAno");
		
	}
	
	public void carregarRelatoriosDisponiveis() {
		adicionarRelatorioPrevistoXExistente();
		seOMSubordinadaNulaAtribuiAOMDoUsuario();
		if (oMComum()) {
			carregarTelasDeOMComum();
		} else if (omDepartamento()) {
			carregarTodasAsTelas();
		} else if (omDiretoria()) {
			carregarTelasDaDiretoria();
		}

		// Ordenação das opções de relatorio
		Collections.sort(relatoriosDisponiveis, (r1, r2) -> r1.getOrdem().compareTo(r2.getOrdem()));
	}

	private void seOMSubordinadaNulaAtribuiAOMDoUsuario() {
		if (organizacaoMilitar.getId() == null) {
			organizacaoMilitar = getUsuario().getOrganizacaoMilitar();
		}
	}

	private boolean omDiretoria() {
		return organizacaoMilitar.getTipo().equals(TipoOrganizacaoMilitar.DIRETORIA);
	}

	private boolean omDepartamento() {
		return organizacaoMilitar.getTipo().equals(TipoOrganizacaoMilitar.DEPARTAMENTO);
	}

	private boolean omDepartamento(OrganizacaoMilitar organizacaomilitar) {
		if (organizacaomilitar != null) {
			if (organizacaomilitar.getTipo() != null) {
				return organizacaomilitar.getTipo().equals(TipoOrganizacaoMilitar.DEPARTAMENTO);
			}
		}
		return false;
	}

	private boolean oMComum() {
		return (!omDepartamento()) && ((!omDiretoria()));
	}

	private void carregarTelasDaDiretoria() {
		List<OrganizacaoMilitar> organizacoesSubordinadas = new ArrayList<OrganizacaoMilitar>();
		organizacoesSubordinadas = omRepository.encontrarSubordinados(organizacaoMilitar);
		for (OrganizacaoMilitar omsub : organizacoesSubordinadas) {
			omsub = omRepository.encontrarComAsTelas(omsub.getId());
			List<Tela> listaDeTelas = new ArrayList<Tela>();
			listaDeTelas = omsub.getTelas();
			if ((listaDeTelas != null) && (listaDeTelas.size() > 0)) {
				for (Tela t : listaDeTelas) {
					if (!contemRelatorio(t, this.getRelatoriosDisponiveis())) {
						this.getRelatoriosDisponiveis().add(t);
					}
				}
			}
		}
		relatoriosDisponiveis.add(getRepository().encontrar(Tela.class, "nome", "RESUMO_GERAL").get(0));
		
		removeTela(relatoriosDisponiveis, "PRACAS_TEMPORARIOS");
		alteraTituloRelatorioOficiaisTemporarios();
		

		ajustarTelas(organizacaoMilitar);
	}

	private void alteraTituloRelatorioOficiaisTemporarios() {
		Tela tela = new Tela();
		tela.setNome("OFICIAIS_TEMPORARIOS");
		if (relatoriosDisponiveis.contains(tela)) {
			relatoriosDisponiveis.get(relatoriosDisponiveis.indexOf(tela)).setTituloRelatorio("Militares Temporários");
		}
		
	}

	private void carregarTodasAsTelas() {
		List<Tela> listaCompletaDeTelas = new ArrayList<Tela>();
		listaCompletaDeTelas = getRepository().encontrar(Tela.class);
		for (Tela t : listaCompletaDeTelas) {
			if (!contemRelatorio(t, this.getRelatoriosDisponiveis())) {
				this.getRelatoriosDisponiveis().add(t);
			}
		}
		
		relatoriosDisponiveis.remove(2);
		removeTela(relatoriosDisponiveis, "PRACAS_TEMPORARIOS");
		alteraTituloRelatorioOficiaisTemporarios();
	}

	private void carregarTelasDeOMComum() {
		OrganizacaoMilitar omTelas;
		omTelas = omRepository.encontrarComAsTelas(organizacaoMilitar.getId());
		if (omTelas.getTelas() != null) {
			for (Tela t : omTelas.getTelas()) {
				if (!contemRelatorio(t, this.getRelatoriosDisponiveis())) {
					this.getRelatoriosDisponiveis().add(t);
				}
			}
		}
		relatoriosDisponiveis.add(getRepository().encontrar(Tela.class, "nome", "RESUMO_GERAL").get(0));
		ajustarTelas(superiorSelected);
	}

	private void ajustarTelas(OrganizacaoMilitar om) {
		if (om != null) {
			if (om.getSigla() != null) {
				if (om.getSigla().equals("DEPA")) {
					ajustarTelasDaDEPA();
				} else if (om.getSigla().equals("DESMIL")) {
					ajustarTelasDaDESMIL();
				}
			}
		}
	}

	private void ajustarTelasDaDESMIL() {
		removeTela(this.getRelatoriosDisponiveis(), "ALUNOS_COLEGIO_MILITAR");
		if ((organizacaoMilitar.getSigla().toString().equals("EsFCEx"))
				|| (organizacaoMilitar.getSigla().toString().equals("CPOR/BH"))) {
			removeTela(this.getRelatoriosDisponiveis(), "PROFESSOR_MILITAR");
			removeTela(this.getRelatoriosDisponiveis(), "PROFESSOR_CIVIL");
		}
	}

	private void ajustarTelasDaDEPA() {
		removeTela(this.getRelatoriosDisponiveis(), "ALUNO_MILITAR_OMDS");
		removeTela(this.getRelatoriosDisponiveis(), "OM_VINCULADAS");
		removeTela(this.getRelatoriosDisponiveis(), "ALUNOS_MILITAR_OUTRAS_FORCAS");
		if (organizacaoMilitar.getSigla().toString().equals("CMS")) {
			removeTela(this.getRelatoriosDisponiveis(), "ENSINO_DISTANCIA");
		}

	}

	private void removeTela(List<Tela> relatoriosDisponiveis, String nomeTela) {
		Tela tela = new Tela();
		tela.setNome(nomeTela);
		if (relatoriosDisponiveis.contains(tela)) {
			relatoriosDisponiveis.remove(tela);
		}
	}

	private void adicionarRelatorioPrevistoXExistente() {
		this.getRelatoriosDisponiveis().clear();
		Tela relatorioExistente = new Tela();
		relatorioExistente.setNome("EXISTENTE_X_EFETIVO");
		relatorioExistente.setCaminho("-");
		relatorioExistente.setTituloRelatorio("Efetivo Previsto X Efetivo Existente");
		relatorioExistente.setCaminhoRelatorio("relatorioGeralVertical");
		relatorioExistente.setOrdem(1);
		this.getRelatoriosDisponiveis().add(0, relatorioExistente);
	}

	private boolean contemRelatorio(Tela rel, List<Tela> relatorios) {
		for (Tela r : relatorios) {
			if (rel.getTituloRelatorio().trim().toLowerCase().equals(r.getTituloRelatorio().trim().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	

	private void verificaSeOMDoUsuarioPossuiVinculadas() {
		listarSubordinados();
		organizacaoMilitar = getUsuario().getOrganizacaoMilitar();
		desabilitarComboOrganizacoesSubordinadas = true;
		setOrganizacoesVinculadas(getRepository().encontrarComQueryNomeada(OrganizacaoMilitar.class,
				"OrganizacaoMilitar.porSuperior", new Object[] { "sigla", getUsuario().getOrganizacaoMilitar() }));
		omUsuarioPossuiVinculadas = false;
		if (getOrganizacoesVinculadas().size() > 1) {
			omUsuarioPossuiVinculadas = true;
		}
	}

	private void setarCombosSuperiorESubordinado() {
		if (perfilAdministrador) {
			listarSuperiores();
			habilitarCombos();
		} else if (perfilAuditoria) {
			if (omUsuarioDepartamento) {
				listarSuperiores();
				habilitarCombos();
			} else if (omUsuarioDiretoria) {
				preencherComboSuperior();
				preencherOrganizacoesSubordinadasEDestravarCombo();
			} else if (omUsuarioPossuiVinculadas) {
				preencherComboSuperior();
				preencherOrganizacoesSubordinadasComVinculadasETravarCombo();
			} else {
				preecherSuperioresPelaOrganizacaoDoUsuario();
				preencherOrganizacoesSubordinadasComOmDoUsuarioETravarCombo();
				setaDisableComboDepartamentoDiretorias();
			}

		} else if (perfilDiretoria) {
			if (omUsuarioDiretoria) {
				preencherComboSuperior();
				preencherOrganizacoesSubordinadasEDestravarCombo();
			} else if (omUsuarioPossuiVinculadas) {
				preencherComboSuperior();
				preencherOrganizacoesSubordinadasComVinculadasETravarCombo();
			} else {
				preecherSuperioresPelaOrganizacaoDoUsuario();
				preencherOrganizacoesSubordinadasComOmDoUsuarioETravarCombo();
				setaDisableComboDepartamentoDiretorias();
			}
		} else if (perfilUsuario) {
			if (omUsuarioDiretoria) {
				preencheESelecionaComboSuperiorPelaOMSuperiorDoUsuario();
				preencheESelecionaComboSubordinadoPelaOMDoUsuario();
				desabilitarCombos();
			} else {
				preecherSuperioresPelaOrganizacaoDoUsuario();
				preencherOrganizacoesSubordinadasComOmDoUsuarioETravarCombo();
				setaDisableComboDepartamentoDiretorias();
			}
		}
	}

	private void preencheESelecionaComboSubordinadoPelaOMDoUsuario() {
		this.getOrganizacoesMilitares().clear();
		this.getOrganizacoesMilitares().add(getUsuario().getOrganizacaoMilitar());
		organizacaoMilitar = getUsuario().getOrganizacaoMilitar();
	}

	private void preencheESelecionaComboSuperiorPelaOMSuperiorDoUsuario() {
		superiores.clear();
		if (getUsuario().getOrganizacaoMilitar().getId().intValue() == 1) {
			superiorSelected = getUsuario().getOrganizacaoMilitar();

		} else {
			superiores = getRepository().encontrarComQueryNomeada(OrganizacaoMilitar.class,
					"OrganizacaoMilitar.porSubordinado", new Object[] { "sigla", getUsuario().getOrganizacaoMilitar() })
					.get(0).getSuperiores();
			superiorSelected = superiores.get(0);
		}

	}

	private void preencherOrganizacoesSubordinadasComOmDoUsuarioETravarCombo() {
		// this.getOrganizacoesMilitares().clear();
		// organizacaoMilitar = usuario.getOrganizacaoMilitar();
		organizacaoMilitar = new OrganizacaoMilitar();
		organizacaoMilitar.setId(getUsuario().getOrganizacaoMilitar().getId());
		organizacaoMilitar.setSigla(getUsuario().getOrganizacaoMilitar().getSigla());
		organizacaoMilitar.setTipo(getUsuario().getOrganizacaoMilitar().getTipo());
		this.getOrganizacoesMilitares().add(organizacaoMilitar);
		desabilitarComboOrganizacoesSubordinadas = true;
	}

	private void preencherOrganizacoesSubordinadasEDestravarCombo() {
		organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
		desabilitarComboOrganizacoesSubordinadas = false;
	}

	private void desabilitarCombos() {
		desabilitarComboDepartamentoDiretorias = true;
		desabilitarComboOrganizacoesSubordinadas = true;
	}

	private void habilitarCombos() {
		desabilitarComboDepartamentoDiretorias = false;
		desabilitarComboOrganizacoesSubordinadas = false;
	}

	private void preencherOrganizacoesSubordinadasComVinculadasETravarCombo() {
		organizacoesMilitares = organizacoesVinculadas;
		desabilitarComboOrganizacoesSubordinadas = false;
	}

	private void preencherComboSuperior() {
		superiores.clear();
		if (getUsuario().getOrganizacaoMilitar().getId().intValue() != 1) {
			superiores = getRepository().encontrarComQueryNomeada(OrganizacaoMilitar.class,
					"OrganizacaoMilitar.porSubordinado", new Object[] { "sigla", getUsuario().getOrganizacaoMilitar() })
					.get(0).getSuperiores();
		}
		superiores.add(getUsuario().getOrganizacaoMilitar());

	}

	private void setaDisableComboDepartamentoDiretorias() {
		if (superiores.size() == 1) {
			superiorSelected = superiores.get(0);
			desabilitarComboDepartamentoDiretorias = true;
		} else {
			desabilitarComboDepartamentoDiretorias = false;
		}
	}

	private void preecherSuperioresPelaOrganizacaoDoUsuario() {

		List<OrganizacaoMilitar> superiorLista = new ArrayList<OrganizacaoMilitar>();

		superiorLista = getRepository().encontrarComQueryNomeada(OrganizacaoMilitar.class,
				"OrganizacaoMilitar.porSubordinado", new Object[] { "sigla", getUsuario().getOrganizacaoMilitar() });

		if (superiorLista.size() > 0)
			superiores = superiorLista.get(0).getSuperiores();

	}

	public void listarSuperiores() {
		superiores = omRepository.encontrarSuperiores();
	}

	private void verificaSeOMDoUsuarioEDiretoriaOuDepartamento() {
		listarSuperiores();
		for (OrganizacaoMilitar om : superiores) {
			if (getUsuario().getOrganizacaoMilitar().getId().intValue() == om.getId().intValue()) {
				omUsuarioDiretoria = true;
				break;
			}
		}
		if (getUsuario().getOrganizacaoMilitar().getId().intValue() == 1) {
			omUsuarioDepartamento = true;
			omUsuarioDiretoria = false;
		}
	}

	private void reconhecerPerfisDoUsuario() {
		for (UserRoles role : getUsuario().getUsers().getRoles()) {
			if (role.getRoleName().equals(TipoAcesso.ADMINISTRADOR))
				this.setPerfilAdministrador(true);
			else if (role.getRoleName().equals(TipoAcesso.USUARIO))
				this.setPerfilUsuario(true);
			else if (role.getRoleName().equals(TipoAcesso.DIRETORIA))
				this.setPerfilDiretoria(true);
			else if (role.getRoleName().equals(TipoAcesso.AUDITORIA))
				this.setPerfilAuditoria(true);
		}

	}

	/***
	 * Lista apenas as organizações com o superior informado
	 ***/
	public void listarSubordinados2() {
		if (perfilAdministrador) {
			organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
			ajustarNomenclaturaDuplaSubordinacao(superiorSelected, organizacoesMilitares);
			if (omDepartamento(superiorSelected)) {
				adicionarOpcaoRelatorioDepartamento(0);
				adicionarDiretoriaOM(1);
			} else {
				adicionarDiretoriaOM(0);
			}
		} else if (perfilDiretoria) {
			if (omDepartamento(superiorSelected)) {
				this.getOrganizacoesMilitares().clear();
				organizacoesMilitares.add(getUsuario().getOrganizacaoMilitar());
			} else {
				this.getOrganizacoesMilitares().clear();
				organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
				adicionarDiretoriaOM(0);
			}
		} else if (perfilAuditoria) {
			if (omDepartamento(getUsuario().getOrganizacaoMilitar())) {
				if (superiorSelected.getId() != null) {
					organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
					ajustarNomenclaturaDuplaSubordinacao(superiorSelected, organizacoesMilitares);
					if (omDepartamento(superiorSelected)) {
						adicionarOpcaoRelatorioDepartamento(0);
						adicionarDiretoriaOM(1);
					} else {
						adicionarDiretoriaOM(0);
					}
				}
			} else if (omDiretoria(getUsuario().getOrganizacaoMilitar())) {
				if (superiorSelected.getId() != null) {
					if (omDepartamento(superiorSelected)) {
						this.getOrganizacoesMilitares().clear();
						organizacoesMilitares.add(getUsuario().getOrganizacaoMilitar());
					} else {
						this.getOrganizacoesMilitares().clear();
						organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
						ajustarNomenclaturaDuplaSubordinacao(superiorSelected, organizacoesMilitares);
						adicionarDiretoriaOM(0);
					}
				}

			} else {
				this.getOrganizacoesMilitares().clear();
				organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
				adicionarDiretoriaOM(0);
				carregarRelatoriosDisponiveis();
			}
		} else {
			this.getOrganizacoesMilitares().clear();
			organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
			adicionarDiretoriaOM(0);
			carregarRelatoriosDisponiveis();
		}
	}

	public void listarSubordinados() {
		if ((perfilAdministrador) || (perfilAuditoria && omDepartamento(getUsuario().getOrganizacaoMilitar()))) {
			if (superiorSelected.getId() != null) {
				organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
				ajustarNomenclaturaDuplaSubordinacao(superiorSelected, organizacoesMilitares);
				if (omDepartamento(superiorSelected)) {
					adicionarOpcaoRelatorioDepartamento(0);
					adicionarDiretoriaOM(1);
				} else {
					adicionarDiretoriaOM(0);
				}
			}
		} else if ((perfilDiretoria) || (perfilAuditoria && omDiretoria(getUsuario().getOrganizacaoMilitar()))) {
			if (superiorSelected.getId() != null) {
				if (omDepartamento(superiorSelected)) {
					this.getOrganizacoesMilitares().clear();
					organizacoesMilitares.add(getUsuario().getOrganizacaoMilitar());
				} else {
					this.getOrganizacoesMilitares().clear();
					organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
					adicionarDiretoriaOM(0);
				}
			}
		 
		} else if ((perfilUsuario) || (perfilAuditoria && omEstabelecimentoEnsino(getUsuario().getOrganizacaoMilitar()))) {
			this.getOrganizacoesMilitares().clear();
			organizacoesMilitares = omRepository.encontrarSubordinados(superiorSelected);
			adicionarDiretoriaOM(0);
			carregarRelatoriosDisponiveis();
		}
	}

	private void ajustarNomenclaturaDuplaSubordinacao(OrganizacaoMilitar diretoria,
			List<OrganizacaoMilitar> organizacoes) {

		OrganizacaoMilitar omESFCEX = new OrganizacaoMilitar();
		omESFCEX.setSigla("EsFCEx/CMS");
		OrganizacaoMilitar omCPORBH = new OrganizacaoMilitar();
		omCPORBH.setSigla("CPOR-BH/CMBH");

		if (organizacoes.contains(omESFCEX)) {
			if (diretoria.getSigla().equals("DESMIL")) {
				organizacoes.get(organizacoes.indexOf(omESFCEX)).setSigla("EsFCEx");
			} else if (diretoria.getSigla().equals("DEPA")) {
				organizacoes.get(organizacoes.indexOf(omESFCEX)).setSigla("CMS");
			}
		}

		if (organizacoes.contains(omCPORBH)) {
			if (diretoria.getSigla().equals("DESMIL")) {
				organizacoes.get(organizacoes.indexOf(omCPORBH)).setSigla("CPOR/BH");
			} else if (diretoria.getSigla().equals("DEPA")) {
				organizacoes.get(organizacoes.indexOf(omCPORBH)).setSigla("CMBH");
			}
		}
	}

	private void adicionarOpcaoRelatorioDepartamento(int indice) {
		OrganizacaoMilitar departamento = new OrganizacaoMilitar();
		// Adiciona o DECEX Sistema a lista de OM Subordinada
		departamento.setSigla(superiorSelected.getSigla() + " Sistema");
		departamento.setId(superiorSelected.getId());
		departamento.setTipo(TipoOrganizacaoMilitar.DEPARTAMENTO);
		if (organizacoesMilitares.size() > 0) {
			organizacoesMilitares.add(indice, departamento);
		}

	}

	private void adicionarDiretoriaOM(int indice) {
		if (organizacoesMilitares.size() > 0) {
			organizacoesMilitares.add(indice,
					getRepository().encontrar(OrganizacaoMilitar.class, "sigla", superiorSelected + " OM").get(0));
		}
	}

	/**
	 * carrega valores de uma lista de um enun de EnunRelatorio
	 */
	public void carregarRelatorio() {
		enumRelatorio = new ArrayList<EnumRelatorio>();
		enumRelatorio.addAll(Arrays.asList(EnumRelatorio.values()));
	}

	public void setArquivoRetorno(StreamedContent arquivo) {
		this.arquivoRetorno = arquivo;
	}

	public StreamedContent getArquivoRetorno() {
		FacesContext context = FacesContext.getCurrentInstance();
		setArquivoRetorno(imprimirRelatorioGeral(context, relatorioEscolhido));
		return arquivoRetorno;
	}

	private StreamedContent imprimirRelatorioGeral(FacesContext context, Tela relatorioEscolhido) {
		String nomeRelatorioSaida;
		String nomeRelatorioJasper = relatorioEscolhido.getCaminhoRelatorio();
		nomeRelatorioJasper = encaminhaRelatoriosEmCasoDepartamentoOuDiretoria(nomeRelatorioJasper);
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
			preencheParametrosParaIReport(relatorioEscolhido, map);
			nomeRelatorioSaida = formataNomeDeSaidaDoRelatorio(relatorioEscolhido);
			nomeRelatorioSaida = retiraBarraDoNomedaOM(nomeRelatorioSaida);
			nomeRelatorioJasper = ajustaCaminhoDoRelatorio(nomeRelatorioJasper);
			gerarRelatorio(nomeRelatorioJasper, nomeRelatorioSaida, relatorioUtil, map);
		} catch (UtilException e) {
			System.out.println(e.getMessage());
			context.addMessage(null, new FacesMessage("Relatório ainda em desenvolvimento."));
			return null;
		}
		return this.arquivoRetorno;
	}

	private void gerarRelatorio(String nomeRelatorioJasper, String nomeRelatorioSaida, RelatorioUtil relatorioUtil,
			HashMap<String, Object> map) {
		this.arquivoRetorno = relatorioUtil.geraRelatorio(map, nomeRelatorioJasper, nomeRelatorioSaida,
				getTipoSelected(), "relatorio");
	}

	/**
	 * chamada para o arquivo retorno do relatório
	 * 
	 * @return StreamedContent
	 */
	public StreamedContent getArquivoRetornoAuditoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		setArquivoRetorno(gerarRelatorioAuditoria(context));
		return arquivoRetorno;
	}
	
	public StreamedContent getArquivoRetornoAuditoriaDiretoria() {
		FacesContext context = FacesContext.getCurrentInstance();
		setArquivoRetorno(gerarRelatorioAuditoriaDiretoria(context));
		return arquivoRetorno;
	}
	

	/**
	 * monta toda a estrutura necessário para gerar o relatório
	 * 
	 * @param context
	 * @return StreamedContent
	 */
	private StreamedContent gerarRelatorioAuditoriaDiretoria(FacesContext context) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String nomeRelatorioJasper = "relatorioAuditoriaDiretoria/relatorioAuditoriaDiretoria";
		String nomeRelatorioSaida = "relatorioAuditoriaDiretoria";
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		setTipoSelected(1);
		map.put("id_diretoria", usuario.getOrganizacaoMilitar().getId());
		map.put("id_periodo", periodo.getId());

		try {
			this.arquivoRetorno = relatorioUtil.geraRelatorio(map, nomeRelatorioJasper, nomeRelatorioSaida,
					getTipoSelected(), "relatorio");

		} catch (UtilException e) {
			context.addMessage(null, new FacesMessage("Relatório ainda em desenvolvimento."));
			return null;
		}

		return this.arquivoRetorno;
	}

	
	private StreamedContent gerarRelatorioAuditoria(FacesContext context) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		String nomeRelatorioJasper = "relatorioAuditoriaDepartamento/relatorioAuditoriaDepartamento";
		String nomeRelatorioSaida = "relatorioAuditoria_Departamento";
		RelatorioUtil relatorioUtil = new RelatorioUtil();
		setTipoSelected(1);

		try {
			this.arquivoRetorno = relatorioUtil.geraRelatorio(map, nomeRelatorioJasper, nomeRelatorioSaida,
					getTipoSelected(), "relatorio");
		} catch (UtilException e) {
			context.addMessage(null, new FacesMessage("Relatório ainda em desenvolvimento."));
			return null;
		}

		return this.arquivoRetorno;
	}
	
	private void preencheParametrosParaIReport(Tela relatorio, HashMap<String, Object> map) {
		map.put("id_om", 0);

		// verifica se o perfil é diretoria e se tem " OM" em sua string
		// caso true
		// retira o " OM" e seta seu id
		if (omDiretoria(this.organizacaoMilitar) && (organizacaoMilitar.getSigla().indexOf(" OM") > 0)) {
			List<OrganizacaoMilitar> organizacaoList = new ArrayList<OrganizacaoMilitar>();

			String sigla = organizacaoMilitar.getSigla();
			Integer tamanhoSigla = new Integer(0);

			tamanhoSigla = sigla.length();
			tamanhoSigla = tamanhoSigla - 3;

			organizacaoList = getRepository().encontrar(OrganizacaoMilitar.class, "sigla", sigla.substring(0, tamanhoSigla));

			if (organizacaoList.size() > 0)
				map.put("id_om", organizacaoList.get(0).getId().intValue());
		} else {
			map.put("id_om", organizacaoMilitar.getId().intValue());
		}

		map.put("id_om_enquadrante", superiorSelected.getId().intValue());
		map.put("id_periodo", periodo.getId().intValue());
		map.put("SUBREPORT_DIR", SUBREPORT_DIR);
	}

	/*
	 * private String encaminhaRelatoriosEmCasoAMANECEME(Tela relatorio, String
	 * nomeRelatorioJasper) { if
	 * (relatorio.getCaminho().toString().equals("relatorioAlunosMilitares")){
	 * if (organizacaoMilitar.getId().intValue()==7){
	 * nomeRelatorioJasper+="ECEME"; } else if
	 * (organizacaoMilitar.getId().intValue()==9){ nomeRelatorioJasper+="AMAN";
	 * } } return nomeRelatorioJasper; }
	 */
	private String encaminhaRelatoriosEmCasoDepartamentoOuDiretoria(String nomeRelatorioJasper) {
		if (omDiretoria(this.organizacaoMilitar)) {
			nomeRelatorioJasper = modificaCaminhoParaRelatoriosDeDiretoria(nomeRelatorioJasper);
		} else if (omDepartamento(this.organizacaoMilitar)) {
			nomeRelatorioJasper = modificaCaminhoParaRelatoriosDeDepartamento(nomeRelatorioJasper);
		}
		return nomeRelatorioJasper;
	}

	private String modificaCaminhoParaRelatoriosDeDiretoria(String nomeRelatorioJasper) {
		nomeRelatorioJasper += "Diretoria";
		return nomeRelatorioJasper;
	}

	private String modificaCaminhoParaRelatoriosDeDepartamento(String nomeRelatorioJasper) {
		nomeRelatorioJasper += "Departamento";
		return nomeRelatorioJasper;
	}

	private boolean omDiretoria(OrganizacaoMilitar omDiretoria) {
		if (omDiretoria != null) {
			if (omDiretoria.getTipo() != null) {
				return omDiretoria.getTipo().equals(TipoOrganizacaoMilitar.DIRETORIA);
			}
		}
		return false;
	}
	private boolean omEstabelecimentoEnsino(OrganizacaoMilitar omDiretoria) {
		if (omDiretoria != null) {
			if (omDiretoria.getTipo() != null) {
				return omDiretoria.getTipo().equals(TipoOrganizacaoMilitar.ESTABELECIMENTO_ENSINO);
			}
		}
		return false;
	}

	private String formataNomeDeSaidaDoRelatorio(Tela relatorioEscolhido) {
		String nomeRelatorioSaida = relatorioEscolhido.getTituloRelatorio() + "-" + organizacaoMilitar.getSigla();
		return nomeRelatorioSaida;
	}

	private String ajustaCaminhoDoRelatorio(String nomeRelatorioJasper) {
		nomeRelatorioJasper += "/" + nomeRelatorioJasper;
		return nomeRelatorioJasper;
	}

	private String retiraBarraDoNomedaOM(String nomeRelatorioSaida) {
		nomeRelatorioSaida = nomeRelatorioSaida.replace('/', '-');
		return nomeRelatorioSaida;
	}

	public int getTipoSelected() {
		return tipoSelected;
	}

	public void setTipoSelected(int tipoSelected) {
		this.tipoSelected = tipoSelected;
	}

	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	public List<Periodo> getPeriodos() {
		return periodos;
	}

	public void setPeriodos(List<Periodo> periodos) {
		this.periodos = periodos;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	public boolean isDesabilitarComboOrganizacoesSubordinadas() {
		return desabilitarComboOrganizacoesSubordinadas;
	}

	public void setDesabilitarComboOrganizacoesSubordinadas(boolean desabilitarComboOrganizacoesSubordinadas) {
		this.desabilitarComboOrganizacoesSubordinadas = desabilitarComboOrganizacoesSubordinadas;
	}

	public boolean isPerfilUsuario() {
		return perfilUsuario;
	}

	public void setPerfilUsuario(boolean perfilUsuario) {
		this.perfilUsuario = perfilUsuario;
	}

	public boolean isPerfilAdministrador() {
		return perfilAdministrador;
	}

	public void setPerfilAdministrador(boolean perfilAdministrador) {
		this.perfilAdministrador = perfilAdministrador;
	}

	public boolean isPerfilDiretoria() {
		return perfilDiretoria;
	}

	public void setPerfilDiretoria(boolean perfilDiretoria) {
		this.perfilDiretoria = perfilDiretoria;
	}

	public boolean isOmUsuarioDiretoria() {
		return omUsuarioDiretoria;
	}

	public void setOmUsuarioDiretoria(boolean omUsuarioDiretoria) {
		this.omUsuarioDiretoria = omUsuarioDiretoria;
	}

	public boolean isDesabilitarComboDepartamentoDiretorias() {
		return desabilitarComboDepartamentoDiretorias;
	}

	public void setDesabilitarComboDepartamentoDiretorias(boolean desabilitarComboDepartamentoDiretorias) {
		this.desabilitarComboDepartamentoDiretorias = desabilitarComboDepartamentoDiretorias;
	}

	public boolean isOmUsuarioPossuiVinculadas() {
		return omUsuarioPossuiVinculadas;
	}

	public void setOmUsuarioPossuiVinculadas(boolean omUsuarioPossuiVinculadas) {
		this.omUsuarioPossuiVinculadas = omUsuarioPossuiVinculadas;
	}

	public List<OrganizacaoMilitar> getOrganizacoesVinculadas() {
		if (organizacoesVinculadas == null) {
			organizacoesVinculadas = new ArrayList<OrganizacaoMilitar>();
		}
		return organizacoesVinculadas;
	}

	public void setOrganizacoesVinculadas(List<OrganizacaoMilitar> organizacoesVinculadas) {
		this.organizacoesVinculadas = organizacoesVinculadas;
	}

	public static String getSUBREPORT_DIR() {
		return SUBREPORT_DIR;
	}

	public static void setSUBREPORT_DIR(String sUBREPORT_DIR) {
		SUBREPORT_DIR = sUBREPORT_DIR;
	}

	public List<Tela> getRelatoriosDisponiveis() {
		if (relatoriosDisponiveis == null) {
			relatoriosDisponiveis = new ArrayList<Tela>();
		}
		return relatoriosDisponiveis;
	}

	public void setRelatoriosDisponiveis(List<Tela> relatoriosDisponiveis) {
		this.relatoriosDisponiveis = relatoriosDisponiveis;
	}

	public void setRelatorioEscolhido(Tela relatorioEscolhido) {
		this.relatorioEscolhido = relatorioEscolhido;
	}

	public Tela getRelatorioEscolhido() {
		return this.relatorioEscolhido;
	}

	public boolean isPerfilAuditoria() {
		return perfilAuditoria;
	}

	public void setPerfilAuditoria(boolean perfilAuditoria) {
		this.perfilAuditoria = perfilAuditoria;
	}

	public boolean isOmUsuarioDepartamento() {
		return omUsuarioDepartamento;
	}

	public void setOmUsuarioDepartamento(boolean omUsuarioDepartamento) {
		this.omUsuarioDepartamento = omUsuarioDepartamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Repository getRepository() {
		return repository;
	}

	public void setRepository(Repository repository) {
		this.repository = repository;
	}

}
