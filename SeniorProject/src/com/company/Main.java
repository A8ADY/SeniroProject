package com.company;

import com.illposed.osc.*;
import com.illposed.osc.OSCMessage;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import oscP5.OscMessage;
import oscP5.OscP5;

import javax.sql.DataSource;
import java.net.SocketException;
import java.sql.*;
import java.util.Date;



public class Main {

    static float directLeft;
    static float directRight;
    static float directBackward;
    static float directForward;
    static float med;
    static float excit;
    //static float push;
    //static float pull;

    public static void main(String[] args) throws SQLException {


        //OscP5 oscp5;
        //OscMessage theOscMessage;
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        DBconnection connect = null;
        conn = connect.getMysql().getConnection();
        String updateValues = "UPDATE cogVal SET mLeft = ?, mRight = ?," +
                " forward = ?, backward = ?, meditation = ?, excitement = ?, WHERE id = ?;";
        PreparedStatement preStm = conn.prepareStatement(updateValues);
        stm = conn.createStatement();



        try {
            OSCPortIn receiver = new OSCPortIn(15000);
            OSCListener listener = new OSCListener() {
                @Override
                public void acceptMessage(Date time, OSCMessage message) {

                    System.out.println("MessageReceived!");
                    Object [] args = message.getArguments();
                    String myMessage = args[0].toString();
                    //System.out.println(myMessage);
                    if (message.getAddress().contains("/COG/LEFT")) {

                        directLeft = Float.parseFloat(myMessage);

                    } else if (message.getAddress().contains("/COG/RIGHT")) {

                        directRight = Float.parseFloat(myMessage);

                    } else if (message.getAddress().contains("/COG/ROTATE_FORWARD")) {

                        directForward = Float.parseFloat(myMessage);

                    } else if (message.getAddress().contains("/COG/ROTATE_REVERSE")) {

                        directBackward = Float.parseFloat(myMessage);

                    } else if (message.getAddress().contains("/AFF/Meditation")) {

                        med = Float.parseFloat(myMessage);

                    } else if (message.getAddress().contains("/AFF/Excitement")) {

                        excit = Float.parseFloat(myMessage);

                    } else if (message.getAddress().contains("/test")) {

                        System.out.println(myMessage);
                    }


                        try {
                        preStm.setFloat(1, directLeft);
                        preStm.setFloat(2, directRight);
                        preStm.setFloat(3, directForward);
                        preStm.setFloat(4, directBackward);
                        preStm.setFloat(5, med);
                        preStm.setFloat(6, excit);
                        preStm.setInt(7, 1);
                        preStm.executeUpdate();

                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                }
            };

            receiver.addListener("/COG/LEFT", listener);
            receiver.addListener("/test", listener);
            receiver.addListener("/COG/RIGHt", listener);
            receiver.addListener("/COG/ROTATE_REVERSE", listener);
            receiver.addListener("/COG/ROTATE_FORWARD", listener);
            receiver.addListener("/AFF/Meditation", listener);
            receiver.addListener("/AFF/Excitement", listener);
            receiver.startListening();




        } catch (SocketException e) {
            e.printStackTrace();
        }

        conn.close();
    }

//    public static DataSource getMysql() {
//
//        MysqlDataSource dataSource = new MysqlDataSource();
//
//        dataSource.setDatabaseName("Project");
//        dataSource.setUser("emotiv");
//        dataSource.setPassword("499");
//        dataSource.setServerName("localhost");
//
//
//
//
//        return dataSource;
//    }


}
