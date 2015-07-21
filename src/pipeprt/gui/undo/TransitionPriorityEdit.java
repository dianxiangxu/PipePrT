/*
 * TransitionPriorityEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.PipeTransition;


/**
 *
 * @author corveau
 */
public class TransitionPriorityEdit 
        extends UndoableEdit {
   
   PipeTransition transition;
   Integer newPriority;
   Integer oldPriority;
   
   
   /** Creates a new instance of placePriorityEdit */
   public TransitionPriorityEdit(
           PipeTransition _transition, Integer _oldPriority, Integer _newPriority) {
      transition = _transition;
      oldPriority = _oldPriority;      
      newPriority = _newPriority;
   }

   
   /** */
   public void undo() {
      transition.setPriority(oldPriority);
   }

   
   /** */
   public void redo() {
      transition.setPriority(newPriority);
   }
   
}
