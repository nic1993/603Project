package cn.edu.layout;

import cn.hdu.timentity.Template;

public class LayoutMarkov {
  public static void layout(Template template) {
	  try {

		  UpdateXml updateXml = new UpdateXml(template);
		  updateXml.update();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  public static void main(String[] args) {
//	LayoutMarkov.layout("E:\\markov", "D:\\ModelDriverProjectFile\\NoTimeMarkov\\EADemo2Seq_MarkovChainModel2.markov.violet.xml", 
//			"D:\\ModelDriverProjectFile\\NoTimeMarkov\\EADemo2Seq_MarkovChainModel2layout.markov.violet.xml");
//    System.out.println("hello");
  }
}
