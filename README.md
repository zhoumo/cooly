### POM引用样例
###
	<plugin>
		<groupId>com.funshion.maven.plugin</groupId>
		<artifactId>cooly</artifactId>
		<version>0.1</version>
		<executions>
			<execution>
				<goals>
					<goal>replace</goal>
				</goals>
				<phase>test</phase>
				<configuration>
					<!-- 记录配置信息的文件名 -->
					<propsFilename>ci_props.xml</propsFilename>
					<!-- 要替换的扩展名 -->
					<extName>ci_tmpl</extName>
					<!-- 对哪些目录下的内容进行查找替换操作 -->
					<directoryToOperate>classes,test-classes</directoryToOperate>
					<!-- 用于匹配变量的前缀 -->
					<propPrefix>${</propPrefix>
					<!-- 用于匹配变量的后缀 -->
					<propSuffix>}</propSuffix>
					<!-- 对哪些目录下的内容进行检查校验 -->
					<directoryToCheck></directoryToCheck>
					<!-- 最小Jar包数量限制 -->
					<minJarNumber></minJarNumber>
					<!-- 最大Jar包数量限制 -->
					<maxJarNumber></maxJarNumber>
					<!-- 匹配存在的Jar包 -->
					<existJars></existJars>
				</configuration>
			</execution>
		</executions>
	</plugin>

### 模板文件样例
###
	<Props xmlns="http://funshion.com/maven/plugin/cooly/schemas">
		<Prop name="example">zhoumo</Prop>
	</Props>
