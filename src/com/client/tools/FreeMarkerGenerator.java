package com.client.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.util.SystemOutLogger;




import freemarker.log.Logger;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerGenerator{
	
	protected static Logger logger = Logger.getLogger("Generator");
	protected static Configuration configuration;
	public void init() {
		if (configuration == null) {
			try {
				configuration = new Configuration();
				configuration.setDirectoryForTemplateLoading(new File(this
						.getClass()
						.getResource("/resource/templates")
						.getFile()));
				// temp
				configuration.setObjectWrapper(new DefaultObjectWrapper());
				} catch (IOException ex) {
				logger.error(ex.toString());
			}
		}
	}
	
	public void generate(Entity entity, String packageName, String outputPath, String outputfilename,String templatefilename) {		
		init();
		SimpleHash root = new SimpleHash();
		root.put("package",packageName);//temp
		root.put("entity", entity);		
		Date date = new Date(System.currentTimeMillis());
		String ds = "2016-" + (date.getMonth() + 1) + "-" + date.getDate();
		root.put("date", ds);
		try
		{
			Template template = configuration.getTemplate(templatefilename, "UTF-8");
			File directory = new File(outputPath);
			if (!directory.exists())
			{
				directory.mkdirs();
			}
			File file = new File(outputPath + "/" + entity.getName() + outputfilename);
			System.out.println(entity.getName() + outputfilename);
			if (file.exists())
			{
				file.delete();
			}
			FileOutputStream stream = new FileOutputStream(file);
			Writer writer = new OutputStreamWriter(stream, "UTF-8");
			template.process(root, writer);
			writer.flush();			
			stream.close();
			writer.close();			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	public void generate() {
		// TODO Auto-generated method stub
		
	}
}
