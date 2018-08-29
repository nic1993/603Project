package cn.hdu.serviceImp;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.hdu.seqentity.Constraint;
import cn.hdu.seqentity.DiagramsData;
import cn.hdu.seqentity.Fragment;
import cn.hdu.seqentity.LifeLine;
import cn.hdu.seqentity.Message;
import cn.hdu.seqentity.Node;
import cn.hdu.seqentity.Operand;
import cn.hdu.seqentity.SD;
import cn.hdu.seqentity.SDRectangle;
import cn.hdu.seqentity.State;
import cn.hdu.service.ReadWork;
import cn.hdu.tool.FixTool;

public class ReadWorkImpl implements ReadWork{

	static ArrayList<DiagramsData> umlAllDiagramData = new ArrayList<DiagramsData>();	
	
	public static void main(String[] args) {
		ReadWorkImpl workImp = new ReadWorkImpl();
		DiagramsData diagramsData = workImp.readXML("C:\\Users\\supercomputer\\Desktop\\12.seq.violet.xml");
	

	}

	//读取xml入口
	@Override
	public DiagramsData readXML(String FileName) {
		// TODO Auto-generated method stubs
		if(FileName.contains("seq.violet.xml")){
			try {
				DiagramsData dd=parserSequence2DiagramData(FileName); //读取文件入口
				dd.setName(FileName.substring(0, FileName.indexOf(".seq.violet.xml")));
				return dd;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		return null;
	}
	
	/**
	 * 解析所有的顺序图对应文件，
	 * @param sequenceFileName 顺序图对应的文件全名
	 * @return 每个顺序图对应的存储结构
	 * @throws Exception 
	 */
	private DiagramsData parserSequence2DiagramData(String sequenceFileName) throws Exception
	{
		DiagramsData dd=new DiagramsData();
		//识别不了别的文件，只能识别xml文件
		SAXReader reader=new SAXReader();
		File file = new File(sequenceFileName);
		
		Document dom=reader.read(file);
		Element sdRoot=dom.getRootElement();
		if(sdRoot==null)
		{
			throw new Exception("文件内容异常.");
		}
		else
		{ 
			//umlAllDiagramData			
			if(sdRoot.getName().contains("SequenceDiagramGraph"))
			{
				if(sdRoot.element("nodes").hasContent())
				{
					
					 dd.setParams(retriveNote(sdRoot));
					//获取生命线集合
					dd.setLifelineArray(retrieveLifeLine(sdRoot));
					
					//获取结点集合
					dd.setNodes(retrieveNodes(dd.getLifelineArray()));
					
					//获取外部约束集合
					dd.setConstraints(retrieveConstraints(sdRoot,dd.getLifelineArray()));
					
					//获取状态集合
					dd.setStates(retrieveStates(sdRoot, dd.getLifelineArray(),dd.getConstraints()));
					
					//获取消息集合
					dd.setMessageArray(retrieveMessages(sdRoot,dd));	
					
					//获取修正(组合片段之间的嵌套，引用和组合片段之间的嵌套)后的组合片段集合
					dd.setFragmentArray(retrieveFragments(sdRoot,dd));	
				}
			}
			return dd;
		}
	}
	
	private ArrayList retriveNote(Element root) {
		List<Element> NoteNodeList=root.element("nodes").elements("NoteNode");
		ArrayList<String> params = new ArrayList<>();
		if(NoteNodeList.size() > 0) {
			for(Iterator<Element> it=NoteNodeList.iterator();it.hasNext();)
			{
				Element tempE=it.next();
				
				String param = tempE.element("text").element("text").getText();
				if(param != null && param != "") {
					String[] strs = param.split(";");
					
					for(String str : strs) {
						params.add(str);
					}
				}
			}
		}
		
		return params;
	}
	
	/**
	 * 
	 * @param root :SequenceDiagramGraph结点
	 * @return ：生命线集合
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<LifeLine> retrieveLifeLine(Element root) throws Exception
	{
		List<Element> lifeElementList=root.element("nodes").elements("LifelineNode");
		ArrayList<LifeLine> lifeLineList=new ArrayList<LifeLine>();
		if(lifeElementList.size()>0)
		{				
			for(Iterator<Element> it=lifeElementList.iterator();it.hasNext();)
			{
				Element tempE=it.next();
				LifeLine lifeLine=new LifeLine(tempE.attributeValue("id"),
						tempE.element("name").elementText("text"));		
				double width = Double.valueOf(tempE.elementText("width"));
				double left = Double.valueOf(tempE.element("location").attributeValue("x"));
				double right = left + width;
				SDRectangle sdRectangle = new SDRectangle();
				sdRectangle.setLeft(left);
				sdRectangle.setRight(right);
				lifeLine.setSdRectangle(sdRectangle);
				
//				if(tempE.element("global").element("text") != null && tempE.element("global").element("text").getText() != null) {
//					lifeLine.setGlobel(tempE.element("global").element("text").getText());
//				}
				
				if(tempE.element("local").element("text") != null && tempE.element("local").element("text").getText() != null) {
					lifeLine.setLocal(tempE.element("local").element("text").getText());
				}
				
				//封装该生命线上包含的结点
				ArrayList<Node> ownedNodeList=retrieveLifeLineNodes(tempE);					
				lifeLine.setNodes(ownedNodeList);			
				lifeLineList.add(lifeLine);
			}
		}
		else
		{
			throw new Exception("生命线信息异常");
		}
		return lifeLineList;
	}
	
	/**
	 * 获取该生命线下所有结点
	 * @param root :生命线结点
	 * @return ：所有结点集合
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Node> retrieveLifeLineNodes(Element lifeLineRoot) 
	{
		ArrayList<Node> nodesList=new ArrayList<Node>();
		String lifeLineID=lifeLineRoot.attributeValue("id");
		String lifeLineName=lifeLineRoot.element("name").elementText("text");
		if(lifeLineRoot.element("children").hasContent())				
		{
			List<Element> nodeElementList=lifeLineRoot.element("children").elements("ActivationBarNode");
			
			for(Element e:nodeElementList)
			{
				Node node=new Node(e.attributeValue("id"),lifeLineID) ;
				
				node.setLifeLineName(lifeLineName);
				nodesList.add(node);
				if(e.element("children").hasContent())
				{
					nodesList.addAll(dfsSearchNodes(e,lifeLineID,lifeLineName));
				}
			}				
		}
		return nodesList;
	}
	private ArrayList<Node> dfsSearchNodes(Element e,String lifeLineID,String lifeLineName)
	{
		ArrayList<Node> nodesList=new ArrayList<Node>();
		@SuppressWarnings("unchecked")
		List<Element> nodeElementList=e.element("children").elements("ActivationBarNode");
		for(Element e2:nodeElementList)
		{
			Node node=new Node(e2.attributeValue("id"),lifeLineID);
			node.setLifeLineName(lifeLineName);
			nodesList.add(node);
			if(e2.element("children").hasContent())
			{
				nodesList.addAll(dfsSearchNodes(e2,lifeLineID,lifeLineName));					
			}
		}
		return nodesList;
	}
	
	/**
	 * 根据所有生命线下的结点，获取所有结点
	 * @param lifeLineList
	 * @return
	 */
	private ArrayList<Node> retrieveNodes(ArrayList<LifeLine> lifeLineList) 
	{
		ArrayList<Node> nodesList=new ArrayList<Node>();
		if(lifeLineList.size()>0)
		{
			for(LifeLine lifeLine:lifeLineList)
			{
				if(lifeLine.getNodes().size()>0)
				{
					for(Node node:lifeLine.getNodes())
					{
						nodesList.add(node);
					}
				}
			}
		}
		return nodesList;			
	}
	
	
	/**
	 * 获取状态
	 * @param root
	 * @param lifeLineList
	 * @return
	 */
	private ArrayList<State> retrieveStates(Element root,ArrayList<LifeLine> lifeLineList,ArrayList<Constraint> constraints) 
	{
		List<Element> stateElementList = root.element("nodes").elements("State");
		
		ArrayList<State> statesList=new ArrayList<State>();
		if(stateElementList.size() > 0)
		{
			for(Element stateElement : stateElementList){
				State state = new State();
				statesList.add(state);
				state.setId(stateElement.attributeValue("id"));
				String stateName = stateElement.element("name").elementText("text");

				state.setName(stateName);
				
				if(stateElement.element("CustomParameters").element("text") != null && stateElement.element("CustomParameters").element("text").getText() != null) {
					state.setSelect(stateElement.element("CustomParameters").element("text").getText());
				}
				
				if(stateElement.element("Assignment").element("text") != null && stateElement.element("Assignment").element("text").getText() != null) {
					state.setUpdate(stateElement.element("Assignment").element("text").getText());
				}
				
				if(stateElement.element("StateInvariant").element("text") != null && stateElement.element("StateInvariant").element("text").getText() != null) {
					state.setInvariant(stateElement.element("StateInvariant").element("text").getText());
				}
				
				if(state.getName().equals("Over")) state.setOver(true);
				if(state.getName().equals("Waiting")) state.setInitial(true);
				
				//获取状态位置	
				SDRectangle sdRectangle = new SDRectangle();
				double left = Double.valueOf(stateElement.element("location").attributeValue("x"));
				double right = left + 85;
				double top = Double.valueOf(stateElement.element("location").attributeValue("y"));
				double bottom = Double.valueOf(top + 30);
				sdRectangle.setLeft(left);
				sdRectangle.setRight(right);
				sdRectangle.setTop(top);
				sdRectangle.setBottom(bottom);
				state.setRectangle(sdRectangle);
				
				//判断状态所属的生命线
				searchLifeLine(state,lifeLineList);
			}
			
			//对状态节点进行排序
			for(LifeLine lifeLine : lifeLineList){
				if(lifeLine.getStates().size() != 0) {
					FixTool.sortStates(lifeLine.getStates());
					lifeLine.setLists(FixTool.SplitMonitorState(lifeLine));
					//默认设置第一个状态为初始状态
					lifeLine.getStates().get(0).setInitial(true);
					lifeLine.getStates().get(lifeLine.getStates().size() - 1).setOver(true);
					
				}
			}
			
			searchProbeMessage(lifeLineList);
			
			searchConstraint(lifeLineList);
		}
		return statesList;			
	}
	/**
	 * 获取状态
	 * @param root
	 * @return
	 */
	private ArrayList<Constraint> retrieveConstraints(Element root,List<LifeLine> lifeLines) 
	{
		List<Element> stateElementList = root.element("nodes").elements("Constraint");
		ArrayList<Constraint> constraints = new ArrayList<>();
		if(stateElementList.size() != 0) {
			for(Element element : stateElementList) {
				Constraint constraint = new Constraint();
				constraint.setId(element.attributeValue("id"));
				constraint.setText(element.element("name").elementText("text"));
				constraint.setX(Double.valueOf(element.element("location").attributeValue("x")));
				constraint.setY(Double.valueOf(element.element("location").attributeValue("y")));
				
				for(LifeLine lifeLine : lifeLines) {
					if(FixTool.Point_belong_rectangle(lifeLine.getSdRectangle(), constraint.getX(), constraint.getY())) {
						lifeLine.getConstraints().add(constraint);
					}
				}
				constraints.add(constraint);
			}
		}
		for(LifeLine lifeLine : lifeLines)
		FixTool.sortConstraints(lifeLine.getConstraints());
		return constraints;
	}
	
	/**
	 * 获取消息
	 * @param root
	 * @param sd
	 * @return 每个顺序图中的消息
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Message> retrieveMessages(Element root,DiagramsData sd) throws Exception
	{			
		//封装消息
		ArrayList<Element> messageElementList=(ArrayList<Element>) root.element("edges").elements("CallEdge");
		
		ArrayList<Element> returnMessageElementList=(ArrayList<Element>) root.element("edges").elements("ReturnEdge");
		ArrayList<Message> messageList=new ArrayList<Message>();
		
		for(Element e:messageElementList)
		{
			
			Message message=new Message();
			message.setId(e.elementText("ID"));
			message.setName(e.elementText("middleLabel"));				
			
			
			message.setSenderID(searchSendLifeLine(message,e.element("start"),sd).getId());
			
			message.setReceiverID(searchReceiveLifeLine(message,e.element("end"),sd).getId());
			
			message.setSender(searchLifeLine(e.element("start"),sd).getName());
			message.setReceiver(searchLifeLine(e.element("end"),sd).getName());

			message.setPointY(Double.parseDouble(e.element("startLocation").attributeValue("y")));
			
			
			if(e.element("guard") != null) {
				message.setGuard(e.element("guard").getText());
			}
			
			
			//message.setProb(sd.getProb());
			/*if(e.equals(messageElementList.get(messageElementList.size()-1)))
			{
				message.setLast(true);
			}*/
			
			//消息是否在组合片段中，消息所属组合片段类型，所属组合片段ID，所属组合片段操作ID，进出组合片段的标记
			
			//消息属性不全
			//………………
			//执行时间				
			messageList.add(message);
		}
		for(Element e:returnMessageElementList)
		{
			Message message=new Message();
			message.setId(e.elementText("ID"));
			message.setName(e.elementText("middleLabel"));				
			message.setSenderID(searchLifeLine(e.element("start"),sd).getId());
			
			message.setReceiverID(searchLifeLine(e.element("end"),sd).getId());
			
			message.setSender(searchLifeLine(e.element("start"),sd).getName());
			message.setReceiver(searchLifeLine(e.element("end"),sd).getName());
							
			message.setNoteID(e.elementText("ID"));
			
			message.setPointY(Double.parseDouble(e.element("startLocation").attributeValue("y")));
			
			//message.setProb(sd.getProb());
			if(e.equals(messageElementList.get(messageElementList.size()-1)))
			{
				message.setLast(true);
			}
			
			//消息是否在组合片段中，消息所属组合片段类型，所属组合片段ID，所属组合片段操作ID，进出组合片段的标记
			
			//消息属性不全
			//………………
			//执行时间				
			messageList.add(message);
		}
		//对消息按照坐标进行一次排序
		FixTool.sortMesses(messageList);
		for(LifeLine lifeLine : sd.getLifelineArray()){
			FixTool.sortMesses(lifeLine.getSendMessages());
			FixTool.sortMesses(lifeLine.getReceiveMessages());
		}
		
		searchStateBySendMessage(sd.getLifelineArray());
		return messageList;
	}
	
	
	/**
	 * 获取顺序图第一层组合片段（第一层组合片段已经嵌套完毕）
	 * @param root：SequenceDiagramGraph根节点
	 * @param sd：场景
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Fragment> retrieveFragments(Element root,DiagramsData dd)
	{
		ArrayList<Fragment> firstFragmentsList=new ArrayList<Fragment>();
		List<Element> firstLevelFragmentsList=new ArrayList<Element>();
		firstLevelFragmentsList.addAll(root.element("nodes").elements("CombinedFragment"));
		for(Element e:firstLevelFragmentsList)
		{
			//组合片段id,name,type
			Fragment fragment=new Fragment();
			firstFragmentsList.add(fragment);
			
			fragment.Set(e.elementText("ID"),e.elementText("fragmentType").toLowerCase());
			fragment.setName(e.elementText("name"));
			
			//操作集合：封装操作
			ArrayList<Operand> operandList=new ArrayList<Operand>();
			fragment.setOperands(operandList);//此条放在前面后面均可以,因为加入的是引用，不是空间存储值。
			
			ArrayList<Element> operandElementList= new ArrayList<Element>();
			operandElementList.addAll(e.element("fragmentParts").elements("com.horstmann.violet.product.diagram.abstracts.property.FragmentPart"));
			for(Element operandE:operandElementList)
			{
				Operand operand =new Operand();
				operandList.add(operand);
				operand.setCondition(operandE.elementText("conditionText"));
				operand.setId(operandE.attributeValue("id"));					
				operand.setRectangle(retrieveOperandSDRectangle(operandE));
			}
			//组合片段坐标获取
			fragment.setRectangle(retrieveFragmentSDRectangle(e));
		}
		//对组合片段封装消息，并修正组合片段嵌套关系和 引用片段与组合片段操作的关系
		FixTool.fixFragmentsOfOneDiagram(firstFragmentsList,dd);
		
		for(Fragment fragment : firstFragmentsList) {
			String str = "";
			AssemblyGuard(fragment, str);
		}
		return firstFragmentsList;
	}
	
	private void AssemblyGuard(Fragment fragment,String str) {
		for(Operand operand : fragment.getOperands()) {

			str += operand.getCondition() + "&&";
			for(State state : operand.getStates()) {
				String guard = str.substring(0, str.length() - 2);
				state.setGuard(guard);
			}
			
			for(Message message : operand.getMessages()) {
				String guard = operand.getCondition();
				message.setGuard(guard);
			}
			for(Fragment fragment2 : operand.getFragments()) {
				AssemblyGuard(fragment2,str);
			} 
		}
	}
	
	
	/**
	 * 寻找该节点所依附的生命线
	 * @param e 
	 * @param sd 场景对应的顺序图
	 * @return 
	 */
	private LifeLine searchLifeLine(Element e,DiagramsData sd)
	{
		String ref=e.attributeValue("reference");
		for(LifeLine l: sd.getLifelineArray())
		{
			if(l.getNodes().size()>0)
			{
				for(Node node:l.getNodes())
				{
					if(ref.equals(node.getId()))
					{
					   return l;	
					}
				}
			}				
		}		
		return null;
	}
	
	/**
	 * 寻找发送消息的生命线
	 * @param e 
	 * @param sd 场景对应的顺序图
	 * @return 
	 */
	private LifeLine searchSendLifeLine(Message message,Element e,DiagramsData sd)
	{
		String ref=e.attributeValue("reference");
		for(LifeLine l: sd.getLifelineArray())
		{
			if(l.getNodes().size()>0)
			{
				for(Node node:l.getNodes())
				{
					if(ref.equals(node.getId()))
					{
						l.getSendMessages().add(message);
					   return l;	
					}
				}
			}				
		}		
		return null;
	}
	
	/**
	 * 寻找接收消息的生命线
	 * @param e 
	 * @param sd 场景对应的顺序图
	 * @return 
	 */
	private LifeLine searchReceiveLifeLine(Message message,Element e,DiagramsData sd)
	{
		String ref=e.attributeValue("reference");
		
		for(LifeLine l: sd.getLifelineArray())
		{
			if(l.getNodes().size()>0)
			{
				for(Node node:l.getNodes())
				{
					if(ref.equals(node.getId()))
					{
						l.getReceiveMessages().add(message);
					   return l;	
					}
				}
			}				
		}		
		return null;
	}
	
	/**
	 * 寻找状态所依附的生命线
	 * @param state
	 * @param lifeLineList
	 */
	private void searchLifeLine(State state,ArrayList<LifeLine> lifeLineList){
		SDRectangle sdRectangle = state.getRectangle();
		double left = sdRectangle.getLeft();
		double right = sdRectangle.getRight();
		for(LifeLine lifeLine : lifeLineList){
			double center = (lifeLine.getSdRectangle().getLeft() + lifeLine.getSdRectangle().getRight()) / 2;
			if(center > left && center < right){
				lifeLine.getStates().add(state);
				break;
			}
		}
	}
	
	/**
	 * 寻找状态的外部约束
	 * @param state
	 * @param constraints
	 */
	private void searchConstraint(ArrayList<LifeLine> lifeLineList){
		for(LifeLine lifeLine : lifeLineList) {
			ArrayList<State> states = lifeLine.getStates();
			List<Constraint> constraints = lifeLine.getConstraints();
			for(int j = 0;j < states.size();j++){
				State state = states.get(j);
				if(!state.isInitial() || !state.isOver()) {
					SDRectangle rectangle = state.getRectangle();
					
				
					for(int i = 0;i < constraints.size();i++) {
						if(FixTool.Point_in_rectangle(rectangle, constraints.get(i).getX(), constraints.get(i).getY()) && constraints.get(i).isBelongToState() == false) {
							
							
							
							state.getConstraints().add(constraints.get(i));
							//寻找下一个约束 (默认存在分支)
							fixConstraint(lifeLine, state, constraints, i + 1);
//							state.getConstraints().add(constraints.get(i+1));
//							
//							constraints.get(i).setBelongToState(true);
//							constraints.get(i+1).setBelongToState(true);
//							if(j < states.size() - 2) {
//						    	constraints.get(i).setNextStateName(states.get(j+1).getName());
//						    	constraints.get(i+1).setNextStateName(states.get(j+2).getName());
//						    }
//							else if (j < states.size() - 1) {
//								constraints.get(i).setNextStateName(states.get(j+1).getName());
//						    	constraints.get(i+1).setNextStateName("Over");
//							}else {
//						    	constraints.get(i).setNextStateName("Over");
//						    	constraints.get(i+1).setNextStateName("Over");
//							}
						}
					}
				}
				
			}
		}
		
	}
	/**
	 * 获取组合片段坐标
	 * @param e
	 * @return
	 */
	private SDRectangle retrieveFragmentSDRectangle(Element e)
	{
		SDRectangle rec=new SDRectangle();
		rec.setLeft(Double.parseDouble(e.element("location").attributeValue("x")));
		rec.setTop(Double.parseDouble(e.element("location").attributeValue("y")));
		rec.setRight(rec.getLeft()+Double.parseDouble(e.element("width").getText()));
		rec.setBottom(rec.getTop()+Double.parseDouble(e.element("height").getText()));		
		return rec;		
	}
	
	/**
	 * 获取操作域坐标
	 * @return
	 */
	private SDRectangle retrieveOperandSDRectangle(Element operE)
	{
		SDRectangle rec=new SDRectangle();
		rec.setLeft(Double.parseDouble(operE.element("borderline").elementText("x1")));
		rec.setTop(Double.parseDouble(operE.element("borderline").elementText("y1")));
		rec.setRight(Double.parseDouble(operE.element("borderline").elementText("x2")));
		rec.setBottom(Double.parseDouble(operE.element("size").getText())+Double.parseDouble(operE.element("borderline").elementText("y2")));		
		return rec;
	}
	/**
	 * 寻找状态所发送的消息以及接收的消息
	 * @param lifeLineList
	 */
	public void searchStateBySendMessage(ArrayList<LifeLine> lifeLineList){
		 
		 for(LifeLine lifeLine : lifeLineList){
			 
			//获取状态所发送的消息
			 List<Message> messages = lifeLine.getSendMessages();
			 
				for(Message message : messages){
					double messageY = message.getPointY();
					double min = Double.MAX_VALUE;
					int loc = 0;
					for(int i = 0;i < lifeLine.getStates().size();i++){
						double stateY = lifeLine.getStates().get(i).getRectangle().getTop();
						if(stateY < messageY && min > (messageY - stateY)){
							min = messageY - stateY;
							loc = i;
						}
					}
					for(int j = loc;j > 0;j--) {
						if(!lifeLine.getStates().get(j).getName().equals("Over")) {
							lifeLine.getStates().get(j).getSendSync().add(message);
							break;
						}
					}		
				  }
				
			 
			//获取状态所接收的消息

			List<Message> recs = lifeLine.getReceiveMessages();
				for(Message message : recs){
					double messageY = message.getPointY();
					double min = Double.MAX_VALUE;
					int loc = 0;
					for(int i = 0;i < lifeLine.getStates().size();i++){
						double stateY = lifeLine.getStates().get(i).getRectangle().getTop();
						if(stateY > messageY && min > (stateY - messageY)){
							min = stateY - messageY;
							loc = i;
						}
					}
					lifeLine.getStates().get(loc).setReceiveSync(message);
				}
			
		 } 
	   }
	
	/**
	 * 修正属于状态的约束
	 * @param state 当前状态
	 * @param constraints 约束结合
	 * @param i 当前约束所属的位置
	 */
	private void fixConstraint(LifeLine lifeLine,State cur,List<Constraint> constraints,int i) {
		for(;i < constraints.size();i++) {
			Constraint constraint = constraints.get(i);
			for(State state : lifeLine.getStates()) {
				if(state.getName().equals("Over") || 
						state.getName().equals("Waiting") || state == cur) continue;
				else if(FixTool.Point_in_rectangle(state.getRectangle(), constraint.getX(), constraint.getY())){
					return;
				}
			}
			
			constraint.setBelongToState(true);
			cur.getConstraints().add(constraint);
		}
	}
	
	/*
	 * 监视单元没有over 发送消息需要监视单元接收
	 */
	public void searchProbeMessage(ArrayList<LifeLine> lifeLineList){
		 for(LifeLine lifeLine : lifeLineList){
			 if(lifeLine.getName().contains("Probe")) {
				 State initState = lifeLine.getStates().get(0);
				 State probState = lifeLine.getStates().get(1);
				 List<Message> message = probState.getSendSync();
				 initState.setSendSync(message);		
				}
			 }
		 }
	
	//读取xml入口
	
		
	}
