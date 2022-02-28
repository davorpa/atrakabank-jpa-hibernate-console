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
		return sucursalDAO.findById(id);
	}

	public List<Sucursal> findByCodigoBanco(String codigo) {
		return sucursalDAO.findByCodigoBanco(codigo);
	}

	public Sucursal findByCodigo(String codigo) {
		return sucursalDAO.findByCodigo(codigo);
	}

	public Sucursal insert(Sucursal sucursal) {
		return sucursalDAO.insert(sucursal);
	}

	public void update(Sucursal sucursal) {
		sucursalDAO.update(sucursal);
	}

	public void delete(Sucursal sucursal) {
		sucursalDAO.remove(sucursal);
	}

}
