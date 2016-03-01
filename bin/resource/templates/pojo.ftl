package ${package}.pojo;

import java.sql.Date;
/**
 * ${entity.chineseName} 实体类
 *@author ZhangRuiLong
 */
public class ${entity.name}
{
   /**
    * ${entity.keyColumn.chineseName},主键
    */
   private ${entity.keyColumn.dataType} ${entity.keyColumn.fieldName}; 
<#list entity.columns as column>
   /**
    * ${column.chineseName}
    */
   private ${column.dataType} ${column.fieldName};   
</#list>
    //属性方法	    
     /**
	 *设置主键"${entity.keyColumn.chineseName}"属性
	 *@param ${entity.keyColumn.fieldName} 实体的${entity.keyColumn.name}属性
	 */
	public void set${entity.keyColumn.name}(${entity.keyColumn.dataType} ${entity.keyColumn.fieldName})
	{
		this.${entity.keyColumn.fieldName} = ${entity.keyColumn.fieldName};
	}
	
	/**
	 *获取主键"${entity.keyColumn.chineseName}"属性
	 */
	public ${entity.keyColumn.dataType} get${entity.keyColumn.name}()
	{
		return this.${entity.keyColumn.fieldName};
	}
<#list entity.columns as column>		

	/**
	 *设置"${column.chineseName}"属性
	 *@param ${column.fieldName} 实体的${column.name}属性
	 */
	public void set${column.name}(${column.dataType} ${column.fieldName})
	{
		this.${column.fieldName} = ${column.fieldName};
	}
	
	/**
	 *获取"${column.chineseName}"属性
	 */
	public ${column.dataType} get${column.name}()
	{
		return this.${column.fieldName};
	}	   
	</#list>
	public ${entity.name}() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ${entity.name}(
		${entity.keyColumn.dataType} ${entity.keyColumn.fieldName}
	 <#list entity.columns as column>
	 	,${column.dataType} ${column.fieldName}
	 </#list>
		 ){
		super();
		this.${entity.keyColumn.fieldName} = ${entity.keyColumn.fieldName};
	<#list entity.columns as column>
	 	this.${column.fieldName} = ${column.fieldName};
	</#list>
	}
}

