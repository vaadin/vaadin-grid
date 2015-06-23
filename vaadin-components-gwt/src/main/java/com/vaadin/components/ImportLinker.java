package com.vaadin.components;

import com.google.gwt.core.ext.LinkerContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.linker.ArtifactSet;
import com.google.gwt.core.ext.linker.CompilationResult;
import com.google.gwt.core.linker.SingleScriptLinker;

public class ImportLinker extends SingleScriptLinker {

    @Override
    protected String fillSelectionScriptTemplate(StringBuffer selectionScript,
            TreeLogger logger, LinkerContext context, ArtifactSet artifacts,
            CompilationResult result) throws UnableToCompleteException {
        // No need to compute script base
        return super.fillSelectionScriptTemplate(selectionScript, logger,
                context, artifacts, result).replace("computeScriptBase();", "");
    }

}
