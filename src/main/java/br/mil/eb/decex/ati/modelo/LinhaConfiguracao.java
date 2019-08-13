package br.mil.eb.decex.ati.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import br.mil.eb.decex.ati.enumerado.Linha;
import br.mil.eb.decex.ati.enumerado.PostoGraduacao;
import br.mil.eb.decex.ati.enumerado.Tipo;

/**
 * Configuração para cada posto e/ou graduação de acordo com o exmplo abaixo:</p>
 * 
 * Posto/Graduação -> CEL<br/>
 * Linha -> Bélica<br/>
 * Tipo -> QEMA
 * 
 * @author William <b>Moreira</b> de Pinho - 1º Ten QCO
 * @version 1.0
 * @author Jonathan Philipe Amaral <b>Crespo</b> 3º SGT
 * @modified Alteração de NamedQuery
 */
@Entity
@NamedQueries({
    @NamedQuery(name="LinhaConfiguracao.porParametros",
                query="SELECT o FROM LinhaConfiguracao o WHERE o.postoGraduacao = :posto AND o.linha = :linha AND o.tipo = :tipo"),
}) 
@Table(name="linhaconfiguracao", uniqueConstraints={@UniqueConstraint(columnNames={ "postoGraduacao", "linha", "tipo"})})
public class LinhaConfiguracao extends BaseModel<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="LC_ID_GENERATOR", sequenceName="LC_ID_SEQ", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LC_ID_GENERATOR")		
	private Long id;	
	
	@Enumerated(EnumType.STRING)
	private PostoGraduacao postoGraduacao;
	
	@Enumerated(EnumType.STRING)
	private Linha linha;
	
	@Enumerated(EnumType.STRING)
	private Tipo tipo;
	
	@Column
	private Integer ordem;
	
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
	 * Posto/Graduação para configuração da linha e tipo
	 * @return Posto/Graduação 
	 */
	public PostoGraduacao getPostoGraduacao() {
		return postoGraduacao;
	}
	public void setPostoGraduacao(PostoGraduacao postoGraduacao) {
		this.postoGraduacao = postoGraduacao;
	}
	
	/**
	 * Linha para o Posto/Graduação
	 * @return linha do posto/graduação
	 */
	public Linha getLinha() {
		return linha;
	}
	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	/**
	 * Tipo para Linha do Posto/Graduação
	 * @return tipo para linha do Posto/Graduação
	 */	
	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	/**
	 * Campo referênte a ordenção da Linha Configuração
	 * @return Integer de ordenação
	 */
	public Integer getOrdem() {
		return ordem;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((linha == null) ? 0 : linha.hashCode());
		result = prime * result + ((postoGraduacao == null) ? 0 : postoGraduacao.hashCode());
		result = prime * result + ((tipo == null) ? 0 : tipo.hashCode());
		result = prime * result + ((ordem == null) ? 0 : ordem.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof LinhaConfiguracao))
			return false;
		LinhaConfiguracao other = (LinhaConfiguracao) obj;
		if (getLinha() != other.getLinha())
			return false;
		if (getPostoGraduacao() != other.getPostoGraduacao())
			return false;
		if (getTipo() != other.getTipo())
			return false;
		if (getOrdem() != other.getOrdem())
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "LinhaConfiguracao [postoGraduacao=" + postoGraduacao + ", linha=" + linha + ", tipo=" + tipo + "]";
	}
	
}