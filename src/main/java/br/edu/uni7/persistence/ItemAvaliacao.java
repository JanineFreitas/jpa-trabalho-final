package br.edu.uni7.persistence;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="TBL_ITENS_AVALIACAO")
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="ST_TIPO_ITEM_AVALIACAO")
public abstract class ItemAvaliacao {
	
	@Id
	@Column(name = "PK_ITEM_AVAL")
	@GeneratedValue(strategy=GenerationType.IDENTITY) 
	private Long id;
	
	@Column(name = "NM_COMENTARIO")
	@NotNull
	@NotEmpty
	@Size (max=500)
	private String comentario;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ST_STATUS")
	@NotNull
	private Status status;
//	
//	@PrePersist @PreUpdate
//	public void validarCampos() {
//		if(comentario == null || comentario.isEmpty()){
//			throw new IllegalStateException("O atributo comentario � obrigat�rio");
//		}
//	}
	
	@PrePersist @PreUpdate
	public void validarCamposStatus() {
		this.status = Status.ABERTO;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		ItemAvaliacao other = (ItemAvaliacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
