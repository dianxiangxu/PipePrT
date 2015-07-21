/*
 * Created on 18-Jul-2005
 */
package pipeprt.gui.action;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import pipeprt.dataLayer.ArcPathPoint;


/**
 * @author Nadeem
 */
public class GetIndexAction 
        extends AbstractAction {
   
   private ArcPathPoint selected;
   private Point mp;
   
   
   public GetIndexAction(ArcPathPoint component, Point mousepos) {
      selected = component;
      mp = mousepos;
   }
   
   
   public void actionPerformed(ActionEvent arg0) {
      System.out.println("Index is: " + selected.getIndex());
      System.out.println("At position: " + selected.getPoint().x + ", " +
                         selected.getPoint().y);
      System.out.println("Mousepos: " + mp.x + ", " + mp.y);
   }
   
}
