package cn.hdu.seqentity;
/*
 * 外部约束实体
 */
public class Constraint {

	private String id;
	
	private String text;
	
	private double x;
	
	private double y;
	
	private boolean isBelongToState = false;
	
	private String nextStateName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public boolean isBelongToState() {
		return isBelongToState;
	}

	public void setBelongToState(boolean isBelongToState) {
		this.isBelongToState = isBelongToState;
	}

	public String getNextStateName() {
		return nextStateName;
	}

	public void setNextStateName(String nextStateName) {
		this.nextStateName = nextStateName;
	}
}
