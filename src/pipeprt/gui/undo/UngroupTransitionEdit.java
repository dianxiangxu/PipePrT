package pipeprt.gui.undo;

import java.util.ArrayList;

import pipeprt.dataLayer.GroupTransition;
import pipeprt.dataLayer.PipeTransition;

/**
 * 
 * @author Alex Charalambous
 */
public class UngroupTransitionEdit extends UndoableEdit {

	GroupTransition groupTransition;

	public UngroupTransitionEdit(GroupTransition _groupTransition) {
		groupTransition = _groupTransition;
	}

	/** */
	public void undo() {
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

	/** */
	public void redo() {
		groupTransition.ungroupTransitionsHelper();
	}
}
