package edu.sdsc.Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * A class that imports json data into postgres database.
 * @author Haoran Sun
 * @since 02/05/2018
 */
public class PostgreDataIngestor {
  static final String DRIVER = "org.postgresql.Driver";
  static final String PREFIX = "jdbc:postgresql://";
  
  private static final Logger logger =
      Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
  
  private HashSet<String> cols;
  
  /**
   * Default constructor
   */
  public PostgreDataIngestor() {
  	this.cols = new HashSet<>();
  	this.cols.add("nct_id");
  }
  
  /**
   * Connect to specified PostgreSQL database
   * @param DBName - database name
   * @param ConnectString - database ip and port
   * @param uid - user name
   * @param password - password
   * @return a valid connection
   */
  public Connection getConn(String ConnectString, String uid,
      String password) {
    Connection conn = null;
    try {
      Class.forName(DRIVER); //Load PostgreSQL JDBC driver
      /* Enter user and password information */
      Properties props = new Properties();
      props.setProperty("user", uid);
      props.setProperty("password", password);
      conn = DriverManager.getConnection(PREFIX + ConnectString , props);
    } catch (ClassNotFoundException | SQLException e) {
      logger.severe("Postgres connection failed!");
    }
    return conn;
  }
  
  /**
   * Send query and obtain result
   * @param conn - connection
   * @param queryString - query expression
   * @return query result set
   */
  public void query(Connection conn, String queryString) {
    try {
      Statement st = conn.createStatement();
      st.executeUpdate(queryString); //Get query result
    } catch (SQLException e) {
      logger.severe("Failed to get resultSet!");
    }
  }
  
  public static void main(String[] args) {
  	PostgreDataIngestor pg = new PostgreDataIngestor();
  	Connection conn = pg.getConn("10.128.36.22/postgres", "postgres", args[0]);
  	pg.query(conn, "CREATE TABLE IF NOT EXISTS metamap (nct_id text)");
  }
}