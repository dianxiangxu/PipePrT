/*
 * ArcWeightEdit.java
 */

package pipeprt.gui.undo;

import java.util.LinkedList;

import pipeprt.dataLayer.Arc;
import pipeprt.dataLayer.PipeMarking;

/**
 *
 * @author Alex Charalambous
 */
public class ArcWeightEdit 
        extends UndoableEdit {
   
   Arc arc;
   LinkedList<PipeMarking> newWeight;
   LinkedList<PipeMarking> oldWeight;
   
   
   /** Creates a new instance of arcWeightEdit */
   public ArcWeightEdit(Arc _arc, LinkedList<PipeMarking> _oldWeight, LinkedList<PipeMarking> _newWeight) {
      arc = _arc;
      oldWeight = _oldWeight;      
      newWeight = _newWeight;
   }

   
   /** */
   public void undo() {
      arc.setWeight(oldWeight);
   }

   
   /** */
   public void redo() {
      arc.setWeight(newWeight);
   }
   
   
   public String toString(){
      return super.toString() + " " + arc.getName() + 
              "oldWeight: " + oldWeight + "newWeight: " + newWeight;
   }   
   
}
