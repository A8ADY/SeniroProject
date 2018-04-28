package com.company;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

import java.net.SocketException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Arbiter on 22/04/2018.
 */
public class TrainingSession {


    ArrayList<Float> left = new ArrayList<Float>();
    ArrayList<Float> right = new ArrayList<Float>();
    ArrayList<Float> backward = new ArrayList<Float>();
    ArrayList<Float> forward = new ArrayList<Float>();
    ArrayList<Float> med = new ArrayList<Float>();
    ArrayList<Float> excit = new ArrayList<Float>();
    Statement stm = null;
    ResultSet rs = null;
    DBconnection connect = null;
    OSCPortIn receiver;
    OSCListener listener;
    int userID;
    Connection conn = connect.getMysql().getConnection();
    String updateMeditation = "UPDATE cogVal SET meditation = ? WHERE id = ?;";
    String updateLeft = "UPDATE cogVal SET mLeft = ? WHERE id = ?;";
    String updateRight = "UPDATE cogVal SET mRight = ? WHERE id = ?;";
    String updateForward = "UPDATE cogVal SET forward = ? WHERE id = ?;";
    String updateBackward = "UPDATE cogVal SET backward = ? WHERE id = ?;";
    PreparedStatement preStm;


    public TrainingSession(int id) throws SQLException {

        this.userID = id;

    }

    public float meditation() {

        messageHandler("/AFF/Meditation", med);
        float k = 0;
        for (int i = 0; i<med.size(); i++) {

            k += med.get(i);

        }

        float average = k/med.size();
        //DecimalFormat dc = new DecimalFormat("#.####");
        updateValues(average, updateMeditation);

        return average;
    }



    public float trainLeft() {

        messageHandler("/COG/LEFT", left);
        float k = 0;
        System.out.println("size= "+left.size());
        for (int i = 0; i<left.size(); i++) {

            k += left.get(i);

        }

        float average = k/left.size();
        updateValues(average, updateLeft);

        return average;
    }

    public float trainRight() {

        messageHandler("/COG/RIGHT", right);
        float k = 0;
        for (int i = 0; i<right.size(); i++) {

            k += right.get(i);

        }

        float average = k/right.size();
        updateValues(average, updateRight);
        return average;
    }

    public float trainForward() {

        messageHandler("/COG/PUSH", forward);
        float k = 0;
        for (int i = 0; i<forward.size(); i++) {

            k += forward.get(i);

        }

        float average = k/forward.size();
        updateValues(average, updateForward);
        return average;
    }

    public float trainBackward() {

        messageHandler("/COG/PULL", backward);
        float k = 0;
        for (int i = 0; i<backward.size(); i++) {

            k += backward.get(i);

        }

        float average = k/backward.size();
        updateValues(average, updateBackward);
        return average;
    }

    private void updateValues(float avg, String query) {

        //the method will take 2 parameters and update the database based on them

        try {
            preStm = conn.prepareStatement(query);
            preStm.setFloat(1, avg);
            preStm.setInt(2, userID);
            preStm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void messageHandler(String address, ArrayList list) {

        //the method will take 2 parameters first is the address pattern that is going to be used by the listener
        //the second parameter is an arraylist that is going to be passed by the caller
        //the method will start listening to the incoming messages and add it to the list

        try {
            receiver = new OSCPortIn(7400);
            listener = new OSCListener() {
                @Override
                public void acceptMessage(Date date, OSCMessage message) {
                    Object [] args = message.getArguments();
                    String myMessage = args[0].toString();
                    if (message.getAddress().contains(address)) {
                        System.out.println("message received");
                        list.add(Float.parseFloat(myMessage));
                        System.out.println("address is: "+address);
                        System.out.println("Message: "+myMessage);
                        System.out.println("\n");

                    }

                }
            };

            receiver.addListener(address, listener);
            receiver.startListening();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }


}
