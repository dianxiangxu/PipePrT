package pipeprt.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Observable;
import java.util.ArrayList;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.border.BevelBorder;

import pipeprt.dataLayer.DataLayer;
import pipeprt.dataLayer.DataLayerWriter;
import pipeprt.dataLayer.PNMLTransformer;
import pipeprt.gui.action.GuiAction;
import pipeprt.gui.widgets.FileBrowser;
import pipeprtlocales.PipePrTLocales;

/**
 *
 * This class is based on GuiFrame in Pipe 3.0
 *
 * Dianxiang Xu, August 2011
 */

public class PrTPanel extends JPanel implements Observer{
	
	// for zoom combobox and dropdown
	private final String[] zoomExamples = { "40%", "60%", "80%", "100%",
			"120%", "140%", "160%", "180%", "200%", "300%" };
	
	private DataLayer appModel;
	private GuiView appView;
	private JScrollPane scroller; // JScrollPane for appView
	private File appFile;
	
	private JToolBar paletteToolBar;
	
	private int mode, prev_mode, old_mode; // *** mode WAS STATIC ***
	
	private JComboBox zoomComboBox;

	private FileAction createAction, openAction, closeAction, saveAction,
			saveAsAction, printAction, exportPNGAction,
			exportTNAction, exportPSAction, importAction;

	private EditAction copyAction, cutAction;
	public EditAction pasteAction;
	private EditAction undoAction, redoAction;
	private GridAction toggleGrid;
	private ZoomAction zoomOutAction, zoomInAction, zoomAction;
	private DeleteAction deleteAction;
	private TypeAction annotationAction, arcAction, inhibarcAction,biarcAction,
			placeAction, transAction, transitionAction, 
			selectAction, 
			dragAction;

	public boolean dragging = false;

	private boolean editionAllowed = true;

	static private CopyPasteManager copyPasteManager;

	public PrTPanel(File file, boolean isEditable) {
		initActions();
		if (copyPasteManager==null)
		copyPasteManager = new CopyPasteManager();
		appModel = new DataLayer();
		appModel.getTokenClasses(); //actually initialize tokenClasses; Only the Default token class is used.
		appView = new GuiView(appModel, this);
		appFile = file;
		editionAllowed = isEditable;
		setLayout(new BorderLayout());
		createPaletteToolBar(isEditable);
		createDrawingCanvas(file, false);
		add(scroller, BorderLayout.CENTER);
		setUndoActionEnabled(false);
		setRedoActionEnabled(false);
		pasteAction.setEnabled(getCopyPasteManager().pasteEnabled());
	}

	public DataLayer getModel() {
		return appModel;
	}

	public GuiView getView() {
		return appView;
	}

	public boolean isNetChanged(){
		if (appView!=null)
			return appView.getNetChanged();
		else
			return false;
	}
	
	public File getFile() {
		return appFile;
	}

	public void setFile(File newFile) {
		this.appFile = newFile;
	}
	
	private void initActions() {
		createAction = new FileAction("New", "Create a new Petri net", "ctrl N");
		openAction = new FileAction("Open", "Open", "ctrl O");
		closeAction = new FileAction("Close", "Close the current tab", "ctrl W");

		saveAction = new FileAction("Save", "Save", "ctrl S");
		saveAsAction = new FileAction("Save as", "Save as", "shift ctrl S");

		printAction = new FileAction("Print", "Print net", "ctrl P");

		undoAction = new EditAction("Undo", "Undo", "ctrl Z");
		redoAction = new EditAction("Redo", "Redo", "ctrl Y");

		cutAction = new EditAction("Cut", "Cut", "ctrl X");
		copyAction = new EditAction("Copy", "Copy", "ctrl C");
		//		pasteAction = new EditAction("Paste", "Paste", "ctrl V");
		pasteAction = new EditAction("Paste", "Paste", "V");

		deleteAction = new DeleteAction("Delete", "Delete selection", "DELETE");

		selectAction = new TypeAction("Select", Constants.SELECT, "Select", "S", true);

		placeAction = new TypeAction("Place", Constants.PLACE, "Add a place", "P", true);
		transAction = new TypeAction(
				"Immediate transition", Constants.IMMTRANS,
				"Add an immediate transition", "M", true);
//		timedtransAction = new TypeAction("Timed transition", Constants.TIMEDTRANS,
//				"Add a timed transition", "T", true);
		transitionAction = new TypeAction("transition", Constants.TRANSITION,
				"Add a transition", "T", true);
		arcAction = new TypeAction("Arc", Constants.ARC, "Add an arc", "A", true);
		biarcAction = new TypeAction("BidirectionalArc", Constants.BIARC, "Add an bidirectional arc", "B", true);
		inhibarcAction = new TypeAction("Inhibitor Arc", Constants.INHIBARC, "Add an inhibitor arc from a place", "I", true);
		annotationAction = new TypeAction("Annotation", Constants.ANNOTATION, "Add an annotation", "N", true);


		zoomOutAction = new ZoomAction("Zoom out", "Zoom out by 10% ", "ctrl MINUS");
		zoomInAction = new ZoomAction("Zoom in", "Zoom in by 10% ", "ctrl PLUS");

		toggleGrid = new GridAction("Cycle grid", "Change the grid size", "G");
		dragAction = new TypeAction("Drag", Constants.DRAG, "Drag the drawing", "D", true);
	}
	
	private void createPaletteToolBar(boolean isEditable) {
		// Create the toolbar
		paletteToolBar = new JToolBar();
//paletteToolBar.setOrientation(JToolBar.VERTICAL);
		
		paletteToolBar.setFloatable(false);

		if (isEditable){
//		addButton(paletteToolBar, createAction);
//		addButton(paletteToolBar, openAction);
//		addButton(paletteToolBar, saveAction);
//		addButton(paletteToolBar, saveAsAction);
//		addButton(paletteToolBar, closeAction);

		addButton(paletteToolBar, cutAction);
		addButton(paletteToolBar, copyAction);
		addButton(paletteToolBar, pasteAction);
		addButton(paletteToolBar, deleteAction);
		addButton(paletteToolBar, undoAction);
		addButton(paletteToolBar, redoAction);

		addButton(paletteToolBar, selectAction);

		addButton(paletteToolBar, placeAction);
//		addButton(paletteToolBar, transAction);
		addButton(paletteToolBar, transitionAction);
		addButton(paletteToolBar, arcAction);
		addButton(paletteToolBar, biarcAction);
		addButton(paletteToolBar, inhibarcAction);
		addButton(paletteToolBar, annotationAction);
		}
		
//		addButton(paletteToolBar, zoomOutAction);
		addZoomComboBox(paletteToolBar, zoomAction = new ZoomAction("Zoom",
				"Select zoom percentage", ""));
//		addButton(paletteToolBar, zoomInAction);

		addButton(paletteToolBar, toggleGrid);
//		addButton(paletteToolBar, dragAction);


		addButton(paletteToolBar, printAction);

		for (int i = 0; i < paletteToolBar.getComponentCount(); i++) {
			paletteToolBar.getComponent(i).setFocusable(false);
		}

	}
	
	public JMenu getPrTMenu(){
		JMenu editMenu = new JMenu("Edit");
		editMenu.setMnemonic('E');
		addMenuItem(editMenu, undoAction);
		addMenuItem(editMenu, redoAction);
		editMenu.addSeparator();
		addMenuItem(editMenu, cutAction);
		addMenuItem(editMenu, copyAction);
		addMenuItem(editMenu, pasteAction);
		addMenuItem(editMenu, deleteAction);
		editMenu.addSeparator();
		addMenuItem(editMenu, selectAction);
		addMenuItem(editMenu, placeAction);
		addMenuItem(editMenu, transitionAction);
		addMenuItem(editMenu, arcAction);
		addMenuItem(editMenu, biarcAction);
		addMenuItem(editMenu, inhibarcAction);
		addMenuItem(editMenu, annotationAction);

		pasteAction.setEnabled(getCopyPasteManager().pasteEnabled());
		
		return editMenu;
	}

	private JMenuItem addMenuItem(JMenu menu, Action action) {
		JMenuItem item = menu.add(action);
		KeyStroke keystroke = (KeyStroke) action
				.getValue(Action.ACCELERATOR_KEY);

		if (keystroke != null) {
			item.setAccelerator(keystroke);
		}
		return item;
	}
	
	public JToolBar getPaletteToolBar(){
		return paletteToolBar;
	}
	
	private void addButton(JToolBar toolBar, GuiAction action) {

		if (action.getValue("selected") != null) {
			toolBar.add(new ToggleButton(action));
		} else {
			toolBar.add(action);
		}
	}


	/**
	 * @author Ben Kirby Just takes the long-winded method of setting up the
	 *         ComboBox out of the main buildToolbar method. Could be adapted
	 *         for generic addition of comboboxes
	 * @param toolBar
	 *            the JToolBar to add the button to
	 * @param action
	 *            the action that the ZoomComboBox performs
	 */
	private void addZoomComboBox(JToolBar toolBar, Action action) {
		Dimension zoomComboBoxDimension = new Dimension(65, 28);
		zoomComboBox = new JComboBox(zoomExamples);
		zoomComboBox.setEditable(true);
		zoomComboBox.setSelectedItem("100%");
		zoomComboBox.setMaximumRowCount(zoomExamples.length);
		zoomComboBox.setMaximumSize(zoomComboBoxDimension);
		zoomComboBox.setMinimumSize(zoomComboBoxDimension);
		zoomComboBox.setPreferredSize(zoomComboBoxDimension);
		zoomComboBox.setAction(action);
		toolBar.add(zoomComboBox);
	}

//	private Component c = null; // arreglantzoom
	private Component blankComponent = new BlankLayer(this);

	/* */
/*	void hideNet(boolean doHide) {
		if (doHide) {
			c = appTab.getComponentAt(appTab.getSelectedIndex());
			appTab.setComponentAt(appTab.getSelectedIndex(), p);
		} else {
			if (c != null) {
				appTab.setComponentAt(appTab.getSelectedIndex(), c);
				c = null;
			}
		}
		appTab.repaint();
	}
*/
	void hideNet(boolean doHide) {
//		if (System.getProperty("os.name").contains("Mac")){
			if (doHide) {
				remove(scroller);
				add(blankComponent, BorderLayout.CENTER);
			} else {
				remove(blankComponent);
				add(scroller, BorderLayout.CENTER);
			}
			updateUI();
//		}
	}

	private boolean undoStatus = false;
	private boolean redoStatus = false;
	
	/* sets all buttons to enabled or disabled according to status. */
	public void enableActions(boolean status) {

		saveAction.setEnabled(status);
		saveAsAction.setEnabled(status);

		placeAction.setEnabled(status);
		arcAction.setEnabled(status);
		biarcAction.setEnabled(status);
		inhibarcAction.setEnabled(status);
		annotationAction.setEnabled(status);
		transAction.setEnabled(status);
		transitionAction.setEnabled(status);
		selectAction.setEnabled(status);

		zoomAction.setEnabled(status);
		if (!status) {
			undoStatus = undoAction.isEnabled();
			redoStatus = redoAction.isEnabled();
			pasteAction.setEnabled(status);
			undoAction.setEnabled(status);
			redoAction.setEnabled(status);
		} else {
			pasteAction.setEnabled(getCopyPasteManager().pasteEnabled());
			undoAction.setEnabled(undoStatus);
			redoAction.setEnabled(redoStatus);			
		}
		copyAction.setEnabled(status);
		cutAction.setEnabled(status);
		deleteAction.setEnabled(status);
		
	}

	// HAK Method called by netModel object when it changes
	public void update(Observable o, Object obj) {
//		if ((mode != Constants.CREATING) && (!appView.isInAnimationMode())) {
		if ((mode != Constants.CREATING)) {
			appView.setNetChanged(true);
		}
	}

	public void saveNet() {
		saveNet(appFile);
	}

	private void saveNet(File outFile) {
		try {
			// BK 10/02/07:
			// changed way of saving to accomodate new DataLayerWriter class
			DataLayerWriter saveModel = new DataLayerWriter(appModel);
			saveModel.savePNML(outFile);

			appView.setNetChanged(false);

//			appView.getUndoManager().clear();
//			undoAction.setEnabled(false);
//			redoAction.setEnabled(false);
		} catch (Exception e) {
//			System.err.println(e);
//			e.printStackTrace();
			JOptionPane.showMessageDialog(PrTPanel.this, e.toString(),
					PipePrTLocales.bundleString("Fail to Save File"), JOptionPane.ERROR_MESSAGE);
			return;
		}
	}

	/**
	 * Creates a new tab with the selected file, or a new file if filename==null
	 * 
	 * @param filename
	 *            Filename of net to load, or <b>null</b> to create a new, empty
	 *            tab
	 */
	public void createDrawingCanvas(File file, boolean isTN) {
//		if (copyPasteManager.pasteInProgress()) {
//			copyPasteManager.cancelPaste();
//		}

		appModel.addObserver((Observer) appView); // Add the view as Observer
		appModel.addObserver(this); 

		if (file!=null && file.exists())
			try {
					// ProgressBar pb = new ProgressBar("test");
					PNMLTransformer transformer = new PNMLTransformer();
					appModel.createFromPNML(transformer.transformPNML(file.getPath()));
					appView.scrollRectToVisible(new Rectangle(0, 0, 1, 1));
			} catch (Exception e) {
				JOptionPane.showMessageDialog(PrTPanel.this,
						PipePrTLocales.bundleString("Error loading file")+":\n" + file.getName() + "\n"
								+ e.toString(), PipePrTLocales.bundleString("File load error"),
						JOptionPane.ERROR_MESSAGE);
//				e.printStackTrace();
//				return;
			}

		if (appModel.hasPlaceTransitionObjects()){
				setMode(Constants.SELECT);
				selectAction.actionPerformed(null);
		} else {
			setMode(Constants.PLACE);
			placeAction.actionPerformed(null);
		}
			

		appView.setNetChanged(false); 

		scroller = new JScrollPane(appView);

		scroller.setBorder(new BevelBorder(BevelBorder.LOWERED));

		appView.updatePreferredSize();
		appView.add(new ViewExpansionComponent(appView.getWidth(), appView.getHeight()));
//appModel.print();
//		selectAction.actionPerformed(null);
	}

	public void resetMode() {
		setMode(old_mode);
	}

	public void enterFastMode(int _mode) {
		old_mode = mode;
		setMode(_mode);
	}

	public int getOldMode() { // NOU-PERE
		return old_mode;
	}

	public void setMode(int _mode) {
		// Don't bother unless new mode is different.
		if (mode != _mode) {
			prev_mode = mode;
			mode = _mode;
		}
	}

	public int getMode() {
		return mode;
	}

	public void restoreMode() {
		mode = prev_mode;
		placeAction.setSelected(mode == Constants.PLACE);
		transAction.setSelected(mode == Constants.IMMTRANS);
		transitionAction.setSelected(mode == Constants.TRANSITION);
		arcAction.setSelected(mode == Constants.ARC);
		biarcAction.setSelected(mode == Constants.BIARC);
		inhibarcAction.setSelected(mode == Constants.INHIBARC);
		selectAction.setSelected(mode == Constants.SELECT);
		annotationAction.setSelected(mode == Constants.ANNOTATION);
	}

	public boolean isEditionAllowed() {
		return editionAllowed;
	}

	public void setEditionAllowed(boolean flag) {
		editionAllowed = flag;
	}

	public void setUndoActionEnabled(boolean flag) {
		undoAction.setEnabled(flag);
	}

	public void setRedoActionEnabled(boolean flag) {
		redoAction.setEnabled(flag);
	}

	public CopyPasteManager getCopyPasteManager() {
		return copyPasteManager;
	}

	/**
	 * @author Ben Kirby Remove the listener from the zoomComboBox, so that when
	 *         the box's selected item is updated to keep track of ZoomActions
	 *         called from other sources, a duplicate ZoomAction is not called
	 */
	public void updateZoomCombo() {
		ActionListener zoomComboListener = (zoomComboBox.getActionListeners())[0];
		zoomComboBox.removeActionListener(zoomComboListener);
		zoomComboBox.setSelectedItem(String.valueOf(appView.getZoomController()
				.getPercent())
				+ "%");
		zoomComboBox.addActionListener(zoomComboListener);
	}

	class DeleteAction extends GuiAction {

		DeleteAction(String name, String tooltip, String keystroke) {
			super(name, tooltip, keystroke);
		}

		public void actionPerformed(ActionEvent e) {
			appView.getUndoManager().newEdit(); // new "transaction""
			appView.getUndoManager().deleteSelection(
					appView.getSelectionObject().getSelection());
			appView.getSelectionObject().deleteSelection();
		}

	}

	class TypeAction extends GuiAction {

		private int typeID;

		TypeAction(String name, int typeID, String tooltip, String keystroke) {
			super(name, tooltip, keystroke);
			this.typeID = typeID;
		}

		TypeAction(String name, int typeID, String tooltip, String keystroke,
				boolean toggleable) {
			super(name, tooltip, keystroke, toggleable);
			this.typeID = typeID;
		}

		public void actionPerformed(ActionEvent e) {
			
			// if (!isSelected()){
			this.setSelected(true);

			// deselect other actions
			if (this != placeAction) {
				placeAction.setSelected(false);
			}
			if (this != transAction) {
				transAction.setSelected(false);
			}
			if (this != transitionAction) {
				transitionAction.setSelected(false);
			}
			if (this != arcAction) {
				arcAction.setSelected(false);
			}
			if (this != biarcAction) {
				biarcAction.setSelected(false);
			}
			if (this != inhibarcAction) {
				inhibarcAction.setSelected(false);
			}
			if (this != selectAction) {
				selectAction.setSelected(false);
			}
			if (this != annotationAction) {
				annotationAction.setSelected(false);
			}
			if (this != dragAction) {
				dragAction.setSelected(false);
			}

			if (appView == null) {
				return;
			}

			appView.getSelectionObject().disableSelection();
			// appView.getSelectionObject().clearSelection();

			setMode(typeID);

			if ((typeID != Constants.ARC && typeID != Constants.INHIBARC && typeID != Constants.BIARC) && (appView.createArc != null)) {
				appView.createArc.delete();
				appView.createArc = null;
				appView.repaint();
			}

			if (typeID == Constants.SELECT) {
				// disable drawing to eliminate possiblity of connecting arc to
				// old coord of moved component
				appView.getSelectionObject().enableSelection();
				appView.setCursorType("arrow");
			} else if (typeID == Constants.DRAG) {
				appView.setCursorType("move");
			} else {
				appView.setCursorType("crosshair");
			}
		}
		// }

	}

	class GridAction extends GuiAction {

		GridAction(String name, String tooltip, String keystroke) {
			super(name, tooltip, keystroke);
		}

		public void actionPerformed(ActionEvent e) {
			Grid.increment();
			repaint();
		}

	}


	
	class ZoomAction extends GuiAction {

		ZoomAction(String name, String tooltip, String keystroke) {
			super(name, tooltip, keystroke);
		}

		public void actionPerformed(ActionEvent e) {
			boolean doZoom = false;
			try {
				String actionName = (String) getValue(NAME);
				ZoomController zoomer = appView.getZoomController();
				JViewport thisView = scroller.getViewport();
				String selection = null, strToTest = null;

				double midpointX = ZoomController.getUnzoomedValue(thisView
						.getViewPosition().x
						+ (thisView.getWidth() * 0.5), zoomer.getPercent());
				double midpointY = ZoomController.getUnzoomedValue(thisView
						.getViewPosition().y
						+ (thisView.getHeight() * 0.5), zoomer.getPercent());

				if (actionName.equals("Zoom in")) {
					doZoom = zoomer.zoomIn();
				} else if (actionName.equals("Zoom out")) {
					doZoom = zoomer.zoomOut();
				} else {
					if (actionName.equals("Zoom")) {
						selection = (String) zoomComboBox.getSelectedItem();
					}
					if (e.getSource() instanceof JMenuItem) {
						selection = ((JMenuItem) e.getSource()).getText();
					}
					strToTest = validatePercent(selection);

					if (strToTest != null) {
						// BK: no need to zoom if already at that level
						if (zoomer.getPercent() == Integer.parseInt(strToTest)) {
							return;
						} else {
							zoomer.setZoom(Integer.parseInt(strToTest));
							doZoom = true;
						}
					} else {
						return;
					}
				}
				if (doZoom == true) {
					updateZoomCombo();
					appView.zoomTo(new java.awt.Point((int) midpointX,
							(int) midpointY));
				}
			} catch (ClassCastException cce) {
				// zoom
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		private String validatePercent(String selection) {

			try {
				String toTest = selection;

				if (selection.endsWith("%")) {
					toTest = selection.substring(0, (selection.length()) - 1);
				}

				if (Integer.parseInt(toTest) < Constants.ZOOM_MIN
						|| Integer.parseInt(toTest) > Constants.ZOOM_MAX) {
					throw new Exception();
				} else {
					return toTest;
				}
			} catch (Exception e) {
				zoomComboBox.setSelectedItem("");
				return null;
			}
		}

	}

	class FileAction extends GuiAction {

		// constructor
		FileAction(String name, String tooltip, String keystroke) {
			super(name, tooltip, keystroke);
		}

		public void actionPerformed(ActionEvent e) {
			if (this == saveAction) {
				saveNet(); 
			} else if (this == openAction) { // code for Open operation
				File filePath = new FileBrowser(appFile.getParent()).openFile();
				if ((filePath != null) && filePath.exists()
						&& filePath.isFile() && filePath.canRead()) {
					appFile = filePath;
					createDrawingCanvas(filePath, false);
				}
				if ((filePath != null) && (!filePath.exists())) {
					String message = "File \"" + filePath.getName()
							+ "\" does not exist.";
					JOptionPane.showMessageDialog(null, message, "Warning",
							JOptionPane.WARNING_MESSAGE);
				}
			} else if (this == importAction) {
				File filePath = new FileBrowser(appFile.getParent()).openFile();
				if ((filePath != null) && filePath.exists()
						&& filePath.isFile() && filePath.canRead()) {
					appFile = filePath;
					createDrawingCanvas(filePath, true);
					appView.getSelectionObject().enableSelection();
				}
			} else if (this == createAction) {
				createDrawingCanvas(null, false); 
			} else if (this == exportPNGAction) {
				Export.exportGuiView(appView, Export.PNG, null);
			} else if (this == exportPSAction) {
				Export.exportGuiView(appView, Export.POSTSCRIPT, null);
			} else if (this == printAction) {
				Export.exportGuiView(appView, Export.PRINTER, null);
			}
		}

	}

	public class EditAction extends GuiAction {

		EditAction(String name, String tooltip, String keystroke) {
			super(name, tooltip, keystroke);
		}

		public void actionPerformed(ActionEvent e) {
			if (CreateGui.getPrTPanel().isEditionAllowed()) {
				if (this == cutAction) {
					ArrayList selection = appView.getSelectionObject()
							.getSelection();
					copyPasteManager.doCopy(selection, appView);
					appView.getUndoManager().newEdit(); // new "transaction""
					appView.getUndoManager().deleteSelection(selection);
					appView.getSelectionObject().deleteSelection();
					pasteAction.setEnabled(copyPasteManager.pasteEnabled());
				} else if (this == copyAction) {
					copyPasteManager.doCopy(appView.getSelectionObject().getSelection(),appView);
//System.out.println("#Objects selected: "+appView.getSelectionObject().getSelection().size());
					pasteAction.setEnabled(copyPasteManager.pasteEnabled());
				} else if (this == pasteAction) {
					appView.getSelectionObject().clearSelection();
					copyPasteManager.showPasteRectangle(appView);
				} else if (this == undoAction) {
					appView.getUndoManager().doUndo();
				} else if (this == redoAction) {
					appView.getUndoManager().doRedo();
				}
			}
		}
	}

	class ValidateAction extends GuiAction {

		ValidateAction(String name, String tooltip, String keystroke) {
			super(name, tooltip, keystroke);
		}

		public void actionPerformed(ActionEvent e) {
			// call the function to run validate.....
			appModel.validTagStructure();
		}
	}

	/**
	 * A JToggleButton that watches an Action for selection change
	 * 
	 * @author Maxim
	 * 
	 *         Selection must be stored in the action using
	 *         putValue("selected",Boolean);
	 */
	class ToggleButton extends JToggleButton implements PropertyChangeListener {

		public ToggleButton(Action a) {
			super(a);
			if (a.getValue(Action.SMALL_ICON) != null) {
				// toggle buttons like to have images *and* text, nasty
				setText(null);
			}
			a.addPropertyChangeListener(this);
		}

		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName() == "selected") {
				Boolean b = (Boolean) evt.getNewValue();
				if (b != null) {
					setSelected(b.booleanValue());
				}
			}
		}

	}

}
