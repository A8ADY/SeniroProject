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

    public static void main(String[] args) throws SQLException, SocketException, UnknownHostException {

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




