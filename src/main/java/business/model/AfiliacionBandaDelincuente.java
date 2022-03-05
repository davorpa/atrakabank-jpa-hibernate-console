package business.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
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

/**
 * Entidad Afiliación entre Bandas Organizadas y Delincuentes.
 * <p>
 * Una banda organizada estará formada por 2 miembros como mínimo. Así mismo
 * un determinado delincuente podrá pertenecer a varios grupos criminales.
 */
@Entity
@Table(name = "AFILIACION_BANDA", indexes = {
		@Index(name = "AFILIACION_BANDA__PK",  columnList = "ID",                       unique = true),
		@Index(name = "AFILIACION_BANDA__UK0", columnList = "ID_BANDA, ID_DELINCUENTE", unique = true),
	}, uniqueConstraints = {
		@UniqueConstraint(name = "AFILIACION_BANDA__UK0", columnNames = { "ID_BANDA", "ID_DELINCUENTE" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class AfiliacionBandaDelincuente implements Serializable {

	private static final long serialVersionUID = 7421974046128067070L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "ID_BANDA", nullable = false, foreignKey = @ForeignKey(name = "AFILIACION_BANDA__BANDA__FK"))
	private BandaOrganizada bandaOrganizada;

	@ManyToOne
	@JoinColumn(name = "ID_DELINCUENTE", nullable = false, foreignKey = @ForeignKey(name = "AFILIACION_BANDA__DELINCUENTE__FK"))
	private Delincuente delincuente;


	/**
	 * Construye un nuevo registro de afiliación a partir de las entidades
	 * que forman su foreign key compuesta.
	 *
	 * @param bandaOrganizada nunca {@code null}.
	 * @param delincuente nunca {@code null}.
	 */
	public AfiliacionBandaDelincuente(final BandaOrganizada bandaOrganizada, final Delincuente delincuente) {
		super();
		Objects.requireNonNull(bandaOrganizada, "`bandaOrganizada` must be non-null");
		Objects.requireNonNull(delincuente, "`delincuente` must be non-null");
		this.delincuente = delincuente;
		this.bandaOrganizada = bandaOrganizada;
	}


	//
	// Metodos heredados
	//

	@Override
	public String toString() {
		return String.format("AfiliacionBandaDelincuente(id=%s, ids=%s, Banda(codigo=%s), Delincuente(codigo=%s, nombre=%s))",
				getId(), getClaveIds(), getBandaOrganizadaCodigo(), getDelincuenteCodigo(), getDelincuenteNombre());
	}

	/**
	 * Dos objetos son parecidos si siendo del mismo tipo,
	 * tienen la misma clave natural.
	 * @param obj el objeto a comparar
	 * @return
	 * @see #getBandaOrganizada()
	 * @see #getDelincuente()
	 */
	public boolean same(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AfiliacionBandaDelincuente other = (AfiliacionBandaDelincuente) obj;
		return same(other.getBandaOrganizada(), other.getDelincuente());
	}

	/**
	 * Dos objetos son parecidos si siendo del mismo tipo,
	 * tienen la misma clave natural.
	 * @param obj el objeto a comparar
	 * @return
	 * @see #getBandaOrganizada()
	 * @see #getDelincuente()
	 */
	public boolean same(final BandaOrganizada bandaOrganizada, final Delincuente delincuente) {
		return Objects.equals(getBandaOrganizada(), bandaOrganizada) &&
				Objects.equals(getDelincuente(), delincuente);
	}

	//
	// Getters y Setters
	//



	//
	// Metodos delegados
	//

	/**
	 * @return {@code null} si no esta seteado la banda organizada o su clave artificial.
	 * @see BandaOrganizada#getId()
	 */
	public Long getBandaOrganizadaId() {
		BandaOrganizada delegate = getBandaOrganizada();
		return delegate == null ? null : delegate.getId();
	}

	/**
	 * @return {@code null} si no esta seteado la banda organizada o su clave natural.
	 * @see BandaOrganizada#getCodigo()
	 */
	public String getBandaOrganizadaCodigo() {
		BandaOrganizada delegate = getBandaOrganizada();
		return delegate == null ? null : delegate.getCodigo();
	}

	/**
	 * @return {@code null} si no esta seteado el delincuente o su clave artificial.
	 * @see Delincuente#getId()
	 */
	public Long getDelincuenteId() {
		Delincuente delegate = getDelincuente();
		return delegate == null ? null : delegate.getId();
	}

	/**
	 * @return {@code null} si no esta seteado el delincuente o su clave natural.
	 * @see Delincuente#getIdentificador()
	 */
	public String getDelincuenteCodigo() {
		Delincuente delegate = getDelincuente();
		return delegate == null ? null : delegate.getIdentificador();
	}

	/**
	 * @return {@code null} si no esta seteado el delincuente o su nombre.
	 * @see Delincuente#getNombre()
	 */
	public String getDelincuenteNombre() {
		Delincuente delegate = getDelincuente();
		return delegate == null ? null : delegate.getNombre();
	}


	/**
	 * @return las claves artificiales de la foreign key compuesta
	 * 		que identifican inequívocamente al registro de afiliación.
	 * @see #getBandaOrganizadaId()
	 * @see #getDelincuenteId()
	 */
	@NonNull
	public String getClaveIds() {
		return String.format("%s-%s", getBandaOrganizadaId(), getDelincuenteId());
	}

	/**
	 * @return las claves naturales de la foreign key compuesta
	 * 		que identifican inequívocamente al registro de afiliación.
	 * @see #getBandaOrganizadaId()
	 * @see #getDelincuenteId()
	 */
	@NonNull
	public String getClaveCodigos() {
		return String.format("%s//%s", getBandaOrganizadaCodigo(), getDelincuenteCodigo());
	}
}
