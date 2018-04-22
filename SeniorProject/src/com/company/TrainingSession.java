package com.company;

import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

import java.net.SocketException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Arbiter on 22/04/2018.
 */
public class TrainingSession {


    //float directLeft;
    ArrayList<Float> left = new ArrayList<Float>();
    //float directRight;
    ArrayList<Float> right = new ArrayList<Float>();
    //float directBackward;
    ArrayList<Float> backward = new ArrayList<Float>();
    //float directForward;
    ArrayList<Float> forward = new ArrayList<Float>();
    //float med;
    ArrayList<Float> med = new ArrayList<Float>();
    //float excit;
    ArrayList<Float> excit = new ArrayList<Float>();
    Connection conn = null;
    Statement stm = null;
    ResultSet rs = null;
    DBconnection connect = null;
    OSCPortIn receiver;
    OSCListener listener;
    int userID;

    public TrainingSession() {

    }

    public float meditation() {

        try {
            receiver = new OSCPortIn(7400);
            listener = new OSCListener() {
                @Override
                public void acceptMessage(Date date, OSCMessage message) {
                    Object [] args = message.getArguments();
                    String myMessage = args[0].toString();
                    if (message.getAddress().contains("/AFF/Meditation")) {
                        med.add(Float.parseFloat(myMessage));

                    }

                }
            };

            receiver.addListener("/AFF/Meditation", listener);
            receiver.startListening();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        float k = 0;
        for (int i = 0; i<med.size(); i++) {

            k += med.get(i);

        }

        float average = k/med.size();


        return average;
    }



    public float trainLeft() {

        //the method will listen to incoming osc messages and collect values and average it where user is in a
        //good meditation state and thinking left

        try {
            receiver = new OSCPortIn(7400);
            listener = new OSCListener() {
                @Override
                public void acceptMessage(Date date, OSCMessage message) {
                    Object [] args = message.getArguments();
                    String myMessage = args[0].toString();
                    float medVal = meditation();
                        if (medVal >=5 && message.getAddress().contains("/COG/LEFT")) {
                            left.add(Float.parseFloat(myMessage));
                        }

                }
            };

            receiver.addListener("/COG/LEFT", listener);
            receiver.startListening();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        float k = 0;
        for (int i = 0; i<left.size(); i++) {

            k += left.get(i);

        }

        float average = k/left.size();


        return average;
    }

    public float trainRight() {

        //the method will listen to incoming osc messages and collect values and average it where user is in a
        //good meditation state and thinking right

        try {
            receiver = new OSCPortIn(7400);
            listener = new OSCListener() {
                @Override
                public void acceptMessage(Date date, OSCMessage message) {
                    Object [] args = message.getArguments();
                    String myMessage = args[0].toString();
                    float medVal = meditation();
                    if (medVal >=5 && message.getAddress().contains("/COG/RIGHT")) {
                        right.add(Float.parseFloat(myMessage));
                    }

                }
            };

            receiver.addListener("/COG/RIGHT", listener);
            receiver.startListening();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        float k = 0;
        for (int i = 0; i<right.size(); i++) {

            k += right.get(i);

        }

        float average = k/right.size();


        return average;
    }

    public float trainForward() {

        //the method will listen to incoming osc messages and collect values and average it where user is in a
        //good meditation state and thinking forward

        try {
            receiver = new OSCPortIn(7400);
            listener = new OSCListener() {
                @Override
                public void acceptMessage(Date date, OSCMessage message) {
                    Object [] args = message.getArguments();
                    String myMessage = args[0].toString();
                    float medVal = meditation();
                    if (medVal >=5 && message.getAddress().contains("/COG/ROTATE_FORWARD")) {
                        forward.add(Float.parseFloat(myMessage));
                    }

                }
            };

            receiver.addListener("/COG/ROTATE_FORWARD", listener);
            receiver.startListening();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        float k = 0;
        for (int i = 0; i<forward.size(); i++) {

            k += forward.get(i);

        }

        float average = k/forward.size();


        return average;
    }

    public float trainBackward() {

        //the method will listen to incoming osc messages and collect values and average it where user is in a
        //good meditation state and thinking backward

        try {
            receiver = new OSCPortIn(7400);
            listener = new OSCListener() {
                @Override
                public void acceptMessage(Date date, OSCMessage message) {
                    Object [] args = message.getArguments();
                    String myMessage = args[0].toString();
                    float medVal = meditation();
                    if (medVal >=5 && message.getAddress().contains("/COG/ROTATE_REVERSE")) {
                        backward.add(Float.parseFloat(myMessage));
                    }

                }
            };

            receiver.addListener("/COG/ROTATE_REVERSE", listener);
            receiver.startListening();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        float k = 0;
        for (int i = 0; i<backward.size(); i++) {

            k += backward.get(i);

        }

        float average = k/backward.size();


        return average;
    }

}
