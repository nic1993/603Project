package cn.hdu.service;

import java.util.List;

import cn.hdu.seqentity.DiagramsData;
import cn.hdu.seqentity.LifeLine;
import cn.hdu.timentity.Declaration;
import cn.hdu.timentity.SystemStatment;

public interface ExchangeWork {
	
	
	public void exchange();
	//全局变量声明
	public void globelStatement(List<String> params,Declaration declaration);
	
	//通道声明
	public void chanStatement(List<LifeLine> lifeLines,Declaration declaration);
	
	//模板声明
	public void templateStatement(DiagramsData dd);

	//系统模板声明
	public void systemStatement(List<LifeLine> lifeLines,SystemStatment system);
	
}
