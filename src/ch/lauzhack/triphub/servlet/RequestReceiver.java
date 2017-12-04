package ch.lauzhack.triphub.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ch.lauzhack.triphub.data.SBBParser;
import ch.lauzhack.triphub.engine.Meetup;
import ch.lauzhack.triphub.social.User;
import ch.lauzhack.triphub.trip.Station;
import ch.lauzhack.triphub.trip.Trip;

//@WebServlet(description = "Get request and launches algorithm", urlPatterns = { "/request" })
public class RequestReceiver extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
    public RequestReceiver() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		response.sendRedirect(request.getRequestURI() + "/..");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		HttpSession session = request.getSession();
		String destination = request.getParameter("dest");
		String date = request.getParameter("date");
		ArrayList<String> starts = new ArrayList<>();
		ArrayList<String> names = new ArrayList<>();
		ArrayList<Calendar> dates = new ArrayList<>();
		int startNb = ((Integer) session.getAttribute("startNb")).intValue();
		boolean proceed = true;
		
		if(destination.equals(""))
		{
			request.setAttribute("errorDest", true);
			request.setAttribute("startSet", true);
			proceed = false;
		}
		
		if(date.equals(""))
		{
			request.setAttribute("errorDate", true);
			request.setAttribute("startSet", true);
			proceed = false;
		}
		
		String[] s = request.getParameterValues("start");
		String[] n = request.getParameterValues("name");
		String[] t = request.getParameterValues("time");
		for(int i = 0 ; i < s.length ; i++)
		{
			if(!s[i].equals(""))
				starts.add(s[i]);
			if(!n[i].equals(""))
				names.add(n[i]);
			
			if(t[i].equals("")) 
				continue;
			
			System.out.println("start : " + s[i]);
			System.out.println("time : " + t[i]);
			System.out.println("name : " + n[i]);

			Calendar cal = Calendar.getInstance();
			System.out.println("date" + date);
			String thisDate = date + "T" + t[i];
			System.out.println("this date : " + thisDate);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
			try 
			{
				cal.setTime(dateFormat.parse(thisDate));
			} 
			catch (ParseException e) 
			{}
			System.out.println("Date before adding:" + cal.getTime().toString());
			dates.add(cal);
		}

		if(starts.size() < startNb)
		{
			request.setAttribute("errorStart", true);
			request.setAttribute("startSet", true);
			proceed = false;
		}
		if(dates.size() < startNb)
		{
			request.setAttribute("errorTime", true);
			request.setAttribute("startSet", true);
			proceed = false;
		}
		if(names.size() < startNb)
		{
			request.setAttribute("errorName", true);
			request.setAttribute("startSet", true);
			proceed = false;
		}
		
		if(!proceed)
		{
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/form.jsp").forward(request, response);
			return;
		}
		
		
		System.out.println("ALGO !");
		
		SBBParser parser = new SBBParser();
		Station end = parser.getStation(destination);
		ArrayList<User> users = new ArrayList<>();
		
		for(int i = 0 ; i < names.size() ; i++)
		{
			Station begin = parser.getStation(starts.get(i));
			System.out.println("date " + dates.get(i).getTime().toString());
			users.add(new User(names.get(i), begin, end, parser.getConnections(begin, end, dates.get(i)), dates.get(i)));
		}	
		
		try
		{
			Meetup.getBestTrip(users);
			for (User user : users) {
				System.out.println(user.getPath());
				System.out.println("-------------------------------");
			}
			session.setAttribute("users", users);
			
			this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/solution.jsp").forward(request, response);
		}
		catch(RuntimeException e)
		{
			response.sendError(400, "We couldn't process the request, as one of your user's destination is unreachable.");
		}
	}

}
