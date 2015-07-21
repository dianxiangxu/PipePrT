 /*
 * Created on 05-Mar-2004
 * Author is Michael Camacho
 *
 */
package pipeprt.gui.handler;

import java.awt.Container;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import pipeprt.dataLayer.AnnotationNote;
import pipeprt.gui.action.EditAnnotationBackgroundAction;
import pipeprt.gui.action.EditAnnotationBorderAction;
import pipeprt.gui.action.EditNoteAction;
import pipeprtlocales.PipePrTLocales;


public class AnnotationNoteHandler 
        extends NoteHandler {
   

   public AnnotationNoteHandler(Container contentpane, AnnotationNote note) {
      super(contentpane, note);
      enablePopup = true;
   }

   
   /** 
    * Creates the popup menu that the user will see when they right click on a 
    * component */
   public JPopupMenu getPopup(MouseEvent e) {
      int popupIndex = 0;
      JPopupMenu popup = super.getPopup(e);
      
      JMenuItem menuItem =
              new JMenuItem(new EditNoteAction((AnnotationNote)myObject));
      menuItem.setText(PipePrTLocales.bundleString("Edit text"));
      popup.insert(menuItem, popupIndex++);
      
      menuItem = new JMenuItem(
              new EditAnnotationBorderAction((AnnotationNote)myObject));
      if (((AnnotationNote)myObject).isShowingBorder()){
         menuItem.setText(PipePrTLocales.bundleString("Disable Border"));
      } else{
         menuItem.setText(PipePrTLocales.bundleString("Enable Border"));
      }
      popup.insert(menuItem, popupIndex++);
      
      menuItem = new JMenuItem(
              new EditAnnotationBackgroundAction((AnnotationNote)myObject));
      if (((AnnotationNote)myObject).isFilled()) {
         menuItem.setText(PipePrTLocales.bundleString("Transparent"));
      } else {
         menuItem.setText(PipePrTLocales.bundleString("Solid Background"));
      }
      popup.insert(new JPopupMenu.Separator(), popupIndex++);      
      popup.insert(menuItem, popupIndex);

      return popup;
   }

   
   public void mouseClicked(MouseEvent e) {
      if ((e.getComponent() == myObject || !e.getComponent().isEnabled()) && 
              (SwingUtilities.isLeftMouseButton(e))) { 
         if (e.getClickCount() == 2){
            ((AnnotationNote)myObject).enableEditMode();
         }
      }
   }
   
}
