package business.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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
@Table(name = "VIGILANTE", indexes = {
		@Index(name = "VIGILANTE__CODIGO__UK", columnList = "CODIGO", unique = true),
		@Index(name = "VIGILANTE__NOMBRE__IX", columnList = "NOMBRE")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "VIGILANTE__CODIGO__UK", columnNames = { "CODIGO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Vigilante implements IEntity<Long> {

	private static final long serialVersionUID = -7901671813878211299L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	@Column(name = "CODIGO", nullable = false, length = 10)
	private String codigo;

	@NonNull
	@Column(name = "NOMBRE", nullable = false, length = 100)
	private String nombre;

	@ToString.Exclude
	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "vigilante")
	private List<Contrato> contratos = new ArrayList<>();


	public Vigilante(final @NonNull String codigo, final @NonNull String nombre) {
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

	public long getNumContratos() {
		return contratos == null ? 0 : contratos.size();
	}

	@NonNull
	public List<Contrato> getContratosActivos() {
		return getContratosActivos(new Date());
	}

	@NonNull
	public List<Contrato> getContratosActivos(final @NonNull Date date) {
		// lamda entre fechas con fecha de fin abierta
		final Predicate<Contrato> predicate = contrato -> contrato.isBetweenFechas(date);
		return this.contratos.stream()
			.filter(Objects::nonNull)
			.filter(predicate)
			.collect(Collectors.toCollection(ArrayList::new));
	}

	//
	// Getters y setters
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	@NonNull
	List<Contrato> getContratos() {
		return new ArrayList<>(this.contratos);
	}

	@NonNull
	List<Contrato> _getContratos() {
		return this.contratos;
	}

	public void addContrato(final @NonNull Contrato contrato) {
		RelationalHelper.ContratarVigilante.link(this, contrato);
	}

}
