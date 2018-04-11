package cdds;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;	
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import java.sql.SQLException;
/**
 * Servlet implementation class SubirArchivo
 */
@MultipartConfig
@WebServlet("/SubirArchivo")
public class SubirArchivo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int id;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubirArchivo() {
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
		// TODO Auto-generated method stub
		Part file = request.getPart("file");
		HttpSession sesion= request.getSession();
		String v=(String)sesion.getAttribute("username");
		id = (Integer) sesion.getAttribute("user_id");
		
		File folder = new File("c:/BridgeGreen"+"\\" +v);
		if (!folder.exists()) {
			folder.mkdir();
		}
		String path ="c:/BridgeGreen"+"\\" +v+"\\" +this.getFileName(file);
		setDB(request,path);
		InputStream filecontent = file.getInputStream();
		OutputStream os = null;
		try {
		
			os = new FileOutputStream(path);
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = filecontent.read(bytes)) != -1) {
				os.write(bytes, 0, read);
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
		
	
	private String getFileName(Part part) {
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

	private void  setDB(HttpServletRequest req,String url) {
		//conexion y luego insert con el getfilename en la tabla media_thumbnail 
			DB db= DB.getInstance();
			long date=new java.util.Date().getTime();
			try {
			db.ExecuteUpdate("insert.media", null,id,url,req.getParameter("titulovid"),getFileName(req.getPart("file")),req.getParameter("deArchivo"),date);
	}catch (SQLException | IOException | ServletException e) {
		System.out.println("no se pudo guardar en la base de datos");
	}
	
	}
}
	
