package cn.hdu.timentity;

import java.util.ArrayList;
import java.util.List;

/*
 * ×´Ì¬Ç¨ÒÆµÄÊµÌå
 */
public class Transition {

	
	
	private String source;
	private String target;
	private String select;
	private String synchronisation;
	private String assignment;
	private String guard;
	
	private String selectX;
	private String selectY;
	private String synchronisationX;
	private String synchronisationY;
	private String assignmentX;
	private String assignmentY;
	private String guardX;
	private String guardY;
	
	private Location sourceLoc;
	private Location targetLoc;
	private List<Nail> nails = new ArrayList<Nail>();
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTarget() {
		return target;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public String getSelect() {
		return select;
	}
	public void setSelect(String select) {
		this.select = select;
	}
	public String getSynchronisation() {
		return synchronisation;
	}
	public void setSynchronisation(String synchronisation) {
		this.synchronisation = synchronisation;
	}
	public String getAssignment() {
		return assignment;
	}
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}
	public String getGuard() {
		return guard;
	}
	public void setGuard(String guard) {
		this.guard = guard;
	}
	public String getSelectX() {
		return selectX;
	}
	public void setSelectX(String selectX) {
		this.selectX = selectX;
	}
	public String getSelectY() {
		return selectY;
	}
	public void setSelectY(String selectY) {
		this.selectY = selectY;
	}
	public String getSynchronisationX() {
		return synchronisationX;
	}
	public void setSynchronisationX(String synchronisationX) {
		this.synchronisationX = synchronisationX;
	}
	public String getSynchronisationY() {
		return synchronisationY;
	}
	public void setSynchronisationY(String synchronisationY) {
		this.synchronisationY = synchronisationY;
	}
	public String getAssignmentX() {
		return assignmentX;
	}
	public void setAssignmentX(String assignmentX) {
		this.assignmentX = assignmentX;
	}
	public String getAssignmentY() {
		return assignmentY;
	}
	public void setAssignmentY(String assignmentY) {
		this.assignmentY = assignmentY;
	}
	public String getGuardX() {
		return guardX;
	}
	public void setGuardX(String guardX) {
		this.guardX = guardX;
	}
	public String getGuardY() {
		return guardY;
	}
	public void setGuardY(String guardY) {
		this.guardY = guardY;
	}
	public List<Nail> getNails() {
		return nails;
	}
	public Location getSourceLoc() {
		return sourceLoc;
	}
	public void setSourceLoc(Location sourceLoc) {
		this.sourceLoc = sourceLoc;
	}
	public Location getTargetLoc() {
		return targetLoc;
	}
	public void setTargetLoc(Location targetLoc) {
		this.targetLoc = targetLoc;
	}
}
