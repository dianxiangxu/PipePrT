/*
 * TagArcEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.NormalArc;


/**
 *
 * @author corveau
 */
public class TagArcEdit 
        extends UndoableEdit {
   
   NormalArc arc;
   
   
   /** Creates a new instance of TagArcEdit */
   public TagArcEdit(NormalArc _arc) {
      arc = _arc;
   }

   
   /** */
   public void undo() {
      arc.setTagged(!arc.isTagged());
   }

   
   /** */
   public void redo() {
      arc.setTagged(!arc.isTagged());
   }
   
}
