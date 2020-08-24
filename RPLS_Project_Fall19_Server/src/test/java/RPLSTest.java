import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.Serializable;
import java.util.function.Consumer;

class RPLSTest {

	GameInfo gameInfo;
	Server theServer;

	@BeforeEach
	void setUp(){
		Consumer<Serializable> call = new Consumer<Serializable>() {
			@Override
			public void accept(Serializable serializable) {
				System.out.println("Hello!");
			}
		};
		gameInfo = new GameInfo();
		theServer = new Server(call);
	}

	@Test
	void testServerClass() {
		assertEquals("Server", theServer.getClass().getName(), "Server construction is not correct");
	}

	@Test
	void testGameInfoClass() {
		assertEquals("GameInfo", gameInfo.getClass().getName(), "GameInfo construction is not correct");
	}

	@Test
	void testGameInfoResetInfo(){
		gameInfo.setTextCommunication("Test 1");
		gameInfo.setP1Plays("Test 2");
		gameInfo.setP2Plays("Test 3");
		gameInfo.setP1Points(4);
		gameInfo.setP2Points(5);
		gameInfo.setPlayerChoice("Test 6");
		gameInfo.resetInfo();

		boolean result = gameInfo.getTextCommunication().equals("") &&
				gameInfo.getPlayerChoice().equals("") && gameInfo.getP1Plays().equals("")
				&& gameInfo.getP2Plays().equals("") && gameInfo.getP1Points() == 0 &&
				gameInfo.getP2Points() == 0;

		assertTrue(result, "Info not being reset correctly");
	}

}
