package org.dbdoclet.jive.widget.sidebar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.dbdoclet.jive.widget.SideBar;

public class SideBarButton extends JButton implements MouseListener, Comparable<SideBarButton> {

	private static final long serialVersionUID = 1L;
	public static final Border INACTIVE_BORDER = new EmptyBorder(3, 3, 3, 3);
	public static final Border ACTIVE_BORDER = new EmptyBorder(3, 3, 3, 3);
	private static final int MAX_TAB_INDEX = 64;
	private SideBar sideBar;
	private boolean active;
	private Font font;
	private Font activeFont;
	private SideBarGroup group;
	private int tabIndex = MAX_TAB_INDEX;

	public SideBarButton(AbstractAction action) {
		this(action, MAX_TAB_INDEX);
	}
	public SideBarButton(AbstractAction action, int tabIndex) {

		super(action);
		this.tabIndex = tabIndex;

		Rectangle rect = new Rectangle(SideBar.BUTTON_WIDTH,
				SideBar.BUTTON_HEIGHT);

		setBorder(INACTIVE_BORDER);
		setMinimumSize(new Dimension(SideBar.BUTTON_WIDTH,
				SideBar.BUTTON_HEIGHT));
		setPreferredSize(new Dimension(SideBar.BUTTON_WIDTH,
				SideBar.BUTTON_HEIGHT));
		setMaximumSize(new Dimension(SideBar.BUTTON_WIDTH,
				SideBar.BUTTON_HEIGHT));
		setSize(SideBar.BUTTON_WIDTH, SideBar.BUTTON_HEIGHT);
		setBounds(rect);

		setVerticalTextPosition(SwingConstants.BOTTOM);
		setHorizontalTextPosition(SwingConstants.CENTER);

		setContentAreaFilled(false);
		setRolloverEnabled(false);
		setBorderPainted(true);
		setFocusPainted(false);
		setOpaque(true);

		addMouseListener(this);

		active = false;

		font = getFont();
		font = font.deriveFont(Font.PLAIN);
		activeFont = font.deriveFont(Font.BOLD);

		setFont(font);
	}

	public int compareTo(SideBarButton o) {
		
		if (o == null) {
			return 1;
		}
		
		if (getTabIndex() != o.getTabIndex()) {
			return getTabIndex().compareTo(o.getTabIndex());
		}
		
		String text = getText();
		
		if (text == null) {
			return -1;
		}
		
		return text.compareTo(o.getText());
	}

	public SideBarGroup getGroup() {

		if (group == null) {
			group = new SideBarGroup("default");
		}

		return group;
	}

	public SideBar getSideBar() {
		return sideBar;
	}

	public boolean isActive() {
		return active;
	}

	public void isActive(boolean active) {

		if (sideBar == null) {
			throw new IllegalStateException("The field sideBar must not be null!");
		}
		
		this.active = active;

		if (active == false) {

			setBorder(SideBarButton.INACTIVE_BORDER);
			setBackground(sideBar.getBackground());
			setForeground(Color.black);
			setFont(font);

		} else {

			setBorder(SideBarButton.ACTIVE_BORDER);
			setBackground(new Color(199, 199, 199));
			setForeground(Color.black);
			setFont(activeFont);
		}
	}

	public final void mouseClicked(final MouseEvent mouseEvent) {
		//
	}

	public final void mouseEntered(final MouseEvent mouseEvent) {

		setFont(activeFont);
	}

	public final void mouseExited(final MouseEvent mouseEvent) {

		if (isActive() == false) {
			setFont(font);
		}
	}

	public final void mousePressed(final MouseEvent mouseEvent) {
		//
	}

	public final void mouseReleased(final MouseEvent mouseEvent) {
		//
	}

	public void setGroup(SideBarGroup group) {
		this.group = group;
	}

	public void setSideBar(SideBar sideBar) {
		
		this.sideBar = sideBar;

		if (sideBar != null) {
			setBackground(sideBar.getBackground());
		}
	}

	private Integer getTabIndex() {
		return tabIndex;
	}

}