package tcc_gui;

/**
 *
 * @author juliano
 */
public class Tcc_GUI {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        final IHM ihm = new IHM();

//        ihm.commController = new CommunicationController();
//        ihm.commController.init("COM3");
//        ihm.commController.setDEBUG(true);
        
        
        //CommReturn ret = new CommReturn();

        //ret = communicationController.writeRegister(2, CommunicationController.Register.EMERGENCY_STOP, 1);
        //ret = communicationController.readRegister(1, CommunicationController.Register.EMERGENCY_STOP, 1);
        
          /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                ihm.setVisible(true);
                ihm.refresh.start();
            }
        });

    }
}
