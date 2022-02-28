package business.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "BANDA_ORGANIZADA", indexes = {
		@Index(name = "BANDA_ORGANIZADA__CODIGO__UK", columnList = "CODIGO", unique = true),
		@Index(name = "BANDA_ORGANIZADA__NMIEMB__IX", columnList = "NUM_MIEMBROS")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "BANDA_ORGANIZADA__CODIGO__UK", columnNames = { "CODIGO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BandaOrganizada implements Serializable {

	private static final long serialVersionUID = -5644197328475561675L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	@Column(name = "CODIGO", nullable = false, length = 10)
	private String codigo;

	@Column(name = "NUM_MIEMBROS", nullable = false, columnDefinition = "int unsigned default 0")
	private int numMiembros;

	@ToString.Exclude
	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "bandaOrganizada")
	private List<Delincuente> delincuentes = new ArrayList<>();


	public BandaOrganizada(final @NonNull String codigo, final int numMiembros) {
		super();
		this.codigo = codigo;
		this.numMiembros = numMiembros;
	}


	//
	// Metodos heredados
	//



	//
	// Metodos delegados
	//



	//
	// Getters y setters
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	@NonNull
	public List<Delincuente> getDelincuentes() {
		return new ArrayList<>(delincuentes);
	}

	@NonNull
	List<Delincuente> _getDelincuentes() {
		return this.delincuentes;
	}

	public void setDelincuentes(List<Delincuente> delincuentes) {
		this.delincuentes = delincuentes;
		this.numMiembros = delincuentes.size();
	}

	public void addDelincuente(Delincuente delincuente) {
		RelationalHelper.Afiliarse.link(this, delincuente);
		this.numMiembros++;
	}

	public void removeDelincuente(final @NonNull Delincuente delincuente) {
		RelationalHelper.Afiliarse.unlink(this, delincuente);
		this.numMiembros--;
	}

}
