/*
 * AddArcPathPointEdit.java
 */

package pipeprt.gui.undo;

import pipeprt.dataLayer.Arc;
import pipeprt.dataLayer.ArcPath;
import pipeprt.dataLayer.ArcPathPoint;


/**
 *
 * @author Pere Bonet
 */
public class AddArcPathPointEdit
        extends UndoableEdit {
   
   ArcPath arcPath;
   ArcPathPoint point;
   Integer index;

   /** Creates a new instance of AddArcPathPointEdit */
   public AddArcPathPointEdit(Arc _arc, ArcPathPoint  _point) {
      arcPath = _arc.getArcPath();
      point = _point;
      index = point.getIndex();
   }

   
   /**
    *
    */
   public void undo() {
      point.delete();
   }

   
   /** */
   public void redo() {
      arcPath.insertPoint(index, point);
      arcPath.updateArc();
   }
   
}
