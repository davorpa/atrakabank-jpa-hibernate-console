package business.model;

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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@Entity
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
		return String.format("Banco [id=%s, codigo=%s, sede=%s, numSucursales=%s, sucursales=%s]",
				id, codigo, sede, numSucursales, sucursales);
	}

	//
	// Metodos delegados
	//

	public long getNumSucursales() {
		return numSucursales;
		//return sucursales == null ? 0 : sucursales.size();
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

}
