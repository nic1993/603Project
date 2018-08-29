package cn.hdu.serviceImp;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.jgraph.graph.EdgeView;

import cn.edu.layout.LayoutMarkov;
import cn.hdu.seqentity.Constraint;
import cn.hdu.seqentity.DiagramsData;
import cn.hdu.seqentity.Fragment;
import cn.hdu.seqentity.LifeLine;
import cn.hdu.seqentity.Message;
import cn.hdu.seqentity.Operand;
import cn.hdu.seqentity.State;
import cn.hdu.service.ExchangeWork;
import cn.hdu.timentity.Declaration;
import cn.hdu.timentity.Location;
import cn.hdu.timentity.SystemStatment;
import cn.hdu.timentity.Template;
import cn.hdu.timentity.TimedAutomata;
import cn.hdu.timentity.Transition;

public class ExchangeWorkImpl implements ExchangeWork {
    
	private TimedAutomata timedAutomata;
	
	private DiagramsData dd;
	
	public static void main(String[] args) {
		String str = "t>=30";
		System.out.println(str.matches("[a-zA-Z]<=\\d+|[a-zA-Z]<\\d+|[a-zA-Z]>\\d+|[a-zA-Z]>=\\d+"));
		
	}
	
	public ExchangeWorkImpl(TimedAutomata timedAutomata,DiagramsData dd) {
		// TODO Auto-generated constructor stub
		this.timedAutomata = timedAutomata;
		this.dd = dd;
	}
	
	public void exchange() {
		Declaration declaration = new Declaration();
		SystemStatment system = new SystemStatment();
		
		timedAutomata.setDeclaration(declaration);
		timedAutomata.setSystemStatment(system);
		
		globelStatement(dd.getParams(), declaration);
		chanStatement(dd.getLifelineArray(), declaration);
		systemStatement(dd.getLifelineArray(), system);
		
		templateStatement(dd);
		
	}
	
	@Override
	public void globelStatement(List<String> params, Declaration declaration) {
		// TODO Auto-generated method stub
		if(params.size() > 0) {
			for(int i = 0;i < params.size();i++) {
				for(String str : params) {
					if(str.contains("const")) {
						String newConst = declaration.getConst() + str + ";";
						declaration.setConst(newConst);
					}else if (str.contains("clock")) {
						String newclock = declaration.getClocks() + str + ";";
						declaration.setClocks(newclock);
					}else if(str.contains("int")) {
						String newVariable = declaration.getVariables() + str + ";";
						declaration.setVariables(newVariable);
					}
				}
			}
		}
	}

	@Override
	public void chanStatement(List<LifeLine> lifeLines, Declaration declaration) {
		// TODO Auto-generated method stub
		String chan = "chan ";
		for(LifeLine lifeLine : lifeLines) {
			List<Message> sendMessages = lifeLine.getSendMessages();
			for(Message message : sendMessages) {
				chan += message.getName().substring(0, message.getName().length() - 2) + ",";
			}
		}
		//去除最后一个，并添加；
		String newChan = chan.substring(0, chan.length() - 1) + ";";
	    declaration.setChans(newChan);
	}

	@Override
	public void templateStatement(DiagramsData dd) {
		// TODO Auto-generated method stub
			ArrayList<LifeLine> lifeLines = dd.getLifelineArray();
			for(LifeLine lifeLine : lifeLines) {
				//是Probe模块特殊处理
				if(lifeLine.getName().contains("Probe")) {
					Template template = new Template();
					template.setName(getName(lifeLine));
					
					timedAutomata.getTemplates().add(template);
					initStatment(lifeLine,template);
					
					LayoutMarkov.layout(template);
					
				}else if (lifeLine.getName().contains("Envoirnment")) {
					Template template = new Template();
					template.setName(getName(lifeLine));
					
					timedAutomata.getTemplates().add(template);
					environemntStatement(lifeLine,template);			
					
					LayoutMarkov.layout(template);
				}
				//是Soft模块特殊处理
				else if (lifeLine.getName().contains("Soft")) {
					Template template = new Template();
					template.setName(getName(lifeLine));
					timedAutomata.getTemplates().add(template);
					softStatment(dd, lifeLine,template);
					
					LayoutMarkov.layout(template);
				}else if (lifeLine.getName().contains("Planner")) {
					Template template = new Template();
					template.setName(getName(lifeLine));
					
					State waitState = lifeLine.getStates().get(0);
					Location initLoc = new Location();
					initLoc.setName(waitState.getName());
					initLoc.setId(waitState.getId());
					
					State overState = lifeLine.getStates().get(lifeLine.getStates().size() - 1);
					Location overLoc = new Location();
					overLoc.setName(overState.getName());
					overLoc.setId(overState.getId());
					
					Transition overTowait = new Transition();
					overTowait.setSource(overState.getId());
					overTowait.setTarget(waitState.getId());
					
					overTowait.setSourceLoc(overLoc);
					overTowait.setTargetLoc(initLoc);
					overTowait.setAssignment(waitState.getUpdate());
					overTowait.setSelect(waitState.getSelect());
					
					template.setInitRefID(waitState.getId());
					template.getTransitions().add(overTowait);
					template.getLocations().add(initLoc);
					template.getLocations().add(overLoc);
					for(List<State> states : lifeLine.getLists()) {
					    if(states.contains(waitState)) states.remove(waitState);
					    planStatement(states, lifeLine, initLoc, overLoc, template);
					}
					timedAutomata.getTemplates().add(template);
					
					LayoutMarkov.layout(template);
				}else {
					Template template = new Template();
					template.setName(getName(lifeLine));
					timedAutomata.getTemplates().add(template);
					State waitState = lifeLine.getStates().get(0);
					Location initLoc = new Location();
					initLoc.setName(waitState.getName());
					initLoc.setId(waitState.getId());
					
					State overState = lifeLine.getStates().get(lifeLine.getStates().size() - 1);

					Location overLoc = new Location();
					overLoc.setName(overState.getName());
					overLoc.setId(overState.getId());
					
					Transition overTowait = new Transition();
					overTowait.setSource(overState.getId());
					overTowait.setTarget(waitState.getId());
					
					overTowait.setSourceLoc(overLoc);
					overTowait.setTargetLoc(initLoc);
					overTowait.setAssignment(waitState.getUpdate());
					
					template.setInitRefID(waitState.getId());
					template.getTransitions().add(overTowait);
					template.getLocations().add(initLoc);
					template.getLocations().add(overLoc);
					
					for(List<State> states : lifeLine.getLists()) {
//						List<List<State>> lists = getSplit(states);
//						
//						statement(lists, lifeLine,waitState,overState,template);
						change(getStates(states), lifeLine, waitState, initLoc, overState, overLoc, template);
					}
					
					
					
					LayoutMarkov.layout(template);
					
				}
			}		
	}

	@Override
	public void systemStatement(List<LifeLine> lifeLines,SystemStatment system) {
		// TODO Auto-generated method stub
		String systemStr = "system ";
		String template = "";
		for(LifeLine lifeLine : lifeLines) {
			systemStr += getName(lifeLine).toLowerCase().replaceAll("&", "&amp;") + ",";
			template += getName(lifeLine).toLowerCase().replaceAll("&", "&amp;") + " = " + getName(lifeLine).replaceAll("&", "&amp;") + "();";
		}
		String newsystemStr = systemStr.substring(0, systemStr.length() - 1) + ";";
		system.setSystem(newsystemStr);
		system.setTemplate(template);
	}
	
	/**
	 * 探测模块特殊处理
	 * @param dd
	 * @param lifeLine
	 */
	private void initStatment(LifeLine lifeLine,Template template) {
		State waitState = lifeLine.getStates().get(0);
		State probState = lifeLine.getStates().get(1);
		
		Location initLocation = new Location();
		initLocation.setId(waitState.getId());
		initLocation.setName(waitState.getName());
		if(waitState.getInvariant() != null && waitState.getInvariant().matches("[a-zA-Z]<=\\d+|[a-zA-Z]<\\d+|[a-zA-Z]>\\d+|[a-zA-Z]>=\\d+")) {
			initLocation.setInvariant(waitState.getInvariant());
		}
		
		template.getLocations().add(initLocation);
		
		Location probLoaction = new Location();
		probLoaction.setId(probState.getId());
		probLoaction.setName(probState.getName());
		template.getLocations().add(probLoaction);
		
		//处理waiting状态到probe状态迁移
		Transition waitToprobe = new Transition();
		waitToprobe.setSource(initLocation.getId());
		waitToprobe.setTarget(probLoaction.getId());
		if(probState.getGuard() != null) 
		{
			waitToprobe.setGuard(probState.getGuard());
		}
		if(probState.getInvariant() != null && probState.getInvariant().matches("[a-zA-Z]==\\d+")) {
			if(waitToprobe.getGuard() != null) {
				waitToprobe.setGuard(waitToprobe.getGuard() + "&&" + probState.getInvariant());
			}else {
				waitToprobe.setGuard(probState.getInvariant());
			}
			
		}
		
		waitToprobe.setSourceLoc(initLocation);
		waitToprobe.setTargetLoc(probLoaction);
		template.getTransitions().add(waitToprobe);
		
		//处理probe状态到waiting状态迁移
		Transition probeTowait = new Transition();
		probeTowait.setSource(probLoaction.getId());
		probeTowait.setTarget(initLocation.getId());
		
		probeTowait.setSourceLoc(probLoaction);
		probeTowait.setTargetLoc(initLocation);
		if(waitState.getSelect() != null) {
			probeTowait.setSelect(waitState.getSelect());
		}
		
		if(waitState.getUpdate() != null) {
			probeTowait.setGuard(waitState.getGuard());
		}
		
		if(waitState.getUpdate() != null) {
			probeTowait.setAssignment(waitState.getUpdate());
		}

			
//			if(probState.getInvariant() != null) {
//				if(probeTowait.getGuard() == null) {
//					probeTowait.setGuard(probState.getInvariant());
//				}else {
//					probeTowait.setGuard(probeTowait.getGuard() + "&&" + probState.getInvariant());
//				}
//			}
		
		
		if(probState.getSendSync().size() != 0) {
			String send = "";
			for(Message message : probState.getSendSync()) {
				send += FixMessageName(message.getName()) + "! ";
			}
			probeTowait.setSynchronisation(send);
		}

		if(waitState.getUpdate() != null) {
			probeTowait.setAssignment(waitState.getUpdate());
		}
		
		template.getTransitions().add(probeTowait);

		template.setInitRefID(initLocation.getId());
		
		
		//缺少局部变量声明
	}
	
	/**
	 * 环境模块特殊处理
	 * @param lifeLine
	 * @param template
	 */
	private void environemntStatement(LifeLine lifeLine,Template template) {
		State waitState = lifeLine.getStates().get(0);
		State effectState = lifeLine.getStates().get(1);
		
		Location initLocation = new Location();
		initLocation.setId(waitState.getId());
		initLocation.setName(waitState.getName());
		
		if(waitState.getInvariant() != null)
			initLocation.setInvariant(waitState.getInvariant());
		template.getLocations().add(initLocation);
		
		Location probLoaction = new Location();
		probLoaction.setId(effectState.getId());
		probLoaction.setName(effectState.getName());
		template.getLocations().add(probLoaction);
		
		//处理waiting状态到probe状态迁移
		Transition waitToeffect = new Transition();
		waitToeffect.setSource(initLocation.getId());
		waitToeffect.setTarget(probLoaction.getId());
		waitToeffect.setGuard(effectState.getGuard());
		waitToeffect.setSourceLoc(initLocation);
		waitToeffect.setTargetLoc(probLoaction);
		template.getTransitions().add(waitToeffect);
		
		//处理probe状态到waiting状态迁移
		Transition effectTowait = new Transition();
		effectTowait.setSource(probLoaction.getId());
		effectTowait.setTarget(initLocation.getId());
		effectTowait.setSourceLoc(probLoaction);
		effectTowait.setTargetLoc(initLocation);
		if(waitState.getSelect() != null) {
			effectTowait.setSelect(waitState.getSelect());
		}
				
				
		if(waitState.getUpdate() != null) {
			effectTowait.setAssignment(waitState.getUpdate());
		}
		
        if(effectState.getGuard() != null) {
        	effectTowait.setGuard(effectState.getGuard());
		}
		
//		if(effectState.getInvariant() != null) {
//			if(effectTowait.getGuard() == null) {
//				effectTowait.setGuard(effectState.getInvariant());
//			}else {
//				effectTowait.setGuard(effectTowait.getGuard() + "&&" + effectState.getInvariant());
//			}
//		}
		
		if(effectState.getSendSync().size() != 0) {
			String send = "";
			for(Message message : effectState.getSendSync()) {
				send += FixMessageName(message.getName()) + "! ";
			}
			
			effectTowait.setSynchronisation(send);
		}
		
		template.getTransitions().add(effectTowait);

		template.setInitRefID(initLocation.getId());
	}
	
	/**
	 * 
	 * @param states 状态集合
	 * @param lifeLine 当前生命节点
	 * @param waiting 初始状态
	 * @param over 结束位置
	 * @param template 模板
	 */
	private void planStatement(List<State> states,LifeLine lifeLine,Location waiting,Location over,Template template) {
		//结束状态到初始状态
		State planState = states.get(0);
		Location planLoc = new Location();
		planLoc.setId(planState.getId());
		planLoc.setName(planState.getName());
		template.getLocations().add(planLoc);
		
		List<Message> sendMessage = planState.getSendSync();
		Message reveiveMessage = planState.getReceiveSync();
		
		Transition waitToplan = new Transition();
		waitToplan.setSource(waiting.getId());
		waitToplan.setTarget(planState.getId());
		waitToplan.setSourceLoc(waiting);
		waitToplan.setTargetLoc(planLoc);
		
		if(reveiveMessage != null) {
			waitToplan.setSynchronisation(FixMessageName(reveiveMessage.getName()) + "?");
			waitToplan.setGuard(planState.getGuard());
			waitToplan.setAssignment(planState.getUpdate());
			waitToplan.setSelect(planState.getSelect());
		}
		template.getTransitions().add(waitToplan);
		
		for(int i = 0;i < sendMessage.size();i++) {
			Transition transition = new Transition();
			transition.setSource(planState.getId());
			transition.setTarget(over.getId());
			
			transition.setSourceLoc(planLoc);
			transition.setTargetLoc(over);
			FixSeTranstion(sendMessage.get(i), transition);
			template.getTransitions().add(transition);
		}
	}
	/**
	 *  对决策特殊处理
	 * @param dd
	 * @param lifeLine
	 */
	private void softStatment(DiagramsData dd,LifeLine lifeLine,Template template) { 
		template.setName(getName(lifeLine));
		
		State waitState = lifeLine.getStates().get(0);
		
		String[] updates = null;
		if(waitState.getUpdate() != null && !waitState.getUpdate().equals("")) {
			updates = waitState.getUpdate().split(";");
		}

		Location initLocation = new Location();
		initLocation.setId(waitState.getId());
		initLocation.setName(waitState.getName());
		
		template.setInitRefID(initLocation.getId());
		template.getLocations().add(initLocation);
		
		//从第二个状态开始拼接
		for(int i = 1;i < lifeLine.getStates().size();i++) {
			State actionState = lifeLine.getStates().get(i);
			
			Message message = actionState.getReceiveSync();
			
			Location action = new Location();
			action.setId(actionState.getId());
			action.setName(actionState.getName());
			template.getLocations().add(action);
			
			Transition waitToaction = new Transition();
			waitToaction.setSource(waitState.getId());
			waitToaction.setTarget(action.getId());
			
			waitToaction.setSourceLoc(initLocation);
			waitToaction.setTargetLoc(action);
			if(message != null) {
//				FixReTranstion(message, waitToaction);
				waitToaction.setSynchronisation(FixMessageName(message.getName()) + "?");
			}
			
			if(actionState.getGuard() != null) {
				String guard = "";
				if(waitToaction.getGuard() != null) {
					 guard = waitToaction.getGuard() + "," + actionState.getGuard();
				}else {
					 guard = actionState.getGuard();
				}
				waitToaction.setGuard(guard);
			}
			if(actionState.getSelect() != null) {
				waitToaction.setSelect(actionState.getSelect());
			}
			if(actionState.getUpdate() != null) {
				waitToaction.setAssignment(actionState.getUpdate());
			}
			
			template.getTransitions().add(waitToaction);
			
			Transition actionTowait = new Transition();
			actionTowait.setSource(action.getId());
			actionTowait.setTarget(waitState.getId());
			
			actionTowait.setSourceLoc(action);
			actionTowait.setTargetLoc(initLocation);
			if(waitState.getGuard() != null) {
              actionTowait.setGuard(waitState.getGuard());
			}
			
			if(updates != null) {
				for(String str : updates) {
					String name = str.split(":")[0];
					String update = str.split(":")[1];
					if(name.equals(actionState.getName())) {
						actionTowait.setGuard(update);
						break;
					}
				}
			}
			
			template.getTransitions().add(actionTowait);
		}
	}
	
	
	private void statement(List<List<State>> lists,LifeLine lifeLine,State waiting,State over,Template template) {
		HashMap<Location, State> map = new HashMap<>();
		if(lists.size() == 1) {
			List<State> states = lists.get(0);
			List<Location> locations = new ArrayList<>();
			
			for(int i = 0; i < states.size() - 1;i++) {
				State state = states.get(i);
				Location location = new Location();
				locations.add(location);
				
				location.setId(state.getId());
				location.setName(state.getName());
				
				if(!location.getName().equals("Over")) {
					template.getLocations().add(location);
				}
				map.put(location, state);
			}
			
			//从初始状态出发的第一个迁移
			Location fir = locations.get(0);
			State firState = map.get(fir);
			Message message = map.get(fir).getReceiveSync();
			Transition waitTofir = new Transition();
			waitTofir.setSource(waiting.getId());
			waitTofir.setTarget(fir.getId());
			
			if(message != null) {
				String syn = "";
				if(waitTofir.getSynchronisation() != null) {
					syn = waitTofir.getSynchronisation() + "," + FixMessageName(message.getName()) + "?"; 
				}else {
					syn = FixMessageName(message.getName()) + "?";
				}
				waitTofir.setSynchronisation(syn);
			}
			
			waitTofir.setGuard(firState.getGuard());
			waitTofir.setSelect(firState.getSelect());
			waitTofir.setAssignment(firState.getUpdate());
			
			template.getTransitions().add(waitTofir);
			
			for(int i = 0;i < locations.size();i++) {
				if(locations.get(i).getName().equals("Over")) continue;
				
				Location location = locations.get(i);
				State state = map.get(location);
				//第一个由waiting状态转移
				if(i == 0) {
						//最后一个非over状态的节点
					    State nextState = map.get(locations.get(locations.size() - 1));
						if(i == locations.size() - 2) {
							List<Constraint> constraints = state.getConstraints();
							
							List<Message> messages  = state.getSendSync();
							Message receive = state.getReceiveSync();
		
							if(state.getConstraints().size() != 0) {
								//后期可能需要处理 默认第一个约束没有消息发出 直接到over状态
									Constraint constraint = state.getConstraints().get(0);

									Transition locTover = new Transition();
									locTover.setSource(location.getId());
									locTover.setTarget(over.getId());
									locTover.setGuard(constraint.getText() + "&&" + nextState.getGuard());
									template.getTransitions().add(locTover);
								}
							}else {
								Transition locTover = new Transition();
								locTover.setSource(location.getId());
								locTover.setTarget(over.getId());
								
								locTover.setGuard(nextState.getGuard());
								locTover.setAssignment(nextState.getUpdate());
								locTover.setSelect(nextState.getSelect());
								
								List<Message> messages2  = state.getSendSync();
								if(messages2.size() != 0) {
									/*TODO*/
									for(Message message2 : messages2) {
										locTover.setSynchronisation(FixMessageName(message2.getName()) + "?");
									}
								}
								template.getTransitions().add(locTover);
							}
					}else {
						  Location next = locations.get(i + 1);
						  State nextState = map.get(next);
						  
						  Transition locTover = new Transition();
						  locTover.setSource(location.getId());
						  locTover.setTarget(next.getId());
						  
						  locTover.setGuard(nextState.getGuard());
						  locTover.setAssignment(nextState.getUpdate());
						  locTover.setSelect(nextState.getSelect());
						  
						  template.getTransitions().add(locTover);
					}
			}	
		}
		else {
				List<State> states1 = lists.get(0);
				
				for(List<State> states : lists) {
					for(State state : states) {
						Location location = new Location();
						
						location.setId(state.getId());
						location.setName(state.getName());
						if(!location.getName().equals("Over"))
						template.getLocations().add(location);
						
						map.put(location, state);
					}
				}
				Connect(map, lists, 0, waiting, over,0,template);
		}
	}
	
	/**
	 *  按照over来分割状态节点
	 * @param lifeLine
	 * @return
	 */
	private List<List<State>> getSplit(List<State> states){
		List<List<State>> lists = new ArrayList<>();
		List<State> splits = new ArrayList<>();
		for(int i = 0;i < states.size();i++) {
			State state = states.get(i);
			if(state.getName().equals("Waiting")) 
				continue;
			else if(!state.getName().equals("Over")) {
				splits.add(state);
			}else {
				splits.add(state);
				lists.add(splits);
				splits = new ArrayList<>();
			}
		}
		return lists;
	}
	
	/**
	 * 去除Waiting状态节点
	 * @param states
	 * @return
	 */
	private List<State> getStates(List<State> states){
		List<State> splits = new ArrayList<>();
		for(int i = 0;i < states.size();i++) {
			State state = states.get(i);
			if(!state.getName().equals("Waiting")) {
				splits.add(state);
			}
		}
		return splits;
	}

	/**
	 * TODO 
	 * @param map  获取对应location的state
	 * @param lsits 分割的集合
	 * @param i  当前的List<State>
	 * @param fir 每次需要连接的初始位置
	 * @param over 结束位置
	 */
	public void Connect(HashMap<Location, State> map,List<List<State>> lsits,int i,State fir,State over,int loc,Template template) {
        //不是初始状态
		if(!fir.isInitial()) {
			if(loc >= fir.getConstraints().size()) {
				return;
			}else {
				//考虑到约束没有状态节点 
				Constraint constraint = fir.getConstraints().get(loc);
			   if(lsits.size() < i) {
				   Transition firTover = new Transition();
				   firTover.setSource(fir.getId());
				   firTover.setTarget(over.getId());
				   String guard = constraint.getText();
				   Message message = fir.getSendSync().get(0); //默认只存在一条发送消息
				   if(message != null) {
					   firTover.setSynchronisation(FixMessageName(message.getName()) + "!");	   
				   }
			   }else {
				   List<Message> sendMessage = fir.getSendSync();
				   List<State> states = lsits.get(i);
				   if(states.size() == 1) { //只有一个over状态
					   State overState = states.get(0);
					   Transition firTover = new Transition();
					   firTover.setSource(fir.getId());
					   firTover.setTarget(over.getId());
					   if(sendMessage != null) {
						   firTover.setSynchronisation(FixMessageName(sendMessage.get(loc-1).getName()) + "!");
						   firTover.setGuard(constraint.getText());
						   firTover.setAssignment(overState.getUpdate());
						   firTover.setSelect(overState.getSelect());
					   }
					   template.getTransitions().add(firTover);
				   }else {
					   //默认存在两个状态节点
					   State next = states.get(0);
					   List<Message> sendMessage1 = next.getSendSync();
					   Transition firTonext = new Transition();
					   
					   firTonext.setSource(fir.getId());
					   firTonext.setTarget(next.getId());
					   firTonext.setGuard(constraint.getText());
					   firTonext.setAssignment(next.getUpdate());
					   template.getTransitions().add(firTonext);
					  
					   State overState = states.get(0);
					   Transition nextTover = new Transition();
					   nextTover.setSource(next.getId());
					   nextTover.setTarget(over.getId());
					   if(sendMessage1.size() != 0){
						   nextTover.setSynchronisation(FixMessageName(sendMessage1.get(0).getName()) + "!");
					   }
					   nextTover.setAssignment(overState.getUpdate());
					   nextTover.setGuard(overState.getGuard());
					   nextTover.setSelect(overState.getSelect());
					   
					   template.getTransitions().add(nextTover);
					   
				   } 
				   Connect(map, lsits, ++i, fir, over, ++loc,template);
			}
			   
			}
			
        }else {
    			List<State> states = lsits.get(i); //存放不包括wait状态的集合
    			for(int j = 0;j < states.size();j++) {
    				State state = states.get(j);
    				if(state.getName().equals("Over")) continue;
    				//第一个状态
    				if(j == 0) {
//    					Message sendMessage = new Message();
    					Message receiveMessage = state.getReceiveSync();
    					Location wait = getKey(map, fir);
    					Location action = getKey(map, state);
    					
    					Transition waitToaction = new Transition();
    					waitToaction.setSource(fir.getId());
    					waitToaction.setTarget(action.getId());
    					if(receiveMessage != null) {
    						waitToaction.setSynchronisation(FixMessageName(receiveMessage.getName()) + "?");
    						waitToaction.setGuard(state.getGuard());
    						waitToaction.setAssignment(state.getUpdate());
    						waitToaction.setSelect(state.getSelect());
    					}
    					template.getTransitions().add(waitToaction);
    					
    					if(state.getConstraints().size() != 0) {
    						State nextState = states.get(j+1);
    						Constraint constraint = state.getConstraints().get(0);
    						Transition nowTover = new Transition();
    						nowTover.setSource(action.getId());
    						nowTover.setTarget(over.getId());
    						
    						nowTover.setGuard(constraint.getText());
    						nowTover.setAssignment(nextState.getUpdate());
    						nowTover.setSelect(nextState.getSelect());
    						
    						template.getTransitions().add(nowTover);
    						Connect(map, lsits, i+1, state, over,1,template);
        					break; 
    					}else {
								State next = states.get(j+1);
								
								Transition transition = new Transition();
								transition.setSource(state.getId());
								if(next.getName().equals("Over")) {
									transition.setTarget(over.getId());
								}else {
									transition.setTarget(next.getId());
								}
								
								/*TODO*/
								
								transition.setAssignment(next.getUpdate());
								transition.setGuard(next.getGuard());
								transition.setSelect(next.getSelect());
								
								template.getTransitions().add(transition);
						}	
    				}else if(j == states.size() - 2){
    					if(state.getConstraints().size() != 0) {
    						Constraint constraint = state.getConstraints().get(0);
    						Transition nowTover = new Transition();
    						nowTover.setSource(state.getId());
    						nowTover.setTarget(over.getId());
    						
    						nowTover.setGuard(constraint.getText());
    						
    						Connect(map, lsits, i+1, state, over,1,template);
        					break; 
    					}else {
    						Transition nowTover = new Transition();
    						nowTover.setSource(state.getId());
    						nowTover.setTarget(over.getId());
						}
    					
    				}else {
    					if(state.getConstraints().size() != 0) {
    						Constraint constraint = state.getConstraints().get(0);
    						Transition nowTover = new Transition();
    						nowTover.setSource(state.getId());
    						nowTover.setTarget(over.getId());
    						
    						nowTover.setGuard(constraint.getText());
    						
    						Connect(map, lsits, i+1, state, over,1,template);
        					break; 
    					}else {
    						State next =  states.get(j + 1);
    						Transition nowTover = new Transition();
    						nowTover.setSource(state.getId());
    						nowTover.setTarget(next.getId());
						}	
    				}
    			}
		}
	}
	
	private void change(List<State> states,LifeLine lifeLine,State waitState, Location initLoc,State over,Location overLoc,Template template) {
		State preState = waitState;
		Location preLoc = initLoc;
		HashMap<State, Integer> map = new HashMap<>();
		int index = 0; //标记状态间约束位置	
		for(int i = 0;i < states.size();i++) {
			State next = states.get(i);
			Location nextLoc = new Location();
			if(next.getName().equals("Over")) {
				nextLoc = overLoc;
			}else {
				nextLoc.setName(next.getName());
				nextLoc.setId(next.getId());
				
				template.getLocations().add(nextLoc);
			}
			Transition preTonext = new Transition();
			preTonext.setSource(preState.getId());
			preTonext.setTarget(nextLoc.getId());
			
			preTonext.setSourceLoc(preLoc);
			preTonext.setTargetLoc(nextLoc);
			preTonext.setAssignment(next.getUpdate());
			
			template.getTransitions().add(preTonext);
			
			if(i == 1 && preState.getGuard() != null) {
				preTonext.setGuard(preState.getGuard());
			}
			if(next.getInvariant() != null && next.getInvariant().matches("[a-zA-Z]==\\d+")) {
				if(preTonext.getGuard() != null) {
					preTonext.setGuard(preTonext.getGuard() + "&&" + next.getInvariant());
				}else {
					preTonext.setGuard(next.getInvariant());
				}
			}
			
			if(preState.getConstraints() != null && preState.getConstraints().size() > 0) {
				Constraint constraint = preState.getConstraints().get(index);
				
				if(preTonext.getGuard() != null) {
					preTonext.setGuard(preTonext.getGuard() + "&&" + constraint.getText());
				}else {
					preTonext.setGuard(constraint.getText());
				}
				index++;
			}
			
			if(next.getReceiveSync() != null) {
				preTonext.setSynchronisation(FixMessageName(next.getReceiveSync().getName()) + "?");
			}
			
			if(preState.getSendSync().size() != 0 && index == 2) {
				preTonext.setSynchronisation(FixMessageName(preState.getSendSync().get(0).getName()) + "!");
			}
			
			if(!next.getName().equals("Over")) {
				preState = next;
			}
		}
	}
	
	private void FixReTranstion(Message message,Transition transition) {
			String syn = "";
			if(transition.getSynchronisation() != null) {
				syn = transition.getSynchronisation() + "," + FixMessageName(message.getName()) + "?"; 
			}else {
				syn = FixMessageName(message.getName()) + "?";
			}
			transition.setSynchronisation(syn);
			
			if(message.getGuard() != null) {
				String guard = "";
				if(transition.getGuard() != null) {
					 guard = transition.getGuard() + "," + message.getGuard();
				}else {
					 guard = message.getGuard();
				}
				transition.setGuard(guard);
			}

//			if(message.getUpdate() != null) {
//				String ass = "";
//				if(transition.getAssignment() != null) {
//					ass = transition.getAssignment() + "," + message.getUpdate();
//				}else {
//					ass = message.getUpdate();
//				}
//				transition.setAssignment(ass);
//			}
//			
//			if(message.getSelect() != null) {
//				String select = "";
//				if(transition.getSelect() != null) {
//					select = transition.getSelect() + "," + message.getSelect();
//				}
//				else {
//					select = message.getSelect();
//				}
//				transition.setSelect(select);
//			}
		}

	
	private void FixSeTranstion(Message message,Transition transition) {
		String syn = "";
		if(transition.getSynchronisation() != null) {
			syn = transition.getSynchronisation() + "," + FixMessageName(message.getName()) + "!"; 
		}else {
			syn = FixMessageName(message.getName()) + "!";
		}
		transition.setSynchronisation(syn);
		
		if(message.getGuard() != null) {
			String guard = "";
			if(transition.getGuard() != null) {
				 guard = transition.getGuard() + "," + message.getGuard();
			}else {
				 guard = message.getGuard() ;
			}
			transition.setGuard(guard);
		}
		
//		if(message.getUpdate() != null) {
//			String ass = "";
//			if(transition.getAssignment() != null) {
//				ass = transition.getAssignment() + "," + message.getUpdate();
//			}else {
//				ass = message.getUpdate();
//			}
//			transition.setAssignment(ass);
//		}
//		
//		if(message.getSelect() != null) {
//			String select = "";
//			if(transition.getSelect() != null) {
//				select = transition.getSelect() + "," + message.getSelect();
//			}
//			else {
//				select = message.getSelect();
//			}
//			transition.setSelect(select);
//		}
	}
	
	private String getName(LifeLine lifeLine) {
		return lifeLine.getName().split(":")[1];
	}
	
	//去除消息名称最后的()
	private String FixMessageName(String name) {
		return name.substring(0, name.length() - 2);
	}
	/**
	 * 根据value获取key值
	 * @param map
	 * @param value
	 * @return
	 */
	 private static Location getKey(HashMap<Location,State> map,State state){  
	        Location key = null;  
	        for (Entry<Location, State> entry : map.entrySet()) {  
	            if(state.equals(entry.getValue())){  
	                key=entry.getKey();  
	            }  
	        }  
	        return key;  
	    } 
	private static List<Object> list = new ArrayList<>();
}
