package com.vaadin.components;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.CompilationResult;
import com.google.gwt.core.linker.CrossSiteIframeLinker;

/**
 * An extension of the xsiframe linker, which puts JS in an
 * html file able to be imported via the <link rel='import'> tag.
 */
public class ImportLinker extends CrossSiteIframeLinker {

    private static final String IMPORT_FILE_SUFFIX = "-import.html";

    public ImportLinker() {
    }

    @Override
    public ArtifactSet link(TreeLogger logger, LinkerContext context,
            ArtifactSet artifacts, boolean onePermutation)
            throws UnableToCompleteException {

        ArtifactSet ret =  super.link(logger, context, artifacts, onePermutation);
        ArtifactSet toReturn = new ArtifactSet(ret);

        if (!onePermutation) {
            String moduleName = context.getModuleName();
            String js = "<!DOCTYPE  html>\n<html>\n<head>\n";
            if ("false".equals(getStringConfigurationProperty(context, "includeSourceMapUrl", null))) {
                js += ""
                        + "<script>\n"
                        + "$wnd = window; $doc = document;\n"
                        + generateVaadinScript(logger, context, artifacts, null);
                for (CompilationResult result : artifacts.find(CompilationResult.class)) {
                    String strongName = result.getStrongName();
                    js += ""
                            + "\nfunction _" +  strongName  + "($wnd, $doc){\n"
                            + getModulePrefix(logger, context, strongName)
                            + result.getJavaScript()[0] + "\n"
                            + getModuleSuffix2(logger, context, strongName)
                            + "\n}\n";
                }
                js += "</script>\n";
            } else {
                js += "<script src='" + moduleName + ".nocache.js'></script>\n";
            }
            js += "</head>\n<body>\n</body>\n</html>\n";
            System.out.println("Generated import file: " + moduleName + IMPORT_FILE_SUFFIX);
            toReturn.add(emitString(logger, js, moduleName + IMPORT_FILE_SUFFIX, System.currentTimeMillis()));
        }
        return toReturn;
    }

    protected String generateVaadinScript(TreeLogger logger,
            LinkerContext context, ArtifactSet artifacts, CompilationResult result)
            throws UnableToCompleteException {
          String selectionScriptText;
          StringBuffer buffer = readFileToStringBuffer(
                  getSelectionScriptTemplate(logger, context), logger);
          replaceAll(buffer, "__INSTALL_SCRIPT__", ""
                  + "function installScript(filename) {"
                  + " this['_' + filename.replace(/.*([\\dA-F]{32}).cache.js/,'$1')](window, document);"
                  + "}"
                  + "");

          selectionScriptText = fillSelectionScriptTemplate(
              buffer, logger, context, artifacts, result);
          selectionScriptText =
              context.optimizeJavaScript(logger, selectionScriptText);

          return selectionScriptText;
    }
}
