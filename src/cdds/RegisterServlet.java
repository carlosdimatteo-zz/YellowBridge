package cdds;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;



/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
    public RegisterServlet() {
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
		JSONObject req=new JSONObject(request.getReader().lines().collect(Collectors.joining(System.lineSeparator())));
		System.out.println(req.getString("name"));
		System.out.println(req.getString("lastname"));
		System.out.println(req.getString("password"));
		System.out.println(req.getString("username"));
		System.out.println(req.getString("email"));
		JSONObject j=new JSONObject();
		boolean f=setDB(req);
		if(f) {
			j.put("sesion",true).put("username",req.getString("username")).put("msg", "El usuario "+req.getString("username")+" ha sido creado con exito").put("url", "/Entrega1/login.html");
			out.print(j.toString());
			
		}else {
			j.put("name","NO SE PUDO CREAR EL USUARIO");
			out.print(j.toString());
			
		}
	}
	private boolean setDB(JSONObject req) {
		boolean b=false;

		try {
			DB db= DB.getInstance();
			//Statement s=con.createStatement();
			String[] datos;
			datos= new String[2];
			datos[0]="username";
			datos[1]="password";
			db.ExecuteUpdate("insert.usuario",datos,req.getString("name"),req.getString("lastname"),req.getString("username"),DigestUtils.md5Hex(req.getString("password")),req.getString("email"));
			if(datos[0].equals(req.getString("username")) & datos[1].equals(DigestUtils.md5Hex(req.getString("password")))) {
				b=true;
			}else {
				b=false;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("no se pudo guardar en la base de datos");
			b=false;
		}
		return b;
			
	}
}

