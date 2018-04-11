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
import com.pinkmatter.spi.flamingo.RibbonDefaultRolloverProvider;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.Lookup;
import org.pushingpixels.flamingo.api.common.RichTooltip;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryFooter;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary.PrimaryRolloverCallback;

/**
 *
 * @author Chris
 */
public class LayerRibbonAppMenuProvider extends RibbonAppMenuProvider {

    @Override
    public RibbonApplicationMenu createApplicationMenu() {
        RibbonApplicationMenu appMenu = new RibbonApplicationMenu();
        List<? extends ActionItem> actions = ActionItems.forPath("Ribbon/AppMenu");// NOI18N
        RibbonComponentFactory factory = new RibbonComponentFactory();
        for (ActionItem action : actions) {
            if (action.isSeparator()) {
                appMenu.addMenuSeparator();
            } else {
                appMenu.addMenuEntry(factory.createAppMenuPresenter(action));
            }
        }
        for (RibbonApplicationMenuEntryFooter entry : createApplicationMenuFooter()) {
            appMenu.addFooterEntry(entry);
        }
        PrimaryRolloverCallback cb = createPrimaryRolloverCallback();
        if (cb != null) {
            appMenu.setDefaultCallback(cb);
        }
        return appMenu;
    }

    public RibbonApplicationMenuEntryFooter[] createApplicationMenuFooter() {
        List<? extends ActionItem> actions = ActionItems.forPath("Ribbon/AppMenuFooter");// NOI18N
        ArrayList<RibbonApplicationMenuEntryFooter> footer = new ArrayList<RibbonApplicationMenuEntryFooter>();
        RibbonComponentFactory factory = new RibbonComponentFactory();
        for (ActionItem action : actions) {
            if (action.getAction() != null) {
                footer.add(factory.createAppMenuFooterPresenter(action));
            }
        }
        return footer.toArray(new RibbonApplicationMenuEntryFooter[footer.size()]);
    }

    @Override
    protected PrimaryRolloverCallback createPrimaryRolloverCallback() {
        return Lookup.getDefault().lookup(RibbonDefaultRolloverProvider.class);
    }

}
