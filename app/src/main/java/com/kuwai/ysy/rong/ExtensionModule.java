package com.kuwai.ysy.rong;

import java.util.List;

import io.rong.callkit.AudioPlugin;
import io.rong.callkit.VideoPlugin;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imkit.widget.provider.FilePlugin;
import io.rong.imlib.model.Conversation;

public class ExtensionModule extends DefaultExtensionModule {

    @Override
    public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType) {
        List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
        pluginModules.add(new GiftPlugin());
        pluginModules.add(new RedPackPlugin());

       /* IPluginModule temp = null;
        for (IPluginModule module : pluginModules) {
            if (module instanceof FilePlugin) {
                temp = module;
                break;
            }
        }
        pluginModules.remove(temp);*/
        IPluginModule temp = null;
        for (IPluginModule module : pluginModules) {
            if (module instanceof AudioPlugin) {
                temp = module;
                break;
            }
        }
        pluginModules.remove(temp);
        IPluginModule temp1 = null;
        for (IPluginModule module : pluginModules) {
            if (module instanceof VideoPlugin) {
                temp1 = module;
                break;
            }
        }
        pluginModules.remove(temp1);

        return pluginModules;
    }
}
