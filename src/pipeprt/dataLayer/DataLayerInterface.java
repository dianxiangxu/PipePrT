package pipeprt.dataLayer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import org.w3c.dom.Document;

public interface DataLayerInterface {

	/**
	 * Method to clone a DataLayer obejct
	 */
	public abstract DataLayerInterface clone();

	public abstract void setTokenClasses(LinkedList<TokenClass> tokenclasses);

	public abstract LinkedList<TokenClass> getTokenClasses();

	public abstract TokenClass getActiveTokenClass();

	public abstract void setActiveTokenClass(TokenClass tc);

	public abstract void lockTokenClass(String id);

	public abstract void unlockTokenClass(String id);

	public abstract int getPosInList(String tokenClassID,
			LinkedList<PipeMarking> markings);

	public abstract TokenClass getTokenClassFromID(String id);

	/**
	 * Add arcInput to back of the Arc ArrayList All observers are notified of
	 * this change (Model-View Architecture)
	 * 
	 * @param arcInput
	 *            Arc Object to add
	 */
	public abstract void addArc(NormalArc arcInput);

	public abstract void addArc(BidirectionalArc arcInput);

	/**
	 * Add inhibitorArcInput to back of the InhibitorArc ArrayList All observers
	 * are notified of this change (Model-View Architecture)
	 * 
	 * @param arcInput
	 *            Arc Object to add
	 */
	public abstract void addArc(InhibitorArc inhibitorArcInput);

	public abstract void addStateGroup(StateGroup stateGroupInput);

	/**
	 * Add any PetriNetObject - the object will be added to the appropriate
	 * list. If the object passed in isn't a Transition, Place or Arc nothing
	 * will happen. All observers are notified of this change.
	 * 
	 * @param pnObject
	 *            The PetriNetObject to be added.
	 */
	public abstract void addPetriNetObject(PetriNetObject pnObject);

	/**
	 * Removes the specified object from the appropriate ArrayList of objects.
	 * All observers are notified of this change.
	 * 
	 * @param pnObject
	 *            The PetriNetObject to be removed.
	 */
	public abstract void removePetriNetObject(PetriNetObject pnObject);

	/**
	 * This method removes a state group from the arrayList
	 * 
	 * @param SGObject
	 *            The State Group objet to be removed
	 */
	public abstract void removeStateGroup(StateGroup SGObject);

	/**
	 * Checks whether a state group with the same name exists already as the
	 * argument * @param stateName
	 * 
	 * @return
	 */
	public abstract boolean stateGroupExistsAlready(String stateName);

	/**
	 * Returns an iterator for the transitions array. Used by Animator.class to
	 * set all enabled transitions to highlighted
	 */
	public abstract Iterator returnTransitions();

	/**
	 * Returns an iterator of all PetriNetObjects - the order of these cannot be
	 * guaranteed.
	 * 
	 * @return An iterator of all PetriNetObjects
	 */
	public abstract Iterator getPetriNetObjects();

	public abstract boolean hasPlaceTransitionObjects();

	/**
	 * Stores Current Marking
	 */
	public abstract void storeState();

	/**
	 * Restores To previous Stored Marking
	 */
	public abstract void restoreState();

	/**
	 * Get position of Petri-Net Object in ArrayList of given Petri-Net Object's
	 * type
	 * 
	 * @param pnObject
	 *            PlaceTransitionObject to get the position of
	 * @return Position (-1 if not present) of Petri-Net Object in ArrayList of
	 *         given Petri-Net Object's type
	 */
	public abstract int getListPosition(PetriNetObject pnObject);

	/**
	 * Get a List of all the Place objects in the Petri-Net
	 * 
	 * @return A List of all the Place objects
	 */
	public abstract PipePlace[] getPlaces();

	/**
	 * Get an ArrayList of all the Place objects in the Petri-Net
	 * 
	 * @return A ArrayList of all the Place objects
	 */
	public abstract ArrayList<PipePlace> getPlacesArrayList();

	public abstract int getPlacesCount();

	/* wjk added 03/10/2007 */
	/**
	 * Get the current marking of the Petri net
	 * 
	 * @return The current marking of the Petri net
	 */
	public abstract LinkedList<PipeMarking>[] getMarking();

	/**
	 * Get a List of all the net-level NameLabel objects in the Petri-Net
	 * 
	 * @return A List of all the net-level (as opposed to element-specific)
	 *         label objects
	 */
	public abstract AnnotationNote[] getLabels();

	/**
	 * Get a List of all the marking Parameter objects in the Petri-Net
	 * 
	 * @return A List of all the marking Parameter objects
	 */
	public abstract RateParameter[] getRateParameters();

	/**
	 * Get an List of all the Transition objects in the Petri-Net
	 * 
	 * @return An List of all the Transition objects
	 */
	public abstract PipeTransition[] getTransitions();

	/**
	 * Get an ArrayList of all the Transition objects in the Petri-Net
	 * 
	 * @return An List of all the Transition objects
	 */
	public abstract ArrayList<PipeTransition> getTransitionsArrayList();

	public abstract int getTransitionsCount();

	/**
	 * Get an List of all the Arcs objects in the Petri-Net
	 * 
	 * @return An List of all the Arc objects
	 */
	public abstract Arc[] getArcs();

	/**
	 * Get an ArrayList of all the Arcs objects in the Petri-Net
	 * 
	 * @return An List of all the Arc objects
	 */
	public abstract ArrayList<Arc> getArcsArrayList();

	public abstract BidirectionalArc[] getBiArcs();
	public abstract ArrayList<BidirectionalArc> getBiArcsArrayList();

	/**
	 * Get an List of all the InhibitorArc objects in the Petri-Net
	 * 
	 * @return An List of all the InhibitorArc objects
	 */
	public abstract InhibitorArc[] getInhibitors();

	/**
	 * Get an ArrayList of all the InhibitorArc objects in the Petri-Net
	 * 
	 * @return An ArrayList of all the InhibitorArc objects
	 */
	public abstract ArrayList<InhibitorArc> getInhibitorsArrayList();

	/**
	 * Return the Transition called transitionName from the Petri-Net
	 * 
	 * @param transitionID
	 *            ID of Transition object to return
	 * @return The first Transition object found with a name equal to
	 *         transitionName
	 */
	public abstract PipeTransition getTransitionById(String transitionID);

	/**
	 * Return the Transition called transitionName from the Petri-Net
	 * 
	 * @param transitionName
	 *            Name of Transition object to return
	 * @return The first Transition object found with a name equal to
	 *         transitionName
	 */
	public abstract PipeTransition getTransitionByName(String transitionName);

	/**
	 * Return the Transition called transitionName from the Petri-Net
	 * 
	 * @param transitionNo
	 *            No of Transition object to return
	 * @return The Transition object
	 */
	public abstract PipeTransition getTransition(int transitionNo);

	/**
	 * Return the Place called placeName from the Petri-Net
	 * 
	 * @param placeId
	 *            ID of Place object to return
	 * @return The first Place object found with id equal to placeId
	 */
	public abstract PipePlace getPlaceById(String placeID);

	/**
	 * Return the Place called placeName from the Petri-Net
	 * 
	 * @param placeName
	 *            Name of Place object to return
	 * @return The first Place object found with a name equal to placeName
	 */
	public abstract PipePlace getPlaceByName(String placeName);

	/**
	 * Return the Place called placeName from the Petri-Net
	 * 
	 * @param placeNo
	 *            No of Place object to return
	 * @return The Place object
	 */
	public abstract PipePlace getPlace(int placeNo);

	/**
	 * Return the PlaceTransitionObject called ptoName from the Petri-Net
	 * 
	 * @param ptoId
	 *            Id of PlaceTransitionObject object to return
	 * @return The first Arc PlaceTransitionObject found with a name equal to
	 *         ptoName
	 */
	public abstract PlaceTransitionObject getPlaceTransitionObject(String ptoId);

	/**
	 * Return the Initial Marking Vector for the Petri-Net
	 * 
	 * @return The Initial Marking Vector for the Petri-Net
	 */
	public abstract LinkedList<PipeMarking>[] getInitialMarkingVector();

	/**
	 * Return the Initial Marking Vector for the Petri-Net
	 * 
	 * @return The Initial Marking Vector for the Petri-Net
	 */
	public abstract LinkedList<PipeMarking>[] getCurrentMarkingVector();

	/**
	 * Return the capacity Matrix for the Petri-Net
	 * 
	 * @return The capacity Matrix for the Petri-Net
	 */
	public abstract int[] getCapacityVector();

	/**
	 * Return the capacity Matrix for the Petri-Net
	 * 
	 * @return The capacity Matrix for the Petri-Net
	 */
	public abstract int[] getPriorityVector();

	/**
	 * Return the capacity Matrix for the Petri-Net
	 * 
	 * @return The capacity Matrix for the Petri-Net
	 */
	public abstract boolean[] getTimedVector();

	/**
	 * Create model from transformed PNML file
	 * 
	 * @author Ben Kirby, 10 Feb 2007
	 * @param filename
	 *            URI location of PNML
	 * 
	 * @author Edwin Chung This code is modified so that dataLayer objects can
	 *         be created outside the GUI
	 */
	public abstract void createFromPNML(Document PNMLDoc);

	public abstract StateGroup[] getStateGroups();

	public abstract ArrayList<StateGroup> getStateGroupsArray();

	/**
	 * Return a URI for the PNML file for the Petri-Net
	 * 
	 * @return A DOM for the Petri-Net
	 */
	public abstract String getURI();

	/** prints out a brief representation of the dataLayer object */
	public abstract void print();

	public abstract boolean existsRateParameter(String name);

	public abstract boolean changeRateParameter(String oldName, String newName);

	/**
	 * See if the supplied net has any timed transitions.
	 * 
	 * @param DataLayer
	 * @return boolean
	 * @author Matthew
	 */
	public abstract boolean hasTimedTransitions();

	/**
	 * See if the net has any timed transitions.
	 * 
	 * @return boolean
	 * @author Matthew
	 */
	public abstract boolean hasImmediateTransitions();


	public abstract String getTransitionName(int i);

	// Function to check the structure of the Petri Net to ensure that if tagged
	// arcs are included then they obey the restrictions on how they can be used
	// (i.e. a transition may only have one input tagged Arc and one output
	// tagged Arc and if it has one it must have the other).
	public abstract boolean validTagStructure();

	public abstract boolean checkTransitionIDAvailability(String newName);

	public abstract boolean checkPlaceIDAvailability(String newName);

	public abstract int getPlaceIndex(String placeName);

	// Added for passage time analysis of tagged nets
	/*use to check if structure contain any tagged token or tagged arc, then the structure
	 * needs to be validated before animation
	 */
	public abstract boolean hasValidatedStructure();

	public abstract void setValidate(boolean valid);

}