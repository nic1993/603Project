package cn.hdu.timentity;

import java.util.ArrayList;
import java.util.List;
/*
 * ģ��ʵ����
 */
public class Template {
	private String name; //ģ������
	private String initRefID; //��ʼ�ڵ�ID
	private List<Location> locations = new ArrayList<>();//
	private List<Transition> transitions = new ArrayList<>();
	private String Local;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getInitRefID() {
		return initRefID;
	}
	public void setInitRefID(String initRefID) {
		this.initRefID = initRefID;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public List<Transition> getTransitions() {
		return transitions;
	}
	public String getLocal() {
		return Local;
	}
	public void setLocal(String local) {
		Local = local;
	}
}
