package ${package}.poco;

/**
 * ${entity.chineseName} 实体类的常量
 *@author ZhangRuiLong
 */
public class ${entity.name}Poco
{
   /**
    * 实体中文名
    */
   public static String NAME = "${entity.chineseName}";
   /**
    * 实体表名
    */
   public static String TABLE = "${entity.name}";
   /**
    * 实体主键
    */
   public static String[] KEYCOLUMN = {"${entity.keyColumn.fieldName}"};
   /**
    * 实体中文字段
    */
   public static String[] CHINESENAME = {
   		"${entity.keyColumn.chineseName}",
	<#list entity.columns as column>
	 	"${column.chineseName}",
	</#list>
	};
	/**
	 * 实体英文字段
	 */
   public static final String[] FIELDNAME = {
   		"${entity.keyColumn.fieldName}",
	<#list entity.columns as column>
	 	"${column.fieldName}",
	</#list>
   };
   /**
    * 实体排序
    */
   public static final String ORDER = " ${entity.keyColumn.fieldName} desc ";
   /**
	 * 要模糊查询字段
	 */
   public static final String[] QUERYFIELDNAME = {
   		"${entity.keyColumn.fieldName}",
	<#list entity.columns as column>
	 	"${column.fieldName}",
	</#list>
   };
}

