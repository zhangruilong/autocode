package com.client.tools;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Column 
{
	private String name;
	private String chineseName;
	private String column_type;
	private String is_nullable;
	private String column_default;
	private String data_type;
	private String column_key; //pri, mul
	private int character_maximum_length;
	private long numberic_scale;
	private long numberic_precision;
	private String column_comment;//column_comment=data_type的首字母大写
	private boolean primaryKey = false;
	
	public Column(String name)
	{
		this.name = name;
	}
	
	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
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

	public String getColumn_type() {
		return column_type;
	}

	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}

	public String getIs_nullable() {
		return is_nullable;
	}

	public void setIs_nullable(String is_nullable) {
		this.is_nullable = is_nullable;
	}

	public String getColumn_default() {
		return column_default;
	}

	public void setColumn_default(String column_default) {
		this.column_default = column_default;
	}

	public int getCharacter_maximum_length() {
		return character_maximum_length;
	}

	public void setCharacter_maximum_length(int character_maximum_length) {
		this.character_maximum_length = character_maximum_length;
	}

	public void setNumberic_scale(int numberic_scale) {
		this.numberic_scale = numberic_scale;
	}
	public String getColumn_key() {
		return column_key;
	}

	public void setColumn_key(String column_key) {
		this.column_key = column_key;
	}

	public boolean isForeignKey() {
		if (column_key != null && column_key.toLowerCase().equals("mul"))
		{
			return true;
		}
		return false;
	}

	

	public String getData_type() {
		return data_type;
	}

	public void setData_type(String data_type) {
		this.data_type = data_type;
	}

	public void setNumberic_precision(int numberic_precision) {
		this.numberic_precision = numberic_precision;
	}

	public String getColumn_comment() {//
		return getDataType().substring(0, 1).toUpperCase() + getDataType().substring(1);
	}

	public void setColumn_comment(String column_comment) {
		this.column_comment = column_comment;
	}

	public long getNumberic_scale() {
		return numberic_scale;
	}

	public void setNumberic_scale(long numberic_scale) {
		this.numberic_scale = numberic_scale;
	}

	public long getNumberic_precision() {
		return numberic_precision;
	}

	public void setNumberic_precision(long numberic_precision) {
		this.numberic_precision = numberic_precision;
	}
	
	public String getDataType()
	{
		String columnType = this.column_type;
		if (columnType.startsWith("number"))
		{
			return "int";
		}
		else if (columnType.startsWith("nvarchar2")|| columnType.startsWith("varchar2")|| columnType.startsWith("char"))
		{
			return "String";
		}
		else if (columnType.startsWith("date"))
		{
			return "Date";
		}
		else if (columnType.startsWith("binary_float"))
		{
			return "float";
		}
		else if (columnType.startsWith("binary_double"))
		{
			return "double";
		}
		else if (columnType.startsWith("long"))
		{
			return "long";
		}
		return "String";
				
	}
	
	public String getFieldName()
	{
		return this.name.substring(0, 1).toLowerCase() + this.name.substring(1);
	}
	
	
	public int getWidth()
	{
		if (this.character_maximum_length <= 50)
		{
			return 50;
		}
		/*else if (this.character_maximum_length <= 32)
		{
			return 50;
		}*/
		else if (this.character_maximum_length > 100)
		{
			return 150;
		}
		return this.character_maximum_length;
	}
	
	public String getRequestSettingCode()
	{
		String v = "set" + this.getName() + "(";
		String columnType = this.column_type.toLowerCase();
		String value = "request.getParameter(\"" + this.getFieldName() + "\")";
		if (columnType.startsWith("int") || columnType.startsWith("bigint"))
		{
			v += "Integer.parseInt(" + value + "));//整型类型转换";
		}
		else if (columnType.startsWith("varchar") || columnType.startsWith("char"))
		{
			v += value + ");";
		}
		else if (columnType.startsWith("date"))
		{
			v += "dateFormatter.parse(" + value + "));//日期时间类型转换	";
		}
		else
		{
			v += value + ");";
		}
		return v;
		
		//.set${column.name}(request.getParameter("${column.name}"));
	}
	
	public String getControlHtml()
	{
		String columnType = this.column_type.toLowerCase();
		String required = "";
		String maxlength = "";
		if (this.character_maximum_length > 0)
		{
			maxlength = "" + this.character_maximum_length;
		}
		if (this.is_nullable != null && this.is_nullable.toLowerCase().equals("no"))
		{
			required = " class='easyui-validatebox' required='true'";
		}
		if (columnType.startsWith("int") || columnType.startsWith("bigint"))
		{
			if (this.isForeignKey())
			{
				/*return "<select id='cc' name='" + this.getFieldName() + "' class='easyui-combogrid' style='width:200px;'  data-options='panelWidth:450, idField:'" + this.getFieldName() + "',  textField:'name', url:'datagrid_data.json', columns:[[" + 
			"{field:'code',title:'编号',width:60}, " + 
						"{field:'name',title:'名称',width:100}]]></select>";  	*/			
				return "<select class='easyui-combobox' name='" + this.getFieldName() + "' style='width:200px;'" + required + "><option value='1'>外键,请通过ajax方式去调用相应的control方法去获取json</option></select>";
			}
			return "<input name='" + this.getFieldName() + "'  type='text'  class='easyui-numberbox' style='width:200px;'" + required + "></input> ";
			
		}
		else if (columnType.startsWith("varchar") || columnType.startsWith("char"))
		{
			if (this.character_maximum_length > 100)
			{
				return "<textarea name='" + this.getFieldName() + "' style='width:200px;height:60px'" + maxlength + " " + required + "></textarea>"; 
			}
			return "<input name='" + this.getFieldName() + "'  type='text'  style='width:200px;' " + maxlength + " " + required + "></input> ";
		}
		else if (columnType.startsWith("date"))
		{
			return "<input name='" + this.getFieldName() + "' class='easyui-datetimebox'  style='width:200px'" + required + "></input>";
		}
		return "<input name='" + this.getFieldName() + "'  type='text'  style='width:200px;'" + maxlength + " " + required + "></input> ";
	}
	
}
