package business.service;

import java.util.List;

import business.dao.JuezDAO;
import business.model.Juez;
import lombok.NonNull;

public class JuezService {

	@NonNull
	private JuezDAO juezDAO;


	public JuezService() {
		super();
		juezDAO = new JuezDAO();
	}

	JuezService(@NonNull final JuezDAO juezDAO) {
		super();
		this.juezDAO = juezDAO;
	}


	public List<Juez> findAll() {
		return juezDAO.findAll();
	}

	public Juez findById(final Long id) {
		return juezDAO.findOne(id).orElse(null);
	}

	public Juez insert(final Juez juez) {
		return juezDAO.create(juez);
	}

	public void update(final Juez juez) {
		juezDAO.update(juez);
	}

	public void delete(final Juez juez) {
		juezDAO.delete(juez);
	}

}
