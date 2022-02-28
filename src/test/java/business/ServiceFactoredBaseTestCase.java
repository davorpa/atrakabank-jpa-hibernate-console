package business;

import org.junit.Before;
import org.mockito.InjectMocks;

import business.service.ServiceFactory;

public class ServiceFactoredBaseTestCase extends SampleBaseTestCase {

	@InjectMocks
	private ServiceFactory serviceFactory;

	@Before
	public void openMocks() {
		super.openMocks();
		ServiceFactory.setServiceFactory(serviceFactory);
	}

}
