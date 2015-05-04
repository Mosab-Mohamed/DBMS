package DBMS ;

import org.w3c.dom.*;
import org.xml.sax.InputSource;

import java.util.*;

import javax.xml.parsers.*;

public class ReadXml implements dbms{

	public static void main(String[] args) {

		ReadXml r = new ReadXml();
		r.start();
		
	}

	void start(){
		
		System.out.println(selectElement("x1", "type", '=', "3", "a.xml"));
	}
	
	private String selectElement(String elementName,String selectedElement,char condition,String value,String path) {
		//convert the xml file to Dom object to parse date
		Document doc = getDocument(path);
		NodeList matchNodes = doc.getElementsByTagName(selectedElement);
		boolean happened = false ;
		String returnedWord = "" ;
		String collector ;
		
		if(elementName.equals("*") && selectedElement==null ){
			
			NodeList selectedNode = doc.getDocumentElement().getChildNodes() ;
			
			for(int i=0 ; i<selectedNode.getLength() ; i++){
				
				NodeList siblingChilds = selectedNode.item(i).getChildNodes();
				collector = "";
				
				for(int j=0 ; j<siblingChilds.getLength() ; j++){
					if(siblingChilds.item(j).getTextContent()!=null){
						happened = true ;
						if(j!=siblingChilds.getLength()-1)
							collector += siblingChilds.item(j).getTextContent().trim()+" ";
						else if(j==siblingChilds.getLength()-1 && i!=selectedNode.getLength()-1)
							collector += siblingChilds.item(j).getTextContent().trim()+"\n";
						else
							collector += siblingChilds.item(j).getTextContent().trim();
					}
				}
				collector = collector.trim();
				returnedWord = returnedWord + collector+"\n";
				returnedWord = returnedWord.replaceAll("\n\n", "\n");
			}
		}
		
		else if(elementName.equals("*") && selectedElement!=null ){
			
			for(int i=0 ; i<matchNodes.getLength() ; i++){
				
				Node selectedNode = matchNodes.item(i);
				collector = "";
				
				if(condition == '>' ){
					int intValue1 = Integer.parseInt(value);
					
					if(Integer.parseInt(selectedNode.getTextContent()) > intValue1){
						NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
						
						for(int j=0 ; j<siblingChilds.getLength() ; j++){
							
							if(siblingChilds.item(j).getTextContent()!=null){
								happened= true;
								if(j!=siblingChilds.getLength()-1)
									collector += siblingChilds.item(j).getTextContent().trim()+" ";
								else if(j==siblingChilds.getLength()-1 && i!=matchNodes.getLength()-1)
									collector += siblingChilds.item(j).getTextContent().trim()+"\n";
								else
									collector += siblingChilds.item(j).getTextContent().trim();
							}
						}
					}
				}
					
				else if(condition =='<'){
					int intValue2 = Integer.parseInt(value);
					
					if(Integer.parseInt(selectedNode.getTextContent()) < intValue2){
						NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
						
						for(int j=0 ; j<siblingChilds.getLength() ; j++){
							
							if(siblingChilds.item(j).getTextContent()!=null){
								happened= true;
								if(j!=siblingChilds.getLength()-1)
									collector += siblingChilds.item(j).getTextContent().trim()+" ";
								else if(j==siblingChilds.getLength()-1 && i!=matchNodes.getLength()-1)
									collector += siblingChilds.item(j).getTextContent().trim()+"\n";
								else
									collector += siblingChilds.item(j).getTextContent().trim();
							}
						}
					}
				}
				
				else if(condition == '='){
					if(selectedNode.getTextContent().equals(value)){
						
						NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
						
						for(int j=0 ; j<siblingChilds.getLength() ; j++){
							
							if(siblingChilds.item(j).getTextContent()!=null){
								happened= true;
								if(j!=siblingChilds.getLength()-1)
									collector += siblingChilds.item(j).getTextContent().trim()+" ";
								else if(j==siblingChilds.getLength()-1 && i!=matchNodes.getLength()-1)
									collector += siblingChilds.item(j).getTextContent().trim()+"\n";
								else
									collector += siblingChilds.item(j).getTextContent().trim();
							}
						}
						
					}
				}
				collector = collector.trim();
				returnedWord = returnedWord + collector+"\n";
			}
		} 

		else{
			
			for(int i=0 ; i<matchNodes.getLength() ; i++){
				
				Node selectedNode = matchNodes.item(i);
				collector = "";
				
				if(condition == '>' ){
					int intValue1 = Integer.parseInt(value);
					
					if(Integer.parseInt(selectedNode.getTextContent()) > intValue1){
						NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
						
						for(int j=0 ; j<siblingChilds.getLength() ; j++){
							
							if(siblingChilds.item(j).getNodeName().equals(elementName)){
								collector += siblingChilds.item(j).getTextContent()+"\n";
								happened = true;
							}
						}
					}
				}
					
				else if(condition =='<'){
					int intValue2 = Integer.parseInt(value);
					
					if(Integer.parseInt(selectedNode.getTextContent()) < intValue2){
						NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
						
						for(int j=0 ; j<siblingChilds.getLength() ; j++){
							
							if(siblingChilds.item(j).getNodeName().equals(elementName)){
								collector += siblingChilds.item(j).getTextContent()+"\n";
								happened = true;
							}
						}
					}
				}
				
				else if(condition == '='){
					if(selectedNode.getTextContent().equals(value)){
						
						NodeList siblingChilds = selectedNode.getParentNode().getChildNodes() ;
						
						for(int j=0 ; j<siblingChilds.getLength() ; j++){

							if(siblingChilds.item(j).getNodeName().equals(elementName)){
								collector += siblingChilds.item(j).getTextContent()+"\n";
								happened = true;
							}
						}
					}
				}
				collector = collector.trim();
				returnedWord = returnedWord + collector+"\n";
			}
		}
		
		returnedWord = returnedWord.trim();
		returnedWord =returnedWord.replaceAll("  ", " ");
		
		if(happened)
			return returnedWord ;
		else 
			return NOT_MATCH_CRITERIA ;
	}
	
	private String deleteElement(String deletedElement,String elementName,char condition,String value,String path) {
		//convert the xml file to Dom object to parse date
		Document doc = getDocument(path);
		NodeList matchNodes = doc.getElementsByTagName(elementName);
		boolean happened = false ;
		
		 //if the root is the deletedElement
		if(deletedElement.equals("*") && elementName==null ){
			doc.removeChild(doc.getDocumentElement());
			happened=true;
		}
		
		else if(elementName != null){
			
			for(int i=matchNodes.getLength()-1 ; i>=0 ; i--){
				Node deletedNode = matchNodes.item(i);
				
				if(condition == '>' ){
					int intValue1 = Integer.parseInt(value);
					
					if(Integer.parseInt(deletedNode.getTextContent()) > intValue1){
						Element parentNode = (Element)deletedNode.getParentNode() ;
						doc.getDocumentElement().removeChild(parentNode);
						happened = true;
					}
				}
					
				else if(condition =='<'){
					int intValue2 = Integer.parseInt(value);
					
					if(Integer.parseInt(deletedNode.getTextContent()) < intValue2){
						Node parentNode = deletedNode.getParentNode() ;
						doc.getDocumentElement().removeChild(parentNode);
						happened = true;
					}
				}
				
				else if(condition == '='){
					if(deletedNode.getTextContent().equals(value)){
						Node parentNode = deletedNode.getParentNode() ;
						doc.getDocumentElement().removeChild(parentNode);
						happened = true;
						System.out.println(doc.getElementsByTagName("shape").getLength());
					}
				}
				
			}
		}
		
		if(happened)
			return Con_Delete;
		else
			return NOT_MATCH_CRITERIA;
		
	}

	private Document getDocument(String s) {
		
		try{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setIgnoringComments(true);
		factory.setIgnoringElementContentWhitespace(true);
		factory.setValidating(true);
		
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		return builder.parse(new InputSource(s));
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	public String input(String input) {
		// TODO Auto-generated method stub
		return null;
	}

}
