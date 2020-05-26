/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lab2server;

import java.util.List;
import javax.swing.SwingWorker;
import lab2.Registration;

public class ServerFrame extends javax.swing.JFrame {
    
    /* show authentication list on textArea */
    class AreaUpdateThread extends Thread {
        
        @Override
        public void run() {
            int current = 0;
            while (true) {
                if (current < Server.getUsers().size()) {
                    List<Registration> users = Server.getUsers().subList(current, Server.getUsers().size());
                    
                    users.forEach(
                        (user) -> { 
                            textArea.append(user.getUser() + " " +
                                    user.getDate().toString() +
                                    System.lineSeparator());
                                    });
                    current = Server.getUsers().size();
                }
            }
        }
    }
    
    /* alternative */
    /* show authentication list on textArea */
    class UpdateArea extends SwingWorker<Object, String> {
        
        @Override
        protected Object doInBackground() throws Exception {
            int current = 0;
            
            while (!isCancelled()){
                if (current >= Server.getUsers().size())
                    continue;
                
                StringBuilder s = new StringBuilder();
                List<Registration> users = Server.getUsers();
                
                users.forEach(
                        (user) -> { 
                            s.append(user.getUser() + " " +
                                    user.getDate().toString() +
                                    System.lineSeparator());
                                    });

                publish(s.toString());
            }
            
            return null;
        }
        
        @Override
        protected void process(List<String> chunks) {
            textArea.setText(null);
            chunks.forEach((chunk) -> {textArea.append(chunk);});
        }
        
    }
    
    /**
     * Creates new form ServerFrame
     */
    public ServerFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Server");
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        textArea.setEditable(false);
        textArea.setBackground(java.awt.SystemColor.info);
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane2.setViewportView(textArea);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 362, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        new Server().start();
        new AreaUpdateThread().start();
    }//GEN-LAST:event_formWindowOpened

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ServerFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ServerFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}