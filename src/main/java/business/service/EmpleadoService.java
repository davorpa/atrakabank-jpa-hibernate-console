package business.service;

import java.util.List;
import java.util.Optional;

import business.dao.EmpleadoDAO;
import business.model.Empleado;
import business.model.tipos.CargoDirectivo;
import lombok.NonNull;

public class EmpleadoService {

	private EmpleadoDAO empleadoDAO;

	public EmpleadoService() {
		super();
		empleadoDAO = new EmpleadoDAO();
	}

	@NonNull
	public List<Empleado> findAll() {
		return empleadoDAO.findAll();
	}

	public Empleado findById(final Long id) {
		return empleadoDAO.findOne(id).orElse(null);
	}

	public @NonNull Empleado insert(final Empleado empleado) {
		return empleadoDAO.create(empleado);
	}

	public @NonNull Empleado update(final Empleado empleado) {
		return empleadoDAO.update(empleado);
	}

	public void delete(final Empleado empleado) {
		empleadoDAO.delete(empleado);
	}

	public @NonNull Optional<Empleado> findByDNI(final String dni) {
		return empleadoDAO.findByDNI(dni);
	}

	public @NonNull List<Empleado> findAllByBancoCodigo(final String codigo) {
		return empleadoDAO.findAllByBancoCodigo(codigo);
	}

	public List<Empleado> findAllByCargo(final CargoDirectivo cargo) {
		if (cargo == null) {
			return empleadoDAO.findAll();
		}
		return empleadoDAO.findAllByCargo(cargo);
	}

}
