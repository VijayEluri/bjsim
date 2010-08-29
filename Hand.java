package bj;

/**
 * Class representing a single hand of blackjack for one player
*/
public class Hand
{
	private int[] currentCards;
	private double bet;

	public void clearHand()
	{
		currentCards = new int[0];
		bet = 0;
	}

	public void addBet(double amount)
	{
		bet += amount;
	}

	public double getBet()
	{
		return bet;
	}

	public void addCard(int card)
	{
		int[] temp = new int[currentCards.length+1];
		for(int i=0;i<currentCards.length;i++)
			temp[i] = currentCards[i];
		temp[temp.length-1] = card;
		currentCards = temp;
	}

	public void removeLastCard()
	{
		int[] temp = new int[currentCards.length-1];
		for(int i=0;i<temp.length;i++)
			temp[i] = currentCards[i];
		currentCards = temp;
	}

	public int[] getCards()
	{
		return currentCards;
	}
}
