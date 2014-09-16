package com.funshion.maven.plugin.sdk.xml.cr;

import javax.xml.bind.annotation.XmlRegistry;

@XmlRegistry
public class ObjectFactory {

	public ObjectFactory() {
	}

	public Prop createProp() {
		return new Prop();
	}

	public Props createProps() {
		return new Props();
	}
}
