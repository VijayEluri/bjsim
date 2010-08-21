package bj;

import java.io.File;

public class BJTest
{
	public static void main(String[] args)
	{
		System.out.println("beginning test...");
		try
		{
			Rules r = new Rules(new File("8DS17DAS_rules.properties"));
			System.out.println("A,A vs. A = "+r.getRule(12,new int[]{12,12}));
			System.out.println("A,A vs. 2 = "+r.getRule(0,new int[]{12,12}));
			System.out.println("4,5 vs. 6 = "+r.getRule(4,new int[]{2,3}));
			System.out.println("4,5 vs. 7 = "+r.getRule(5,new int[]{2,3}));
			System.out.println("2,3,4,5 vs. A = "+r.getRule(12,new int[]{0,1,2,3}));
			System.out.println("2,3,4,5 vs. 6 = "+r.getRule(4,new int[]{0,1,2,3}));
			System.out.println("9,9 vs. 8 = "+r.getRule(6,new int[]{7,7}));
			System.out.println("T,T vs. A = "+r.getRule(12,new int[]{8,8}));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("test done.");
	}
}