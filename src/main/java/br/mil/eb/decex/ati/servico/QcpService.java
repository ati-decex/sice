package br.mil.eb.decex.ati.servico;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;

import br.mil.eb.decex.ati.jpa.Repository;
import br.mil.eb.decex.ati.modelo.EfetivoPrevisto;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.QCP;

/**
 * Regra para criação de novos QCPs e com clonagem automática.
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3° SGT
 * @version 1.0
 */
@Stateless
public class QcpService implements Serializable {

	private static final long serialVersionUID = 1L;

	private QCP qcp;
	private List<QCP> qcps;

	@Inject
	private Repository repository;

	@Inject
	private EfetivoPrevistoService previstoService;

	/**
	 * Guarda o último QCP ativo
	 * 
	 * @return QCP
	 */
	public QCP getQcp() {
		return qcp;
	}

	public void setQcp(QCP qcp) {
		this.qcp = qcp;
	}

	@PostConstruct
	private void init() {
		qcp = new QCP();
	}

	/**
	 * Recupera o QCP ativo pela OrganizacaoMilitar
	 * 
	 * @param om
	 *            OrganizacaoMilitar
	 */
	public void recuperarQCP(OrganizacaoMilitar om) {
		qcps = repository.encontrarComQueryNomeada(QCP.class, "Qcp.porOrganizacaoPorAtivo",
				new Object[] { "organizacao", om });

		if (!qcps.isEmpty())
			setQcp(qcps.get(0));
	}

	/**
	 * Recupera o QCP ativo pela OrganizacaoMilitar
	 * 
	 * @param om
	 *            OrganizacaoMilitar
	 * @return _qcp QCP
	 */
	public QCP retornarQCPPorOM(OrganizacaoMilitar om) {
		QCP _qcp = new QCP();

		qcps = repository.encontrarComQueryNomeada(QCP.class, "Qcp.porOrganizacaoPorAtivo",
				new Object[] { "organizacao", om });

		if (!qcps.isEmpty())
			_qcp = qcps.get(0);

		return _qcp;
	}

	/**
	 * Roda o método {@link #recuperarQCP(OrganizacaoMilitar)} para recuperar o
	 * QCP e atualizar o Ativo para false
	 * 
	 * @param om
	 *            OrganizacaoMilitar
	 */
	public void atualizar(OrganizacaoMilitar om) {
		// recuperarQCP(om);
		setQcp(retornarQCPPorOM(om));
		qcp.setAtivo(false);
		repository.mudar(qcp);
	}

	/**
	 * Salva um novo QCP com o Ativo true e faz a clonagem de EfetivoPrevisto do
	 * último QCP
	 * 
	 * @param qcpSalvar
	 *            QCP
	 */

	public void salvar(QCP qcpSalvar) {
		qcpSalvar.setEfetivosPrevistos(null);
		atualizar(qcpSalvar.getOrganizacaoMilitar());
		repository.adicionarOuMudar(qcpSalvar);
		previstoService.clonar(qcpSalvar, qcp.getEfetivosPrevistos());
	}

	public void salvar(QCP qcpAtual, QCP qcpNovo) {

		qcpAtual.setAtivo(false);
		System.out.println("id qcp atual: " + qcpAtual.getId());

		repository.adicionarOuMudar(qcpAtual);
		qcpNovo.setAtivo(true);

		repository.adicionarOuMudar(qcpNovo);
		System.out.println("id qcp novo: " + qcpNovo.getId());

		previstoService.clonar(qcpNovo, qcpAtual.getEfetivosPrevistos());

	}

	public void salvar(QCP qcpAtual, QCP qcpNovo, List<EfetivoPrevisto> listaEfetivosPrevistosAtual) {
		
		System.out.println("01 id qcp atual: " + qcpAtual.getId());
		System.out.println("02 id qcp novo : " + qcpNovo.getId());
		System.out.println("03 size lista previstos atual: " + qcpAtual.getEfetivosPrevistos().size());
		System.out.println("04 size lista previstos novo: " + qcpNovo.getEfetivosPrevistos().size());
		
		repository.mudar(qcpAtual);
		
		System.out.println("05 id qcp atual: " + qcpAtual.getId());
		System.out.println("06 id qcp novo : " + qcpNovo.getId());
		System.out.println("07 size lista previstos atual: " + qcpAtual.getEfetivosPrevistos().size());
		System.out.println("08 size lista previstos novo: " + qcpNovo.getEfetivosPrevistos().size());
		
		repository.adicionar(qcpNovo);
		qcpNovo =	repository.encontrarComQueryNomeada(QCP.class, "Qcp.porOrganizacaoPorAtivo",
						new Object[] { "organizacao", qcpNovo.getOrganizacaoMilitar() }).get(0);
		
		System.out.println("09 id qcp atual: " + qcpAtual.getId());
		System.out.println("10 id qcp novo : " + qcpNovo.getId());
		System.out.println("11 size lista previstos atual: " + qcpAtual.getEfetivosPrevistos().size());
		System.out.println("12 size lista previstos novo: " + qcpNovo.getEfetivosPrevistos().size());
		
		previstoService.clonar(qcpNovo, listaEfetivosPrevistosAtual);
		
		
		System.out.println("13 id qcp atual: " + qcpAtual.getId());
		System.out.println("14 id qcp novo : " + qcpNovo.getId());
		System.out.println("15 size lista previstos atual: " + qcpAtual.getEfetivosPrevistos().size());
		System.out.println("16 size lista previstos novo: " + qcpNovo.getEfetivosPrevistos().size());
		
	}

}
