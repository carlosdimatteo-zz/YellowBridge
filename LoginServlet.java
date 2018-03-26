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
	int id;
       
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
			j.put("sesion",true).put("user_id",id).put("username",req.getString("username")).put("url","/Entrega1/channel.html").put("type_user",req.getInt("type_user"));
			out.print(j.toString());
			sesion.setAttribute("username",req.getString("username"));
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
		datos=new String[3];
		datos[0]="username";
		datos[1]="password";
		datos[2]="id_user";
		try {
		db.ExecuteQuery("select.usuario",datos, req.getString("username"));
		String u= datos[0];
		String  c= datos[1];
		id=Integer.parseInt(datos[2]);
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