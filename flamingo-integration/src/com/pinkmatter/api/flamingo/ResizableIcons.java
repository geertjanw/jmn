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

package com.pinkmatter.api.flamingo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

/**
 *
 * @author Chris
 */
public class ResizableIcons {

    public static final ResizableIcon EMPTY = new Empty();

    private ResizableIcons() {
    }

    public static ResizableIcon fromResource(String resource) {
        return new DiscreteResizableIcon(resource);
    }

    public static ResizableIcon fromImage(Image image) {
        return new ResizableImageIcon(image);
    }

    public static ResizableIcon binary(Icon smallIcon, Icon largeIcon) {
        return new BinaryResizableIcon(smallIcon, largeIcon);
    }

    public static ResizableIcon empty() {
        return EMPTY;
    }

    private static class BinaryResizableIcon implements ResizableIcon {

        private Icon _small;
        private Icon _large;
        private Icon _delegate;
        private int _width;
        private int _height;

        public BinaryResizableIcon(Icon small, Icon large) {
            _small = small;
            _large = large;
            if (_small == null) {
                _small = large;
            }
            if (_large == null) {
                _large = small;
            }
            if (_small != null) {
                setSize(_small.getIconWidth(), _small.getIconWidth());
            } else if (_large != null) {
                setSize(_large.getIconWidth(), _large.getIconWidth());
            }
        }

        @Override
        public void setDimension(Dimension size) {
            setSize((int) size.getWidth(), (int) size.getHeight());
        }

        private void setSize(int width, int height) {
            if (_small != null || _large != null) {
                if (width != _width || height != _height) {
                    _width = width;
                    _height = height;
                    if (_height > _small.getIconHeight() + 2) {
                        _delegate = _large;
                    } else {
                        _delegate = _small;
                    }
                }
            }
        }

        @Override
        public int getIconHeight() {
            if (_delegate != null) {
                return _delegate.getIconHeight();
            } else {
                return _height;
            }
        }

        @Override
        public int getIconWidth() {
            if (_delegate != null) {
                return _delegate.getIconWidth();
            } else {
                return _width;
            }
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
            if (_delegate != null) {
                _delegate.paintIcon(c, g, x, y);
            } else {
                g.setColor(Color.GRAY);
                g.fillRect(x, y, getIconWidth(), getIconHeight());
            }
        }
    }

    private static class ResizableImageIcon extends ImageIcon implements ResizableIcon {

        public ResizableImageIcon(Image image) {
            super(image);
        }

        @Override
        public void setDimension(Dimension dmnsn) {
        }
    }

    private static class Empty implements ResizableIcon {

        @Override
        public void setDimension(Dimension dmnsn) {
        }

        @Override
        public void paintIcon(Component c, Graphics g, int x, int y) {
        }

        @Override
        public int getIconWidth() {
            return 0;
        }

        @Override
        public int getIconHeight() {
            return 0;
        }
    }
}
