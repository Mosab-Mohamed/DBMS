package DBMS;


import java.io.BufferedReader;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.thoughtworks.xstream.XStream;

public class Schema {
	XStream xstream = new XStream();
	BufferedReader in;
	class Escanner{
	     StringTokenizer st;
	     Escanner()throws Throwable{
	         //in = new BufferedReader(new FileReader("save.txt"));
	         st = new StringTokenizer("");
	     }
	     int nextInt() throws Throwable{
	         if(st.hasMoreTokens()) return Integer.parseInt(st.nextToken());
	         st = new StringTokenizer(in.readLine());
	         return nextInt();
	     }
	     String nextStr() throws Throwable{
	         if(st.hasMoreTokens()) return st.nextToken();
	         st = new StringTokenizer(in.readLine());
	         return nextStr();
	     }
	}
	public ArrayList<Column> GetFromSchema(String tablename)throws Throwable{
		try{
			Escanner sc = new Escanner();
			in = new BufferedReader(new FileReader("currentdatabase.txt"));
			String dataname= sc.nextStr();
			String path=System.getProperty("user.dir");
			path = path+"\\"+ dataname + "\\" + tablename + "schema.xml" ;
			path = path.replaceAll("\\\\", "\\\\\\\\");
			@SuppressWarnings("resource")
			BufferedReader in =new BufferedReader(new FileReader(path));
			StringBuilder out = new StringBuilder();
	        String line;
	        while ((line = in.readLine()) != null) {
	            out.append(line);
	        }
	       String xml = out.toString();
	       @SuppressWarnings("unchecked")
	       ArrayList<Column> columns = (ArrayList<Column>) xstream.fromXML(xml);
	       return columns;
		}
		catch(FileNotFoundException e){
			return null;
		}
	}
	public void NewTableSchema(String tablename,ArrayList<Column> columns) throws Throwable{
		Escanner sc = new Escanner();
		in = new BufferedReader(new FileReader("currentdatabase.txt"));
		String dataname= sc.nextStr();
		String path=System.getProperty("user.dir");
		path = path+"\\"+ dataname + "\\" + tablename + "schema.xml" ;
		path = path.replaceAll("\\\\", "\\\\\\\\");
		Writer output = null;
        File file = new File(path);
        output = new BufferedWriter(new FileWriter(file));
    	String xml = xstream.toXML(columns);
        output.write(xml);
        output.close();
		
	}
//	public static void main(String args[]) throws Throwable{
//		CreateDatabase n = new CreateDatabase("data3");
//		LogicalValidation f= new LogicalValidation();
//		Schema s = new Schema();
//		ArrayList<Column> columns   = new ArrayList<Column>();
////		columns.add(new Column("ff","varchar",255));
////		columns.add(new Column("gg","varchar",255));
////		columns.add(new Column("hh","varchar",255));
//		columns.add(new Column("rr","varchar",255));
//		//s.NewTableSchema("table1", columns);
//		if(f.update(columns,"gg>5","table1")==null)
//			System.out.println("ok");
//		
//	}
}
