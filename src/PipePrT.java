
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import pipeprt.gui.CreateGui;
import pipeprt.gui.PrTPanel;

public class PipePrT extends JFrame {

//	public static File file = new File("Example nets\\blocks.xml");
	public static File file = new File("\\work\\JavaProjects\\ISTA\\examples-cpn\\spoofing1.pnml");

   public PipePrT(){
		super("Pipe PrT");
		PrTPanel prtPanel = CreateGui.createPrTPanel(this,file, true);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(prtPanel.getPaletteToolBar(), BorderLayout.NORTH);
		panel.add(prtPanel, BorderLayout.CENTER);
		setContentPane(panel);
   }
   
	public static void setLookAndFeel() {
		if (System.getProperty("os.name").contains("Windows")) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception e) {
			}
		}
	}

    private static void startGUI() {
    	setLookAndFeel();
		PipePrT window = new PipePrT();
		window.setPreferredSize(new Dimension(800, 500));
		window.pack();
		window.setVisible(true);
   }

   public static void main(String args[]) {
      SwingUtilities.invokeLater(new Runnable() {

         public void run() {
        	 startGUI();
//            CreateGui.createPrTPanel(new Pipe3PrT(), new File(fileName));
         }

      });
   }

}
