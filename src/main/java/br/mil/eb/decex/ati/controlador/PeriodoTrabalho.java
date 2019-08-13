package br.mil.eb.decex.ati.controlador;

import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.servico.PeriodoService;

/**
 * Exibe lista de períodos para o administrador informar o prazo 
 * de lançamento dos efetivos.
 * 
 * @author William <b>Moreira</b> de Pinho - CAP
 * @version 1.0
 */
@Named("periodoTrabalho")
@ViewScoped
public class PeriodoTrabalho extends BaseController {

	private static final long serialVersionUID = 1L;

	private List<Periodo> periodos;
	private int anoCorrente;
	
	private static final String INTERVALO_DATA = "intervalo_data";		
	
	@Inject
	private PeriodoService service;
	
	/**
	 * Lista de períodos para o ano corrente
	 * @return lista de períodos
	 */
	public List<Periodo> getPeriodos() {
		return periodos;
	}

	/**
	 * Ano corrente
	 * @return ano corrente
	 */
	public int getAnoCorrente() {
		return anoCorrente;
	}
	
	@PostConstruct
	public void init() {
		//Recupera o ano corrente
		anoCorrente = GregorianCalendar.getInstance().get(GregorianCalendar.YEAR);
		
		//Recupera os períodos do ano corrente
		periodos = repository.encontrar(Periodo.class, "ano", anoCorrente);
		
		//Ordena os meses
		periodos.sort((p1, p2) -> p1.getMes().compareTo(p2.getMes()));
	}
	
	/**
	 * Salva um novo periodo
	 * @param event
	 */
    public void onSalvar(RowEditEvent event) {
    	Periodo periodo = (Periodo) event.getObject();
    	
    	if(!validate(periodo)) return;
    	
    	try {
    		service.salvar(periodo);
    		addMessage("confirmacao_operacao", "sucesso_na_operacao", FacesMessage.SEVERITY_INFO);
    		    		
    	} catch (Exception e) {
    		addMessage("erro_operacao", e.getMessage(), FacesMessage.SEVERITY_ERROR);
    	} finally {
    		init();
		}
    }	
    
    /**
     * Faz a validação do periodo
     * @param periodo Periodo
     * @return boolean
     */
    public boolean validate(Periodo periodo) {
    	if(periodo.getDataFinal().before(periodo.getDataInicial())) {
    		periodo.setDataInicial(null);
    		periodo.setDataFinal(null);
    		addMessage("erro_operacao", INTERVALO_DATA, FacesMessage.SEVERITY_ERROR);
    		
    		return false;
    	}

    	return true;
    }   
}