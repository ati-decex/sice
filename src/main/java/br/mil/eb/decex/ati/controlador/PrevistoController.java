package br.mil.eb.decex.ati.controlador;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.Linha;
import br.mil.eb.decex.ati.enumerado.LinhaEspecialidade;
import br.mil.eb.decex.ati.enumerado.PostoGraduacao;
import br.mil.eb.decex.ati.enumerado.PostoGraduacaoEspecialidade;
import br.mil.eb.decex.ati.enumerado.Tipo;
import br.mil.eb.decex.ati.jpa.OrganizacaoMilitarRepository;
import br.mil.eb.decex.ati.modelo.EfetivoPrevisto;
import br.mil.eb.decex.ati.modelo.Especialidade;
import br.mil.eb.decex.ati.modelo.LinhaConfiguracao;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.QCP;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;
import br.mil.eb.decex.ati.servico.QcpService;
import br.mil.eb.decex.ati.servico.TransacaoLocal;
import br.mil.eb.decex.ati.util.Auditable;

/**
 * Realiza o cadastro do efetivo Previsto de cada OM
 * </p>
 * 
 * @author Luiz Augusto Lourenço do <b>Amparo</b> 3º SGT
 * @version 1.0
 */

@ViewScoped
@Named("previsto")
public class PrevistoController extends BaseController {

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
	private QcpService qcpService;

	private EfetivoPrevisto efetivo;
	private OrganizacaoMilitar superiorSelected;
	private OrganizacaoMilitar docSuperiorSelected;

	private Date dataNovoQCP;

	private Boolean desabilitarEspecialidade = true;
	private Boolean desabilitarQCP = true;
	private boolean exibirEspecialidades = false;

	private List<OrganizacaoMilitar> organizacoesPorEfetivo;
	private List<OrganizacaoMilitar> superiores;
	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<Especialidade> especialidades;

	private List<OrganizacaoMilitar> docOrganizacoesMilitares;
	private List<LinhaConfiguracao> linhasConfiguracoes;
	private List<EfetivoPrevisto> listaEfetivoPrevisto;
	private List<Linha> linhas;
	private List<Tipo> tipos;
	private List<QCP> documentos;

	private String siglaOM;
	private List<OrganizacaoMilitar> listaOMBusca;

	@PostConstruct
	public void init() {
		listaEfetivoPrevisto = new ArrayList<EfetivoPrevisto>();
		documentos = new ArrayList<QCP>();
		superiorSelected = new OrganizacaoMilitar();
		efetivo = new EfetivoPrevisto();
		efetivo.setLinhaConfiguracao(new LinhaConfiguracao());
		linhasConfiguracoes = new ArrayList<LinhaConfiguracao>();
		efetivo.setPeriodo(periodo);
		superiores = omRepository.encontrarSuperiores();
		buscarOM();
	}

	public List<String> completeText(String query) {
		listaOMBusca = omRepository.encontrarOMPorSigla(query);
		List<String> results = new ArrayList<String>();
		for (OrganizacaoMilitar om : listaOMBusca) {
			results.add(om.getSigla());
		}
		return results;
	}

	public void resetarEscolhaPorPalavraChave() {
		if (this.siglaOM == null) {
			this.setSuperiorSelected(null);
			this.setOrganizacoesMilitares(null);
			this.getEfetivo().setOrganizacaoMilitar(null);
			this.getEfetivo().setQcp(null);
			this.getListaEfetivoPrevisto().clear();
		} else if (this.siglaOM.isEmpty()) {
			this.setSuperiorSelected(null);
			this.setOrganizacoesMilitares(null);
			this.getEfetivo().setOrganizacaoMilitar(null);
			this.getEfetivo().setQcp(null);
			this.getListaEfetivoPrevisto().clear();
		}
	}

	public void buscarOM() {
		if (!this.getSiglaOM().isEmpty()) {
			this.getListaOMBusca().clear();
			this.setListaOMBusca(omRepository.encontrarOMPorSigla(this.getSiglaOM()));

			OrganizacaoMilitar om = new OrganizacaoMilitar();
			om.setSigla(this.getSiglaOM());

			om = listaOMBusca.get(listaOMBusca.indexOf(om));
			om.setQcp(null);

			this.setSuperiorSelected(om.getSuperiores().get(0));
			this.listarSubordinados();
			this.getEfetivo().setOrganizacaoMilitar(om);
			this.carregarQCP(om);
			listaEfetivoPrevisto.clear();
			listaEfetivoPrevisto = repository.encontrarComQueryNomeada(EfetivoPrevisto.class,
					"EfetivoPrevisto.listarporQCPAtivo", new Object[] { "om", om });
			Collections.sort(listaEfetivoPrevisto);
		}
	}

	public void recuperarEfetivoPrevistoAtualizado(QCP qcp) {
		this.setSiglaOM(this.getEfetivo().getOrganizacaoMilitar().getSigla());
		if (this.getEfetivo().getOrganizacaoMilitar() != null) {
			listaEfetivoPrevisto.clear();
			listaEfetivoPrevisto = repository.encontrarComQueryNomeada(EfetivoPrevisto.class,
					"EfetivoPrevisto.listarporQCPAtivo",
					new Object[] { "om", this.getEfetivo().getOrganizacaoMilitar() });
			qcp.getEfetivosPrevistos().clear();
			qcp.getEfetivosPrevistos().addAll(listaEfetivoPrevisto);
			Collections.sort(qcp.getEfetivosPrevistos());

		}

	}

	@Auditable
	public void editar(EfetivoPrevisto editarEfetivo) {
		setEfetivo(editarEfetivo);
		if (this.getEfetivo().getEspecialidade().getId() != null) {
			if (this.getEfetivo().getEspecialidade().getId().intValue() == 0) {
				this.getEfetivo().setEspecialidade(null);
			}
		} else {
			this.getEfetivo().setEspecialidade(null);
		}
		super.executar(() -> {
			repository.adicionarOuMudar(efetivo);
			init();
		}, SUCESSO, FALHA);

	}

	@Auditable
	public void excluir(EfetivoPrevisto excluirEfetivo) {
		super.executar(() -> {
			repository.apagarFlush(EfetivoPrevisto.class, excluirEfetivo.getId());
			init();
		}, SUCESSO, FALHA);
	}

	public void carregarQCP(OrganizacaoMilitar om) {
		this.getLinhas().clear();
		this.getTipos().clear();
		this.efetivo.setLinhaConfiguracao(new LinhaConfiguracao());
		this.efetivo.setEspecialidade(null);
		this.efetivo.setQcp(null);
		this.efetivo.setQuantidade(null);
		this.getEspecialidades().clear();
		this.desabilitarEspecialidade = true;
		documentos = repository.encontrarComQueryNomeada(QCP.class, "Qcp.porOrganizacaoPorAtivo",
				new Object[] { "organizacao", om });
		recuperarEfetivoPrevistoAtualizado(documentos.get(0));

		this.efetivo.setQcp(documentos.get(0));
	}

	public String formatarData(Date data) {
		String dataFormatada = "";
		SimpleDateFormat formataData;
		if (data != null) {
			formataData = new SimpleDateFormat("dd/MM/yyyy");
			dataFormatada = formataData.format(data);
		}
		return dataFormatada;
	}

	public void listarSubordinados() {
		setOrganizacoesMilitares(omRepository.encontrarEstabelecimentosEnsinoSubordinados(superiorSelected));
		this.getLinhas().clear();
		this.getTipos().clear();
		this.efetivo.setLinhaConfiguracao(new LinhaConfiguracao());
		this.efetivo.setEspecialidade(null);
		this.efetivo.setQcp(null);
		this.efetivo.setQuantidade(null);
		setDesabilitarEspecialidade(true);
	}

	public void carregarEspecialidades() {
		especialidades = new ArrayList<Especialidade>();
		especialidades.clear();
		if (this.efetivo.getLinhaConfiguracao().getPostoGraduacao().equals(PostoGraduacao.PRI_TEN)
				|| this.efetivo.getLinhaConfiguracao().getPostoGraduacao().equals(PostoGraduacao.SEG_TEN)) {
			if (!this.efetivo.getLinhaConfiguracao().getLinha().equals(Linha.LPR)) {
				especialidades = repository.encontrarComQueryNomeada(Especialidade.class,
						"Especialidade.oficiais",
						new Object[] { "linhaEspecialidade",
								LinhaEspecialidade.valueOf(this.efetivo.getLinhaConfiguracao().getLinha().name()) },
						new Object[] { "postograduacao", PostoGraduacaoEspecialidade.OFI });
			}
		} else if (this.efetivo.getLinhaConfiguracao().getPostoGraduacao().equals(PostoGraduacao.TER_SGT)) {
			if (!this.efetivo.getLinhaConfiguracao().getTipo().equals(Tipo.QE)) {
				especialidades = repository.encontrarComQueryNomeada(Especialidade.class,
						"Especialidade.oficiaisPorLinha",
						new Object[] { "linhaEspecialidade",
								LinhaEspecialidade.valueOf(this.efetivo.getLinhaConfiguracao().getTipo().name()) },
						new Object[] { "postograduacao", PostoGraduacaoEspecialidade.PRA });
			}
		}
		desabilitarEspecialidade = especialidades.isEmpty();
	}

	public void preparaCarregarEfetivoPrevisto() {
		if (this.isExibirEspecialidades()) {
			carregarEspecialidades();
			this.efetivo.setEspecialidade(null);
			this.efetivo.setQuantidade(null);
		} else {
			this.efetivo.setEspecialidade(null);
			this.desabilitarEspecialidade = true;
			this.getEspecialidades().clear();
			carregarEfetivoPrevisto();
		}

	}

	public boolean isExibirEspecialidades() {
		exibeEspecialidades();
		return exibirEspecialidades;
	}

	public void exibeEspecialidades() {
		boolean oficiaisComEspecialidades = false;
		boolean pracasComEspecialidades = false;

		oficiaisComEspecialidades = this.efetivo.getLinhaConfiguracao().getPostoGraduacao()
				.equals(PostoGraduacao.PRI_TEN) && !this.efetivo.getLinhaConfiguracao().getLinha().equals(Linha.LPR);

		oficiaisComEspecialidades |= this.efetivo.getLinhaConfiguracao().getPostoGraduacao()
				.equals(PostoGraduacao.SEG_TEN) && !this.efetivo.getLinhaConfiguracao().getLinha().equals(Linha.LPR);

		pracasComEspecialidades = this.efetivo.getLinhaConfiguracao().getPostoGraduacao().equals(PostoGraduacao.TER_SGT)
				&& !this.efetivo.getLinhaConfiguracao().getTipo().equals(Tipo.QE);

		setExibirEspecialidades(oficiaisComEspecialidades || pracasComEspecialidades);
	}

	public void limparEspecialidade() {
		if (!getEspecialidades().isEmpty()) {
			getEspecialidades().clear();
		}
		desabilitarEspecialidade = true;
	}

	public void listarSubordinadosDoc() {
		docOrganizacoesMilitares = omRepository.encontrarSubordinados(docSuperiorSelected);

		docOrganizacoesMilitares.add(0,
				repository.encontrar(OrganizacaoMilitar.class, "sigla", docSuperiorSelected + " OM").get(0));
	}

	public void carregarLinhaMilitar() {
		linhas = new ArrayList<Linha>();
		Set<Linha> conjunto = new HashSet<Linha>(linhas);

		linhasConfiguracoes = repository.encontrar(LinhaConfiguracao.class, "postoGraduacao",
				efetivo.getLinhaConfiguracao().getPostoGraduacao());

		efetivo.getLinhaConfiguracao().setLinha(null);

		linhasConfiguracoes.forEach(lc -> conjunto.add(lc.getLinha()));
		linhas.addAll(conjunto);

		Collections.sort(linhas);
		limparEspecialidade();
		tipos = new ArrayList<Tipo>();
		tipos.clear();
		this.efetivo.getLinhaConfiguracao().setTipo(null);
		this.efetivo.setQuantidade(null);
		setDesabilitarEspecialidade(true);

	}

	public void carregarTipoMilitar() {
		tipos = new ArrayList<Tipo>();
		Set<Tipo> conjunto = new HashSet<Tipo>(tipos);

		for (LinhaConfiguracao lc : linhasConfiguracoes) {
			if (lc.getLinha().equals(efetivo.getLinhaConfiguracao().getLinha())) {
				// Retira os temporários do Previsto
				if (!lc.getTipo().equals(Tipo.TMPR)) {
					conjunto.add(lc.getTipo());
				}
			}
		}

		tipos.addAll(conjunto);
		limparEspecialidade();
		this.efetivo.setQuantidade(null);
	}

	@Auditable
	public void carregarEfetivoPrevistoPorEspecialidade() {
		LinhaConfiguracao lc = new LinhaConfiguracao();
		lc.setLinha(efetivo.getLinhaConfiguracao().getLinha());
		lc.setPostoGraduacao(efetivo.getLinhaConfiguracao().getPostoGraduacao());
		lc.setTipo(efetivo.getLinhaConfiguracao().getTipo());
		lc = repository.encontrarComQueryNomeada(LinhaConfiguracao.class, "LinhaConfiguracao.porParametros",
				new Object[] { "posto", lc.getPostoGraduacao() }, new Object[] { "linha", lc.getLinha() },
				new Object[] { "tipo", lc.getTipo() }).get(0);
		efetivo.setLinhaConfiguracao(lc);
		List<EfetivoPrevisto> efetivosPrevisto;
		efetivosPrevisto = repository.encontrarComQueryNomeada(EfetivoPrevisto.class,
				"EfetivoPrevisto.listarPorEspepecialidade", new Object[] { "om", efetivo.getOrganizacaoMilitar() },
				new Object[] { "especialidade", efetivo.getEspecialidade() },
				new Object[] { "linhaConfiguracao", efetivo.getLinhaConfiguracao() });
		if (efetivosPrevisto.size() > 0) {
			setEfetivo(efetivosPrevisto.get(0));
		} else {
			getEfetivo().setId(null);
			getEfetivo().setQuantidade(null);
		}

	}
	
	@Auditable
	public void carregarEfetivoPrevistoPorLinhaConfiguracao() {
		LinhaConfiguracao lc = new LinhaConfiguracao();
		lc.setLinha(efetivo.getLinhaConfiguracao().getLinha());
		lc.setPostoGraduacao(efetivo.getLinhaConfiguracao().getPostoGraduacao());
		lc.setTipo(efetivo.getLinhaConfiguracao().getTipo());
		lc = repository.encontrarComQueryNomeada(LinhaConfiguracao.class, "LinhaConfiguracao.porParametros",
				new Object[] { "posto", lc.getPostoGraduacao() }, new Object[] { "linha", lc.getLinha() },
				new Object[] { "tipo", lc.getTipo() }).get(0);
		efetivo.setLinhaConfiguracao(lc);
		List<EfetivoPrevisto> efetivosPrevisto;
		efetivosPrevisto = repository.encontrarComQueryNomeada(EfetivoPrevisto.class,
				"EfetivoPrevisto.listarComLinhaConfiguracaoPorPeriodoELinhaConfiguracao",
				new Object[] { "linhaConfiguracao", efetivo.getLinhaConfiguracao() },
				new Object[] { "om", efetivo.getOrganizacaoMilitar() });
		if (efetivosPrevisto.size() > 0)
			setEfetivo(efetivosPrevisto.get(0));

	}

	@Auditable
	private void atribuirLinhaConfiguracao() {
		LinhaConfiguracao lc = repository
				.encontrarComQueryNomeada(LinhaConfiguracao.class, "LinhaConfiguracao.porParametros",
						new Object[] { "posto", efetivo.getLinhaConfiguracao().getPostoGraduacao() },
						new Object[] { "linha", efetivo.getLinhaConfiguracao().getLinha() },
						new Object[] { "tipo", efetivo.getLinhaConfiguracao().getTipo() })
				.get(0);
		this.efetivo.setLinhaConfiguracao(lc);
	}

	@Auditable
	private void preparaEspecialidade() {
		if (efetivo.getEspecialidade().getId() == null) {
			efetivo.getEspecialidade().setId(0L);
		}
	}

	@Auditable
	public void carregarEfetivoPrevisto() {
		atribuirLinhaConfiguracao();
		preparaEspecialidade();
		List<EfetivoPrevisto> efetivosPrevistos;
		efetivosPrevistos = repository.encontrarComQueryNomeada(EfetivoPrevisto.class,
				"EfetivoPrevisto.listarPorEspepecialidade", new Object[] { "om", efetivo.getOrganizacaoMilitar() },
				new Object[] { "especialidade", efetivo.getEspecialidade() },
				new Object[] { "linhaConfiguracao", efetivo.getLinhaConfiguracao() });

		if (!efetivosPrevistos.isEmpty()) {
			setEfetivo(efetivosPrevistos.get(0));
		} else {
			this.efetivo.setId(null);
			this.efetivo.setQuantidade(null);
		}

	}

	@Auditable
	public void salvar(EfetivoPrevisto efetivoAuditoria) {
		LinhaConfiguracao lc = repository
				.encontrarComQueryNomeada(LinhaConfiguracao.class, "LinhaConfiguracao.porParametros",
						new Object[] { "posto", efetivo.getLinhaConfiguracao().getPostoGraduacao() },
						new Object[] { "linha", efetivo.getLinhaConfiguracao().getLinha() },
						new Object[] { "tipo", efetivo.getLinhaConfiguracao().getTipo() })
				.get(0);

		efetivo.setLinhaConfiguracao(lc);

		if (this.getEfetivo().getEspecialidade().getId() != null) {
			if (this.getEfetivo().getEspecialidade().getId().intValue() == 0) {
				this.getEfetivo().setEspecialidade(null);
			}
		} else {
			this.getEfetivo().setEspecialidade(null);
		}

		if (efetivo.getOrganizacaoMilitar() == null){
			efetivo.setOrganizacaoMilitar(superiorSelected);
		}

		salvarOuAtualizar();

	}

	public void salvarOuAtualizar() {
		super.executar(() -> {
			repository.adicionarOuMudar(efetivo);
			init();
		}, SUCESSO, FALHA);
	}

	public void salvarDocumento() {
		TransacaoLocal t = () -> {
			List<EfetivoPrevisto> listaEfetivoPrevistoAtual = inicializarListaDeEfetivosPrevistos();
			OrganizacaoMilitar om = inicializarOrganizacaoMilitar();
			QCP qcpNovo = prepararNovoQCP(om);
			QCP qcpAtual = prepararQCPAtual(om);
			prepararListaEfetivosPrevistos(listaEfetivoPrevistoAtual, qcpAtual);
			inserirQCPNovoAtualizarQCPAntigo(listaEfetivoPrevistoAtual, qcpNovo, qcpAtual);
			init();
		};
		super.executar(t, SUCESSO, FALHA);

	}

	private void inserirQCPNovoAtualizarQCPAntigo(List<EfetivoPrevisto> listaEfetivoPrevistoAtual, QCP qcpNovo,
			QCP qcpAtual) {
		qcpService.salvar(qcpAtual, qcpNovo, listaEfetivoPrevistoAtual);
	}

	private void prepararListaEfetivosPrevistos(List<EfetivoPrevisto> listaEfetivoPrevistoAtual, QCP qcpAtual) {
		listaEfetivoPrevistoAtual.clear();
		listaEfetivoPrevistoAtual.addAll(qcpAtual.getEfetivosPrevistos());
	}

	private QCP prepararQCPAtual(OrganizacaoMilitar om) {
		QCP qcpAtual = new QCP();
		qcpAtual = repository
				.encontrarComQueryNomeada(QCP.class, "Qcp.porOrganizacaoPorAtivo", new Object[] { "organizacao", om })
				.get(0);
		qcpAtual.setAtivo(false);
		return qcpAtual;
	}

	private QCP prepararNovoQCP(OrganizacaoMilitar om) {
		QCP qcpNovo = new QCP();
		qcpNovo.setId(null);
		qcpNovo.setAtivo(true);
		qcpNovo.setDataAlteracao(this.getDataNovoQCP());
		qcpNovo.setOrganizacaoMilitar(om);
		qcpNovo.setEfetivosPrevistos(null);
		return qcpNovo;
	}

	private OrganizacaoMilitar inicializarOrganizacaoMilitar() {
		OrganizacaoMilitar om = new OrganizacaoMilitar();
		om = this.getEfetivo().getOrganizacaoMilitar();
		return om;
	}

	private List<EfetivoPrevisto> inicializarListaDeEfetivosPrevistos() {
		List<EfetivoPrevisto> _listaEfetivoPrevistoAtual;
		_listaEfetivoPrevistoAtual = new ArrayList<EfetivoPrevisto>();
		return _listaEfetivoPrevistoAtual;
	}

	public Boolean getDesabilitarEspecialidade() {
		return desabilitarEspecialidade;
	}

	public void setDesabilitarEspecialidade(Boolean desabilitarEspecialidade) {
		this.desabilitarEspecialidade = desabilitarEspecialidade;
	}

	public Boolean getDesabilitarQCP() {
		return desabilitarQCP;
	}

	public void setDesabilitarQCP(Boolean desabilitarQCP) {
		this.desabilitarQCP = desabilitarQCP;
	}

	public void setExibirEspecialidades(boolean exibirEspecialidades) {
		this.exibirEspecialidades = exibirEspecialidades;
	}

	public void setOrganizacoesPorEfetivo(List<OrganizacaoMilitar> organizacoesPorEfetivo) {

		this.organizacoesPorEfetivo = organizacoesPorEfetivo;

	}

	public String getSiglaOM() {
		if (siglaOM == null) {
			siglaOM = "";
		}
		return siglaOM;
	}

	public void setSiglaOM(String siglaOM) {
		this.siglaOM = siglaOM;
	}

	public List<OrganizacaoMilitar> getListaOMBusca() {
		if (listaOMBusca == null) {
			listaOMBusca = new ArrayList<OrganizacaoMilitar>();
		}
		return listaOMBusca;
	}

	public void setListaOMBusca(List<OrganizacaoMilitar> listaOMBusca) {
		this.listaOMBusca = listaOMBusca;
	}

	public void setOrganizacoesMilitares(List<OrganizacaoMilitar> organizacoesMilitares) {
		this.organizacoesMilitares = organizacoesMilitares;
	}

	public Date getDataNovoQCP() {
		return dataNovoQCP;
	}

	public void setDataNovoQCP(Date dataNovoQCP) {
		this.dataNovoQCP = dataNovoQCP;
	}

	public List<Especialidade> getEspecialidades() {
		if (especialidades == null) {
			return new ArrayList<Especialidade>();
		}

		return especialidades;
	}

	public void setEspecialidades(List<Especialidade> especialidades) {
		this.especialidades = especialidades;
	}

	public List<QCP> getDocumentos() {
		return documentos;
	}

	public void setDocumentos(List<QCP> documentos) {
		this.documentos = documentos;
	}

	public List<OrganizacaoMilitar> getOrganizacoesPorEfetivo() {
		if (organizacoesPorEfetivo == null) {
			organizacoesPorEfetivo = new ArrayList<OrganizacaoMilitar>();
		}
		return organizacoesPorEfetivo;
	}

	public List<OrganizacaoMilitar> getDocOrganizacoesMilitares() {
		return docOrganizacoesMilitares;
	}

	public List<OrganizacaoMilitar> getOrganizacoesMilitares() {
		if (organizacoesMilitares == null) {
			organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		}
		return organizacoesMilitares;
	}

	public List<PostoGraduacao> getPostosGraduacoes() {
		return Arrays.asList(PostoGraduacao.values());
	}

	public List<Linha> getLinhas() {
		if (linhas == null) {
			linhas = new ArrayList<Linha>();
		}
		return linhas;
	}

	public List<Tipo> getTipos() {
		if (tipos == null) {
			tipos = new ArrayList<Tipo>();
		}
		return tipos;
	}

	public EfetivoPrevisto getEfetivo() {
		if (efetivo == null) {
			efetivo = new EfetivoPrevisto();
		}
		return efetivo;
	}

	public void setEfetivo(EfetivoPrevisto efetivo) {
		this.efetivo = efetivo;
	}

	public List<EfetivoPrevisto> getListaEfetivoPrevisto() {
		if (listaEfetivoPrevisto == null) {
			listaEfetivoPrevisto = new ArrayList<EfetivoPrevisto>();
		}
		return listaEfetivoPrevisto;
	}

	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}

	public OrganizacaoMilitar getDocSuperiorSelected() {
		return docSuperiorSelected;
	}

	public void setDocSuperiorSelected(OrganizacaoMilitar docSuperiorSelected) {
		this.docSuperiorSelected = docSuperiorSelected;
	}

	public OrganizacaoMilitar getSuperiorSelected() {
		return superiorSelected;
	}

	public void setSuperiorSelected(OrganizacaoMilitar superiorSelected) {
		this.superiorSelected = superiorSelected;
	}

}