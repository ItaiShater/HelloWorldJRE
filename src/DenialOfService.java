import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/dos")
public class DenialOfService extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public DenialOfService() { super(); }
    
    // http://localhost:8080/HelloWorldJRE/dos?type=1&input=abcde
    private void dos1 (String input) {
		for (int i = 0; i < input.length(); i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    
    // http://localhost:8080/HelloWorldJRE/dos?type=2&input=5
    private void dos2 (String input) {
    	Integer input_int = Integer.parseInt(input);
    	
    	for (int i = 0; i < input_int; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
    
    // http://localhost:8080/HelloWorldJRE/dos?type=3&input=10000
    private void dos3 (String input) {
    	Integer input_int = Integer.parseInt(input);
    	
		try {
			Thread.sleep(input_int);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
    
    // http://localhost:8080/HelloWorldJRE/dos?type=4&input=aaaaaaaaaaaaaaaaaaaaaaaaaaj
    private void dos4 (PrintWriter out, String input) {
    	String regex = "^(a+)+$";
    	Pattern r = Pattern.compile(regex);
    	Matcher m = r.matcher(input);
    	if (m.find()) {
    		out.write("Found value: " + m.group(0));
    	}
    	else {
    		out.write("Value not found");
    	}
	}
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String type = request.getParameter("type");
			String input = request.getParameter("input");

			if ("1".equals(type)) {
				dos1(input);
			} else if ("2".equals(type)) {
				dos2(input);
			} else if ("3".equals(type)) {
				dos3(input);
			} else if ("4".equals(type)) {
				dos4(response.getWriter(), input);
			} else {
				response.getWriter().write("<p>parameter 'type' is invalid.</p>");
			}
			
			response.getWriter().append("Served at: ").append(request.getContextPath());
		}
		catch (Exception e) {
			response.getWriter().write("<p>parameters 'type' or 'input' are missing.</p>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
