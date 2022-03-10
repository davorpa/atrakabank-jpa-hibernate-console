package business.model;

import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import util.Strings;

@Entity(name = "Empleado")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "EMPLEADO", indexes = {
		@Index(name = "EMPLEADO__DNI__UK", columnList = "DNI", unique = true)
	}, uniqueConstraints = {
		@UniqueConstraint(name = "EMPLEADO__DNI__UK", columnNames = { "DNI" })
	})
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuperBuilder
public abstract class Empleado implements IEntity<Long> {

	private static final long serialVersionUID = -3211514238829002343L;

	//
	// Campos
	//

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@ToString.Exclude
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	@EqualsAndHashCode.Include(rank = 999)
	@Column(name = "DNI", nullable = false, length = 12)
	private String DNI;

	@NonNull
	@Column(name = "NOMBRE", nullable = false, length = 42)
	private String nombre;

	@NonNull
	@Column(name = "APELLIDO", nullable = false, length = 120)
	private String apellido;

	@NonNull
	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "BANCO_ID", nullable = false, foreignKey = @ForeignKey(name = "EMPLEADO__BANCO__FK"))
	private Banco banco;

	//
	// Constructores
	//

	protected Empleado(
			final @NonNull String DNI,
			final @NonNull String nombre, final @NonNull String apellido,
			final @NonNull Banco banco) {
		super();
		this.DNI = DNI;
		this.nombre = nombre;
		this.apellido = apellido;
		this.banco = banco;
		banco.addEmpleado(this);
	}

	//
	// Metodos heredados
	//

	@Override
	public String toString() {
		return String.format("Empleado(DNI=%s, nombre=%s, apellido=%s, banco=%s)",
				getDNI(), getNombre(), getApellido(), getBancoCodigo());
	}

	//
	// Metodos delegados
	//

	/**
	 *
	 * @return
	 * @see #getBanco()
	 * @see Banco#getId()
	 */
	public Long getBancoId() {
		Banco delegate = getBanco();
		return delegate == null ? null : delegate.getId();
	}

	/**
	 *
	 * @return
	 * @see #getBanco()
	 * @see Banco#getCodigo()
	 */
	public String getBancoCodigo() {
		Banco delegate = getBanco();
		return delegate == null ? null : delegate.getCodigo();
	}

	/**
	 * Concatena {@link #getNombre()} y {@link #getApellido()} para formar el
	 * nombre completo del empleado.
	 * @return
	 */
	public String getNombreCompleto() {
		return Stream.of(getNombre(), getApellido())
				// filter and transform: trimToNull
				.map(Strings::trimToNull)
				.filter(Objects::nonNull)
				// join
				.collect(Collectors.joining(Strings.SPACE));
	}

	//
	// Getters y setters
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	void _setBanco(final Banco banco) {
		this.banco = banco;
	}

}
