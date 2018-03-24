package cdds;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class LikeServlet
 */
@WebServlet("/LikeServlet")
public class LikeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sesion = request.getSession();
		String v = (String) sesion.getAttribute("username");
		String p= request.getParameter("videoname");
		setDB(v,p);
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	private void  setDB(String username,String media_name) {
		try {
			DB db= DB.getInstance();
			String datos[];
			datos=new String[1];
			datos[0]="id_user";
			db.ExecuteQuery("select.usuario", datos, username);
			Integer id_user=Integer.parseInt(datos[0]);
			datos[0]="media_id";
			db.ExecuteQuery("select.media", datos,media_name);
			Integer id_media=Integer.parseInt(datos[0]);
			db.ExecuteUpdate("insert.like", null, id_user,id_media);
	}catch (SQLException e) {
		System.out.println("no se pudo guardar en la base de datos");
	}
	
	}

}
