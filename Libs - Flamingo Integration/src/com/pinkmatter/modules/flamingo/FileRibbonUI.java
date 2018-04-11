
package com.pinkmatter.modules.flamingo;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.ComponentUI;
import org.pushingpixels.flamingo.api.ribbon.AbstractRibbonBand;
import org.pushingpixels.flamingo.api.ribbon.RibbonTask;

import org.pushingpixels.flamingo.internal.ui.ribbon.BasicRibbonUI;
import org.pushingpixels.flamingo.internal.ui.ribbon.RibbonBandUI;
import org.pushingpixels.flamingo.internal.utils.FlamingoUtilities;


/**
 *
 * @author Bruce Schubert <bruce@emxsys.com>
 * @version $Id$
 */
public class FileRibbonUI extends BasicRibbonUI
{

    public static ComponentUI createUI(JComponent c)
    {
        return new FileRibbonUI();
    }


    /**
     * We're not using the taskbar within the ribbon area, so we return
     * zero to shift the task buttons upwards into the space normally reserved
     * for the taskbar.
     * @return zero instead of the default value of 22.
     */
    @Override
    public int getTaskbarHeight()
    {
        return 0;
    }



    @Override
    protected LayoutManager createLayoutManager()
    {
        return new FileRibbonLayout();
    }



    /**
     * Layout for the ribbon with a dynamically sized application menu button.
     * This is a modified copy of the BasicRibbonUI.RibbonLayout private class.
     * It has been modified to accommodate a dynamically sized application menu
     * button.  See the layoutContainer method for modifications.
     * 
     * @author Kirill Grouchnikov
     * @author Bruce Schubert
     */
    private class FileRibbonLayout implements LayoutManager
    {

        /* Copied. */
        @Override
        public void addLayoutComponent(String name, Component c)
        {
        }


        /* Copied. */
        @Override
        public void removeLayoutComponent(Component c)
        {
        }



        /* Copied. */
        @Override
        public Dimension preferredLayoutSize(Container c)
        {
            Insets ins = c.getInsets();
            int maxPrefBandHeight = 0;
            boolean isRibbonMinimized = ribbon.isMinimized();
            if (!isRibbonMinimized)
            {
                if (ribbon.getTaskCount() > 0)
                {
                    RibbonTask selectedTask = ribbon.getSelectedTask();
                    for (AbstractRibbonBand<?> ribbonBand : selectedTask.getBands())
                    {
                        int bandPrefHeight = ribbonBand.getPreferredSize().height;
                        Insets bandInsets = ribbonBand.getInsets();
                        maxPrefBandHeight = Math.max(maxPrefBandHeight,
                                bandPrefHeight + bandInsets.top
                                + bandInsets.bottom);
                    }
                }
            }

            int extraHeight = getTaskToggleButtonHeight();
            if (!isUsingTitlePane())
            {
                extraHeight += getTaskbarHeight();
            }
            int prefHeight = maxPrefBandHeight + extraHeight + ins.top
                    + ins.bottom;
            // System.out.println("Ribbon pref = " + prefHeight);
            return new Dimension(c.getWidth(), prefHeight);
        }


        /* Copied. */
        @Override
        public Dimension minimumLayoutSize(Container c)
        {
            // go over all ribbon bands and sum the width
            // of ribbon buttons (of collapsed state)
            Insets ins = c.getInsets();
            int width = 0;
            int maxMinBandHeight = 0;
            int gap = getBandGap();

            int extraHeight = getTaskToggleButtonHeight();
            if (!isUsingTitlePane())
            {
                extraHeight += getTaskbarHeight();
            }

            if (ribbon.getTaskCount() > 0)
            {
                boolean isRibbonMinimized = ribbon.isMinimized();
                // minimum is when all the tasks are collapsed
                RibbonTask selectedTask = ribbon.getSelectedTask();
                for (AbstractRibbonBand ribbonBand : selectedTask.getBands())
                {
                    int bandPrefHeight = ribbonBand.getMinimumSize().height;
                    Insets bandInsets = ribbonBand.getInsets();
                    RibbonBandUI bandUI = ribbonBand.getUI();
                    width += bandUI.getPreferredCollapsedWidth();
                    if (!isRibbonMinimized)
                    {
                        maxMinBandHeight = Math.max(maxMinBandHeight,
                                bandPrefHeight + bandInsets.top
                                + bandInsets.bottom);
                    }
                }
                // add inter-band gaps
                width += gap * (selectedTask.getBandCount() - 1);
            }
            else
            {
                // fix for issue 44 (empty ribbon)
                width = 50;
            }
            return new Dimension(width, maxMinBandHeight + extraHeight
                    + ins.top + ins.bottom);
        }


        /**
         * Modified.  Customized so that the layout adapts to the actual size
         * of the application menu button.
         */
        @Override
        public void layoutContainer(Container c)
        {
            // System.out.println("Ribbon real = " + c.getHeight());

            Insets ins = c.getInsets();
            int tabButtonGap = getTabButtonGap();

            boolean ltr = ribbon.getComponentOrientation().isLeftToRight();

            // the top row - task bar components
            int width = c.getWidth();
            int taskbarHeight = getTaskbarHeight();
            int y = ins.top;

            boolean isUsingTitlePane = isUsingTitlePane();
            // handle taskbar only if it is not marked
            if (!isUsingTitlePane)
            {
                taskBarPanel.removeAll();
                for (Component regComp : ribbon.getTaskbarComponents())
                {
                    taskBarPanel.add(regComp);
                }
                // taskbar takes all available width
                taskBarPanel.setBounds(ins.left, ins.top, width - ins.left
                        - ins.right, taskbarHeight);
                y += taskbarHeight;
            }
            else
            {
                taskBarPanel.setBounds(0, 0, 0, 0);
            }

            int taskToggleButtonHeight = getTaskToggleButtonHeight();

            int x = ltr ? ins.left : width - ins.right;
            // the application menu button
// BDS      int appMenuButtonSize = taskbarHeight + taskToggleButtonHeight;           
            // TODO: Compute / sync with icon plust text
            int appMenuButtonWidth = applicationMenuButton.getWidth();
            int appMenuButtonHeight = taskToggleButtonHeight; //applicationMenuButton.getHeight();
            
            if (!isUsingTitlePane)
            {
                applicationMenuButton.setVisible(ribbon.getApplicationMenu() != null);
                if (ribbon.getApplicationMenu() != null)
                {
                    if (ltr)
                    {
                        applicationMenuButton.setBounds(x, ins.top,
                                appMenuButtonWidth, appMenuButtonHeight);
// BDS                      applicationMenuButton.setBounds(x, ins.top,
//                                appMenuButtonSize, appMenuButtonSize);
                    }
                    else
                    {
                        applicationMenuButton.setBounds(x - appMenuButtonWidth,
                                ins.top, appMenuButtonWidth, appMenuButtonHeight);
// BDS                       applicationMenuButton.setBounds(x - appMenuButtonSize,
//                                ins.top, appMenuButtonSize, appMenuButtonSize);
                    }
                }
            }
            else
            {
                applicationMenuButton.setVisible(false);
            }
            x = ltr ? x + 2 : x - 2;
            if (FlamingoUtilities.getApplicationMenuButton(SwingUtilities.getWindowAncestor(ribbon)) != null)
            {
                x = ltr ? x + appMenuButtonWidth : x - appMenuButtonWidth;
//BDS                x = ltr ? x + appMenuButtonSize : x - appMenuButtonSize;
            }

            // the help button
            if (helpButton != null)
            {
                Dimension preferred = helpButton.getPreferredSize();
                if (ltr)
                {
                    helpButton.setBounds(width - ins.right - preferred.width,
                            y, preferred.width, preferred.height);
                }
                else
                {
                    helpButton.setBounds(ins.left, y, preferred.width,
                            preferred.height);
                }
            }

            // task buttons
            if (ltr)
            {
                int taskButtonsWidth = (helpButton != null) ? (helpButton.getX()
                        - tabButtonGap - x) : (c.getWidth() - ins.right - x);
                taskToggleButtonsScrollablePanel.setBounds(x, y,
                        taskButtonsWidth, taskToggleButtonHeight);
            }
            else
            {
                int taskButtonsWidth = (helpButton != null) ? (x - tabButtonGap
                        - helpButton.getX() - helpButton.getWidth())
                        : (x - ins.left);
                taskToggleButtonsScrollablePanel.setBounds(
                        x - taskButtonsWidth, y, taskButtonsWidth,
                        taskToggleButtonHeight);
            }

            TaskToggleButtonsHostPanel taskToggleButtonsHostPanel = taskToggleButtonsScrollablePanel.getView();
            int taskToggleButtonsHostPanelMinWidth = taskToggleButtonsHostPanel.getMinimumSize().width;
            taskToggleButtonsHostPanel.setPreferredSize(new Dimension(
                    taskToggleButtonsHostPanelMinWidth,
                    taskToggleButtonsScrollablePanel.getBounds().height));
            taskToggleButtonsScrollablePanel.doLayout();

            y += taskToggleButtonHeight;

            int extraHeight = taskToggleButtonHeight;
            if (!isUsingTitlePane)
            {
                extraHeight += taskbarHeight;
            }

            if (bandScrollablePanel.getParent() == ribbon)
            {
                if (!ribbon.isMinimized() && (ribbon.getTaskCount() > 0))
                {
                    // y += ins.top;
                    Insets bandInsets = (ribbon.getSelectedTask().getBandCount() == 0) ? new Insets(0, 0, 0, 0)
                            : ribbon.getSelectedTask().getBand(0).getInsets();
                    bandScrollablePanel.setBounds(1 + ins.left, y
                            + bandInsets.top, c.getWidth() - 2 * ins.left - 2
                            * ins.right - 1, c.getHeight() - extraHeight
                            - ins.top - ins.bottom - bandInsets.top
                            - bandInsets.bottom);
                    // System.out.println("Scrollable : "
                    // + bandScrollablePanel.getBounds());
                    BandHostPanel bandHostPanel = bandScrollablePanel.getView();
                    int bandHostPanelMinWidth = bandHostPanel.getMinimumSize().width;
                    bandHostPanel.setPreferredSize(new Dimension(
                            bandHostPanelMinWidth, bandScrollablePanel.getBounds().height));
                    bandScrollablePanel.doLayout();
                    bandHostPanel.doLayout();
                }
                else
                {
                    bandScrollablePanel.setBounds(0, 0, 0, 0);
                }
            }
        }
    }
}
