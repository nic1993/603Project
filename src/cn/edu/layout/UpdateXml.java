package cn.edu.layout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import cn.hdu.seqentity.LifeLine;
import cn.hdu.seqentity.State;
import cn.hdu.timentity.Location;
import cn.hdu.timentity.Nail;
import cn.hdu.timentity.Template;
import cn.hdu.timentity.Transition;

import java.io.File;
import java.io.FileOutputStream;

public class UpdateXml {
	String route;
	Document dom;
	Element root;
	SAXReader reader = new SAXReader();	
	Template template;
	public UpdateXml(Template template) throws DocumentException
	{
		this.template = template;
  
	}
    public void update() throws Exception{

		int i = 0, j=0,n = 0, m = 0, p = 0, xxxx, yyyy, YYYY,k = 0;
		String XXX[] = new String[500];
		String YYY[] = new String[500];
		String xxx[] = new String[500];
		String yyy[] = new String[500];
		int[] A = new int[30];
	
//	    root=dom.getRootElement();
//	    Element nodes=root.element("nodes");
//		Element edges=root.element("edges");
//		List<Element> list=edges.elements("MarkovTransitionEdge");
//		Element MarkovStartNode=nodes.element("MarkovStartNode");//����ʼ�Ľڵ�
//		List<Element> nodeList=nodes.elements("MarkovNode");//�����Ľڵ����
//		List.add(MarkovStartNode);
//		List<Element> List=new ArrayList<Element>();
//		List.add(MarkovStartNode);
//		for(int count=0;count<nodeList.size();count++){
//			List.add(nodeList.get(count));
//		}
		Location midLocation;
		Transition midTransition;
//		List<Element> LIST=root.elements("template");//LIST��ʾXML�м���Template
//		List<Element> list=LIST.get(a).elements("transition");
// 		List<Element> List=LIST.get(a).elements("location");//List�����ŵ�ļ���
//		Element Template=List.get(0);//Templateָ���
//      Element template1=list.get(0);
		List<Location> List = template.getLocations();
		List<Transition> list = template.getTransitions();
 		Iterator it = List.iterator();
 		Iterator IT = list.iterator();
			// ͨ�õĵ�����iterator,��list����it��	
		    while(it.hasNext()) {// �ж��Ƿ�����һ��Ԫ��
					it.next();
					i++;
				}
		    while(IT.hasNext())
		    {
		    	IT.next();
		    	j++;//j������ÿ��Template�м�����
		    }
		    
         A=new TestGraph().init(template);
//         System.out.println("A"+A.length);
         for (int I = 0; I < 2 * i; I+=2) {
    		 A[I] += 15;//�������ǰ�ÿ�����X��������100����
    		 A[I+1]+=10;
    		 }
    
		 for (int I = 0; I < 2 * i; I++) {
		 A[I] *= 2.5;//�������ǰ�ÿ�����X��Y���궼������ͬ�ı��������ڲ��ı�����ͼ��ֵ�ǰ���£��Ŵ�����ͼ�����¼��ٱ�ǩ���ص�����Ϊ��ǩ�г���
		 }
			while (n < i) {
//				Template  = List.get(n);
				midLocation=List.get(n);
//				System.out.println(midElement.getClass().toString()+"88888");
				String xx = String.valueOf((int) A[m]);
				String yy = String.valueOf((int) A[m + 1] );
				String XX = String.valueOf((int) A[m]-30);
				String YY = String.valueOf((int) A[m + 1]-30 );
//				Template.attribute("y").setText(xx); 
//				Template.attribute("x").setText(yy);
				midLocation.setX(xx); 
				midLocation.setY(YY);
				m += 2;
				n++;
//				Template.element("name").attribute("y").setText(XX);
//				Template.element("name").attribute("x").setText(YY);
//				midElement.element("name").attribute("y").setText(xx);
//				midElement.element("name").attribute("x").setText(yy);
//				System.out.println("�ڵ������óɹ�������");
            }                                    	
			//Template �����ŵ�List��template1�����ű�list				
			n = 0;
			while (p < j && n < i) {
//				template1 = list.get(p);
//				Template = List.get(n);
//				if (template1.element("source").attribute("ref").getValue()
//						.equals(Template.attribute("id").getValue())) {	
//					XXX[p] = Template.attribute("x").getValue();
//					YYY[p] = Template.attribute("y").getValue();
				midTransition = list.get(p);//��ʾmidElement1��ʾ��
				midLocation = List.get(n);//midElement��ʾ�߱�ʾ��
				if (midTransition.getSource()
						.equals(midLocation.getId())) {	
					XXX[p] = midLocation.getX();
					YYY[p] = midLocation.getY();
					n = 0;
					p++;
				} else
					n++;
			}
			n = 0;
			p = 0;
			while (p < j && n < i) {
				midTransition = list.get(p);
				midLocation = List.get(n);
				if (midTransition.getTarget()
						.equals(midLocation.getId())) {
					xxx[p] = midLocation.getX();
					yyy[p] = midLocation.getY();
					n = 0;
					p++;
				} else
					n++;
			}
			
			HashMap<String, Integer> countMap = new HashMap<>();
			while (k < j) {
				
				midTransition = list.get(k);
				String source = midTransition.getSource();
				String end = midTransition.getTarget();
				
				String net = source + "To" + end;
				if(countMap.get(net) == null) {
					countMap.put(net, 1);
				}else {
					int time = countMap.get(net);
					countMap.put(net, ++time);
				}
			
				if(midTransition.getSource().equals
						(midTransition.getTarget()))//����е��Լ��ı�.Ĭ�ϳ�ʼͼ����nail��ǩ������Ͳ����Լ����Լ�
				{
					xxxx=Integer.valueOf(xxx[k])+30;
					yyyy=Integer.valueOf(xxx[k])-30;
					YYYY=Integer.valueOf(yyy[k])-30;
					String xxxxxx =String.valueOf(xxxx);
					String yyyyyy =String.valueOf(yyyy);
//					String xxxxxxx=String.valueOf(YYYY);	
//				    Element nail1=template1.addElement("nail");
//				    Element nail2=template1.addElement("nail");
//				nail1.addAttribute("x").setText(xxxxxx);
//				nail1.addAttribute("y").setText(xxxxxxx);
//				nail2.addAttribute("x").setText(yyyyyy);
//				nail2.addAttribute("y").setText(xxxxxxx);
				
//				System.out.println("���Լ��бߵĵ�ı�ǩ���óɹ�������");
				
				}	   
				else 
				{
					if(countMap.get(net) != 1) {
						int time = countMap.get(net);
						xxxx = (Integer.valueOf(xxx[k]) + Integer.valueOf(XXX[k])) / 2-30*(time - 1);
						yyyy = (Integer.valueOf(yyy[k]) + Integer.valueOf(YYY[k])) / 2-35*(time - 1);//��ǩ�г���
						String xxxxx = String.valueOf(xxxx);
						String yyyyy = String.valueOf(yyyy);
//						System.out.println("��ǩ���óɹ�������"+xxxxx+"  "+yyyyy);
						if(xxxxx!=null||yyyyy!=null){
							Nail nail = new Nail();
							nail.setX(xxxxx);
							nail.setY(yyyyy);
							midTransition.getNails().add(nail);
						//midElement1.element("location").attribute("y").setText(yyyyy);//���������ֿ�ָ���������Ϊ����û�б�ǩ
						//midElement1.element("location").attribute("x").setText(xxxxx);
						}
						
					}
				
				}
				k++;
			}
			n = 0;//n�����ű�
			p = 0;//P�����ŵ�
			k = 0;//k�����ű�
			m = 0;//m����������ĳ�ʼ
			int B[][]=new ReadXml().find(template);
			int temp1=0,temp2=0;
			String x1=null,y1=null,x2=null,y2=null;
			for(int M=0;M<i;M++)
			for(int N=0;N<i;N++)
			{
				if(M!=N&&B[M][N]==B[N][M]&&B[M][N]==1)//�ж��������ڵĵ��Ƿ��л�·
				{
					for(int c=0;c<j;c++)
					{
						String ID=List.get(M).getId();
						String id=List.get(N).getId();
						String Ref=list.get(c).getSource();
						String ref=list.get(c).getTarget();
					if(ID.equals(Ref)&&id.equals(ref))
		                 {
						 temp1=c;		                 
					         x1= List.get(M).getX();
					          y1= List.get(M).getY();
		                 }
				    }//��ȡ��·�ĵ�һ������transition��ǩ�е�λ�ã�����������
				for(int c=0;c<j;c++)
				{
					String ID=List.get(M).getId();
					String id=List.get(N).getId();
					String Ref=list.get(c).getTarget();
					String ref=list.get(c).getSource();
				if(ID.equals(Ref)&id.equals(ref))
	                 {
					temp2=c;              
				         x2= List.get(N).getX();
				          y2= List.get(N).getY();
	                 }
				}//��ȡ��·�ĵڶ�������transition��ǩ�е�λ�ã����������꣨��������ʵ���Ͼ��ǵ�һ���ߵ��յ㣩
				midTransition=list.get(temp1);
					int X1=Integer.valueOf(x1);
					int X2=Integer.valueOf(x2);
					int Y1=Integer.valueOf(y1)+10;
					int Y2=Integer.valueOf(y2)-10;
					
					int Y = (Y1 + Y2) / 2;
					int X=(X1+X2)/2;
					String XX=String.valueOf(X);
					String YY1=String.valueOf(Y1);
					String YY2=String.valueOf(Y2);				
//					midTransition.addElement("nail").addAttribute("y", YY1);//�����Զ����nail��ǩ
//					midTransition.setGuardY(YY1);
//					midTransition.setGuardX(XX);
					
					
//					midTransition.element("nail").addAttribute("x", XX);					
//					midTransition.element("label").attribute("y").setText(YY1);
//					midTransition.element("label").attribute("x").setText(XX);
					midTransition=list.get(temp2);			
//					midTransition.addElement("nail").addAttribute("y", YY2);//�����Զ����nail��ǩ
//					midTransition.element("nail").addAttribute("x", XX);
//					midTransition.element("label").attribute("y").setText(YY2);
//					midTransition.element("label").attribute("x").setText(XX);
					Nail nail = new Nail();
					nail.setX(XX);
					nail.setY(String.valueOf(Y));
					midTransition.getNails().add(nail);
			    }	
				
				B[M][N]=B[N][M]=0;
			}	
//		    XMLWriter writer = new XMLWriter(new FileOutputStream(route+newFileName),//("stabilize_run.xml"),
//		    OutputFormat.createPrettyPrint());	
//		    writer.write(dom);
//		    writer.close();		
		  }//whileѭ��      
       }

	

	