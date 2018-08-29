package cn.hdu.seqentity;

import java.util.ArrayList;
/*
 * ���Ƭ����Ϣ
 * ���Ƭ��ID�����ͣ�������������ID�����Ƭ��������ִ��·����Ӧ�Ĳ�����
 */


public class Fragment implements Cloneable{
	@Override
	public Object clone() {   
		Fragment o = null;   
        try {   
            o = (Fragment) super.clone();   
        } catch (CloneNotSupportedException e) {   
            e.printStackTrace();   
        }  
        o.setCoveredIDs(new ArrayList<String>(this.coveredIDs));
        ArrayList<Operand> copyOpers=new ArrayList<Operand>();
        for(Operand oper:this.operands)
        {
        	copyOpers.add((Operand)oper.clone());
        }
        o.setOperands(copyOpers);
        o.setRectangle((SDRectangle)rectangle.clone());
        return o;   
    }
	private String id;
	private String name;
	private String type;
	private ArrayList<String> coveredIDs=new ArrayList<String>(); //����������
	private ArrayList<Operand> operands=new ArrayList<Operand>(); //����������
	private boolean isTranslationed=false;
	
	private boolean enFlag=false;  //�Ƿ񱻷�װ��Ƕ��
	
	private SDRectangle rectangle=new SDRectangle(); //���Ƭ��������Ϣ
	private String inID; //���ڲ���ID
	
	public Fragment(){}
	public Fragment(String id,String type)
	{
		Set(id,type);
	}
	public void Set(String id,String type)
	{
		this.id=id;
		this.type=type;
	}
	
	public String toString()
	{
		return "Fragment: id="+id+", Type="+type;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ArrayList<String> getCoveredIDs() {
		return coveredIDs;
	}
	public void setCoveredIDs(ArrayList<String> coveredIDs) {
		this.coveredIDs = coveredIDs;
	}
	public ArrayList<Operand> getOperands() {
		return operands;
	}
	public void setOperands(ArrayList<Operand> operands) {
		this.operands = operands;
	}
	public boolean isTranslationed() {
		return isTranslationed;
	}
	public void setTranslationed(boolean isTranslationed) {
		this.isTranslationed = isTranslationed;
	}
	public boolean isEnFlag() {
		return enFlag;
	}
	public void setEnFlag(boolean enFlag) {
		this.enFlag = enFlag;
	}
	public SDRectangle getRectangle() {
		return rectangle;
	}
	public void setRectangle(SDRectangle rectangle) {
		this.rectangle = rectangle;
	}
	public String getInID() {
		return inID;
	}
	public void setInID(String inID) {
		this.inID = inID;
	}
	  
}


