package cdds;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
	
	Props prop= Props.getInstance();
	private static Connection con=null;
	private static DB ins=null;
		protected  DB() {
			try {
				Class.forName(prop.getProp("driver"));
				 con = DriverManager.getConnection(prop.getProp("url"),prop.getProp("usuario"),prop.getProp("clave"));
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("No se pudo generar la conexion a la base de datos error: \n"+e.getMessage());
			}
			
		}
		//metodo para realizar selects
		public  void ExecuteQuery(String query,String requests[],Object...objects) throws SQLException{
			PreparedStatement s;
			s = con.prepareStatement(prop.getProp(query));
			set(s,objects);
			ResultSet rs=s.executeQuery();
			rs.next();
			get(rs,requests);
			s.close();	
			}


//		metodo para relizar cambios ya sean insert o delete etc/ con un parametro condicional(arreglo) que son los datos 
//		que quieres que retorne el insert para verificaciones para no tener que hacer un select luego ;)
	public void ExecuteUpdate(String query,String requests[],Object...objects) throws SQLException{
			PreparedStatement s= con.prepareStatement(prop.getProp(query));
			if(requests!=null) {
				String q=prop.getProp(query);
				q+="returning "+requests[0];
				for(int i=1;i<requests.length;i++) {
					q+=","+requests[i];
				}
				System.out.println(q);
				s=con.prepareStatement(q);
				set(s,objects);
				ResultSet rs=s.executeQuery();
				rs.next();
				get(rs,requests);
				s.close();
				}else {
					set(s,objects);

				System.out.println(s.toString());
				s.executeUpdate();
				s.close();
			}
			
		}
	 private void set(PreparedStatement s,Object...objects) {
		try{for(int i=0;i<objects.length;i++) {
			System.out.println(objects[i].getClass().toString());
			//s.setObject(i+1,objects[i]);
			if(objects[i].getClass().toString().equals("class java.lang.String")) {
				s.setString(i+1,(String) objects[i]);
			}else if(objects[i].getClass().toString().equals("class java.util.Date")||objects[i].getClass().toString().equals("class java.lang.Long")) {
				java.sql.Timestamp sq= new java.sql.Timestamp((long) objects[i]);
				s.setTimestamp(i+1,sq);
			}else {
				s.setInt(i+1,(int)objects[i]);
			}
		}}catch(SQLException e) {
			System.out.println("error en la base de datos :  "+e.getMessage());
		}
	}
		public static DB getInstance(){
			if(ins==null) {
				ins=new DB();
				
			}
			return ins;
		}
		
		private void get(ResultSet rs,String[] requests) {
			for(int i=0;i<requests.length;i++) {
				String x= requests[i];
				try {
					requests[i]=String.valueOf(rs.getObject(x));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				}
		}
		
}
