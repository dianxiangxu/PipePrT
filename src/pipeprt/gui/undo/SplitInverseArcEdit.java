/*
 * SplitInverseArcEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.NormalArc;


/**
 *
 * @author corveau
 */
public class SplitInverseArcEdit 
        extends UndoableEdit {
   
   NormalArc arc;
   
   
   /** Creates a new instance of placeRateEdit */
   public SplitInverseArcEdit(NormalArc _arc) {
      arc = _arc;
   }

   
   /** */
   public void undo() {
      arc.join();
   }

   
   /** */
   public void redo() {
      arc.split();
   }
   
}
