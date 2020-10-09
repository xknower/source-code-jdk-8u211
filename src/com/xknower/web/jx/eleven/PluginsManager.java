package com.xknower.web.jx.eleven;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.PluginFilter;
import com.teamdev.jxbrowser.chromium.PluginInfo;
import com.teamdev.jxbrowser.chromium.PluginManager;

import java.util.List;

public class PluginsManager {
    public static void main(String[] args) {
        Browser browser = new Browser();

        // To get information about all installed and available plugins you can use the PluginManager.getPluginsInfo() method:
        PluginManager pluginManager = browser.getPluginManager();
        List<PluginInfo> pluginsList = pluginManager.getPluginsInfo();
        for (PluginInfo plugin : pluginsList) {
            System.out.println("Plugin Name: " + plugin.getName());
        }

        // To enable/disable specified plugin register your own PluginFilter implementation:
        pluginManager.setPluginFilter(new PluginFilter() {
            @Override
            public boolean isPluginAllowed(PluginInfo pluginInfo) {
                // Allow only PDF plugin
                return pluginInfo.getMimeTypes().contains("application/pdf");
            }
        });
    }

}
