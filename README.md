# POM��������
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
				<!-- ��¼������Ϣ���ļ��� -->
				<propsFilename>./ci_props.xml</propsFilename>
				<!-- Ҫ�滻����չ�� -->
				<extName>ci_tmpl</extName>
				<!-- ����ЩĿ¼�µ����ݽ��в����滻���� -->
				<directoryToOperate>classes,test-classes</directoryToOperate>
				<!-- ����ƥ�������ǰ׺ -->
				<propPrefix>${</propPrefix>
				<!-- ����ƥ������ĺ�׺ -->
				<propSuffix>}</propSuffix>
			</configuration>
		</execution>
	</executions>
</plugin>

# ģ���ļ�����

<Props xmlns="http://funshion.com/maven/plugin/cooly/schemas">
	<Prop name="example">zhoumo</Prop>
</Props>