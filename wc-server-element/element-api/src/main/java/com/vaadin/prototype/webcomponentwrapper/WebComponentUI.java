package com.vaadin.prototype.webcomponentwrapper;

import java.util.Map;

import com.vaadin.prototype.webcomponentwrapper.element.Document;
import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.template.TemplateDefinition;
import com.vaadin.ui.UI;

public abstract class WebComponentUI extends UI implements
        WebComponentUISupport {

    /**
     * 
     */
    private static final long serialVersionUID = 4923163596058295077L;

    private Map<String, TemplateDefinition<? extends Element>> templateDefinitions = new TemplateDefinitionsMap();

    @Override
    public abstract Document getDocumentRoot();

    @Override
    public TemplateDefinition<? extends Element> getTemplateDefinition(
            String tag) {
        return templateDefinitions.get(tag);
    }
    
    @Override
    public void addTemplateDefinition(TemplateDefinition<? extends Element> td) {
        String tag = td.getTag();
        templateDefinitions.put(tag, td);
    }
    

    public static WebComponentUI getCurrent() {
        return (WebComponentUI) UI.getCurrent();
    }
    
    
}
