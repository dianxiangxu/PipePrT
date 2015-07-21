package pipeprt.gui.handler;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import pipeprt.dataLayer.PipeTransition;
import pipeprt.gui.Constants;
import pipeprt.gui.CreateGui;
import pipeprt.gui.ZoomController;
import pipeprt.gui.action.ShowHideInfoAction;
import pipeprt.gui.undo.UndoableEdit;
import pipeprtlocales.PipePrTLocales;

/**
 * Class used to implement methods corresponding to mouse events on transitions.
 */
public class TransitionHandler 
        extends PlaceTransitionObjectHandler {
        //implements java.awt.event.MouseWheelListener {  //NOU-PERE
  
   
   public TransitionHandler(Container contentpane, PipeTransition obj) {
      super(contentpane, obj);
   }

   
   public void mouseWheelMoved (MouseWheelEvent e) {

      if (CreateGui.getPrTPanel().isEditionAllowed() == false || 
              e.isControlDown()) {
         return;
      }
      
      if (e.isShiftDown()) {
         CreateGui.getView().getUndoManager().addNewEdit(
                 ((PipeTransition)myObject).setTimed(
                 !((PipeTransition)myObject).isTimed()));
      } else {
         int rotation = 0;
         if (e.getWheelRotation() < 0) {
            rotation = -e.getWheelRotation() * 135;
         } else {
            rotation = e.getWheelRotation() * 45;
         }
         CreateGui.getView().getUndoManager().addNewEdit(
                 ((PipeTransition)myObject).rotate(rotation));
      }
   }   
   
   
   /** 
    * Creates the popup menu that the user will see when they right click on a 
    * component 
    */
   public JPopupMenu getPopup(MouseEvent e) {
      int index = 0;
      JPopupMenu popup = super.getPopup(e);
      
      JMenuItem menuItem = new JMenuItem(PipePrTLocales.bundleString("Edit Transition"));      
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
            ((PipeTransition)myObject).showEditor();
         }
      });       
      popup.insert(menuItem, index++);             
      
      menuItem = new JMenuItem(new ShowHideInfoAction((PipeTransition)myObject));
      if (((PipeTransition)myObject).getAttributesVisible() == true){
         menuItem.setText(PipePrTLocales.bundleString("Hide Attributes"));
      } else {
         menuItem.setText(PipePrTLocales.bundleString("Show Attributes"));
      }
      popup.insert(menuItem, index++);     
      
      popup.insert(new JPopupMenu.Separator(), index);
      menuItem = new JMenuItem(PipePrTLocales.bundleString("Group Transitions"));      
      menuItem.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
        	 UndoableEdit edit = ((PipeTransition)myObject).groupTransitions();
        	 if(edit != null){
        		 CreateGui.getView().getUndoManager().addNewEdit(edit);
        	 }
         }
      });       
//      popup.insert(menuItem, index++);           
            
      return popup;
   }
   
   
   public void mouseClicked(MouseEvent e) {   
      if (SwingUtilities.isLeftMouseButton(e)){    
         if (e.getClickCount() == 2 &&
                 CreateGui.getPrTPanel().isEditionAllowed() && 
                 (CreateGui.getPrTPanel().getMode() == Constants.TRANSITION || 
                 CreateGui.getPrTPanel().getMode() == Constants.IMMTRANS ||
                 CreateGui.getPrTPanel().getMode() == Constants.SELECT)) {
            ((PipeTransition)myObject).showEditor();
         } 
      }  else if (SwingUtilities.isRightMouseButton(e)){
         if (CreateGui.getPrTPanel().isEditionAllowed() && enablePopup) { 
            JPopupMenu m = getPopup(e);
            if (m != null) {           
               int x = ZoomController.getZoomedValue(
                       ((PipeTransition)myObject).getNameOffsetXObject().intValue(),
                       myObject.getZoom());
               int y = ZoomController.getZoomedValue(
                       ((PipeTransition)myObject).getNameOffsetYObject().intValue(),
                       myObject.getZoom());
               m.show(myObject, x, y);
            }
         }
      }
   }
   
}
