package com.example.webcomponentwrapper.element;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import net.sf.cglib.proxy.CallbackHelper;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.proxy.NoOp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Elements {

    private static final Map<String, Class<? extends Element>> registeredElements = new ConcurrentHashMap<>();

    public static <T extends Element> void registerElement(Class<T> type) {
        registeredElements.put(getElementTag(type), type);
    }

    public static Element create(String tag) {
        Class<? extends Element> type = registeredElements.get(tag);
        if (type == null) {
            return new ElementImpl(tag);
        } else {
            return create(type);
        }
    }

    public static <T extends Element> T create(final Class<T> type) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ElementImpl.class);
        enhancer.setInterfaces(new Class[] { type });
        enhancer.setClassLoader(Elements.class.getClassLoader());

        MethodInterceptor methodInterceptor = new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args,
                    MethodProxy proxy) throws Throwable {

                ElementImpl element = (ElementImpl) obj;
                String name = method.getName();

                if ((name.startsWith("get") || name.startsWith("is"))
                        && method.getParameterCount() == 0) {
                    name = getPropertyName(name);

                    if (method.getReturnType() == String.class) {
                        return element.getAttribute(name);
                    } else if (method.getReturnType() == boolean.class) {
                        return element.hasAttribute(name);
                    }
                } else if (name.startsWith("set")
                        && method.getParameterCount() == 1) {
                    name = getPropertyName(name);
                    Class<?> type = method.getParameterTypes()[0];

                    if (type == String.class) {
                        element.setAttribute(name, (String) args[0]);
                        return null;
                    } else if (type == boolean.class) {
                        element.setAttribute(name,
                                ((Boolean) args[0]).booleanValue());
                        return null;
                    }
                }

                throw new RuntimeException("Unsupported method: " + method);
            }
        };

        CallbackHelper callbackHelper = new CallbackHelper(ElementImpl.class,
                new Class[] { type }) {
            @Override
            protected Object getCallback(Method method) {
                if (method.isDefault()
                        || !Modifier.isAbstract(method.getModifiers())) {
                    return NoOp.INSTANCE;
                } else {
                    return methodInterceptor;
                }
            }
        };
        enhancer.setCallbackFilter(callbackHelper);
        enhancer.setCallbacks(callbackHelper.getCallbacks());

        String tagName = getElementTag(type);
        Object instance = enhancer.create(new Class[] { String.class },
                new Object[] { tagName });

        return type.cast(instance);
    }

    private static String getElementTag(final Class<?> type) {
        Tag tag = type.getAnnotation(Tag.class);
        String value = tag.value();
        return value;
    }

    static String getPropertyName(String name) {
        name = name.replaceAll("^(get|set|is)", "");
        // TODO locale for lower case
        name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
        return name;
    }

    public static TextNode createText(String text) {
        return new TextNode(text);
    }

    public static List<Node> parse(String html) {
        Document parse = Jsoup.parseBodyFragment(html);

        // Convert parsed soup nodes to our nodes
        return parse.body().childNodes().stream().map(Elements::buildNode)
                .collect(Collectors.toList());
    }

    private static Node buildNode(org.jsoup.nodes.Node soupNode) {
        if (soupNode instanceof org.jsoup.nodes.TextNode) {
            return Elements.createText(((org.jsoup.nodes.TextNode) soupNode)
                    .text());
        } else if (soupNode instanceof org.jsoup.nodes.Element) {
            org.jsoup.nodes.Element soupElement = (org.jsoup.nodes.Element) soupNode;

            Element element = Elements.create(soupElement.tagName());

            // Copy attribute values
            soupElement
                    .attributes()
                    .asList()
                    .forEach(
                            a -> element.setAttribute(a.getKey(), a.getValue()));

            // Adopt children
            soupElement.childNodes().forEach(
                    n -> element.appendChild(buildNode(n)));

            return element;
        } else {
            throw new RuntimeException(soupNode.toString());
        }
    }
}
