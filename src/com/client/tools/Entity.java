package com.client.tools;

import java.util.List;

public class Entity 
{
	private String name;
	private String chineseName;
	private Column keyColumn;
	private List<Column> columns;
	
	public Entity(String name)
	{
		this.name = name;
	}
	public String getName() {
		return name.substring(0, 1).toUpperCase() + name.substring(1);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChineseName() {
		if (chineseName == null)
		{
			return name;
		}
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public List<Column> getColumns() {
		return columns;
	}
	public void setColumns(List<Column> columns) {
		this.columns = columns;
	}
	
	public String getObjectName()
	{
		return name.substring(0, 1).toLowerCase() + name.substring(1);
	}
	
	public String getInsertSql()
	{
		//"insert into template(name, sceneCount, fileName) values(?,?,?)";
		String value = "insert into " + this.getObjectName() + "(";
		int count = columns.size();
		int index = 0;
		for(Column column:columns)
		{
			
			value += column.getName();
			if (index < count - 1)
			{
				value += ",";
			}
			index++;			
		}
		value += ") values(";
		for(int i = 0; i < count; i++)
		{
			value += "?";
			if (i < count - 1)
			{
				value += ",";
			}
		}
		value += ")";
		
		//System.out.println("value:" + value);
		
		return value;
	}
	
	public String getInsertArguments()
	{
		
		//return "template.getName(), template.getSceneCount(), template.getFileName()";
		
		String value = "";
		int count = columns.size();
		int index = 0;
		
		for(Column column:columns)
		{
			value += this.getObjectName() + ".get" + column.getName() + "()";
			if (index < count - 1)
			{
				value += ",";
			}
			index++;			
		}
		
		
		//System.out.println("\narguments:" + value);
		
		return value;
		
	}
	
	public String getUpdateSql()
	{
		//return "\" update template set name=?, sceneCount=?, fileName=? where templateid= \" + template.getTemplateId()";
		
		String value = "\"update " + this.getObjectName() + " set ";
		int count = columns.size();
		int index = 0;
		
		for(Column column:columns)
		{
			value += column.getName() + "=?";
			if (index < count - 1)
			{
				value += ",";
			}
			index++;			
		}
		//"where InstallLocationId=" + installLocation.getInstallLocationId()
		value += " where " + keyColumn.getName() + "=\" + " + this.getObjectName() + ".get" + keyColumn.getName() + "()";
		//System.out.println("value:" + value);
		return value;
	}
	
	public String getUpdateArguments()
	{
		//return "template.getName(), template.getSceneCount(), template.getFileName()";
		return getInsertArguments();
	}
	
	public String getDeleteSql()
	{
		//return "delete from template where templateid=";
		/*Column keyColumn = null;
		
		for(Column column:columns)
		{
			if (column.isPrimaryKey())
			{
				keyColumn = column;
				
				break;
			}
		}*/
		
		return "delete from " + name  + " where " + keyColumn.getName() + "=";
	}
	
	public String getGetSql()
	{
		/*//return "select * from template where templateId=";
		Column keyColumn = null;
		
		for(Column column:columns)
		{
			if (column.isPrimaryKey())
			{
				keyColumn = column;
				
				break;
			}
		}*/
		return "select * from " + name + " where " + keyColumn.getName() + "=";
	}
	
	public String getFindSql()
	{
		
		//return "select * from template";
		return "select * from " + this.name;
	}
	
	public String getKeyColumnName()
	{
		/*Column keyColumn = null;
		for(Column column:columns)
		{
			if (column.isPrimaryKey())
			{
				keyColumn = column;
				
				break;
			}
		}
		return keyColumn.getName();*/
		return keyColumn.getName();
	}

	public Column getKeyColumn() {
		return keyColumn;
	}

	public void setKeyColumn(Column keyColumn) {
		this.keyColumn = keyColumn;
	}
	
	public String getViewDialogSize()
	{
		//width:1024px;height:600px
		int height = 150 + this.columns.size() * 30;
		if (height > 700)
		{
			height = 700;
		}
		return "width:600px;height:" + height + "px";
	}
	
	
	
}
