package DBMS;
import java.util.ArrayList;


public interface XMLParserInterface {

	public static final String TABLE_NOT_FOUND = "This Table doesn't exists in database";
	public static final String COLUMN_NOT_FOUND = "This column doesn't exists in this table";
	public static final String TABLE_ALREADY_EXISTS = "This Table already exists";
	public static final String PARSING_ERROR = "bad formated input";
	public static final String DB_NOT_FOUND = "No database exists";
	public static final String COLUMN_TYPE_MISMATCH = "Entered value doesn't match column type";
	public static final String Con_DB = "DB created";
	public static final String Con_Table = "Table created";
	public static final String Con_insert = "insertion Complete";
	public static final String Con_Delete = "Row/s deleted";
	public static final String Con_Update = "Row/s Updated";
	public static final String NOT_MATCH_CRITERIA = "no row exists with this criteria";
	
	/*
	 * This Function inserts a new row to the table
	 * ident is the name of the row parent node
	 * columns array contains columns of the table
	 * columns must contain an ID column
	 * values array contains values of the columns with the same index
	 * if a column do not have value insert its value with a string equal "null"
	 */
	public void insertToXML(String ident,ArrayList<Column> arr);
	
	/*
	 * This function updates a specific cell in the table
	 * you should send the id of the row you want to change in it
	 * you should send the name of the column you want to change in it
	 * finally, you should send the new value
	 */
	//public String updateXML(String id, String column, String newValue);
	public void Create(String ident,ArrayList<Column> arr);
	
	public String selectElement(String elementName, String selectedElement, char condition, String value) throws Throwable;
	public String deleteElement(String deletedElement,String elementName ,char condition, String value) throws Throwable;
}
