package pipeprt.gui.widgets;

import java.awt.Color;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import pipeprt.dataLayer.Arc;
import pipeprt.dataLayer.BidirectionalArc;
import pipeprt.dataLayer.DataLayerInterface;
import pipeprt.dataLayer.InhibitorArc;
import pipeprt.dataLayer.ObjectDeepCopier;
import pipeprt.dataLayer.PipeMarking;
import pipeprt.gui.CreateGui;
import pipeprt.gui.GuiView;
import pipeprtlocales.PipePrTLocales;

/**
 * 
 * @author Alex Charalambous
 */
public class ArcWeightEditorPanel extends javax.swing.JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Arc arc;
	boolean attributesVisible;
	String name;

	DataLayerInterface pnmlData;
	GuiView view;
	JRootPane rootPane;

	/**
	 * Creates new form Arc Weight Editor
	 */
	public ArcWeightEditorPanel(JRootPane _rootPane, Arc _arc,
			DataLayerInterface _pnmlData, GuiView _view) {
		arc = _arc;
		pnmlData = _pnmlData;
		view = _view;
		name = arc.getName();
		rootPane = _rootPane;

		initComponents();

		rootPane.setDefaultButton(okButton);
	}

	private String getLabelText(){
/*		if (arc instanceof BidirectionalArc)
			return PipePrTLocales.bundleString("Arc label")+ "\n ("+arc.getSource().getName()+" - "+arc.getTarget().getName()+"): ";
		else if (arc instanceof InhibitorArc)
			return PipePrTLocales.bundleString("Inhibitor arc label")+ "\n ("+arc.getSource().getName()+" - "+arc.getTarget().getName()+"): ";
		else
			return PipePrTLocales.bundleString("Arc label")+ "\n ("+arc.getSource().getName()+" -> "+arc.getTarget().getName()+"): ";
*/		if (arc instanceof BidirectionalArc)
			return arc.getSource().getName()+" -- "+arc.getTarget().getName();
		else if (arc instanceof InhibitorArc)
			return arc.getSource().getName()+" -o "+arc.getTarget().getName();
		else
			return arc.getSource().getName()+" \u2192 "+arc.getTarget().getName();
	}
	
	private void initComponents() {

		java.awt.GridBagConstraints gridBagConstraints;

		arcEditorPanel = new javax.swing.JPanel();
		buttonPanel = new javax.swing.JPanel();
		cancelButton = new javax.swing.JButton();
		okButton = new javax.swing.JButton();

//		arcLabel = new javax.swing.JTextField(40);
		arcLabel = new javax.swing.JTextArea(3, 40);
		
		setLayout(new java.awt.GridBagLayout());

		arcEditorPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder(PipePrTLocales.bundleString("Edit Arc Label")));
		arcEditorPanel.setLayout(new java.awt.GridBagLayout());

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		arcEditorPanel.add(new JLabel(getLabelText()), gridBagConstraints);
		
		String activeTokenClassID = CreateGui.getModel().getActiveTokenClassID();
		int pos = CreateGui.getModel().getPosInList(activeTokenClassID, arc.getWeight());
		if (pos >= 0) {
			arcLabel.setText(arc.getWeight().get(pos).getCurrentMarking());
		}
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		arcEditorPanel.add(new JScrollPane(arcLabel), gridBagConstraints);
		
		arcLabel
				.addFocusListener(new java.awt.event.FocusAdapter() {
					public void focusGained(
							java.awt.event.FocusEvent evt) {
						nameTextFieldFocusGained(evt);
					}

					public void focusLost(java.awt.event.FocusEvent evt) {
						nameTextFieldFocusLost(evt);
					}
				});		

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		add(arcEditorPanel, gridBagConstraints);
		buttonPanel.setLayout(new java.awt.GridBagLayout());

		cancelButton.setText(PipePrTLocales.bundleString("Cancel"));
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonHandler(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		buttonPanel.add(cancelButton, gridBagConstraints);

		okButton.setText(PipePrTLocales.bundleString("OK"));
		okButton.setMaximumSize(new java.awt.Dimension(75, 25));
		okButton.setMinimumSize(new java.awt.Dimension(75, 25));
		okButton.setPreferredSize(new java.awt.Dimension(75, 25));
		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okButtonHandler(evt);
			}
		});
		okButton.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyPressed(java.awt.event.KeyEvent evt) {
				okButtonKeyPressed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
		buttonPanel.add(okButton, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 8, 3);
		add(buttonPanel, gridBagConstraints);
	}// </editor-fold>//GEN-END:initComponents

	
	private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_nameTextFieldFocusLost
		// focusLost(nameTextField);
	}// GEN-LAST:event_nameTextFieldFocusLost

	private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_nameTextFieldFocusGained
		// focusGained(nameTextField);
	}// GEN-LAST:event_nameTextFieldFocusGained

	private void okButtonKeyPressed(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_okButtonKeyPressed
		if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
			okButtonHandler(new java.awt.event.ActionEvent(this, 0, ""));
		}
	}// GEN-LAST:event_okButtonKeyPressed

	CaretListener caretListener = new javax.swing.event.CaretListener() {
		public void caretUpdate(javax.swing.event.CaretEvent evt) {
			JTextField textField = (JTextField) evt.getSource();
			textField.setBackground(new Color(255, 255, 255));
			// textField.removeChangeListener(this);
		}
	};

	private void okButtonHandler(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonHandler
		LinkedList<PipeMarking> newWeight;
		newWeight = (LinkedList<PipeMarking>) ObjectDeepCopier.mediumCopy(arc
				.getWeight());
	    view.getUndoManager().newEdit(); // new "transaction""

		String activeTokenClassID = CreateGui.getModel().getActiveTokenClassID();
		int pos = CreateGui.getModel().getPosInList(activeTokenClassID, newWeight);
		PipeMarking m;
		if (pos >= 0) {
			m = newWeight.get(pos);
		} else {
			m = new PipeMarking(CreateGui.getModel().getActiveTokenClass(), "");
			newWeight.add(m);
		}
		String currentMarking = m.getCurrentMarking();
		String newMarking = arcLabel.getText();
		if (!newMarking.equals(currentMarking)) {
				m.setCurrentMarking(newMarking);
		}
		CreateGui.getView().getUndoManager().addEdit(arc.setWeight(newWeight));		
		arc.repaint();
		exit();
	}

	/*
	 * 
	 * view.getUndoManager().newEdit(); // new "transaction"" String newName =
	 * "";//nameTextField.getText(); if (!newName.equals(name)) { if
	 * (pnmlData.checkTransitionIDAvailability(newName)) {
	 * view.getUndoManager().addEdit(arc.setPNObjectName(newName)); } else { //
	 * aquest nom no està disponible... JOptionPane.showMessageDialog(null,
	 * "There is already a transition named " + newName, "Error",
	 * JOptionPane.WARNING_MESSAGE); return; } }
	 */

	// GEN-LAST:event_okButtonHandler

	private void exit() {
		rootPane.getParent().setVisible(false);
	}

	private void cancelButtonHandler(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonHandler
		// Provisional!
		exit();
	}// GEN-LAST:event_cancelButtonHandler

	private javax.swing.JPanel buttonPanel;
	private javax.swing.JButton cancelButton;
	private javax.swing.JButton okButton;
	private javax.swing.JPanel arcEditorPanel;
	
//	private javax.swing.JTextField arcLabel;
	private javax.swing.JTextArea arcLabel;


}
