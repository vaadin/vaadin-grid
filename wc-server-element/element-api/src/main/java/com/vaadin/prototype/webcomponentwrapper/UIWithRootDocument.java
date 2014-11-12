package com.vaadin.prototype.webcomponentwrapper;

import com.vaadin.prototype.webcomponentwrapper.element.Document;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public abstract class UIWithRootDocument extends UI {

    /**
	 * 
	 */
    private static final long serialVersionUID = 4923163596058295077L;

    public abstract Document getDocumentRoot();

    @Override
    protected void init(VaadinRequest request) {

    }

}
