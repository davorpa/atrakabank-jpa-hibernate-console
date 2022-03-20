package business.model;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import business.model.RelationalHelper.EmpleadoBanco;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "Banco")
@Table(name = "BANCO", indexes = {
		@Index(name = "BANCO__CODIGO__UK", columnList = "CODIGO", unique = true),
		@Index(name = "BANCO__NSUCURS__IX", columnList = "NUM_SUCURSALES")
	}, uniqueConstraints = {
		@UniqueConstraint(name = "BANCO__CODIGO__UK", columnNames = { "CODIGO" })
	})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Banco implements IEntity<Long> {

	private static final long serialVersionUID = -5303145511746498228L;


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
	@Column(name = "SEDE", nullable = false, length = 50)
	private String sede;

	@Column(name = "NUM_SUCURSALES", nullable = false, columnDefinition = "int unsigned default 0")
	private int numSucursales;

	@ToString.Exclude
	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "banco")
	private List<Sucursal> sucursales = new ArrayList<>();

	@ToString.Exclude
	@Getter(AccessLevel.NONE)
	@OneToMany(mappedBy = "banco", fetch = FetchType.LAZY,
			cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@OrderBy("dni ASC, id ASC")
	private Set<Empleado> empleados;


	public Banco(final @NonNull String codigo, final @NonNull String sede, final int numSucursales) {
		super();
		this.codigo = codigo;
		this.sede = sede;
		this.numSucursales = numSucursales;
	}


	//
	// Metodos heredados
	//

	@Override
	public String toString() {
		return String.format("Banco(codigo=%s, sede=%s, numSucursales=%s, numEmpleados=%s)",
				getCodigo(), getSede(), getNumSucursales(), getNumEmpleados());
	}

	//
	// Metodos delegados
	//

	public long getNumSucursales() {
		return numSucursales;
		//return sucursales == null ? 0 : sucursales.size();
	}

	public long getNumEmpleados() {
		return empleados == null ? 0 : empleados.size();
	}

	//
	// Getters y setters
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

	@NonNull
	public List<Sucursal> getSucursales() {
		return new ArrayList<>(this.sucursales);
	}

	/**
	 * Devuelve las sucursales
	 * @return
	 */
	@NonNull
	List<Sucursal> _getSucursales() {
		return sucursales;
	}

	public void setSucursales(final @NonNull List<Sucursal> sucursales) {
		this.sucursales = sucursales;
		this.numSucursales = sucursales.size();
	}

	public void addSucursal(final @NonNull Sucursal sucursal) {
		RelationalHelper.BancoSucursal.link(this, sucursal);
		this.numSucursales++;
	}

	public void removeSucursal(final @NonNull Sucursal sucursal) {
		RelationalHelper.BancoSucursal.unlink(this, sucursal);
		this.numSucursales--;
	}

	/**
	 * Obtiene los empleados en plantilla.
	 *
	 * @return nunca {@code null}.
	 */
	@NonNull
	Set<Empleado> _getEmpleados() {
		if (this.empleados == null) {
			this.empleados = new LinkedHashSet<>();
		}
		return this.empleados;
	}

	/**
	 * Obtiene los empleados en plantilla.
	 *
	 * @return nunca {@code null}.
	 */
	@NonNull
	public Set<Empleado> getEmpleados() {
		return new LinkedHashSet<>(_getEmpleados());
	}

	/**
	 * Establece los empleados en plantilla.
	 *
	 * @param empleados nunca {@code null}.
	 */
	public void setEmpleados(final @NonNull Set<Empleado> empleados) {
		this.empleados = empleados;
	}

	/**
	 * Agrega un nuevo empleado a plantilla.
	 *
	 * @param empleado
	 * @see EmpleadoBanco#link(Banco, Empleado)
	 */
	public void addEmpleado(final @NonNull Empleado empleado) {
		RelationalHelper.EmpleadoBanco.link(this, empleado);
	}

	/**
	 * Elimina por completo un empleado registrado en plantilla.
	 *
	 * @param empleado
	 * @see EmpleadoBanco#link(Banco, Empleado)
	 */
	public void removeEmpleado(final @NonNull Empleado empleado) {
		RelationalHelper.EmpleadoBanco.unlink(this, empleado);
	}

}
