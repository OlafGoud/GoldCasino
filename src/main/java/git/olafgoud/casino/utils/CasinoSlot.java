package git.olafgoud.casino.utils;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public class CasinoSlot {
	
	private Integer slot;
	private ItemStack input;
	private Float modifier;
	private String name;
	private ArrayList<Integer> winningNumbers;
	
	public CasinoSlot(Integer slot, String name, ItemStack input, Float modifier, ArrayList<Integer> winningNumbers) {
		this.slot = slot;
		this.input = input;
		this.modifier = modifier;
		this.name = name;
		this.winningNumbers = winningNumbers;
		
	}
	
	

	public Integer getSlot() {
		return slot;
	}

	public void setSlot(Integer slot) {
		this.slot = slot;
	}

	public ItemStack getInput() {
		return input;
	}

	public void setInput(ItemStack input) {
		this.input = input;
	}

	public Float getModifier() {
		return modifier;
	}

	public void setModifier(Float modifier) {
		this.modifier = modifier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Integer> getWinningNumbers() {
		return winningNumbers;
	}

	public void setWinningNumbers(ArrayList<Integer> winningNumbers) {
		this.winningNumbers = winningNumbers;
	}
}
