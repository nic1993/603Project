package cn.hdu.service;

import java.util.List;

import cn.hdu.seqentity.DiagramsData;
import cn.hdu.seqentity.LifeLine;
import cn.hdu.timentity.Declaration;
import cn.hdu.timentity.SystemStatment;

public interface ExchangeWork {
	
	
	public void exchange();
	//ȫ�ֱ�������
	public void globelStatement(List<String> params,Declaration declaration);
	
	//ͨ������
	public void chanStatement(List<LifeLine> lifeLines,Declaration declaration);
	
	//ģ������
	public void templateStatement(DiagramsData dd);

	//ϵͳģ������
	public void systemStatement(List<LifeLine> lifeLines,SystemStatment system);
	
}
