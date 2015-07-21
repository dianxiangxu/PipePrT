/*
 * PlaceMarkingEdit.java
 */

package pipeprt.gui.undo;

import java.util.LinkedList;

import pipeprt.dataLayer.PipeMarking;
import pipeprt.dataLayer.PipePlace;
import pipeprt.gui.CreateGui;

/**
 * 
 * @author corveau
 */
public class PlaceMarkingEdit extends UndoableEdit {

	PipePlace place;
	LinkedList<PipeMarking> newMarking;
	LinkedList<PipeMarking> oldMarking;

	public PlaceMarkingEdit(PipePlace _place, LinkedList<PipeMarking> _oldMarking,
			LinkedList<PipeMarking> _newMarking) {
		place = _place;
		oldMarking = _oldMarking;
		newMarking = _newMarking;
	}

	public void undo() {
		// Restore references to tokenClasses so that updates are reflected
		// in marking.
		for (PipeMarking m : oldMarking) {
			m.setTokenClass(CreateGui.getModel().getTokenClassFromID(
					m.getTokenClass().getID()));
		}
		place.setCurrentMarking(oldMarking);
		place.update();
	}

	public void redo() {
		// Restore references to tokenClasses so that updates are reflected
		// in marking.
		for (PipeMarking m : newMarking) {
			m.setTokenClass(CreateGui.getModel().getTokenClassFromID(
					m.getTokenClass().getID()));
		}
		place.setCurrentMarking(newMarking);
		place.update();
	}

}
