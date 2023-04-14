package main;

import java.util.*;
import java.util.stream.Collectors;
import entity.Card;

public class Main {
	// Declaring the deck of cards as a static and final member
	public static final Stack<Card> deck = new Stack<Card>();


	// Declaring the static block to get the deck complete with all different cards
	// as soon as the class gets loaded to the memory
	static {
		// Adding cards to deck
		List<String> suits = Arrays.asList("Diamonds", "Spades", "Hearts", "Clubs");
		for(int i = 0; i < 4; i++) {
			for(int j = 1; j <= 13; j++) {
				Card card = new Card(suits.get(i), j);
				deck.push(card);
			}
		}
	}
	public static void run(int initialPlayer, int totalPlayers, boolean noCardsPresent,
						   Stack<Card> usedPile, Stack<Stack<Card>> playersHands, Stack<Card> drawPile) {
		if (initialPlayer >= 1 && initialPlayer <= totalPlayers) {
			int activePlayer = initialPlayer - 1;

			// Marking the first round to be 1
			int round = 1;

			// Loop will run till any one player runs out of cards
			while (noCardsPresent == false) {
				boolean isCardPresent = false;
				System.out.println("The suit and rank of the discard pile's top card are: "
						+ usedPile.peek().getSuit() + " and " + usedPile.peek().getNumber()
						+ " respectively, for round " + (round++) + ".");

				// Searching for the availability of the required type of card in the current
				// user hand
				for (int i = 0; i < playersHands.get(activePlayer).size(); i++) {
					if (playersHands.get(activePlayer).get(i).getSuit().equals(usedPile.peek().getSuit())
							|| (playersHands.get(activePlayer).get(i).getNumber() == usedPile.peek().getNumber())) {
						isCardPresent = true;
						usedPile.push(playersHands.get(activePlayer).remove(i));
						activePlayer++;
						if (activePlayer == totalPlayers) {
							activePlayer = 0;
						}
						break;
					}
				}

				// If the required type of card is not present in the current user hand
				if (isCardPresent == false) {
					if (!drawPile.isEmpty()) {
						Card drawedCard = drawPile.pop();
						if (drawedCard.getSuit().equals(usedPile.peek().getSuit())
								|| drawedCard.getNumber() == usedPile.peek().getNumber()) {
							usedPile.push(drawedCard);
						} else {
							playersHands.get(activePlayer).push(drawedCard);
						}

						activePlayer++;
						if (activePlayer == totalPlayers) {
							activePlayer = 0;
						}
					}

					// If the draw pile is empty for an instance
					else {
						System.out.println("Draw pile became empty. Match stopped. No one is the winner...!!!");
						break;
					}
				}

				// If current player played all his cards and ran out of card
				if (playersHands.get(activePlayer).isEmpty()) {
					noCardsPresent = true;
					System.out.println();
					System.out.println("-------------------------------------------");
					System.out.println("No of rounds played: "+(--round));
					System.out.println();
					System.out.println("Player " + (activePlayer + 1) + " is the winner.");
					System.out.println("-------------------------------------------");
					System.out.println();

				}
			}
		} else {
			System.out.println("Please select a player between 1 and " + totalPlayers
					+ ", because you have only selected " + totalPlayers + " players for this game.");
		}
	}

	public static void main(String[] args) {
		// Shuffling the deck of cards before starting the game
		Collections.shuffle(deck);

		// Taking the number of players as input
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of players. (2-4)");
		int totalPlayers = sc.nextInt();

		// The player count must be between 2 and 4 (both inclusive)
		if (totalPlayers >= 2 && totalPlayers <= 4) {
			// Hand of cards of all players in a stack of stacks
			Stack<Stack<Card>> playersHands = new Stack<Stack<Card>>();

			// Distributing cards among all the players
			for (int i = 0; i < totalPlayers; i++) {
				Stack<Card> singlePlayerCards = new Stack<Card>();

				// Each player starts with a hand of 5 cards
				for (int j = 0; j < 5; j++) {
					singlePlayerCards.push(deck.pop());
				}
				playersHands.push(singlePlayerCards);
			}

			// Creating the draw pile for the game
			Stack<Card> drawPile = new Stack<Card>();

			// Copying all the rest cards in deck after distribution, to the draw pile
			drawPile.addAll(deck);

			// Filtering the draw pile to get only the number cards except the action cards
			List<Card> onlyNumberCards = drawPile.stream().filter(card -> (card.getNumber() != 1 && card.getNumber() != 11
					&& card.getNumber() != 12 && card.getNumber() != 13)).collect(Collectors.toList());

			// Creating discard pile
			Stack<Card> usedPile = new Stack<Card>();

			// Adding a number card from the draw pile to the discard pile to start the game
			usedPile.push(onlyNumberCards.get(onlyNumberCards.size() - 1));

			boolean noCardsPresent = false;

			System.out.println("Which player you want to start the game from?");
			int initialPlayer = sc.nextInt();
			System.out.println();
			
			// Closing the Scanner class here
			sc.close();

			run(initialPlayer, totalPlayers, noCardsPresent, usedPile, playersHands, drawPile);

		} else {
			System.out.println("Number of players cannot be less than 2 and more than 4.");
		}
	}

}
