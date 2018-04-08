package cdds;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int id,type;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		PrintWriter out= response.getWriter();
		HttpSession sesion = request.getSession();
		JSONObject req=new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println(req.getString("username"));
		System.out.println(req.getString("password"));
		JSONObject j=new JSONObject();
		System.out.println(System.getProperty("props.dir"));
		boolean f=getDB(req);
			if(f==true ) {
			j.put("sesion",true).put("msg", "el usuario "+req.getString("username")+" ha iniciado sesion con exito").put("user_id",id).put("username",req.getString("username")).put("url","/Entrega1/channel.html").put("type_user",req.getInt("type_user"));
			out.print(j.toString());
			sesion.setAttribute("username",req.getString("username"));
			sesion.setAttribute("user_id", id);//Changed Servlet functionality to work with user id instead of username Added user id column t media table in database for user-video relationship changed xhr.login function for a bug in message when registering
			sesion.setAttribute("type_user", type);
			}else {
				j.put("sesion", false).put("username","invalido");
				out.print(j.toString());
				sesion.invalidate();
		}
	}
private boolean getDB(JSONObject req) {
		boolean b;
		DB db= DB.getInstance();
		String datos[];
		datos=new String[4];
		datos[0]="username";
		datos[1]="password";
		datos[2]="id_user";
		datos[3]="type_id";
		try {
		db.ExecuteQuery("select.usuario",datos, req.getString("username"));
		String u= datos[0];
		String  c= datos[1];
		id=Integer.parseInt(datos[2]);
		type=Integer.parseInt(datos[3]);
		if(u.equals(req.getString("username")) & c.equals(DigestUtils.md5Hex(req.getString("password")))) {
			b=true;
		}else {
			b=false;
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		b=false;
	}
	return b;

}}