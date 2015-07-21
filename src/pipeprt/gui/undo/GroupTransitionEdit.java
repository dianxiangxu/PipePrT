package pipeprt.gui.undo;

import java.util.ArrayList;

import pipeprt.dataLayer.GroupTransition;
import pipeprt.dataLayer.PipeTransition;

/**
 * 
 * @author Alex Charalambous
 */
public class GroupTransitionEdit extends UndoableEdit {

	GroupTransition groupTransition;

	public GroupTransitionEdit(GroupTransition _groupTransition) {
		groupTransition = _groupTransition;
	}

	/** */
	public void undo() {
		groupTransition.ungroupTransitionsHelper();
	}

	public void redo() {
		PipeTransition foldedInto = groupTransition.getFoldedInto();
		ArrayList<PipeTransition> transitions = new ArrayList<PipeTransition>();
		for(PipeTransition t:groupTransition.getTransitions()){
			transitions.add(t);
		}
		groupTransition.getTransitions().clear();
		// Make the transition "foldedInto" group the transitions 
		// "transitions" into the group transition: "groupTransition"
		foldedInto.groupTransitionsHelper(
				transitions, groupTransition);
	}
}
