package cdds;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

@MultipartConfig
@WebServlet("/Prueba")
public class Prueba extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Prueba() {
    	super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession sesion = request.getSession();
		Part file = request.getPart("file");
		InputStream filecontent = file.getInputStream();
		OutputStream os = null;
		JSONObject json = (JSONObject) sesion.getAttribute("sesion");
		try {
			Props prop= Props.getInstance();
		String path = prop.getProp("usuario.folder");
		
	
		System.out.println(json.toString());

		
			if (!sesion.isNew()) {
				String fileName = this.getFileName(file);
				path = path + "\\" + json.getString("username") + "\\" + fileName;
				File folder = new File(path +"\\" + json.getString("username"));
				
				if (!folder.exists()) {
					folder.mkdir();
				}

				os = new FileOutputStream(path);
				int read = 0;
				byte[] bytes = new byte[1024];

				while ((read = filecontent.read(bytes)) != -1) {
					os.write(bytes, 0, read);
				}
				
				setDB(request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (filecontent != null) {
				filecontent.close();
			}
			if (os != null) {
				os.close();
			}
		}
	}
	private String getFileName(Part file) {
		for (String content :  file.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	private void  setDB(HttpServletRequest req) {
		//conexion y luego insert con el getfilename en la tabla media_thumbnail 
		//conexion y luego insert con el getfilename en la tabla media_thumbnail 
		try {
			Props prop= Props.getInstance();
			Class.forName(prop.getProp("driver"));
			Connection con = DriverManager.getConnection(prop.getProp("url"),prop.getProp("usuario"),prop.getProp("clave"));
			//Statement s=con.createStatement();
			PreparedStatement s=con.prepareStatement(prop.getProp("insert.media"));
			java.util.Date date=new java.util.Date();
			java.sql.Timestamp sq = new java.sql.Timestamp(date.getTime());
			s.setString(1,System.getProperty("usuario.folder")+ "\\" + req.getParameter("username") + "\\" +getFileName(req.getPart("file")));
			s.setString(2, req.getParameter("titulovid"));
			s.setString(3,getFileName(req.getPart("file")));
			s.setString(4,req.getParameter("deArchivo"));
			s.setTimestamp(5, sq);
			s.executeUpdate();
			s.close();
	}catch (ClassNotFoundException |SQLException | IOException | ServletException e) {
		System.out.println("no se pudo guardar en la base de datos");
	}
	
	}
}
