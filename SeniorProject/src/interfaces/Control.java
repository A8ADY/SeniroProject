/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import com.company.DBconnection;
import com.company.TrainingSession;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;
import com.illposed.osc.OSCPortOut;

import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Arbiter
 */
public class Control extends javax.swing.JFrame {

    String userId;
    DBconnection connect = null;
    Connection conn = connect.getMysql().getConnection();
    Statement stm = conn.createStatement();
    ResultSet rs = null;
    float left = (float)0;
    float med = (float)0;
    float right = (float)0;
    float forward = (float)0;
    float backward = (float)0;


    public Control(String id) throws SQLException {
        this.userId = id;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jButton1.setText("Train");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });



        jButton2.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jButton2.setText("Control");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(143, 143, 143)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(75, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        TrainingInterface tr = new TrainingInterface(userId);
        tr.setVisible(true);

    }

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

        Object val[] = new Object[3];
        String sql = "Select * From cogval where id ='"+userId+"'";
        try {
            rs = stm.executeQuery(sql);
            while (rs.next()) {
                med = rs.getFloat("meditation");
                left = rs.getFloat("mLeft");
                right = rs.getFloat("mRight");
                forward = rs.getFloat("forward");
                backward = rs.getFloat("backward");
            }
            stm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            OSCPortIn receiver = new OSCPortIn(7400);
            OSCListener listener = new OSCListener() {
                @Override
                public void acceptMessage(Date date, OSCMessage message) {
                    Object [] args = message.getArguments();
                    String myMessage = args[0].toString();
                    float value = Float.parseFloat(myMessage);
                    if (message.getAddress().contains("/COG/LIFT") && value >= left) {
                        System.out.println("message received");
                        System.out.println("Message: "+myMessage);
                        val[0] = left;
                        messageSender("/COG/LIFT",val);

                    } else if (message.getAddress().contains("/COG/RIGHT") && value >= right) {
                        System.out.println("message received");
                        System.out.println("Message: "+myMessage);
                        val[0] = right;
                        messageSender("/COG/RIGHT",val);
                    } else if (message.getAddress().contains("/COG/PUSH") && value >= forward) {
                        System.out.println("message received");
                        System.out.println("Message: "+myMessage);
                        val[0] = forward;
                        messageSender("/COG/PUSH",val);
                    } else if (message.getAddress().contains("/COG/PULL") && value >= backward) {
                        System.out.println("message received");
                        System.out.println("Message: "+myMessage);
                        val[0] = backward;
                        messageSender("/COG/PULL",val);
                    }

                }
            };

            receiver.addListener("/COG/RIGHT", listener);
            receiver.addListener("/COG/LEFT", listener);
            receiver.addListener("/COG/PUSH", listener);
            receiver.addListener("/COG/PULL", listener);
            receiver.addListener("/AFF/Meditation", listener);
            receiver.startListening();
        } catch (SocketException e) {
            e.printStackTrace();
        }

    }

    public void messageSender(String address, Object[] val) {

        try {
            OSCPortOut sender = new OSCPortOut();
            OSCMessage msg = new OSCMessage(address, val);
            sender.send(msg);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    // End of variables declaration//GEN-END:variables
}
