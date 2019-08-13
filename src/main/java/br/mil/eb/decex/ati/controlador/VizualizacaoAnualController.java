package br.mil.eb.decex.ati.controlador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.mil.eb.decex.ati.enumerado.TelaEnum;
import br.mil.eb.decex.ati.modelo.AlunosCivis;
import br.mil.eb.decex.ati.modelo.AlunosColegioMilitar;
import br.mil.eb.decex.ati.modelo.AlunosMilitarOutrasForcas;
import br.mil.eb.decex.ati.modelo.AlunosMilitaresOMDS;
import br.mil.eb.decex.ati.modelo.EfetivoInstrutorMonitor;
import br.mil.eb.decex.ati.modelo.EfetivoRealizado;
import br.mil.eb.decex.ati.modelo.EnsinoADistancia;
import br.mil.eb.decex.ati.modelo.FundacaoOsorio;
import br.mil.eb.decex.ati.modelo.OrganizacaoMilitar;
import br.mil.eb.decex.ati.modelo.Periodo;
import br.mil.eb.decex.ati.modelo.ProfessorCivil;
import br.mil.eb.decex.ati.modelo.ProfessorMilitar;
import br.mil.eb.decex.ati.modelo.Usuario;
import br.mil.eb.decex.ati.seguranca.Autenticado;
import br.mil.eb.decex.ati.seguranca.PeriodoSelecionado;
import br.mil.eb.decex.ati.vo.EfetivoResumidoVO;
import br.mil.eb.decex.ati.vo.GraficoVO;

/**
 * Realiza o cadastro das OMs Vinculadas de cada OM
 * </p>
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @version 1.0
 */
@ViewScoped
@Named("vizAnualCtr")
public class VizualizacaoAnualController extends BaseController {
//teste
	private static final long serialVersionUID = 1L;

	@Inject 
	@Autenticado
	private Usuario usuario;

	@Inject
	@PeriodoSelecionado
	private Periodo periodo;

	@Inject
	private GraficoVO graficoVO;

	@Inject
	private OrganizacaoMilitar omEscolhida;

	@PostConstruct
	public void init() {
		this.getGraficoVO().iniciarGrafico();
		prepararGraficoEnsinoADistancia();
		prepararGraficoProfessorCivil();
		prepararGraficoProfessorMilitar();
		prepararGraficoInsturtorMoniro();
		prepararGraficoAlunosCivis();
		prepararGraficoAlunosColegioMilitar();
		prepararGraficoAlunosMilitares();
		prepararGraficoAlunosMilitaresOMDS();
//		prepararGraficoAlunosFundacaoOsorio();
		prepararGraficoEfetivoRealizado();
		this.getGraficoVO().ajustarAlturaMaxima();
	
	}

	public void carregarGraficos() {
		
		this.getGraficoVO().iniciarGrafico();
		prepararGraficoEnsinoADistancia(getOmEscolhida());
		prepararGraficoProfessorCivil(getOmEscolhida());
		prepararGraficoProfessorMilitar(getOmEscolhida());
		prepararGraficoInsturtorMoniro(getOmEscolhida());
		prepararGraficoAlunosCivis(getOmEscolhida());
		prepararGraficoAlunosColegioMilitar(getOmEscolhida());
		prepararGraficoAlunosMilitares(getOmEscolhida());
		prepararGraficoAlunosMilitaresOMDS(getOmEscolhida());
//		prepararGraficoAlunosFundacaoOsorio();
		prepararGraficoEfetivoRealizado(getOmEscolhida());
		this.getGraficoVO().ajustarAlturaMaxima();

	}

	public void prepararGraficoEnsinoADistancia(OrganizacaoMilitar om) {
		List<EnsinoADistancia> listaEfetivo;
		listaEfetivo = new ArrayList<EnsinoADistancia>();
		listaEfetivo = repository.encontrarComQueryNomeada(EnsinoADistancia.class, "EnsinoADistancia.VisualizacaoAnual",
				new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.EAD, transformarListaEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoProfessorCivil(OrganizacaoMilitar om) {
		List<ProfessorCivil> listaEfetivo;
		listaEfetivo = new ArrayList<ProfessorCivil>();
		listaEfetivo = repository.encontrarComQueryNomeada(ProfessorCivil.class, "ProfessorCivil.VisualizacaoAnual",
				new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.PROFESSORCIVIL,
					transformarListaProfessorCivilEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoAlunosColegioMilitar(OrganizacaoMilitar om) {
		List<AlunosColegioMilitar> listaEfetivo;
		listaEfetivo = new ArrayList<AlunosColegioMilitar>();
		listaEfetivo = repository.encontrarComQueryNomeada(AlunosColegioMilitar.class,
				"AlunosColegioMilitar.VisualizacaoAnual", new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSCOLEGIOMILITAR,
					transformarListaAlunosColegioMilitarEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoProfessorMilitar(OrganizacaoMilitar om) {
		List<ProfessorMilitar> listaEfetivo;
		listaEfetivo = new ArrayList<ProfessorMilitar>();
		listaEfetivo = repository.encontrarComQueryNomeada(ProfessorMilitar.class, "ProfessorMilitar.VisualizacaoAnual",
				new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.PROFESSORMILITAR,
					transformarListaProfessorMilitarEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoAlunosCivis(OrganizacaoMilitar om) {
		List<AlunosCivis> listaEfetivo;
		listaEfetivo = new ArrayList<AlunosCivis>();
		listaEfetivo = repository.encontrarComQueryNomeada(AlunosCivis.class, "AlunosCivis.VisualizacaoAnual",
				new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSCIVIS,
					transformarListaAlunosCivisEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoInsturtorMoniro(OrganizacaoMilitar om) {
		List<EfetivoInstrutorMonitor> listaEfetivo;
		listaEfetivo = new ArrayList<EfetivoInstrutorMonitor>();
		listaEfetivo = repository.encontrarComQueryNomeada(EfetivoInstrutorMonitor.class,
				"EfetivoInstrutorMonitor.VisualizacaoAnual", new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.INTRUTORMONITORES,
					transformarListaInstrutorMuniotorEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoAlunosMilitares(OrganizacaoMilitar om) {

		List<AlunosMilitarOutrasForcas> listaEfetivo;

		listaEfetivo = new ArrayList<AlunosMilitarOutrasForcas>();

		listaEfetivo = repository.encontrarComQueryNomeada(AlunosMilitarOutrasForcas.class,
				"AlunosMilitarOutrasForcas.VisualizacaoAnual", new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSMILITARES,
					transformarListaAlunosMinitaresEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoAlunosMilitaresOMDS(OrganizacaoMilitar om) {

		List<AlunosMilitaresOMDS> listaEfetivo;

		listaEfetivo = new ArrayList<AlunosMilitaresOMDS>();

		listaEfetivo = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class,
				"alunosMilitaresOMDS.VisualizacaoAnual", new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSMILITARESOMDS,
					transformarListaAlunosMilitaresOMDSEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoEnsinoADistancia() {
		List<EnsinoADistancia> listaEfetivo;
		listaEfetivo = new ArrayList<EnsinoADistancia>();
		listaEfetivo = repository.encontrarComQueryNomeada(EnsinoADistancia.class, "EnsinoADistancia.VisualizacaoAnual",
				new Object[] { "om", this.usuario.getOrganizacaoMilitar() });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.EAD, transformarListaEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoProfessorCivil() {
		List<ProfessorCivil> listaEfetivo;
		listaEfetivo = new ArrayList<ProfessorCivil>();
		listaEfetivo = repository.encontrarComQueryNomeada(ProfessorCivil.class, "ProfessorCivil.VisualizacaoAnual",
				new Object[] { "om", this.usuario.getOrganizacaoMilitar() });
		this.getGraficoVO().preencherGrafico(TelaEnum.PROFESSORCIVIL,
				transformarListaProfessorCivilEmEfetivoResumidoVO(listaEfetivo));
	}

	public void prepararGraficoAlunosColegioMilitar() {
		List<AlunosColegioMilitar> listaEfetivo;
		listaEfetivo = new ArrayList<AlunosColegioMilitar>();
		listaEfetivo = repository.encontrarComQueryNomeada(AlunosColegioMilitar.class,
				"AlunosColegioMilitar.VisualizacaoAnual", new Object[] { "om", this.usuario.getOrganizacaoMilitar() });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSCOLEGIOMILITAR,
					transformarListaAlunosColegioMilitarEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoProfessorMilitar() {
		List<ProfessorMilitar> listaEfetivo;
		listaEfetivo = new ArrayList<ProfessorMilitar>();
		listaEfetivo = repository.encontrarComQueryNomeada(ProfessorMilitar.class, "ProfessorMilitar.VisualizacaoAnual",
				new Object[] { "om", this.usuario.getOrganizacaoMilitar() });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.PROFESSORMILITAR,
					transformarListaProfessorMilitarEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoAlunosCivis() {
		List<AlunosCivis> listaEfetivo;
		listaEfetivo = new ArrayList<AlunosCivis>();
		listaEfetivo = repository.encontrarComQueryNomeada(AlunosCivis.class, "AlunosCivis.VisualizacaoAnual",
				new Object[] { "om", this.usuario.getOrganizacaoMilitar() });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSCIVIS,
					transformarListaAlunosCivisEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoInsturtorMoniro() {
		List<EfetivoInstrutorMonitor> listaEfetivo;
		listaEfetivo = new ArrayList<EfetivoInstrutorMonitor>();
		listaEfetivo = repository.encontrarComQueryNomeada(EfetivoInstrutorMonitor.class, "EfetivoInstrutorMonitor.VisualizacaoAnual",
				new Object[] { "om", this.usuario.getOrganizacaoMilitar() });
		this.getGraficoVO().preencherGrafico(TelaEnum.INTRUTORMONITORES,
				transformarListaInstrutorMuniotorEmEfetivoResumidoVO(listaEfetivo));
	}

	public void prepararGraficoAlunosMilitares() {
		List<AlunosMilitarOutrasForcas> listaEfetivo;
		listaEfetivo = new ArrayList<AlunosMilitarOutrasForcas>();
		listaEfetivo = repository.encontrarComQueryNomeada(AlunosMilitarOutrasForcas.class, "AlunosMilitarOutrasForcas.VisualizacaoAnual",
				new Object[] { "om", this.usuario.getOrganizacaoMilitar() });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSMILITARES,
					transformarListaAlunosMinitaresEmEfetivoResumidoVO(listaEfetivo));
		}
	}

	public void prepararGraficoAlunosMilitaresOMDS() {
		List<AlunosMilitaresOMDS> listaEfetivo;
		listaEfetivo = new ArrayList<AlunosMilitaresOMDS>();
		listaEfetivo = repository.encontrarComQueryNomeada(AlunosMilitaresOMDS.class, "alunosMilitaresOMDS.VisualizacaoAnual",
				new Object[] { "om", this.usuario.getOrganizacaoMilitar() });
		if (!listaEfetivo.isEmpty()) {
			this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSMILITARESOMDS,
					transformarListaAlunosMilitaresOMDSEmEfetivoResumidoVO(listaEfetivo));
		}
	}
	
	/**
	 * Fundaçao osorio
	 */
	public void prepararGraficoAlunosFundacaoOsorio() {
		List<FundacaoOsorio> listaEfetivo;
		listaEfetivo = new ArrayList<FundacaoOsorio>();
		listaEfetivo = repository.encontrarComQueryNomeada(FundacaoOsorio.class, "fundacaoOsorio.VisualizacaoAnual");
		if (!listaEfetivo.isEmpty()) {
		this.getGraficoVO().preencherGrafico(TelaEnum.ALUNOSFUNDACAOOSORIO,
				transformarListaAlunosFundacaoOsorioEmEfetivoResumidoVO(listaEfetivo));}
	}

	private List<EfetivoResumidoVO> transformarListaAlunosFundacaoOsorioEmEfetivoResumidoVO( List<FundacaoOsorio> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		for (FundacaoOsorio le : listaEfetivo) {
			int efetivo = le.getFeminino()+ le.getMasculino();
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);
			} else {
				listaEfetivoResumido.add(er);
			}
		}
		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}
	
	/**
	 * Efetivo Exitente
	 */
	public void prepararGraficoEfetivoRealizado() {
		List<EfetivoRealizado> listaEfetivo;
		listaEfetivo = new ArrayList<EfetivoRealizado>();
		listaEfetivo = repository.encontrarComQueryNomeada(EfetivoRealizado.class, "EfetivoRealizado.VisualizacaoAnual",
				new Object[] { "om",  this.usuario.getOrganizacaoMilitar() });
		if (!listaEfetivo.isEmpty()) {
		this.getGraficoVO().preencherGrafico(TelaEnum.EFETIVOEXISTENTE,transformarListaEfetivoRealizadoEmEfetivoResumidoVO(listaEfetivo));}
	}
	
	
	public void prepararGraficoEfetivoRealizado(OrganizacaoMilitar om) {
		List<EfetivoRealizado> listaEfetivo;
		listaEfetivo = new ArrayList<EfetivoRealizado>();
		listaEfetivo = repository.encontrarComQueryNomeada(EfetivoRealizado.class, "EfetivoRealizado.VisualizacaoAnual",
				new Object[] { "om", om });
		if (!listaEfetivo.isEmpty()) {
		this.getGraficoVO().preencherGrafico(TelaEnum.EFETIVOEXISTENTE,transformarListaEfetivoRealizadoEmEfetivoResumidoVO(listaEfetivo));}
	}

	private List<EfetivoResumidoVO> transformarListaEfetivoRealizadoEmEfetivoResumidoVO( List<EfetivoRealizado> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		for (EfetivoRealizado le : listaEfetivo) {
			int efetivo = le.getFeminino() + le.getMasculino();
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);
			} else {
				listaEfetivoResumido.add(er);
			}
		}
		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}
	
	/**
	 * Colegio Militar
	 */
	private List<EfetivoResumidoVO> transformarListaAlunosColegioMilitarEmEfetivoResumidoVO(
			List<AlunosColegioMilitar> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		// Map<Periodo, Integer> somatorio = new HashMap<Periodo, Integer>();
		for (AlunosColegioMilitar le : listaEfetivo) {
			int efetivo = le.getFeminino() + le.getMasculino() ;
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);

			} else {
				listaEfetivoResumido.add(er);
			}
		}

		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}
	
	/**
	 * professor militar
	 */

	private List<EfetivoResumidoVO> transformarListaProfessorMilitarEmEfetivoResumidoVO(
			List<ProfessorMilitar> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		for (ProfessorMilitar le : listaEfetivo) {
			int efetivo = le.getFeminino() + le.getMasculino();
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);

			} else {
				listaEfetivoResumido.add(er);
			}
		}
		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}

	/**
	 * alunos Civis
	 */

	private List<EfetivoResumidoVO> transformarListaAlunosCivisEmEfetivoResumidoVO(List<AlunosCivis> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		for (AlunosCivis le : listaEfetivo) {
			int efetivo = le.getFeminino() + le.getMasculino();
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);

			} else {
				listaEfetivoResumido.add(er);
			}
		}
		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}

	/**
	 * Instrutor Monitor
	 */

	private List<EfetivoResumidoVO> transformarListaInstrutorMuniotorEmEfetivoResumidoVO(
			List<EfetivoInstrutorMonitor> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		// Map<Periodo, Integer> somatorio = new HashMap<Periodo, Integer>();
		for (EfetivoInstrutorMonitor le : listaEfetivo) {
			int efetivo = le.getFeminino() + le.getMasculino();
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);

			} else {
				listaEfetivoResumido.add(er);
			}
		}

		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}

	/**
	 * alunos militares
	 */

	private List<EfetivoResumidoVO> transformarListaAlunosMilitaresOMDSEmEfetivoResumidoVO(
			List<AlunosMilitaresOMDS> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;

		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();

		for (AlunosMilitaresOMDS le : listaEfetivo) {
			int efetivo = le.getFeminino() + le.getMasculino();
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);

			} else {
				listaEfetivoResumido.add(er);
			}
		}

		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}
	
	/**
	 * alunos militares outras forças
	 */
	private List<EfetivoResumidoVO> transformarListaAlunosMinitaresEmEfetivoResumidoVO(
			List<AlunosMilitarOutrasForcas> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		// Map<Periodo, Integer> somatorio = new HashMap<Periodo, Integer>();
		for (AlunosMilitarOutrasForcas le : listaEfetivo) {
			int efetivo = le.getFeminino() + le.getMasculino();
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);

			} else {
				listaEfetivoResumido.add(er);
			}
		}

		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}
	
	/**
	 * Professor civil
	 */
	private List<EfetivoResumidoVO> transformarListaProfessorCivilEmEfetivoResumidoVO(
			List<ProfessorCivil> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		for (ProfessorCivil le : listaEfetivo) {
			int efetivo = le.getFeminino() + le.getMasculino();
			EfetivoResumidoVO er = new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), efetivo);
			if (listaEfetivoResumido.contains(er)) {
				int index = listaEfetivoResumido.indexOf(er);
				EfetivoResumidoVO er2 = listaEfetivoResumido.get(index);
				er2.setEfetivo(er2.getEfetivo() + efetivo);

			} else {
				listaEfetivoResumido.add(er);
			}
		}

		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}
	
	/**
	 * ensino a distancia
	 */
	private List<EfetivoResumidoVO> transformarListaEmEfetivoResumidoVO(List<EnsinoADistancia> listaEfetivo) {
		List<EfetivoResumidoVO> listaEfetivoResumido;
		listaEfetivoResumido = new ArrayList<EfetivoResumidoVO>();
		for (EnsinoADistancia le : listaEfetivo) {
			listaEfetivoResumido.add(new EfetivoResumidoVO(le.getPeriodo().getMes().getValue(),
					le.getPeriodo().getAno().intValue(), le.getAlunoCM() + le.getAlunoOf() + le.getAlunoPr()));
		}
		Collections.sort(listaEfetivoResumido);
		return listaEfetivoResumido;
	}

	// gets e sets
	public GraficoVO getGraficoVO() {
		return graficoVO;
	}

	public void setGraficoVO(GraficoVO graficoVO) {
		this.graficoVO = graficoVO;
	}

	public OrganizacaoMilitar getOmEscolhida() {
		return omEscolhida;
	}

	public void setOmEscolhida(OrganizacaoMilitar omEscolhida) {
		this.omEscolhida = omEscolhida;
	}

}
