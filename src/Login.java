import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// VULNERABILITY - no expiration process
	public static HashMap<String, String> sessions = new HashMap<String, String>();

    public Login() { super(); }

    // VULNERABILITY - Password sent in plain text in the URL
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
	}
	
	// VULNERABILITY - if there is an error thrown, the function will return true, as if the user was logged in
	// VULNERABILITY - Side Channel Data Leakage
	private Boolean checkUser(PrintWriter out, String user, String pass) {
		try {
			Thread.sleep(200);	// represents database searching
			if (! user.equals("user")) {
				out.write("<p>user wrong</p>");	// VULNERABILITY - User enumeration
				return false;
			}

			Thread.sleep(200);	// represents database searching
			if (! pass.equals("password")) {
				out.write("<p>password wrong</p>");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		out.write("everything right");
		
		return true;
	}
	
	private void login (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	RequestDispatcher rs = request.getRequestDispatcher("welcome");
        rs.forward(request, response);
	}

	// VULNERABILITY - Session Fixation
	// VULNERABILITY - password reflected in plain text
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        
        String email = request.getParameter("user");
        String pass = request.getParameter("pass");
        
        if (sessions.get(session.getId()) != null) {
        	out.write("<p>You have already a logged in session!</p>");
        	login(request, response);
        }
        
        if(checkUser(out, email, pass)) {
            sessions.put(session.getId(), email);
            out.write("<p>new session created</p>");
        	login(request, response);
        }
        else {
			out.println("Username or Password incorrect");
			out.println("<p>user: " + email);
			out.println("<p>pass: " + pass);
        }
	}
}
