package com.vaadin.prototype.webcomponentwrapper;

import com.vaadin.prototype.webcomponentwrapper.element.Document;
import com.vaadin.ui.UI;

public abstract class UIWithRootDocument extends UI {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4923163596058295077L;

	public abstract Document getDocumentRoot();

}
