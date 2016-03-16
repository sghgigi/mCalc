package model;

import java.security.InvalidParameterException;
//import java.util.Map;

//import cacheonix.Cacheonix;

public class Mortgage 
{
	public Mortgage()	{
		
	}
	private final int million = 1000000;
	
	/*final Cacheonix cacheManager = Cacheonix.getInstance();
	final Map<String, String> cache = cacheManager.getCache("invoce.cache");*/
	
	public double computePayment(String principle, String interest, String amortization) throws Exception
	{
		
		/*final String key = principle + interest + amortization; 
	    final String cacheresult = (String) cache.get(key);
	    if(cacheresult != null)
	    {
	    	return Double.parseDouble(cacheresult);
	    }*/
		
		double p = this.extractPrinciple(principle);
		double r = this.extractInterest(interest);
		int n = this.extractAmortization(amortization);
		r = r / 100 / 12;
		return r*p / (1 - Math.pow(1+r, -n*12));
	}
	
	public double gcomputePayment(String principle, String interest, String amortization) throws Exception
	{
		/*final String key = principle + interest + amortization; 
	    final String cacheresult = cache.get(key);
	    if(cacheresult != null)
	    {
	    	return Double.parseDouble(cacheresult);
	    }*/
	    /*else
	    {*/
		double p = this.extractPrinciple(principle);
		double r = this.extractInterest(interest);
		int n = this.extractAmortization(amortization);
		
		if (p < million) throw new InvalidParameterException("Principle less than a million!");
		r = r / 12 / 100;
		n = n * 12;
		double g = n * r / 2;
		//String value = (p / n) * (1 + g + g*g/3) + "";
		
		//cache.put(key, value);
		return ((p / n) * (1 + g + g*g/3));
	    /*}*/
	}
	
	
	private double extractPrinciple(String principle)
	{
		double tmp = Double.parseDouble(principle);
		if (tmp <= 0) throw new InvalidParameterException("Principle not positive!");
		return tmp;
		
	}	
	private double extractInterest(String interest)
	{
		double tmp = Double.parseDouble(interest);
		if (tmp <= 0 || tmp >= 100) throw new InvalidParameterException("Interest not in (0 ... 100)");
		return tmp;
		
	}
	private int extractAmortization(String amortization)
	{
		int tmp = Integer.parseInt(amortization);
		if (tmp != 20 && tmp != 25 && tmp != 30) throw new InvalidParameterException();
		return tmp;
		
	}

}
