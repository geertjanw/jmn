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

import com.pinkmatter.api.flamingo.ResizableIcons;
import com.pinkmatter.api.flamingo.RibbonPresenter;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.Action;
import javax.swing.JComponent;
import org.pushingpixels.flamingo.api.common.AbstractCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.JCommandButton.CommandButtonKind;
import org.pushingpixels.flamingo.api.common.JCommandMenuButton;
import org.pushingpixels.flamingo.api.common.RichTooltip;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;
import org.pushingpixels.flamingo.api.common.popup.JCommandPopupMenu;
import org.pushingpixels.flamingo.api.common.popup.JPopupPanel;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelCallback;
import org.pushingpixels.flamingo.api.ribbon.AbstractRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.JRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryFooter;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntryPrimary;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenuEntrySecondary;
import org.pushingpixels.flamingo.api.ribbon.RibbonElementPriority;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;
import org.pushingpixels.flamingo.api.ribbon.resize.CoreRibbonResizePolicies.Mid2Mid;
import org.pushingpixels.flamingo.api.ribbon.resize.RibbonBandResizePolicy;

/**
 *
 * @author Chris
 */
class RibbonComponentFactory {

    public RibbonApplicationMenuEntryPrimary createAppMenuPresenter(ActionItem item) {
        Action action = item.getAction();
        if (action != null && RibbonPresenter.AppMenu.class.isAssignableFrom(action.getClass())) {
            return ((RibbonPresenter.AppMenu) action).getPrimaryMenuEntry();
        } else {
            PrimaryMenuItem menuItem =
                    new PrimaryMenuItem(item.getIcon(),
                    item.getText(), action, getButtonKind(item));
            ArrayList<RibbonApplicationMenuEntrySecondary> secondaries = new ArrayList<RibbonApplicationMenuEntrySecondary>();
            for (ActionItem child : item.getChildren()) {
                if (child.getAction() == null) {
                    menuItem.addSecondaryMenuGroup(child.getText(), createSecondaryItems(child.getChildren()));
                } else {
                    if (!child.isSeparator()) {
                        secondaries.add(createAppMenuSecondaryPresenter(child));
                    }
                }
            }

            RibbonApplicationMenuEntrySecondary[] secondary = secondaries.toArray(new RibbonApplicationMenuEntrySecondary[secondaries.size()]);
            if (secondary != null && secondary.length > 0) {
                menuItem.addSecondaryMenuGroup(null, secondary);
            }
            return menuItem;
        }
    }

    private RibbonApplicationMenuEntrySecondary createAppMenuSecondaryPresenter(ActionItem item) {
        Action action = item.getAction();
        if (action != null && RibbonPresenter.AppMenuSecondary.class.isAssignableFrom(action.getClass())) {
            return ((RibbonPresenter.AppMenuSecondary) action).getSecondaryMenuEntry();
        } else {
            SecondaryMenuItem menuItem =
                    new SecondaryMenuItem(item.getIcon(),
                    item.getText(), action, getButtonKind(item));
            menuItem.setDescriptionText(item.getDescription());
            return menuItem;
        }
    }

    private RibbonApplicationMenuEntrySecondary[] createSecondaryItems(List<ActionItem> children) {
        ArrayList<RibbonApplicationMenuEntrySecondary> secondaries = new ArrayList<RibbonApplicationMenuEntrySecondary>();
        for (ActionItem child : children) {
            if (!child.isSeparator()) {
                secondaries.add(createAppMenuSecondaryPresenter(child));
            }
        }
        return secondaries.toArray(new RibbonApplicationMenuEntrySecondary[secondaries.size()]);
    }

    public RibbonApplicationMenuEntryFooter createAppMenuFooterPresenter(ActionItem item) {
        RibbonApplicationMenuEntryFooter footer = new RibbonApplicationMenuEntryFooter(
                item.getIcon(), item.getText(), item.getAction());
        return footer;
    }

    public Component createTaskBarPresenter(ActionItem item) {
        return createButtonPresenter(item);
    }

    public AbstractCommandButton createButtonPresenter(ActionItem item) {
        Action action = item.getAction();
        if (action != null && RibbonPresenter.Button.class.isAssignableFrom(action.getClass())) {
            return ((RibbonPresenter.Button) action).getRibbonButtonPresenter();
        } else {
            return createCommandButton(item);
        }
    }

    private AbstractCommandButton createCommandButton(ActionItem item) {
        //TODO
        //button.setDisabledIcon(disabledIcon);
        ActionCommandButton button = new ActionCommandButton(item.getActionDelegate().getIcon(),
                item.getActionDelegate().getText(), item.getActionDelegate().getAction(), getButtonKind(item));
        RichTooltip toolTip = item.getActionDelegate().createTooltip();
        button.setActionRichTooltip(toolTip);
        if (item.hasChildren()) {
            //TODO differentiate between the two
            //button.setPopupRichTooltip(tooltip);
            final JCommandPopupMenu menu = createPopupMenu(item.getChildren());
            button.setPopupCallback(new PopupPanelCallback() {

                @Override
                public JPopupPanel getPopupPanel(JCommandButton commandButton) {
                    return menu;
                }
            });
        }
        return button;
    }

    public JCommandPopupMenu createPopupMenu(List<ActionItem> items) {
        JCommandPopupMenu menu = new JCommandPopupMenu();
        for (ActionItem item : items) {
            if (item.isSeparator()) {
                menu.addMenuSeparator();
            } else {
                menu.addMenuButton(createPopupMenuPresenter(item));
            }
        }
        return menu;
    }

    public JCommandMenuButton createPopupMenuPresenter(ActionItem item) {
        //TODO orientation of popup
        ActionMenuButton button = new ActionMenuButton(item.getIcon(),
                item.getText(), item.getAction(), getButtonKind(item));
        RichTooltip toolTip = item.createTooltip();
        button.setActionRichTooltip(toolTip);
        if (item.hasChildren()) {
            //TODO differentiate between the two
            //button.setPopupRichTooltip(tooltip);
            final JCommandPopupMenu menu = createPopupMenu(item.getChildren());
            button.setPopupCallback(new PopupPanelCallback() {

                @Override
                public JPopupPanel getPopupPanel(JCommandButton commandButton) {
                    return menu;
                }
            });
        }
        return button;
    }

    public RibbonTask createRibbonTask(ActionItem item) {
        RibbonTask task = new RibbonTask(item.getText(), createRibbonBands(item.getChildren()));
        //TODO sequencing policy
        return task;
    }

    private AbstractRibbonBand[] createRibbonBands(List<? extends ActionItem> items) {
        List<AbstractRibbonBand> bands = new ArrayList<AbstractRibbonBand>();
        for (ActionItem item : items) {
            JComponent component = item.getComponent();
            if (component instanceof AbstractRibbonBand) {
                bands.add((AbstractRibbonBand) component);
            } else {
                bands.add(createRibbonBand(item));
            }
        }
        return bands.toArray(new AbstractRibbonBand[bands.size()]);
    }

    public AbstractRibbonBand createRibbonBand(ActionItem item) {
        //TODO icon
        JRibbonBand band = new JRibbonBand(item.getText(), ResizableIcons.empty(), getDefaultAction(item));
        for (ActionItem child : item.getChildren()) {
            if (child.isSeparator()) {
                band.startGroup();
            } else if (child.getValue(ActionItem.DEFAULT_ACTION) != Boolean.TRUE) {
                addRibbonBandAction(band, child);
            }
        }
        band.setResizePolicies(Arrays.<RibbonBandResizePolicy>asList(
                new Mid2Mid(band.getControlPanel())));
        return band;
    }

    private static ActionListener getDefaultAction(ActionItem item) {
        for (ActionItem child : item.getChildren()) {
            if (child.getValue(ActionItem.DEFAULT_ACTION) == Boolean.TRUE
                    && child.getAction() != null) {
                return child.getAction();
            }
        }
        return null;
    }

    private void addRibbonBandAction(JRibbonBand band, ActionItem item) {
        Action action = item.getAction();
        if (action != null && RibbonPresenter.Component.class.isAssignableFrom(action.getClass())) {
            //TODO calculate height
            band.addRibbonComponent(((RibbonPresenter.Component) action).getRibbonBarComponentPresenter(), 3);
        } else {
            band.addCommandButton(createButtonPresenter(item), getPriority(item));
        }
    }

    private static RibbonElementPriority getPriority(ActionItem item) {
        RibbonElementPriority p = RibbonElementPriority.TOP;
        String priority = (String) item.getValue("priority");
        if (priority != null) {
            p = RibbonElementPriority.valueOf(priority.toUpperCase());
        }
        return p;
    }

    private static class ActionCommandButton extends JCommandButton {

        public ActionCommandButton(ResizableIcon icon, String text, final Action action,
                CommandButtonKind type) {
            super(text, icon);
            setCommandButtonKind(type);
            if (action != null) {
                addActionListener(action);
                action.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("enabled".equals(evt.getPropertyName())) {
                            setEnabled(action.isEnabled());
                        }
                    }
                });
                setEnabled(action.isEnabled());
            }
        }
    }

    private static class ActionMenuButton extends JCommandMenuButton {

        public ActionMenuButton(ResizableIcon icon, String text, final Action action,
                CommandButtonKind type) {
            super(text, icon);
            setCommandButtonKind(type);
            if (action != null) {
                addActionListener(action);

                action.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("enabled".equals(evt.getPropertyName())) {
                            setEnabled(action.isEnabled());
                        }
                    }
                });
                setEnabled(action.isEnabled());
            }
        }
    }

    private static class PrimaryMenuItem extends RibbonApplicationMenuEntryPrimary {

        public PrimaryMenuItem(ResizableIcon icon, String text, final Action action, CommandButtonKind type) {
            super(icon, text, action, type);

            if (action != null) {
                action.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("enabled".equals(evt.getPropertyName())) {
                            setEnabled(action.isEnabled());
                        }
                    }
                });
                setEnabled(action.isEnabled());
            }
        }
    }

    private static class SecondaryMenuItem extends RibbonApplicationMenuEntrySecondary {

        public SecondaryMenuItem(ResizableIcon icon, String text, final Action action, CommandButtonKind type) {
            super(icon, text, action, type);

            if (action != null) {
                action.addPropertyChangeListener(new PropertyChangeListener() {

                    @Override
                    public void propertyChange(PropertyChangeEvent evt) {
                        if ("enabled".equals(evt.getPropertyName())) {
                            setEnabled(action.isEnabled());
                        }
                    }
                });
                setEnabled(action.isEnabled());
            }
        }
    }

    private static CommandButtonKind getButtonKind(ActionItem item) {
        Action delegate = item.getActionDelegate().getAction();
        if (delegate == null && item.hasChildren()) {
            return CommandButtonKind.POPUP_ONLY;
        } else if (delegate != null && item.hasChildren()) {
            return CommandButtonKind.ACTION_AND_POPUP_MAIN_ACTION;
        } else if (delegate != null && !item.hasChildren()) {
            return CommandButtonKind.ACTION_ONLY;
        } else {
            return CommandButtonKind.POPUP_ONLY;
        }
    }
}
