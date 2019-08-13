package br.mil.eb.decex.ati.controlador;

import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;
import br.mil.eb.decex.ati.servico.PeriodoService;

/**
 * Preenche uma lista de períodos para o ano corrente. Caso os períodos não
 * existam, este controller realizará a gravação dos períodos.
 * <p/>
 * 
 * O período selecionado pelo usuário ficará disponível na sessão da aplicação
 * como o nome periodoSelecionado ou através da anotação
 * <code>@PeriodoSelecionado</code>
 * 
 * @author William <b>Moreira</b> de Pinho - 1º TEN QCO
 * @version 1.0
 *
 */
@Named("periodoCtrl")
@SessionScoped
public class PeriodoController extends BaseController {

	private static final long serialVersionUID = 1L;

	@Inject
	private PeriodoService service;

	private Periodo periodo;

	private Periodo periodoAnterior;

	private Periodo periodoDeLancamento;

	private Integer anoSelecionado;

	private int mesSelecionado;

	private String proximoPeriodoLancamento;

	private Boolean dentroDoPeriodoDeLancamento;

	private List<Periodo> periodos;

	private List<Periodo> todosPeriodos;

	private List<Integer> anos;

	private List<Month> meses;

	/**
	 * Período selecionado pelo usuário. Esta informação é utilizada para
	 * gravação do efetivo previsto e realizado.
	 * 
	 * @return período selecionado pelo usuário
	 */
	@Produces
	@PeriodoSelecionado
	@Named("periodoSelecionado")
	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	/**
	 * Lista de períodos para o ano corrente
	 * 
	 * @return lista de períodos
	 */
	public List<Periodo> getPeriodos() {
		return periodos;
	}

	/**
	 * Guarda a informação do mês selecionado
	 * 
	 * @return mesSelecionado int
	 */
	public int getMesSelecionado() {
		return mesSelecionado;
	}

	public void setMesSelecionado(int mesSelecionado) {
		this.mesSelecionado = mesSelecionado;
	}

	/**
	 * Guarda os anos
	 * 
	 * @return anos Integer
	 */
	public List<Integer> getAnos() {
		return anos;
	}

	/**
	 * Guarda os meses
	 * 
	 * @return meses Month
	 */
	public List<Month> getMeses() {
		return meses;
	}

	/**
	 * Guarda o ano selecionado
	 * 
	 * @return anoSelecionado Integer
	 */
	public Integer getAnoSelecionado() {
		return this.anoSelecionado;
	}

	public void setAnoSelecionado(Integer anoSelecionado) {
		this.anoSelecionado = anoSelecionado;
	}

	/**
	 * Guarda o proximo periodo de lançamento
	 * 
	 * @return proximoPeriodoLancamento String
	 */
	public String getProximoPeriodoLancamento() {
		return proximoPeriodoLancamento;
	}

	public void setProximoPeriodoLancamento(String proximoPeriodoLancamento) {
		this.proximoPeriodoLancamento = proximoPeriodoLancamento;
	}

	/**
	 * Guarda o periodo de lançamento atual
	 * 
	 * @return periodoDeLancamento Periodo
	 */
	public Periodo getPeriodoDeLancamento() {
		return periodoDeLancamento;
	}

	public void setPeriodoDeLancamento(Periodo periodoDeLancamento) {
		this.periodoDeLancamento = periodoDeLancamento;
	}

	/**
	 * Verifica a informação do periodo de lançamento atual, data inicial até
	 * data final
	 * 
	 * @return dentroDoPeriodoDeLancamento Boolean
	 */
	public Boolean getDentroDoPeriodoDeLancamento() {
		return dentroDoPeriodoDeLancamento;
	}

	public void setDentroDoPeriodoDeLancamento(Boolean foraDoPeriodoDeLancamento) {
		this.dentroDoPeriodoDeLancamento = foraDoPeriodoDeLancamento;
	}

	@PostConstruct
	public void init() {
		periodo = new Periodo();
		// Carrega períodos caso não existam
		periodos = service.carregarPeriodoAnualSeNaoExitir();
		// Carrega todos os períodos
		todosPeriodos = service.carregarTodosOsPeriodos();
		// Carrega apenas os anos registrados em banco
		anos = selecionaApenasOsAnos(todosPeriodos);
		// Carrega e Ordena Lista de Meses
		buscarPeriodoAnterior();
		prepararListaDeMeses();
		preencherPeriodoDeLancamento();
	}

	/**
	 * faz a verificação de periodo de lançamento
	 * {@link #verificaSeEstamosEmPeriodoDeLancamento()} caso não esteja, faz uma
	 * busca do próximo periodo de lançamento
	 * {@link #buscarProximoPeriodoDeLancamento()} e abre uma sessão com o
	 * periodo atual
	 */
	private void preencherPeriodoDeLancamento() {
		if (!verificaSeEstamosEmPeriodoDeLancamento())
		{
			buscarProximoPeriodoDeLancamento();
		}
		this.setPeriodo(periodoDeLancamento);
		HttpSession session = (HttpSession) getContext().getExternalContext().getSession(false);
		session.setAttribute("periodoSelecionado", periodo);
		this.setProximoPeriodoLancamento(imprimirPeriodoDeLancamento());
	}

	/**
	 * imprimi o periodo atual com data inicial até data final
	 * 
	 * @return String do periodo atual
	 */
	private String imprimirPeriodoDeLancamento() {
		
		return periodoDeLancamento.toString() + " de " + formatarData(periodoDeLancamento.getDataInicial()) + " até " + formatarData(periodoDeLancamento.getDataFinal());
		
	}

	/**
	 * Formata a data para dd/MM/yyyy
	 * 
	 * @param data
	 *           Date
	 * @return dataFormatada String
	 */
	public String formatarData(Date data) {
		SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
		String dataFormatada = formataData.format(data);
		return dataFormatada;
	}

	/**
	 * Verifica se estamos no periodo atual de lançamento
	 * 
	 * @return dentroDoPeriodoDeLancamento boolean
	 */
	private boolean verificaSeEstamosEmPeriodoDeLancamento() {
		Calendar hoje = GregorianCalendar.getInstance();
		int mesAtual = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH) + 1;
		int mesAnterior = mesAtual - 1;
		// periodoDeLancamento = service.encontrarPeriodo(Month.of(mesAnterior),
		// hoje.get(GregorianCalendar.YEAR));
		if (mesAnterior == 0)
		{
			periodoDeLancamento = service.encontrarPeriodo(Month.of(12), (hoje.get(GregorianCalendar.YEAR) - 1));
		} else
		{
			// periodoDeLancamento =
			// service.encontrarPeriodo(Month.of(mesAnterior),
			// hoje.get(GregorianCalendar.YEAR));
			periodoDeLancamento = service.encontrarPeriodo(hoje);
		}
		if (periodoDeLancamento.getId() == null)
		{
			this.setDentroDoPeriodoDeLancamento(false);
		} else
		{
			Calendar dataInicial = Calendar.getInstance();
			dataInicial.setTime(periodoDeLancamento.getDataInicial());
			dataInicial.set(GregorianCalendar.HOUR, 0);
			dataInicial.set(GregorianCalendar.MINUTE, 0);
			dataInicial.set(GregorianCalendar.SECOND, 0);
			Calendar dataFinal = Calendar.getInstance();
			dataFinal.setTime(periodoDeLancamento.getDataFinal());
			dataFinal.set(GregorianCalendar.HOUR, 23);
			dataFinal.set(GregorianCalendar.MINUTE, 59);
			dataFinal.set(GregorianCalendar.SECOND, 59);
			this.setDentroDoPeriodoDeLancamento(hoje.after(dataInicial) && hoje.before(dataFinal));
		}
		return dentroDoPeriodoDeLancamento;
	}

	/**
	 * busca o próximo periodo de lançamento
	 */
	private void buscarProximoPeriodoDeLancamento03() {
		Calendar hoje = GregorianCalendar.getInstance();
		int mesAtual = GregorianCalendar.getInstance().get(GregorianCalendar.MONTH) + 1;
		periodoDeLancamento = service.encontrarPeriodo(Month.of(mesAtual), hoje.get(GregorianCalendar.YEAR));
	}

	public void buscarProximoPeriodoDeLancamento() {
		
		Calendar hoje = GregorianCalendar.getInstance();
		
		// Periodo periodo = new Periodo();
		/*
		 * GregorianCalendar.MONTH -> Janeiro = 0; GregorianCalendar.MONTH ->
		 * Fevereiro = 1;
		 */
		
		int mesAtual = hoje.get(GregorianCalendar.MONTH);
		int anoAtual = hoje.get(GregorianCalendar.YEAR);
		List<Periodo> periodos = null;
		System.out.println(mesAtual + " - " + anoAtual + " - " + Month.of(mesAtual));
		if (mesAtual == 0)
		{
			periodos = repository.encontrarComQueryNomeada(Periodo.class, "Periodo.lancamento", new Object[]{"ano", anoAtual - 1}, new Object[]{"mes", Month.of(mesAtual)});
		} else if ((mesAtual > 0) && (mesAtual < 11))
		{
			periodos = repository.encontrarComQueryNomeada(Periodo.class, "Periodo.lancamento2", new Object[]{"ano", anoAtual}, new Object[]{"mes", Month.of(mesAtual)});
		} else if (mesAtual == 11)
		{
			periodos = repository.encontrarComQueryNomeada(Periodo.class, "Periodo.lancamento", new Object[]{"ano", anoAtual + 1}, new Object[]{"mes", 0});
		}
		periodoDeLancamento = periodos.get(0);
		/*
		 * 
		 * for (Periodo per : periodos) {
		 * 
		 * Calendar dataInicial = Calendar.getInstance();
		 * dataInicial.setTime(per.getDataInicial());
		 * dataInicial.set(GregorianCalendar.HOUR,0);
		 * dataInicial.set(GregorianCalendar.MINUTE,0);
		 * dataInicial.set(GregorianCalendar.SECOND,0);
		 * 
		 * Calendar dataFinal = Calendar.getInstance();
		 * dataFinal.setTime(per.getDataFinal());
		 * dataFinal.set(GregorianCalendar.HOUR,23);
		 * dataFinal.set(GregorianCalendar.MINUTE,59);
		 * dataFinal.set(GregorianCalendar.SECOND,59);
		 * 
		 * if (hoje.after(dataInicial) && hoje.before(dataFinal)){ return periodo
		 * = per; }
		 * 
		 * }
		 * 
		 * return periodo;
		 */
	}

	/**
	 * retorna o periodo anterior de lançamento
	 */
	public Periodo BuscarPeriodoAnterior() {
		Calendar hoje = GregorianCalendar.getInstance();
		int mesAtual = hoje.get(GregorianCalendar.MONTH);
		int mesAnterior = mesAtual - 1;
		return service.encontrarPeriodo(Month.of(mesAnterior), hoje.get(GregorianCalendar.YEAR));
	}

	private void buscarPeriodoAnterior() {
		Calendar hoje = GregorianCalendar.getInstance();
		int mesAnterior = hoje.get(GregorianCalendar.MONTH);
		int anoAtual = hoje.get(GregorianCalendar.YEAR);
		// int mesAnterior = mesAtual -1 ;
		if (mesAnterior == 0)
		{
			periodoAnterior = service.encontrarPeriodo(Month.of(11), (anoAtual - 1));
		} else
		{
			periodoAnterior = service.encontrarPeriodo(Month.of(mesAnterior), anoAtual);
		}
	}

	/**
	 * Prepara uma lista com todos os meses
	 */
	private void prepararListaDeMeses() {
		meses = new ArrayList<Month>();
		meses.addAll(Periodo.getMestraduzido().keySet());
		Collections.sort(meses);
	}

	/**
	 * Seta apenas os anos para dentro de lista de anos
	 * 
	 * @param periodos
	 *           Periodo
	 * @return listaDeAnos Integer
	 */
	private List<Integer> selecionaApenasOsAnos(List<Periodo> periodos) {
		List<Integer> listaDeAnos = new ArrayList<Integer>();
		for (Periodo periodo : periodos)
		{
			if (!listaDeAnos.contains(periodo.getAno()))
			{
				listaDeAnos.add(periodo.getAno());
			}
		}
		return listaDeAnos;
	}

	/**
	 * Cria uma sessão com o periodo
	 * 
	 * @return /restrito/index.xhtml String
	 */
	public String prosseguir() {
		// Colocar período selecionado em parametro de sessao
		HttpSession session = (HttpSession) getContext().getExternalContext().getSession(false);
		session.setAttribute("periodoSelecionado", periodo);
		return "/restrito/index.xhtml?faces-redirect=true";
	}

	/**
	 * Método acionado por ajax quando da seleção de um mês ou ano na tela de
	 * login
	 */
	public void salvarPeriodo() {
		// Este método instancia um Periodo, busca no banco o ID do referido
		// Período e o coloca em parametro de sessao
		if ((getMesSelecionado() != 0) && ((getAnoSelecionado() != null) && (getAnoSelecionado().intValue() != 0)))
		{
			this.setPeriodo(service.encontrarPeriodo(Month.of(this.getMesSelecionado()), this.getAnoSelecionado()));
			HttpSession session = (HttpSession) getContext().getExternalContext().getSession(false);
			session.setAttribute("periodoSelecionado", periodo);
		}
	}

	/**
	 * Instancia um Periodo, busca no banco o ID do referido Período e o coloca
	 * em parametro de sessao
	 * 
	 * @param mes
	 *           int
	 * @param ano
	 *           Integer
	 */
	public void salvarPeriodo(int mes, Integer ano) {
		this.setPeriodo(service.encontrarPeriodo(Month.of(this.getMesSelecionado()), this.getAnoSelecionado()));
		HttpSession session = (HttpSession) getContext().getExternalContext().getSession(false);
		session.setAttribute("periodoSelecionado", periodo);
	}

	public Periodo getPeriodoAnterior() {
		return periodoAnterior;
	}

	public void setPeriodoAnterior(Periodo periodoAnterior) {
		this.periodoAnterior = periodoAnterior;
	}
	// Método para resgatar o nome em português de cada mês na classe Month
	/*
	 * public String traduzirMes(Month mes) { return
	 * mes.getDisplayName(TextStyle.FULL, new Locale("pt", "BR")); }
	 */
}
