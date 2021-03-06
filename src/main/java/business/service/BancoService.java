package business.service;

import java.util.List;

import business.dao.BancoDAO;
import business.model.Banco;
import lombok.NonNull;

public class BancoService {

	@NonNull
	private final BancoDAO bancoDAO;


	public BancoService() {
		super();
		bancoDAO = new BancoDAO();
	}

	BancoService(@NonNull final BancoDAO bancoDAO) {
		super();
		this.bancoDAO = bancoDAO;
	}


	public List<Banco> findAll() {
		return bancoDAO.findAll();
	}

	public Banco findById(final Long id) {
		return bancoDAO.findOne(id).orElse(null);
	}

	public Banco findByCodigo(final String codigo) {
		return bancoDAO.findOneByCodigo(codigo).orElse(null);
	}

	public Banco insert(final Banco banco) {
		return bancoDAO.create(banco);
	}

	public void update(final Banco banco) {
		bancoDAO.update(banco);
	}

	public void delete(final Banco banco) {
		bancoDAO.delete(banco);
	}

}
