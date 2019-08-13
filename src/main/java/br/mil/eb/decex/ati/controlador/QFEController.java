package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.ForcaInstrutores;
import br.mil.eb.decex.ati.enumerado.LinhaQFE;
import br.mil.eb.decex.ati.enumerado.PostoGraduacaoQFE;
import br.mil.eb.decex.ati.modelo.EfetivoInstrutorMonitor;
import br.mil.eb.decex.ati.modelo.EfetivoQFE;
import br.mil.eb.decex.ati.modelo.EspecialidadeQFE;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.TipoQFE;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;
import br.mil.eb.decex.ati.util.Auditable;

/**
 * Realiza o cadastro do efetivo Existente de cada OM</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */

@ViewScoped
@Named("qfe")
public class QFEController extends BaseController {

	private static final long serialVersionUID = 1L;
//	
//	@Inject
//    @Autenticado
//    private Usuario usuario;
//	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
//	@Inject
//	private OrganizacaoMilitar organizacao;

	@Inject
	private TelasController telasController;
	
	private EfetivoQFE efetivo;
//	private OrganizacaoMilitar organizacaoMilitar;
//	private OrganizacaoMilitar organizacaoMilitarSuperior;
	
	private List<EfetivoQFE> listaEfetivoQfe;
	private List<LinhaQFE> linhas;
	private List<TipoQFE> tiposqfe;
	private List<PostoGraduacaoQFE> postos;
	private List<EspecialidadeQFE> especialidadesQfe;
	private List<OrganizacaoMilitar> superiores;
	private List<OrganizacaoMilitar> organizacoesMilitares;
	
	private boolean habilitaBotao=true;
	
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
	 * Carrega a OrganizacaoMilitar  
	 * @return OrganizacaoMilitar
	 */
//	public OrganizacaoMilitar getOrganizacaoMilitar() {
//		return organizacaoMilitar;
//	}
//	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
//		this.organizacaoMilitar = organizacaoMilitar;
//	}
//	
//	/**
//	 * Carrega a OrganizacaoMilitar  
//	 * @return OrganizacaoMilitar
//	 */
//	public OrganizacaoMilitar getOrganizacaoMilitarSuperior() {
//		return organizacaoMilitarSuperior;
//	}
//	public void setOrganizacaoMilitarSuperior(OrganizacaoMilitar organizacaoMilitarSuperior) {
//		this.organizacaoMilitarSuperior = organizacaoMilitarSuperior;
//	}
	

	/**
	 * Lista de superiores	
	 * @return OrganizacaoMilitar
	 */
	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}
	
	/**
	 * realiza a função de habilitar o botão 
	 * @return habilitaBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
	}
	
	public List<TipoQFE> getTiposqfe() {
		return tiposqfe;
	}
	public void setTiposqfe(List<TipoQFE> tiposqfe) {
		this.tiposqfe = tiposqfe;
	}
	
	
	/**
	 * lista de especialidade cadastradas
	 * @return Especialidade
	 */
	public List<EspecialidadeQFE> getEspecialidadesQfe() {
		if (especialidadesQfe == null) {
			especialidadesQfe = new ArrayList<EspecialidadeQFE>();
		}
		return especialidadesQfe;
	}

	/**
	 * lista de efetivoExistente cadastrado
	 * @return EfetivoExistente
	 */
	public List<EfetivoQFE> getListaEfetivoRealizado() {
		if (listaEfetivoQfe == null){
			listaEfetivoQfe = new ArrayList<EfetivoQFE>();
		}
		return listaEfetivoQfe;
	}

	/**
	 * Lista de postos e graduações para escolha do usuário solicitante
	 * @return Posto/Graduação do usuário solicitante
	 */	
	public List<PostoGraduacaoQFE> getPostos() {
		return Arrays.asList(PostoGraduacaoQFE.values()); 
	}
	
	/**
	 * Conjunto de linhas para escolha do usuário solicitante
	 * @see br.mil.eb.decex.ati.enumerado.Linha
	 * @return Linha do usuário solicitante
	 */	
	public List<LinhaQFE> getLinhas() {
		return Arrays.asList(LinhaQFE.values());
	}
	

	/**
	 * Guarda as informações do efetivo existente
	 * @return {@link EfetivoQFE}
	 */
	public EfetivoQFE getEfetivo() {
		if (efetivo==null){
			efetivo = new EfetivoQFE();	
		}
		return efetivo;
	}
	public void setEfetivo(EfetivoQFE efetivo) {
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
	
	
	public List<EfetivoQFE> getListaEfetivoQfe() {
		return listaEfetivoQfe;
	}
	public void setListaEfetivoQfe(List<EfetivoQFE> listaEfetivoQfe) {
		this.listaEfetivoQfe = listaEfetivoQfe;
	}
	
	
	@PostConstruct
	public void init() {
		
		
		efetivo = new EfetivoQFE();
//		organizacaoMilitar = new OrganizacaoMilitar();
//		organizacaoMilitarSuperior = new OrganizacaoMilitar();
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
//		getTelasController().init();

		efetivo.setPeriodo(periodo);
		efetivo.setOrganizacaoMilitar(usuario.getOrganizacaoMilitar());
		listaEfetivoQfe=  new ArrayList<EfetivoQFE>();
		superiores = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class, "OrganizacaoMilitar.porSubordinado",
				new Object[]{"sigla", usuario.getOrganizacaoMilitar()}).get(0).getSuperiores();
		
		
		habilitaBotao= true;
		
//		listaEfetivoQfe = repository.encontrarComQueryNomeada(EfetivoQFE.class,"efetivoQfe.listarPorPeriodo",
//				new Object[]{"periodo", periodo},
//				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
//		
		
//		if (listaEfetivoQfe.size()>0)
//			habilitaBotao=false;
		listarRegistro();
	}
	
	
	/**
	 * Limpa a a organização militar superior quando for selecionado uma outra organização no xhtml.
	 */
	public void carregarOrganizacaoMilitar() {
//		organizacaoMilitar = new OrganizacaoMilitar();
		limpar();
		organizacoesMilitares.clear();
		organizacoesMilitares.add(usuario.getOrganizacaoMilitar());
		listarRegistro();
	}
	
	
	/**
	 * carrega informaços do enum  forcaInstrutores 
	 */
	public void carregarPostoGraduação() {
		
		postos = new ArrayList<PostoGraduacaoQFE>();
		postos.addAll(Arrays.asList(PostoGraduacaoQFE.values()));	
		limpar();
		
	}
	
	
	
	/**
	 * Carrega a linha de acordo com o Posto/Graduação selecionado
	 */
	public void carregarLinhaMilitar() {
		
		linhas = new ArrayList<LinhaQFE>();
		linhas.addAll(Arrays.asList(LinhaQFE.values()));	
		
	}
	
	/**
	 * Carrega Tipo de militares do quadro QFE
	 */
	public void carregarTipoMilitar() {		
		tiposqfe= repository.encontrar(TipoQFE.class );
	}
	
	/**
	 * Carrega  EspecialidadeQFE de acordo com o tipo selecionada
	 */
    public void carregarEspecialidadeQfe() {		
		
    	especialidadesQfe= repository.encontrarComQueryNomeada(EspecialidadeQFE.class, "EspecialidadeQfe.tipoQfe", 
									new Object[]{"tipoQfe", efetivo.getTipoQfe() });

    
    }
    /**
	 * Recupera registros do banco de dados para popular a data grid
	 */
	public void listarRegistro() {
		superiores.forEach(listaOm->{
			List<EfetivoQFE> lista = repository.encontrarComQueryNomeada(EfetivoQFE.class, "efetivoQfe.listarPorOm",
					new Object[]{"periodo", periodo},
					new Object[]{"organizacaoM", usuario.getOrganizacaoMilitar()},
					new Object[]{"organizacao", listaOm});
			
			if (lista.size()>0) {
				listaEfetivoQfe.addAll(lista);
				habilitaBotao=false;
			}
		});
	}
	
    /**
	 * Recupera os registros de acordo com a OM selecionada e seta em seu respectivo lugar
	 */
	public void carregarRegistro() {
		limpar();
		
		listaEfetivoQfe = repository.encontrarComQueryNomeada(EfetivoQFE.class, "efetivoQfe.listarPorTodos", 
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()},				 
				new Object[]{"linha", efetivo.getLinha()},
				new Object[]{"postoGraduacao",efetivo.getPostoGraduacao()},
				new Object[]{"tipoQfe", efetivo.getTipoQfe()},
				new Object[]{"especialidadeQfe", efetivo.getEspecialidadeQfe()});
		
		if (listaEfetivoQfe.size() > 0) {
			setEfetivo(listaEfetivoQfe.get(0));
			habilitaBotao=false;
		}
	}
	
	public void limpar() {
		efetivo.setId(null);
		efetivo.setMasculino(null);
		
	}
	
	public void limparEspecialidade() {
		if (!getEspecialidadesQfe().isEmpty()){
			getEspecialidadesQfe().clear();
		}
	}
	
	/**
	 * Prepara o salvar ou atualizar
	 */
	public void prepararDados() {
//		efetivo.setOrganizacaoMilitar(organizacaoMilitar);
		salvarOuAtualizar(efetivo);
		init();
		habilitaBotao=false;
	}
//	
	
	/**
	 * Salva um novo cadastro de intrutores e monitores no Banco de Dados
	 */
	public void salvarOuAtualizar(EfetivoQFE efetivo) {
		super.executar(() -> {repository.adicionarOuMudar(efetivo);}, SUCESSO, FALHA);
	}
	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(EfetivoQFE editarEfetivo) {		
		setEfetivo(editarEfetivo);
		salvarOuAtualizar(editarEfetivo);
	}
	
	/**
	 * Verifica na sua chamada se as informações seram salvas ou editadas.
	 * Salvas caso seja um novo cadastro e não tenha um Id cadastrado no Banco de Dados.
	 * Editadas caso já tenha um Id cadastrado no Banco de Dados.
	 */
//	public void salvarOuAtualizar() {		
//		super.executar(() -> {repository.adicionarOuMudar(efetivo); init();}, SUCESSO, FALHA);
//	}
	
	/**
	 * Exclui a instancia de EfetivoRealizado da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	public void excluir(EfetivoQFE excluirEfetivo) {
		super.executar(() -> {repository.apagar(EfetivoQFE.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}


}