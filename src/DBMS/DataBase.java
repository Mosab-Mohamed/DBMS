package DBMS;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.codehaus.stax2.validation.ValidationContext;

public class DataBase implements dbms {
	
	
	
	@Override
	public String input(String input) throws Throwable {
		LogicalValidation validation  = new LogicalValidation();
		Schema ss = new Schema();
		//create database
		if(input.toLowerCase().matches("create(\\s+)database(\\s+)(\\w*+)(\\;)")){
			String[] splitString = (input.split("\\s+"));
			String table_name=splitString[splitString.length-1].replace(";","");
//			if(validation.existdatabase()){
//				return "Already exist";
//			}
//			else{
				return(createDatabase(table_name));
//			}
		}
		
		//create table
		else if(input.toLowerCase().matches("create(\\s+)table(\\s+)(\\w*+)(\\()(\\w*+)(\\s+)[()\\w]*+((\\,)(\\w*+)(\\s+)[;'()\\w]*+)*")){
			ArrayList<Column> arr=new ArrayList<Column>();
			String column,type;
			int size=0;
			String parse=input.replace(",", " ");
			String[] splitString = (parse.split("\\s+"));
			String nfirst=splitString[2].replace("("," ");
			String[] s=nfirst.split("\\s+");	
			String table_name=s[0];		
			column=s[1];
			for(int i=3;i<splitString.length;i++){
				if(i%2==1){
					splitString[i]=splitString[i].replace(")", " ");
					splitString[i]=splitString[i].replace("(", " ");
					String[] h=null;
					 h= splitString[i].split("\\s+");
					type=h[0].replace(" ", "");
					if(type.equals("int")){
							size=0;
					}
					else{
						size=Integer.parseInt(h[1]);				

					}
					if(size==0){
						Column c=new Column(column,type);
						arr.add(c);
					}
					else{
						Column c=new Column(column,type,size);
			 			arr.add(c);
					}
					
				}
				else{
					column=splitString[i].replace(" ", "");
				}
			}		
			if(validation.createTable(arr,table_name)!=null)
			{
				return validation.createTable(arr,table_name);
			}
			else{
				ss.NewTableSchema(table_name, arr);
				return(createTable(arr, table_name));
				//make a new object of your class and call function from your class that returns 
				//an answer string
			}
		}
		
		
		//select
		else if(input.toLowerCase().matches("select(\\s+)(\\*)(\\s+)from(\\s+)([;\\w]*+)")){
			ArrayList<Column> arr=new ArrayList<Column>();
			String[] splitString = (input.split("\\s+"));
			String table_name=splitString[3].replace(";","");
			if(validation.select(arr,null,table_name)!=null)
				return validation.select(arr,null,table_name);
			else
				return(select(arr, null , table_name));
				//make a new object of your class and call function from your class that returns 
				//an answer string
		}
		else if(input.toLowerCase().matches("select(\\s+)([*\\w]*+)([,\\w*+]*)(\\s+)from(\\s+)(\\w*+)(\\s+)where([\\s+\\w]*+)([=><])(['\\s+\\w]*+)")){
			ArrayList<Column> arr=new ArrayList<Column>();
			input=input.replace(",", " ");
			String[] splitString = (input.split("\\s+"));
			int start=input.indexOf(splitString[4]);
			String ineq=input.substring(start+6);
			
			if(splitString[1].equals("*")){
				if(validation.select(arr,ineq,splitString[3])!=null)
					return validation.select(arr,ineq,splitString[3]);
				else
					return(select(arr,ineq,splitString[3]));
					//make a new object of your class and call function from your class that returns 
					//an answer string
			}
			else{		
				int i;
				for( i=1;;i++){
					if(splitString[i].toLowerCase().replace(" ", "").equals("from")){
						break;
					}
					splitString[i]=splitString[i].replace(" ", "");
					Column c=new Column(splitString[i],null);
					arr.add(c);
				}
				if(validation.select(arr,ineq,splitString[i+1])!=null)
					return validation.select(arr,ineq,splitString[i+1]);
				//else
					//make a new object of your class and call function from your class that returns 
					//an answer string
			}	
		}
		
		
		//insert
		else if(input.toLowerCase().matches("insert(\\s+)into(\\s+)(\\w*+)(\\s+)values(\\s+)(\\()(['\\s+\\w]*+)((\\,)([');\\s+\\w]*+))*")){
			//ArrayList<Column> arr=new ArrayList<Column>();
			String[] values=new String[100000];
			String[] split=input.split("\\s+");
			String table_name=split[2];
			
			//////
			ArrayList<Column> arr2=new ArrayList<Column>();
			Schema s = new Schema();										// get column names from schema 
			arr2 = s.GetFromSchema(table_name);
			if(!(validation.existtable(table_name)))
				return TABLE_NOT_FOUND;
			/////
			String[] splitString = (input.split(","));
			int val=0;
			splitString[0]=splitString[0].replace("(",",");
			String[]k=splitString[0].split(",");
			values[val]=k[1].replace("(", "").replaceAll("'", "");
			
			
			////
			arr2.get(0).DataType = values[val];                            // assign the first value to new array
			////
			val++;
			
			for(int i=1;i<splitString.length;i++){	
				String p=splitString[i].replace("(","");
				values[val]=p.replace(")","");
				values[val]=values[val].replace(";","");
				values[val]=values[val].replace("'","");
				//values[val]=values[val].replace(" ","");//
				//Column c=new Column(null,values[val]);                  // arr is not used anymore in this insert 
				//arr.add(c);
				/////
				arr2.get(i).DataType = values[val] ;                      // << assign value to the new array instead of datatype
				////
				val++;
			}
			///// (
			if(validation.insert(arr2, table_name)!=null)                // insert arr2 include all column names and values
				return validation.insert(arr2, table_name);
			else{
				return(insert(arr2, table_name));
			}
			//// )
//			if(validation.insert(arr, table_name)!=null)
//				return validation.insert(arr, table_name);
//			else
//				return(insert(arr, table_name));
				//make a new object of your class and call function from your class that returns 
				//an answer string
		}
		
		else if(input.toLowerCase().matches("insert(\\s+)into(\\s+)(\\w*+)(\\s+)(\\()([\\s+\\w]*+)((\\,)([\\s+\\w]*+))*([)\\s+])*values(\\s+)(\\()(['\\s+\\w]*+)((\\,)([);'\\s+\\w]*+))*")){
			ArrayList<Column> arr=new ArrayList<Column>();
			
			String[] columns=new String[100000];
			String[] values=new String[100000];
			input=input.replace(")",",");
			String[] splitString = (input.split(","));
			String[] split=input.split("\\s+");
			String table_name=split[2];
			int val=0,col=0;
			int flag=0;
			splitString[0]=splitString[0].replace("(",",");
			String[]k=splitString[0].split(",");
			columns[col]=k[1].replace("(", "");
			col++;
			for(int i=1;i<splitString.length;i++){
				if(splitString[i].toLowerCase().contains("values")){
					flag=1;
					splitString[i]=splitString[i].replace("(",",");
					String[]p=splitString[i].split(",");
					values[val]=p[1].replace("(", "");
					val++;
					i++;
				}
				if(flag==0){
					String p=splitString[i].replace("(","");
					columns[col]=p.replace(")","");
					columns[col]=columns[col].replace(";","");
					columns[col]=columns[col].replace("'","");
					columns[col]=columns[col].replace(" ","");
					col++;
				}
				else if(val<col){
					String p=splitString[i].replace("(","");
					values[val]=p.replace(")","");
					values[val]=values[val].replace(";","");
					values[val]=values[val].replace("'","");
					//values[val]=values[val].replace(" ","");//
					val++;
				}
			}
			for(int i=0;i<col;i++)
			{
				Column c= new Column(columns[i],values[i]);
				arr.add(c);
			}

			if(validation.insert(arr, table_name)!=null)
				return validation.insert(arr, table_name);
			else
				return(insert(arr, table_name));
				//make a new object of your class and call function from your class that returns 
				//an answer string
		}
		
		
		//delete
		else if(input.toLowerCase().matches("delete(\\s+)from(\\s+)([;\\w]*+)")){
			String[] splitString = (input.split("\\s+"));
			String table_name=splitString[2];
			table_name=table_name.replace(";", "");
			if(validation.delete(null,table_name)!=null)
				return validation.delete(null,table_name);
			else
				return delete(null, table_name);
				//make a new object of your class and call function from your class that returns 
				//an answer string
		}
		else if(input.toLowerCase().matches("delete(\\s+)(\\*)(\\s+)from(\\s+)([;\\w]*+)")){
			String[] splitString = (input.split("\\s+"));
			String table_name=splitString[3];
			table_name=table_name.replace(";", "");
			if(validation.delete(null,table_name)!=null)
				return validation.delete(null,table_name);
			else
				return delete(null, table_name);
				//make a new object of your class and call function from your class that returns 
				//an answer string
		}
		else if(input.toLowerCase().matches("delete(\\s+)from(\\s+)(\\w*+)(\\s+)where([\\s+\\w]*+)([=><])([';\\s+\\w]*+)")){
			String[] splitString = (input.split("\\s+"));
			String table_name=splitString[2];
			String ineq=splitString[4].replace(";", "");
			table_name=table_name.replace(";", "");
			if(validation.delete(ineq,table_name)!=null)
				return validation.delete(ineq,table_name);
			else
				return delete(ineq, table_name);
				//make a new object of your class and call function from your class that returns 
				//an answer string
		}
		
		
		//update
		else if(input.toLowerCase().matches("update(\\s+)(\\w*+)(\\s+)set(\\s+)([\\s+\\w]*+)(\\=)(['\\s+\\w]*+)(([,\\s+\\w]*+)(\\=)(['\\w]*+))*(\\s+)where([\\s+\\w]*+)([=><])([;'\\s+\\w]*+)")){
			ArrayList<Column> arr=new ArrayList<Column>();
			String[] splitString=input.split("\\s+");			
			String table_name=splitString[1];
			table_name=table_name.replace(" ", "");
			int i;
			for( i=3;i<splitString.length;i++){
				if(splitString[i].toLowerCase().replace(" ", "").equals("where")){
					break;
				}
				String[] s=splitString[i].split("=");
				s[0]=s[0].replace(" ", "");
				s[1]=s[1].replace(" ", "");
				s[1]=s[1].replace("'", "");
				s[1]=s[1].replace(",", "");
				Column c=new Column(s[0],s[1]);
				arr.add(c);
			}
			int start=input.indexOf(splitString[i+1]);
			String ineq=input.substring(start).replace(";","");
			if(validation.update(arr, ineq, table_name)!=null)
				return validation.update(arr, ineq, table_name);
			else
				return(update(arr, ineq , table_name));
				//make a new object of your class and call function from your class that returns 
				//an answer string
		}
		else{
			return dbms.PARSING_ERROR;
		}
		
		return "ffff";
	}


	@Override
	public String createDatabase(String databaseName) throws Throwable {
		File dir = new File(databaseName);
		dir.mkdir();
		PrintWriter writer = new PrintWriter("currentdatabase.txt", "UTF-8");
		writer.println(databaseName);
		writer.close();
		
		return Con_DB;
		
	}
	@Override
	public String insert(ArrayList<Column> arr, String table_name) throws Throwable {
		
		for (Column c : arr)
		{
			if (c.size!=0)
			{
				if (c.name.length()>c.size)
				{
					return "OverFlow in size";
				}
			}
		}
		Schema sc = new Schema();
		ArrayList<Column> arr2 = new ArrayList<Column>();
		arr2 = sc.GetFromSchema(table_name);
		for (Column s : arr2)
		{
			s.DataType=null;
		}
		XMLParser x = new XMLParser(DataBaseName()+"\\"+table_name+".xml");
		/////////
		x.insertToXML(table_name,arr2);
		x.updateInsert(arr,table_name);
		////////
		//x.insertToXML(table_name,arr);
		
		return Con_insert;
	}
	@Override
	public String delete(String inequality, String table_name) throws Throwable {
		
		XMLParser x = new XMLParser(DataBaseName()+"\\"+table_name+".xml");
		
		String deletedElement = new String(), elementName, value, path;
		char condition = 0;
		if(inequality == null)
		{
			elementName=null;
			condition='#';
			deletedElement="*";
			value=null;
		}
		else
		{
			if(inequality.contains("="))
				condition='=';
			else if(inequality.contains(">"))
				condition='>';
			else if(inequality.contains("<"))
				condition='<';
			String array[]= inequality.split("[<=>]");
			elementName=array[0];
			value=array[1].replaceAll("'", "");
		}
		return x.deleteElement(deletedElement,elementName,condition,value);
		
	}
	@Override
	public String update(ArrayList<Column> arr,String inequality,String table_name) throws Throwable {
		XMLParser x = new XMLParser(DataBaseName()+"\\"+table_name+".xml");
		
		inequality = inequality.replaceAll("\\s+","");
		String[] inequal = inequality.split("=");
		
		boolean matched = x.updateXML(inequal[0] , inequal[1], arr);
		
		if (matched)
			return Con_Update;
		else 
			return NOT_MATCH_CRITERIA;
	}
	@Override
	public String createTable(ArrayList<Column> arr, String table_name) throws Throwable {
		
		XMLParser x = new XMLParser(DataBaseName()+"\\"+table_name+".xml");
		
		x.Create(table_name, arr);
		//x.insertToXML(table_name,arr);
		
		return Con_Table;
	}
	@Override
	public String select(ArrayList<Column> arr, String inequality,String table_name) throws Throwable {
		
		XMLParser x = new XMLParser(DataBaseName()+"\\"+table_name+".xml");
		String elementName, selectedElement, value;
		char condition = 0;
		
		if(arr.isEmpty())
			elementName="*";
		else
			elementName=arr.get(0).name;
		if(inequality == null)
		{
			condition='#';
			selectedElement=null;
			value=null;
		}
		else
		{
			if(inequality.contains("="))
				condition='=';
			else if(inequality.contains(">"))
				condition='>';
			else if(inequality.contains("<"))
				condition='<';
			String array[]= inequality.split("[<=>]");
			selectedElement=array[0];
			value=array[1].replace("'", "");
		}
		
		return x.selectElement(elementName, selectedElement, condition, value);
		
		
	}
	
	public String DataBaseName() throws IOException
	{
		BufferedReader in=new BufferedReader(new FileReader("currentdatabase.txt"));
		String databasename= in.readLine();
		return databasename;
	}
	
	public static void main(String[] args) throws Throwable{
		DataBase d=new DataBase();
		
//		System.out.println(d.input("CREATE DATABASE LAB3;"));
//		System.out.println(d.input("CREATE TABLE Persons(PersonID int,LastName varchar(255),FirstName varchar(255),Address varchar(255),City varchar(255));"));
//		System.out.println(d.input("INSERT INTO Persons VALUES (3,'ahmed','Mohsen','12 street','Cairo');"));
//		System.out.println(d.input("UPDATE Persons SET LastName=5, City='Hamburg' WHERE PersonID= 3;"));
//		System.out.println(d.input("INSERT INTO Persons (PersonID,LastName,FirstName)VALUES (1,'salem','Tamer');"));
		//System.out.println(d.input("DELETE * FROM Persons;"));
		System.out.println(d.input("SELECT * FROM Persons"));
	}
}
