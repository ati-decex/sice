package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.jpa.OrganizacaoMilitarRepository;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Tela;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;

/**
 * Realiza o controle das telas para cadastro de cada OM
 * 
 * @author Fernando <b>Nunes</b> de Almeida - 1º TEN
 * @version 1.0
 */

@SessionScoped
@Named("telas")
// @WebFilter(filterName="TelasController",urlPatterns="/restrito/*")
public class TelasController extends BaseController/* implements Filter */ {

	private static final long serialVersionUID = 1L;

	@Inject
	@Autenticado
	private Usuario usuario;
	private OrganizacaoMilitar organizacaoMilitar;
	private List<Tela> telas;
	private List<String> telasExcecaoFiltro;
	private int indexTelaAtual;
	private boolean exibirBotaoAnterior;
	private boolean exibirBotaoProximo;

	@Inject
	private OrganizacaoMilitarRepository omRepository;

	/**
	 * Guarda as informações da OrganizacaoMilitar
	 * 
	 * @return OrganizacaoMilitar
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}

	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	/**
	 * Telas disponíveis para cadastramento do efetivo
	 * 
	 * @return telas disponíveis para cadastramento do efetivo
	 */
	public List<Tela> getTelas() {
		return telas;
	}

	/**
	 * Faz a contagem da Tela atual
	 * 
	 * @return indexTelaAtual int
	 */
	public int getIndexTelaAtual() {
		return indexTelaAtual;
	}

	public void setIndexTelaAtual(int indexTelaAtual) {
		this.indexTelaAtual = indexTelaAtual;
	}

	/**
	 * Valida a exibição do botão anterior
	 * 
	 * @return exibirBotaoAnterior boolean
	 */
	public boolean isExibirBotaoAnterior() {
		return exibirBotaoAnterior;
	}

	public void setExibirBotaoAnterior(boolean exibirBotaoAnterior) {
		this.exibirBotaoAnterior = exibirBotaoAnterior;
	}

	/**
	 * Valida a exibição do botão próximo
	 * 
	 * @return exibirBotaoProximo boolean
	 */
	public boolean isExibirBotaoProximo() {
		return exibirBotaoProximo;
	}

	public void setExibirBotaoProximo(boolean exibirBotaoProximo) {
		this.exibirBotaoProximo = exibirBotaoProximo;
	}

	@PostConstruct
	public void init() {
		indexTelaAtual = -1;
		preencherTelas();
		preencherTelasExcecaoFiltro();
		exibirBotaoProximo = false;
		if (telas != null) {
			if (!telas.isEmpty()) {
				exibirBotaoProximo = (indexTelaAtual < (telas.size() - 1));
			}
		}

	}

	private void preencherTelasExcecaoFiltro() {
		telasExcecaoFiltro = new ArrayList<String>();
		telasExcecaoFiltro.add("cadastrar_existente.xhtml");
		telasExcecaoFiltro.add("inicio.xhtml");
		telasExcecaoFiltro.add("avisoForaPeriodo.xhtml");
		telasExcecaoFiltro.add("index.xhtml");
		telasExcecaoFiltro.add("periodo.xhtml");
		telasExcecaoFiltro.add("trocarSenha.xhtml");

	}

	/**
	 * Carrega cada organizacaoMilitar com suas telas
	 */
	public void preencherTelas() {
		organizacaoMilitar = usuario.getOrganizacaoMilitar();
		organizacaoMilitar = omRepository.encontrarComAsTelas(organizacaoMilitar.getId());
		telas = new ArrayList<Tela>();
		telas.addAll(organizacaoMilitar.getTelas());
		removerTelasIndevidas();
	}

	private void removerTelasIndevidas() {
		Tela tela;
		tela = new Tela("OFICIAIS_TEMPORARIOS");
		if (telas.contains(tela)){
			telas.remove(tela);
		}
		tela = new Tela("PRACAS_TEMPORARIOS");
		if (telas.contains(tela)){
			telas.remove(tela);
		}
	}

	@PreDestroy
	public void finalizar() {
		indexTelaAtual = -1;
	}

	/**
	 * Carrega as telas da Organização Militar logada
	 */
	public void recuperarTelasDaOMLogada() {
		organizacaoMilitar = omRepository.encontrarComAsTelas(organizacaoMilitar.getId());
	}

	/**
	 * Faz a validação da próxima tela e retorna o caminho
	 * 
	 * @return caminho String
	 */
	public String avancarTela() {
		++indexTelaAtual;
		String caminho = "";
		if (telas != null)
			if (!telas.isEmpty()) {
				caminho = telas.get(indexTelaAtual).getCaminho() + "?faces-redirect=true";
				exibirBotaoProximo = (indexTelaAtual < (telas.size() - 1));
			}

		exibirBotaoAnterior = true;

		return caminho;
	}

	/**
	 * Faz a validação da tela anterior e retorna o caminho
	 * 
	 * @return caminho String
	 */
	public String voltarTela() {
		String caminho = "cadastrar_existente.xhtml?faces-redirect=true";
		indexTelaAtual--;
		if (indexTelaAtual >= 0) {
			caminho = telas.get(indexTelaAtual).getCaminho() + "?faces-redirect=true";
		}
		exibirBotaoProximo = (indexTelaAtual < (telas.size() - 1));
		exibirBotaoAnterior = true;
		return caminho;

	}

	/*
	 * @Override public void init(FilterConfig filterConfig) throws
	 * ServletException { init();
	 * 
	 * }
	 * 
	 * @Override public void doFilter(ServletRequest servletRequest,
	 * ServletResponse response, FilterChain chain) throws IOException,
	 * ServletException { HttpServletRequest request = (HttpServletRequest)
	 * servletRequest;
	 * 
	 * if (!verificaExcecao(request.getRequestURI())){ if
	 * (!verificaSePaginaPermitida(request.getRequestURI())){
	 * request.getRequestDispatcher("../error404.xhtml").forward(request,
	 * response); } } System.out.println("["+request.getRequestURI()+" , "
	 * +verificaSePaginaPermitida(request.getRequestURI())+"]");
	 * chain.doFilter(request, response);
	 * 
	 * } private boolean verificaExcecao(String url) { for (String tela :
	 * telasExcecaoFiltro) { if (url.contains(tela)){ return true; } } return
	 * false; }
	 * 
	 * @Override public void destroy() { // TODO Auto-generated method stub
	 * 
	 * }
	 */
	private boolean verificaSePaginaPermitida(String url) {
		for (Tela tela : telas) {
			if (url.contains(tela.getCaminho())) {
				return true;
			}
			System.out.println("[" + url + " , " + tela.getCaminho());
		}
		return false;
	}

	
		
}