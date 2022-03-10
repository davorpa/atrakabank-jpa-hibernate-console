package business.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@Entity(name = "Administrativo")
@Table(name = "ADMINISTRATIVO", indexes = {
		@Index(name = "ADMINISTRATIVO__NUSS__UK", columnList = "NUSS", unique = true)
	}, uniqueConstraints = {
		@UniqueConstraint(name = "ADMINISTRATIVO__NUSS__UK", columnNames = { "NUSS" })
	})
@PrimaryKeyJoinColumn(name = "ID", columnDefinition = "bigint unsigned", foreignKey = @ForeignKey(name = "ADMINISTRATIVO__EMPLEADO__FK"))
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@SuperBuilder
public class Administrativo extends Empleado {

	private static final long serialVersionUID = -2610909158122484193L;

	//
	// Campos
	//

	@NonNull
	@Column(name = "NUSS", nullable = false, length = 18)
	private String NUSS;

	@NonNull
	@Column(name = "IBAN", nullable = false, length = 24)
	private String IBAN;

	//
	// Constructores
	//

	public Administrativo(
			final @NonNull String DNI,
			final @NonNull String nombre, final @NonNull String apellido,
			final @NonNull Banco banco,
			final @NonNull String NUSS, final @NonNull String IBAN) {
		super(DNI, nombre, apellido, banco);
		this.NUSS = NUSS;
		this.IBAN = IBAN;
	}

	//
	// Metodos heredados
	//

	@Override
	public String toString() {
		return String.format("Administrativo(DNI=%s, nombre=%s, banco=%s, NUSS=%s, IBAN=%s)",
				getDNI(), getNombreCompleto(), getBancoCodigo(), getNUSS(), getIBAN());
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
