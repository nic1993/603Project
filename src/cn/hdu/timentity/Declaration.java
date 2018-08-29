package cn.hdu.timentity;
/*
 * 存放全局变量、通道实体类
 */
public class Declaration {

	//通道 
	private String Chans = ""; 
	
	//时钟
	private String Clocks = "";
	
	//变量
	private String Variables = "";
	
	//常量
	private String Const = "";

	public String getChans() {
		return Chans;
	}

	public void setChans(String chans) {
		this.Chans = chans;
	}

	public String getClocks() {
		return Clocks;
	}

	public void setClocks(String clocks) {
		this.Clocks = clocks;
	}

	public String getVariables() {
		return Variables;
	}

	public void setVariables(String variables) {
		this.Variables = variables;
	}

	public String getConst() {
		return Const;
	}

	public void setConst(String const1) {
		Const = const1;
	}
}
