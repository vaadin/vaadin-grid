package com.example.webcomponentwrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.example.webcomponentwrapper.element.Element;
import com.example.webcomponentwrapper.element.Elements;
import com.example.webcomponentwrapper.element.EventParam;
import com.example.webcomponentwrapper.element.Import;
import com.example.webcomponentwrapper.element.Tag;
import com.example.webcomponentwrapper.element.WebComponentWrapper;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.communication.ServerRpc;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
@Theme("valo")
public class DemoUI extends UI {

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = DemoUI.class)
    public static class Servlet extends VaadinServlet {

        // All this just to include a script in the head in the generated HTML
        @Override
        protected void servletInitialized() throws ServletException {
            super.servletInitialized();

            WebComponentWrapper.initBootsrap(getService());
        }
    }

    @Tag("paper-button")
    @Import("https://www.polymer-project.org/components/paper-button/paper-button.html")
    public interface PaperButtonElement extends Element {
        void setRaised(boolean b);
    }

    public interface PaperRpc extends ServerRpc {
        public void click(@EventParam("clientX") double x);
    }

    @Override
    protected void init(VaadinRequest request) {
        System.out.println("Initing");

        WebComponentWrapper wrapper = new WebComponentWrapper();
        setContent(wrapper);

        PaperButtonElement button = Elements.create(PaperButtonElement.class);
        button.setRaised(true);
        button.setInnerHtml("Hello <b>world 2</b>");

        button.addEventListener(new PaperRpc() {
            @Override
            public void click(double x) {
                Notification.show("Clicked at " + x);
            }
        });

        Element input = Elements.create("input");
        input.setAttribute("type", "date");

        Element root = wrapper.getRoot();
        root.setAttribute("style", "margin: 20px; overflow: visible;");

        root.appendChild(button);
        root.appendChild(input);
    }

}