package pipeprt.gui.handler;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;


import pipeprt.dataLayer.GroupTransition;
import pipeprt.gui.Constants;
import pipeprt.gui.CreateGui;
import pipeprt.gui.ZoomController;
import pipeprt.gui.undo.UndoableEdit;

/**
 * Class used to implement methods corresponding to mouse events on transitions.
 */
public class GroupTransitionHandler 
        extends PlaceTransitionObjectHandler {
        //implements java.awt.event.MouseWheelListener {  //NOU-PERE
  
   
   public GroupTransitionHandler(Container contentpane, GroupTransition obj) {
      super(contentpane, obj);
   }

   
   public void mouseWheelMoved (MouseWheelEvent e) {

      if (CreateGui.getPrTPanel().isEditionAllowed() == false || 
              e.isControlDown()) {
         return;
      }
      
         int rotation = 0;
         if (e.getWheelRotation() < 0) {
            rotation = -e.getWheelRotation() * 135;
         } else {
            rotation = e.getWheelRotation() * 45;
         }
         CreateGui.getView().getUndoManager().addNewEdit(
                 ((GroupTransition)myObject).rotate(rotation));
      
   }   
   
   
   /** 
    * Creates the popup menu that the user will see when they right click on a 
    * component 
    */
   public JPopupMenu getPopup(MouseEvent e) {
      int index = 0;
      JPopupMenu popup = super.getPopup(e);
      
      JMenuItem menuItem = new JMenuItem("Edit Transition");      
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
            ((GroupTransition)myObject).showEditor();
         }
      });       
      popup.insert(menuItem, index++);             
           
      popup.insert(new JPopupMenu.Separator(), index);
      menuItem = new JMenuItem("Ungroup Transitions");      
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
        	UndoableEdit edit = ((GroupTransition)myObject).ungroupTransitions();
        	CreateGui.getView().getUndoManager().addNewEdit(edit);
/*    		DataLayer model = CreateGui.getModel();
    		model.removePetriNetObject(((GroupTransition)myObject));
    		GuiView view = CreateGui.getView();
    		view.remove(((GroupTransition)myObject));*/
         }
      });       
      popup.insert(menuItem, index++);           
            
      return popup;
   }
   
   
   public void mouseClicked(MouseEvent e) {   
      if (SwingUtilities.isLeftMouseButton(e)){    
         if (e.getClickCount() == 2 &&
                 CreateGui.getPrTPanel().isEditionAllowed() && 
                 (CreateGui.getPrTPanel().getMode() == Constants.TRANSITION || 
                 CreateGui.getPrTPanel().getMode() == Constants.IMMTRANS ||
                 CreateGui.getPrTPanel().getMode() == Constants.SELECT)) {
            ((GroupTransition)myObject).showEditor();
         } 
      }  else if (SwingUtilities.isRightMouseButton(e)){
         if (CreateGui.getPrTPanel().isEditionAllowed() && enablePopup) { 
            JPopupMenu m = getPopup(e);
            if (m != null) {           
               int x = ZoomController.getZoomedValue(
                       ((GroupTransition)myObject).getNameOffsetXObject().intValue(),
                       myObject.getZoom());
               int y = ZoomController.getZoomedValue(
                       ((GroupTransition)myObject).getNameOffsetYObject().intValue(),
                       myObject.getZoom());
               m.show(myObject, x, y);
            }
         }
      }
   }
   
}
