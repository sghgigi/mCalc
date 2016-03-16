package ctrl;
import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Mortgage;

/**
 * Servlet implementation class Start
 */
//@WebServlet(urlPatterns = {"/Start/*"})
@WebServlet("/Start/*")
public class Start extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;
	
	
	@Override
	public void init() throws ServletException 
	{
		// TODO Auto-generated method stub
		super.init();
		//Mortgage ml = new Mortgage();
		this.getServletContext().setAttribute("model", new Mortgage());
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Start() {
		super();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{
		//``````````````````````````````````````````````````````Initialize
		String jsp;	
		String pathinfo = request.getPathInfo();	
		Boolean global;
		global = false;

				
		
		
		
		Mortgage m = (Mortgage) this.getServletContext().getAttribute("model");
		
		String p, r, n;
		p = request.getParameter("principle");
	    r = request.getParameter("interest");
	    n = request.getParameter("amortization");
	    
	    if (pathinfo.matches("/g"))
		{
			global = true;
			r = "3.5";
			
		}
	    request.setAttribute("global", global);
	   		 
	    if (n == null)
		{
			//request.setAttribute("amortization", this.getServletContext().getInitParameter("amortization")); 
	    	n = this.getServletContext().getInitParameter("amortization");
		}
	    
	    
	    HttpSession sn = request.getSession();
		if (p ==null || n == null)
		{
			p = (String) sn.getAttribute("principle");
			n = (String) sn.getAttribute("amortization");

			
		}
		
		sn.setAttribute("principle", p);
		sn.setAttribute("amortization", n);
		sn.setAttribute("interest", r);
		
		
		request.setAttribute("principle", p);
		request.setAttribute("interest", r);
		request.setAttribute("amortization", n);
		
		
		
		//`````````````````````````````````````````````````````Control
		if(request.getParameter("doit") == null)
		{
			jsp ="/UI.jspx";
			//request.getRequestDispatcher("/UI.jspx").forward(request, response);
			//System.out.println("Back to controller from jsp");
		}
		else
		{
			
			try
			{
				if(!global)
				{
				request.setAttribute("monthly", round(m.computePayment(p, r, n), 2));
				jsp = "/Result.jspx";
				}
				else
				{
						r = "3.5";	
						request.setAttribute("interest", r);
							
						try
						{
							request.setAttribute("monthly", round(m.gcomputePayment(p, r, n), 2));
							jsp = "/Result.jspx";
						}
						catch (Exception e)
						{
							
							request.setAttribute("error", e);
							jsp = "/UI.jspx";
						}
				
				}
				
			}
			catch (Exception e)
			{
				
				request.setAttribute("error", e);
				jsp = "/UI.jspx";
			}
			
		}
		this.getServletContext().getRequestDispatcher(jsp).forward(request, response);
		
		
	}

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
		
		// TODO Auto-generated method stub
	}
	
	//utility function for rounding up double to a certain decimalplace.
	public static double round(double d, int decimalPlace){
	    // see the Javadoc about why we use a String in the constructor
	    // http://java.sun.com/j2se/1.5.0/docs/api/java/math/BigDecimal.html#BigDecimal(double)
	    BigDecimal bd = new BigDecimal(Double.toString(d));
	    bd = bd.setScale(decimalPlace,BigDecimal.ROUND_HALF_UP);
	    return bd.doubleValue();
	  }

}
