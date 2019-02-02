package com.child.methout.model;

import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.JDBCDataModel;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MyDataModel {

	public static JDBCDataModel myDataModel() {
		MysqlDataSource dataSource = new MysqlDataSource();
		JDBCDataModel dataModel = null;
		try {
			dataSource.setServerName("127.0.0.1");
			dataSource.setUser("root");
			dataSource.setPassword("root");
			dataSource.setDatabaseName("movie");
			// use JNDI
//			new MySQLJDBCDataModel(dataSource, preferenceTable, userIDColumn, itemIDColumn, preferenceColumn, timestampColumn)
			dataModel = new MySQLJDBCDataModel(dataSource,"user_movie_reference", "user_id", "movie_id","refrence",null);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return dataModel;
	}

}
