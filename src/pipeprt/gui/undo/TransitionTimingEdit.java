/*
 * TransitionTimingEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.PipeTransition;


/**
 *
 * @author corveau
 */
public class TransitionTimingEdit
        extends UndoableEdit {
   
   PipeTransition transition;
   
   
   /** Creates a new instance of placeRateEdit */
   public TransitionTimingEdit(PipeTransition _transition) {
      transition = _transition;
   }

   
   /** */
   public void undo() {
      transition.setTimed(!transition.isTimed());
   }

   
   /** */
   public void redo() {
      transition.setTimed(!transition.isTimed());
   }
   
}
