package cn.hdu.seqentity;

import java.util.ArrayList;
import java.util.Iterator;

/*
 * ������
 * ÿ�����Ƭ�������ж���ִ��·��
 * ÿ��ִ��·����Ӧһ�������򣨲���id,ִ��������ִ��·���Ϻ��еĽ�����Ϣ��
 */
public class Operand implements Cloneable{
	@Override
	public Object clone() {   
		Operand o = null;   
        try {   
            o = (Operand) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }  
        o.setNodeIds(new ArrayList<String>(this.nodeIds));
        
        ArrayList<Node> copyNodes=new ArrayList<Node>();
        for(Node node:this.nodes)
        {
        	copyNodes.add((Node)node.clone());
        }        
        o.setNodes(copyNodes);
        
        ArrayList<Message> copyMessages=new ArrayList<Message>();
        for(Message mess:this.messages)
        {
        	copyMessages.add((Message)mess.clone());
        }        
        o.setMessages(copyMessages);
        
        if(this.fragments.size()>0)
        {
        	ArrayList<Fragment> copyFragments=new ArrayList<Fragment>();
        	for(Fragment f:this.fragments)
            {
        		copyFragments.add((Fragment)f.clone());
            }        
            o.setFragments(copyFragments);        	
        }
        
        ArrayList<String> copyFragIDs=new ArrayList<String>(fragmentIDs);
        o.setFragmentIDs(copyFragIDs);
        o.setRectangle((SDRectangle)rectangle.clone());
        return o;
    }
	
	private String id;
	private String condition;
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<String> nodeIds=new ArrayList<String>();
	private ArrayList<Message> messages=new ArrayList<Message>();
	private ArrayList<State> states = new ArrayList<State>();
	
	private boolean isHasFragment=false;
	private ArrayList<String> fragmentIDs=new ArrayList<String>();
	private ArrayList<Fragment> fragments=new ArrayList<Fragment>();
	
	private SDRectangle rectangle; //������������Ϣ

	
	public Operand(){}
	public String toString()
	{
		return "Operand:"+id+","+condition;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<String> getNodeIds() {
		return nodeIds;
	}
	public void setNodeIds(ArrayList<String> nodeIds) {
		this.nodeIds = nodeIds;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	public boolean isHasFragment() {
		return isHasFragment;
	}
	public void setHasFragment(boolean isHasFragment) {
		this.isHasFragment = isHasFragment;
	}
	public ArrayList<Fragment> getFragments() {
		return fragments;
	}
	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}
	public void addFragment(Fragment fragment) {
		this.fragments.add(fragment);
	}
	
	public ArrayList<String> getFragmentIDs() {
		return fragmentIDs;
	}
	public void setFragmentIDs(ArrayList<String> fragmentIDs) {
		this.fragmentIDs = fragmentIDs;
	}
	public SDRectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(SDRectangle rectangle) {
		this.rectangle = rectangle;
	}	
	public void addMessage(Message message)
	{
		this.messages.add(message);
	}
	public ArrayList<State> getStates() {
		return states;
	}
	
}
