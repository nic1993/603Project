package cn.hdu.timentity;
 /*
  * 存放状态的实体
  */
public class Location {
 
	private String id; 
	private String name; //名称
	private String invariant; //内部约束
	private String type;  //位置类型
	private String X = "50"; //初始位置
	private String Y = "50"; //初始位置
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
