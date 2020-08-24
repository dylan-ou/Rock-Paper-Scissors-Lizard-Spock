import java.io.Serializable;
import java.util.ArrayList;

public class GameInfo implements Serializable {
    private int p1Points;
    private int p2Points;
    private String p1Plays;
    private String p2Plays;
    private String playerChoice;
    private Boolean have2Players;
    private String textCommunication;
    private ArrayList listOfPlayers;
    private String playerCount;

    public GameInfo(){
        this.p1Points = 0;
        this.p2Points = 0;
        this.p1Plays = "";
        this.p2Plays = "";
        this.playerChoice="";
        this.have2Players = false;
        this.textCommunication = "";
    }

    public int getP1Points() {
        return p1Points;
    }

    public int getP2Points() {
        return p2Points;
    }

    public void setP1Points(int p1Points) {
        this.p1Points = p1Points;
    }

    public void setP2Points(int p2Points) {
        this.p2Points = p2Points;
    }

    public String getP1Plays() {
        return p1Plays;
    }

    public String getP2Plays() {
        return p2Plays;
    }

    public void setP1Plays(String p1Plays) {
        this.p1Plays = p1Plays;
    }

    public void setP2Plays(String p2Plays) {
        this.p2Plays = p2Plays;
    }

    public Boolean getHave2Players() {
        return have2Players;
    }

    public void setHave2Players(Boolean have2Players) {
        this.have2Players = have2Players;
    }

    public String getPlayerChoice() {
        return this.playerChoice;
    }

    public void setPlayerChoice(String playerChoice) {
        this.playerChoice = playerChoice;
    }

    public String getTextCommunication() {
        return textCommunication;
    }

    public void setTextCommunication(String textCommunication) {
        this.textCommunication = textCommunication;
    }

    public void resetInfo(){ // resets everything
        this.p1Points = 0;
        this.p2Points = 0;
        this.p1Plays = "";
        this.p2Plays = "";
        this.playerChoice="";
        this.textCommunication = "";
    }

    public ArrayList getListOfPlayers() {
        return listOfPlayers;
    }

    public void setListOfPlayers(ArrayList listOfPlayers) {
        this.listOfPlayers = listOfPlayers;
    }

    public String getElemOfList(int i){
        return listOfPlayers.get(i).toString();
    }

    public String getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(String playerCount) {
        this.playerCount = playerCount;
    }
}
