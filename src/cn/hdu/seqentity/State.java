package cn.hdu.seqentity;

import java.util.ArrayList;
import java.util.List;

public class State implements Cloneable{

	@Override
	public Object clone() {   
		State o = null;   
        try {   
            o = (State) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }  
        return o;   
	}
	private String id;
	private String name;
	private String select;
	private String guard;
	private String update;
	private String invariant;
	private SDRectangle sdRectangle;
	private List<Message> SendSync = new ArrayList<>();
	private Message ReceiveSync;
	private List<Constraint> constraints = new ArrayList<>();
	private boolean inFragFlag=false;
	private String inID;
	private String inName;
	private String X = "50";
	private String Y = "50";
	//ÅÐ¶ÏÊÇ·ñÊÇ³õÊ¼×´Ì¬
	private boolean isInitial = false;
	//ÊÇ·ñÎª½áÊø×´Ì¬
	private boolean isOver = false;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGuard() {
		return guard;
	}
	public void setGuard(String guard) {
		this.guard = guard;
	}
	public String getUpdate() {
		return update;
	}
	public void setUpdate(String update) {
		this.update = update;
	}
	public SDRectangle getRectangle() {
		return sdRectangle;
	}
	public void setRectangle(SDRectangle sdRectangle) {
		this.sdRectangle = sdRectangle;
	}
	public void setSendSync(List<Message> sendSync) {
		this.SendSync = sendSync;
	}
	public List<Message> getSendSync() {
		return SendSync;
	}
	public Message getReceiveSync() {
		return ReceiveSync;
	}
	public void setReceiveSync(Message receiveSync) {
		ReceiveSync = receiveSync;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isInitial() {
		return isInitial;
	}
	public void setInitial(boolean isInitial) {
		this.isInitial = isInitial;
	}
	public List<Constraint> getConstraints() {
		return constraints;
	}
	public boolean isOver() {
		return isOver;
	}
	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getInvariant() {
		return invariant;
	}
	public void setInvariant(String invariant) {
		this.invariant = invariant;
	}
	public String getX() {
		return X;
	}
	public void setX(String x) {
		X = x;
	}
	public String getY() {
		return Y;
	}
	public void setY(String y) {
		Y = y;
	}
	
}
