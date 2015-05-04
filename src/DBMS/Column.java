package DBMS;

public class Column {
	String name;
	String DataType;
	int size;
	public Column(String n,String d,int s){
		name = n;
		DataType = d;
		size = s;
	}
	public Column(String n,String d){
		name = n;
		DataType = d;
		size=0;
	}
}
