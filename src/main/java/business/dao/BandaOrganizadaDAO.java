package business.dao;

import java.util.List;

import business.model.BandaOrganizada;
import lombok.NonNull;

public class BandaOrganizadaDAO extends AbstractRepositoryDAO<BandaOrganizada> {

	@Override
	public @NonNull List<BandaOrganizada> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BandaOrganizada findById(final @NonNull Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BandaOrganizada insert(final @NonNull BandaOrganizada entity) {
		return runInTransaction(em -> {
			em.persist(entity);
			return entity;
		});
	}

	@Override
	public @NonNull BandaOrganizada update(final @NonNull BandaOrganizada entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(final @NonNull BandaOrganizada entity) {
		// TODO Auto-generated method stub

	}

}
