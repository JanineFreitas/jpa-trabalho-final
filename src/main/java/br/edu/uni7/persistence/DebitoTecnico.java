package br.edu.uni7.persistence;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "TBL_DEBITOS_TECNICOS")
@DiscriminatorValue("DT") 
@PrimaryKeyJoinColumn(name="FK_ITEM_AVAL")
public class DebitoTecnico extends ItemAvaliacao {
	
	@Column(name = "NU_CUSTO")
	@Min(1)
	@Max(5)
	private Long custo;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name="ST_IMPACTO")
	private Impacto impacto;

	public Impacto getImpacto() {
		return impacto;
	}

	public void setImpacto(Impacto impacto) {
		this.impacto = impacto;
	}

	public Long getCusto() {
		return custo;
	}

	public void setCusto(Long custo) {
		this.custo = custo;
	}
}
