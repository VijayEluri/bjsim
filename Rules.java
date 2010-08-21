package bj;

import java.util.HashMap;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;

public class Rules
{
	public static final char HIT = 'H';
	public static final char STAND = 'S';
	public static final char DOUBLE_HIT = 'D';
	public static final char DOUBLE_STAND = 'd';
	public static final char SPLIT_HIT = 'V';
	public static final char SPLIT_STAND = 'v';
	public static final char SURRENDER_HIT = 'R';
	public static final char SURRENDER_STAND = 'r';

	/** Special value for Aces different from normal card values. This is
		used because for rules, J,Q and K are all treated as T. Because the
		rules use indices into arrays, this reduces the ACE index by three
		to fill the gap. */
	public static final int RULES_ACE_VALUE = 9;

	private String[] hardTotals;
	private String[] softTotals;
	private String[] pairs;
	private HashMap compositionRules;

	private boolean S17 = true;
	private boolean DAS = true;
	private boolean DA2 = true;
	private boolean D9_11 = true;
	private boolean D10_11 = true;
	private int RSP = 4;
	private boolean RSA = false;
	private boolean AAHit = false;
	private boolean ESurr = false;
	private boolean LSurr = true;
	private int numDecks = 8;

	public Rules(File rulesFile) throws Exception
	{
		load(rulesFile);
	}

	public void load(File rulesFile) throws Exception
	{
		// first initialize rules
		initializeRules();

		// now override certain rules as specified in props file
		FileInputStream fin = new FileInputStream(rulesFile);
		Properties props = new Properties();
		props.load(fin);
		updateRules(props);
	}

	/** Returns the rule for the given player cards against the given dealer card. */
	public char getRule(int dealerCard, int[] playerCards)
	{
		// first, if dealer card is J,Q or K then treat as a T
		if(dealerCard == Shoe.JACK || dealerCard == Shoe.QUEEN || dealerCard == Shoe.KING)
			dealerCard = Shoe.TEN;

		// next, rule indices omit J,Q and K, so A is off by 3, adjust for that
		// because all rules will be returned by index into rule Strings
		if(dealerCard == Shoe.ACE)
			dealerCard = RULES_ACE_VALUE;
		convertAces(playerCards);

		// now must determine current player card condition and return appropriate rule
		// first check for 2-card pair
		if(playerCards.length == 2 && playerCards[0] == playerCards[1])
			return getPairRule(dealerCard, playerCards);

		// next check soft total
		if(containsAce(playerCards) && total(playerCards) <= 11)
			return getSoftTotalRule(dealerCard, playerCards);

		// finally check hard totals
		return getHardTotalRule(dealerCard, playerCards);
	}

	public char getPairRule(int dealerCard, int[] playerCards)
	{
		return pairs[playerCards[0]].charAt(dealerCard);
	}

	public char getSoftTotalRule(int dealerCard, int[] playerCards)
	{
		return softTotals[total(playerCards)-3].charAt(dealerCard);
	}

	public char getHardTotalRule(int dealerCard, int[] playerCards)
	{
		return hardTotals[total(playerCards)-5].charAt(dealerCard);
	}

	public boolean isS17() {return S17;}
	public boolean isDAS() {return DAS;}
	public boolean isDA2() {return DA2;}
	public boolean isD9_11() {return D9_11;}
	public boolean isD10_11() {return D10_11;}
	public int getRSP() {return RSP;}
	public boolean isRSA() {return RSA;}
	public boolean isAAHit() {return AAHit;}
	public boolean isESurr() {return ESurr;}
	public boolean isLSurr() {return LSurr;}
	public int getNumDecks() {return numDecks;}

	// returns the total value of all cards given, treating Aces as 1 always.
	private int total(int[] cards)
	{
		int sum = 0;
		for(int i=0;i<cards.length;i++)
			if(cards[i] == RULES_ACE_VALUE)
				sum += 1;
			else
				sum += cards[i] + 2;
		return sum;
	}

	// converts all aces in given cards array to rules value for aces
	private void convertAces(int[] cards)
	{
		for(int i=0;i<cards.length;i++)
			if(cards[i] == Shoe.ACE)
				cards[i] = RULES_ACE_VALUE;
	}

	// tells if given array of cards contains one whose value equals RULES_ACE_VALUE.
	private boolean containsAce(int[] cards)
	{
		for(int i=0;i<cards.length;i++)
			if(cards[i] == RULES_ACE_VALUE)
				return true;
		return false;
	}

	// sets rules up initially
	// default is that all rules are to Hit
	// select rules can/will be overridden by properties
	private void initializeRules()
	{
		hardTotals = new String[17];
		softTotals = new String[9];
		pairs = new String[10];
		compositionRules = new HashMap(); // just create empty

		String allHit = "HHHHHHHHHH";

		for(int i=0;i<hardTotals.length;i++)
			hardTotals[i] = allHit;
		for(int i=0;i<softTotals.length;i++)
			softTotals[i] = allHit;
		for(int i=0;i<pairs.length;i++)
			pairs[i] = allHit;
	}

	// assumes that rules have been initialized
	// updates rules based on properties found
	// will only overwrite rules that are found in properties given
	// NOTE: The order of all of these rules is important, DO NOT ALTER
	private void updateRules(Properties props)
	{
		// Hard Totals
		updateOne(props,"H05",hardTotals,0);
		updateOne(props,"H06",hardTotals,1);
		updateOne(props,"H07",hardTotals,2);
		updateOne(props,"H08",hardTotals,3);
		updateOne(props,"H09",hardTotals,4);
		updateOne(props,"H10",hardTotals,5);
		updateOne(props,"H11",hardTotals,6);
		updateOne(props,"H12",hardTotals,7);
		updateOne(props,"H13",hardTotals,8);
		updateOne(props,"H14",hardTotals,9);
		updateOne(props,"H15",hardTotals,10);
		updateOne(props,"H16",hardTotals,11);
		updateOne(props,"H17",hardTotals,12);
		updateOne(props,"H18",hardTotals,13);
		updateOne(props,"H19",hardTotals,14);
		updateOne(props,"H20",hardTotals,15);
		updateOne(props,"H21",hardTotals,16);

		// Soft Totals
		updateOne(props,"A2",softTotals,0);
		updateOne(props,"A3",softTotals,1);
		updateOne(props,"A4",softTotals,2);
		updateOne(props,"A5",softTotals,3);
		updateOne(props,"A6",softTotals,4);
		updateOne(props,"A7",softTotals,5);
		updateOne(props,"A8",softTotals,6);
		updateOne(props,"A9",softTotals,7);
		updateOne(props,"AT",softTotals,8);

		// Pairs
		updateOne(props,"22",pairs,0);
		updateOne(props,"33",pairs,1);
		updateOne(props,"44",pairs,2);
		updateOne(props,"55",pairs,3);
		updateOne(props,"66",pairs,4);
		updateOne(props,"77",pairs,5);
		updateOne(props,"88",pairs,6);
		updateOne(props,"99",pairs,7);
		updateOne(props,"TT",pairs,8);
		updateOne(props,"AA",pairs,9);

		if(props.getProperty("S17") != null && props.getProperty("S17").equalsIgnoreCase("true"))
			S17 = true;
		if(props.getProperty("DAS") != null && props.getProperty("DAS").equalsIgnoreCase("true"))
			DAS = true;
		if(props.getProperty("DA2") != null && props.getProperty("DA2").equalsIgnoreCase("true"))
			DA2 = true;
		if(props.getProperty("D9_11") != null && props.getProperty("D9_11").equalsIgnoreCase("true"))
			D9_11 = true;
		if(props.getProperty("D10_11") != null && props.getProperty("D10_11").equalsIgnoreCase("true"))
			D10_11 = true;

		if(props.getProperty("RSP") != null)
			RSP = Integer.parseInt(props.getProperty("RSP"));

		if(props.getProperty("RSA") != null && props.getProperty("RSA").equalsIgnoreCase("true"))
			D10_11 = true;
		if(props.getProperty("AAHit") != null && props.getProperty("AAHit").equalsIgnoreCase("true"))
			D10_11 = true;

		if(props.getProperty("numDecks") != null)
			RSP = Integer.parseInt(props.getProperty("numDecks"));
	}

	// possibly updates one rule, if found in the given Properties.
	private void updateOne(Properties props, String rule,String[] ruleSet, int index)
	{
		String s = props.getProperty(rule);
		if(s != null)
			ruleSet[index] = s;
	}
}