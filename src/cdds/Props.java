package cdds;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Props {

    private static Props instance = null;
    private static Properties properties = null;
   

    protected Props() throws IOException{
    	
    	 properties = new Properties();
    	 System.out.println(System.getProperty("props.dir"));
    	    FileInputStream is = new FileInputStream(System.getProperty("props.dir"));
    		properties.load(is);
    }

    public static Props getInstance() {//method  for singleton instance requesting
        if(instance == null) {
            try {
                instance = new Props();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public  String getProp(String key) {
        return properties.getProperty(key);
    }

}
