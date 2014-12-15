package com.funshion.cooly.check;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.StringUtils;

@Mojo(name = "check-jar", defaultPhase = LifecyclePhase.PROCESS_TEST_CLASSES)
public class CheckJar extends AbstractMojo {

	@Parameter(property = "basedir")
	private String basedir;

	@Parameter(property = "directoryToCheck")
	private String directoryToCheck;

	@Parameter(property = "minJarNumber")
	private String minJarNumber;

	@Parameter(property = "maxJarNumber")
	private String maxJarNumber;

	@Parameter(property = "existJars")
	private String existJars;

	public void execute() throws MojoExecutionException, MojoFailureException {
		File target = new File(basedir + File.separatorChar + directoryToCheck);
		getLog().info("check path: " + target.getAbsolutePath());
		if (target.listFiles() == null) {
			return;
		}
		if (!StringUtils.isEmpty(minJarNumber) && target.listFiles().length < Integer.parseInt(minJarNumber)) {
			throw new MojoExecutionException("Less than the min of jars");
		}
		if (!StringUtils.isEmpty(maxJarNumber) && target.listFiles().length > Integer.parseInt(maxJarNumber)) {
			throw new MojoExecutionException("Greater than the max of jars");
		}
		if (!StringUtils.isEmpty(existJars)) {
			return;
		}
		List<String> jarList = new ArrayList<String>();
		for (File file : target.listFiles()) {
			jarList.add(file.getName().substring(file.getName().lastIndexOf(File.separatorChar) + 1));
		}
		for (String existJar : existJars.split(",")) {
			if (!jarList.contains(existJar)) {
				throw new MojoExecutionException("Can not find: " + existJar);
			}
		}
	}
}
