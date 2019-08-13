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
import br.mil.eb.decex.ati.enumerado.Sexo;
import br.mil.eb.decex.ati.enumerado.Tipo;
import br.mil.eb.decex.ati.modelo.ComboTela;
import br.mil.eb.decex.ati.modelo.EfetivoPrevisto;
import br.mil.eb.decex.ati.modelo.EfetivoRealizado;
import br.mil.eb.decex.ati.modelo.Especialidade;
import br.mil.eb.decex.ati.modelo.LinhaConfiguracao;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.QCP;
import br.mil.eb.decex.ati.modelo.RegistroAlteracaoEfetivoExistente;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;
import br.mil.eb.decex.ati.servico.EfetivoExistenteService;
import br.mil.eb.decex.ati.servico.QcpService;
import br.mil.eb.decex.ati.util.Auditable;

/**
 * Realiza o cadastro do efetivo Existente de cada OM</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */

@ViewScoped
@Named("realizado")
public class RealizadoController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
	@Inject
	private PeriodoController periodoController;

	@Inject
	private QcpService qcpService;
	
	@Inject
	private EfetivoExistenteService realizadoService;
	
	@Inject
	private TelasController telasController;
	
	private EfetivoRealizado efetivo;
	private EfetivoPrevisto efetivoPrevisto;
	private String labelQCP;
	private boolean habilitaBotao;
	private boolean validarClonagem;
	private Boolean desabilitarEspecialidade = true;
	private boolean exibirEspecialidades = false;
	private RegistroAlteracaoEfetivoExistente registro;
	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<ComboTela> comboTela;
	private List<EfetivoPrevisto> efetivosPrevistos;
	private List<LinhaConfiguracao> linhasConfiguracoes;
	private List<EfetivoRealizado> listaEfetivoRealizado;
	private List<EfetivoRealizado> listaRealizado;
	private List<EfetivoRealizado> listaClonarRealizado;
	private List<Linha> linhas;
	private List<Tipo> tipos;
	private List<PostoGraduacao> postos;
	private List<Especialidade> especialidades;
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
	 * Guarda as informação do QCP Ativo
	 * @return labelQCP String
	 */
	public String getLabelQCP() {
		return labelQCP;
	}
	public void setLabelQCP(String labelQCP) {
		this.labelQCP = labelQCP;
	}
	
	/**
	 * Guarda as informações do efetivo previsto
	 * @return EfetivoPrevisto
	 */
	public EfetivoPrevisto getEfetivoPrevisto() {
		if (efetivoPrevisto==null){
			efetivoPrevisto = new EfetivoPrevisto();
		}
		return efetivoPrevisto;
	}
	public void setEfetivoPrevisto(EfetivoPrevisto efetivoPrevisto) {
		this.efetivoPrevisto = efetivoPrevisto;
	}
	
	/**
	 * lista de EfetivoRealizado cadastrado
	 * @return EfetivoRealizado
	 */
	public List<EfetivoRealizado> getListaRealizado() {
		return listaRealizado;
	}
	public void setListaRealizado(List<EfetivoRealizado> listaRealizado) {
		this.listaRealizado = listaRealizado;
	}
	
	/**
	 * lista de especialidade cadastradas
	 * @return Especialidade
	 */
	
	public List<Especialidade> getEspecialidades() {
		if (especialidades == null) {
			especialidades = new ArrayList<Especialidade>();
		}
		return especialidades;
	}

	/**
	 * lista de efetivoPrevisto cadastrado
	 * @return EfetivoPrevisto
	 */
	public List<EfetivoPrevisto> getEfetivosPrevistos() {
		return efetivosPrevistos;
	}

	/**
	 * lista de efetivoExistente cadastrado
	 * @return EfetivoExistente
	 */
	public List<EfetivoRealizado> getListaEfetivoRealizado() {
		if (listaEfetivoRealizado == null){
			listaEfetivoRealizado = new ArrayList<EfetivoRealizado>();
		}
		return listaEfetivoRealizado;
	}

	/**
	 * Lista de postos e graduações para escolha do usuário solicitante
	 * @return Posto/Graduação do usuário solicitante
	 */	
	public List<PostoGraduacao> getPostos() {
		return postos;
	}

	/**
	 * Lista de tipos para escolha do usuário solicitante
	 * @see br.mil.eb.decex.ati.enumerado.Tipo
	 * @return Tipo do usuário solicitante
	 */
	public List<Tipo> getTipos() {
		return tipos;
	}

	/**
	 * Conjunto de linhas para escolha do usuário solicitante
	 * @see br.mil.eb.decex.ati.enumerado.Linha
	 * @return Linha do usuário solicitante
	 */	
	public List<Linha> getLinhas() {
		return linhas;
	}
	
	/**
	 * Lista de sexo para escolha do usuário solicitante
	 * @return Sexo do usuário solicitante
	 */	
	public List<Sexo> getSexos() {
		return Arrays.asList(Sexo.values());
	}

	/**
	 * Guarda as informações do efetivo existente
	 * @return EfetivoRealizado
	 */
	public EfetivoRealizado getEfetivo() {
		if (efetivo==null){
			efetivo = new EfetivoRealizado();	
		}
		return efetivo;
	}
	public void setEfetivo(EfetivoRealizado efetivo) {
		this.efetivo = efetivo;
	}
	
	/**
	 * Controla a exibição das telas de acordo 
	 * com a Organização Logada
	 * @return telasController
	 */
	public TelasController getTelasController() {
		return telasController;
	}
	public void setTelasController(TelasController telasController) {
		this.telasController = telasController;
	}
	
	public boolean getValidarClonagem() {
		return validarClonagem;
	}

	@PostConstruct
	public void init() {
		getTelasController().init();
		
		registro = new RegistroAlteracaoEfetivoExistente();
		registro.setOrganizacaoMilitar(usuario.getOrganizacaoMilitar());
		registro.setPeriodo(periodo);
		registro.setData(new Date());
		registro.setUsuario(usuario);
		
		efetivo = new EfetivoRealizado();
		efetivo.setLinhaConfiguracao(new LinhaConfiguracao());
		efetivo.setPeriodo(periodo);
		
		linhasConfiguracoes = new ArrayList<LinhaConfiguracao>();
		
		postos = new ArrayList<PostoGraduacao>();
		postos.addAll(Arrays.asList(PostoGraduacao.values()));		
		postos.remove(PostoGraduacao.ALUNO);
		postos.remove(PostoGraduacao.PRF_CIV);
		
		efetivo.setOrganizacaoMilitar(usuario.getOrganizacaoMilitar());
		
		habilitaBotao=true;
				
		/* Recupera uma lista com todo o efetivoExistente cadastrado */
		listaEfetivoRealizado = repository.encontrarComQueryNomeada(EfetivoRealizado.class, "EfetivoRealizado.linhaConfiguracaoPorPeriodo", 
				new Object[]{"periodo", periodo.getId()},
				new Object[]{"om", usuario.getOrganizacaoMilitar().getId()});
		Collections.sort(listaEfetivoRealizado);
		
		if (listaEfetivoRealizado.size() > 0)
			habilitaBotao=false;
		
		/* Recupera uma lista com todo o efetivoExistente cadastrado no periodo anterior */
		listaClonarRealizado = repository.encontrarComQueryNomeada(EfetivoRealizado.class, "EfetivoRealizado.linhaConfiguracaoPorPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"om", usuario.getOrganizacaoMilitar()});
		
		preencherLabelQCP();
		validarClonagem();
	}
	
	/**
	 * realiza a validação da clonagem
	 */
	public void validarClonagem() {
		if (listaClonarRealizado.size() > 0){
			//validarClonagem = realizadoService.primeiroAcesso();
			validarClonagem = realizadoService.verificaSePrimeiroAcessoNoPeriodo();
		}
	}
	
	/**
	 * preenche o a label QCP com o último QCP Ativo
	 */
	private void preencherLabelQCP() {
		QCP qcp;
		String label = "";
		
		qcp = qcpService.retornarQCPPorOM(usuario.getOrganizacaoMilitar());
		
		if (qcp.getDataAlteracao() != null)
			label = formatarData(qcp.getDataAlteracao());
		
		setLabelQCP(label);
	}
	
	/**
	 * formatação da data para dd/MM/yyyy
	 * @param data
	 * @return dataFormatada String
	 */
	public String formatarData(Date data) {
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = formataData.format(data);
		return dataFormatada;
	}
	
	/**
	 * Carrega a linha de acordo com o Posto/Graduação selecionado
	 */
	public void carregarLinhaMilitar() {
		linhas = new ArrayList<Linha>();
		Set<Linha> conjunto = new HashSet<Linha>(linhas);
		
		linhasConfiguracoes = repository.encontrar(LinhaConfiguracao.class, "postoGraduacao", 
				efetivo.getLinhaConfiguracao().getPostoGraduacao());
		
		efetivo.getLinhaConfiguracao().setLinha(null);
		
		linhasConfiguracoes.forEach(lc -> conjunto.add(lc.getLinha()));
		linhas.addAll(conjunto);
		
		Collections.sort(linhas);
		
		tipos = null;
		efetivo.getLinhaConfiguracao().setTipo(null);
		efetivoPrevisto = new EfetivoPrevisto();
		efetivo.setFeminino(null);
		efetivo.setMasculino(null);
		efetivo.setEspecialidade(new Especialidade());
		this.getEspecialidades().clear();
		setDesabilitarEspecialidade(true);
	}
	
	/**
	 * Carrega Tipo de acordo com a linha selecionada
	 */
	public void carregarTipoMilitar() {		
		efetivo.getLinhaConfiguracao().setTipo(null);
		tipos = new ArrayList<Tipo>();
		Set<Tipo> conjunto = new HashSet<Tipo>(tipos);
		
		linhasConfiguracoes.forEach(lc -> {
			if(lc.getLinha().equals(efetivo.getLinhaConfiguracao().getLinha()))
				conjunto.add(lc.getTipo());
		});
		tipos.addAll(conjunto);
		Collections.sort(tipos);
		
		efetivoPrevisto = new EfetivoPrevisto();
		efetivo.setFeminino(null);
		efetivo.setMasculino(null);
		especialidades = new ArrayList<Especialidade>();
		especialidades.clear();
	}
	
	/**
	 * Exclui a instancia de EfetivoRealizado da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	
	@Auditable
	public void excluir(EfetivoRealizado excluirEfetivo) {
		super.executar(() -> {repository.apagar(EfetivoRealizado.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}

	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	@Auditable
	public void editar(EfetivoRealizado editarEfetivo) {		
		setEfetivo(editarEfetivo);
		if (this.getEfetivo().getEspecialidade().getId() != null) {
			if (this.getEfetivo().getEspecialidade().getId().intValue() == 0) {
				this.getEfetivo().setEspecialidade(null);
			}
		}
		else{
			this.getEfetivo().setEspecialidade(null);
		}
		salvarOuAtualizar();
	}
	
	private void atribuirLinhaConfiguracao() {
		LinhaConfiguracao lc = repository
				.encontrarComQueryNomeada(LinhaConfiguracao.class, "LinhaConfiguracao.porParametros",
						new Object[] { "posto", efetivo.getLinhaConfiguracao().getPostoGraduacao() },
						new Object[] { "linha", efetivo.getLinhaConfiguracao().getLinha() },
						new Object[] { "tipo", efetivo.getLinhaConfiguracao().getTipo() })
				.get(0);
		this.getEfetivoPrevisto().setLinhaConfiguracao(lc);
		this.getEfetivo().setLinhaConfiguracao(lc);
	}
	



	
	

	public void carregarEfetivoPrevisto(){
		atribuirLinhaConfiguracao();
		preparaEspecialidade();
		efetivosPrevistos= repository.encontrarComQueryNomeada(EfetivoPrevisto.class, "EfetivoPrevisto.listarPorEspepecialidade",
			new Object[]{"om", usuario.getOrganizacaoMilitar()},
			new Object[]{"especialidade", efetivo.getEspecialidade()},
			new Object[]{"linhaConfiguracao", efetivoPrevisto.getLinhaConfiguracao()});	
		
		if(!efetivosPrevistos.isEmpty()) {
			setEfetivoPrevisto(efetivosPrevistos.get(0));
		} else {
			efetivoPrevisto.setQuantidade(0);
		}
		carregarEfetivoRealizadoParaOsValoresSelecionados();
	}

	private void preparaEspecialidade() {
		if(efetivo.getEspecialidade().getId()==null){
			efetivo.getEspecialidade().setId(0L);
			efetivoPrevisto.getEspecialidade().setId(0L);
		}
	}
	
	public void carregarEspecialidades() {
		especialidades = new ArrayList<Especialidade>();
		especialidades.clear();
		if (this.efetivo.getLinhaConfiguracao().getPostoGraduacao().equals(PostoGraduacao.PRI_TEN)
		 || this.efetivo.getLinhaConfiguracao().getPostoGraduacao().equals(PostoGraduacao.SEG_TEN)
		 || this.efetivo.getLinhaConfiguracao().getPostoGraduacao().equals(PostoGraduacao.ASP)) {
			if (!this.efetivo.getLinhaConfiguracao().getLinha().equals(Linha.LPR)){
				especialidades= repository.encontrarComQueryNomeada(Especialidade.class, "Especialidade.oficiaisPorLinhaQuaisQuer", 
						new Object[]{"linhaEspecialidade", LinhaEspecialidade.valueOf(this.efetivo.getLinhaConfiguracao().getLinha().name())},
						new Object[]{"postograduacao", PostoGraduacaoEspecialidade.OFI});
			}
		} else if (this.efetivo.getLinhaConfiguracao().getPostoGraduacao().equals(PostoGraduacao.TER_SGT)) {
			if (this.efetivo.getLinhaConfiguracao().getTipo().equals(Tipo.CARR)) {
				especialidades = repository.encontrarComQueryNomeada(Especialidade.class,"Especialidade.pracasDeCarreira"	);
			}
			else  if (this.efetivo.getLinhaConfiguracao().getTipo().equals(Tipo.TMPR)) {
				especialidades = repository.encontrarComQueryNomeada(Especialidade.class,"Especialidade.pracasTecTemporarios"	);
			}
		}
		desabilitarEspecialidade = especialidades.isEmpty();
	}
	
	
	public void exibeEspecialidades() {
		boolean oficiaisComEspecialidades = false;
		boolean pracasComEspecialidades = false;

		oficiaisComEspecialidades = this.efetivo.getLinhaConfiguracao().getPostoGraduacao()
				.equals(PostoGraduacao.PRI_TEN) && !this.efetivo.getLinhaConfiguracao().getLinha().equals(Linha.LPR);
		
		oficiaisComEspecialidades |= this.efetivo.getLinhaConfiguracao().getPostoGraduacao()
				.equals(PostoGraduacao.SEG_TEN) && !this.efetivo.getLinhaConfiguracao().getLinha().equals(Linha.LPR);
		
		oficiaisComEspecialidades |= this.efetivo.getLinhaConfiguracao().getPostoGraduacao()
				.equals(PostoGraduacao.ASP) && !this.efetivo.getLinhaConfiguracao().getLinha().equals(Linha.LPR);
		
		pracasComEspecialidades = this.efetivo.getLinhaConfiguracao().getPostoGraduacao()
				.equals(PostoGraduacao.TER_SGT) && !this.efetivo.getLinhaConfiguracao().getTipo().equals(Tipo.QE);
		
		setExibirEspecialidades(oficiaisComEspecialidades || pracasComEspecialidades);
	}
	
	
	public void preparaCarregarEfetivoPrevisto() {
		this.getEspecialidades().clear();
		this.setDesabilitarEspecialidade(true);
		this.getEfetivo().setEspecialidade(null);
		this.preparaEspecialidade();
		this.getEfetivo().setFeminino(null);
		this.getEfetivo().setMasculino(null);
		if (this.isExibirEspecialidades()){
			carregarEspecialidades();
		}
		else{
			carregarEfetivoPrevisto();
		}
	}
	
	public void carregarEfetivoRealizadoParaOsValoresSelecionados() {
		preparaEspecialidade();
		atribuirLinhaConfiguracao();
		listaRealizado = repository.encontrarComQueryNomeada(EfetivoRealizado.class,
				"EfetivoRealizado.listarPorEspecialidade", new Object[] { "periodo", periodo },
				new Object[] { "om", usuario.getOrganizacaoMilitar() },
				new Object[] { "especialidade", efetivo.getEspecialidade() },
				new Object[] { "linhaConfiguracao", efetivo.getLinhaConfiguracao() });
		if (listaRealizado.size() > 0){
			setEfetivo(listaRealizado.get(0));
		}
		else{
			this.getEfetivo().setId(null);
			this.getEfetivo().setFeminino(null);
			this.getEfetivo().setMasculino(null);
		}
		
	}


	/**
	 * Recupera os registros de acordo com a OM selecionada e seta em seu respectivo lugar
	 */
	/*public void carregarRegistroPorEspecialidade(Especialidade especialidade) {
		
		listaRealizado = repository.encontrarComQueryNomeada(EfetivoRealizado.class, "EfetivoRealizado.listarPorEspecialidade", 
				new Object[]{"periodo", periodo},
				new Object[]{"om", usuario.getOrganizacaoMilitar()},
				new Object[]{"especialidade", especialidade});

		if(listaRealizado.size()>0) 
			setEfetivo(listaRealizado.get(0));
	}*/
	
	/**
	 * Salva no Banco de Dados as informações preencidas pelo usuário. 
	 * @param efetivoAuditoria
	 */
	@Auditable	
	public void salvar(EfetivoRealizado efetivoAuditoria) {
		/*
		 * Recupera a linha de configuração do banco de dados para setar o objeto com seu ID no efetivo que será 
		 * cadastrado no banco
		 */
		/*LinhaConfiguracao lc = repository.encontrarComQueryNomeada(LinhaConfiguracao.class, "LinhaConfiguracao.porParametros", 
				new Object[]{"posto", efetivo.getLinhaConfiguracao().getPostoGraduacao()}, 
				new Object[]{"linha", efetivo.getLinhaConfiguracao().getLinha()}, 
				new Object[]{"tipo", efetivo.getLinhaConfiguracao().getTipo()}).get(0);
		
		efetivo.setLinhaConfiguracao(lc);*/
		atribuirLinhaConfiguracao();
		if (this.getEfetivo().getEspecialidade().getId() != null) {
			if (this.getEfetivo().getEspecialidade().getId().intValue() == 0) {
				this.getEfetivo().setEspecialidade(null);
			}
		}
		else{
			this.getEfetivo().setEspecialidade(null);
		}
		
		
		salvarOuAtualizar();
		
		habilitaBotao=false;
	}
	
	
	public void limparEspecialidade() {
		if (!getEspecialidades().isEmpty()){
			getEspecialidades().clear();
		}
		desabilitarEspecialidade = true;
	}
	
	
	/**
	 * Recupera a resposta do usuário, pega o efetivo do periodo anterior e executa o método clonar
	 * @param valor
	 */
	
	/*public void clonarTodos() {
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		organizacoesMilitares = repository.encontrar(OrganizacaoMilitar.class);
		realizadoService.preparaClonagemTodos(organizacoesMilitares);

	}
	*/
	public void clonar(boolean valor) {
		
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		
		comboTela = repository.encontrarComQueryNomeada(ComboTela.class, "ComboTela.porOm", 
				new Object[]{"omUsuario", usuario.getOrganizacaoMilitar()});
		
		comboTela.forEach(tela->{
			adicionaCasoNaoExista(organizacoesMilitares,tela.getOrganizacaoVinculadaParaTela());
		});
		
		//Adiciona a OM principal a lista de OM que irá para a clonagem
		//organizacoesMilitares.add(usuario.getOrganizacaoMilitar());
		adicionaCasoNaoExista(organizacoesMilitares,usuario.getOrganizacaoMilitar());
		
		registro.setResposta(valor);
		
		realizadoService.preparaClonagem(registro, listaClonarRealizado, organizacoesMilitares);
		
		init();
	}
	
	private void adicionaCasoNaoExista(List<OrganizacaoMilitar> lista,OrganizacaoMilitar om) {
		if (!lista.contains(om)){
			lista.add(om);
		}
	}

/**
	 * Verifica na sua chamada se as informações seram salvas ou editadas.
	 * Salvas caso seja um novo cadastro e não tenha um Id cadastrado no Banco de Dados.
	 * Editadas caso já tenha um Id cadastrado no Banco de Dados.
	 */
	public void salvarOuAtualizar() {		
		super.executar(() -> {repository.adicionarOuMudar(efetivo); init();}, SUCESSO, FALHA);
	}

public Boolean getDesabilitarEspecialidade() {
	return desabilitarEspecialidade;
}

public void setDesabilitarEspecialidade(Boolean desabilitarEspecialidade) {
	this.desabilitarEspecialidade = desabilitarEspecialidade;
}

public boolean isExibirEspecialidades() {
	exibeEspecialidades();
	return exibirEspecialidades;
}

public void setExibirEspecialidades(boolean exibirEspecialidades) {
	this.exibirEspecialidades = exibirEspecialidades;
	}

}