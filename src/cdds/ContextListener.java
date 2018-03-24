package cdds;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
@WebListener
public class ContextListener implements ServletContextListener {

	public ContextListener() {
		
	}
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}
	/*
@see ServletContextListener#contextInitialized(ServletContextEvent)
*/
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
			Props prop= Props.getInstance();
			System.out.println("Archivo de propiedades cargado:  "+prop);
	}

}
