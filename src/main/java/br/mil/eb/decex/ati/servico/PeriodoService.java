package br.mil.eb.decex.ati.servico;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.hibernate.criterion.Order;

import br.mil.eb.decex.ati.jpa.Repository;
import br.mil.eb.decex.ati.modelo.Periodo;

/**
 * Regra para criação de períodos de informações de efetivos.
 * 
 * @author William <b>Moreira</b> de Pinho - CAP QCO
 * @version 1.0
 */
@Stateless
public class PeriodoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Repository repository;

	private static final Map<Integer, Month> meses;

	static {
		meses = new HashMap<Integer, Month>();
		meses.put(1, Month.JANUARY);
		meses.put(2, Month.FEBRUARY);
		meses.put(3, Month.MARCH);
		meses.put(4, Month.APRIL);
		meses.put(5, Month.MAY);
		meses.put(6, Month.JUNE);
		meses.put(7, Month.JULY);
		meses.put(8, Month.AUGUST);
		meses.put(9, Month.SEPTEMBER);
		meses.put(10, Month.OCTOBER);
		meses.put(11, Month.NOVEMBER);
		meses.put(12, Month.DECEMBER);
	}

	/**
	 * Verifica no banco de dados se existe o período para aquele ano gravado,
	 * caso não exista faz a gravação do período para todos os meses do ano
	 * corrente.
	 * 
	 * @return lista de períodos para o ano corrente
	 * @throws Exception
	 *             Erro durante a verificação e/ou gravação do período
	 */
	public List<Periodo> carregarPeriodoAnualSeNaoExitir() throws RuntimeException {

		// Recupera o ano correte
		Integer anoCorrente = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);

		// Verifica ano corrente na base
		List<Periodo> periodos = repository.encontrar(Periodo.class, "ano", anoCorrente);

		// Se o ano não existir, grava no banco de dados
		if (periodos == null || periodos.size() == 0) {
			for (int i = 1; i <= 12; ++i)
				repository.adicionar(new Periodo(meses.get(Integer.valueOf(i)), anoCorrente));

			// Após a gravação, refaz a busca para trazer o período com seus
			// id's
			periodos = repository.encontrar(Periodo.class, "ano", anoCorrente);

		}

		return periodos;

	}

	/**
	 * Salva um período
	 * @param periodo período de trabalho
	 * @throws Exception exceções de Regras de negócio
	 */
	public void salvar(Periodo periodo) throws Exception {
		
		try {
			
			validacaoPeriodoNoMes(periodo);
			repository.adicionarOuMudar(periodo);
			
		} catch(Exception e) {
			throw new Exception(e.getMessage());			
		}
	}
	
	/**
	 *  Esse método busca trazer o Id do Período passando o Mês e o Ano como parâmetros
	 * @param mes
	 * @param ano
	 * @return
	 */
	public Periodo encontrarPeriodo(Month mes, int ano) {
		Periodo periodo = new Periodo();
		List<Periodo> periodos = repository.encontrar(Periodo.class, "ano", ano);
		for (Periodo per : periodos) {
			if (mes.getValue() == per.getMes().getValue()) {
				return periodo = per;
			}
		}
		return periodo;
	}
	
	public Periodo encontrarPeriodo(Calendar hoje) {
		
		Periodo periodo = new Periodo();
		
		List<Periodo> periodos = repository.encontrar(Periodo.class, "ano", hoje.get(GregorianCalendar.YEAR));
		
		for (Periodo per : periodos) {
			
			Calendar dataInicial = Calendar.getInstance();
			dataInicial.setTime(per.getDataInicial());
			dataInicial.set(GregorianCalendar.HOUR,0);
			dataInicial.set(GregorianCalendar.MINUTE,0);
			dataInicial.set(GregorianCalendar.SECOND,0);
			
			Calendar dataFinal = Calendar.getInstance();
			dataFinal.setTime(per.getDataFinal());
			dataFinal.set(GregorianCalendar.HOUR,23);
			dataFinal.set(GregorianCalendar.MINUTE,59);
			dataFinal.set(GregorianCalendar.SECOND,59);
			
			if (hoje.after(dataInicial) && hoje.before(dataFinal)){
				return periodo = per;
			}
			
		}
		
		return periodo;
		
	}
	
	/**
	 *  Traz todos os períodos indistintamente
	 * @return
	 * @throws RuntimeException
	 */
	public List<Periodo> carregarTodosOsPeriodos() throws RuntimeException {
		List<Periodo> periodos = repository.encontrarComQueryNomeada(Periodo.class,"Periodo.ordenado" );
		return periodos;

	}
	
	public List<Periodo> carregarPeriodosRelatorio() throws RuntimeException {
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 20);
		c.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH)+1); 
		List<Periodo> periodos = repository.encontrarComQueryNomeada(Periodo.class,"Periodo.incluindoProximoMes",
		new Object[] { "datainicial",c.getTime()});
		return periodos;

	}

	/**
	 * Validação do intervalo de lançamento. Valida intervalos apenas dentro do mesmo mês e  
	 * subsequentes ao mes de lançamento do efetivo.
	 * @param periodo período de trabalho
	 * @throws Exception manipulação de datas
	 */
	public void validacaoPeriodoNoMes(Periodo periodo) throws Exception {

		Calendar cal = Calendar.getInstance();
		
		/*
		 * Recupero o período de lançamento a somo um ao mês 
		 */
		cal.set(periodo.getAno(), (periodo.getMes().getValue()-1), 1);		
		cal.add(Calendar.MONTH, 1);
		
		//Realizo formatação de data para retirada da hora
		String dataFormatada = cal.get(Calendar.DAY_OF_MONTH) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.YEAR) ;
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Date dataFmt = fmt.parse(dataFormatada);
				
		cal.setTime(dataFmt);		
		
		//Verifica se o intervalo está dentro do mesmo mês e ano
		if(cal.getTime().compareTo(periodo.getDataInicial()) > 0)
			throw new Exception("intervalo_lancamento");						
		
		Month mes = (cal.get(Calendar.MONTH)+2 > 12 ? Month.JANUARY : Month.of(cal.get(Calendar.MONTH)+2));
						
		cal.setTime(periodo.getDataInicial());
		
		Month intervalo = Month.of(cal.get(Calendar.MONTH)+1);		
		
		//Verifica se está dentro do mesmo mês (o ano já foi validado acima)
		if(!mes.equals(intervalo))
			throw new Exception("intervalo_lancamento");
		
		Calendar calInicio = Calendar.getInstance();
		Calendar calFim = Calendar.getInstance();
		
		calInicio.setTime(periodo.getDataInicial());
		calFim.setTime(periodo.getDataFinal());
		
		Month mesInicio = Month.of(calInicio.get(Calendar.MONTH)+1);
		Month mesFim = Month.of(calFim.get(Calendar.MONTH)+1);		
		
		//Verifica se é o mês subsequente
		if(!mesInicio.equals(mesFim))
			throw new Exception("intervalo_lancamento");					
	}		
}