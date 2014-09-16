# HOW TO USE
<plugin>
	<groupId>com.funshion.maven.plugin</groupId>
	<artifactId>cooly</artifactId>
	<version>0.1</version>
	<executions>
		<execution>
			<goals>
				<goal>replace</goal>
			</goals>
			<!-- 在 test 阶段 -->
			<phase>test</phase>
			<configuration>
				<!-- 记录配置信息的文件名 -->
				<propsFilename>./ci_props.xml</propsFilename>
				<!-- 要替换的扩展名 -->
				<extName>ci_tmpl</extName>
				<!-- 对哪些目录下的内容进行查找替换操作 -->
				<directoryToOperate>classes,test-classes</directoryToOperate>
				<!-- 用于匹配变量的前缀 -->
				<propPrefix>${</propPrefix>
				<!-- 用于匹配变量的后缀 -->
				<propSuffix>}</propSuffix>
			</configuration>
		</execution>
	</executions>
</plugin>