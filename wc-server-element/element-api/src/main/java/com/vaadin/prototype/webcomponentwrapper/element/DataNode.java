package com.vaadin.prototype.webcomponentwrapper.element;

public class DataNode extends NodeImpl {
    

    private String wholeData;

    public DataNode(String wholeData) {
        this.wholeData = wholeData;
    }

    @Override
    public String asHtml() {
        return "";
    }
    
    public String getWholeData() {
        return wholeData;
    }
    
    public void setWholeData(String wholeData) {
        this.wholeData = wholeData;
    }

}
