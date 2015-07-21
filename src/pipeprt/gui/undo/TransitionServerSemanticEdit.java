/*
 * TransitionServerSemanticEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.PipeTransition;


/**
 *
 * @author corveau
 */
public class TransitionServerSemanticEdit
        extends UndoableEdit {
   
   PipeTransition transition;
   
   
   /** Creates a new instance of TransitionServerSemanticEdit */
   public TransitionServerSemanticEdit(PipeTransition _transition) {
      transition = _transition;
   }

   
   /** */
   public void undo() {
      transition.setInfiniteServer(!transition.isInfiniteServer());
   }

   
   /** */
   public void redo() {
      transition.setInfiniteServer(!transition.isInfiniteServer());
   }
   
}
