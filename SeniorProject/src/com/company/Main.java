package com.company;

import com.illposed.osc.*;
import com.illposed.osc.OSCMessage;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import oscP5.OscMessage;
import oscP5.OscP5;

import javax.sql.DataSource;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    public static void main(String[] args) throws SQLException, SocketException, UnknownHostException {


        //OscP5 oscp5;
        //OscMessage theOscMessage;
        Connection conn = null;
        Statement stm = null;
        ResultSet rs = null;
        DBconnection connect = null;
        conn = connect.getMysql().getConnection();
        stm = conn.createStatement();
        TrainingSession train = new TrainingSession(5);

//        OSCPortOut sender = new OSCPortOut();
//        Object argss[] = new Object[2];
//        argss[0] = "hello";
//        OSCMessage msg = new OSCMessage("/test", argss);
//        try {
//            sender.send(msg);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        train.meditation();
//        train.trainLeft();
//        train.trainRight();
        train.trainForward();


//



    }
}




