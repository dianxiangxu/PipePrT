package pipeprt.gui.handler;

import java.awt.Container;
import java.awt.Point;
import java.awt.event.MouseEvent;
//import java.awt.event.MouseWheelEvent; eliminat NOU-PERE

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import pipeprt.dataLayer.ObjectDeepCopier;
import pipeprt.dataLayer.PetriNetObject;
import pipeprt.gui.Constants;
import pipeprt.gui.CreateGui;
import pipeprt.gui.Grid;
import pipeprt.gui.GuiView;
import pipeprt.gui.action.DeletePetriNetObjectAction;
import pipeprtlocales.PipePrTLocales;


/**
 * Class used to implement methods corresponding to mouse events on all 
 * PetriNetObjects.
 * @author unknown
 */
public class PetriNetObjectHandler 
        extends javax.swing.event.MouseInputAdapter { //NOU-PERE
        //implements java.awt.event.MouseWheelListener {
   
   protected Container contentPane;
   protected PetriNetObject myObject = null;
   
   // justSelected: set to true on press, and false on release;
   protected static boolean justSelected = false;	
   
   protected boolean isDragging = false;
   protected boolean enablePopup = false;
   protected Point dragInit = new Point();
   
   private int totalX = 0;
   private int totalY = 0;
   
   // constructor passing in all required objects
   public PetriNetObjectHandler(Container contentpane, PetriNetObject obj) {
      contentPane = contentpane;
      myObject = obj;
   }
   
   
   /** 
    * Creates the popup menu that the user will see when they right click on a 
    * component 
    */
   public JPopupMenu getPopup(MouseEvent e) {
      JPopupMenu popup = new JPopupMenu();
      JMenuItem menuItem = 
              new JMenuItem(new DeletePetriNetObjectAction(myObject));
      menuItem.setText(PipePrTLocales.bundleString("Delete"));
      popup.add(menuItem);      
      return popup;
   }
   
   
   /** 
    * Displays the popup menu 
    */
   private void checkForPopup(MouseEvent e) {
      if (SwingUtilities.isRightMouseButton(e)){
         JPopupMenu m = getPopup(e);
         if (m != null) {
            m.show(myObject, e.getX(), e.getY());
         }
      }
   }
   
   
   public void mousePressed(MouseEvent e) {
      
      if (CreateGui.getPrTPanel().isEditionAllowed() && enablePopup) { 
         checkForPopup(e);
      }
      
      if (!SwingUtilities.isLeftMouseButton(e)){ 
         return;
      }
      
      if (CreateGui.getPrTPanel().getMode() == Constants.SELECT) {
         if (!myObject.isSelected()) {
            if (!e.isShiftDown()) {
               ((GuiView)contentPane).getSelectionObject().clearSelection();
            }
            myObject.select();
            justSelected = true;
         }
         dragInit = e.getPoint();
      }
   }

   
   /** 
    * Event handler for when the user releases the mouse, used in conjunction 
    * with mouseDragged and mouseReleased to implement the moving action 
    */
   public void mouseReleased(MouseEvent e) {
      // Have to check for popup here as well as on pressed for crossplatform!!
      if (CreateGui.getPrTPanel().isEditionAllowed() && enablePopup){ 
         checkForPopup(e);
      }
      
      if (!SwingUtilities.isLeftMouseButton(e)){ 
         return;
      }
      
      if (CreateGui.getPrTPanel().getMode() == Constants.SELECT) {
         if (isDragging) {
            isDragging = false;
            CreateGui.getView().getUndoManager().translateSelection(
                        ((GuiView)contentPane).getSelectionObject().getSelection(),
                        totalX,
                        totalY);
            totalX = 0;
            totalY = 0;
         } else {
            if (!justSelected) {
               if (e.isShiftDown()) {
                  myObject.deselect();
               } else {
                  ((GuiView)contentPane).getSelectionObject().clearSelection();
                  myObject.select();
               }
            }
         }
      }
      justSelected = false;
   }
   
   
   /** 
    * Handler for dragging PlaceTransitionObjects around 
    */
   public void mouseDragged(MouseEvent e) {
     
      if (!SwingUtilities.isLeftMouseButton(e)){ 
         return;
      }
      
      if (CreateGui.getPrTPanel().getMode() == Constants.SELECT) {
         if (myObject.isDraggable()) {
            if (!isDragging) {
               isDragging = true;
            }
         }

         // Calculate translation in mouse
         int transX = Grid.getModifiedX(e.getX() - dragInit.x);
         int transY = Grid.getModifiedY(e.getY() - dragInit.y);
         totalX += transX;
         totalY += transY;
         ((GuiView)contentPane).getSelectionObject().translateSelection(
                 transX, transY);
      }
   }
   
   //NOU-PERE: eliminat mouseWheelMoved   
}
