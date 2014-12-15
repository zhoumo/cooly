package com.funshion.cooly.replace.schema;

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
