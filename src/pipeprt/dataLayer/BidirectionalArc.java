package pipeprt.dataLayer;

import java.awt.BasicStroke;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

import pipeprt.gui.Constants;
import pipeprt.gui.ZoomController;
import pipeprt.gui.undo.ArcWeightEdit;
import pipeprt.gui.undo.UndoableEdit;


public class BidirectionalArc 
        extends Arc {
   
 	private static final long serialVersionUID = 1L;
 	
 	public final static String type = "bidirectional";   
   
    BidirectionalArc(double startPositionXInput, double startPositionYInput, double endPositionXInput, double endPositionYInput, 
                       PlaceTransitionObject sourceInput, 
                       PlaceTransitionObject targetInput,
                       LinkedList<PipeMarking> weightInput, 
                       String idInput) {
      super(startPositionXInput, startPositionYInput,
            endPositionXInput, endPositionYInput,
            sourceInput,
            targetInput,
            weightInput,
            idInput);
   }
   
   
   /**
    * Create Petri-Net Arc object
    */
   BidirectionalArc(PlaceTransitionObject newSource) {
      super(newSource);
   }
   
   
   
   BidirectionalArc(BidirectionalArc arc) {
	   weightLabel = new LinkedList<NameLabel>();
	   for(int i = 0; i < 100; i++){
			weightLabel.add(new NameLabel(zoom));
	   }
      
      for (int i = 0; i <= arc.myPath.getEndIndex(); i++){
         this.myPath.addPoint(arc.myPath.getPoint(i).getX(),
                              arc.myPath.getPoint(i).getY(),
                              arc.myPath.getPointType(i));         
      }      
      this.myPath.createPath();
      this.updateBounds();  
      this.id = arc.id;
      this.setSource(arc.getSource());
      this.setTarget(arc.getTarget());
      this.setWeight((LinkedList<PipeMarking>)ObjectDeepCopier.mediumCopy(arc.getWeight()));
   }
   
   
   public BidirectionalArc paste(double despX, double despY, boolean toAnotherView, DataLayerInterface model){
      PlaceTransitionObject source = this.getSource().getLastCopy();
      PlaceTransitionObject target = this.getTarget().getLastCopy();
      
      if (source == null && target == null) {
         // don't paste an arc with neither source nor target
         return null;
      }
      
      if (source == null){
         if (toAnotherView) {
            // if the source belongs to another Petri Net, the arc can't be 
            // pasted
            return null;
         } else {
            source = this.getSource();
         }
      }
      
      if (target == null){
         if (toAnotherView) {
            // if the target belongs to another Petri Net, the arc can't be 
            // pasted
            return null;
         } else {
            target = this.getTarget();
         }
      }

      BidirectionalArc copy =
              ArcFactory.createBidirectionalArc(0, 0, 0, 0, source, target, this.getWeight(),
			source.getId() + " to " + target.getId());      

      copy.myPath.delete();
      for (int i = 0; i <= this.myPath.getEndIndex(); i++){
         copy.myPath.addPoint(this.myPath.getPoint(i).getX() + despX,
                              this.myPath.getPoint(i).getY() + despY,
                              this.myPath.getPointType(i));         
         //copy.myPath.selectPoint(i);
      }

      source.addConnectFrom(copy);
      target.addConnectTo(copy);
      return copy;
   }
   
   
   public BidirectionalArc copy(){
      return ArcFactory.createBidirectionalArc(this);
   }
    
   
   public String getType(){
      return this.type;
   }   

	public UndoableEdit setWeight(LinkedList<PipeMarking> weightInput) {
		removeLabelsFromArc();
		repaint();
		LinkedList<PipeMarking> oldWeight = (LinkedList<PipeMarking>) ObjectDeepCopier
				.mediumCopy(weight);
		weight = weightInput;

		// Now set new arc labels
		int size = weightInput.size();
		for (int i = 0; i < size; i++) {
			NameLabel nameLabel = new NameLabel(zoom);
			PipeMarking weight = weightInput.get(i);
			if (!weight.getCurrentMarking().equals("")) {
				nameLabel.setText(
					weight.getCurrentMarking());
			}
			else{
				nameLabel.setText("");
			}
			nameLabel.setColor(
					weightInput.get(i).getTokenClass().getColour());
			nameLabel.updateSize();
			weightLabel.add(nameLabel);
			
			Container parent = getParent();
			if (parent != null) {
					parent.add(nameLabel);
			}
		}

		setWeightLabelPosition();
		updateWeightLabel();
		repaint();
		weight = weightInput;

		return new ArcWeightEdit(this, oldWeight, weight);
	}
	
	public void updateWeightLabel() {

		setWeightLabelPosition();

	}

	public void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D g2 = (Graphics2D)g;   
      
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                          RenderingHints.VALUE_ANTIALIAS_ON);
      
      g2.translate(getComponentDrawOffset() + zoomGrow - myPath.getBounds().getX(),
               getComponentDrawOffset() + zoomGrow - myPath.getBounds().getY());
      
      if (selected && !ignoreSelection){
         g2.setPaint(Constants.SELECTION_LINE_COLOUR);
      } else{
         g2.setPaint(Constants.ELEMENT_LINE_COLOUR);
      }
     
      g2.setStroke(new BasicStroke(0.01f * zoom));
      g2.draw(myPath);
      
      g2.translate(myPath.getPoint(myPath.getEndIndex()).getX(),
               myPath.getPoint(myPath.getEndIndex()).getY());
        
      g2.rotate(myPath.getEndAngle()+Math.PI);
      g2.setColor(java.awt.Color.WHITE);
            
      AffineTransform reset = g2.getTransform();
      g2.transform(ZoomController.getTransform(zoom));   
  
      g2.setTransform(reset);
   }   

}
