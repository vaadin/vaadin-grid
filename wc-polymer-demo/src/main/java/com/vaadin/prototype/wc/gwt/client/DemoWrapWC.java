package com.vaadin.prototype.wc.gwt.client;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.$$;
import static com.google.gwt.query.client.GQuery.Widgets;

import java.util.Arrays;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.EventListener;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Panel;
import com.vaadin.prototype.wc.gwt.client.components.ChessBoard;
import com.vaadin.prototype.wc.gwt.client.components.CoreCollapse;
import com.vaadin.prototype.wc.gwt.client.components.CoreDrawerPanel;
import com.vaadin.prototype.wc.gwt.client.components.CoreIcon;
import com.vaadin.prototype.wc.gwt.client.components.CoreIconButton;
import com.vaadin.prototype.wc.gwt.client.components.CoreIcons;
import com.vaadin.prototype.wc.gwt.client.components.CoreItem;
import com.vaadin.prototype.wc.gwt.client.components.CoreMenu;
import com.vaadin.prototype.wc.gwt.client.components.CoreMenuButton;
import com.vaadin.prototype.wc.gwt.client.components.CoreSplitter;
import com.vaadin.prototype.wc.gwt.client.components.CoreSubmenu;
import com.vaadin.prototype.wc.gwt.client.components.CoreToolbar;
import com.vaadin.prototype.wc.gwt.client.components.CoreTooltip;
import com.vaadin.prototype.wc.gwt.client.components.CoreTransition;
import com.vaadin.prototype.wc.gwt.client.components.CoreTransitionCss;
import com.vaadin.prototype.wc.gwt.client.components.Icon;
import com.vaadin.prototype.wc.gwt.client.components.PaperButton;
import com.vaadin.prototype.wc.gwt.client.components.PaperDialog;
import com.vaadin.prototype.wc.gwt.client.components.PaperFab;
import com.vaadin.prototype.wc.gwt.client.components.PaperRipple;
import com.vaadin.prototype.wc.gwt.client.components.PaperShadow;
import com.vaadin.prototype.wc.gwt.client.components.PaperSlider;
import com.vaadin.prototype.wc.gwt.client.components.PaperToast;
import com.vaadin.prototype.wc.gwt.client.html.HTMLElement;
import com.vaadin.prototype.wc.gwt.client.util.Elements;
import com.vaadin.prototype.wc.gwt.client.util.WC;

/**
 */
public class DemoWrapWC implements EntryPoint {

    HTMLElement left = WC.create("div");
    HTMLElement right = WC.create("div");
    PaperDialog dialog;
    Panel gwtPanel;
    PaperToast toast;

    LoremIpsum lorem = new LoremIpsum();

    public void onModuleLoad() {
        demoCreateCoreDrawer();
        demoCoreToolbar();
        demoCoreCollapse();
        demoCoreIcon();
        demoPaperSlider();
        demoCoreMenu();
        demoShadow();
        demoSpliter();
        demoChessBoard();
        demoWidgetPanel();
        demoWidgetPaperToggleButton();
    }

    private native void export(String name, Object o) /*-{
        $wnd[name] = o;
    }-*/;

    private void demoCreateCoreDrawer() {
        WC.load(CoreIcon.class);

        final CoreDrawerPanel drawer = WC.create(CoreDrawerPanel.class);
        drawer.setAttribute("rightDrawer", "");
        left.setAttribute("drawer", "");
        left.style().backgroundColor("#FFC9B5");
        right.setAttribute("main", "");
        right.style().backgroundColor("#4F7DC9");

        drawer.appendChild(left);
        drawer.appendChild(right);
        Elements.body.appendChild(drawer);

        final PaperFab fab = WC.create(PaperFab.class);
        fab.icon(Icon.MORE_HORIZ);
        right.appendChild(fab);
        drawer.addEventListener("core-responsive-change", new EventListener() {
            public void onBrowserEvent(Event event) {
                if (drawer.narrow()) {
                    $(fab).show();
                } else {
                    $(fab).hide();
                }
            }
        });
        if (!drawer.narrow()) {
            $(fab).hide();
        }
        fab.addEventListener("click", new EventListener() {
            public void onBrowserEvent(Event event) {
                drawer.togglePanel();
            }
        });
        fab.style().position("absolute").right("5px").top("5px");
    }

    private void demoCoreCollapse() {
        final CoreCollapse collapse = WC.create(CoreCollapse.class);
        collapse.innerHTML(lorem.getParagraphs());

        PaperButton button = WC.create(PaperButton.class);
        button.icon(Icon.ARROW_DROP_DOWN_CIRCLE).label("toogle")
                .raisedButton(true);
        button.addEventListener("click", new EventListener() {
            public void onBrowserEvent(Event event) {
                collapse.toggle();
            }
        });

        HTMLElement section = WC.create("section");
        section.appendChild(button);
        section.appendChild(collapse);

        right.appendChild(section);
        section.style().background("#E2E1A5").padding("10px");
        collapse.style().background("#CFA0E9").padding("10px");
    }

    private void demoPaperSlider() {
        final PaperSlider slider = WC.create(PaperSlider.class);
        final HTMLElement span = WC.create("span");
        slider.addEventListener("change", new EventListener() {
            public void onBrowserEvent(Event event) {
                span.innerHTML("" + slider.value());
            }
        });
        slider.pin(true).max(80).value(20);

        PaperSlider slider2 = WC.create(PaperSlider.class);
        slider2.max(100);
        slider2.step(10);
        slider2.snaps(true);
        slider2.secondaryProgress(80);
        left.appendChild(slider);
        left.appendChild(span);
        left.appendChild(slider2);
    }

    private void demoCoreIcon() {
        final PaperButton button = WC.create(PaperButton.class);

        button.icon(Icon.POLYMER).label(Icon.POLYMER).raisedButton(true)
                .style().background("#8BACE2");
        button.addEventListener("click", new EventListener() {
            int cont = 0;

            public void onBrowserEvent(Event event) {
                String icon = Icon.ALL[cont++ % Icon.ALL.length];
                button.icon(icon).label(icon);
            }
        });

        CoreTooltip tip = WC.create(CoreTooltip.class);
        tip.label("Switch the icon");
        tip.appendChild(button);
        tip.setAttribute("large", "");

        right.appendChild(tip);
    }

    private void demoShadow() {
        HTMLElement div = WC.create("div");
        div.style().margin("10px").padding("10px").backgroundColor("white")
                .maxWidth("70%");
        div.innerHTML(lorem.getWords(40));
        PaperShadow shadow = WC.create(PaperShadow.class).z(2);
        div.appendChild(shadow);
        right.appendChild(div);

        PaperRipple riple = WC.create(PaperRipple.class);
        riple.setAttribute("fit", "");
        div.appendChild(riple);
    }

    private void demoCoreMenu() {
        CoreMenu menu = WC.create(CoreMenu.class);
        CoreSubmenu submenu1 = WC.create(CoreSubmenu.class);
        submenu1.icon(Icon.SETTINGS).label("Settings");
        CoreSubmenu submenu2 = WC.create(CoreSubmenu.class);
        submenu2.icon(Icon.FAVORITE).label("Favorite");

        CoreItem item = WC.create(CoreItem.class);
        item.icon(Icon.SOCIAL_CAKE).label("Like");
        submenu1.appendChild(item);
        item = WC.create(CoreItem.class);
        item.icon(Icon.ANDROID).label("Android");
        submenu1.appendChild(item);

        item = WC.create(CoreItem.class);
        item.icon(Icon.APPS).label("Apps");
        submenu2.appendChild(item);
        item = WC.create(CoreItem.class);
        item.icon(Icon.AV_MIC).label("Sound");
        submenu2.appendChild(item);

        menu.appendChild(submenu1);
        menu.appendChild(submenu2);

        $(menu).addClass("paper-shadow-top-z-3");

        left.appendChild(menu);
        menu.style().backgroundColor("antiquewhite");
    }

    private void demoCoreMenuHtml() {
        WC.load(CoreIcon.class, CoreIcons.class, CoreMenu.class,
                CoreSubmenu.class, CoreItem.class);
        String html = "<core-menu selected='0'>"
                + "      <core-submenu icon='settings' label='Topics'>"
                + "        <core-item label='Topic 1'></core-item>"
                + "        <core-item label='Topic 2'></core-item>"
                + "      </core-submenu>"
                + "      <core-submenu icon='favorite' label='Favorites'>"
                + "        <core-item icon='settings' label='Favorite 1'></core-item>"
                + "        <core-item label='Favorite 2'></core-item>"
                + "        <core-item label='Favorite 3'></core-item>"
                + "      </core-submenu>" + "    </core-menu>";
        $(left).append(html);
    }

    private void demoCoreToolbar() {

        WC.load(CoreIcon.class, CoreSubmenu.class, CoreItem.class);
        final CoreToolbar toolbar = WC.create(CoreToolbar.class);
        final CoreMenuButton menu = WC.create(CoreMenuButton.class);
        menu.icon(Icon.MENU);

        final List<String> items = Arrays.asList(Icon.ARCHIVE, Icon.REPLY,
                Icon.AV_MOVIE, Icon.AV_VIDEO_YOUTUBE, Icon.CHECK_BOX_OUTLINE,
                Icon.COMMUNICATION_GMAIL);
        for (int i = 0; i < items.size(); i++) {
            CoreItem item = WC.create(CoreItem.class).icon(items.get(i))
                    .label("Item " + i);
            menu.appendChild(item);
        }

        toast = WC.create(PaperToast.class);
        Elements.body.appendChild(toast);

        CoreIconButton refresh = WC.create(CoreIconButton.class);
        refresh.icon(Icon.REFRESH);
        CoreIconButton add = WC.create(CoreIconButton.class);
        add.icon(Icon.AV_WEB);
        final HTMLElement div = WC.create("div");
        div.innerHTML("Demo");
        div.setAttribute("flex", "");
        toolbar.appendChild(menu);
        toolbar.appendChild(div);
        toolbar.appendChild(refresh);
        toolbar.appendChild(add);
        menu.addEventListener("core-select", new EventListener() {
            public void onBrowserEvent(Event event) {
                toast.text("Itemaa: " + menu.selected() + " selected").show();
            }
        });

        left.appendChild(toolbar);
        $(toolbar)
                .css($$("bottom: 0px, color: white, fill: white, background: #7D25AC"));

        // FIXME: why is the shadowRoot available after a while?
        new Timer() {
            @Override
            public void run() {
                $(menu.shadowRoot()).find("#dropdown").css("background",
                        "#6EA754");
            }
        }.schedule(2000);

        demoDialog();
        add.addEventListener("click", new EventListener() {
            public void onBrowserEvent(Event event) {
                dialog.toggle();
            }
        });
        refresh.addEventListener("click", new EventListener() {
            public void onBrowserEvent(Event event) {
                toast.text("Nothing to do yet").show();
            }
        });
    }

    private void demoDialog() {
        WC.load(CoreTransition.class, CoreTransitionCss.class);
        dialog = WC.create(PaperDialog.class);
        dialog.heading("Dialog").transition("core-transition-bottom");

        HTMLElement div = WC.create("div");
        div.innerHTML(lorem.getParagraphs(1));
        dialog.appendChild(div);

        PaperButton more = WC.create(PaperButton.class).label("More Info ...");
        more.setAttribute("dismissive", "");
        dialog.appendChild(more);
        PaperButton decline = WC.create(PaperButton.class).label("Decline");
        decline.setAttribute("affirmative", "");
        dialog.appendChild(decline);
        PaperButton accept = WC.create(PaperButton.class).label("Accept");
        accept.setAttribute("affirmative", "");
        accept.setAttribute("autofocus", "");
        dialog.appendChild(accept);
        Elements.body.appendChild(dialog);
    }

    private void demoSpliter() {
        HTMLElement container = WC.create("div");
        container.setAttribute("horizontal", "");
        container.setAttribute("layout", "");
        container.style().backgroundColor("#ECD4D8").margin("10px")
                .padding("10px").height("300px");
        $(container).addClass("paper-shadow-top-z-5");

        HTMLElement div1 = WC.create("div");
        div1.innerHTML(lorem.getParagraphs(1));
        CoreSplitter spl1 = WC.create(CoreSplitter.class);
        spl1.direction("left");
        HTMLElement div2 = WC.create("div");
        div2.setAttribute("flex", "");
        div2.setAttribute("vertical", "");
        div2.setAttribute("layout", "");
        div2.style().minWidth("200px");

        container.appendChild(div1);
        container.appendChild(spl1);
        container.appendChild(div2);

        HTMLElement div3 = WC.create("div");
        div3.innerHTML(lorem.getParagraphs(1));
        div3.style().backgroundColor("#D8F7A2");
        CoreSplitter spl2 = WC.create(CoreSplitter.class);
        spl2.minSize("100px");
        spl2.direction("up");

        HTMLElement div4 = WC.create("div");
        div4.innerHTML(lorem.getParagraphs(1));
        div4.setAttribute("flex", "");

        div2.appendChild(div3);
        div2.appendChild(spl2);
        div2.appendChild(div4);

        right.appendChild(container);
    }

    private void demoChessBoard() {
        final ChessBoard chess = WC.create(ChessBoard.class);
        chess.innerHTML("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
        chess.setAttribute("unicode", "");
        chess.setAttribute("frame", "");
        left.appendChild(chess);
        HTMLElement b = WC.create("button");
        b.innerHTML("move");
        b.addEventListener("click", new EventListener() {
            int c = 0;

            public void onBrowserEvent(Event event) {
                chess.fen(c++ % 2 == 0 ? "k7/8/8/8/8/8/8/7K"
                        : "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR");
            }
        });
        left.appendChild(b);
    }

    private void demoWidgetPanel() {
        Panel rightPanel = $(right).as(Widgets).panel().widget();
        gwtPanel = new FlowPanel();
        rightPanel.add(gwtPanel);
        gwtPanel.getElement().getStyle().setBackgroundColor("white");
        gwtPanel.getElement().getStyle().setMargin(10, Unit.PX);
        gwtPanel.getElement().getStyle().setPadding(8, Unit.PX);
        gwtPanel.add(new HTML("<h2>This is a gwt widget panel.</h2>"));
    }

    private void demoWidgetPaperToggleButton() {
        // gwtPanel.add(new
        // Label("This is a <paper-toggle-button> promoted to widget:"));
        // final PaperToggleButtonWidget b = new PaperToggleButtonWidget();
        // gwtPanel.add(b);
        // b.addChangeHandler(new EventListener() {
        // public void onBrowserEvent(Event event) {
        // toast.text("Toggled, enabled=" + b.checked()).show();
        // }
        // });
    }
}
