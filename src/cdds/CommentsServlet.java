package cdds;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class CommentsServlet
 */
@MultipartConfig
@WebServlet("/CommentsServlet")
public class CommentsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentsServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession sesion = request.getSession();
		Integer id = (Integer) sesion.getAttribute("user_id");
		String p= request.getParameter("videoname");
		System.out.println("videoname enviado en el formdata"+p);
		String comment= request.getParameter("comment");
		System.out.println("comentario enviado en el formdata"+comment);
		setDB(id,p,comment);
		doGet(request, response);
	}
	private void  setDB(Integer id,String media_name,String comment) {
			DB db= DB.getInstance();
			String datos[];
			datos=new String[1];
			try {
			Integer id_user=id;
			datos[0]="media_id";
			db.ExecuteQuery("select.media", datos,media_name);
			Integer id_media=Integer.parseInt(datos[0]);
			long date=new java.util.Date().getTime();
			db.ExecuteUpdate("insert.comment",null,id_media,id_user,date,comment);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("ocurrio un error en la interaccion con la bdd: "+e.getMessage());
				e.printStackTrace();
			}
			
	
	
	}

}
