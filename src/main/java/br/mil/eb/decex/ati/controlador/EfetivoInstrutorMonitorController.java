 package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.ForcaInstrutores;
import br.mil.eb.decex.ati.enumerado.TipoInstrutorEMonitor;
import br.mil.eb.decex.ati.modelo.EfetivoInstrutorMonitor;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Realiza o cadastro do Efetivo dos Instrutores e Monitores</p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("instrutorMonitor")
public class EfetivoInstrutorMonitorController extends BaseController {

	private static final long serialVersionUID = 1L;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	

	private List<OrganizacaoMilitar> organizacoesMilitares;
	private List<EfetivoInstrutorMonitor> efetivoInstrutoresMonitores;
	private List<EfetivoInstrutorMonitor> listaInstrutoresMonitores;
	private List<EfetivoInstrutorMonitor> efetivos;
	private List<OrganizacaoMilitar> superiores;
	private List<ForcaInstrutores> forcas;
	
	private OrganizacaoMilitar organizacaoMilitar;
	private OrganizacaoMilitar organizacaoMilitarSuperior;
	private ForcaInstrutores alunosForca;
	private EfetivoInstrutorMonitor instrutor;
	private EfetivoInstrutorMonitor monitor;
	private EfetivoInstrutorMonitor efetivo;
	private boolean habilitaBotao;	

	private static final String SUCESSO = "sucesso_na_operacao";
	private static final String FALHA = "operacao_nao_realizada";
	
	public EfetivoInstrutorMonitor getEfetivo() {
		return efetivo;
	}
	public void setEfetivo(EfetivoInstrutorMonitor efetivo) {
		this.efetivo = efetivo;
	}

	/**
	 * habilita a função de abilitar o botão apartir do prencimento do formulario, ou se ja estiver informação cadastrada.
	 * @return HabilitarBotao
	 */
	public boolean isHabilitaBotao() {
		return habilitaBotao;
	}
	
	/**
	 * habilita a função de habilitar o para aparecer o combo;
	 * @return HabilitaForca
	 */
//	public boolean isHabilitaForca() {
//		return habilitaForca;
//	}
	
	/**
	 * Lista de superiores	
	 * @return OrganizacaoMilitar
	 */
	public List<OrganizacaoMilitar> getSuperiores() {
		return superiores;
	}
	
	/**
	 * Carrega a lista de do efetivo de Instrutores e monitores para poder retornar a lista cadastrada 
	 * @return lista de EfetivoInstrutorMonitor
	 */
	public List<EfetivoInstrutorMonitor> getListaInstrutoresMonitores() {
		return listaInstrutoresMonitores;
	}
	public void setListaInstrutoresMonitores(List<EfetivoInstrutorMonitor> listaInstrutoresMonitores) {
		this.listaInstrutoresMonitores = listaInstrutoresMonitores;
	}

	/**
	 * Carrega a lista de EfetivoInstrutorMonitor recuperando registro do banco de dados 
	 * @return lista de Organizações Militares
	 */
	public List<EfetivoInstrutorMonitor> getEfetivos() {
		return efetivos;
	}
	public void setEfetivos(List<EfetivoInstrutorMonitor> efetivos) {
		this.efetivos = efetivos;
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
	 * Carrega a lista de do efetivo de instrutores e monitores 
	 * @return lista de EfetivoInstrutorMonitor
	 */
	public List<EfetivoInstrutorMonitor> getEfetivoInstrutoresMonitores() {
		return efetivoInstrutoresMonitores;
	}
	public void setEfetivoInstrutoresMonitores(List<EfetivoInstrutorMonitor> efetivoInstrutoresMonitores) {
		this.efetivoInstrutoresMonitores = efetivoInstrutoresMonitores;
	}
	
	/**
	 * Carrega a instrutor masculinos e femininos  
	 * @return monitor
	 */
	public EfetivoInstrutorMonitor getInstrutor() {
		return instrutor;
	}
	public void setInstrutor(EfetivoInstrutorMonitor instrutor) {
		this.instrutor = instrutor;
	}
	
	/**
	 * Carrega a monitores masculinos e femininos  
	 * @return monitor
	 */
	public EfetivoInstrutorMonitor getMonitor() {
		return monitor;
	}
	public void setMonitor(EfetivoInstrutorMonitor monitor) {
		this.monitor = monitor;
	}
	
	/**
	 * Carrega valores de Forca com valores do Enum ForcaInstrutores
	 * @see br.mil.eb.decex.ati.enumerado.ForcaInstrutores
	 * @returnf ForcaInstrutores
	 */
	public ForcaInstrutores getAlunosForcas() {
		return alunosForca;
	}
	public void setAlunosForcas(ForcaInstrutores alunosForca) {
		this.alunosForca = alunosForca;
	}
	
	/**
	 * Carrega a OrganizacaoMilitar  
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}
	
	/**
	 * Carrega a OrganizacaoMilitar  
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getOrganizacaoMilitarSuperior() {
		return organizacaoMilitarSuperior;
	}
	public void setOrganizacaoMilitarSuperior(OrganizacaoMilitar organizacaoMilitarSuperior) {
		this.organizacaoMilitarSuperior = organizacaoMilitarSuperior;
	}
	
	/**
	 * Carrega a lista forca com valores do Enum forca
	 * @see br.mil.eb.decex.ati.enumerado.Forca
	 * @returnf Forca
	 */
	public List<ForcaInstrutores> getForcas() {
		return forcas;
	}
	
	@PostConstruct
	public void init() {
		instrutor = new EfetivoInstrutorMonitor();
		monitor = new EfetivoInstrutorMonitor();
		organizacaoMilitar = new OrganizacaoMilitar();
		organizacaoMilitarSuperior = new OrganizacaoMilitar();
		forcas = new ArrayList<ForcaInstrutores>();
		organizacoesMilitares = new ArrayList<OrganizacaoMilitar>();
		efetivos = new ArrayList<EfetivoInstrutorMonitor>();
		superiores = repository.encontrarComQueryNomeada(OrganizacaoMilitar.class, "OrganizacaoMilitar.porSubordinado",
				new Object[]{"sigla", usuario.getOrganizacaoMilitar()}).get(0).getSuperiores();
		instrutor.setPeriodo(periodo);
		monitor.setPeriodo(periodo);
		habilitaBotao=true;
//		habilitaForca=false;
		listarRegistro();
//		carregarForca();
	}
	
	/**
	 * Recupera registros do banco de dados para popular a data grid
	 */
	public void listarRegistro() {
		superiores.forEach(listaOm->{
			List<EfetivoInstrutorMonitor> lista = repository.encontrarComQueryNomeada(EfetivoInstrutorMonitor.class, "efetivoInstrutorMonitor.listarPorOm",
					new Object[]{"periodo", periodo},
					new Object[]{"organizacaoM", usuario.getOrganizacaoMilitar()},
					new Object[]{"organizacao", listaOm});
			
			if (lista.size()>0) {
				efetivos.addAll(lista);
				habilitaBotao=false;
			}
		});
	}
	
	/**
	 * Seta os valores da Organização Militar e o tipo(Intrutor, Aluno de intrutor, Monitor ou Aluno de Monitor)
	 */
	public void organizarValores() {
		instrutor.setOrganizacaoMilitar(usuario.getOrganizacaoMilitar());
		instrutor.setOrganizacaoMilitarSuperior(organizacaoMilitarSuperior);
		instrutor.setTipoInstrutorEMonitor(TipoInstrutorEMonitor.INSTRUTOR);
		instrutor.setForcaInstrutores(alunosForca);

		monitor.setOrganizacaoMilitar(usuario.getOrganizacaoMilitar());
		monitor.setOrganizacaoMilitarSuperior(organizacaoMilitarSuperior);
		monitor.setTipoInstrutorEMonitor(TipoInstrutorEMonitor.MONITOR);
		monitor.setForcaInstrutores(alunosForca);
	}
	


	/**
	 * carrega informaços do enum  forcaInstrutores 
	 */
	public void carregarForca() {
		forcas.addAll(Arrays.asList(ForcaInstrutores.values()));
		alunosForca=null;
		limpar();
	}
	
	/**
	 * Limpa a a organização militar superior quando for selecionado uma outra organização no xhtml.
	 */
	public void carregarOrganizacaoMilitar() {
		organizacaoMilitar = new OrganizacaoMilitar();
		limpar();
		organizacoesMilitares.clear();
		organizacoesMilitares.add(usuario.getOrganizacaoMilitar());
		alunosForca=null;
		listarRegistro();
	}
	
	/**
	 * Limpa Id, Masculino e Feminino de alunosMilitarOutrasForca
	 */
	public void limpar() {
		instrutor.setId(null);
		instrutor.setMasculino(null);
		instrutor.setFeminino(null);
		
		monitor.setId(null);
		monitor.setMasculino(null);
		monitor.setFeminino(null);		
	}
	
	/**
	 * Verifica se já existe um valor de alunosMilitarOutrasForca no banco de dados e retornar as informaços na tela 
	 */
	public void carregarRegistro() {
		limpar();
		listaInstrutoresMonitores= repository.encontrarComQueryNomeada(EfetivoInstrutorMonitor.class, "efetivoInstrutorMonitor.listarPorForcaEPorSuperior",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacaoSuperior",organizacaoMilitarSuperior},
				new Object[]{"organizacaoSubordinada",organizacaoMilitar},
				new Object[]{"forcaInstrutores", alunosForca});
		
		if(listaInstrutoresMonitores.size() > 0) {
			listaInstrutoresMonitores.forEach(efetivo ->{
				if (efetivo.getTipoInstrutorEMonitor().equals(TipoInstrutorEMonitor.INSTRUTOR))
					setInstrutor(efetivo);
				if(efetivo.getTipoInstrutorEMonitor().equals(TipoInstrutorEMonitor.MONITOR))
					setMonitor(efetivo);
			});
		}
		
		organizarValores();
	}
	
	/**
	 * Exclui a instancia de EfetivoRealizado da base de dados de acordo com o id selecionado
	 * @param excluirEfetivo
	 */
	
	public void excluir(EfetivoInstrutorMonitor excluirEfetivo) {
		super.executar(() -> {repository.apagar(EfetivoInstrutorMonitor.class, excluirEfetivo.getId()); init();}, SUCESSO, FALHA);
	}
	
	/**
	 * Edita as informações salvas, ja cadastrada no Banco de Dados pelo usuário.
	 * @param editarEfetivo
	 */
	public void editar(EfetivoInstrutorMonitor editarEfetivo) {		
		salvarOuAtualizar(editarEfetivo);
	}
	
	/**
	 * Prepara o salvar ou atualizar
	 */
	public void prepararDados() {
		monitor.setOrganizacaoMilitar(organizacaoMilitar);
		instrutor.setOrganizacaoMilitar(organizacaoMilitar);
		salvarOuAtualizar(monitor);
		salvarOuAtualizar(instrutor);
		init();
		habilitaBotao=false;
	}

	/**
	 * Salva um novo cadastro de intrutores e monitores no Banco de Dados
	 */
	public void salvarOuAtualizar(EfetivoInstrutorMonitor MOINS) {
		super.executar(() -> {repository.adicionarOuMudar(MOINS);}, SUCESSO, FALHA);
	}
}
