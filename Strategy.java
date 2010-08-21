package bj;

public class Strategy
{
	public static final int HIT = 0;
	public static final int STAND = 1;
	public static final int DOUBLE = 2;
	public static final int SPLIT = 3;
	public static final int SURRENDER = 4;

	private Rules rules;

	public Strategy(Rules r)
	{
		rules = r;
	}

	/**
		@param splitHand tells number of times hand has been split so far.
		A value of 0 indicates that this hand hasn't been split. This does
		include re-splits of other splits of the same hand.
	*/
	public int getPlay(int dealerCard, int[] playerCards, int splits)
	{
		char c = rules.getRule(dealerCard, playerCards);

		switch(c)
		{
			case Rules.HIT:
										return HIT;
			case Rules.STAND:
										return STAND;
			case Rules.DOUBLE_HIT:
										return getDoubleHitPlay(dealerCard,playerCards,splits);
			case Rules.DOUBLE_STAND:
										return getDoubleStandPlay(dealerCard,playerCards,splits);
			case Rules.SPLIT_HIT:
										return getSplitHitPlay(dealerCard,playerCards,splits);
			case Rules.SPLIT_STAND:
										return getSplitStandPlay(dealerCard,playerCards,splits);
			case Rules.SURRENDER_HIT:
										return getSurrenderHitPlay(dealerCard,playerCards);
			case Rules.SURRENDER_STAND:
										return getSurrenderStandPlay(dealerCard,playerCards);
			default:
										System.out.println("Strategy got invalid rule");
										System.exit(-1);
										return -1; // to satisfy compiler
		}
	}

	/** Checks rules to see if double is allowed, if so, returns DOUBLE, if not
		returns HIT. */
	public int getDoubleHitPlay(int dealerCard, int[] playerCards, int splits)
	{
		if(canDouble(dealerCard,playerCards,splits))
			return DOUBLE;
		else
			return HIT;
	}

	public int getDoubleStandPlay(int dealerCard, int[] playerCards, int splits)
	{
		if(canDouble(dealerCard,playerCards,splits))
			return DOUBLE;
		else
			return STAND;
	}

	public int getSplitHitPlay(int dealerCard, int[] playerCards, int splits)
	{
		if(canSplit(dealerCard,playerCards,splits))
			return SPLIT;
		else
			return HIT;
	}

	public int getSplitStandPlay(int dealerCard, int[] playerCards, int splits)
	{
		if(canSplit(dealerCard,playerCards,splits))
			return SPLIT;
		else
			return STAND;
	}

	public int getSurrenderHitPlay(int dealerCard, int[] playerCards)
	{
		if(canSurrender(dealerCard,playerCards))
			return SURRENDER;
		else
			return HIT;
	}

	public int getSurrenderStandPlay(int dealerCard, int[] playerCards)
	{
		if(canSurrender(dealerCard,playerCards))
			return SURRENDER;
		else
			return STAND;
	}

	public boolean takeInsurance()
	{
		return false;
	}

	// checks rules to see if doubling is allowed for given cards
	private boolean canDouble(int dealerCard, int[] playerCards, int splits)
	{
		if(playerCards.length != 2) // must be 2 cards
			return false;

		// if can't double after split, but is after split, then can't double
		if(!rules.isDAS() && splits > 0)
			return false;

		if(rules.isDA2())  // if can double any 2, then ok to double at this point
			return true;

		if(!rules.isD9_11() && !rules.isD10_11())  // if can't do either, then can't double
			return false;

		int sum = playerCards[0] + playerCards[1];

		// check 9-11 for range
		if(rules.isD9_11())
		{
			if(sum >= 5 && sum <= 7)
				return true;
			else
				return false;
		}

		// now check 9-10 for range because more restrictive
		if(rules.isD10_11())
		{
			if(sum >= 6 && sum <= 7)
				return true;
			else
				return false;
		}
		return false; // can't double any two, or any range, so can't double
	}

	// checks rules to see if splitting is allowed for given cards
	private boolean canSplit(int dealerCard, int[] playerCards, int splits)
	{
		// can only split if there are 2 cards
		if(playerCards.length != 2)
			return false;

		// can only split if cards are the same
		if(playerCards[0] != playerCards[1] &&!(isTenValue(playerCards[0]) && isTenValue(playerCards[1])))
			return false;

		// can't split if have already split max number of times
		if(rules.getRSP() <= splits)
			return false;

		// can't split aces if not allowed
		if(!rules.isRSA() && playerCards[0] == 12)
			return false;

		// if got this far then ok to split
		return true;
	}

	// checks rules to see if surrender is allowed for given cards
	private boolean canSurrender(int dealerCard, int[] playerCards)
	{
		if(rules.isLSurr() || rules.isESurr())
			return true;
		else
			return false;
	}

	// tells if given card is a Ten, Jack, Queen or King
	private boolean isTenValue(int card)
	{
		return (card >= 8 && card <= 11);
	}
}