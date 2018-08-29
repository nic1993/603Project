package cn.hdu.tool;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cn.hdu.seqentity.Constraint;
import cn.hdu.seqentity.DiagramsData;
import cn.hdu.seqentity.Fragment;
import cn.hdu.seqentity.LifeLine;
import cn.hdu.seqentity.Message;
import cn.hdu.seqentity.Operand;
import cn.hdu.seqentity.SDRectangle;
import cn.hdu.seqentity.State;
import cn.hdu.timentity.SystemStatment;

public class FixTool {
	
	@SuppressWarnings("unchecked")
	public static void fixFragmentsOfOneDiagram(ArrayList<Fragment> allFrags,DiagramsData dd) 
	{
		ArrayList<Fragment> fragments = allFrags;		
		Collections.sort(fragments, new SortByTopWithFrag()); //�����������,�߶���С�������Ƭ�δ��ϵ���
		for(Fragment frag:fragments)
		{
			Collections.sort(frag.getOperands(), new SortByTopWithOper()); //�����������,�߶���С�������Ƭ�δ��ϵ���			
		}
		//��״̬��װ��������
		encapsulateOperWithState(fragments, dd.getStates());
		//�����Ƭ�εĲ������װ��Ϣ
		fixMessageInOperand(allFrags,dd.getMessageArray());
		//**********���������������Ƭ�ε�Ƕ�׵����
		fixFragments(fragments);
		
	}
	@SuppressWarnings("unchecked")
	public static void sortMesses(ArrayList<Message> messageList) {
			Collections.sort(messageList, new SortByYWithMess());		
	}
	@SuppressWarnings("unchecked")
	public static void sortConstraints(ArrayList<Constraint> constraints) {
			Collections.sort(constraints, new SortByYWithConstraint());		
	}
	@SuppressWarnings("unchecked")
	public static void sortStates(ArrayList<State> stateList) {
			Collections.sort(stateList, new SortByYWithState());		
	}
	@SuppressWarnings("unchecked")
	private static void encapsulateOperWithState(ArrayList<Fragment> fragments,ArrayList<State> states)
	{
		//����   ����Ƭ��ref ����������ID***************************************************************
		//������������Ƭ�β�����
				
				for (int i = 0; i < states.size(); i++) 
				{			
					State state = states.get(i);			
					int flag=0;
					for (int j = fragments.size() - 1; j >= 0; j--) 
					{				
						Fragment fragmentJ = fragments.get(j);
						for(int k=fragmentJ.getOperands().size()-1;k>=0;k--)
						{
							Operand oper=fragmentJ.getOperands().get(k);
							if (rectangleI_in_rectangleJ(state.getRectangle(), oper.getRectangle())) {						
								oper.getStates().add(state);
								flag=1;
								break;
							}
						}
						if(flag==1)
						{
							break;
						}
					}
				}
		
	}
	/**
	 * �������꣬����ϢǶ�׽���Ӧ�Ĳ�����
	 * @param allFrags
	 * @param messes
	 */
	private static void fixMessageInOperand(ArrayList<Fragment> allFrags,ArrayList<Message> messes)
	{
		for (Message m:messes) {			
			for(int i=allFrags.size()-1;i>=0;i--)
			{
				int f1=0;
				if(m.getPointY()>=allFrags.get(i).getRectangle().getTop()
						&&m.getPointY()<=allFrags.get(i).getRectangle().getBottom())
				{
					for(int j=allFrags.get(i).getOperands().size()-1;j>=0;j--)
					{
						if(m.getPointY()>=allFrags.get(i).getOperands().get(j).getRectangle().getTop()
								&& m.getPointY()<=allFrags.get(i).getOperands().get(j).getRectangle().getBottom())
						{
							
							m.setInFragment(true);
							m.setFragmentId(allFrags.get(i).getId());
							m.setFragType(allFrags.get(i).getType());
							m.setOperandId(allFrags.get(i).getOperands().get(j).getId());		
							allFrags.get(i).getOperands().get(j).getMessages().add(m);
							f1=1;
							break;
						}
					}
				}
				if(f1==1)
				{
					break;
				}
			}
		}
	}
	/**
	 * ���������������Ƭ�ε�Ƕ�����
	 * @param fragments
	 */
	private static void fixFragments(ArrayList<Fragment> fragments)
	{
		for (int i = 1; i < fragments.size(); i++) {
			int flag=0;
			Fragment fragmentI = fragments.get(i);
			for (int j = i - 1; j >= 0; j--) {
				Fragment fragmentJ = fragments.get(j);
				for(int k=fragmentJ.getOperands().size()-1;k>=0;k--)
				{
					Operand oper=fragmentJ.getOperands().get(k);
					if (rectangleI_in_rectangleJ(fragmentI.getRectangle(), oper.getRectangle())) {
						
						//�������Ƭ������������ID*********************************************************************
						fragmentI.setInID(oper.getId());
						oper.getFragmentIDs().add(fragmentI.getId());//����������������Ƭ�ε�id
						oper.getFragments().add(fragmentI);
						oper.setHasFragment(true);
						fragmentI.setEnFlag(true);
						flag=1;
						break;
					}
				}
				if(flag==1)
				{
					break;
				}			
			}
		}
		
		//ȥ��Ƕ�׵����Ƭ��  
		//i--����д��Ϊ�˸ı����������ⱨ�������޸��쳣����)
		for(int i=0;i<fragments.size();i++)
		{
			if(fragments.get(i).isEnFlag())
			{
				fragments.remove(i);
				i--;
			}
		}
	}

	private static boolean rectangleI_in_rectangleJ(SDRectangle rectangleI, SDRectangle rectangleJ) {
		if (rectangleI.getTop() < rectangleJ.getTop()) {
			return false;
		}
		if (rectangleI.getLeft() < rectangleJ.getLeft()) {
			return false;
		}
		if (rectangleI.getBottom() > rectangleJ.getBottom()) {
			return false;
		}
		if (rectangleI.getRight() > rectangleJ.getRight()) {
			return false;
		}
		return true;
	}
	
	public static boolean Point_in_rectangle(SDRectangle rectangleI,double x,double y) {
		if(rectangleI.getTop() >  y || rectangleI.getBottom() < y) {
			return false;
		}
		if(rectangleI.getLeft() > x || rectangleI.getRight() < x) {
			return false;
		}
		return true;
	}
	
	public static boolean Point_belong_rectangle(SDRectangle rectangleI,double x,double y) {
		if(rectangleI.getLeft() > x || rectangleI.getRight() < x) {
			return false;
		}
		return true;
		
	}
	public static List<List<State>> SplitMonitorState(LifeLine lifeLine){
		List<State> states = lifeLine.getStates();
		List<List<State>> lists = new ArrayList<>();
		List<State> list = new ArrayList<>();
		int flag = 0; //��� �Ƿ��ǵ�һ�����
		if(lifeLine.getName().contains("Monitor")) {
			flag = 0;
			list = new ArrayList<>();
			for(int i = 0;i < states.size();i++) {
				if(states.get(i).getName().equals("Comparing")) {
				   if(flag == 0) {
					   list.add(states.get(i));
					   flag = 1;
				   }else {
					   lists.add(list);
					   list = new ArrayList<>();
					   list.add(states.get(i));
				}
				}else {
					list.add(states.get(i));
				}
			}
			lists.add(list);
		}else if (lifeLine.getName().contains("Analyzer")) {
			flag = 0;
			list = new ArrayList<>();
			for(int i = 0;i < states.size();i++) {
				if(states.get(i).getName().equals("Analyzing")) {
				   if(flag == 0) {
					   list.add(states.get(i));
					   flag = 1;
				   }else {
					   lists.add(list);
					   list = new ArrayList<>();
					   list.add(states.get(i));
				}
				}else {
					list.add(states.get(i));
				}
			}
			lists.add(list);
		}else if (lifeLine.getName().contains("Planner")) {
			flag = 0;
			list = new ArrayList<>();
			for(int i = 0;i < states.size();i++) {
				if(states.get(i).getName().equals("Planning")) {
				   if(flag == 0) {
					   list.add(states.get(i));
					   flag = 1;
				   }else {
					   lists.add(list);
					   list = new ArrayList<>();
					   list.add(states.get(i));
				}
				}else {
					list.add(states.get(i));
				}
			}
			lists.add(list);
		}
		
		return lists;
	}
	
	        //ʵ��һ�����Ƭ�εıȽ���
			@SuppressWarnings("rawtypes")
			static class SortByTopWithFrag implements Comparator {
				 public int compare(Object o1, Object o2) {
					 Fragment f1 = (Fragment) o1;
					 Fragment f2 = (Fragment) o2;
				  return f1.getRectangle().getTop() > f2.getRectangle().getTop() ? 1 : -1;
				 }
			}
			//ʵ��һ����������ıȽ���
			@SuppressWarnings("rawtypes")
			static class SortByTopWithOper implements Comparator {
				 public int compare(Object o1, Object o2) {
					 Operand oper1 = (Operand) o1;
					 Operand oper2 = (Operand) o2;
				  return oper1.getRectangle().getTop() > oper2.getRectangle().getTop() ? 1 : -1;
				 }
			}
			
			//ʵ��һ����Ϣ�ıȽ���
			@SuppressWarnings("rawtypes")
			static class SortByYWithMess implements Comparator {
				 public int compare(Object o1, Object o2) {
					 Message m1 = (Message) o1;
					 Message m2 = (Message) o2;
				  return m1.getPointY()> m2.getPointY() ? 1 : -1;
				 }
			}
			
			//ʵ��һ���ⲿԼ���ıȽ���
			@SuppressWarnings("rawtypes")
			static class SortByYWithConstraint implements Comparator {
				 public int compare(Object o1, Object o2) {
					 Constraint s1 = (Constraint) o1;
					 Constraint s2 = (Constraint) o2;
				  return s1.getY()> s2.getY() ? 1 : -1;
				 }
			}
			
			//ʵ��һ��״̬�ıȽ���
			@SuppressWarnings("rawtypes")
			static class SortByYWithState implements Comparator {
				 public int compare(Object o1, Object o2) {
					 State s1 = (State) o1;
					 State s2 = (State) o2;
				  return s1.getRectangle().getTop()> s2.getRectangle().getTop() ? 1 : -1;
				 }
			}
			
			
	public static String FixString(String str) {
		if(str.contains("&"))
			str.replaceAll("&", "&amp;");
		if(str.contains("<")) {
			str.replaceAll("<", "&lt;");
		}
		if(str.contains(">")) {
			str.replaceAll(">", "&gt;");
		}
		
		return str;
	}		
	
	public static void main(String[] args) {
		System.out.println(FixString("Load&LowerBound"));
	}
}
