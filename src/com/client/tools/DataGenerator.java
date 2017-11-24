package com.client.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataGenerator 
{
	private List<Entity> entities;
	private String packageName;
	private String savePath;
	private String dbConnectionString;
	private String dbName;
	private String userName;
	private String password;
	private String dictionaryPath;
	private HashMap<String, String> map = new HashMap<String, String>();
	private HashMap<String, String> classNames = new HashMap<String, String>();
	
	public DataGenerator(String dictionaryPath, String packageName, String savePath, String dbConnectionString, String dbName, String userName, String password)
	{
		this.packageName = packageName;
		this.dictionaryPath = dictionaryPath;
		this.savePath = savePath;
		this.dbConnectionString = dbConnectionString;
		this.dbName = dbName;
		this.userName = userName;
		this.password = password;
		this.entities = new ArrayList<Entity>();		
	}
	public void loadFromDbMetasformysql()
	{
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			this.dbName = dbName.toLowerCase();
			this.userName = userName.toLowerCase();
			this.password = password.toLowerCase();
			con = DriverManager.getConnection(this.dbConnectionString, this.userName, this.password);
			//con = DriverManager.getConnection(this.dbConnectionString, "root", "x5");
			//去除系统表的版本
			statement = con.prepareStatement("select table_name from tables where TABLE_SCHEMA='" + this.dbName 
					+ "' and table_name not like 'system_%' and table_name not like 'om_%' and table_name not like 'cms_%'");
			//statement = con.prepareStatement("select table_name from tables where TABLE_SCHEMA='" + this.dbName + "'");
			ResultSet set = statement.executeQuery();
			while(set.next())
			{
				String tableName = set.getString("table_name");
				String className = tableName.toLowerCase();
				
				Entity entity = new Entity(className);
				entity.setChineseName(this.map.get(tableName.toLowerCase()));
				this.entities.add(entity);
				statement = con.prepareStatement("select * from columns where table_name='" + tableName + "' and table_schema='" + this.dbName + "'");
				ResultSet set2 = statement.executeQuery();
				List<Column> columns = new ArrayList<Column>();
				entity.setColumns(columns);
				boolean begin = true;
				while(set2.next())
				{
					Column column = new Column(set2.getString("column_name"));
					String str = set2.getString("Column_comment");
					if(null == str || str.length() <= 0 || "null".equals(str.toLowerCase()) || "undefined".equals(str.toLowerCase()))
						column.setChineseName(set2.getString("column_name"));
					else
						column.setChineseName(set2.getString("Column_comment"));
					column.setIs_nullable(set2.getString("is_nullable"));
					//column.setCharacter_maximum_length(set2.getInt("Character_maximum_length"));
					column.setColumn_comment(set2.getString("Column_comment"));
					column.setColumn_default(set2.getString("Column_default"));
					column.setColumn_type(set2.getString("Column_type"));
					String v = set2.getString("column_key");
					column.setColumn_key(v);
					column.setData_type(set2.getString("data_type"));
					
					if(begin){
						column.setPrimaryKey(true);
						entity.setKeyColumn(column);//key column单独分开
						begin =false;
					}
					else
					{
						columns.add(column);
					}
					//column.setNumberic_precision(set2.getBigDecimal("Numberic_precision").longValue());
					//column.setNumberic_scale(set2.getBigDecimal("Numberic_scale").longValue());
					
				}
				set2.close();
			}		
			set.close();			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
			}
		}
		
	}
	public void loadFromDbMetas()
	{
		if(this.dictionaryPath.equals("MYSQL")) {
			loadFromDbMetasformysql();
			return;
		}
		//initDictionary();//中文对照
		Connection con = null;
		PreparedStatement statement = null;
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.dbName = dbName.toUpperCase();
			this.userName = userName.toUpperCase();
			this.password = password.toUpperCase();
			con = DriverManager.getConnection(this.dbConnectionString, this.userName, this.password);
			//con = DriverManager.getConnection(this.dbConnectionString, "root", "x5");
			String sqltables = "(select b.* from all_tables a left join user_tab_comments b on a.table_name = b.table_name" +
					" where owner='" + this.userName + "')" + 
					" union all (select b.* from all_views a left join user_tab_comments b on a.view_name = b.table_name" +
					" where owner='" + this.userName + "')";
			statement = con.prepareStatement(sqltables);
			//statement = con.prepareStatement("select table_name from tables where TABLE_SCHEMA='" + this.dbName + "'");
			ResultSet set = statement.executeQuery();
			while(set.next())
			{
				String tableName = set.getString("table_name");
				System.out.println(tableName);
				//String className = this.classNames.get(tableName.toLowerCase());
				String className = tableName.toLowerCase();
				
				Entity entity = new Entity(className);
				entity.setChineseName(set.getString("comments"));
				this.entities.add(entity);
				
				//String sqlcolums = "select * from columns where table_name='" + tableName + "' and table_schema='" + this.dbName + "'";
				String sqlcolums = "select c.position,a.comments,b.* "+  
									" from user_col_comments a inner join all_tab_columns b "+
									" on a.table_name = b.table_name and a.column_name = b.column_name " +
									" left join user_cons_columns c "+
									" on a.table_name = c.table_name and a.column_name = c.column_name and c.position is not null "+
									" where a.table_name = upper('" + tableName + "') and b.owner=upper('" + this.userName + "') order by b.COLUMN_ID ";
				statement = con.prepareStatement(sqlcolums);
				ResultSet set2 = statement.executeQuery();
				List<Column> columns = new ArrayList<Column>();
				entity.setColumns(columns);
				boolean begin = true;
				while(set2.next())
				{
					String column_name = set2.getString("column_name").toLowerCase();
					Column column = new Column(column_name);
					//column.setChineseName(this.map.get(column.getName().toLowerCase()));
					String chinesename = set2.getString("comments");
					column.setChineseName(chinesename);
					column.setIs_nullable(set2.getString("nullable"));
					//column.setCharacter_maximum_length(set2.getInt("Character_maximum_length"));
					//column.setColumn_comment(set2.getString("Column_comment"));
					column.setColumn_default(set2.getString("data_default"));
					//column.setColumn_type(set2.getString("Column_type"));
					//String v = set2.getString("column_key");
					//String v = "id";
					//column.setColumn_key(v);
					String data_type = set2.getString("data_type").toLowerCase();
					column.setData_type(data_type);
					column.setColumn_type(data_type);
					String position = set2.getString("position");
					/*if (column_name.equals("rowno"))
					{
					}else */
					if(begin){
						column.setPrimaryKey(true);
						entity.setKeyColumn(column);//key column单独分开
						begin =false;
					}
					else
					{
						columns.add(column);
					}
					
					//column.setNumberic_precision(set2.getBigDecimal("Numberic_precision").longValue());
					//column.setNumberic_scale(set2.getBigDecimal("Numberic_scale").longValue());
					
				}
				set2.close();
			}		
			set.close();			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally
		{
			if (con != null)
			{
				try
				{
					con.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			if (statement != null)
			{
				try
				{
					statement.close();
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
				
			}
		}
		
	}
	public void createAbfConfigXml(String packageName,String savePath,String outputfilename,String templatefilename)
	{
		FreeMarkerGenerator freemarker = new FreeMarkerGenerator();
		freemarker.generateAbfConfigXml(entities, this.packageName, this.savePath + "/"+savePath, outputfilename, templatefilename);
	}
	public void create(String packageName,String savePath,String outputfilename,String templatefilename)
	{
		if(savePath.endsWith("pages")){
			for(Entity entity:entities)
			{
				FreeMarkerGenerator freemarker = new FreeMarkerGenerator();
				freemarker.generate(entity, 
						//this.packageName + "."+packageName, 
						this.packageName,
						this.savePath + "/"+savePath + "/" + entity.getName(),
						outputfilename,
						templatefilename);
			}
		}else{
			for(Entity entity:entities)
			{
				FreeMarkerGenerator freemarker = new FreeMarkerGenerator();
				freemarker.generate(entity, 
						//this.packageName + "."+packageName, 
						this.packageName,
						this.savePath + "/"+savePath,
						outputfilename,
						templatefilename);
			}
		}
	}
}
