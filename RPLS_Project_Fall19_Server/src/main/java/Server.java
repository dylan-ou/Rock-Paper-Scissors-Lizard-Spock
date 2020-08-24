import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.regex.PatternSyntaxException;

import javafx.application.Platform;
import javafx.scene.control.ListView;


public class Server{

    int count = 1;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>();
    TheServer server;
    private Consumer<Serializable> callback;

    Server(Consumer<Serializable> call){
        callback = call;
        server = new TheServer();
        server.start();
    }

    public class TheServer extends Thread{
        public void run() {
            try(ServerSocket mySocket = new ServerSocket(5555);){
                System.out.println("Server is waiting for a client!");
                callback.accept("Server is waiting for a client!");
                while(true) {
                    ClientThread c = new ClientThread(mySocket.accept(), count);
                    callback.accept("Client has connected to server: " + "Client #" + count);
                    clients.add(c);
                    c.start();
                    count++;
                }
            } //end of try
            catch(Exception e) {
                callback.accept("Server socket did not launch");
            }
        } //end of while
    }

    class ClientThread extends Thread{
        Socket connection;
        int count;
        Boolean inGame;
        ClientThread opponent;
        ObjectInputStream in;
        ObjectOutputStream out;
        String choice;

        GameInfo gameObj;

        ClientThread(Socket s, int count){
            this.connection = s;
            this.count = count;
            this.inGame = false;
            this.opponent = null;
            this.choice = "";
        }

        ClientThread getClient(int count){
            ClientThread result = clients.get(0);
            for(int i=0;i<clients.size();i++){
                if(clients.get(i).count == count){
                    result = clients.get(i);
                }
            }

            return result;
        }

        public synchronized void updateClients(GameInfo g) {
            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                try {
                    t.out.reset();
                    t.out.writeObject(g);
                }
                catch(Exception e) {}
            }
        }

        public synchronized void run(){
            ClientThread firstPlayer = null, secondPlayer = null;
            int secondPlayerCount = 0;
            gameObj = new GameInfo();

            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
                Platform.exit();
            }

            while(true) {
                try {
                    synchronized (this) {
                        ArrayList<String> temp = new ArrayList<>();
                        for (int i = 0; i < clients.size(); i++) {
                            temp.add("Client " + clients.get(i).count);
                            gameObj.setPlayerCount("Client " + count);
                        }
                        gameObj.setListOfPlayers(temp);
                        updateClients(gameObj);
                    }

                    Thread.sleep(100);

                    synchronized (this) {
                        GameInfo temp2 = (GameInfo) in.readObject();
                        if (temp2.getTextCommunication().equals("Client " + count)) {
                            gameObj.setTextCommunication("You can't play yourself!");
                            for (int i = 0; i < clients.size(); i++) {
                                if (clients.get(i).count == count) {
                                    try {
                                        synchronized (this) {
                                            clients.get(i).out.reset();
                                            clients.get(i).out.writeObject(gameObj);
                                        }
                                    } catch (Exception e) {
                                    }
                                    gameObj.setTextCommunication("");
                                    break;
                                } else {
                                    continue;
                                }
                            }
                        } else if (temp2.getTextCommunication().matches("Client\\s\\d*")) {
                            secondPlayerCount = Integer.parseInt(temp2.getTextCommunication().substring(7));
                            firstPlayer = getClient(count);
                            secondPlayer = getClient(secondPlayerCount);

                            if (!secondPlayer.inGame) {
                                callback.accept("Client " + firstPlayer.count + " challenged Client " +
                                        secondPlayer.count + " to a game");
                                getClient(count).inGame = true;
                                getClient(count).opponent = secondPlayer;
                                gameObj.setTextCommunication("Game");
                                synchronized (this) {
                                    firstPlayer.out.reset();
                                    firstPlayer.out.writeObject(gameObj);
                                }
                                gameObj.setTextCommunication("Client " + firstPlayer.count);
                                synchronized (this) {
                                    secondPlayer.out.reset();
                                    secondPlayer.out.writeObject(gameObj);
                                }
                            } else {
                                if(secondPlayer.opponent.count == firstPlayer.count){
                                    firstPlayer.inGame = true;
                                    firstPlayer.opponent = secondPlayer;
                                    gameObj.setTextCommunication("Game");
                                    synchronized (this) {
                                        firstPlayer.out.reset();
                                        firstPlayer.out.writeObject(gameObj);
                                    }
                                } else {
                                    gameObj.setTextCommunication("Other person is already in another game");
                                    synchronized (this) {
                                        out.reset();
                                        out.writeObject(gameObj);
                                    }
                                }

                            }
                        } else if(temp2.getTextCommunication().equals("Game")
                                && temp2.getPlayerChoice().matches("Rock|Paper|Scissors|Lizard|Spock")){
                            //firstPlayer = getClient(count);
                            //secondPlayer = getClient(firstPlayer.opponent.count);
                            while(gameObj.getP1Plays().equals("")||gameObj.getP2Plays().equals("")){
                                if(gameObj.getP1Plays().equals("")){
                                    gameObj.setP1Plays(temp2.getPlayerChoice());
                                    gameObj.setTextCommunication("Waiting");
                                    getClient(count).choice = gameObj.getP1Plays();
                                    synchronized (this) {
                                        getClient(count).out.reset();
                                        getClient(count).out.writeObject(gameObj);
                                    }
                                    callback.accept("Client " + count + " played " + getClient(count).choice);
                                }

                                if(!getClient(count).opponent.choice.equals("")){
                                    gameObj.setP2Plays(getClient(count).opponent.choice);
                                }

                            }

                            if(!gameObj.getP1Plays().equals("") && (!gameObj.getP2Plays().equals(""))){
                                if (gameObj.getP1Plays().equals("Rock")) {
                                    if (gameObj.getP2Plays().equals("Scissors") || gameObj.getP2Plays().equals("Lizard")) {
                                        gameObj.setTextCommunication("Win");
                                        callback.accept("Client " + getClient(count).count + " wins!");
                                    } else if (gameObj.getP2Plays().equals("Paper") || gameObj.getP2Plays().equals("Spock")) {
                                        gameObj.setTextCommunication("Lose");
                                        callback.accept("Client " + getClient(count).count + " loses!");
                                    } else {
                                        gameObj.setTextCommunication("Tie");
                                    }
                                }

                                // if player 1 plays paper
                                if (gameObj.getP1Plays().equals("Paper")) {
                                    if (gameObj.getP2Plays().equals("Rock") || gameObj.getP2Plays().equals("Spock")) {
                                        gameObj.setTextCommunication("Win");
                                        callback.accept("Client " + getClient(count).count + " wins!");
                                    } else if (gameObj.getP2Plays().equals("Scissors") || gameObj.getP2Plays().equals("Lizard")) {
                                        gameObj.setTextCommunication("Lose");
                                        callback.accept("Client " + getClient(count).count + " loses!");
                                    } else {
                                        gameObj.setTextCommunication("Tie");
                                    }
                                }

                                // player 1 plays scissors
                                if (gameObj.getP1Plays().equals("Scissors")) {
                                    if (gameObj.getP2Plays().equals("Paper") || gameObj.getP2Plays().equals("Lizard")) {
                                        gameObj.setTextCommunication("Win");
                                        callback.accept("Client " + getClient(count).count + " wins!");
                                    } else if (gameObj.getP2Plays().equals("Rock") || gameObj.getP2Plays().equals("Spock")) {
                                        gameObj.setTextCommunication("Lose");
                                        callback.accept("Client " + getClient(count).count + " loses!");
                                    } else {
                                        gameObj.setTextCommunication("Tie");
                                    }
                                }

                                // player 1 plays lizard
                                if (gameObj.getP1Plays().equals("Lizard")) {
                                    if (gameObj.getP2Plays().equals("Paper") || gameObj.getP2Plays().equals("Spock")) {
                                        gameObj.setTextCommunication("Win");
                                        callback.accept("Client " + getClient(count).count + " wins!");
                                    } else if (gameObj.getP2Plays().equals("Scissors") || gameObj.getP2Plays().equals("Rock")) {
                                        gameObj.setTextCommunication("Lose");
                                        callback.accept("Client " + getClient(count).count + " loses!");
                                    } else {
                                        gameObj.setTextCommunication("Tie");
                                    }
                                }

                                // player 1 plays spock
                                if (gameObj.getP1Plays().equals("Spock")) {
                                    if (gameObj.getP2Plays().equals("Scissors") || gameObj.getP2Plays().equals("Rock")) {
                                        gameObj.setTextCommunication("Win");
                                        callback.accept("Client " + getClient(count).count + " wins!");
                                    } else if (gameObj.getP2Plays().equals("Paper") || gameObj.getP2Plays().equals("Lizard")) {
                                        gameObj.setTextCommunication("Lose");
                                        callback.accept("Client " + getClient(count).count + " loses!");
                                    } else {
                                        gameObj.setTextCommunication("Tie");
                                    }
                                }

                                synchronized (this) {
                                    getClient(count).out.reset();
                                    getClient(count).out.writeObject(gameObj);
                                }
                            }

                        } else if(gameObj.getTextCommunication().equals("Reset")){
                            System.out.println("HELLO");
                            gameObj.resetInfo();
                            getClient(count).choice = "";
                            getClient(count).opponent.choice = "";
                            getClient(count).opponent = null;
                            getClient(count).inGame = false;
                        }
                    }

                    gameObj.setTextCommunication("");
                    updateClients(gameObj);


                } catch (Exception e) {
                    callback.accept("UH OH...Something wrong with the socket from client: " + count + "....closing down!");
                    //updateClients("Player #" + count + " has left the server!");
                    clients.remove(this);
                    ArrayList<String> temp = new ArrayList<>();
                    for (int i = 0; i < clients.size(); i++) {
                        temp.add("Client " + clients.get(i).count);
                    }
                    gameObj.setListOfPlayers(temp);
                    updateClients(gameObj);
                    e.printStackTrace();
                    break;
                }
            }

        }//end of run
    }//end of client thread
}
