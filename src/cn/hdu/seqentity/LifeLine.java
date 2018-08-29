package cn.hdu.seqentity;
import java.util.ArrayList;
import java.util.List;

public class LifeLine {
	/* 
	 * 生命线的id 和 name，以及本身包含的位点结点；
	 */
	private String id;
	private String name;
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<State> states = new ArrayList<State>();
	private ArrayList<Message> sendMessages = new ArrayList<Message>();
	private ArrayList<Message> receiveMessages = new ArrayList<Message>();
	private List<List<State>> lists = new ArrayList<>();
	private ArrayList<Constraint> constraints = new ArrayList<>();
	private SDRectangle sdRectangle;
	private String globel;
	private String local;
	public LifeLine() {}
	
	public LifeLine(String id) 
	{
		this.id = id;
	}
	
	public LifeLine(String id, String name) 
	{
		set(id, name);
	}
	
	public void set(String id, String name) 
	{
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public void addNode(Node node) {
		this.nodes.add(node);
	}
	
	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
    
	public List<List<State>> getLists() {
		return lists;
	}

	public void setLists(List<List<State>> lists) {
		this.lists = lists;
	}

	public ArrayList<Message> getSendMessages() {
		return sendMessages;
	}

	public void setSendMessages(ArrayList<Message> sendMessages) {
		this.sendMessages = sendMessages;
	}

	public ArrayList<Message> getReceiveMessages() {
		return receiveMessages;
	}

	public void setReceiveMessages(ArrayList<Message> receiveMessages) {
		this.receiveMessages = receiveMessages;
	}
    
	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}

	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}

	public String toString() 
	{
		return "LifeLine: "+ id + "\t" + name;
	}
	
	//以下两种方法有待重新整合
//	public void print_nodes()
//	{
//		for (Node n : this.nodes)
//			n.print_node();
//		
//	}
	
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public SDRectangle getSdRectangle() {
		return sdRectangle;
	}

	public void setSdRectangle(SDRectangle sdRectangle) {
		this.sdRectangle = sdRectangle;
	}

	public String getGlobel() {
		return globel;
	}

	public void setGlobel(String globel) {
		this.globel = globel;
	}

	
}
