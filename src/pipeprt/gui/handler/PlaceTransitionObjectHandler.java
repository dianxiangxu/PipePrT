package pipeprt.gui.handler;

import java.awt.Container;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import java.util.LinkedList;

import pipeprt.dataLayer.Arc;
import pipeprt.dataLayer.ArcFactory;
import pipeprt.dataLayer.BidirectionalArc;
import pipeprt.dataLayer.DataLayerInterface;
import pipeprt.dataLayer.InhibitorArc;
import pipeprt.dataLayer.NormalArc;
import pipeprt.dataLayer.ObjectDeepCopier;
import pipeprt.dataLayer.PipeMarking;
import pipeprt.dataLayer.PipePlace;
import pipeprt.dataLayer.PipeTransition;
import pipeprt.dataLayer.PlaceTransitionObject;
import pipeprt.dataLayer.TokenClass;
import pipeprt.gui.Constants;
import pipeprt.gui.CreateGui;
import pipeprt.gui.GuiView;
import pipeprt.gui.PrTPanel;
import pipeprt.gui.undo.AddPetriNetObjectEdit;
import pipeprt.gui.undo.UndoManager;

/**
 * Class used to implement methods corresponding to mouse events on places.
 *
 * @author Pere Bonet - changed the mousePressed method to only allow the
 * creation of an arc by left-clicking
 * @author Matthew Worthington - modified the handler which was causing the
 * null pointer exceptions and incorrect petri nets xml representation.
 */
public class PlaceTransitionObjectHandler 
        extends PetriNetObjectHandler {
   
   ArcKeyboardEventHandler keyHandler = null;
   
   // constructor passing in all required objects
   public PlaceTransitionObjectHandler(Container contentpane,
           PlaceTransitionObject obj) {
      super(contentpane, obj);
      enablePopup = true;
   }
   
   
   private void createArc(Arc newArc, PlaceTransitionObject currentObject){
	   TokenClass tc = CreateGui.getModel().getActiveTokenClass();
	   PipeMarking m = new PipeMarking(tc, "");
	   LinkedList<PipeMarking> markings= new LinkedList<PipeMarking>();
	   markings.add(m);
	   newArc.setWeight(markings);
	   newArc.setZoom(CreateGui.getView().getZoom());
      contentPane.add(newArc);
      currentObject.addConnectFrom(newArc);
      CreateGui.getView().createArc = (Arc)newArc;
      // addPetriNetObject a handler for shift & esc actions drawing arc
      // this is removed when the arc is finished drawing:
      keyHandler = new ArcKeyboardEventHandler((Arc)newArc);
      newArc.addKeyListener(keyHandler);
      newArc.requestFocusInWindow();
      newArc.setSelectable(false);
   }
   
   
   public void mousePressed(MouseEvent e) {
      super.mousePressed(e);
      // Prevent creating arcs with a right-click or a middle-click
      if (e.getButton() != MouseEvent.BUTTON1) {
         return;
      }
      
      PlaceTransitionObject currentObject = (PlaceTransitionObject)myObject;
      switch (CreateGui.getPrTPanel().getMode()) {
         case Constants.ARC:
            if (e.isControlDown()) {
               // user is holding Ctrl key; switch to fast mode
               if (this.myObject instanceof PipePlace) {
                  CreateGui.getPrTPanel().enterFastMode(Constants.FAST_TRANSITION);
               } else if (this.myObject instanceof PipeTransition) {
                  CreateGui.getPrTPanel().enterFastMode(Constants.FAST_PLACE);
               }
            }
      	 case Constants.BIARC:
         case Constants.INHIBARC:
         case Constants.FAST_PLACE:
         case Constants.FAST_TRANSITION:
            if (CreateGui.getView().createArc == null) {
               if (CreateGui.getPrTPanel().getMode() == Constants.INHIBARC){
            	// An inhibitor arc only starts from a place            	   
                  if (currentObject instanceof PipePlace) {
                     createArc(ArcFactory.createInhibitorArc(currentObject), currentObject);
                  }
               } else 
               if (CreateGui.getPrTPanel().getMode() == Constants.BIARC){
               		createArc(ArcFactory.createBidirectionalArc(currentObject), currentObject);
               } else {
               		createArc(ArcFactory.createNormalArc(currentObject), currentObject);
               }
            }
            break;
         default:
            break;
      }
   }
   
   private boolean isDuplicateBidirectionalArc(BidirectionalArc createBiArc, PlaceTransitionObject currentObject){
       Iterator arcsFrom = createBiArc.getSource().getConnectFromIterator();
       while(arcsFrom.hasNext()) {
    	   Arc someArc = ((Arc)arcsFrom.next());
    	   if (someArc != createBiArc && 
    		  someArc.getTarget() == currentObject &&
              someArc.getSource() == createBiArc.getSource()) {
               createBiArc.delete();
               someArc.getTransition().removeArcCompareObject(createBiArc);
               someArc.getTransition().updateConnected();
    		   return true;
    	   }
       }
       Iterator arcsTo = createBiArc.getSource().getConnectToIterator();
       while(arcsTo.hasNext()) {
    	   Arc someArc = ((Arc)arcsTo.next());
    	   if (someArc != createBiArc && 
    		  someArc.getTarget() == createBiArc.getSource() &&
              someArc.getSource() == currentObject) {
               createBiArc.delete();
               someArc.getTransition().removeArcCompareObject(createBiArc);
               someArc.getTransition().updateConnected();
               return true;
    	   }
       }
       return false;
   }
   
   public void mouseReleased(MouseEvent e) {
      boolean isNewArc = true; // true if we have to add a new arc to the Petri Net
      boolean fastMode = false;
      
      GuiView view = CreateGui.getView();
      DataLayerInterface model = CreateGui.getModel();
      UndoManager undoManager = view.getUndoManager();
      PrTPanel app = CreateGui.getPrTPanel();
      
      super.mouseReleased(e);
      
      PlaceTransitionObject currentObject = (PlaceTransitionObject)myObject;
      
      switch (app.getMode()) {
      	case Constants.BIARC:
          	BidirectionalArc createBiArc = (BidirectionalArc) view.createArc;
          	if (createBiArc != null) {
        	  	if (!currentObject.getClass().equals(
            		 createBiArc.getSource().getClass())) {
            	 	if (!isDuplicateBidirectionalArc(createBiArc, currentObject)){
            			createBiArc.setSelectable(true);
                   		createBiArc.setTarget(currentObject);
                   		currentObject.addConnectTo(createBiArc);
                   		// Evil hack to prevent the arc being added to GuiView twice
                   		contentPane.remove(createBiArc);
                   		model.addArc(createBiArc);
                   		view.addNewPetriNetObject(createBiArc);
                   		undoManager.addNewEdit(new AddPetriNetObjectEdit(createBiArc, view, model));
                	}
                	// arc is drawn, remove handler:
                	createBiArc.removeKeyListener(keyHandler);
                	keyHandler = null;
                	view.createArc = null;
             	}
          	}
          	break;
         case Constants.INHIBARC:
            InhibitorArc createInhibitorArc = (InhibitorArc) view.createArc;
            if (createInhibitorArc != null) {
               if (!currentObject.getClass().equals(
                       createInhibitorArc.getSource().getClass())) {
                   // search for pre-existent inhibitor arc
                  Iterator arcsFrom =
                          createInhibitorArc.getSource().getConnectFromIterator();
                  while(arcsFrom.hasNext()) {
                     Arc someArc = ((Arc)arcsFrom.next());
                     if (someArc == createInhibitorArc) {
                        break;
                     } else if (someArc.getTarget() == currentObject &&
                             someArc.getSource() == createInhibitorArc.getSource()) {
                         // user has drawn an inhibitor arc where there is 
                         // a normal/bi/inhibitor arc already
                        isNewArc = false;
                        createInhibitorArc.delete();
                        someArc.getTransition().removeArcCompareObject(createInhibitorArc);
                        someArc.getTransition().updateConnected();
                        break;
                     }
                  }
                  // search for pre-existent bidirectional arc
                  Iterator arcsTo =
                      createInhibitorArc.getSource().getConnectToIterator();
                  while(arcsTo.hasNext()) {
                     Arc someArc = ((Arc)arcsTo.next());
                     if (someArc == createInhibitorArc) {
                        break;
                     } else if (someArc instanceof BidirectionalArc && someArc.getTarget() == createInhibitorArc.getSource() &&
                             someArc.getSource() == currentObject) {
                        isNewArc = false;
                        createInhibitorArc.delete();
                        someArc.getTransition().removeArcCompareObject(createInhibitorArc);
                        someArc.getTransition().updateConnected();
                        break;
                     }
                  }
                  
                  if (isNewArc == true) {
                     createInhibitorArc.setSelectable(true);
                     createInhibitorArc.setTarget(currentObject);
                     currentObject.addConnectTo(createInhibitorArc);
                     // Evil hack to prevent the arc being added to GuiView twice
                     contentPane.remove(createInhibitorArc);
                     model.addArc(createInhibitorArc);
                     view.addNewPetriNetObject(createInhibitorArc);
                     undoManager.addNewEdit(
                             new AddPetriNetObjectEdit(createInhibitorArc,
                             view, model));
                  }
                  
                  // arc is drawn, remove handler:
                  createInhibitorArc.removeKeyListener(keyHandler);
                  keyHandler = null;
                  view.createArc = null;
               }
            }
            break;
            
         case Constants.FAST_TRANSITION:
         case Constants.FAST_PLACE:
            fastMode = true;
         case Constants.ARC:
            Arc createArc = (NormalArc) view.createArc;
            if (createArc != null) {
               if (currentObject != createArc.getSource()) {
                  createArc.setSelectable(true);
                  Iterator arcsFrom = createArc.getSource().getConnectFromIterator();
                  // search for pre-existent arcs from createArc's source to 
                  // createArc's target                  
                  while(arcsFrom.hasNext()) {
                     Arc someArc = ((Arc)arcsFrom.next());
                     if (someArc == createArc) {
                        break;
                     } else if (someArc.getSource() == createArc.getSource() &&
                             someArc.getTarget() == currentObject) {
                        isNewArc = false;
                        createArc.delete();
                        someArc.getTransition().removeArcCompareObject(createArc);
                        someArc.getTransition().updateConnected();
                        break; 
                     }
                  }
                  NormalArc inverse = null;
                  if (isNewArc == true) {
                     createArc.setTarget(currentObject);
                     Iterator arcsFromTarget =
                             createArc.getTarget().getConnectFromIterator();
                     while (arcsFromTarget.hasNext()) {
                        Arc anArc = (Arc)arcsFromTarget.next();
                        if (anArc.getTarget() == createArc.getSource()) {
                           if (anArc instanceof NormalArc) {
                              inverse = (NormalArc)anArc;
                              // inverse arc found
                              if (inverse.hasInverse()){
                                 isNewArc = false;
                                 LinkedList<PipeMarking> weightInverse = (LinkedList<PipeMarking>)ObjectDeepCopier.mediumCopy(inverse.getInverse().getWeight());
                                 for(PipeMarking m:weightInverse){
                              	   m.setCurrentMarking(m.getCurrentMarking()+1);
                                 }
                                 undoManager.addNewEdit( inverse.getInverse().setWeight(weightInverse));
                                 
                                 createArc.delete();
                                 inverse.getTransition().removeArcCompareObject(
                                         createArc);
                                 inverse.getTransition().updateConnected();
                              }
                              break;
                           }
                           else if (anArc instanceof BidirectionalArc){
                               isNewArc = false;
                               createArc.delete();
                               anArc.getTransition().removeArcCompareObject(createArc);
                               anArc.getTransition().updateConnected();
                               break;
                           }
                        }
                     }
                  }
                  
                  if (isNewArc == true) {
                     currentObject.addConnectTo(createArc);
                     
                     // Evil hack to prevent the arc being added to GuiView twice
                     contentPane.remove(createArc);
                     
                     model.addArc((NormalArc)createArc);
                     view.addNewPetriNetObject(createArc);
                     if (!fastMode) {
                        // we are not in fast mode so we have to set a new edit
                        // in undoManager for adding the new arc
                        undoManager.newEdit(); // new "transaction""
                     }
                     undoManager.addEdit(
                             new AddPetriNetObjectEdit(createArc, view, model));
                     if (inverse != null) {
                        undoManager.addEdit(
                                inverse.setInverse((NormalArc)createArc,
                                Constants.JOIN_ARCS));
                     }
                  }
                  
                  // arc is drawn, remove handler:
                  createArc.removeKeyListener(keyHandler);
                  keyHandler = null;
                  /**/
                  if (isNewArc == false){
                     view.remove(createArc);
                  }
                  /* */
                  view.createArc = null;
               }
            }
            
            if (app.getMode() == Constants.FAST_PLACE ||
                    app.getMode() == Constants.FAST_TRANSITION) {
               if (view.newPNO == true) {
                  // a new PNO has been created 
                  view.newPNO = false;

                  if (currentObject instanceof PipeTransition) {
                     app.setMode(Constants.FAST_PLACE);
                  } else if (currentObject instanceof PipePlace) {
                     app.setMode(Constants.FAST_TRANSITION);
                  }
               } else {
                  if (view.createArc == null) {
                     // user has clicked on an existent PNO
                     app.resetMode();
                  } else {
                     if (currentObject instanceof PipeTransition) {
                        app.setMode(Constants.FAST_PLACE);
                     } else if (currentObject instanceof PipePlace) {
                        app.setMode(Constants.FAST_TRANSITION);
                     }
                  }
               }
            }
            break;
            
         default:
            break;
      }
   }
   
}
