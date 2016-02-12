package burp;

/**
 * Created by Marc Lebrun @ XMCO.
 */

import com.slurp.SlurpOptionsTab;
import com.slurp.SlurpUtils;
import com.slurp.helper.SlurpHelperTab;

public class BurpExtender implements IBurpExtender
{
    private IBurpExtenderCallbacks callbacks;
    private SlurpOptionsTab optionsTab;
    private SlurpHelperTab helperTab;

    private SlurpUtils utils;

    @Override
    public void registerExtenderCallbacks(IBurpExtenderCallbacks callbacks)
    {
        // keep a reference to our callbacks object
        this.callbacks = callbacks;

        // set plugin name
        callbacks.setExtensionName("Slurp Universal Burp plugin");

        // We grab an instance of SlurpUtils and Initialize it (mandatory)
        utils = SlurpUtils.getInstance();
        utils.initUtils(callbacks);

        // create tabs
        callbacks.issueAlert("Creating Tabs...");
        helperTab = new SlurpHelperTab();
//    optionsTab = new SlurpOptionsTab(callbacks);
        callbacks.issueAlert("Creating Tabs done!");

        // register our message editor tab and menu factories
        callbacks.issueAlert("Registering editors and menus...");
//    callbacks.registerMessageEditorTabFactory(new SlurpBase64UnserializeExampleEditor(callbacks));
//    callbacks.registerContextMenuFactory(new SlurpMenu(callbacks));
        callbacks.issueAlert("Registering editors and menus done!");

    }
}

