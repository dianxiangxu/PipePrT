/*
 * DeleteArcPathPointEdit.java
 */

package pipeprt.gui.undo;

import pipeprt.dataLayer.Arc;
import pipeprt.dataLayer.ArcPath;
import pipeprt.dataLayer.ArcPathPoint;

/**
 *
 * @author Pere Bonet
 */
public class DeleteArcPathPointEdit
        extends UndoableEdit {
   
   ArcPath arcPath;
   ArcPathPoint point;
   Integer index;

   /** Creates a new instance of placeWeightEdit */
   public DeleteArcPathPointEdit(Arc _arc, ArcPathPoint  _point, Integer _index) {
      arcPath = _arc.getArcPath();
      point = _point;
      index = _index;
   }

   
   /** */
   public void undo() {
      arcPath.insertPoint(index, point);
      arcPath.updateArc();      
   }

   
   /** */
   public void redo() {
      point.delete();
   }
   
}
