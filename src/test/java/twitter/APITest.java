package twitter;

import static org.junit.Assert.*;

import org.junit.Test;

public class APITest {

	@Test
	public void BearerKey_test() {
		ApiMain api = new ApiMain();
		api.application_only_auth();
		String x = "AAAAAAAAAAAAAAAAAAAAAO17FwAAAAAAJOj0gHfNVU1B8ApcZ0w2V6Xn4gU%3DybtkGasAOkE4EEl5O1W77B3dLKuBQXqOs9ErfmXDtJvdKslg0z";
		// bearer key is constant for an application, so this test the bearer key is correct or not
		assertEquals(api.getBearer(),x);
	}

}
