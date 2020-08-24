import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.Serializable;
import java.util.function.Consumer;

class RPLSTest {

	Client theClient;

	@BeforeEach
	void setUp(){
		Consumer<Serializable> call = new Consumer<Serializable>() {
			@Override
			public void accept(Serializable serializable) {
				System.out.println("Hello!");
			}
		};
		theClient = new Client(call);
	}

	@Test
	void testClientClass() {
		assertEquals("Client", theClient.getClass().getName(), "Client construction is not correct");
	}

	// GameInfo tests are on Server test cases file

}
