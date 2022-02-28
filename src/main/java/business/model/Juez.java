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
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "JUEZ", indexes = {
		@Index(name = "JUEZ__IDENTIFICADOR__UK", columnList = "IDENTIFICADOR", unique = true),
		@Index(name = "JUEZ__NOMBRE__IX", columnList = "NOMBRE")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "JUEZ__IDENTIFICADOR__UK", columnNames = { "IDENTIFICADOR" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Juez implements Serializable {

	private static final long serialVersionUID = -4610606391379915543L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	@Column(name = "IDENTIFICADOR", nullable = false, length = 10)
	private String codigo;

	@NonNull
	@Column(name = "NOMBRE", nullable = false, length = 100)
	private String nombre;

	@ToString.Exclude
	@OneToMany(mappedBy = "juez")
	private List<Atraco> atracos = new ArrayList<>();


	public Juez(final @NonNull String codigo, final @NonNull String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	//
	// Metodos heredados
	//



	//
	// Metodos delegados
	//

	public long getNumAtracos() {
		return atracos == null ? 0 : atracos.size();
	}

	//
	// Getters y setters
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	@NonNull
	public List<Atraco> getAtracos() {
		return new ArrayList<>(atracos);
	}

	@NonNull
	List<Atraco> _getAtracos() {
		return this.atracos;
	}

	public void addAtraco(final @NonNull Atraco atraco) {
		RelationalHelper.Juzgar.link(this, atraco);
	}

}
