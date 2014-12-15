package com.funshion.cooly.replace;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

import com.funshion.cooly.replace.schema.Prop;
import com.funshion.cooly.replace.schema.Props;

@Mojo(name = "replace", defaultPhase = LifecyclePhase.PROCESS_TEST_CLASSES)
public class Replace extends AbstractMojo {

	@Parameter(property = "extName", defaultValue = "ci_tmpl")
	private String extName;

	@Parameter(property = "propsFilename", defaultValue = "ci_props.xml")
	private String propsFilename;

	@Parameter(property = "propPrefix", defaultValue = "${")
	private String propPrefix;

	@Parameter(property = "propSuffix", defaultValue = "}")
	private String propSuffix;

	@Parameter(property = "directoryToOperate", defaultValue = "classes")
	private String directoryToOperate;

	private static final char[] REG_EXP_TRIGGER_CHARS = { '^', '$', '(', ')', '[', ']', '{', '}', '.', '?', '+', '*', '|', '\\' };

	@Parameter(property = "basedir")
	private String basedir;

	@Parameter(property = "project.build.directory")
	private String projectBuildDirectory;

	private Map<String, String> props = new HashMap<String, String>();

	private Pattern verifyReg;

	public void execute() throws MojoExecutionException, MojoFailureException {
		try {
			verifyReg = Pattern.compile(StringUtils.escape(propPrefix, REG_EXP_TRIGGER_CHARS, '\\') + ".*?" + StringUtils.escape(propSuffix, REG_EXP_TRIGGER_CHARS, '\\'));
			extName = "." + extName;
			loadPropsFromXml(basedir + File.separatorChar + propsFilename);
			for (String dir : directoryToOperate.split(",")) {
				if (!StringUtils.isBlank(dir)) {
					dir = projectBuildDirectory + File.separatorChar + dir;
					getLog().info("Scanning directory : " + dir);
					File[] files = new File(dir).listFiles();
					if (files != null) {
						for (File item : files) {
							doReplace(item);
						}
					}
				}
			}
		} catch (Exception e) {
			getLog().warn(e.getMessage());
		}
	}

	private void doReplace(File file) throws Exception {
		if (file.isFile()) {
			String filepath = file.getAbsolutePath();
			if (filepath.endsWith(extName)) {
				String confFilepath = filepath.substring(0, filepath.lastIndexOf('.'));
				String content = loadFileContent(filepath);
				getLog().info("Replace file: " + confFilepath);
				for (String key : props.keySet()) {
					content = StringUtils.replace(content, (propPrefix + key + propSuffix), props.get(key));
				}
				Matcher m = verifyReg.matcher(content);
				StringBuilder buff = new StringBuilder();
				boolean found = m.find();
				if (found) {
					buff.append("Missing property ");
				}
				while (found) {
					buff.append("\"").append(m.group()).append("\" ");
					found = m.find();
				}
				if (buff.length() > 0) {
					buff.append(" in ").append(propsFilename).append(", when handle ").append(confFilepath);
					throw new Exception(buff.toString());
				}
				saveFileContent(confFilepath, content);
				new File(filepath).delete();
			}
		} else if (file.isDirectory()) {
			for (File item : file.listFiles()) {
				doReplace(item);
			}
		}
	}

	private String loadFileContent(String path) throws Exception {
		BufferedReader reader = null;
		StringBuffer buff = new StringBuffer();
		try {
			reader = new BufferedReader(new FileReader(new File(path)));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				buff.append(tempString).append("\n");
			}
			return buff.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					getLog().warn("Fail to close BufferedReader.");
				}
			}
		}
	}

	private void saveFileContent(String path, String content) throws Exception {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new FileWriter(new File(path)));
			writer.write(content);
			writer.flush();
		} catch (Exception e) {
			throw e;
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e1) {
					getLog().warn("Fail to close BufferedWriter.");
				}
			}
		}
	}

	private void loadPropsFromXml(String filepath) throws Exception {
		getLog().info("Load variables from :" + filepath);
		JAXBContext cxt = JAXBContext.newInstance("com.funshion.cooly.replace.schema");
		Unmarshaller unm = cxt.createUnmarshaller();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filepath);
			Props root = (Props) unm.unmarshal(fis);
			for (Prop item : root.getProp()) {
				String value = item.getValue().trim();
				props.put(item.getName(), value);
				getLog().info("    " + item.getName() + " -> \"" + value + "\"");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (Exception e2) {
					getLog().warn("Fail to close FileInputStream.");
				}
			}
		}
	}
}
