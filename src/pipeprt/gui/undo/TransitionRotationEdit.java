/*
 * transitionPriorityEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.PipeTransition;


/**
 *
 * @author corveau
 */
public class TransitionRotationEdit 
        extends UndoableEdit {
   
   PipeTransition transition;
   Integer angle;
   
   
   /** Creates a new instance of placePriorityEdit */
   public TransitionRotationEdit(PipeTransition _transition, Integer _angle) {
      transition = _transition;
      angle = _angle;
   }

   
   /** */
   public void undo() {
      transition.rotate(-angle);
   }

   
   /** */
   public void redo() {
      transition.rotate(angle);
   }
   
}
