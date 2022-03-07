package business.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import business.model.RelationalHelper.Afiliarse;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "DELINCUENTE", indexes = {
		@Index(name = "DELINCUENTE__IDENTIFICADOR__UK", columnList = "IDENTIFICADOR", unique = true),
		@Index(name = "DELINCUENTE__NOMBRE__IX", columnList = "NOMBRE")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "DELINCUENTE__IDENTIFICADOR__UK", columnNames = { "IDENTIFICADOR" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, doNotUseGetters = false)
public class Delincuente implements IEntity<Long> {

	private static final long serialVersionUID = -7087331123973362373L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@ToString.Exclude
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	@Column(name = "IDENTIFICADOR", nullable = false, length = 10)
	private String identificador;

	@NonNull
	@Column(name = "NOMBRE", nullable = false, length = 100)
	private String nombre;

	@OneToMany(mappedBy = "delincuente", fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<AfiliacionBandaDelincuente> afiliaciones;

	@ToString.Exclude
	@OneToMany(mappedBy = "delincuente")
	private List<Atraco> atracos = new ArrayList<>();


	public Delincuente(final @NonNull String identificador, final @NonNull String nombre) {
		super();
		this.identificador = identificador;
		this.nombre = nombre;
	}

	//
	// Metodos heredados
	//

	@Override
	public String toString() {
		return String.format("Delincuente(codigo=%s, nombre=%s, afiliaciones=%s)",
				getIdentificador(), getNombre(), getBandasOrganizadas().size());
	}

	/**
	 * Dos objetos son parecidos si siendo del mismo tipo,
	 * tienen la misma clave natural.
	 * @param obj el objeto a comparar
	 * @return
	 * @see #getIdentificador()
	 */
	public boolean same(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Delincuente other = (Delincuente) obj;
		return Objects.equals(getIdentificador(), other.getIdentificador());
	}

	//
	// Metodos delegados
	//

	public long getNumAtracos() {
		return atracos == null ? 0 : atracos.size();
	}

	/**
	 * Obtiene las bandas organizadas a las que pertenece / está afiliado.
	 *
	 * @return nunca {@code null}.
	 */
	@NonNull
	public Set<BandaOrganizada> getBandasOrganizadas() {
		return _getAfiliaciones().stream()
				.filter(Objects::nonNull)
				.map(AfiliacionBandaDelincuente::getBandaOrganizada)
				.filter(Objects::nonNull)
				.collect(Collectors.toCollection(LinkedHashSet::new));
	}

	/**
	 * Busca una afiliacion por su clave natural dentro de su registro de afiliaciones.
	 *
	 * @param search registro a buscar
	 * @return {@code null} si no hay resultados.
	 * @see AfiliacionBandaDelincuente#same
	 */
	public AfiliacionBandaDelincuente findAfiliacion(
			final AfiliacionBandaDelincuente search)
	{
		for (final AfiliacionBandaDelincuente afiliacion : _getAfiliaciones()) {
			if (afiliacion.same(search)) {
				return afiliacion;
			}
		}
		return null;
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
		RelationalHelper.Delinquir.link(this, atraco);
	}

	/**
	 * Obtiene las afiliaciones registradas.
	 *
	 * @return nunca {@code null}.
	 */
	Set<AfiliacionBandaDelincuente> _getAfiliaciones() {
		if (afiliaciones == null) {
			this.afiliaciones = new LinkedHashSet<>();
		}
		return this.afiliaciones;
	}

	/**
	 * Obtiene las afiliaciones registradas.
	 *
	 * @return nunca {@code null}.
	 */
	public Set<AfiliacionBandaDelincuente> getAfiliaciones() {
		return new LinkedHashSet<>(_getAfiliaciones());
	}

	/**
	 * Establece las afiliaciones a registrar.
	 *
	 * @param afiliaciones la colección a establecer
	 */
	public void setAfiliaciones(Set<AfiliacionBandaDelincuente> afiliaciones) {
		this.afiliaciones = afiliaciones;
	}

	/**
	 * Agrega una nueva banda organizada a su registro de afiliaciones.
	 *
	 * @param bandaOrganizada nunca {@code null}.
	 * @return el registro de afiliación con los datos seteados, nunca {@code null}.
	 * @see Afiliarse#link(BandaOrganizada, Delincuente)
	 */
	public AfiliacionBandaDelincuente addBandaOrganizada(final BandaOrganizada bandaOrganizada) {
		return RelationalHelper.Afiliarse.link(bandaOrganizada, this);
	}

	/**
	 * Elimina por completo una banda organizada de su registro de afiliaciones.
	 *
	 * @param bandaOrganizada nunca {@code null}.
	 * @return {@code true} si la estructura de datos ha cambiado debido a
	 *         dicha operación.
	 * @see Afiliarse#unlink(BandaOrganizada, Delincuente)
	 */
	public boolean removeBandaOrganizada(final BandaOrganizada bandaOrganizada) {
		return RelationalHelper.Afiliarse.unlink(bandaOrganizada, this);
	}

}
