package com.vaadin.prototype.webcomponentwrapper.element;

import com.vaadin.annotations.JavaScript;
import com.vaadin.server.BootstrapFragmentResponse;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.AbstractJavaScriptComponent;

import elemental.json.JsonValue;
import elemental.json.impl.JsonUtil;

@JavaScript("elementui.js")
public class WebComponentWrapper extends AbstractJavaScriptComponent {
    /**
	 * 
	 */
    private static final long serialVersionUID = -8690500862374495720L;
    private final Document document = new Document(this);

    public WebComponentWrapper() {
        addFunction("callback", arguments -> document.handleCallback(arguments));
    }

    public Document getRoot() {
        return document;
    }

    protected void addImport(String url) {
        document.addImport(url);
    }

    @Override
    public void beforeClientResponse(boolean initial) {
        JsonValue payload = document.flushPendingCommands();

        String query = getUI().getPage().getLocation().getQuery();
        if (query != null && query.contains("debug")) {
            System.out.println(JsonUtil.stringify(payload));
            System.out.println(document.asHtml());
        }

        callFunction("run", payload);

        super.beforeClientResponse(initial);
    }

    public static void initBootstrap(VaadinService service) {
        BootstrapListener l = new BootstrapListener() {
            /**
             * 
             */
            private static final long serialVersionUID = 6175411499678347549L;

            @Override
            public void modifyBootstrapPage(BootstrapPageResponse response) {
                org.jsoup.nodes.Element head = response.getDocument().head();
                head.appendElement("script")
                        .attr("src", "../bower_components/platform/platform.js")
                        .attr("type", "text/javascript");
                head.appendElement("link")
                        .attr("rel", "import")
                        .attr("href",
                                "../bower_components/polymer/polymer.html");
            }

            @Override
            public void modifyBootstrapFragment(
                    BootstrapFragmentResponse response) {
                // Nothing to do here
            }
        };

        service.addSessionInitListener(event -> {
            VaadinSession session = event.getSession();
            session.addBootstrapListener(l);
        });
    }

}
