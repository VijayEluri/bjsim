package bj;

public class Player
{
	private Strategy strategy;
	private BettingStrategy betStrategy;
	private double bankroll;

	public Player(Strategy s, BettingStrategy b, double startingBankroll)
	{
		strategy = s;
		betStrategy = b;
		bankroll = startingBankroll;
	}

	/** Tells player that this card was just dealt, this is passed to the Player's
		betting strategy for card counting. */
	public void cardDealt(int card)
	{
		betStrategy.cardDealt(card);
	}

	/** Tells player that shoe was just shuffled, this is passed to the Player's
		betting strategy for card counting. */
	public void justShuffled()
	{
		betStrategy.justShuffled();
	}

	public double getNextBet()
	{
		int bet = betStrategy.getNextBet();
		bankroll -= bet;
		return bet;
	}

	/** This adds winnings to the player's bankroll and should include the original
		bet made at the start of the hand. */
	public void collectWinnings(double winnings)
	{
		bankroll += winnings;
	}

	public int getPlay(int dealerCard, int[] playerCards, int splits)
	{
		return strategy.getPlay(dealerCard,playerCards,splits);
	}

	/** Dealer uses this for doubles and splits to take additional bet from player */
	public void additionalBet(double amount)
	{
		bankroll -= amount;
	}
}