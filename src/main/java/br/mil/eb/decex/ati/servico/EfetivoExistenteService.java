package br.mil.eb.decex.ati.servico;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import br.mil.eb.decex.ati.controlador.PeriodoController;
import br.mil.eb.decex.ati.jpa.Repository;
import br.mil.eb.decex.ati.modelo.AlunoOmVinculada;
import br.mil.eb.decex.ati.modelo.AlunosColegioMilitar;
import br.mil.eb.decex.ati.modelo.AlunosMilitarOutrasForcas;
import br.mil.eb.decex.ati.modelo.AlunosMilitaresOMDS;
import br.mil.eb.decex.ati.modelo.EfetivoInstrutorMonitor;
import br.mil.eb.decex.ati.modelo.EfetivoRealizado;
import br.mil.eb.decex.ati.modelo.EnsinoADistancia;
import br.mil.eb.decex.ati.modelo.FundacaoOsorio;
import br.mil.eb.decex.ati.modelo.MilitaresNacoesAmigas;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.ProfessorCivil;
import br.mil.eb.decex.ati.modelo.ProfessorMilitar;
import br.mil.eb.decex.ati.modelo.RegistroAlteracaoEfetivoExistente;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;

/**
 * Regra para manipulação de efetivos existente.
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */
@Stateless
public class EfetivoExistenteService implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private Repository repository;
	
	@Inject
    @Autenticado
    private Usuario usuario;
	
	@Inject
	@PeriodoSelecionado
	private Periodo periodo;
	
	@Inject
	private PeriodoController periodoController;
	
	private List<EfetivoRealizado> listaEfetivoExistente; 
	
	/**
	 * Verifica se já existe registro no efetivo Existente e nas telas associadas
	 * @return boolean
	 */
	public boolean verificaSePrimeiroAcessoNoPeriodo() {
		List<RegistroAlteracaoEfetivoExistente> listaRegistros;
		boolean primeiroAcesso = false;
		listaRegistros = repository.encontrarComQueryNomeada(RegistroAlteracaoEfetivoExistente.class,
				"Pegunta.listarPorPeriodo", new Object[] { "periodo", periodo },
				new Object[] { "organizacao", usuario.getOrganizacaoMilitar() });
		if (listaRegistros.isEmpty()) {
			primeiroAcesso = true;
		}
		return primeiroAcesso;
	}
	@Deprecated
	public Boolean primeiroAcesso() {
		Boolean verificarTelasParaClonagem = new Boolean(true);
		
		listaEfetivoExistente = repository.encontrarComQueryNomeada(EfetivoRealizado.class, "EfetivoRealizado.linhaConfiguracaoPorPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"om", usuario.getOrganizacaoMilitar()});
		
		if (listaEfetivoExistente.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<AlunosColegioMilitar> efetivosCM = repository.encontrarComQueryNomeada(AlunosColegioMilitar.class, "alunosColegioMilitar.listarPorPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosCM.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<AlunosMilitaresOMDS> efetivosOMDS = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class, "alunosMilitaresOMDS.listarPorPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosOMDS.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<AlunosMilitarOutrasForcas> efetivosOF = repository.encontrarComQueryNomeada(AlunosMilitarOutrasForcas.class, "alunosMilitarOutrasForcas.listarPorPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosOF.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<EfetivoInstrutorMonitor> efetivosIM = repository.encontrarComQueryNomeada(EfetivoInstrutorMonitor.class, "efetivoInstrutorMonitor.listarPorPeriodoEOm",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosIM.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<EnsinoADistancia> efetivosEAD = repository.encontrarComQueryNomeada(EnsinoADistancia.class, "ensinoADistancia.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosEAD.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<MilitaresNacoesAmigas> efetivosNA = repository.encontrarComQueryNomeada(MilitaresNacoesAmigas.class, "militaresNacoesAmigas.listarPorOM",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosNA.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<AlunoOmVinculada> efetivosOMV = repository.encontrarComQueryNomeada(AlunoOmVinculada.class, "alunoOmVinculada.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosOMV.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<ProfessorCivil> efetivosPC = repository.encontrarComQueryNomeada(ProfessorCivil.class, "professorCivil.listarPorOMEPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosPC.size() > 0)
			verificarTelasParaClonagem = false;
		
		List<ProfessorMilitar> efetivosPM = repository.encontrarComQueryNomeada(ProfessorMilitar.class, "professorMilitar.listarPorPeriodo",
				new Object[]{"periodo", periodo},
				new Object[]{"organizacao", usuario.getOrganizacaoMilitar()});
		
		if (efetivosPM.size() > 0)
			verificarTelasParaClonagem = false;
		
		//Se todos as telas complementares estiverem vazias a clonagem será efetuada.
		return verificarTelasParaClonagem;
	}
	
	/**
	 * Clona um efetivo existente de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos e
	 * salva a escolha do usuário no banco de dados
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos existente a serem clonados
	 * @param organizacoesMilitares lista das organizações vinculadas a Om do usuário
	 */
	public void preparaClonagem(RegistroAlteracaoEfetivoExistente valor, List<EfetivoRealizado> efetivos, List<OrganizacaoMilitar> organizacoesMilitares) {
		persistirInformacaoSeHouveAlteracaoNoMesCorrente(valor);
		//clonar(valor, efetivos, organizacoesMilitares);
	}

	@SuppressWarnings("unused")
	private void clonar(RegistroAlteracaoEfetivoExistente valor, List<EfetivoRealizado> efetivos,
			List<OrganizacaoMilitar> organizacoesMilitares) {
		
		
		/*for (EfetivoRealizado clonado : efetivos) {

			EfetivoRealizado clone = (EfetivoRealizado) EfetivoRealizado.cloneSerializable(clonado);
			clone.setId(null);
			clone.setPeriodo(periodo);

			repository.adicionarOuMudar(clone);
		}*/

		

		/*for (OrganizacaoMilitar om : organizacoesMilitares) {
			clonarAlunosColegioMilitar(om);
			clonarAlunosMilitaresOMDS(om);
			clonarAlunosMilitaresOutrasForcas(om);
			clonarEfetivoInstrutorMonitor(om);
			clonarEnsinoADistancia(om);
			clonarMilitaresNacoesAmigas(om);
			clonarOmVinculadas(om);
			clonarProfessorCivil(om);
			clonarProfessorMilitar(om);

			if (om.getSigla().toString().equals("DECEX OM"))
				clonarFundacaoOsorio();
		}*/
	}

	private void persistirInformacaoSeHouveAlteracaoNoMesCorrente(RegistroAlteracaoEfetivoExistente valor) {
		repository.adicionar(valor);
	}
	
	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	@SuppressWarnings("unused")
	private void clonarAlunosColegioMilitar(OrganizacaoMilitar om) {
		List<AlunosColegioMilitar> efetivos = repository.encontrarComQueryNomeada(AlunosColegioMilitar.class, "alunosColegioMilitar.listarPorPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (AlunosColegioMilitar clonado : efetivos) {
				
				AlunosColegioMilitar clone = (AlunosColegioMilitar) AlunosColegioMilitar.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	}
	
	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	@SuppressWarnings("unused")
	private void clonarAlunosMilitaresOMDS(OrganizacaoMilitar om) {
		List<AlunosMilitaresOMDS> efetivos = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class, "alunosMilitaresOMDS.listarPorPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (AlunosMilitaresOMDS clonado : efetivos) {
				
				AlunosMilitaresOMDS clone = (AlunosMilitaresOMDS) AlunosMilitaresOMDS.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	}

	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	@SuppressWarnings("unused")
	private void clonarAlunosMilitaresOutrasForcas(OrganizacaoMilitar om) {
		List<AlunosMilitarOutrasForcas> efetivos = repository.encontrarComQueryNomeada(AlunosMilitarOutrasForcas.class, "alunosMilitarOutrasForcas.listarPorPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (AlunosMilitarOutrasForcas clonado : efetivos) {
				
				AlunosMilitarOutrasForcas clone = (AlunosMilitarOutrasForcas) AlunosMilitarOutrasForcas.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	}

	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	@SuppressWarnings("unused")
	private void clonarEfetivoInstrutorMonitor(OrganizacaoMilitar om) {
		List<EfetivoInstrutorMonitor> efetivos = repository.encontrarComQueryNomeada(EfetivoInstrutorMonitor.class, "efetivoInstrutorMonitor.listarPorPeriodoEOm",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (EfetivoInstrutorMonitor clonado : efetivos) {
				
				EfetivoInstrutorMonitor clone = (EfetivoInstrutorMonitor) EfetivoInstrutorMonitor.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	}

	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	@SuppressWarnings("unused")
	private void clonarEnsinoADistancia(OrganizacaoMilitar om) {
		List<EnsinoADistancia> efetivos = repository.encontrarComQueryNomeada(EnsinoADistancia.class, "ensinoADistancia.listarPorOMEPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (EnsinoADistancia clonado : efetivos) {
				
				EnsinoADistancia clone = (EnsinoADistancia) EnsinoADistancia.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	}

	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	@SuppressWarnings("unused")
	private void clonarMilitaresNacoesAmigas(OrganizacaoMilitar om) {
		List<MilitaresNacoesAmigas> efetivos = repository.encontrarComQueryNomeada(MilitaresNacoesAmigas.class, "militaresNacoesAmigas.listarPorOM",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (MilitaresNacoesAmigas clonado : efetivos) {
				
				MilitaresNacoesAmigas clone = (MilitaresNacoesAmigas) MilitaresNacoesAmigas.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	}

	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	@SuppressWarnings("unused")
	private void clonarOmVinculadas(OrganizacaoMilitar om) {
		List<AlunoOmVinculada> efetivos = repository.encontrarComQueryNomeada(AlunoOmVinculada.class, "alunoOmVinculada.listarPorOMEPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (AlunoOmVinculada clonado : efetivos) {
				
				AlunoOmVinculada clone = (AlunoOmVinculada) AlunoOmVinculada.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	}

	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	
	private void clonarProfessorCivil(OrganizacaoMilitar om) {
		List<ProfessorCivil> efetivos = repository.encontrarComQueryNomeada(ProfessorCivil.class, "professorCivil.listarPorOMEPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (ProfessorCivil clonado : efetivos) {
				
				ProfessorCivil clone = (ProfessorCivil) ProfessorCivil.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	}

	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 * @param efetivos lista de efetivos a serem clonados
	 */
	@SuppressWarnings("unused")
	private void clonarProfessorMilitar(OrganizacaoMilitar om) {
		List<ProfessorMilitar> efetivos = repository.encontrarComQueryNomeada(ProfessorMilitar.class, "professorMilitar.listarPorPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()},
				new Object[]{"organizacao", om});
		
		if (efetivos.size() > 0) 
			for (ProfessorMilitar clonado : efetivos) {
				
				ProfessorMilitar clone = (ProfessorMilitar) ProfessorMilitar.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	
	}
	
	/**
	 * Clona o efetivo de acordo com o efetivo definido no Periodo anterior
	 * ativo, setando o Periodo atual para os novos efetivos
	 * 
	 * @param periodo Periodo atual válido que será utilizado nos efetivos que serão clonados
	 */
	@SuppressWarnings("unused")
	private void clonarFundacaoOsorio() {
		List<FundacaoOsorio> efetivos = repository.encontrarComQueryNomeada(FundacaoOsorio.class, "fundacaoOsorio.listarPorPeriodo",
				new Object[]{"periodo", periodoController.getPeriodoAnterior()});
		
		if (efetivos.size() > 0) 
			for (FundacaoOsorio clonado : efetivos) {
				
				FundacaoOsorio clone = (FundacaoOsorio) FundacaoOsorio.cloneSerializable(clonado);
				clone.setId(null);
				clone.setPeriodo(periodo);
				
				repository.adicionarOuMudar(clone);
			}
	
	}

	public void preparaClonagemTodos(List<OrganizacaoMilitar> organizacoesMilitares) {

		for (OrganizacaoMilitar om : organizacoesMilitares) {
			clonarEfetivoExistente(om);
			clonarAlunosColegioMilitar(om);
			clonarAlunosMilitaresOMDS(om);
			clonarAlunosMilitaresOutrasForcas(om);
			clonarEfetivoInstrutorMonitor(om);
			clonarEnsinoADistancia(om);
			clonarMilitaresNacoesAmigas(om);
			clonarOmVinculadas(om);
			clonarProfessorCivil(om);
			clonarProfessorMilitar(om);

			if (om.getSigla().toString().equals("DECEX OM"))
				clonarFundacaoOsorio();
		}

	}

	private void clonarEfetivoExistente(OrganizacaoMilitar om) {
		List<EfetivoRealizado> efetivosExistentes = buscarEfetivoExistentePorOM(om); 
		for (EfetivoRealizado clonado : efetivosExistentes) {
			EfetivoRealizado clone = (EfetivoRealizado) EfetivoRealizado.cloneSerializable(clonado);
			clone.setId(null);
			clone.setPeriodo(periodo);
			repository.adicionarOuMudar(clone);
		}
	}

	private List<EfetivoRealizado> buscarEfetivoExistentePorOM(OrganizacaoMilitar om) {
		return listaEfetivoExistente = repository.encontrarComQueryNomeada(EfetivoRealizado.class,
				"EfetivoRealizado.linhaConfiguracaoPorPeriodo", new Object[] { "periodo",  periodoController.getPeriodoAnterior()},
				new Object[] { "om", om.getId() });
	}

}