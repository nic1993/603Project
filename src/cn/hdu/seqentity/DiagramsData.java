package cn.hdu.seqentity;

import java.util.ArrayList;
import java.util.List;

public class DiagramsData{
	
	private String diagramID;
	private String name;
	
	private ArrayList <String> ids = new ArrayList<String>(); // ����ͼ���������Ƭ�Ρ����á��������Ϣ��id����
	private ArrayList<Node> nodes=new ArrayList<Node>();
	private ArrayList<State> states = new ArrayList<State>();
	private ArrayList<Constraint> constraints = new ArrayList<>();
	private ArrayList<String> params = new ArrayList<>();
	private ArrayList <LifeLine> lifelineArray = new ArrayList <LifeLine>();
	private ArrayList <Fragment> fragmentArray = new ArrayList <Fragment>();
	private ArrayList <Message> messageArray = new ArrayList <Message>();
	
    
	int displayCount = 0;
	public int getDisplayCount() {
		return displayCount;
	}
	public void setDisplayCount(int displayCount) {
		this.displayCount = displayCount;
	}

	int RefEndTo;
	
	public ArrayList<String> getIds() {
		return ids;
	}
	public void setIds(ArrayList<String> ids) {
		this.ids = ids;
	}
	public ArrayList<String> getParams() {
		return params;
	}
	public void setParams(ArrayList<String> params) {
		this.params = params;
	}
	public ArrayList<LifeLine> getLifelineArray() {
		return lifelineArray;
	}
	public void setLifelineArray(ArrayList<LifeLine> lifelineArray) {
		this.lifelineArray = lifelineArray;
	}
	public ArrayList<Fragment> getFragmentArray() {
		return fragmentArray;
	}
	public void setFragmentArray(ArrayList<Fragment> fragmentArray) {
		this.fragmentArray = fragmentArray;
	}
	public ArrayList<Message> getMessageArray() {
		return messageArray;
	}
	public void setMessageArray(ArrayList<Message> messageArray) {
		this.messageArray = messageArray;
	}
	public String getDiagramID() {
		return diagramID;
	}
	public void setDiagramID(String diagramID) {
		this.diagramID = diagramID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRefEndTo() {
		return RefEndTo;
	}
	public void setRefEndTo(int refEndTo) {
		RefEndTo = refEndTo;
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	
	public ArrayList<Constraint> getConstraints() {
		return constraints;
	}
	public void setConstraints(ArrayList<Constraint> constraints) {
		this.constraints = constraints;
	}
	public ArrayList<State> getStates() {
		return states;
	}
	public void setStates(ArrayList<State> states) {
		this.states = states;
	}
	@Override
	public String toString() {
		return "DiagramsData [diagramID=" + diagramID + ", name=" + name
				+ ", ids=" + ids + ", lifelineArray=" + lifelineArray
				+ ", fragmentArray=" + fragmentArray + ", messageArray="
				+ messageArray + ", displayCount="
				+ displayCount + ", RefEndTo=" + RefEndTo + "]";
	}
	
}
