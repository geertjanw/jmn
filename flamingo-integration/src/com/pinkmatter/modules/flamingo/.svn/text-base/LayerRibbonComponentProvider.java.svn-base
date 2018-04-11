/*
 * Copyright (c) 2010 Chris Böhme - Pinkmatter Solutions. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of Chris Böhme, Pinkmatter Solutions, nor the names of
 *    any contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.pinkmatter.modules.flamingo;

import com.pinkmatter.spi.flamingo.RibbonAppMenuProvider;
import com.pinkmatter.spi.flamingo.RibbonComponentProvider;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import org.pushingpixels.flamingo.api.common.RichTooltip;
import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;

/**
 *
 * @author Chris
 */
public class LayerRibbonComponentProvider extends RibbonComponentProvider {

    @Override
    public JComponent createRibbon() {
        JRibbon ribbon = new JRibbon();
        addAppMenu(ribbon);
        addTaskBar(ribbon);
        addTaskPanes(ribbon);
        addHelpButton(ribbon);
        return ribbon;
    }


    private static void addAppMenu(JRibbon ribbon) {
        RibbonAppMenuProvider appMenuProvider = RibbonAppMenuProvider.getDefault();
        RibbonApplicationMenu appMenu = appMenuProvider.createApplicationMenu();
        if (appMenu != null) {
            ribbon.setApplicationMenu(appMenu);
        }
        RichTooltip appMenuTooltip = appMenuProvider.createApplicationMenuTooltip();
        if (appMenuTooltip != null) {
            ribbon.setApplicationMenuRichTooltip(appMenuTooltip);
        }
    }

    private static void addTaskBar(JRibbon ribbon) {
        List<? extends ActionItem> actions = ActionItems.forPath("Ribbon/TaskBar");// NOI18N
        RibbonComponentFactory factory = new RibbonComponentFactory();
        for (ActionItem action : actions) {
            if (action.isSeparator()) {
                ribbon.addTaskbarComponent(new JSeparator(JSeparator.VERTICAL));
            } else {
                ribbon.addTaskbarComponent(factory.createTaskBarPresenter(action));
            }
        }
    }

    private void addHelpButton(JRibbon ribbon) {
        List<? extends ActionItem> actions = ActionItems.forPath("Ribbon/HelpButton");// NOI18N
        if (actions.size() > 0) {
            ribbon.configureHelp(actions.get(0).getIcon(), actions.get(0).getAction());
        }
    }

    private void addTaskPanes(JRibbon ribbon) {
        RibbonComponentFactory factory = new RibbonComponentFactory();
        for (ActionItem item : ActionItems.forPath("Ribbon/TaskPanes")) {// NOI18N
             ribbon.addTask(factory.createRibbonTask(item));
        }
    }
 
}
