import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/welcome")
public class Welcome extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public Welcome() { super(); }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String input = URLEncoder.encode(request.getParameter("user"), "UTF-8");
		String message = request.getParameter("debug");
		PrintWriter out = response.getWriter();
		
		out.write("<p>welcome, User " + input + ".</p>");
		out.write("<p id='" + input + "'></p>");
		out.write("<script>document.getElementById('" + input + "').innerText = 'funny...';</script>");
		out.write("<script>/* the name is " + input + " */</script>");
	    // VULNERABLE - XSS in debug parameter (this would not be tested in the application unit tests)
		try { out.write(message); } catch (Exception e) { }
	    // VULNERABLE - Open Redirect
		try { out.write("<a src='" + new URI(request.getHeader("referer")).getHost() + "/logout'>logout</a>"); } catch (Exception e) { }
		out.write("<p>faq@thissite.com</p>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
