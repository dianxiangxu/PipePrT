/*
 * SetRateParameterEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.PipeTransition;
import pipeprt.dataLayer.RateParameter;


/**
 *
 * @author corveau
 */
public class SetRateParameterEdit 
        extends UndoableEdit {
   
   PipeTransition transition;
   Double oldRate;
   RateParameter newRateParameter;
   
   
   /** Creates a new instance of placeCapacityEdit */
   public SetRateParameterEdit(PipeTransition _transition, 
                               Double _oldRate, 
                               RateParameter _newRateParameter) {
      transition = _transition;
      oldRate = _oldRate;
      newRateParameter = _newRateParameter;
   }

   
   /** */
   public void undo() {
      transition.clearRateParameter();
      transition.setRate(oldRate);
   }

   
   /** */
   public void redo() {
      transition.setRateParameter(newRateParameter);
   }
   
}
