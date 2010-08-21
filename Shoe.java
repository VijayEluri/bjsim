package bj;

public class Shoe
{
	public static final int TWO = 0;
	public static final int THREE = 1;
	public static final int FOUR = 2;
	public static final int FIVE = 3;
	public static final int SIX = 4;
	public static final int SEVEN = 5;
	public static final int EIGHT = 6;
	public static final int NINE = 7;
	public static final int TEN = 8;
	public static final int JACK = 9;
	public static final int QUEEN = 10;
	public static final int KING = 11;
	public static final int ACE = 12;

	/** DECK_SIZE must equal UNIQUE_SUITS * UNIQUE_CARDS */
	public static final int DECK_SIZE = 52;
	public static final int UNIQUE_SUITS = 4;
	public static final int UNIQUE_CARDS = 13;

	private int numDecks;		// number of decks in shoe
	private int totalCards;		// total size of shoe
	private int cutCard;		// card after which shoe is re-shuffled
	private int topCard;		// top card in shoe (next to be dealt)
	private int[] cards;		// all the cards in the shoe
	private boolean reshuffle;	// tells if should reshuffle after hand is over

	public Shoe(int decks, int cut)
	{
		numDecks = decks;
		totalCards = DECK_SIZE*numDecks;
		cards = new int[totalCards];
		cutCard = cut;

		shuffle();
	}

	/** Shuffles the shoe by taking an ordered set of cards and randomly moving one
	 	card at a time into the shoe until it is full. */
	public void shuffle()
	{
		int[] newCards = new int[totalCards];
		for(int i=0;i<numDecks;i++)
			for(int j=0;j<UNIQUE_SUITS;j++)
				for(int k=0;k<UNIQUE_CARDS;k++)
					newCards[i*DECK_SIZE+j*UNIQUE_CARDS+k] = k;

		int curCard = 0;
		for(int i=totalCards-1;i>=0;i--)
		{
			int nextNew = (int)(Math.random() * (i+1));
			cards[curCard++] = newCards[nextNew];
			newCards[nextNew] = newCards[i];
		}

		topCard = totalCards - 1;	// reset top card of shoe
		reshuffle = false;			// indicate that don't need to reshuffle
	}

	/** Returns next card in shoe. Cards are ints from 0 to UNIQUE_CARDS - 1. */
	public int getNextCard()
	{
		int card = cards[topCard];
		topCard--;
		if(topCard <= cutCard)
			reshuffle = true;
		return card;
	}

	public boolean shouldReshuffle()
	{
		return reshuffle;
	}
}