package cn.hdu.serviceImp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import cn.hdu.service.WriteWork;
import cn.hdu.timentity.Declaration;
import cn.hdu.timentity.Location;
import cn.hdu.timentity.Nail;
import cn.hdu.timentity.Template;
import cn.hdu.timentity.TimedAutomata;
import cn.hdu.timentity.Transition;
import cn.hdu.tool.FixTool;

public class WriteWorkImpl implements WriteWork{

	public static void main(String[] args) {
		Document document = DocumentHelper.createDocument();
		Element root = createRoot(document);
		
		File file=new File("resources/output/test.xml");
		OutputFormat format=OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer;
		try {
			writer=new XMLWriter(new FileOutputStream(file),format);
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void wirte(TimedAutomata timedAutomata) {
		// TODO Auto-generated method stub
		Document document = DocumentHelper.createDocument();
		Element root = createRoot(document);
		createXML(root, timedAutomata);
		
		File file=new File("resources\\output\\test_gps_new.xml");
		OutputFormat format=OutputFormat.createPrettyPrint();
		format.setEncoding("UTF-8");
		XMLWriter writer;
		try {
			writer=new XMLWriter(new FileOutputStream(file),format);
			writer.setEscapeText(false);
			writer.write(document);
			writer.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	private static Element createRoot(Document document) {
		// 创建Document对象
        Element root = document.addElement("nta");
        return root;
	}
	
	private static void createXML(Element root,TimedAutomata timedAutomata) {
		Element declaration = root.addElement("declaration");
		Declaration decl = timedAutomata.getDeclaration();
		String dec = "";
		if(decl.getChans() != null)
		dec += decl.getChans();
		if(decl.getVariables() != null)
		dec += decl.getVariables();
		declaration.setText(dec);
		
		for(Template template : timedAutomata.getTemplates()) {
			Element element = root.addElement("template");
			
			element.addElement("name").setText(createCDA(template.getName()));
//			if(template.getDeclaration() != null) {
//				Element decElement = element.addElement("declaration");
//				decElement.setText(template.getDeclaration());
//			}
			for(Location location : template.getLocations()) {
				Element locElement = element.addElement("location");
				
				locElement.addAttribute("id", "id" + location.getId());
				locElement.addAttribute("y", location.getX());
				locElement.addAttribute("x", location.getY());
				
				Element nameElement = locElement.addElement("name");
				nameElement.setText(createCDA(location.getName()));
				nameElement.addAttribute("y", location.getX());
				nameElement.addAttribute("x", location.getY());
				
				if(location.getInvariant() != null) {
					Element labelElement = locElement.addElement("label");
					labelElement.setText(createCDA(location.getInvariant()));
					labelElement.addAttribute("y", location.getX());
					labelElement.addAttribute("x", location.getY());
					labelElement.addAttribute("kind", "invariant");
				}
			}
			element.addElement("init").addAttribute("ref", "id" + template.getInitRefID());
			
			for(Transition transition : template.getTransitions()) {
				int count = 1; 
				
				Element tranElement = element.addElement("transition");
				tranElement.addElement("source").addAttribute("ref", "id" + transition.getSource());
				tranElement.addElement("target").addAttribute("ref", "id" + transition.getTarget());
				int sourceX = Integer.valueOf(getLoc(template, transition.getSource()).getX());
				int sourceY =  Integer.valueOf(getLoc(template, transition.getSource()).getY());
				
				Location location = getLoc(template, transition.getTarget());
				
				
				int targetX = Integer.valueOf(getLoc(template, transition.getTarget()).getX());
				int targetY = Integer.valueOf(getLoc(template, transition.getTarget()).getY());
				
				int midX = (sourceX + targetX) / 2;
				int midY = (sourceY + targetY) / 2;
						
				if(transition.getSelect() != null) {
					Element labelElement = tranElement.addElement("label");
					labelElement.addAttribute("y", "50");
					labelElement.addAttribute("x", "50");
					labelElement.addAttribute("kind", "select");
					labelElement.setText(transition.getSelect());
				}
				
				if(transition.getSynchronisation() != null) {
					Element labelElement = tranElement.addElement("label");
					if(transition.getNails().size() == 0) {
						labelElement.addAttribute("y", String.valueOf(midX + GAP*count));
						labelElement.addAttribute("x", String.valueOf(midY + GAP*count));
					}else {
						Nail nail = transition.getNails().get(0);
						int X = Integer.valueOf(nail.getX()) + GAP*count;
						int Y = Integer.valueOf(nail.getY()) + GAP*count;
						
						labelElement.addAttribute("y", String.valueOf(Y));
						labelElement.addAttribute("x", String.valueOf(X));
					}
					
					count++;
					labelElement.addAttribute("kind", "synchronisation");
					labelElement.setText(createCDA(transition.getSynchronisation()));
				}
				
				if(transition.getGuard() != null) {
					Element labelElement = tranElement.addElement("label");
					if(transition.getNails().size() == 0) {
						labelElement.addAttribute("y", String.valueOf(midX + GAP*count));
						labelElement.addAttribute("x", String.valueOf(midY + GAP*count));
					}else {
						Nail nail = transition.getNails().get(0);
						int X = Integer.valueOf(nail.getX()) + GAP*count;
						int Y = Integer.valueOf(nail.getY()) + GAP*count;
						
						labelElement.addAttribute("y", String.valueOf(Y));
						labelElement.addAttribute("x", String.valueOf(X));
					}
					
					count++;
				    labelElement.addAttribute("kind", "guard");
				   
				    labelElement.setText(createCDA(transition.getGuard()));
			    }
				
				if(transition.getAssignment() != null) {
					Element labelElement = tranElement.addElement("label");
					
					if(transition.getNails().size() == 0) {
						labelElement.addAttribute("y", String.valueOf(midX + GAP*count));
						labelElement.addAttribute("x", String.valueOf(midY + GAP*count));
					}else {
						Nail nail = transition.getNails().get(0);
						int X = Integer.valueOf(nail.getX()) + GAP*count;
						int Y = Integer.valueOf(nail.getY()) + GAP*count;
						
						labelElement.addAttribute("y", String.valueOf(Y));
						labelElement.addAttribute("x", String.valueOf(X));
					}
					
				    labelElement.addAttribute("kind", "assignment");
				    labelElement.setText(transition.getAssignment());
				}
				
				if(transition.getNails().size() != 0) {
					for(Nail nail : transition.getNails()) {
						Element nailElement = tranElement.addElement("nail");
						nailElement.addAttribute("y", nail.getY());
						nailElement.addAttribute("x", nail.getX());
					}
					
				}
			}
		}
	  if(timedAutomata.getSystemStatment().getSystem() != null) {
		String string = "";
		string += FixTool.FixString(timedAutomata.getSystemStatment().getTemplate());
		string += FixTool.FixString(timedAutomata.getSystemStatment().getSystem());
		
		Element systemElement = root.addElement("system");
		systemElement.setText(string);
	  }	
	}
	
	private static Location getLoc(Template template,String id) {
		for(Location location : template.getLocations()) {
			if(id.equals(location.getId())) 
				return location;
		}
		return null;
	}
	
	private static String createCDA(String name) {
		return "<![CDATA[" + name + "]]>";
	}
	
	private final static int GAP = 5;
}
