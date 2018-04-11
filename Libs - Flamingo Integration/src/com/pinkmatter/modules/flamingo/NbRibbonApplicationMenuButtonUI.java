/*
 * Created by Gunnar Reinseth and Mikael Tollefsen.
 *
 * Adaptations by Chris Böhme.
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

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import org.openide.util.ImageUtilities;
import org.pushingpixels.flamingo.api.common.JCommandButton;
import org.pushingpixels.flamingo.api.common.model.PopupButtonModel;
import org.pushingpixels.flamingo.api.common.popup.JPopupPanel;
import org.pushingpixels.flamingo.api.common.popup.PopupPanelCallback;
import org.pushingpixels.flamingo.api.ribbon.JRibbon;
import org.pushingpixels.flamingo.api.ribbon.RibbonApplicationMenu;
import org.pushingpixels.flamingo.internal.ui.ribbon.appmenu.BasicRibbonApplicationMenuButtonUI;
import org.pushingpixels.flamingo.internal.ui.ribbon.appmenu.JRibbonApplicationMenuButton;
import org.pushingpixels.flamingo.internal.ui.ribbon.appmenu.JRibbonApplicationMenuPopupPanel;

public class NbRibbonApplicationMenuButtonUI extends BasicRibbonApplicationMenuButtonUI {

    public static ComponentUI createUI(JComponent c) {
        return new NbRibbonApplicationMenuButtonUI();
    }

    @Override
    protected void installComponents() {
        super.installComponents();

        final JRibbonApplicationMenuButton appMenuButton = (JRibbonApplicationMenuButton) this.commandButton;
        appMenuButton.setPopupCallback(new PopupPanelCallback() {

            @Override
            public JPopupPanel getPopupPanel(final JCommandButton commandButton) {
                if (appMenuButton.getParent() instanceof JRibbon) {
                    final JRibbon ribbon = (JRibbon) appMenuButton.getParent();
                    RibbonApplicationMenu ribbonMenu = ribbon.getApplicationMenu();
                    final JRibbonApplicationMenuPopupPanel menuPopupPanel = new JRibbonApplicationMenuPopupPanel(
                            appMenuButton, ribbonMenu);
                    menuPopupPanel.applyComponentOrientation(appMenuButton.getComponentOrientation());
                    menuPopupPanel.setCustomizer(new JPopupPanel.PopupPanelCustomizer() {

                        @Override
                        public Rectangle getScreenBounds() {
                            boolean ltr = commandButton.getComponentOrientation().isLeftToRight();

                            int pw = menuPopupPanel.getPreferredSize().width;
                            int x = ltr ? ribbon.getLocationOnScreen().x
                                    : ribbon.getLocationOnScreen().x
                                    + ribbon.getWidth() - pw;
                            int y = commandButton.getLocationOnScreen().y
                                    + commandButton.getSize().height / 2
                                    + 2;

                            Rectangle scrBounds = commandButton.getGraphicsConfiguration().getBounds();
                            if ((x + pw) > (scrBounds.x + scrBounds.width)) {
                                x = scrBounds.x + scrBounds.width - pw;
                            }
                            int ph = menuPopupPanel.getPreferredSize().height;
                            if ((y + ph) > (scrBounds.y + scrBounds.height)) {
                                y = scrBounds.y + scrBounds.height - ph;
                            }

                            return new Rectangle(
                                    x,
                                    y,
                                    menuPopupPanel.getPreferredSize().width,
                                    menuPopupPanel.getPreferredSize().height);
                        }
                    });
                    return menuPopupPanel;
                }
                return null;
            }
        });

    }

    @Override
    public void paint(Graphics g, JComponent c) {
        if (hasButtonImage()) {
            Graphics2D g2d = (Graphics2D) g;
            Insets ins = c.getInsets();
            Rectangle backgroundRect = new Rectangle(ins.left, ins.top, c.getWidth()
                    - ins.left - ins.right, c.getHeight() - ins.top - ins.bottom);
            paintButtonBackground(g2d, backgroundRect);
            commandButton.putClientProperty("icon.bounds", layoutManager.getLayoutInfo(commandButton, g));
            paintButtonIcon(g2d, backgroundRect);
        } else {
            super.paint(g, c);
        }
    }
    private Boolean _hasButtonImage;
    private ImageIcon _normal;
    private ImageIcon _over;
    private ImageIcon _down;

    private boolean hasButtonImage() {
        if (_hasButtonImage == null) {
            _normal = ImageUtilities.loadImageIcon("com/pinkmatter/modules/flamingo/app-button.png", true);
            if (_normal == null) {
                _hasButtonImage = Boolean.FALSE;
                _normal = ImageUtilities.loadImageIcon("com/pinkmatter/modules/flamingo/app-button-icon24.png", true);
            } else {
                _hasButtonImage = Boolean.TRUE;
                _over = ImageUtilities.loadImageIcon("com/pinkmatter/modules/flamingo/app-button-over.png", true);
                _down = ImageUtilities.loadImageIcon("com/pinkmatter/modules/flamingo/app-button-down.png", true);
            }
        }
        return _hasButtonImage;
    }

    @Override
    protected Icon getIconToPaint() {
        PopupButtonModel model = this.applicationMenuButton.getPopupModel();
        Icon icon = _normal;
        if (model.isPressed() || model.isPopupShowing()) {
            if (_down != null) {
                icon = _down;
            }
        } else if (model.isRollover()) {
            if (_over != null) {
                icon = _over;
            }
        }
        return icon;
    }
}
