package cn.hdu.timentity;
/*
 * ���ȫ�ֱ�����ͨ��ʵ����
 */
public class Declaration {

	//ͨ�� 
	private String Chans = ""; 
	
	//ʱ��
	private String Clocks = "";
	
	//����
	private String Variables = "";
	
	//����
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
