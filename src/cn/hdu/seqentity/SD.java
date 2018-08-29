package cn.hdu.seqentity;

import java.util.ArrayList;


public class SD {

	private String id;
	private String name;
	
	private ArrayList<LifeLine> lifeLines;
	private ArrayList<Node> nodes;
	
	private ArrayList<Message> messages;
	private ArrayList<Fragment> fragments;
	private double prob;
	private String postSD;//场景执行后置条件
	
	public SD(){}
	public String toString()
	{
		return "SDSet:"+id;
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
	public ArrayList<LifeLine> getLifeLines() {
		return lifeLines;
	}
	public void setLifeLines(ArrayList<LifeLine> lifeLines) {
		this.lifeLines = lifeLines;
	}
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.nodes = nodes;
	}
	public ArrayList<Message> getMessages() {
		return messages;
	}
	public void setMessages(ArrayList<Message> messages) {
		this.messages = messages;
	}
	public ArrayList<Fragment> getFragments() {
		return fragments;
	}
	public void setFragments(ArrayList<Fragment> fragments) {
		this.fragments = fragments;
	}
	public double getProb() {
		return prob;
	}
	public void setProb(double prob) {
		this.prob = prob;
	}
	public String getPostSD() {
		return postSD;
	}
	public void setPostSD(String postSD) {
		this.postSD = postSD;
	}
	
}
