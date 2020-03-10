import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/encodingsniffing")
public class EncodingSniffing extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public EncodingSniffing() { super(); }

    // VULNERABILITY - Encode Sniffing
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setContentType("text/html;charset=UTF-8");	// having the charset defined in the request makes it not vulnerable to encoding sniffing
		StringBuilder tHtml = new StringBuilder();
		String input = URLEncoder.encode(request.getParameter("user"), "UTF-8");
		
		
		tHtml.append("<html>" + "\n");
		tHtml.append("<head>" + "\n");
		//tHtml.append("<meta charset=utf-8>" + "\n");	// having the charset defined in the html makes it not vulnerable to encoding sniffing
		tHtml.append("<title>Hello!</title>" + "\n");
		tHtml.append("</head>" + "\n");
		tHtml.append("<body>" + "\n");
		tHtml.append("<p>Hello, " + input + "</p>" + "\n");
		tHtml.append("<img src='http://2.bp.blogspot.com/-Ol8pLJcc9oo/TnZY6R8YJ5I/AAAAAAAACSI/YDxcIHCZhy4/s150/duke_44x80.png'/>" + "\n");
		tHtml.append("</body>" + "\n");
		tHtml.append("</html>" + "\n");
		
		response.getWriter().write(tHtml.toString());
		response.addHeader("X-XSS-Protection", "1; mode=block");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
