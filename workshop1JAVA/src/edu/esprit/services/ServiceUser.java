/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.esprit.services;

import edu.esprit.entities.Admin;
import edu.esprit.entities.User;
import edu.esprit.utils.DataSource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author wala
 */
public class ServiceUser implements iService<User> {

    Connection cnx = DataSource.getInstance().getCnx();

    
     public ObservableList<User> AfficherTout()  {
        ObservableList<User> list = FXCollections.observableArrayList();
        String requete = "SELECT * FROM `user` where `roles` = '[\"ROLE_CLIENT\"]'";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet result = ps.executeQuery();

            while (result.next()) {

             list.add( new User(result.getInt(1), result.getString("username"), result.getString("password"), result.getString("email"), result.getInt("phone_number"), result.getString("image_user")));
 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    
    }
     
     public ObservableList<User> AfficherAdmin()  {
        ObservableList<User> list1 = FXCollections.observableArrayList();
        String requete = "SELECT * FROM `user` where `roles` = '[\"ROLE_ADMIN\"]'";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet result = ps.executeQuery();

            while (result.next()) {

             list1.add( new User(result.getInt(1), result.getString("username"), result.getString("password"), result.getString("email"), result.getInt("phone_number"), result.getString("image_user")));
 }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list1;
    
    }
    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            String req = "SELECT * FROM `user`";
            Statement st = cnx.createStatement();
            ResultSet result = st.executeQuery(req);

            while (result.next()) {
                if (result.getString("roles").equals("[ROLE_ADMIN]")) {
                    // System.out.println("admin");
                    User u = new Admin(result.getInt(1), result.getString("username"), result.getString("password"), result.getString("email"), result.getInt("phone_number"), result.getString("image_user"));
                    users.add(u);
                } else {
                    //  System.out.println("client");
                    User u = new User(result.getInt(1), result.getString("username"), result.getString("password"), result.getString("email"), result.getInt("phone_number"), result.getString("image_user"));
                    users.add(u);
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return users;

    }

    @Override
    public void ajouter(User p) {
        System.out.println(p instanceof Admin);
        System.out.println(p instanceof User);

        try {
            if (p instanceof Admin) {
                String requete = "INSERT INTO user (email, username, password,image_user,phone_number,etat,roles) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement ps = new DataSource().getCnx().prepareStatement(requete);
                ps.setString(1, p.getEmail());
                ps.setString(2, p.getUsername());
                ps.setString(3, HashPass(p.getPassword()));
                ps.setString(4, p.getImageUser());
                ps.setInt(5, p.getPhoneNumber());
                ps.setInt(6, 0);
                
                ps.setString(7, "[" + (char) 34 + "ROLE_ADMIN" + (char) 34 + "]");
                System.out.println("Admin was added successfully");

                ps.executeUpdate();
            } else {
                String requete = "INSERT INTO user (email, username, password,image_user,phone_number,etat,roles) VALUES (?,?,?,?,?,?,?)";
                PreparedStatement ps = new DataSource().getCnx().prepareStatement(requete);
                ps.setString(1, p.getEmail());
                ps.setString(2, p.getUsername());
                ps.setString(3, HashPass(p.getPassword()));
                ps.setString(4, p.getImageUser());
                ps.setInt(5, p.getPhoneNumber());
                ps.setInt(6, 0);
                ps.setString(7, "[" + (char) 34 + "ROLE_CLIENT" + (char) 34 + "]");

                ps.executeUpdate();
                System.out.println("User was added successfully");
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

    }

    @Override
    public void modifier(User p) {
        try {
            String req = "UPDATE `user` SET `email` = '" + p.getEmail() + "', `username` = '" + p.getUsername() + "', `password` = '" + HashPass(p.getPassword()) + "', `phone_number` = '" + p.getPhoneNumber()+ "', `image_user` = '" + p.getImageUser()+"' WHERE `user`.`id` = " + p.getId();
            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("User was updated suuccessfully!");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(int id) {
        try {
            String req = "DELETE FROM `user` WHERE `user`.`id` =" + id;

            Statement st = cnx.createStatement();
            st.executeUpdate(req);
            System.out.println("User removed Successfuly !!");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public boolean delete(User p) {
        String req = "DELETE FROM user WHERE id=?";
        boolean delete = false;
        try {
            PreparedStatement ps = new DataSource().getCnx().prepareStatement(req);
            ps.setInt(1, p.getId());
            delete = ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return delete;
    }

    public String HashPass(String pass) {
        try {
            MessageDigest ms = MessageDigest.getInstance("MD5");

            ms.update(pass.getBytes());

            byte[] resultByteArray = ms.digest();

            StringBuilder sb = new StringBuilder();

            for (byte b : resultByteArray) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return "";
    }

    public boolean block(User user) {
        String req = "UPDATE user SET etat=1 WHERE id=?";
        boolean update = false;
        try {
            PreparedStatement ps = new DataSource().getCnx().prepareStatement(req);

            ps.setInt(1, user.getId());

            update = ps.executeUpdate() > 0;
            System.out.println("User was blocked successfully ");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return update;

    }

    public boolean unBlock(User user) {
        String req = "UPDATE user SET etat=0 WHERE id=?";
        boolean update = false;
        try {
            PreparedStatement ps = new DataSource().getCnx().prepareStatement(req);

            ps.setInt(1, user.getId());

            update = ps.executeUpdate() > 0;
            System.out.println("User was blocked successfully ");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return update;

    }

    public boolean login(String email, String mdp) {
        boolean login = true;
        String hashedPass = HashPass(mdp);

        String req = "SELECT * FROM  `user` WHERE email='" + email + "' and password ='" + hashedPass + "'";
        try {

            PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                if (rs.getInt("etat") == 0) {
                    System.out.println("You have successfully connected! ");
                    System.out.println("Role:" + rs.getString("roles"));

                } else {
                    System.err.println("Sorry you cannot have access to your account ! you are blocked ");

                }

            } else {
                System.err.println("Authetificaton failed! ");
                login = false;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            login = false;
        }
        return login;
    }

    public User login1(String email, String pass) {
        String hashedPass = HashPass(pass);
        int count = 0;

        //Timestamp nowDate = Timestamp.from(Instant.now());
       String req = "SELECT * FROM  `user` WHERE email='" + email + "' and password ='" + hashedPass + "'";
        User u = null;
        try {
             PreparedStatement ps = cnx.prepareStatement(req);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                 if (rs.getInt("etat") == 0) {
                    System.out.println("You have successfully connected! ");
                    u = new User(rs.getInt(1), rs.getString(3), rs.getString(4),
                        rs.getString(2), rs.getInt(12),
                        rs.getString(14));
                   // System.out.println("Role:" + rs.getString("roles")+"user: "+u);

                } else {
                    System.err.println("Sorry you cannot have access to your account ! you are blocked ");

                }

            

            
            }
             else {
                System.err.println("Authetificaton failed! ");
                //login = false;
            }
        } catch (SQLException ex) {
            System.err.println("User not found");

            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }

        return u;

    }

    public void envoyerMail(String recepient, String code) throws Exception {
        String email = "wala.alimi@esprit.tn";
        String password = "rimen2021";

        System.out.println("Preparing to send email");

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });
        Message message = prepareMessage(session, email, recepient, code);
        Transport.send(message);
        System.out.println("Message sent successfully");
    }

    private static Message prepareMessage(Session session, String email, String recepient, String code) {
        try {
            String htmlCode;
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));
            if (code.length() == 6) {
                message.setSubject("Reset Password");

                htmlCode = "<!doctype html>\n"
                        + "<html lang=\"en-US\">\n"
                        + "\n"
                        + "<head>\n"
                        + "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n"
                        + "    <title>mot de passe oublié</title>\n"
                        + "    <meta name=\"description\" content=\"Reset Password Email Template.\">\n"
                        + "    <style type=\"text/css\">\n"
                        + "        a:hover {text-decoration: underline !important;}\n"
                        + "    </style>\n"
                        + "</head>\n"
                        + "\n"
                        + "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">\n"
                        + "    <!--100% body table-->\n"
                        + "    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\"\n"
                        + "        style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">\n"
                        + "        <tr>\n"
                        + "            <td>\n"
                        + "                <table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"\n"
                        + "                    align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"height:80px;\">&nbsp;</td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"text-align:center;\">\n"
                        + "                          <a href=\"https://www.metaTech.tn\" title=\"logo\" target=\"_blank\">\n"
                        + "                            <img width=\"120\" alt=\"logo\">\n"
                        + "                          </a>\n"
                        + "                        </td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"height:20px;\">&nbsp;</td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td>\n"
                        + "                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n"
                        + "                                style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">\n"
                        + "                                <tr>\n"
                        + "                                    <td style=\"height:40px;\">&nbsp;</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td style=\"padding:0 35px;\">\n"
                        + "                                        <h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">Mot De Passe Oublié</h1>\n"
                        + "                                        <span\n"
                        + "                                            style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>\n"
                        + "                                        <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\">\n"
                        + "                                          <strong>" + code + "</strong> : Use this code to reset your password .<br>\n"
                        + " L'équipe MetaTech\n"
                        + "                                        </p>\n"
                        + "                                    </td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td style=\"height:40px;\">&nbsp;</td>\n"
                        + "                                </tr>\n"
                        + "                            </table>\n"
                        + "                        </td>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"height:20px;\">&nbsp;</td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"text-align:center;\">\n"
                        + "                            <p style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">&copy; <strong>www.metaTech.tn</strong></p>\n"
                        + "                        </td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"height:80px;\">&nbsp;</td>\n"
                        + "                    </tr>\n"
                        + "                </table>\n"
                        + "            </td>\n"
                        + "        </tr>\n"
                        + "    </table>\n"
                        + "    <!--/100% body table-->\n"
                        + "</body>\n"
                        + "\n"
                        + "</html>";
            } else {
                message.setSubject("verification e-mail FTF");

                htmlCode = "<!doctype html>\n"
                        + "<html lang=\"en-US\">\n"
                        + "\n"
                        + "<head>\n"
                        + "    <meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />\n"
                        + "    <title>verification E-mail</title>\n"
                        + "    <meta name=\"description\" content=\"Reset Password Email Template.\">\n"
                        + "    <style type=\"text/css\">\n"
                        + "        a:hover {text-decoration: underline !important;}\n"
                        + "    </style>\n"
                        + "</head>\n"
                        + "\n"
                        + "<body marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" style=\"margin: 0px; background-color: #f2f3f8;\" leftmargin=\"0\">\n"
                        + "    <!--100% body table-->\n"
                        + "    <table cellspacing=\"0\" border=\"0\" cellpadding=\"0\" width=\"100%\" bgcolor=\"#f2f3f8\"\n"
                        + "        style=\"@import url(https://fonts.googleapis.com/css?family=Rubik:300,400,500,700|Open+Sans:300,400,600,700); font-family: 'Open Sans', sans-serif;\">\n"
                        + "        <tr>\n"
                        + "            <td>\n"
                        + "                <table style=\"background-color: #f2f3f8; max-width:670px;  margin:0 auto;\" width=\"100%\" border=\"0\"\n"
                        + "                    align=\"center\" cellpadding=\"0\" cellspacing=\"0\">\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"height:80px;\">&nbsp;</td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"text-align:center;\">\n"
                        + "                          <a href=\"https://www.metatech.tn\" title=\"logo\" target=\"_blank\">\n"
                        + "                            <img width=\"120\" title=\"logo\" alt=\"logo\">\n"
                        + "                          </a>\n"
                        + "                        </td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"height:20px;\">&nbsp;</td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td>\n"
                        + "                            <table width=\"95%\" border=\"0\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\"\n"
                        + "                                style=\"max-width:670px;background:#fff; border-radius:3px; text-align:center;-webkit-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);-moz-box-shadow:0 6px 18px 0 rgba(0,0,0,.06);box-shadow:0 6px 18px 0 rgba(0,0,0,.06);\">\n"
                        + "                                <tr>\n"
                        + "                                    <td style=\"height:40px;\">&nbsp;</td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td style=\"padding:0 35px;\">\n"
                        + "                                        <h1 style=\"color:#1e1e2d; font-weight:500; margin:0;font-size:32px;font-family:'Rubik',sans-serif;\">verification E-mail</h1>\n"
                        + "                                        <span\n"
                        + "                                            style=\"display:inline-block; vertical-align:middle; margin:29px 0 26px; border-bottom:1px solid #cecece; width:100px;\"></span>\n"
                        + "                                        <p style=\"color:#455056; font-size:15px;line-height:24px; margin:0;\">\n"
                        + "             Salut ,Veuillez suivre ce lien pour vérifier votre adresse e-mail et la connecter à votre compte FTF :Vérifiez votre e-mail Si vous n'avez pas envoyé la demande, veuillez ignorer cet e-mail.<br>\n"
                        + " L'équipe FTF\n"
                        + "                                        </p>\n"
                        + "                                        <a href=\"javascript:void(0);\"\n"
                        + "                                            style=\"background:red;text-decoration:none !important; font-weight:500; margin-top:35px; color:#fff;text-transform:uppercase; font-size:14px;padding:10px 24px;display:inline-block;border-radius:50px;\">verifier E-mail</a>\n"
                        + "                                    </td>\n"
                        + "                                </tr>\n"
                        + "                                <tr>\n"
                        + "                                    <td style=\"height:40px;\">&nbsp;</td>\n"
                        + "                                </tr>\n"
                        + "                            </table>\n"
                        + "                        </td>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"height:20px;\">&nbsp;</td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"text-align:center;\">\n"
                        + "                            <p style=\"font-size:14px; color:rgba(69, 80, 86, 0.7411764705882353); line-height:18px; margin:0 0 0;\">&copy; <strong>www.ftf.tn</strong></p>\n"
                        + "                        </td>\n"
                        + "                    </tr>\n"
                        + "                    <tr>\n"
                        + "                        <td style=\"height:80px;\">&nbsp;</td>\n"
                        + "                    </tr>\n"
                        + "                </table>\n"
                        + "            </td>\n"
                        + "        </tr>\n"
                        + "    </table>\n"
                        + "    <!--/100% body table-->\n"
                        + "</body>\n"
                        + "\n"
                        + "</html>";
            }

            message.setContent(htmlCode, "text/html");
            return message;
        } catch (Exception ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean sendMailPass(String email) {
        String req = "UPDATE user SET reset_token=? WHERE email=?";
        boolean update = false;
        try {
            PreparedStatement pst = new DataSource().getCnx().prepareStatement(req);
            long code = (long) (Math.random() * (999999 - 100000));
            pst.setLong(1, code);
            pst.setString(2, email);
            update = pst.executeUpdate() > 0;
            envoyerMail(email, Long.toString(code));
            System.out.println("mail sent!");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return update;

    }

    public boolean changePassCode(String email, String code, String newPass) {
        String req = "UPDATE user SET password=?, reset_token=null WHERE email=? and reset_token=?";
        boolean update = false;
        try {
            PreparedStatement pst = new DataSource().getCnx().prepareStatement(req);
            pst.setString(1, HashPass(newPass));
            pst.setString(2, email);
            pst.setString(3, code);
            update = pst.executeUpdate() > 0;
            System.out.println("password wad changes successfully!");

        } catch (SQLException ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServiceUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        return update;
    }

    public void setConnected(String email) {

        try {
            String querry = "update user set connected=1 where email='" + email + "'";
            System.out.println(querry);
            Statement stm = cnx.createStatement();

            stm.executeUpdate(querry);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
    public void disconnectAll() {

        try {
            String querry = "update user set connected=0 ";
            System.out.println(querry);
            Statement stm = cnx.createStatement();

            stm.executeUpdate(querry);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public User getConnected() {

        User u = new User();
        try {
            Statement stm = cnx.createStatement();
            String querry = "SELECT * FROM user where connected=1";

            ResultSet rs = stm.executeQuery(querry);

            while (rs.next()) {

                u.setId(rs.getInt("id"));
                u.setPassword(rs.getString("password"));
                u.setUsername(rs.getString("username"));

                u.setEmail(rs.getString("email"));
                //u.setRole(rs.getString("role")));
                u.setPhoneNumber(rs.getInt("phone_number"));
                u.setImageUser(rs.getString("image_user"));

                System.out.println("name:" + u.getUsername() + " " + "email:" + u.getEmail() + " " + "Role:" + rs.getString("roles"));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

        return u;

    }

    public String checkRole(String email) {
        String default_return = "role not found";
        try {
            String req;
            req = "select roles from user where email=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, email);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                if (rs.getString("roles").equals("[" + (char) 34 + "ROLE_ADMIN" + (char) 34 + "]")) {

                    return "admin";
                } else if (rs.getString("roles").equals("[" + (char) 34 + "ROLE_CLIENT" + (char) 34 + "]")) {
                    System.out.println("third");
                    return "client";

                }

            }

        } catch (SQLException ex) {
        }
        return default_return;
    }
    public int checkBlock(String email){
        int etat=0;
          try {
            String req;
            req = "select etat from user where email=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, email);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                if (rs.getInt("etat")==1) {
                  return 1; // blocked
                    
                } else if(rs.getInt("etat")==0)
                {
                return 0; //not blocked
                }
            }

        } catch (SQLException ex) {
        }
        return etat;
    }
    
    public List<User> findUser(String critere,String rech) {
      
        List<User> Users = new ArrayList();
    
        try {
        Statement stm =cnx.createStatement();
        String querry ="SELECT * FROM user where "+critere+" like '"+rech+"%'";
     
        ResultSet rs= stm.executeQuery(querry);
        
        while(rs.next()){
            User u = new User();
            u.setId(rs.getInt("id"));
            u.setPassword(rs.getString("password"));
            u.setUsername(rs.getString("username"));
            u.setEmail(rs.getString("email"));
            u.setPhoneNumber((rs.getInt("phone_number")));
           u.setImageUser((rs.getString("image_user")));
           

            
            
            
            
            Users.add(u);
        }
        
    } catch (SQLException ex) {
            System.out.println(ex.getMessage()); 
    
    }
        
        
   
        return Users;
   
    }
    public boolean verifyCode(String code,String email){
        
        boolean resultat=false;
        try {
            String req;
            req = "select reset_token from user where email=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, email);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                if (rs.getString("reset_token").equals(code)) {
                    System.out.println("code match");
                  return true;
                    
                } else 
                {
                    System.out.println("code does not match");
                return false;
                }
            }

        } catch (SQLException ex) {
        }
        
        
        return resultat;
    }
     public boolean findEmail(String email){
        
        boolean resultat=false;
        try {
            String req;
            req = "select email from user where email=?";
            PreparedStatement st = cnx.prepareStatement(req);
            st.setString(1, email);

            ResultSet rs = st.executeQuery();

            while (rs.next()) {

                if (rs.getString("email").equals(email)) {
                    System.out.println("email does exist");
                  return true;
                    
                } else 
                {
                    System.out.println("email does no exist");
                return false;
                }
            }

        } catch (SQLException ex) {
        }
        
        
        return resultat;
    }

}
