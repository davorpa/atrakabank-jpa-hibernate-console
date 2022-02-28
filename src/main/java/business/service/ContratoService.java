package business.service;

import java.util.List;

import business.dao.ContratoDAO;
import business.model.Contrato;
import lombok.NonNull;

public class ContratoService {

	@NonNull
	private ContratoDAO contratoDAO;


	public ContratoService() {
		super();
		contratoDAO = new ContratoDAO();
	}

	ContratoService(@NonNull final ContratoDAO contratoDAO) {
		super();
		this.contratoDAO = contratoDAO;
	}


	public List<Contrato> findAll() {
		return contratoDAO.findAll();
	}

	public Contrato findById(final Long idContrato) {
		return contratoDAO.findById(idContrato);
	}

	public Contrato insert(final Contrato contrato) {
		return contratoDAO.insert(contrato);
	}

	public void update(final Contrato contrato) {
		contratoDAO.update(contrato);
	}

	public void delete(final Contrato contrato) {
		contratoDAO.remove(contrato);
	}

}
