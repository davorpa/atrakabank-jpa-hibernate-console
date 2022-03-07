package business.service;

import java.util.List;

import business.dao.SucursalDAO;
import business.model.Sucursal;
import lombok.NonNull;

public class SucursalService {

	@NonNull
	private SucursalDAO sucursalDAO;


	public SucursalService() {
		super();
		sucursalDAO = new SucursalDAO();
	}

	SucursalService(@NonNull final SucursalDAO sucursalDAO) {
		super();
		this.sucursalDAO = sucursalDAO;
	}


	public List<Sucursal> findAll() {
		return sucursalDAO.findAll();
	}

	public Sucursal findById(Long id) {
		return sucursalDAO.findOne(id).orElse(null);
	}

	public List<Sucursal> findAllByCodigoBanco(String codigo) {
		return sucursalDAO.findAllByCodigoBanco(codigo);
	}

	public Sucursal findByCodigo(String codigo) {
		return sucursalDAO.findOneByCodigo(codigo).orElse(null);
	}

	public Sucursal insert(Sucursal sucursal) {
		return sucursalDAO.create(sucursal);
	}

	public Sucursal update(Sucursal sucursal) {
		return sucursalDAO.update(sucursal);
	}

	public void delete(Sucursal sucursal) {
		sucursalDAO.delete(sucursal);
	}

}
