package git.olafgoud.casino.games.roulette.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RouletteWinEvent extends Event implements Cancellable{


    private static final HandlerList HANDLERS = new HandlerList();
    private final Player p;
	private boolean isCancelled;
	private final Integer reward;
    
    
    public RouletteWinEvent(Player player, Integer reward) {
    	p = player;
    	this.reward = reward;
    }
    
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

	public Player getP() {
		return p;
	}

    
	@Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

	public Integer getReward() {
		return reward;
	}
    

}
