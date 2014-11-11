package com.vaadin.prototype.webcomponentwrapper.template;

import com.vaadin.prototype.webcomponentwrapper.element.Element;

class SimpleTemplateDefinitionImpl implements TemplateDefinition<Element> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -904312953292456327L;
	private String innerHTML;
	private String templateId;
	
	public SimpleTemplateDefinitionImpl(String templateId, String innerHTML) {
		this.templateId = templateId;
		this.innerHTML = innerHTML;
	}

	@Override
	public String getId() {
		return templateId;
	}

	@Override
	public String getInnerHTML() {
		return innerHTML;
	}


	@Override
	public TemplateInstance<Element> instantiate() {
		return new SimpleTemplateInstance(getId());
	}

}
