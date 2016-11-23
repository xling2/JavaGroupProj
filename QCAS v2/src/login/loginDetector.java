/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 *
 * @author Yixin1
 */
public class loginDetector {

    private boolean loginSuccess = false;

    public loginDetector(String username, String passwd) {
        String encryptedPwd = new String();
//        try {
//            MessageDigest md = MessageDigest.getInstance("MD5");
//            md.update(passwd.getBytes(), 0, passwd.length());
//            encryptedPwd = new BigInteger(1, md.digest()).toString(16);
//        } catch (NoSuchAlgorithmException nae) {
//            System.out.println("NoSuchAlgorithmException @ login: " + nae);
//        }
//
//        String url = "jdbc:derby://localhost:1527/UserDB";
//        final String userDBusernm = "administrator";
//        final String userDBpasswd = "admin@QCAS";
//        String pQuery = "SELECT * FROM QCASUSER WHERE username LIKE '"
//                + username + "' AND password LIKE '" + encryptedPwd + "'";
//
//        try (Connection con = DriverManager.getConnection(url,
//                userDBusernm, userDBpasswd);
//                PreparedStatement pStmt = con.prepareStatement(pQuery);) {
//            ResultSet userRS = pStmt.executeQuery();
//            if (userRS.next()) {
                loginSuccess = true;
//            }
//        } catch (SQLException e) {
//            System.out.println("SQL Exception @ login: " + e);
//        }
    }
    
    public boolean getLoginSuccess(){
        return loginSuccess;
    }
}