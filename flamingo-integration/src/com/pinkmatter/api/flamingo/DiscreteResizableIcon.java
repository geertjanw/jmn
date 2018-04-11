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

import com.pinkmatter.modules.flamingo.Utils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.Icon;

import org.openide.util.ImageUtilities;
import org.pushingpixels.flamingo.api.common.icon.ResizableIcon;

class DiscreteResizableIcon implements ResizableIcon {

    private String _iconBase;
    private int _size;
    private Icon _delegate;
    private static final int[] SIZES = {16, 24, 32, 48, 64, 72, 128, 256};

    public DiscreteResizableIcon(String iconBase) {
        _iconBase = iconBase;
    }

    @Override
    public void setDimension(Dimension newDimension) {
        setSize(newDimension.height);
    }

    protected void setSize(int size) {
        if (_size != size) {
            IconSizePair pair = findBestMatch(size);
            _delegate = pair.icon;
            _size = pair.size;
        }
    }

    @Override
    public int getIconHeight() {
        return _size;
    }

    @Override
    public int getIconWidth() {
        return _size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        if (_delegate != null) {
            _delegate.paintIcon(c, g, x, y);
        } else {
            g.setColor(Color.red);
            g.fillRect(x, y, getIconWidth(), getIconHeight());
        }
    }

    private IconSizePair findBestMatch(int size) {
        int index = findClosestIndex(size);
        int realSize = getSize(index);
        Icon icon = getIcon(realSize);
        while (icon == null) {
            if (index < 0) {
                break;
            }
            realSize = getSize(--index);
            icon = getIcon(realSize);
        }
        return new IconSizePair(realSize, icon);
    }

    private Icon getIcon(int size) {
        if (size == 16) {
            return ImageUtilities.loadImageIcon(_iconBase, true);
        } else {
            return ImageUtilities.loadImageIcon(Utils.insertBeforeSuffix(_iconBase, size), true);
        }
    }

    private static int findClosestIndex(int size) {
        for (int i = 0; i < SIZES.length; i++) {
            int currentSize = getSize(i);
            if (currentSize == size) {
                return i;
            } else if (currentSize > size) {
                return i - 1;
            }
        }
        return 0;
    }

    private static int getSize(int index) {
        if (index < 0) {
            index = 0;
        }
        if (index > SIZES.length - 1) {
            index = SIZES.length - 1;
        }
        return SIZES[index];
    }

    private static class IconSizePair {

        public IconSizePair(int size, Icon icon) {
            this.size = size;
            this.icon = icon;
        }
        public int size;
        public Icon icon;
    }
}
