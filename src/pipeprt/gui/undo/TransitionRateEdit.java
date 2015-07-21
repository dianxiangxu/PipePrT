/*
 * placeRateEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.PipeTransition;


/**
 *
 * @author corveau
 */
public class TransitionRateEdit 
        extends UndoableEdit {
   
   PipeTransition transition;
   Double newRate;
   Double oldRate;
   
   
   /** Creates a new instance of placeRateEdit */
   public TransitionRateEdit(
           PipeTransition _transition, Double _oldRate, Double _newRate) {
      transition = _transition;
      oldRate = _oldRate;      
      newRate = _newRate;
   }

   
   /** */
   public void undo() {
      transition.setRate(oldRate);
   }

   
   /** */
   public void redo() {
      transition.setRate(newRate);
   }
   
}
