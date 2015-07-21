/*
 * JoinInverseArcEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.NormalArc;

/**
 *
 * @author corveau
 */
public class JoinInverseArcEdit 
        extends UndoableEdit {
   
   NormalArc arc;
   
   
   /** Creates a new instance of placeRateEdit */
   public JoinInverseArcEdit(NormalArc _arc) {
      arc = _arc;
   }

   
   /** */
   public void undo() {
      arc.split();
   }

   
   /** */
   public void redo() {
      arc.join();
   }
   
}
