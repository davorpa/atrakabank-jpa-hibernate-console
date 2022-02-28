package business.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import business.model.tipos.TipoCondena;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "ATRACO")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Atraco implements Serializable {

	private static final long serialVersionUID = 5807833637568906534L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA", nullable = false)
	private Date fecha;

	@Enumerated(EnumType.STRING)
	@Column(name = "TIPO_CONDENA", nullable = true, length = 100)
	private TipoCondena tipoCondena;

	@ManyToOne
	@JoinColumn(name = "ID_SUCURSAL", nullable = false, foreignKey = @ForeignKey(name = "ATRACO__SUCURSAL__FK"))
	private Sucursal sucursal;

	@ManyToOne
	@JoinColumn(name = "ID_DELINCUENTE", nullable = false, foreignKey = @ForeignKey(name = "ATRACO__DELINCUENTE__FK"))
	private Delincuente delincuente;

	@ManyToOne(optional = true)
	@JoinColumn(name = "ID_JUEZ", nullable = true, foreignKey = @ForeignKey(name = "ATRACO__JUEZ__FK"))
	private Juez juez;


	public Atraco(final @NonNull Date fecha) {
		super();
		this.fecha = fecha;
	}


	//
	// Metodos heredados
	//

	@Override
	public String toString() {
		return String.format("Atraco [id=%s, fecha=%s, sucursal=%s, delincuente=%s, tipoCondena=%s]",
				id, fecha, getSucursalCodigo(), getDelincuenteNombre(), tipoCondena);
	}

	//
	// Metodos delegados
	//

	public Long getSucursalId() {
		Sucursal delegate = getSucursal();
		return delegate == null ? null : delegate.getId();
	}

	public String getSucursalCodigo() {
		Sucursal delegate = getSucursal();
		return delegate == null ? null : delegate.getCodigo();
	}

	public Long getDelincuenteId() {
		Delincuente delegate = getDelincuente();
		return delegate == null ? null : delegate.getId();
	}

	public String getDelincuenteIdentificador() {
		Delincuente delegate = getDelincuente();
		return delegate == null ? null : delegate.getIdentificador();
	}

	public String getDelincuenteNombre() {
		Delincuente delegate = getDelincuente();
		return delegate == null ? null : delegate.getNombre();
	}

	public Long getJuezId() {
		Juez delegate = getJuez();
		return delegate == null ? null : delegate.getId();
	}

	public String getJuezCodigo() {
		Juez delegate = getJuez();
		return delegate == null ? null : delegate.getCodigo();
	}

	public String getJuezNombre() {
		Juez delegate = getJuez();
		return delegate == null ? null : delegate.getNombre();
	}

	public boolean isBetween(final Date start, final Date end) {
		if (start == null && end == null) {
			throw new NullPointerException(String.format(
					"One dates between, `start`(%s) or `end`(%s), should be at least non-null",
					start, end));
		}
		Objects.requireNonNull(this.fecha, "Atraco date `fecha` should be non-null");
		if (start == null) {
			return end.compareTo(this.fecha) >= 0;
		}
		if (end == null) {
			return start.compareTo(this.fecha) <= 0;
		}
		return this.fecha.compareTo(start) >= 0 && this.fecha.compareTo(end) <= 0;
	}

	//
	// Getters y setters
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	public Date getFecha() {
		return this.fecha == null ? null : (Date) this.fecha.clone();
	}

}
