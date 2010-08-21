package bj;

public class Dealer
{
	private Rules rules;
	private Player[] players;
	private Hand[][] hands;
	private double[] currentBets;
	private Shoe shoe;

	public Dealer(int maxPlayers, Shoe s, Rules r)
	{
		players = new Player[maxPlayers];
		hands = new Hand[maxPlayers][];
		shoe = s;
		rules = r;
	}

	public void addPlayer(Player p, int seat)
	{
		players[seat] = p;
	}

	public void removePlayer(int seat)
	{
		players[seat] = null;
	}

	/** Deals a single round of blackjack. Starts with player 0 up to
		last player in order. */
	public void dealOneRound()
	{
		// for each player, clear cards, get next bet and deal one card
		for(int i=0;i<players.length;i++)
		{
			HERE NOW
			use default hand for each player, but grow array as needed and
			size of array equals number of splits
		}



		// finally reshuffle if necessary
		if(shoe.shouldReshuffle())
		{
			shoe.shuffle();
			notifyShuffle();
		}
	}

	private void notifyShuffle()
	{
		for(int i=0;i<players.length;i++)
		{
			players[i].justShuffled();
		}
	}

	private void notifyCardDealt(int card)
	{
		for(int i=0;i<players.length;i++)
		{
			players[i].cardDealt(card);
		}
	}
}