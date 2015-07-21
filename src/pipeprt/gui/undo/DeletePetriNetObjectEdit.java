/*
 * DeletePetriNetObjectEdit.java
 */
package pipeprt.gui.undo;

import pipeprt.dataLayer.DataLayerInterface;
import pipeprt.dataLayer.Parameter;
import pipeprt.dataLayer.PetriNetObject;
import pipeprt.dataLayer.PipeTransition;
import pipeprt.dataLayer.RateParameter;
import pipeprt.gui.GuiView;


/**
 *
 * @author Pere Bonet
 */
public class DeletePetriNetObjectEdit 
        extends UndoableEdit {
   
   PetriNetObject pnObject;
   DataLayerInterface model;
   GuiView view;
   Object[] objects;
   Parameter param;
   
   
   /** Creates a new instance of placeWeightEdit */
   public DeletePetriNetObjectEdit(PetriNetObject _pnObject,
            GuiView _view, DataLayerInterface _model) {
      pnObject = _pnObject;
      view = _view;
      model = _model;

      if (pnObject instanceof RateParameter) {
         objects = ((RateParameter)pnObject).getTransitions();
      } else if (pnObject instanceof PipeTransition) {
         RateParameter rParam = ((PipeTransition)pnObject).getRateParameter();
         if (rParam != null) {
            param = rParam;
         }
      }
      pnObject.markAsDeleted();      
   }

     
   /** */
   public void redo() {
      pnObject.delete();
   }

   
   /** */
   public void undo() {
      pnObject.undelete(model,view);
   }
   
   
   public String toString(){
      return super.toString() + " " + pnObject.getClass().getSimpleName() 
             + " [" +  pnObject.getId() + "]";
   }   
   
}
