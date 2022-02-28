package business;

import org.junit.After;
import org.junit.Before;
import org.mockito.MockitoAnnotations;

public class SampleBaseTestCase {

	private AutoCloseable closeable;

	@Before
	public void openMocks() {
		closeable = MockitoAnnotations.openMocks(this);
	}

	@After
	public void releaseMocks() throws Exception {
		closeable.close();
	}

}
