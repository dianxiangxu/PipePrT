package pipeprt.gui.action;

import java.awt.event.ActionEvent;

import pipeprt.dataLayer.PlaceTransitionObject;


/**
 *
 */
public class ShowHideInfoAction 
        extends javax.swing.AbstractAction {
   
   private PlaceTransitionObject pto;
   
   
   public ShowHideInfoAction(PlaceTransitionObject component) {
      pto = component;      
   }
   
   
   /**  */
   public void actionPerformed(ActionEvent e) {    
      pto.toggleAttributesVisible();
   }
   
}
