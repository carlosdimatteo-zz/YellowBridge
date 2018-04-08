package cdds;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;



/**
 * Servlet implementation class video
 */
@WebServlet("/video")
public class video extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public video() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		HttpSession sesion=request.getSession();
		String v=(String) sesion.getAttribute("username");
		System.out.println(request.getParameter("videoname"));
		
		String path = "c:\\BridgeGreen"+ "\\" +v +"\\" + getDB(request.getParameter("videoname"));
		InputStream in = new FileInputStream (path);
		String mimeType = "video/mp4";
		byte[] bytes = new byte[1024];
		int bytesRead;

		response.setContentType(mimeType);

		
		while ((bytesRead = in.read(bytes)) != -1) {
		    out.write(bytes, 0, bytesRead);
		}

		in.close();
		out.close();		
		}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private String getDB(String req) {
		try {
			DB db= DB.getInstance();
			String datos[];
			datos=new String[1];
			datos[0]="media_filename";
			db.ExecuteQuery("select.media", datos, req);
		String u= datos[0];
		return u;
		}catch (SQLException e) {
			System.out.println("error al descargar el archivo");
		}
		return null;
	}

}
