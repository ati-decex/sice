package br.mil.eb.decex.ati.servico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.mil.eb.decex.ati.jpa.Repository;
import br.mil.eb.decex.ati.modelo.EfetivoPrevisto;
import br.mil.eb.decex.ati.modelo.QCP;

/**
 * Regra para manipulação de efetivos previstos.
 * 
 * @author William <b>Moreira</b> de Pinho - CAP QCO
 * @version 1.0
 */
@Stateless
public class EfetivoPrevistoService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Repository repository;

	/**
	 * Clona um efetivo previsto de acordo com o efetivo definido no último QCP
	 * ativo, setando o novo QCP para os novos efetivos
	 * 
	 * @param qcp
	 *            novo QCP válido que será utilizado nos efetivos que serão
	 *            clonados
	 * @param efetivos
	 *            lista de efetivos previsto a serem clonados
	 */
	public void clonar(QCP qcp, List<EfetivoPrevisto> efetivos) {

		List<EfetivoPrevisto> _efetivos = new ArrayList<EfetivoPrevisto>();
		System.out.println("01: " + efetivos.size());
		System.out.println("02: " + _efetivos.size());

		for (EfetivoPrevisto ep : efetivos) {
			if (!_efetivos.contains(ep)){
				_efetivos.add(ep);
			}
		}
		
		System.out.println("03: " + efetivos.size());
		System.out.println("04: " + _efetivos.size());
		
		System.out.println("05: " +qcp.getId());
		

		for (EfetivoPrevisto clonado : _efetivos) {
			
			EfetivoPrevisto clone = (EfetivoPrevisto) EfetivoPrevisto.cloneSerializable(clonado);
			
			clone.setId(null);
			clone.setQcp(qcp);
			
			if (clone.getEspecialidade() != null){
				if (clone.getEspecialidade().getId() == null){
					clone.setEspecialidade(null);
				}
			}
			
			repository.adicionar(clone);
		}
		
		System.out.println("05: " + efetivos.size());
		System.out.println("06: " + _efetivos.size());
	}
}