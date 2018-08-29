package cn.hdu.timentity;

import java.util.ArrayList;
import java.util.List;

/*
 * 时间自动机模板
 */
public class TimedAutomata {

	private Declaration declaration;
	
	private List<Template> templates = new ArrayList<>();
	
	private SystemStatment systemStatment;

	public Declaration getDeclaration() {
		return declaration;
	}

	public void setDeclaration(Declaration declaration) {
		this.declaration = declaration;
	}

	public List<Template> getTemplates() {
		return templates;
	}

	public SystemStatment getSystemStatment() {
		return systemStatment;
	}

	public void setSystemStatment(SystemStatment systemStatment) {
		this.systemStatment = systemStatment;
	}

	public void setTemplates(List<Template> templates) {
		this.templates = templates;
	}

}
