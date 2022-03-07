package business.model;

import java.util.LinkedHashSet;
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
public class BandaOrganizada implements IEntity<Long> {

	private static final long serialVersionUID = -5644197328475561675L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, insertable = false, updatable = false, columnDefinition = "bigint unsigned")
	@EqualsAndHashCode.Include
	@ToString.Exclude
	@Setter(AccessLevel.PACKAGE)
	private Long id;

	@NonNull
	@Column(name = "CODIGO", nullable = false, length = 10)
	private String codigo;

	@Column(name = "NUM_MIEMBROS", nullable = false, columnDefinition = "int unsigned default 0")
	private int numMiembros;

	@ToString.Exclude
	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "bandaOrganizada", fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Set<AfiliacionBandaDelincuente> afiliaciones;


	public BandaOrganizada(final @NonNull String codigo, final int numMiembros) {
		super();
		this.codigo = codigo;
		this.numMiembros = numMiembros;
	}


	//
	// Metodos heredados
	//

	@Override
	public String toString() {
		return String.format("BandaOrganizada(codigo=%s, numMiembros=%s, afiliaciones=%s)",
				getCodigo(), getNumMiembros(), getDelincuentes().size());
	}

	/**
	 * Dos objetos son parecidos si siendo del mismo tipo,
	 * tienen la misma clave natural.
	 * @param obj el objeto a comparar
	 * @return
	 * @see #getCodigo()
	 */
	public boolean same(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BandaOrganizada other = (BandaOrganizada) obj;
		return Objects.equals(getCodigo(), other.getCodigo());
	}

	//
	// Metodos delegados
	//

	/**
	 * Obtiene los delincuentes que se han afiliado como miembros.
	 *
	 * @return nunca {@code null}.
	 */
	@NonNull
	public Set<Delincuente> getDelincuentes() {
		return _getAfiliaciones().stream()
				.filter(Objects::nonNull)
				.map(AfiliacionBandaDelincuente::getDelincuente)
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
	 * Obtiene las afiliaciones registradas.
	 *
	 * @return nunca {@code null}.
	 */
	public void setAfiliaciones(final Set<AfiliacionBandaDelincuente> afiliaciones) {
		this.afiliaciones = afiliaciones;
		this.numMiembros = getDelincuentes().size();
	}

	/**
	 * Agrega un nuevo delincuente a su registro de afiliaciones.
	 *
	 * @param delincuente nunca {@code null}.
	 * @return el registro de afiliación con los datos seteados, nunca {@code null}.
	 * @see Afiliarse#link(BandaOrganizada, Delincuente)
	 */
	public AfiliacionBandaDelincuente addDelincuente(final Delincuente delincuente) {
		AfiliacionBandaDelincuente afiliacion = RelationalHelper.Afiliarse.link(this, delincuente);
		this.numMiembros++;
		return afiliacion;
	}

	/**
	 * Elimina por completo un delincuente de su registro de afiliaciones.
	 *
	 * @param delincuente nunca {@code null}.
	 * @return {@code true} si la estructura de datos ha cambiado debido a
	 *         dicha operación.
	 * @see Afiliarse#unlink(BandaOrganizada, Delincuente)
	 */
	public boolean removeDelincuente(final Delincuente delincuente) {
		boolean dirty = RelationalHelper.Afiliarse.unlink(this, delincuente);
		if (dirty) {
			this.numMiembros--;
		}
		return dirty;
	}

}
