package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Base para lançamento do relatório geral dos efetivos.
 * 
 * @author Jonathan Philipe Amaral <b>Crespo</b> - 3º SGT
 * @version 1.0
 */
@Entity
@Table(name="relatorioGeral")
public class RelatorioGeral  extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="RELATORIOGERAL_ID_GENERATOR", sequenceName="RELATORIOGERAL_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="RELATORIOGERAL_ID_GENERATOR")		
	private Long id;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="periodo_id")
	private Periodo periodo;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="organizacao_id")
	private OrganizacaoMilitar organizacaoMilitar;
	
	@ManyToOne(optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name="omEnquadrante")
	private OrganizacaoMilitar organizacao_enquadrante;
	
	@ManyToOne(optional=true, fetch=FetchType.LAZY)
	@JoinColumn(name="qcp_id")
	private QCP qcp;
	
	@Column
	private boolean isPrevisto;
	
	@Column	
	private Integer genExe_combatente;
	
	@Column
	private Integer genDiv_combatente;
	
	@Column
	private Integer genBri_combatente;
	
	@Column
	private Integer cel_belica_quema_masc;
	
	@Column
	private Integer cel_belica_quema_fem;
	
	@Column
	private Integer cel_belica_qsg_masc;
	
	@Column
	private Integer cel_belica_qsg_fem;
	
	@Column
	private Integer cel_belica_qfe_masc;
	
	@Column
	private Integer cel_belica_qfe_fem;
	
	@Column
	private Integer cel_saude_quema_masc;
	
	@Column
	private Integer cel_saude_quema_fem;
	
	@Column
	private Integer cel_saude_qsg_masc;
	
	@Column
	private Integer cel_saude_qsg_fem;
	
	@Column
	private Integer cel_cieTec_quema_masc;
	
	@Column
	private Integer cel_cieTec_quema_fem;
	
	@Column
	private Integer cel_cieTec_qsg_masc;
	
	@Column
	private Integer cel_cieTec_qsg_fem;
	
	@Column
	private Integer cel_compl_qco_masc;
	
	@Column
	private Integer cel_compl_qco_fem;
	
	@Column
	private Integer cel_qcm_qsg_masc;
	
	@Column
	private Integer cel_qcm_qsg_fem;
	
	@Column
	private Integer tCel_belica_quema_masc;
	
	@Column
	private Integer tCel_belica_quema_fem;
	
	@Column
	private Integer tCel_belica_qsg_masc;
	
	@Column
	private Integer tCel_belica_qsg_fem;
	
	@Column
	private Integer tCel_belica_qfe_masc;
	
	@Column
	private Integer tCel_belica_qfe_fem;
	
	@Column
	private Integer tCel_saude_quema_masc;
	
	@Column
	private Integer tCel_saude_quema_fem;
	
	@Column
	private Integer tCel_saude_qsg_masc;
	
	@Column
	private Integer tCel_saude_qsg_fem;
	
	@Column
	private Integer tCel_cieTec_quema_masc;
	
	@Column
	private Integer tCel_cieTec_quema_fem;
	
	@Column
	private Integer tCel_cieTec_qsg_masc;
	
	@Column
	private Integer tCel_cieTec_qsg_fem;
	
	@Column
	private Integer tCel_compl_qco_masc;
	
	@Column
	private Integer tCel_compl_qco_fem;
	
	@Column
	private Integer tCel_qcm_qsg_masc;
	
	@Column
	private Integer tCel_qcm_qsg_fem;
	
	@Column
	private Integer maj_belica_quema_masc;
	
	@Column
	private Integer maj_belica_quema_fem;
	
	@Column
	private Integer maj_belica_qsg_masc;
	
	@Column
	private Integer maj_belica_qsg_fem;
	
	@Column
	private Integer maj_belica_qfe_masc;
	
	@Column
	private Integer maj_belica_qfe_fem;
	
	@Column
	private Integer maj_saude_quema_masc;
	
	@Column
	private Integer maj_saude_quema_fem;
	
	@Column
	private Integer maj_saude_qsg_masc;
	
	@Column
	private Integer maj_saude_qsg_fem;
	
	@Column
	private Integer maj_cieTec_quema_masc;
	
	@Column
	private Integer maj_cieTec_quema_fem;
	
	@Column
	private Integer maj_cieTec_qsg_masc;
	
	@Column
	private Integer maj_cieTec_qsg_fem;
	
	@Column
	private Integer maj_compl_qco_masc;
	
	@Column
	private Integer maj_compl_qco_fem;
	
	@Column
	private Integer maj_qcm_qsg_masc;
	
	@Column
	private Integer maj_qcm_qsg_fem;
	
	@Column
	private Integer cap_belica_qsg_masc;
	
	@Column
	private Integer cap_belica_qsg_fem;
	
	@Column
	private Integer cap_saude_qsg_masc;
	
	@Column
	private Integer cap_saude_qsg_fem;
	
	@Column
	private Integer cap_cieTec_qsg_masc;
	
	@Column
	private Integer cap_cieTec_qsg_fem;
	
	@Column
	private Integer cap_compl_qco_masc;
	
	@Column
	private Integer cap_compl_qco_fem;
	
	@Column
	private Integer cap_praca_qao_masc;
	
	@Column
	private Integer cap_praca_qao_fem;
	
	@Column
	private Integer cap_qcm_qsg_masc;
	
	@Column
	private Integer cap_qcm_qsg_fem;
	
	@Column
	private Integer ten1_belica_qsg_masc;
	
	@Column
	private Integer ten1_belica_qsg_fem;
	
	@Column
	private Integer ten1_belica_temp_masc;
	
	@Column
	private Integer ten1_belica_temp_fem;
	
	@Column
	private Integer ten1_saude_qsg_masc;
	
	@Column
	private Integer ten1_saude_qsg_fem;
	
	@Column
	private Integer ten1_saude_temp_masc;
	
	@Column
	private Integer ten1_saude_temp_fem;
	
	@Column
	private Integer ten1_cieTec_qsg_masc;
	
	@Column
	private Integer ten1_cieTec_qsg_fem;
	
	@Column
	private Integer ten1_cieTec_temp_masc;
	
	@Column
	private Integer ten1_cieTec_temp_fem;
	
	@Column
	private Integer ten1_compl_qco_masc;
	
	@Column
	private Integer ten1_compl_qco_fem;
	
	@Column
	private Integer ten1_compl_temp_masc;
	
	@Column
	private Integer ten1_compl_temp_fem;
	
	@Column
	private Integer ten1_praca_qao_masc;
	
	@Column
	private Integer ten1_praca_qao_fem;
	
	@Column
	private Integer ten1_qcm_qsg_masc;
	
	@Column
	private Integer ten1_qcm_qsg_fem;
	
	@Column
	private Integer ten1_qcm_temp_masc;
	
	@Column
	private Integer ten1_qcm_temp_fem;
	
	@Column
	private Integer ten2_belica_qsg_masc;
	
	@Column
	private Integer ten2_belica_qsg_fem;
	
	@Column
	private Integer ten2_belica_temp_masc;
	
	@Column
	private Integer ten2_belica_temp_fem;
	
	@Column
	private Integer ten2_saude_qsg_masc;
	
	@Column
	private Integer ten2_saude_qsg_fem;
	
	@Column
	private Integer ten2_saude_temp_masc;
	
	@Column
	private Integer ten2_saude_temp_fem;
	
	@Column
	private Integer ten2_cieTec_qsg_masc;
	
	@Column
	private Integer ten2_cieTec_qsg_fem;
	
	@Column
	private Integer ten2_cieTec_temp_masc;
	
	@Column
	private Integer ten2_cieTec_temp_fem;
	
	@Column
	private Integer ten2_compl_temp_masc;
	
	@Column
	private Integer ten2_compl_temp_fem;
	
	@Column
	private Integer ten2_praca_qao_masc;
	
	@Column
	private Integer ten2_qcm_qsg_masc;
	
	@Column
	private Integer ten2_qcm_temp_masc;
	
	@Column
	private Integer asp_belica_qsg_masc;
	
	@Column
	private Integer asp_belica_qsg_fem;
	
	@Column
	private Integer asp_belica_temp_masc;
	
	@Column
	private Integer asp_belica_temp_fem;
	
	@Column
	private Integer asp_saude_temp_masc;
	
	@Column
	private Integer asp_saude_temp_fem;
	
	@Column
	private Integer asp_cieTec_temp_masc;
	
	@Column
	private Integer asp_cieTec_temp_fem;
	
	@Column
	private Integer asp_qcm_temp_masc;
	
	@Column
	private Integer asp_compl_temp_masc;
	
	@Column
	private Integer asp_compl_temp_fem;
	
	@Column
	private Integer st_masc;
	
	@Column
	private Integer st_fem;
	
	@Column
	private Integer sgt1_masc;
	
	@Column
	private Integer sgt1_fem;
	
	@Column
	private Integer sgt2_carr_masc;
	
	@Column
	private Integer sgt2_carr_fem;
	
	@Column
	private Integer sgt2_qe_masc;
	
	@Column
	private Integer sgt3_carr_masc;
	
	@Column
	private Integer sgt3_carr_fem;
	
	@Column
	private Integer sgt3_temp_masc;
	
	@Column
	private Integer sgt3_temp_fem;
	
	@Column
	private Integer sgt3_qe_masc;
	
	@Column
	private Integer tm;
	
	@Column
	private Integer t1;
	
	@Column
	private Integer t2;
	
	@Column
	private Integer cb_ev;
	
	@Column
	private Integer cb_ep;
	
	@Column
	private Integer cb_et_fem;
	
	@Column
	private Integer cb_et;
	
	@Column
	private Integer sd_nq;
	
	@Column
	private Integer sd_ev;
	
	@Column
	private Integer sd_ep;
	
	@Column
	private Integer pttc_of;
	
	@Column
	private Integer pttc_of_fem;
	
	@Column
	private Integer pttc_pr;
	
	@Column
	private Integer pttc_pr_fem;
	
	@Column
	private Integer servCiv;
	
	@Column
	private Integer servCiv_fem;
	
	@Column
	private Integer profCiv;
	
	@Column
	private Integer profCiv_fem;
	
	@Column
	private Integer aluno_masc;
	
	@Column
	private Integer aluno_fem;
	
	@Column
	private Integer seq_registro; 
	
	/*@Column
	private Integer qcp;
	
	public Integer getQcp() {
		return qcp;
	}
	
	public void setQcp(Integer qcp) {
		this.qcp = qcp;
	}*/
	/**
	 * Identificador de tabela. Código sequencial
	 * @return chave primária da obrigação
	 */		
	@Override
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * Período de lançamento do efetivo do relatório. Cada efetivo é lançado dentro de um período.
	 * @see br.mil.eb.decex.ati.modelo.Periodo
	 * @return {@link Periodo}
	 */
	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}
	
	/**
	 * Organização militar que possui o efetivo do relatório
	 * @see br.mil.eb.decex.ati.modelo.OrganizacaoMilitar
	 * @return {@link OrganizacaoMilitar}
	 */
	public OrganizacaoMilitar getOrganizacaoMilitar() {
		return organizacaoMilitar;
	}
	public void setOrganizacaoMilitar(OrganizacaoMilitar organizacaoMilitar) {
		this.organizacaoMilitar = organizacaoMilitar;
	}

	
	/**
	 * Flag identificadora do efetivo, se o mesmo é Previsto ou Realizado
	 * @return boolean
	 */
	public boolean isPrevisto() {
		return isPrevisto;
	}
	public void setPrevisto(boolean isPrevisto) {
		this.isPrevisto = isPrevisto;
	}
	
	/**
	 * General de Exército
	 * @return Integer
	 */
	public Integer getGenExe_combatente() {
		return genExe_combatente;
	}
	public void setGenExe_combatente(Integer genExe_combatente) {
		this.genExe_combatente = genExe_combatente;
	}
	
	/**
	 * General de Divisão
	 * @return Integer
	 */
	public Integer getGenDiv_combatente() {
		return genDiv_combatente;
	}
	public void setGenDiv_combatente(Integer genDiv_combatente) {
		this.genDiv_combatente = genDiv_combatente;
	}
	
	/**
	 * General de Brigada
	 * @return Integer
	 */
	public Integer getGenBri_combatente() {
		return genBri_combatente;
	}
	public void setGenBri_combatente(Integer genBri_combatente) {
		this.genBri_combatente = genBri_combatente;
	}
	
	/**
	 * Coronel Bélica Quema masculino
	 * @return Integer
	 */
	public Integer getCel_belica_quema_masc() {
		return cel_belica_quema_masc;
	}
	public void setCel_belica_quema_masc(Integer cel_belica_quema_masc) {
		this.cel_belica_quema_masc = cel_belica_quema_masc;
	}
	
	/**
	 * Coronel Bélica Quema feminino
	 * @return Integer
	 */
	public Integer getCel_belica_quema_fem() {
		return cel_belica_quema_fem;
	}
	public void setCel_belica_quema_fem(Integer cel_belica_quema_fem) {
		this.cel_belica_quema_fem = cel_belica_quema_fem;
	}
	
	/**
	 * Coronel Bélica QSG masculino
	 * @return Integer
	 */
	public Integer getCel_belica_qsg_masc() {
		return cel_belica_qsg_masc;
	}
	public void setCel_belica_qsg_masc(Integer cel_belica_qsg_masc) {
		this.cel_belica_qsg_masc = cel_belica_qsg_masc;
	}
	
	/**
	 * Coronel Bélica QSG feminino
	 * @return Integer
	 */
	public Integer getCel_belica_qsg_fem() {
		return cel_belica_qsg_fem;
	}
	public void setCel_belica_qsg_fem(Integer cel_belica_qsg_fem) {
		this.cel_belica_qsg_fem = cel_belica_qsg_fem;
	}
	
	/**
	 * Coronel Saúde Quema masculino
	 * @return Integer
	 */
	public Integer getCel_saude_quema_masc() {
		return cel_saude_quema_masc;
	}
	public void setCel_saude_quema_masc(Integer cel_saude_quema_masc) {
		this.cel_saude_quema_masc = cel_saude_quema_masc;
	}
	
	/**
	 * Coronel Saúde Quema feminino
	 * @return Integer
	 */
	public Integer getCel_saude_quema_fem() {
		return cel_saude_quema_fem;
	}
	public void setCel_saude_quema_fem(Integer cel_saude_quema_fem) {
		this.cel_saude_quema_fem = cel_saude_quema_fem;
	}
	
	/**
	 * Coronel Saúde QSG masculino
	 * @return Integer
	 */
	public Integer getCel_saude_qsg_masc() {
		return cel_saude_qsg_masc;
	}
	public void setCel_saude_qsg_masc(Integer cel_saude_qsg_masc) {
		this.cel_saude_qsg_masc = cel_saude_qsg_masc;
	}
	
	/**
	 * Coronel Saúde QSG feminino
	 * @return Integer
	 */
	public Integer getCel_saude_qsg_fem() {
		return cel_saude_qsg_fem;
	}
	public void setCel_saude_qsg_fem(Integer cel_saude_qsg_fem) {
		this.cel_saude_qsg_fem = cel_saude_qsg_fem;
	}
	
	/**
	 * Coronel Ciências & Tecnologia Quema masculino
	 * @return Integer
	 */
	public Integer getCel_cieTec_quema_masc() {
		return cel_cieTec_quema_masc;
	}
	public void setCel_cieTec_quema_masc(Integer cel_cieTec_quema_masc) {
		this.cel_cieTec_quema_masc = cel_cieTec_quema_masc;
	}
	
	/**
	 * Coronel Ciências & Tecnologia Quema feminino
	 * @return Integer
	 */
	public Integer getCel_cieTec_quema_fem() {
		return cel_cieTec_quema_fem;
	}
	public void setCel_cieTec_quema_fem(Integer cel_cieTec_quema_fem) {
		this.cel_cieTec_quema_fem = cel_cieTec_quema_fem;
	}
	
	/**
	 * Coronel Ciências & Tecnologia QSG masculino
	 * @return Integer
	 */
	public Integer getCel_cieTec_qsg_masc() {
		return cel_cieTec_qsg_masc;
	}
	public void setCel_cieTec_qsg_masc(Integer cel_cieTec_qsg_masc) {
		this.cel_cieTec_qsg_masc = cel_cieTec_qsg_masc;
	}
	
	/**
	 * Coronel Ciências & Tecnologia QSG masculino
	 * @return Integer
	 */
	public Integer getCel_cieTec_qsg_fem() {
		return cel_cieTec_qsg_fem;
	}
	public void setCel_cieTec_qsg_fem(Integer cel_cieTec_qsg_fem) {
		this.cel_cieTec_qsg_fem = cel_cieTec_qsg_fem;
	}
	
	/**
	 * Coronel Complementar QCO masculino
	 * @return Integer
	 */
	public Integer getCel_compl_qco_masc() {
		return cel_compl_qco_masc;
	}
	public void setCel_compl_qco_masc(Integer cel_compl_qco_masc) {
		this.cel_compl_qco_masc = cel_compl_qco_masc;
	}
	
	/**
	 * Coronel Complementar QCO feminino
	 * @return Integer
	 */
	public Integer getCel_compl_qco_fem() {
		return cel_compl_qco_fem;
	}
	public void setCel_compl_qco_fem(Integer cel_compl_qco_fem) {
		this.cel_compl_qco_fem = cel_compl_qco_fem;
	}
	
	/**
	 * Coronel QCM QSG masculino
	 * @return Integer
	 */
	public Integer getCel_qcm_qsg_masc() {
		return cel_qcm_qsg_masc;
	}
	public void setCel_qcm_qsg_masc(Integer cel_qcm_qsg_masc) {
		this.cel_qcm_qsg_masc = cel_qcm_qsg_masc;
	}
	
	/**
	 * Coronel QCM QSG feminino
	 * @return Integer
	 */
	public Integer getCel_qcm_qsg_fem() {
		return cel_qcm_qsg_fem;
	}
	public void setCel_qcm_qsg_fem(Integer cel_qcm_qsg_fem) {
		this.cel_qcm_qsg_fem = cel_qcm_qsg_fem;
	}
	
	/**
	 * Tenente Coronel Bélica QUEMA masculino
	 * @return Integer
	 */
	public Integer gettCel_belica_quema_masc() {
		return tCel_belica_quema_masc;
	}
	public void settCel_belica_quema_masc(Integer tCel_belica_quema_masc) {
		this.tCel_belica_quema_masc = tCel_belica_quema_masc;
	}
	
	/**
	 * Tenente Coronel Bélica QUEMA feminino
	 * @return Integer
	 */
	public Integer gettCel_belica_quema_fem() {
		return tCel_belica_quema_fem;
	}
	public void settCel_belica_quema_fem(Integer tCel_belica_quema_fem) {
		this.tCel_belica_quema_fem = tCel_belica_quema_fem;
	}
	
	/**
	 * Tenente Coronel Bélica QSG masculino
	 * @return Integer
	 */
	public Integer gettCel_belica_qsg_masc() {
		return tCel_belica_qsg_masc;
	}
	public void settCel_belica_qsg_masc(Integer tCel_belica_qsg_masc) {
		this.tCel_belica_qsg_masc = tCel_belica_qsg_masc;
	}
	
	/**
	 * Tenente Coronel Bélica QSG feminino
	 * @return Integer
	 */
	public Integer gettCel_belica_qsg_fem() {
		return tCel_belica_qsg_fem;
	}
	public void settCel_belica_qsg_fem(Integer tCel_belica_qsg_fem) {
		this.tCel_belica_qsg_fem = tCel_belica_qsg_fem;
	}
	
	/**
	 * Tenente Coronel Saúde QUEMA masculino
	 * @return Integer
	 */
	public Integer gettCel_saude_quema_masc() {
		return tCel_saude_quema_masc;
	}
	public void settCel_saude_quema_masc(Integer tCel_saude_quema_masc) {
		this.tCel_saude_quema_masc = tCel_saude_quema_masc;
	}
	
	/**
	 * Tenente Coronel Saúde QUEMA feminino
	 * @return Integer
	 */
	public Integer gettCel_saude_quema_fem() {
		return tCel_saude_quema_fem;
	}
	public void settCel_saude_quema_fem(Integer tCel_saude_quema_fem) {
		this.tCel_saude_quema_fem = tCel_saude_quema_fem;
	}
	
	/**
	 * Tenente Coronel Saúde QSG masculino
	 * @return Integer
	 */
	public Integer gettCel_saude_qsg_masc() {
		return tCel_saude_qsg_masc;
	}
	public void settCel_saude_qsg_masc(Integer tCel_saude_qsg_masc) {
		this.tCel_saude_qsg_masc = tCel_saude_qsg_masc;
	}
	
	/**
	 * Tenente Coronel Saúde QSG feminino
	 * @return Integer
	 */
	public Integer gettCel_saude_qsg_fem() {
		return tCel_saude_qsg_fem;
	}
	public void settCel_saude_qsg_fem(Integer tCel_saude_qsg_fem) {
		this.tCel_saude_qsg_fem = tCel_saude_qsg_fem;
	}
	
	/**
	 * Tenente Coronel Ciências & Tecnologia QUEMA masculino
	 * @return Integer
	 */
	public Integer gettCel_cieTec_quema_masc() {
		return tCel_cieTec_quema_masc;
	}
	public void settCel_cieTec_quema_masc(Integer tCel_cieTec_quema_masc) {
		this.tCel_cieTec_quema_masc = tCel_cieTec_quema_masc;
	}
	
	/**
	 * Tenente Coronel Ciências & Tecnologia QUEMA feminino
	 * @return Integer
	 */
	public Integer gettCel_cieTec_quema_fem() {
		return tCel_cieTec_quema_fem;
	}
	public void settCel_cieTec_quema_fem(Integer tCel_cieTec_quema_fem) {
		this.tCel_cieTec_quema_fem = tCel_cieTec_quema_fem;
	}
	
	/**
	 * Tenente Coronel Ciências & Tecnologia QSG masculino
	 * @return Integer
	 */
	public Integer gettCel_cieTec_qsg_masc() {
		return tCel_cieTec_qsg_masc;
	}
	public void settCel_cieTec_qsg_masc(Integer tCel_cieTec_qsg_masc) {
		this.tCel_cieTec_qsg_masc = tCel_cieTec_qsg_masc;
	}
	
	/**
	 * Tenente Coronel Ciências & Tecnologia QSG feminino
	 * @return Integer
	 */
	public Integer gettCel_cieTec_qsg_fem() {
		return tCel_cieTec_qsg_fem;
	}
	public void settCel_cieTec_qsg_fem(Integer tCel_cieTec_qsg_fem) {
		this.tCel_cieTec_qsg_fem = tCel_cieTec_qsg_fem;
	}
	
	/**
	 * Tenente Coronel Complementar QCO masculino
	 * @return Integer
	 */
	public Integer gettCel_compl_qco_masc() {
		return tCel_compl_qco_masc;
	}
	public void settCel_compl_qco_masc(Integer tCel_compl_qco_masc) {
		this.tCel_compl_qco_masc = tCel_compl_qco_masc;
	}
	
	/**
	 * Tenente Coronel Complementar QCO feminino
	 * @return Integer
	 */
	public Integer gettCel_compl_qco_fem() {
		return tCel_compl_qco_fem;
	}
	public void settCel_compl_qco_fem(Integer tCel_compl_qco_fem) {
		this.tCel_compl_qco_fem = tCel_compl_qco_fem;
	}
	
	/**
	 * Tenente Coronel QCM QSG masculino
	 * @return Integer
	 */
	public Integer gettCel_qcm_qsg_masc() {
		return tCel_qcm_qsg_masc;
	}
	public void settCel_qcm_qsg_masc(Integer tCel_qcm_qsg_masc) {
		this.tCel_qcm_qsg_masc = tCel_qcm_qsg_masc;
	}
	
	/**
	 * Tenente Coronel QCM QSG feminino
	 * @return Integer
	 */
	public Integer gettCel_qcm_qsg_fem() {
		return tCel_qcm_qsg_fem;
	}
	public void settCel_qcm_qsg_fem(Integer tCel_qcm_qsg_fem) {
		this.tCel_qcm_qsg_fem = tCel_qcm_qsg_fem;
	}
	
	/**
	 * Major Bélica QUEMA masculino
	 * @return Integer
	 */
	public Integer getMaj_belica_quema_masc() {
		return maj_belica_quema_masc;
	}
	public void setMaj_belica_quema_masc(Integer maj_belica_quema_masc) {
		this.maj_belica_quema_masc = maj_belica_quema_masc;
	}
	
	/**
	 * Major Bélica QUEMA feminino
	 * @return Integer
	 */
	public Integer getMaj_belica_quema_fem() {
		return maj_belica_quema_fem;
	}
	public void setMaj_belica_quema_fem(Integer maj_belica_quema_fem) {
		this.maj_belica_quema_fem = maj_belica_quema_fem;
	}
	
	/**
	 * Major Bélica QSG masculino
	 * @return Integer
	 */
	public Integer getMaj_belica_qsg_masc() {
		return maj_belica_qsg_masc;
	}
	public void setMaj_belica_qsg_masc(Integer maj_belica_qsg_masc) {
		this.maj_belica_qsg_masc = maj_belica_qsg_masc;
	}
	
	/**
	 * Major Bélica QSG feminino
	 * @return Integer
	 */
	public Integer getMaj_belica_qsg_fem() {
		return maj_belica_qsg_fem;
	}
	public void setMaj_belica_qsg_fem(Integer maj_belica_qsg_fem) {
		this.maj_belica_qsg_fem = maj_belica_qsg_fem;
	}
	
	/**
	 * Major Saúde QUEMA masculino
	 * @return Integer
	 */
	public Integer getMaj_saude_quema_masc() {
		return maj_saude_quema_masc;
	}
	public void setMaj_saude_quema_masc(Integer maj_saude_quema_masc) {
		this.maj_saude_quema_masc = maj_saude_quema_masc;
	}
	
	/**
	 * Major Saúde QUEMA feminino
	 * @return Integer
	 */
	public Integer getMaj_saude_quema_fem() {
		return maj_saude_quema_fem;
	}
	public void setMaj_saude_quema_fem(Integer maj_saude_quema_fem) {
		this.maj_saude_quema_fem = maj_saude_quema_fem;
	}
	
	/**
	 * Major Saúde QSG masculino
	 * @return Integer
	 */
	public Integer getMaj_saude_qsg_masc() {
		return maj_saude_qsg_masc;
	}
	public void setMaj_saude_qsg_masc(Integer maj_saude_qsg_masc) {
		this.maj_saude_qsg_masc = maj_saude_qsg_masc;
	}
	
	/**
	 * Major Saúde QSG feminino
	 * @return Integer
	 */
	public Integer getMaj_saude_qsg_fem() {
		return maj_saude_qsg_fem;
	}
	public void setMaj_saude_qsg_fem(Integer maj_saude_qsg_fem) {
		this.maj_saude_qsg_fem = maj_saude_qsg_fem;
	}
	
	/**
	 * Major Ciências & Tecnologia QUEMA masculino
	 * @return Integer
	 */
	public Integer getMaj_cieTec_quema_masc() {
		return maj_cieTec_quema_masc;
	}
	public void setMaj_cieTec_quema_masc(Integer maj_cieTec_quema_masc) {
		this.maj_cieTec_quema_masc = maj_cieTec_quema_masc;
	}
	
	/**
	 * Major Ciências & Tecnologia QUEMA feminino
	 * @return Integer
	 */
	public Integer getMaj_cieTec_quema_fem() {
		return maj_cieTec_quema_fem;
	}
	public void setMaj_cieTec_quema_fem(Integer maj_cieTec_quema_fem) {
		this.maj_cieTec_quema_fem = maj_cieTec_quema_fem;
	}
	
	/**
	 * Major Ciências & Tecnologia QSG masculino
	 * @return Integer
	 */
	public Integer getMaj_cieTec_qsg_masc() {
		return maj_cieTec_qsg_masc;
	}
	public void setMaj_cieTec_qsg_masc(Integer maj_cieTec_qsg_masc) {
		this.maj_cieTec_qsg_masc = maj_cieTec_qsg_masc;
	}
	
	/**
	 * Major Ciências & Tecnologia QSG feminino
	 * @return Integer
	 */
	public Integer getMaj_cieTec_qsg_fem() {
		return maj_cieTec_qsg_fem;
	}
	public void setMaj_cieTec_qsg_fem(Integer maj_cieTec_qsg_fem) {
		this.maj_cieTec_qsg_fem = maj_cieTec_qsg_fem;
	}
	
	/**
	 * Major Complementar QCO masculino
	 * @return Integer
	 */
	public Integer getMaj_compl_qco_masc() {
		return maj_compl_qco_masc;
	}
	public void setMaj_compl_qco_masc(Integer maj_compl_qco_masc) {
		this.maj_compl_qco_masc = maj_compl_qco_masc;
	}
	
	/**
	 * Major Complementar QCO feminino
	 * @return Integer
	 */
	public Integer getMaj_compl_qco_fem() {
		return maj_compl_qco_fem;
	}
	public void setMaj_compl_qco_fem(Integer maj_compl_qco_fem) {
		this.maj_compl_qco_fem = maj_compl_qco_fem;
	}
	
	/**
	 * Major QCM QSG masculino
	 * @return Integer
	 */
	public Integer getMaj_qcm_qsg_masc() {
		return maj_qcm_qsg_masc;
	}
	public void setMaj_qcm_qsg_masc(Integer maj_qcm_qsg_masc) {
		this.maj_qcm_qsg_masc = maj_qcm_qsg_masc;
	}
	
	/**
	 * Major QCM QSG feminino
	 * @return Integer
	 */
	public Integer getMaj_qcm_qsg_fem() {
		return maj_qcm_qsg_fem;
	}
	public void setMaj_qcm_qsg_fem(Integer maj_qcm_qsg_fem) {
		this.maj_qcm_qsg_fem = maj_qcm_qsg_fem;
	}
	
	/**
	 * Capitão Bélica QSG masculino
	 * @return Integer
	 */
	public Integer getCap_belica_qsg_masc() {
		return cap_belica_qsg_masc;
	}
	public void setCap_belica_qsg_masc(Integer cap_belica_qsg_masc) {
		this.cap_belica_qsg_masc = cap_belica_qsg_masc;
	}
	
	/**
	 * Capitão Bélica QSG feminino
	 * @return Integer
	 */
	public Integer getCap_belica_qsg_fem() {
		return cap_belica_qsg_fem;
	}
	public void setCap_belica_qsg_fem(Integer cap_belica_qsg_fem) {
		this.cap_belica_qsg_fem = cap_belica_qsg_fem;
	}
	
	/**
	 * Capitão Saúde QSG masculino
	 * @return Integer
	 */
	public Integer getCap_saude_qsg_masc() {
		return cap_saude_qsg_masc;
	}
	public void setCap_saude_qsg_masc(Integer cap_saude_qsg_masc) {
		this.cap_saude_qsg_masc = cap_saude_qsg_masc;
	}
	
	/**
	 * Capitão Saúde QSG feminino
	 * @return Integer
	 */
	public Integer getCap_saude_qsg_fem() {
		return cap_saude_qsg_fem;
	}
	public void setCap_saude_qsg_fem(Integer cap_saude_qsg_fem) {
		this.cap_saude_qsg_fem = cap_saude_qsg_fem;
	}
	
	/**
	 * Capitão Ciências & Tecnologia QSG masculino
	 * @return Integer
	 */
	public Integer getCap_cieTec_qsg_masc() {
		return cap_cieTec_qsg_masc;
	}
	public void setCap_cieTec_qsg_masc(Integer cap_cieTec_qsg_masc) {
		this.cap_cieTec_qsg_masc = cap_cieTec_qsg_masc;
	}
	
	/**
	 * Capitão Ciências & Tecnologia QSG feminino
	 * @return Integer
	 */
	public Integer getCap_cieTec_qsg_fem() {
		return cap_cieTec_qsg_fem;
	}
	public void setCap_cieTec_qsg_fem(Integer cap_cieTec_qsg_fem) {
		this.cap_cieTec_qsg_fem = cap_cieTec_qsg_fem;
	}
	
	/**
	 * Capitão Complementar QCO masculino
	 * @return Integer
	 */
	public Integer getCap_compl_qco_masc() {
		return cap_compl_qco_masc;
	}
	public void setCap_compl_qco_masc(Integer cap_compl_qco_masc) {
		this.cap_compl_qco_masc = cap_compl_qco_masc;
	}
	
	/**
	 * Capitão Complementar QCO feminino
	 * @return Integer
	 */
	public Integer getCap_compl_qco_fem() {
		return cap_compl_qco_fem;
	}
	public void setCap_compl_qco_fem(Integer cap_compl_qco_fem) {
		this.cap_compl_qco_fem = cap_compl_qco_fem;
	}
	
	/**
	 * Capitão Praça QAO masculino
	 * @return Integer
	 */
	public Integer getCap_praca_qao_masc() {
		return cap_praca_qao_masc;
	}
	public void setCap_praca_qao_masc(Integer cap_praca_qao_masc) {
		this.cap_praca_qao_masc = cap_praca_qao_masc;
	}
	
	/**
	 * Capitão Praça QAO feminino
	 * @return Integer
	 */
	public Integer getCap_praca_qao_fem() {
		return cap_praca_qao_fem;
	}
	public void setCap_praca_qao_fem(Integer cap_praca_qao_fem) {
		this.cap_praca_qao_fem = cap_praca_qao_fem;
	}
	
	/**
	 * Capitão QCM QSG masculino
	 * @return Integer
	 */
	public Integer getCap_qcm_qsg_masc() {
		return cap_qcm_qsg_masc;
	}
	public void setCap_qcm_qsg_masc(Integer cap_qcm_qsg_masc) {
		this.cap_qcm_qsg_masc = cap_qcm_qsg_masc;
	}
	
	/**
	 * Capitão QCM QSG feminino
	 * @return Integer
	 */
	public Integer getCap_qcm_qsg_fem() {
		return cap_qcm_qsg_fem;
	}
	public void setCap_qcm_qsg_fem(Integer cap_qcm_qsg_fem) {
		this.cap_qcm_qsg_fem = cap_qcm_qsg_fem;
	}
	
	/**
	 * 1º Tenente Bélica QSG masculino
	 * @return Integer
	 */
	public Integer getTen1_belica_qsg_masc() {
		return ten1_belica_qsg_masc;
	}
	public void setTen1_belica_qsg_masc(Integer ten1_belica_qsg_masc) {
		this.ten1_belica_qsg_masc = ten1_belica_qsg_masc;
	}
	
	/**
	 * 1º Tenente Bélica QSG feminino
	 * @return Integer
	 */
	public Integer getTen1_belica_qsg_fem() {
		return ten1_belica_qsg_fem;
	}
	public void setTen1_belica_qsg_fem(Integer ten1_belica_qsg_fem) {
		this.ten1_belica_qsg_fem = ten1_belica_qsg_fem;
	}
	
	/**
	 * 1º Tenente Bélica temporário masculino
	 * @return Integer
	 */
	public Integer getTen1_belica_temp_masc() {
		return ten1_belica_temp_masc;
	}
	public void setTen1_belica_temp_masc(Integer ten1_belica_temp_masc) {
		this.ten1_belica_temp_masc = ten1_belica_temp_masc;
	}
	
	/**
	 * 1º Tenente Bélica temporário feminino
	 * @return Integer
	 */
	public Integer getTen1_belica_temp_fem() {
		return ten1_belica_temp_fem;
	}
	public void setTen1_belica_temp_fem(Integer ten1_belica_temp_fem) {
		this.ten1_belica_temp_fem = ten1_belica_temp_fem;
	}
	
	/**
	 * 1º Tenente Saúde QSG masculino
	 * @return Integer
	 */
	public Integer getTen1_saude_qsg_masc() {
		return ten1_saude_qsg_masc;
	}
	public void setTen1_saude_qsg_masc(Integer ten1_saude_qsg_masc) {
		this.ten1_saude_qsg_masc = ten1_saude_qsg_masc;
	}
	
	/**
	 * 1º Tenente Saúde QSG feminino
	 * @return Integer
	 */
	public Integer getTen1_saude_qsg_fem() {
		return ten1_saude_qsg_fem;
	}
	public void setTen1_saude_qsg_fem(Integer ten1_saude_qsg_fem) {
		this.ten1_saude_qsg_fem = ten1_saude_qsg_fem;
	}
	
	/**
	 * 1º Tenente Saúde Temporário masculino
	 * @return Integer
	 */
	public Integer getTen1_saude_temp_masc() {
		return ten1_saude_temp_masc;
	}
	public void setTen1_saude_temp_masc(Integer ten1_saude_temp_masc) {
		this.ten1_saude_temp_masc = ten1_saude_temp_masc;
	}
	
	/**
	 * 1º Tenente Saúde Temporário feminino
	 * @return Integer
	 */
	public Integer getTen1_saude_temp_fem() {
		return ten1_saude_temp_fem;
	}
	public void setTen1_saude_temp_fem(Integer ten1_saude_temp_fem) {
		this.ten1_saude_temp_fem = ten1_saude_temp_fem;
	}
	
	/**
	 * 1º Tenente Ciências & Tecnologia QSG masculino
	 * @return Integer
	 */
	public Integer getTen1_cieTec_qsg_masc() {
		return ten1_cieTec_qsg_masc;
	}
	public void setTen1_cieTec_qsg_masc(Integer ten1_cieTec_qsg_masc) {
		this.ten1_cieTec_qsg_masc = ten1_cieTec_qsg_masc;
	}
	
	/**
	 * 1º Tenente Ciências & Tecnologia QSG feminino
	 * @return Integer
	 */
	public Integer getTen1_cieTec_qsg_fem() {
		return ten1_cieTec_qsg_fem;
	}
	public void setTen1_cieTec_qsg_fem(Integer ten1_cieTec_qsg_fem) {
		this.ten1_cieTec_qsg_fem = ten1_cieTec_qsg_fem;
	}
	
	/**
	 * 1º Tenente Ciências & Tecnologia Temporário masculino
	 * @return Integer
	 */
	public Integer getTen1_cieTec_temp_masc() {
		return ten1_cieTec_temp_masc;
	}
	public void setTen1_cieTec_temp_masc(Integer ten1_cieTec_temp_masc) {
		this.ten1_cieTec_temp_masc = ten1_cieTec_temp_masc;
	}
	
	/**
	 * 1º Tenente Ciências & Tecnologia Temporário feminino
	 * @return Integer
	 */
	public Integer getTen1_cieTec_temp_fem() {
		return ten1_cieTec_temp_fem;
	}
	public void setTen1_cieTec_temp_fem(Integer ten1_cieTec_temp_fem) {
		this.ten1_cieTec_temp_fem = ten1_cieTec_temp_fem;
	}
	
	/**
	 * 1º Tenente Complemento QCO masculino
	 * @return Integer
	 */
	public Integer getTen1_compl_qco_masc() {
		return ten1_compl_qco_masc;
	}
	public void setTen1_compl_qco_masc(Integer ten1_compl_qco_masc) {
		this.ten1_compl_qco_masc = ten1_compl_qco_masc;
	}
	
	/**
	 * 1º Tenente Complemento QCO feminino
	 * @return Integer
	 */
	public Integer getTen1_compl_qco_fem() {
		return ten1_compl_qco_fem;
	}
	public void setTen1_compl_qco_fem(Integer ten1_compl_qco_fem) {
		this.ten1_compl_qco_fem = ten1_compl_qco_fem;
	}
	
	/**
	 * 1º Tenente Complemento Temporário masculino
	 * @return Integer
	 */
	public Integer getTen1_compl_temp_masc() {
		return ten1_compl_temp_masc;
	}
	public void setTen1_compl_temp_masc(Integer ten1_compl_temp_masc) {
		this.ten1_compl_temp_masc = ten1_compl_temp_masc;
	}
	
	/**
	 * 1º Tenente Complemento Temporário feminino
	 * @return Integer
	 */
	public Integer getTen1_compl_temp_fem() {
		return ten1_compl_temp_fem;
	}
	public void setTen1_compl_temp_fem(Integer ten1_compl_temp_fem) {
		this.ten1_compl_temp_fem = ten1_compl_temp_fem;
	}
	
	/**
	 * 1º Tenente Praça QAO masculino
	 * @return Integer
	 */
	public Integer getTen1_praca_qao_masc() {
		return ten1_praca_qao_masc;
	}
	public void setTen1_praca_qao_masc(Integer ten1_praca_qao_masc) {
		this.ten1_praca_qao_masc = ten1_praca_qao_masc;
	}
	
	/**
	 * 1º Tenente Praça QAO feminino
	 * @return Integer
	 */
	public Integer getTen1_praca_qao_fem() {
		return ten1_praca_qao_fem;
	}
	public void setTen1_praca_qao_fem(Integer ten1_praca_qao_fem) {
		this.ten1_praca_qao_fem = ten1_praca_qao_fem;
	}
	
	/**
	 * 1º Tenente QCM QSG masculino
	 * @return Integer
	 */
	public Integer getTen1_qcm_qsg_masc() {
		return ten1_qcm_qsg_masc;
	}
	public void setTen1_qcm_qsg_masc(Integer ten1_qcm_qsg_masc) {
		this.ten1_qcm_qsg_masc = ten1_qcm_qsg_masc;
	}
	
	/**
	 * 1º Tenente QCM QSG feminino
	 * @return Integer
	 */
	public Integer getTen1_qcm_qsg_fem() {
		return ten1_qcm_qsg_fem;
	}
	public void setTen1_qcm_qsg_fem(Integer ten1_qcm_qsg_fem) {
		this.ten1_qcm_qsg_fem = ten1_qcm_qsg_fem;
	}
	
	/**
	 * 1º Tenente QCM Temporário masculino
	 * @return Integer
	 */
	public Integer getTen1_qcm_temp_masc() {
		return ten1_qcm_temp_masc;
	}
	public void setTen1_qcm_temp_masc(Integer ten1_qcm_temp_masc) {
		this.ten1_qcm_temp_masc = ten1_qcm_temp_masc;
	}
	
	/**
	 * 1º Tenente QCM Temporário feminino
	 * @return Integer
	 */
	public Integer getTen1_qcm_temp_fem() {
		return ten1_qcm_temp_fem;
	}
	public void setTen1_qcm_temp_fem(Integer ten1_qcm_temp_fem) {
		this.ten1_qcm_temp_fem = ten1_qcm_temp_fem;
	}
	
	/**
	 * 2º Tenente Bélica QSG masculino
	 * @return Integer
	 */
	public Integer getTen2_belica_qsg_masc() {
		return ten2_belica_qsg_masc;
	}
	public void setTen2_belica_qsg_masc(Integer ten2_belica_qsg_masc) {
		this.ten2_belica_qsg_masc = ten2_belica_qsg_masc;
	}
	
	/**
	 * 2º Tenente Bélica QSG feminino
	 * @return Integer
	 */
	public Integer getTen2_belica_qsg_fem() {
		return ten2_belica_qsg_fem;
	}
	public void setTen2_belica_qsg_fem(Integer ten2_belica_qsg_fem) {
		this.ten2_belica_qsg_fem = ten2_belica_qsg_fem;
	}
	
	/**
	 * 2º Tenente Bélica Temporário masculino
	 * @return Integer
	 */
	public Integer getTen2_belica_temp_masc() {
		return ten2_belica_temp_masc;
	}
	public void setTen2_belica_temp_masc(Integer ten2_belica_temp_masc) {
		this.ten2_belica_temp_masc = ten2_belica_temp_masc;
	}
	
	/**
	 * 2º Tenente Bélica Temporário feminino
	 * @return Integer
	 */
	public Integer getTen2_belica_temp_fem() {
		return ten2_belica_temp_fem;
	}
	public void setTen2_belica_temp_fem(Integer ten2_belica_temp_fem) {
		this.ten2_belica_temp_fem = ten2_belica_temp_fem;
	}
	
	/**
	 * 2º Tenente Saúde QSG masculino
	 * @return Integer
	 */
	public Integer getTen2_saude_qsg_masc() {
		return ten2_saude_qsg_masc;
	}
	public void setTen2_saude_qsg_masc(Integer ten2_saude_qsg_masc) {
		this.ten2_saude_qsg_masc = ten2_saude_qsg_masc;
	}
	
	/**
	 * 2º Tenente Saúde QSG feminino
	 * @return Integer
	 */
	public Integer getTen2_saude_qsg_fem() {
		return ten2_saude_qsg_fem;
	}
	public void setTen2_saude_qsg_fem(Integer ten2_saude_qsg_fem) {
		this.ten2_saude_qsg_fem = ten2_saude_qsg_fem;
	}
	
	/**
	 * 2º Tenente Saúde Temporário masculino
	 * @return Integer
	 */
	public Integer getTen2_saude_temp_masc() {
		return ten2_saude_temp_masc;
	}
	public void setTen2_saude_temp_masc(Integer ten2_saude_temp_masc) {
		this.ten2_saude_temp_masc = ten2_saude_temp_masc;
	}
	
	/**
	 * 2º Tenente Saúde Temporário feminino
	 * @return Integer
	 */
	public Integer getTen2_saude_temp_fem() {
		return ten2_saude_temp_fem;
	}
	public void setTen2_saude_temp_fem(Integer ten2_saude_temp_fem) {
		this.ten2_saude_temp_fem = ten2_saude_temp_fem;
	}
	
	/**
	 * 2º Tenente Ciências & Tecnologia QSG masculino
	 * @return Integer
	 */
	public Integer getTen2_cieTec_qsg_masc() {
		return ten2_cieTec_qsg_masc;
	}
	public void setTen2_cieTec_qsg_masc(Integer ten2_cieTec_qsg_masc) {
		this.ten2_cieTec_qsg_masc = ten2_cieTec_qsg_masc;
	}
	
	/**
	 * 2º Tenente Ciências & Tecnologia QSG feminino
	 * @return Integer
	 */
	public Integer getTen2_cieTec_qsg_fem() {
		return ten2_cieTec_qsg_fem;
	}
	public void setTen2_cieTec_qsg_fem(Integer ten2_cieTec_qsg_fem) {
		this.ten2_cieTec_qsg_fem = ten2_cieTec_qsg_fem;
	}
	
	/**
	 * 2º Tenente Ciências & Tecnologia Temporário masculino
	 * @return Integer
	 */
	public Integer getTen2_cieTec_temp_masc() {
		return ten2_cieTec_temp_masc;
	}
	public void setTen2_cieTec_temp_masc(Integer ten2_cieTec_temp_masc) {
		this.ten2_cieTec_temp_masc = ten2_cieTec_temp_masc;
	}
	
	/**
	 * 2º Tenente Ciências & Tecnologia Temporário feminino
	 * @return Integer
	 */
	public Integer getTen2_cieTec_temp_fem() {
		return ten2_cieTec_temp_fem;
	}
	public void setTen2_cieTec_temp_fem(Integer ten2_cieTec_temp_fem) {
		this.ten2_cieTec_temp_fem = ten2_cieTec_temp_fem;
	}
	
	/**
	 * 2º Tenente Complementar Temporário masculino
	 * @return Integer
	 */
	public Integer getTen2_compl_temp_masc() {
		return ten2_compl_temp_masc;
	}
	public void setTen2_compl_temp_masc(Integer ten2_compl_temp_masc) {
		this.ten2_compl_temp_masc = ten2_compl_temp_masc;
	}
	
	/**
	 * 2º Tenente Complementar Temporário feminino
	 * @return Integer
	 */
	public Integer getTen2_compl_temp_fem() {
		return ten2_compl_temp_fem;
	}
	public void setTen2_compl_temp_fem(Integer ten2_compl_temp_fem) {
		this.ten2_compl_temp_fem = ten2_compl_temp_fem;
	}
	
	/**
	 * 2º Tenente Praça QAO masculino
	 * @return Integer
	 */
	public Integer getTen2_praca_qao_masc() {
		return ten2_praca_qao_masc;
	}
	public void setTen2_praca_qao_masc(Integer ten2_praca_qao_masc) {
		this.ten2_praca_qao_masc = ten2_praca_qao_masc;
	}
	
	/**
	 * 2º Tenente QCM QSG masculino
	 * @return Integer
	 */
	public Integer getTen2_qcm_qsg_masc() {
		return ten2_qcm_qsg_masc;
	}
	public void setTen2_qcm_qsg_masc(Integer ten2_qcm_qsg_masc) {
		this.ten2_qcm_qsg_masc = ten2_qcm_qsg_masc;
	}
	
	/**
	 * 2º Tenente QCM Temporário masculino
	 * @return Integer
	 */
	public Integer getTen2_qcm_temp_masc() {
		return ten2_qcm_temp_masc;
	}
	public void setTen2_qcm_temp_masc(Integer ten2_qcm_temp_masc) {
		this.ten2_qcm_temp_masc = ten2_qcm_temp_masc;
	}
	
	/**
	 * Aspirante Bélica QSG masculino
	 * @return Integer
	 */
	public Integer getAsp_belica_qsg_masc() {
		return asp_belica_qsg_masc;
	}
	public void setAsp_belica_qsg_masc(Integer asp_belica_qsg_masc) {
		this.asp_belica_qsg_masc = asp_belica_qsg_masc;
	}
	
	/**
	 * Aspirante Bélica QSG feminino
	 * @return Integer
	 */
	public Integer getAsp_belica_qsg_fem() {
		return asp_belica_qsg_fem;
	}
	public void setAsp_belica_qsg_fem(Integer asp_belica_qsg_fem) {
		this.asp_belica_qsg_fem = asp_belica_qsg_fem;
	}
	
	/**
	 * Aspirante Bélica Temporário masculino
	 * @return Integer
	 */
	public Integer getAsp_belica_temp_masc() {
		return asp_belica_temp_masc;
	}
	public void setAsp_belica_temp_masc(Integer asp_belica_temp_masc) {
		this.asp_belica_temp_masc = asp_belica_temp_masc;
	}
	
	/**
	 * Aspirante Bélica Temporário feminino
	 * @return Integer
	 */
	public Integer getAsp_belica_temp_fem() {
		return asp_belica_temp_fem;
	}
	public void setAsp_belica_temp_fem(Integer asp_belica_temp_fem) {
		this.asp_belica_temp_fem = asp_belica_temp_fem;
	}
	
	/**
	 * Aspirante Saúde Temporário masculino
	 * @return Integer
	 */
	public Integer getAsp_saude_temp_masc() {
		return asp_saude_temp_masc;
	}
	public void setAsp_saude_temp_masc(Integer asp_saude_temp_masc) {
		this.asp_saude_temp_masc = asp_saude_temp_masc;
	}
	
	/**
	 * Aspirante Saúde Temporário feminino
	 * @return Integer
	 */
	public Integer getAsp_saude_temp_fem() {
		return asp_saude_temp_fem;
	}
	public void setAsp_saude_temp_fem(Integer asp_saude_temp_fem) {
		this.asp_saude_temp_fem = asp_saude_temp_fem;
	}
	
	/**
	 * Aspirante Ciência & Tecnologia Temporário masculino
	 * @return Integer
	 */
	public Integer getAsp_cieTec_temp_masc() {
		return asp_cieTec_temp_masc;
	}
	public void setAsp_cieTec_temp_masc(Integer asp_cieTec_temp_masc) {
		this.asp_cieTec_temp_masc = asp_cieTec_temp_masc;
	}
	
	/**
	 * Aspirante Ciência & Tecnologia Temporário feminino
	 * @return Integer
	 */
	public Integer getAsp_cieTec_temp_fem() {
		return asp_cieTec_temp_fem;
	}
	public void setAsp_cieTec_temp_fem(Integer asp_cieTec_temp_fem) {
		this.asp_cieTec_temp_fem = asp_cieTec_temp_fem;
	}
	
	/**
	 * Aspirante QCM Temporário masculino
	 * @return Integer
	 */
	public Integer getAsp_qcm_temp_masc() {
		return asp_qcm_temp_masc;
	}
	public void setAsp_qcm_temp_masc(Integer asp_qcm_temp_masc) {
		this.asp_qcm_temp_masc = asp_qcm_temp_masc;
	}
	
	/**
	 * Sub Tenente masculino
	 * @return Integer
	 */
	public Integer getSt_masc() {
		return st_masc;
	}
	public void setSt_masc(Integer st_masc) {
		this.st_masc = st_masc;
	}
	
	/**
	 * Sub Tenente feminino
	 * @return Integer
	 */
	public Integer getSt_fem() {
		return st_fem;
	}
	public void setSt_fem(Integer st_fem) {
		this.st_fem = st_fem;
	}
	
	/**
	 * 1º Sargento masculino
	 * @return Integer
	 */
	public Integer getSgt1_masc() {
		return sgt1_masc;
	}
	public void setSgt1_masc(Integer sgt1_masc) {
		this.sgt1_masc = sgt1_masc;
	}
	
	/**
	 * 1º Sargento feminino
	 * @return Integer
	 */
	public Integer getSgt1_fem() {
		return sgt1_fem;
	}
	public void setSgt1_fem(Integer sgt1_fem) {
		this.sgt1_fem = sgt1_fem;
	}
	
	/**
	 * 2º Sargento Carreira masculino
	 * @return Integer
	 */
	public Integer getSgt2_carr_masc() {
		return sgt2_carr_masc;
	}
	public void setSgt2_carr_masc(Integer sgt2_carr_masc) {
		this.sgt2_carr_masc = sgt2_carr_masc;
	}
	
	/**
	 * 2º Sargento Carreira feminino
	 * @return Integer
	 */
	public Integer getSgt2_carr_fem() {
		return sgt2_carr_fem;
	}
	public void setSgt2_carr_fem(Integer sgt2_carr_fem) {
		this.sgt2_carr_fem = sgt2_carr_fem;
	}
	
	/**
	 * 2º Sargento QE masculino
	 * @return Integer
	 */
	public Integer getSgt2_qe_masc() {
		return sgt2_qe_masc;
	}
	public void setSgt2_qe_masc(Integer sgt2_qe_masc) {
		this.sgt2_qe_masc = sgt2_qe_masc;
	}
	
	/**
	 * 3º Sargento Carreira masculino
	 * @return Integer
	 */
	public Integer getSgt3_carr_masc() {
		return sgt3_carr_masc;
	}
	public void setSgt3_carr_masc(Integer sgt3_carr_masc) {
		this.sgt3_carr_masc = sgt3_carr_masc;
	}
	
	/**
	 * 3º Sargento Carreira feminino
	 * @return Integer
	 */
	public Integer getSgt3_carr_fem() {
		return sgt3_carr_fem;
	}
	public void setSgt3_carr_fem(Integer sgt3_carr_fem) {
		this.sgt3_carr_fem = sgt3_carr_fem;
	}
	
	/**
	 * 3º Sargento Temporário masculino
	 * @return Integer
	 */
	public Integer getSgt3_temp_masc() {
		return sgt3_temp_masc;
	}
	public void setSgt3_temp_masc(Integer sgt3_temp_masc) {
		this.sgt3_temp_masc = sgt3_temp_masc;
	}
	
	/**
	 * 3º Sargento Temporário feminino
	 * @return Integer
	 */
	public Integer getSgt3_temp_fem() {
		return sgt3_temp_fem;
	}
	public void setSgt3_temp_fem(Integer sgt3_temp_fem) {
		this.sgt3_temp_fem = sgt3_temp_fem;
	}
	
	/**
	 * 3º Sargento QE masculino
	 * @return Integer
	 */
	public Integer getSgt3_qe_masc() {
		return sgt3_qe_masc;
	}
	public void setSgt3_qe_masc(Integer sgt3_qe_masc) {
		this.sgt3_qe_masc = sgt3_qe_masc;
	}
	
	/**
	 * Taifero Mor
	 * @return Integer
	 */
	public Integer getTm() {
		return tm;
	}
	public void setTm(Integer tm) {
		this.tm = tm;
	}
	
	/**
	 * Taifero 1
	 * @return Integer
	 */
	public Integer getT1() {
		return t1;
	}
	public void setT1(Integer t1) {
		this.t1 = t1;
	}
	
	/**
	 * Taifero 2
	 * @return Integer
	 */
	public Integer getT2() {
		return t2;
	}
	public void setT2(Integer t2) {
		this.t2 = t2;
	}
	
	/**
	 * Cabo Efetivo Variável
	 * @return Integer
	 */
	public Integer getCb_ev() {
		return cb_ev;
	}
	public void setCb_ev(Integer cb_ev) {
		this.cb_ev = cb_ev;
	}
	
	/**
	 * Cabo Efetivo Profissional
	 * @return Integer
	 */
	public Integer getCb_ep() {
		return cb_ep;
	}
	public void setCb_ep(Integer cb_ep) {
		this.cb_ep = cb_ep;
	}
	
	/**
	 * Cabo Efetivo Temporário
	 * @return Integer
	 */
	public Integer getCb_et() {
		return cb_et;
	}
	public void setCb_et(Integer cb_et) {
		this.cb_et = cb_et;
	}
	
	/**
	 * Soldado Não Qualificado
	 * @return Integer
	 */
	public Integer getSd_nq() {
		return sd_nq;
	}
	public void setSd_nq(Integer sd_nq) {
		this.sd_nq = sd_nq;
	}
	
	/**
	 * Soldado Efetivo Variável
	 * @return Integer
	 */
	public Integer getSd_ev() {
		return sd_ev;
	}
	public void setSd_ev(Integer sd_ev) {
		this.sd_ev = sd_ev;
	}
	
	/**
	 * Soldado Efetivo Profissional
	 * @return Integer
	 */
	public Integer getSd_ep() {
		return sd_ep;
	}
	public void setSd_ep(Integer sd_ep) {
		this.sd_ep = sd_ep;
	}
	
	/**
	 * PTTC Oficial
	 * @return Integer
	 */
	public Integer getPttc_of() {
		return pttc_of;
	}
	public void setPttc_of(Integer pttc_of) {
		this.pttc_of = pttc_of;
	}
	
	/**
	 * PTTC Praça
	 * @return Integer
	 */
	public Integer getPttc_pr() {
		return pttc_pr;
	}
	public void setPttc_pr(Integer pttc_pr) {
		this.pttc_pr = pttc_pr;
	}
	
	/**
	 * Servidor Civil
	 * @return Integer
	 */
	public Integer getServCiv() {
		return servCiv;
	}
	public void setServCiv(Integer servCiv) {
		this.servCiv = servCiv;
	}
	
	/**
	 * Professor Civil
	 * @return Integer
	 */
	public Integer getProfCiv() {
		return profCiv;
	}
	public void setProfCiv(Integer profCiv) {
		this.profCiv = profCiv;
	}
	
	/**
	 * Aluno masculino
	 * @return Integer
	 */
	public Integer getAluno_masc() {
		return aluno_masc;
	}
	public void setAluno_masc(Integer aluno_masc) {
		this.aluno_masc = aluno_masc;
	}
	
	/**
	 * Aluno feminino
	 * @return Integer
	 */
	public Integer getAluno_fem() {
		return aluno_fem;
	}
	public void setAluno_fem(Integer aluno_fem) {
		this.aluno_fem = aluno_fem;
	}
	
	/**
	 * Controle de versão dos registro de relatório
	 * @return Integer
	 */
	public Integer getSeq_registro() {
		return seq_registro;
	}
	public void setSeq_registro(Integer seq_registro) {
		this.seq_registro = seq_registro;
	}
	
	/**
	 * Guarda a organição militar enquadrante
	 * @see br.mil.eb.decex.ati.modelo.OrganizacaoMilitar
	 * @return {@link OrganizacaoMilitar}
	 */
	public OrganizacaoMilitar getOrganizacao_enquadrante() {
		return organizacao_enquadrante;
	}
	public void setOrganizacao_enquadrante(OrganizacaoMilitar organizacao_enquadrante) {
		this.organizacao_enquadrante = organizacao_enquadrante;
	}
	public Integer getCel_belica_qfe_fem() {
		return cel_belica_qfe_fem;
	}
	public void setCel_belica_qfe_fem(Integer cel_belica_qfe_fem) {
		this.cel_belica_qfe_fem = cel_belica_qfe_fem;
	}
	public Integer gettCel_belica_qfe_masc() {
		return tCel_belica_qfe_masc;
	}
	public void settCel_belica_qfe_masc(Integer tCel_belica_qfe_masc) {
		this.tCel_belica_qfe_masc = tCel_belica_qfe_masc;
	}
	public Integer gettCel_belica_qfe_fem() {
		return tCel_belica_qfe_fem;
	}
	public void settCel_belica_qfe_fem(Integer tCel_belica_qfe_fem) {
		this.tCel_belica_qfe_fem = tCel_belica_qfe_fem;
	}
	public Integer getMaj_belica_qfe_masc() {
		return maj_belica_qfe_masc;
	}
	public void setMaj_belica_qfe_masc(Integer maj_belica_qfe_masc) {
		this.maj_belica_qfe_masc = maj_belica_qfe_masc;
	}
	public Integer getMaj_belica_qfe_fem() {
		return maj_belica_qfe_fem;
	}
	public void setMaj_belica_qfe_fem(Integer maj_belica_qfe_fem) {
		this.maj_belica_qfe_fem = maj_belica_qfe_fem;
	}
	public Integer getCel_belica_qfe_masc() {
		return cel_belica_qfe_masc;
	}
	public void setCel_belica_qfe_masc(Integer cel_belica_qfe_masc) {
		this.cel_belica_qfe_masc = cel_belica_qfe_masc;
	}
	public Integer getAsp_compl_temp_masc() {
		return asp_compl_temp_masc;
	}
	public void setAsp_compl_temp_masc(Integer asp_compl_temp_masc) {
		this.asp_compl_temp_masc = asp_compl_temp_masc;
	}
	public Integer getAsp_compl_temp_fem() {
		return asp_compl_temp_fem;
	}
	public void setAsp_compl_temp_fem(Integer asp_compl_temp_fem) {
		this.asp_compl_temp_fem = asp_compl_temp_fem;
	}
	public Integer getCb_et_fem() {
		return cb_et_fem;
	}
	public void setCb_et_fem(Integer cb_et_fem) {
		this.cb_et_fem = cb_et_fem;
	}
	public Integer getPttc_of_fem() {
		return pttc_of_fem;
	}
	public void setPttc_of_fem(Integer pttc_of_fem) {
		this.pttc_of_fem = pttc_of_fem;
	}
	public Integer getPttc_pr_fem() {
		return pttc_pr_fem;
	}
	public void setPttc_pr_fem(Integer pttc_pr_fem) {
		this.pttc_pr_fem = pttc_pr_fem;
	}
	public Integer getServCiv_fem() {
		return servCiv_fem;
	}
	public void setServCiv_fem(Integer servCiv_fem) {
		this.servCiv_fem = servCiv_fem;
	}
	public Integer getProfCiv_fem() {
		return profCiv_fem;
	}
	public void setProfCiv_fem(Integer profCiv_fem) {
		this.profCiv_fem = profCiv_fem;
	}

}