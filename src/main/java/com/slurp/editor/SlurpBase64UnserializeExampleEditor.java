package com.slurp.editor;

import burp.*;

public class SlurpBase64UnserializeExampleEditor implements IMessageEditorTabFactory {
    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;

    public SlurpBase64UnserializeExampleEditor(IBurpExtenderCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public IMessageEditorTab createNewInstance(IMessageEditorController controller, boolean editable) {
        return new SlurpBase64UnserializeExampleTab(controller, editable, callbacks);
    }

}
