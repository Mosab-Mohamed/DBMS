package DBMS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;



public  class LogicalValidation implements dbms {
	public boolean existdatabase(){
		try{
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader("currentdatabase.txt"));
			if(in.readLine().equals(null))
				return false;
			return true;
		}
		catch(FileNotFoundException e){
			return false;
		} catch (NullPointerException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
	}
	public boolean existtable(String tablename) throws Throwable{
		Schema s = new Schema();
		if(!existdatabase())
			return false;
		if(s.GetFromSchema(tablename)==null)
			return false;
		return true;
	}
	@Override
	public String input(String input) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String createDatabase(String databaseName) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String insert(ArrayList<Column> arr, String table_name) throws Throwable {
		if(!existtable(table_name))
			return TABLE_NOT_FOUND;
		if(!existdatabase())
			return DB_NOT_FOUND;
		
		Schema s = new Schema();
		ArrayList<Column> columnsclass = new ArrayList<Column>();
		columnsclass = s.GetFromSchema(table_name);
		for(int i=0;i<arr.size();i++){
			boolean flag=false;
			for(int j=0;j<columnsclass.size();j++){
				if(arr.get(i).name.equals( columnsclass.get(j).name)){
					flag=true;
					if(columnsclass.get(j).DataType.equals("varchar")){
						try {
							   Integer.parseInt(arr.get(i).DataType);
							   return COLUMN_TYPE_MISMATCH;
							} catch (NumberFormatException e) {
							    
							}
					}
					else if(columnsclass.get(j).DataType.equals("int")){
						try {
							arr.get(j).DataType=arr.get(j).DataType.replace(" ", "");
						   Integer.parseInt(arr.get(i).DataType);
						} catch (NumberFormatException e) {
						    return COLUMN_TYPE_MISMATCH;
						}
						
					}
					break;
				}
			}
			if(!flag)
				return COLUMN_NOT_FOUND;
		}
		return null;	
	}
	@Override
	public String delete(String inequality, String table_name) throws Throwable {
		if(!existtable(table_name))
			return TABLE_NOT_FOUND;
		if(!existdatabase())
			return DB_NOT_FOUND;
		
		if(inequality!=null){
			Schema s = new Schema();
			ArrayList<Column> columnsclass = new ArrayList<Column>();
			columnsclass = s.GetFromSchema(table_name);
			String[]tokens = inequality.split("\\W");
			String ColumnName = tokens[0];
			boolean flag=false;
			for(int i=0;i<columnsclass.size();i++){
				if(columnsclass.get(i).name.equals(ColumnName))
					flag=true;
			}
			if(!flag)
				return COLUMN_NOT_FOUND;
		}
		return null;
	}
	@Override
	public String select(ArrayList<Column> arr, String inequality,
			String table_name) throws Throwable {
		if(!existtable(table_name))
			return TABLE_NOT_FOUND;
		if(!existdatabase())
			return DB_NOT_FOUND;
	
		Schema s = new Schema();
		ArrayList<Column> columnsclass = new ArrayList<Column>();
		columnsclass = s.GetFromSchema(table_name);
		for(int i=0;i<arr.size();i++){
			boolean flag=false;
			for(int j=0;j<columnsclass.size();j++){
				if(arr.get(i).name.equals( columnsclass.get(j).name)){
					flag=true;
					break;
				}
			}
			if(!flag)
				return COLUMN_NOT_FOUND;
		}
		if(inequality!=null){
			boolean flag = false;
			String[]tokens = inequality.split("\\W");
			String ColumnName = tokens[0];
			for(int i=0;i<columnsclass.size();i++){
				if(columnsclass.get(i).name.equals(ColumnName))
					flag=true;
			}
			if(!flag)
				return COLUMN_NOT_FOUND;
		}
		return null;
	}
	@Override
	public String createTable(ArrayList<Column> arr, String table_name) throws Throwable {
		if(!existdatabase())
			return DB_NOT_FOUND;
		if(existtable(table_name))
			return TABLE_ALREADY_EXISTS;
		return null;
	}
	@Override
	public String update(ArrayList<Column> arr, String inequality,
			String table_name) throws Throwable {
		if(!existtable(table_name))
			return TABLE_NOT_FOUND;
		if(!existdatabase())
			return DB_NOT_FOUND;
		if(!existtable(table_name))
			return TABLE_NOT_FOUND;
		Schema s = new Schema();
		ArrayList<Column> columnsclass = new ArrayList<Column>();
		columnsclass = s.GetFromSchema(table_name);
		for(int i=0;i<arr.size();i++){
			boolean flag=false;
			for(int j=0;j<columnsclass.size();j++){
				if(arr.get(i).name.equals( columnsclass.get(j).name)){
					flag=true;
					if(columnsclass.get(j).DataType.equals("varchar")){
						try {
							   Integer.parseInt(arr.get(i).DataType);
							   return COLUMN_TYPE_MISMATCH;
							} catch (NumberFormatException e) {
							    
							}
					}
					else if(columnsclass.get(j).DataType.equals("int")){
						try {
							arr.get(j).DataType = arr.get(j).DataType.replace(" ", "");
						   Integer.parseInt(arr.get(i).DataType);
						} catch (NumberFormatException e) {
						    return COLUMN_TYPE_MISMATCH;
						}
						
					}
					break;
				}
			}
			if(!flag)
				return COLUMN_NOT_FOUND;
		}
		if(inequality!=null){
			boolean flag = false;
			String[]tokens = inequality.split("\\W");
			String ColumnName = tokens[0];
			for(int i=0;i<columnsclass.size();i++){
				if(columnsclass.get(i).name.equals(ColumnName))
					flag=true;
			}
			if(!flag)
				return COLUMN_NOT_FOUND;
		}
		return null;
	}

}
