package cn.hdu.seqentity;

import java.util.ArrayList;
import java.util.List;


public class Message implements Cloneable{
	@Override
	public Object clone() {   
		Message o = null;   
        try {   
            o = (Message) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }  
        return o;   
    }
	
	private String id;
	/*
	 * @noteID:平台上ID设置过于混乱，此ID用于映射到消息上的唯一标识id；
	 * 不在平台上修改引用id的程序（修改过于麻烦）;
	 */
	private String noteID;
	private String name;
	private String senderID;
	private String receiverID;
	
	private String sender;       
	private String receiver;   
	private boolean isLast=false;
	
	private boolean isInFragment=false; // 消息是否在组合片段的标记
	private String fragmentId;
	private String fragType;
	private String operandId;//消息在组合片段中对应的操作域ID
	
	private String fragFlag;  //消息进出组合片段标记   inOperand+outOperand
	
	private String guard;
	
	private double pointY=0;
	
	public Message(){}
	public void set(String id, String name, String senderID, String receiverID) {
		this.id=id;
		this.name=name;
		this.senderID=senderID;
		this.receiverID=receiverID;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Message [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", senderID=");
		builder.append(senderID);
		builder.append(", receiverID=");
		builder.append(receiverID);
		builder.append(", sender=");
		builder.append(sender);
		builder.append(", receiver=");
		builder.append(receiver);
		builder.append(", isLast=");
		builder.append(isLast);
		builder.append(", isInFragment=");
		builder.append(isInFragment);
		builder.append(", fragmentId=");
		builder.append(fragmentId);
		builder.append(", fragType=");
		builder.append(fragType);
		builder.append(", operandId=");
		builder.append(operandId);
		builder.append(", fragFlag=");
		builder.append(fragFlag);
		builder.append(", conditions=");
		builder.append("]");
		return builder.toString();
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getNoteID() {
		return noteID;
	}
	public void setNoteID(String noteID) {
		this.noteID = noteID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSenderID() {
		return senderID;
	}
	public void setSenderID(String senderID) {
		this.senderID = senderID;
	}
	public String getReceiverID() {
		return receiverID;
	}
	public void setReceiverID(String receiverID) {
		this.receiverID = receiverID;
	}
	public String getFragFlag() {
		return fragFlag;
	}
	public void setFragFlag(String fragFlag) {
		this.fragFlag = fragFlag;
	}
	public boolean isInFragment() {
		return isInFragment;
	}
	public void setInFragment(boolean isInFragment) {
		this.isInFragment = isInFragment;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getOperandId() {
		return operandId;
	}
	public void setOperandId(String operandId) {
		this.operandId = operandId;
	}
	public String getFragmentId() {
		return fragmentId;
	}
	public void setFragmentId(String fragmentId) {
		this.fragmentId = fragmentId;
	}
	public String getFragType() {
		return fragType;
	}
	public void setFragType(String fragType) {
		this.fragType = fragType;
	}
	public boolean isLast() {
		return isLast;
	}
	public void setLast(boolean isLast) {
		this.isLast = isLast;
	}

	public double getPointY() {
		return pointY;
	}
	public void setPointY(double pointY) {
		this.pointY = pointY;
	}
	public String getGuard() {
		return guard;
	}
	public void setGuard(String guard) {
		this.guard = guard;
	}	
}
