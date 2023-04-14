package entity;

public class Card {
	private String suit;
	private int number;

	public Card() {
		super();
	}

	public Card(String suit, int number) {
		super();
		this.suit = suit;
		this.number = number;
	}

	@Override
	public String toString() {
		return "Card [suit=" + suit + ", number=" + number + "]";
	}

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}
}
