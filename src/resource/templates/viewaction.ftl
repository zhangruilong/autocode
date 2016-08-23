package ${package}.action;

import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ${package}.pojo.${entity.name};
import ${package}.poco.${entity.name}Poco;
import com.system.tools.CommonConst;
import com.system.tools.base.BaseActionDao;
import com.system.tools.pojo.Fileinfo;
import com.system.tools.pojo.Queryinfo;
import com.system.tools.util.CommonUtil;
import com.system.tools.util.FileUtil;
import com.system.tools.pojo.Pageinfo;

/**
 * ${entity.chineseName} 逻辑层
 *@author ZhangRuiLong
 */
public class ${entity.name}Action extends BaseActionDao {
	public String result = CommonConst.FAILURE;
	public ArrayList<${entity.name}> cuss = null;
	public Type TYPE = new TypeToken<ArrayList<${entity.name}>>() {}.getType();

	//导出
	public void expAll(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Queryinfo queryinfo = getQueryinfo(request, ${entity.name}.class, ${entity.name}Poco.QUERYFIELDNAME, ${entity.name}Poco.ORDER, TYPE);
		cuss = (ArrayList<${entity.name}>) selAll(queryinfo);
		FileUtil.expExcel(response,cuss,${entity.name}Poco.CHINESENAME,${entity.name}Poco.NAME);
	}
	//查询所有
	public void selAll(HttpServletRequest request, HttpServletResponse response){
		Queryinfo queryinfo = getQueryinfo(request, ${entity.name}.class, ${entity.name}Poco.QUERYFIELDNAME, ${entity.name}Poco.ORDER, TYPE);
		Pageinfo pageinfo = new Pageinfo(0, selAll(queryinfo));
		result = CommonConst.GSON.toJson(pageinfo);
		responsePW(response, result);
	}
	//分页查询
	public void selQuery(HttpServletRequest request, HttpServletResponse response){
		Queryinfo queryinfo = getQueryinfo(request, ${entity.name}.class, ${entity.name}Poco.QUERYFIELDNAME, ${entity.name}Poco.ORDER, TYPE);
		Pageinfo pageinfo = new Pageinfo(getTotal(queryinfo), selQuery(queryinfo));
		result = CommonConst.GSON.toJson(pageinfo);
		responsePW(response, result);
	}
}
