package pipeprt.gui.widgets;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.event.CaretListener;

import pipeprt.dataLayer.DataLayerInterface;
import pipeprt.dataLayer.GroupTransition;
import pipeprt.gui.GuiView;


/**
 *
 * @author  Alex Charalambous
 */
public class GroupTransitionEditorPanel
        extends javax.swing.JPanel {
   
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
GroupTransition groupTransition;
   String name;   
   DataLayerInterface pnmlData;
   GuiView view;
   JRootPane rootPane;
   
   
   /**
    * Creates new form PlaceEditor
    */
   public GroupTransitionEditorPanel(JRootPane _rootPane, GroupTransition _transition,
           DataLayerInterface _pnmlData, GuiView _view) {
      groupTransition = _transition;
      pnmlData = _pnmlData;
      view = _view;
      name = groupTransition.getName();
      rootPane = _rootPane;
      
      initComponents();
      
      
      rootPane.setDefaultButton(okButton);



   

      
      
        
   }
   
   
  
   
   /** This method is called from within the constructor to
    * initialize the form.
    * WARNING: Do NOT modify this code. The content of this method is
    * always regenerated by the Form Editor.
    */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        transitionEditorPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();

        rotationLabel = new javax.swing.JLabel();
        rotationComboBox = new javax.swing.JComboBox();

        buttonPanel = new javax.swing.JPanel();
        cancelButton = new javax.swing.JButton();
        okButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        transitionEditorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("GroupTransition Editor"));
        transitionEditorPanel.setLayout(new java.awt.GridBagLayout());

        nameLabel.setText("Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transitionEditorPanel.add(nameLabel, gridBagConstraints);

        nameTextField.setText(groupTransition.getName());
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusLost(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transitionEditorPanel.add(nameTextField, gridBagConstraints);

  


        rotationLabel.setText("Rotation:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transitionEditorPanel.add(rotationLabel, gridBagConstraints);

        rotationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "+45\u00B0", "+90\u00B0", "-45\u00B0" }));
        rotationComboBox.setMaximumSize(new java.awt.Dimension(70, 20));
        rotationComboBox.setMinimumSize(new java.awt.Dimension(70, 20));
        rotationComboBox.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transitionEditorPanel.add(rotationComboBox, gridBagConstraints);


        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(transitionEditorPanel, gridBagConstraints);

        buttonPanel.setLayout(new java.awt.GridBagLayout());

        cancelButton.setText("Cancel");
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

        okButton.setText("OK");
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


   private void nameTextFieldFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusLost
      focusLost(nameTextField);
   }//GEN-LAST:event_nameTextFieldFocusLost

   private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_nameTextFieldFocusGained
      focusGained(nameTextField);
   }//GEN-LAST:event_nameTextFieldFocusGained

   
   
   private void focusGained(javax.swing.JTextField textField){
      textField.setCaretPosition(0);
      textField.moveCaretPosition(textField.getText().length());
   }
   
   private void focusLost(javax.swing.JTextField textField){
      textField.setCaretPosition(0);
   }   
   
   

   
   private void okButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_okButtonKeyPressed
      if (evt.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
         okButtonHandler(new java.awt.event.ActionEvent(this,0,""));
      }
   }//GEN-LAST:event_okButtonKeyPressed


   CaretListener caretListener = new javax.swing.event.CaretListener() {
      public void caretUpdate(javax.swing.event.CaretEvent evt) {
         JTextField textField = (JTextField)evt.getSource();
         textField.setBackground(new Color(255,255,255));
         //textField.removeChangeListener(this);
      }
   };   
   
       
   private void okButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonHandler

      view.getUndoManager().newEdit(); // new "transaction""
       
      String newName = nameTextField.getText();
      if (!newName.equals(name)){
         if (pnmlData.checkTransitionIDAvailability(newName)){
            view.getUndoManager().addEdit(groupTransition.setPNObjectName(newName));
         } else{
            // aquest nom no est� disponible...
            JOptionPane.showMessageDialog(null,
                    "There is already a transition named " + newName, "Error",
                                JOptionPane.WARNING_MESSAGE);
            return;
         }
      }
            
      Integer rotationIndex = rotationComboBox.getSelectedIndex();
      if (rotationIndex > 0) {
         int angle = 0;
         switch (rotationIndex) {
            case 1:
               angle = 45;
               break;
            case 2:
               angle = 90;
               break;
            case 3:
               angle = 135; //-45
               break;
            default:
               break;               
         }
         if (angle != 0) {
            view.getUndoManager().addEdit(groupTransition.rotate(angle));
         }
      }
      groupTransition.repaint();
      exit();
   }//GEN-LAST:event_okButtonHandler

   private void exit() {
      rootPane.getParent().setVisible(false);
   }
   
   
   private void cancelButtonHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonHandler
      //Provisional!
      exit();
   }//GEN-LAST:event_cancelButtonHandler
      
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JButton okButton;
    private javax.swing.JComboBox rotationComboBox;
    private javax.swing.JLabel rotationLabel;
    private javax.swing.JPanel transitionEditorPanel;
    // End of variables declaration//GEN-END:variables
   
}
