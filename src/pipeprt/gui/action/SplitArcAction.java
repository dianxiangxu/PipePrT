/*
 * SplitArcAction.java
 */
package pipeprt.gui.action;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.geom.Point2D;

import pipeprt.dataLayer.Arc;
import pipeprt.dataLayer.ArcPathPoint;
import pipeprt.gui.CreateGui;

/**
 * This class is used to split an arc in two at the
 * point the user clicks the mouse button.
 * @author Pere Bonet (after Nadeem)
 */
public class SplitArcAction 
        extends javax.swing.AbstractAction{
   
   private Arc selected;
   Point2D.Float mouseposition;
   
   
   public SplitArcAction(Arc arc, Point mousepos) {
      selected = arc;
      
      // Mousepos is relative to selected component i.e. the arc
      // Need to convert this into actual coordinates
      Point2D.Float offset = new Point2D.Float(selected.getX(), 
                                               selected.getY());
      mouseposition = new Point2D.Float(mousepos.x + offset.x, 
                                        mousepos.y + offset.y);
   }
   
   
   public void actionPerformed(ActionEvent arg0) {
      CreateGui.getView().getUndoManager().addNewEdit(
               selected.getArcPath().insertPointAt(mouseposition, ArcPathPoint.CURVED));
   }
   
}
