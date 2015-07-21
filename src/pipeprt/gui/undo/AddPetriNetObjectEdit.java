/*
 * AddPetriNetObjectEdit.java
 */

package pipeprt.gui.undo;


import pipeprt.dataLayer.DataLayerInterface;
import pipeprt.dataLayer.PetriNetObject;
import pipeprt.gui.GuiView;

/**
 *
 * @author corveau
 */
public class AddPetriNetObjectEdit 
        extends UndoableEdit {
   
   PetriNetObject pnObject;
   DataLayerInterface model;
   GuiView view;
   
   
   /** Creates a new instance of placeWeightEdit */
   public AddPetriNetObjectEdit(PetriNetObject _pnObject, 
                                GuiView _view, DataLayerInterface _model) {
      pnObject = _pnObject;
      view = _view;
      model = _model;
   }

   
   /** */
   public void undo() {
      pnObject.delete();
   }

   
   /** */
   public void redo() {
      pnObject.undelete(model, view);
   }
   
   
   public String toString(){
      return super.toString() + " \"" + pnObject.getName() + "\"";
   }
   
}
