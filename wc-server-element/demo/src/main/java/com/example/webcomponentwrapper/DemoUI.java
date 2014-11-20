package com.example.webcomponentwrapper;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.prototype.webcomponentwrapper.WebComponentUI;
import com.vaadin.prototype.webcomponentwrapper.WebComponentVaadinServlet;
import com.vaadin.prototype.webcomponentwrapper.element.Document;
import com.vaadin.prototype.webcomponentwrapper.element.Element;
import com.vaadin.prototype.webcomponentwrapper.element.Elements;
import com.vaadin.prototype.webcomponentwrapper.element.EventParam;
import com.vaadin.prototype.webcomponentwrapper.element.Import;
import com.vaadin.prototype.webcomponentwrapper.element.Tag;
import com.vaadin.prototype.webcomponentwrapper.element.WebComponentWrapper;
import com.vaadin.prototype.webcomponentwrapper.element.elements.InputElement;
import com.vaadin.prototype.webcomponentwrapper.template.Template;
import com.vaadin.prototype.webcomponentwrapper.template.TemplateInstance;
import com.vaadin.prototype.webcomponentwrapper.template.Templates;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;

@SuppressWarnings("serial")
@Theme("valo")
public class DemoUI extends WebComponentUI {

    private Document root;

    @WebServlet(value = { "/VAADIN/*", "/app/*" }, asyncSupported = true)
    // map to "app" to get static files serving by default servlet
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends WebComponentVaadinServlet {

    }

    @Tag("paper-button")
    @Import("../bower_components/paper-button/paper-button.html")
    // to make imports be served by default servlet
    public static interface PaperButtonElement extends Element {
        void setRaised(boolean b);
    }

    @Tag("core-icon")
    @Import({ "../bower_components/core-icon/core-icon.html",
            "../bower_components/core-icons/core-icons.html",
            "../bower_components/core-icons/av-icons.html",
            "../bower_components/core-icons/communication-icons.html",
            "../bower_components/core-icons/device-icons.html",
            "../bower_components/core-icons/editor-icons.html",
            "../bower_components/core-icons/hardware-icons.html",
            "../bower_components/core-icons/image-icons.html",
            "../bower_components/core-icons/maps-icons.html",
            "../bower_components/core-icons/notification-icons.html",
            "../bower_components/core-icons/social-icons.html", })
    public static interface CoreIconElement extends Element {
        void setIcon(String icon);
    }

    public static interface PaperRpc extends ServerRpc {
        public void click(@EventParam("clientX") double x);
    }

    @Tag("v-hello")
    @Template("hello.html")
    public static interface HelloElement extends Element {
    }

    @Override
    protected void init(VaadinRequest request) {
        CssLayout main = new CssLayout();
        main.setSizeFull();
        setContent(main);

        Label label = new Label("Vaadin label");
        main.addComponent(label);

        WebComponentWrapper wrapper = new WebComponentWrapper();
        main.addComponent(wrapper);
        Document root = wrapper.getRoot();
        setRoot(root);
        root.setAttribute("style", "overflow: visible;");

        PaperButtonElement button = Elements.create(PaperButtonElement.class);
        root.appendChild(button);
        button.setRaised(true);
        button.setInnerHtml("Hello <b>world</b>");

        CoreIconElement icon = Elements.create(CoreIconElement.class);
        icon.setIcon("bug-report");
        button.appendChild(icon);

        button.addEventListener(new PaperRpc() {
            @Override
            public void click(double x) {
                Notification.show("Clicked at " + x);
            }
        });

        
        Elements.registerElement(InputElement.class);
        Element input = Elements.create("input");
        input.setAttribute("type", "date");
        root.appendChild(input);

        TemplateInstance<? extends Element> instance = Templates
                .instantiate("hello.html");
        root.appendChild(instance.getElement());

        TemplateInstance<HelloElement> hello = Templates
                .instantiate(HelloElement.class);
        HelloElement helloElement = hello.getElement();
        root.appendChild(helloElement);

        TemplateInstance<HelloElement> hello2 = Templates
                .instantiate(HelloElement.class);
        HelloElement helloElement2 = hello2.getElement();
        root.appendChild(helloElement2);
        
        InputElement inputElement = hello2.getElementById("myinput");
        //TODO: does not work, needs hooking to the client-side
        inputElement.setMaxLength("10");
        
        assert hello2.getElement().getChildren().size() == 0;
    }

    private void setRoot(Document root) {
        this.root = root;

    }

    public Document getDocumentRoot() {
        return root;
    }
}
