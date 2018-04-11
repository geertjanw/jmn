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
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JComponent;
import org.openide.util.ImageUtilities;
import org.pushingpixels.flamingo.api.common.RichTooltip;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

/**
 *
 * @author Chris
 */
abstract class ActionItem {

    public static final String MENU_TEXT = "menuText";
    public static final String DESCRIPTION = "description";
    public static final String ICON_BASE = "iconBase";
    public static final String TOOLTIP_BODY = "tooltipBody";
    public static final String TOOLTIP_TITLE = "tooltipTitle";
    public static final String TOOLTIP_ICON = "tooltipIcon";
    public static final String TOOLTIP_FOOTER = "tooltipFooter";
    public static final String TOOLTIP_FOOTER_ICON = "tooltipFooterIcon";
    public static final String DEFAULT_ACTION = "defaultAction";

    public static ActionItem separator() {
        return new Separator();
    }

    public static ActionItem leaf(Action action) {
        return new Leaf(action);
    }

    public static ActionItem component(JComponent component) {
        return new Component(component);
    }
    private Map<String, Object> _properties;

    public Action getAction() {
        return null;
    }

    public List<ActionItem> getChildren() {
        return Collections.emptyList();
    }

    public void addChild(ActionItem item) {
    }

    public boolean hasChildren() {
        return false;
    }

    public JComponent getComponent() {
        return null;
    }

    public ActionItem getActionDelegate() {
        return this;
    }

    public Object getValue(String key) {
        Object value = innerGetValue(key);
        if (value == null) {
            Action action = getAction();
            if (action != null) {
                value = action.getValue(key);
            }
        }
        if (value == null) {
            JComponent component = getComponent();
            if (component != null) {
                value = component.getClientProperty(value);
            }
        }
        return value;
    }

    public void putValue(String key, Object value) {
        if (_properties == null) {
            _properties = new TreeMap<String, Object>();
        }
        _properties.put(key, value);
    }

    private Object innerGetValue(String key) {
        if (_properties == null) {
            return null;
        } else {
            return _properties.get(key);
        }
    }

    public String getText() {
        String s;
        if (getValue(MENU_TEXT) != null) {
            s = getValue(MENU_TEXT).toString();
        } else {
            s = String.valueOf(getValue(Action.NAME));
        }
        return s != null ? org.openide.awt.Actions.cutAmpersand(s) : null;
    }

    public void setText(String name) {
        putValue(MENU_TEXT, name);
    }

    public String getDescription() {
        String s = null;
        if (getValue(DESCRIPTION) != null) {
            s = getValue(DESCRIPTION).toString();
        } else if (getValue(Action.SHORT_DESCRIPTION) != null) {
            s = String.valueOf(getValue(Action.SHORT_DESCRIPTION));
        }
        return s;
    }

    public RichTooltip createTooltip() {
        String body = (String) getValue(TOOLTIP_BODY);
        if (body == null) {
            body = (String) getValue(Action.LONG_DESCRIPTION);
        }
        if (body == null) {
            body = getDescription();
        }
        if (body == null) {
            return null;
        }

        String title = (String) getValue(TOOLTIP_TITLE);
        if (title == null) {
            title = getText();
        }

        RichTooltip tooltip = new RichTooltip(title, body);

        String titleIcon = (String) getValue(TOOLTIP_ICON);
        if (titleIcon != null) {
            tooltip.setMainImage(ImageUtilities.loadImage(titleIcon));
        } else {
            tooltip.setMainImage(getLargeImage());
        }

        String footer = (String) getValue(TOOLTIP_FOOTER);
        if (footer != null) {
            tooltip.addFooterSection(footer);
            String footerIcon = (String) getValue(TOOLTIP_FOOTER_ICON);
            if (footerIcon != null) {
                tooltip.setFooterImage(ImageUtilities.loadImage(footerIcon));
            }
        }
        return tooltip;
    }

    private Image getLargeImage() {
        String iconResource = (String) getValue(ICON_BASE);
        Image image = null;
        if (iconResource != null) {
            image = ImageUtilities.loadImage(Utils.insertBeforeSuffix(iconResource, 48));
            if (image == null) {
                image = ImageUtilities.loadImage(Utils.insertBeforeSuffix(iconResource, 32));
            }
            if (image == null) {
                image = ImageUtilities.loadImage(Utils.insertBeforeSuffix(iconResource, 24));
            }
        }
        if (image == null) {
            Object iconKey = getValue(Action.LARGE_ICON_KEY);
            if (iconKey instanceof Image) {
                image = (Image) iconKey;
            }
        }
        return image;
    }

    public ResizableIcon getIcon() {
        String iconResource = (String) getValue(ICON_BASE);
        if (iconResource != null) {
            return ResizableIcons.fromResource(iconResource);
        } else {
            Icon small = (Icon) getValue(Action.SMALL_ICON);
            Icon large = (Icon) getValue(Action.LARGE_ICON_KEY);
            return ResizableIcons.binary(small, large);
        }
    }

    public boolean isSeparator() {
        return false;
    }

    @Override
    public String toString() {
        return toString("");
    }

    private String toString(String prefix) {
        StringBuilder buffer = new StringBuilder();
        buffer.append(prefix);
        buffer.append(getText());
        buffer.append('\n');
        for (ActionItem child : getChildren()) {
            buffer.append(child.toString(prefix + "---"));
        }
        return buffer.toString();
    }

    public static class Leaf extends ActionItem {

        private Action _action;

        public Leaf(Action action) {
            _action = action;
        }

        @Override
        public Action getAction() {
            return _action;
        }
    }

    private static class Separator extends ActionItem {

        public Separator() {
        }

        @Override
        public boolean isSeparator() {
            return true;
        }
    }

    private static class Component extends ActionItem {

        private JComponent _component;

        public Component(JComponent component) {
            _component = component;
        }

        @Override
        public JComponent getComponent() {
            return _component;
        }
    }

    public static class Compound extends ActionItem {

        private List<ActionItem> _children;

        public Compound() {
        }

        public Compound(Collection<ActionItem> children) {
            _children = new ArrayList(children);
        }

        @Override
        public ActionItem getActionDelegate() {
            for (ActionItem child : getChildren()) {
                if (child.getValue("defaultAction") == Boolean.TRUE) {
                    return child;
                }
            }
            return super.getActionDelegate();
        }

        @Override
        public List<ActionItem> getChildren() {
            if (_children == null) {
                _children = new ArrayList<ActionItem>();
            }
            return _children;
        }

        @Override
        public void addChild(ActionItem item) {
            getChildren().add(item);
        }

        @Override
        public boolean hasChildren() {
            return _children != null && _children.size() > 0;
        }
    }
}
