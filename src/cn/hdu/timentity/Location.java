package cn.hdu.timentity;
 /*
  * ���״̬��ʵ��
  */
public class Location {
 
	private String id; 
	private String name; //����
	private String invariant; //�ڲ�Լ��
	private String type;  //λ������
	private String X = "50"; //��ʼλ��
	private String Y = "50"; //��ʼλ��
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
	public String getInvariant() {
		return invariant;
	}
	public void setInvariant(String invariant) {
		this.invariant = invariant;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
