/*
 * PlaceCapacityEdit.java
 */

package pipeprt.gui.undo;

import pipeprt.dataLayer.PipePlace;

/**
 *
 * @author corveau
 */
public class PlaceCapacityEdit 
        extends UndoableEdit {
   
   PipePlace place;
   Integer newCapacity;
   Integer oldCapacity;
   
   
   /**
    * Creates a new instance of PlaceCapacityEdit
    */
   public PlaceCapacityEdit(PipePlace _place,
                            Integer _oldCapacity, Integer _newCapacity) {
      place = _place;
      oldCapacity = _oldCapacity;      
      newCapacity = _newCapacity;
   }

   
   /** */
   public void undo() {
      place.setCapacity(oldCapacity);
   }
   

   /** */
   public void redo() {
      place.setCapacity(newCapacity);
   }
   
}
