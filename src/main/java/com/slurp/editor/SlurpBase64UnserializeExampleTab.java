package com.slurp.editor;

import burp.*;

import java.awt.Component;

public class SlurpBase64UnserializeExampleTab implements IMessageEditorTab {
    private boolean editable;
    private ITextEditor txtInput;
    private byte[] currentMessage;
    private IBurpExtenderCallbacks callbacks;
    private IExtensionHelpers helpers;

    public SlurpBase64UnserializeExampleTab(IMessageEditorController controller, boolean editable, final IBurpExtenderCallbacks callbacks)
    {
        this.callbacks = callbacks;
        this.helpers = callbacks.getHelpers();
        this.editable = editable;
        // create an instance of Burp's text editor, to display our deserialized data
        txtInput = callbacks.createTextEditor();
        txtInput.setEditable(editable);
    }

    //
    // implement IMessageEditorTab
    //

    @Override
    public String getTabCaption()
    {
        return "Serialized input";
    }

    @Override
    public Component getUiComponent()
    {
        return txtInput.getComponent();
    }

    @Override
    public boolean isEnabled(byte[] content, boolean isRequest)
    {
        // enable this tab for requests containing a data parameter
        return isRequest && null != helpers.getRequestParameter(content, "data");
    }

    @Override
    public void setMessage(byte[] content, boolean isRequest)
    {
        if (content == null)
        {
            // clear our display
            txtInput.setText(null);
            txtInput.setEditable(false);
        }
        else
        {
            // retrieve the data parameter
            IParameter parameter = helpers.getRequestParameter(content, "data");

            // deserialize the parameter value
            txtInput.setText(helpers.base64Decode(helpers.urlDecode(parameter.getValue())));
            txtInput.setEditable(editable);
        }

        // remember the displayed content
        currentMessage = content;
    }

    @Override
    public byte[] getMessage()
    {
        // determine whether the user modified the deserialized data
        if (txtInput.isTextModified())
        {
            // reserialize the data
            byte[] text = txtInput.getText();
            String input = helpers.urlEncode(helpers.base64Encode(text));

            // update the request with the new parameter value
            return helpers.updateParameter(currentMessage, helpers.buildParameter("data", input, IParameter.PARAM_BODY));
        }
        else return currentMessage;
    }

    @Override
    public boolean isModified()
    {
        return txtInput.isTextModified();
    }

    @Override
    public byte[] getSelectedData()
    {
        return txtInput.getSelectedText();
    }
}
