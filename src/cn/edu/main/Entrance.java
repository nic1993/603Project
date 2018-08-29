package cn.edu.main;

import java.io.IOException;

import cn.hdu.seqentity.DiagramsData;
import cn.hdu.service.ExchangeWork;
import cn.hdu.service.ReadWork;
import cn.hdu.service.WriteWork;
import cn.hdu.serviceImp.ExchangeWorkImpl;
import cn.hdu.serviceImp.ReadWorkImpl;
import cn.hdu.serviceImp.WriteWorkImpl;
import cn.hdu.timentity.Declaration;
import cn.hdu.timentity.TimedAutomata;

public class Entrance {

	public static void main(String[] args) throws IOException {
		ReadWorkImpl workImp = new ReadWorkImpl(); 
		DiagramsData diagramsData = workImp.readXML("resources/input/mission_and_batt_new_7.21.seq.violet.xml");//读取xml信息
		TimedAutomata timedAutomata = new TimedAutomata();
		
		ExchangeWork exchangeWork = new ExchangeWorkImpl(timedAutomata, diagramsData);
		exchangeWork.exchange();
		
		Declaration declaration = timedAutomata.getDeclaration();
		
		WriteWork writeWork = new WriteWorkImpl();
		writeWork.wirte(timedAutomata);
		
//		Runtime runtime = Runtime.getRuntime();
//    	runtime.exec("cmd.exe /c java -jar H:\\sxs\\自适应\\uppaal\\uppaal-4.0.14-aca\\uppaal-4.0.14\\uppaal.jar");
	}
}
