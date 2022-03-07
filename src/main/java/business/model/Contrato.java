package business.model;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

@Entity
@Table(name = "CONTRATO", indexes = {
		@Index(name = "CONTRATO__IDENTIFICADOR__UK", columnList = "IDENTIFICADOR", unique = true),
		@Index(name = "CONTRATO_UK_0", columnList = "ID_SUCURSAL, ID_VIGILANTE, FECHA_INICIO", unique = true),
		@Index(name = "CONTRATO_FECHAS_IX", columnList = "FECHA_INICIO, FECHA_FIN")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "CONTRATO__IDENTIFICADOR__UK", columnNames = { "IDENTIFICADOR" }),
		@UniqueConstraint(name = "CONTRATO_UK_0", columnNames = { "ID_SUCURSAL", "ID_VIGILANTE", "FECHA_INICIO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contrato implements IEntity<Long> {

	private static final long serialVersionUID = 8701897683893933707L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	@Column(name = "IDENTIFICADOR", nullable = false, length = 10)
	private String identificador;

	@NonNull
	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_INICIO", nullable = false)
	private Date fechaInicio;

	@Temporal(TemporalType.DATE)
	@Column(name = "FECHA_FIN", nullable = true)
	private Date fechaFin;

	@Column(name = "PERMISO_ARMAS", nullable = false, columnDefinition = "boolean default 0")
	private boolean permisoArmas;

	@ManyToOne
	@JoinColumn(name = "ID_SUCURSAL", nullable = false, foreignKey = @ForeignKey(name = "CONTRATO__SUCURSAL__FK"))
	private Sucursal sucursal;

	@ManyToOne
	@JoinColumn(name = "ID_VIGILANTE", nullable = false, foreignKey = @ForeignKey(name = "CONTRATO__VIGILANTE__FK"))
	private Vigilante vigilante;


	public Contrato(final @NonNull String identificador,
			final @NonNull Date fechaInicio, final Date fechaFin,
			boolean permisoArmas)
	{
		super();
		this.identificador = identificador;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.permisoArmas = permisoArmas;
	}


	//
	// Metodos heredados
	//



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

	public Long getVigilanteId() {
		Vigilante delegate = getVigilante();
		return delegate == null ? null : delegate.getId();
	}

	public String getVigilanteCodigo() {
		Vigilante delegate = getVigilante();
		return delegate == null ? null : delegate.getCodigo();
	}

	public boolean isBetweenFechas(final @NonNull Date date) {
		return this.fechaInicio.compareTo(date) <= 0 && (
				this.fechaFin == null ||
				this.fechaFin.compareTo(date) >= 0
			);
	}

	//
	// Getters y setters
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	public Date getFechaInicio() {
		return this.fechaInicio == null ? null : (Date) this.fechaInicio.clone();
	}

	public Date getFechaFin() {
		return this.fechaFin == null ? null : (Date) this.fechaFin.clone();
	}
}
