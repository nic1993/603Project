package cn.hdu.service;

import java.util.List;

import cn.hdu.seqentity.DiagramsData;
import cn.hdu.seqentity.LifeLine;
import cn.hdu.timentity.Declaration;

public interface ReadWork {
	//��ȡXML��Ϣ
	public DiagramsData readXML(String FileName);
}
