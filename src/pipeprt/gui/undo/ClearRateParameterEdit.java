/*
 * ClearRateParameterEdit.java
 */

package pipeprt.gui.undo;

import pipeprt.dataLayer.PipeTransition;
import pipeprt.dataLayer.RateParameter;

/**
 *
 * @author corveau
 */
public class ClearRateParameterEdit 
        extends UndoableEdit {
   
   PipeTransition transition;
   RateParameter oldRateParameter;
   
   
   /** Creates a new instance of placeCapacityEdit */
   public ClearRateParameterEdit(PipeTransition _transition, 
                                 RateParameter _oldRateParameter) {
      transition = _transition;
      oldRateParameter = _oldRateParameter;
   }

   
   /** */
   public void undo() {
      transition.setRateParameter(oldRateParameter);      
   }

   
   /** */
   public void redo() {
      transition.clearRateParameter();
   }
   
}
