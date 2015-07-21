package pipeprt.gui.handler;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import pipeprt.dataLayer.PipePlace;
import pipeprt.gui.Constants;
import pipeprt.gui.CreateGui;
import pipeprt.gui.ZoomController;
import pipeprt.gui.action.ShowHideInfoAction;
import pipeprtlocales.PipePrTLocales;

/**
 * Class used to implement methods corresponding to mouse events on places.
 */
public class PlaceHandler extends PlaceTransitionObjectHandler {

	public PlaceHandler(Container contentpane, PipePlace obj) {
		super(contentpane, obj);
	}

	/**
	 * Creates the popup menu that the user will see when they right click on a
	 * component
	 */
	public JPopupMenu getPopup(MouseEvent e) {
		int index = 0;
		JPopupMenu popup = super.getPopup(e);

		JMenuItem menuItem = new JMenuItem(PipePrTLocales.bundleString("Edit Place"));
		menuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				((PipePlace) myObject).showEditor();
			}
		});
		popup.insert(menuItem, index++);

		menuItem = new JMenuItem(new ShowHideInfoAction((PipePlace) myObject));
		if (((PipePlace) myObject).getAttributesVisible() == true) {
			menuItem.setText(PipePrTLocales.bundleString("Hide Attributes"));
		} else {
			menuItem.setText(PipePrTLocales.bundleString("Show Attributes"));
		}
		popup.insert(menuItem, index++);
		popup.insert(new JPopupMenu.Separator(), index);

		return popup;
	}

	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)){
			if (e.getClickCount() == 2
					&& CreateGui.getPrTPanel().isEditionAllowed()
					&& (CreateGui.getPrTPanel().getMode() == Constants.PLACE || CreateGui
							.getPrTPanel().getMode() == Constants.SELECT)) {
				((PipePlace) myObject).showEditor();
			} 
		} else if (SwingUtilities.isRightMouseButton(e)) {
			if (CreateGui.getPrTPanel().isEditionAllowed() && enablePopup) {
				JPopupMenu m = getPopup(e);
				if (m != null) {
					int x = ZoomController.getZoomedValue(((PipePlace) myObject)
							.getNameOffsetXObject().intValue(), myObject
							.getZoom());
					int y = ZoomController.getZoomedValue(((PipePlace) myObject)
							.getNameOffsetYObject().intValue(), myObject
							.getZoom());
					m.show(myObject, x, y);
				}
			}
		}/*
		 * else if (SwingUtilities.isMiddleMouseButton(e)){ ; }
		 */
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
	}
}
