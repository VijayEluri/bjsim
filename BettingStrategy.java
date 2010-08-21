package bj;

public class BettingStrategy
{
	/** Notifies the strategy that a card was just dealt. This is used in card
		counting. The default BettingStrategy does nothing here. */
	public void cardDealt(int card)
	{
	}

	/** Notifies the strategy that shoe was just shuffled. This is used in card
		counting. The default BettingStrategy does nothing here. */
	public void justShuffled()
	{
	}

	/** Returns next bet, in units. Min amount returned will be 1 unit.
		Makes no assumption about max bet allowed, dealer must handle this. */
	public int getNextBet()
	{
		return 1;
	}
}