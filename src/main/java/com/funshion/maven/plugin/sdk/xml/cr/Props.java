package com.funshion.maven.plugin.sdk.xml.cr;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "prop" })
@XmlRootElement(name = "Props")
public class Props {

	@XmlElement(name = "Prop")
	protected List<Prop> prop;

	public List<Prop> getProp() {
		if (prop == null) {
			prop = new ArrayList<Prop>();
		}
		return this.prop;
	}
}
