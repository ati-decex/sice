package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * Cada efetivo informado por uma OMDS deve obrigatóriamente fazer 
 * parte de um período. <p/>
 * 
 * Exemplo: 10/2016 (Efetivo para este período)
 * @author William <b>Moreira</b> de Pinho - CAP
 * @version 1.0
 */
@Entity
@NamedQueries({
		@NamedQuery(name = "Periodo.doAno", 			  query = "SELECT p FROM Periodo p WHERE p.ano NOT IN(2017) and CURRENT_DATE >= p.dataInicial  ORDER BY p.mes"),
		@NamedQuery(name = "Periodo.ordenado", 			  query = "SELECT p FROM Periodo p WHERE p.id NOT IN(13, 14, 15, 16) and CURRENT_DATE >= p.dataInicial  ORDER BY p.mes"),
		@NamedQuery(name = "Periodo.incluindoProximoMes", query = "SELECT p FROM Periodo p WHERE p.id NOT IN(13, 14, 15, 16) and :datainicial >= p.dataInicial ORDER BY p.ano desc ,p.mes desc"),
		@NamedQuery(name = "Periodo.ordemDesc", query = "SELECT p FROM Periodo p ORDER BY p.ano desc ,p.mes desc"),
		@NamedQuery(name = "Periodo.lancamento", query = "SELECT p FROM Periodo p WHERE p.mes >= :mes and p.ano = :ano  ORDER BY p.ano asc ,p.mes asc") ,
		@NamedQuery(name = "Periodo.lancamento2", query = "SELECT p FROM Periodo p WHERE p.mes > :mes and p.ano = :ano  ORDER BY p.ano asc ,p.mes asc") ,
		@NamedQuery(name = "Periodo.porMes", query = "SELECT p FROM Periodo p WHERE p.mes = :mes") })
    
@Table(name="periodo", uniqueConstraints={@UniqueConstraint(columnNames={"mes", "ano"})})
public class Periodo extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PERIODO_ID_GENERATOR", sequenceName="PERIODO_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PERIODO_ID_GENERATOR")		
	private Long id;			
	
	@Enumerated(EnumType.ORDINAL)
	private Month mes;
	
	@Column
	private Integer ano;
	
	@Temporal(TemporalType.DATE)
	private Date dataInicial;
	
	@Temporal(TemporalType.DATE)	
	private Date dataFinal;
	
	@Transient
	private static final Map<Month, String> mesTraduzido;
	
	static {
		
		mesTraduzido = new HashMap<Month, String>();
		getMestraduzido().put(Month.JANUARY, "Janeiro");
		getMestraduzido().put(Month.FEBRUARY, "Fevereiro");
		getMestraduzido().put(Month.MARCH, "Março");
		getMestraduzido().put(Month.APRIL, "Abril");
		getMestraduzido().put(Month.MAY, "Maio");
		getMestraduzido().put(Month.JUNE, "Junho");
		getMestraduzido().put(Month.JULY, "Julho");
		getMestraduzido().put(Month.AUGUST, "Agosto");
		getMestraduzido().put(Month.SEPTEMBER, "Setembro");
		getMestraduzido().put(Month.OCTOBER, "Outubro");
		getMestraduzido().put(Month.NOVEMBER, "Novembro");
		getMestraduzido().put(Month.DECEMBER, "Dezembro");
		
	}
	
	public Periodo() {
		
	}
	
	public Periodo(Month mes, Integer ano) {
		this.mes = mes;
		this.ano = ano;
	}
	
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
	 * Mês de referência do efetivo informado para a OMDS
	 * @return Janeiro a Dezembro
	 */
	public Month getMes() {
		return mes;
	}
	public void setMes(Month mes) {
		this.mes = mes;
	}

	/**
	 * ano de referência do efetivo informado para a OMDS
	 * @return ano
	 */	
	public Integer getAno() {
		return ano;
	}
	public void setAno(Integer ano) {
		this.ano = ano;
	}

	/**
	 * Data inicial para lançamento das informações do efetivo existente
	 * @return data inicial para lançamento das informações
	 */
	public Date getDataInicial() {
		return dataInicial;
	}
	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	/**
	 * Data final para lançamento das informações do efetivo existente
	 * @return data final para lançamento das informações
	 */	
	public Date getDataFinal() {
		return dataFinal;
	}
	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}
	
	/**
	 * Guarda um mapa com o mês traduzido
	 * @return mesTraduzido Map<Month, String>
	 */
	public static Map<Month, String> getMestraduzido() {
		return mesTraduzido;
	}
	
	/**
	 * Retorna o mês traduzido
	 * @param mes int
	 * @return mês traduzido
	 */
	public String traduzirMes(int mes) {
		return Month.of((mes)).getDisplayName(TextStyle.FULL, new Locale("pt", "BR"));				
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ano == null) ? 0 : ano.hashCode());
		result = prime * result + ((mes == null) ? 0 : mes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Periodo other = (Periodo) obj;
		if (ano == null) {
			if (other.ano != null)
				return false;
		} else if (!ano.equals(other.ano))
			return false;
		if (mes != other.mes)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getMestraduzido().get(mes) +"/" + ano;
	}
}