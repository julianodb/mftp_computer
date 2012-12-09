/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tcc_gui;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author usuario
 */
public class Refresh extends Thread {

    public IHM ihm;
    final int MAX_SENSORS = 2;

    public void run() {
        while (true) {
            try {
                this.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Refresh.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (ihm.connected) {
                CommReturn ret;
                try {
                    ret = ihm.commController.readRegister(1, CommunicationController.Register.SENSOR1, MAX_SENSORS);
                    String values = "";
                    for (int i = 0; i < MAX_SENSORS; i++) {
                        values += Integer.toString(ret.value[i]) + "\n";
                    }
                    ihm.allSensorsValues.setText(values);
                } catch (Exception ex) {
                    Logger.getLogger(Refresh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
