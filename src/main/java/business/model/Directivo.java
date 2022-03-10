package business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import business.model.tipos.CargoDirectivo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Entity(name = "Directivo")
@Table(name = "DIRECTIVO")
@PrimaryKeyJoinColumn(name = "ID", columnDefinition = "bigint unsigned", foreignKey = @ForeignKey(name = "DIRECTIVO__EMPLEADO__FK"))
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder
public class Directivo extends Empleado {

	private static final long serialVersionUID = -2610909158122484193L;

	@NonNull
	@Column(name = "NIVEL", nullable = false, columnDefinition = "tinyint unsigned default 1")
	private Byte nivel;

	@NonNull
	@Enumerated(EnumType.STRING)
	@Column(name = "CARGO", nullable = false, length = 54)
	private CargoDirectivo cargo;


	public Directivo(
			final @NonNull String DNI,
			final @NonNull String nombre, final @NonNull String apellido,
			final @NonNull Banco banco,
			final @NonNull Byte nivel, final @NonNull CargoDirectivo cargo) {
		super(DNI, nombre, apellido, banco);
		this.nivel = nivel;
		this.cargo = cargo;
	}

	//
	// Metodos heredados
	//

	@Override
	public String toString() {
		return String.format("Directivo(DNI=%s, nombre=%s, banco=%s, nivel=%s, cargo=%s)",
				getDNI(), getNombreCompleto(), getBancoCodigo(), getNivel(), getCargo());
	}

	//
	// Metodos delegados
	//



	//
	// Getters y setters
	//



	//
	// Mantener la inmutabilidad y bidireccionalidad de las relaciones y value types
	//

}
