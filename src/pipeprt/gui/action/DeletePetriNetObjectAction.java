/*
 * Created on 04-Mar-2004
 * Author is Michael Camacho
 *
 */
package pipeprt.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import pipeprt.dataLayer.PetriNetObject;
import pipeprt.gui.CreateGui;


public class DeletePetriNetObjectAction 
        extends AbstractAction {

   private PetriNetObject selected;

   
   public DeletePetriNetObjectAction(PetriNetObject component) {
      selected = component;
   }

   /* (non-Javadoc)
    * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
    */
   public void actionPerformed(ActionEvent e) {
      CreateGui.getView().getUndoManager().newEdit(); // new "transaction""
      CreateGui.getView().getUndoManager().deleteSelection(selected);      
      selected.delete();
   }

}
