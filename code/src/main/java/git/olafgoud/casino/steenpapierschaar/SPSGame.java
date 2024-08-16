package git.olafgoud.casino.steenpapierschaar;

public class SPSGame {

	private final Integer gameNumber;
	private Integer playersIn;
	private final Integer beginSlot;
	
	public SPSGame(int number, Integer slot) {
		this.gameNumber = number;
		this.playersIn = 0;
		this.beginSlot = slot;
	}

	public Integer getGameNumber() {
		return gameNumber;
	}

	public Integer getPlayersIn() {
		return playersIn;
	}

	public void setPlayersIn(Integer playersIn) {
		this.playersIn = playersIn;
	}

	public Integer getBeginSlot() {
		return beginSlot;
	}

}
