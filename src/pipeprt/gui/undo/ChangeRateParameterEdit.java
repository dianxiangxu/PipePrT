/*
 * ChangeRateParameterEdit.java
 */

package pipeprt.gui.undo;

import pipeprt.dataLayer.PipeTransition;
import pipeprt.dataLayer.RateParameter;

/**
 *
 * @author corveau
 */
public class ChangeRateParameterEdit 
        extends UndoableEdit {
   
   PipeTransition transition;
   RateParameter oldRateParameter;
   RateParameter newRateParameter;
   
   
   /** Creates a new instance of placeCapacityEdit */
   public ChangeRateParameterEdit(PipeTransition _transition, 
                                  RateParameter _oldRateParameter,
                                  RateParameter _newRateParameter) {
      transition = _transition;
      oldRateParameter = _oldRateParameter;
      newRateParameter = _newRateParameter;
   }

   
   /** */
   public void undo() {
      transition.changeRateParameter(oldRateParameter);
   }

   
   /** */
   public void redo() {
      transition.changeRateParameter(newRateParameter);
   }
   
}
