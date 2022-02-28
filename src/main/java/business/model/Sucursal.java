package business.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "SUCURSAL", indexes = {
		@Index(name = "SUCURSAL__CODIGO__UK", columnList = "CODIGO", unique = true),
		@Index(name = "SUCURSAL__CIUDAD__IX", columnList = "CIUDAD"),
		@Index(name = "SUCURSAL__NTRABAJS__IX", columnList = "NUM_TRABAJADORES")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "SUCURSAL__CODIGO__UK", columnNames = { "CODIGO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Sucursal implements Serializable {

	private static final long serialVersionUID = -4347117274148071411L;


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
	@Column(name = "CIUDAD", nullable = false, length = 50)
	private String ciudad;

	@NonNull
	@Column(name = "DIRECCION", nullable = false, length = 100)
	private String direccion;

	@NonNull
	@Column(name = "NOMBRE_DIRECTOR", nullable = false, length = 100)
	private String nombreDirector;

	@Column(name = "NUM_TRABAJADORES", nullable = false, columnDefinition = "int unsigned default 0")
	private int numTrabajadores;

	@ToString.Exclude
	@ManyToOne
	@JoinColumn(name = "ID_BANCO", nullable = false, foreignKey = @ForeignKey(name = "SUCURSAL__BANCO__FK"))
	private Banco banco;

	@ToString.Exclude
	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "sucursal")
	private List<Atraco> atracos = new ArrayList<>();

	@ToString.Exclude
	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "sucursal")
	private List<Contrato> contratos = new ArrayList<>();


	public Sucursal(final @NonNull String codigo,
			final @NonNull String ciudad, final @NonNull String direccion,
			final @NonNull String nombreDirector, final int numTrabajadores) {
		super();
		this.codigo = codigo;
		this.ciudad = ciudad;
		this.direccion = direccion;
		this.nombreDirector = nombreDirector;
		this.numTrabajadores = numTrabajadores;
	}


	//
	// Metodos heredados
	//



	//
	// Metodos delegados
	//

	public Long getBancoId() {
		Banco delegate = getBanco();
		return delegate == null ? null : delegate.getId();
	}

	public String getBancoCodigo() {
		Banco delegate = getBanco();
		return delegate == null ? null : delegate.getCodigo();
	}

	public long getNumAtracos() {
		return atracos == null ? 0 : atracos.size();
	}

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
	public List<Atraco> getAtracos() {
		return new ArrayList<>(this.atracos);
	}

	@NonNull
	public List<Contrato> getContratos() {
		return new ArrayList<>(this.contratos);
	}

	@NonNull
	List<Atraco> _getAtracos() {
		return this.atracos;
	}

	@NonNull
	List<Contrato> _getContratos() {
		return this.contratos;
	}

	public void addAtraco(final @NonNull Atraco atraco) {
		RelationalHelper.SucursalAtracada.link(this, atraco);
	}

	public void addContrato(final @NonNull Contrato contrato) {
		RelationalHelper.AsignarVigilancia.link(this, contrato);
	}

}
