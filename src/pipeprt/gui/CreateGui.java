// PipePrT - An editor for simplified Predicate/Transition (PrT) nets
// Based on Pipe (Platform Independent Petri net Editor) 3.0: http://pipe2.sourceforge.net/
// Dianxiang Xu, August 2011

package pipeprt.gui;

import java.io.File;
         
import javax.swing.JFrame;

import pipeprt.dataLayer.DataLayer;
import pipeprtlocales.PipePrTLocales;


public class CreateGui {
   
   private static JFrame parentFrame;

   private static PrTPanel prtPanel;		// the working net
   
   public static final String imagePath = "pipeprtimages/";

   public static PrTPanel createPrTPanel(JFrame frame, File file, boolean isEditable){
	      PipePrTLocales.setResourceBundle();
	      prtPanel = new PrTPanel(file, isEditable);
	      Grid.enableGrid();
	      parentFrame = frame;
	      return prtPanel;
   }
   
   public static PrTPanel getPrTPanel() { 
      return prtPanel;
   }

   // change the working net 
   public static void setPrTPanel(PrTPanel newPrtPanel) { 
	     prtPanel=newPrtPanel;
   }

   public static JFrame getParentFrame() {  
	      return parentFrame;
	   }
	 
   public static DataLayer getModel() {
      return prtPanel!=null? prtPanel.getModel(): null;
   }

   public static GuiView getView() {
	  return prtPanel!=null? prtPanel.getView(): null;
   }

   public static File getFile() {
      return prtPanel!=null? prtPanel.getFile(): null;
   }
   
   public static DataLayer currentPNMLData() {
       return prtPanel!=null? prtPanel.getModel(): null;
   }
      
}
