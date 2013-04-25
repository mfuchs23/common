package org.dbdoclet.jive.widget.sidebar;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class SideBarGroup implements Comparable<SideBarGroup> {

	public static final SideBarGroup DEFAULT_GROUP = new SideBarGroup("default");

	private String name;
	private ArrayList<SideBarButton> buttonList;
	private int tabIndex;

	public SideBarGroup(String name) {
		this(name, 0);
	}

	public SideBarGroup(String name, int tabIndex) {

		this.name = name;
		this.tabIndex = tabIndex;

		buttonList = new ArrayList<SideBarButton>();
	}

	public void addButton(SideBarButton button) {

		button.setGroup(this);
		buttonList.add(button);
	}

	public int compareTo(SideBarGroup o) {

		if (tabIndex != o.getTabIndex()) {
			return new Integer(tabIndex)
					.compareTo(new Integer(o.getTabIndex()));
		}

		if (name == null || o.getName() == null) {
			return 0;
		}

		return name.compareTo(o.getName());
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null || obj instanceof SideBarGroup == false) {
			return false;
		}

		SideBarGroup otherGroup = (SideBarGroup) obj;
		String otherName = otherGroup.getName();

		return name.equals(otherName);
	}

	public ArrayList<SideBarButton> getButtonList() {
		return buttonList;
	}

	public String getName() {

		if (name == null) {
			name = "default";
		}

		return name;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	@Override
	public int hashCode() {

		if (name == null) {
			return 0;
		}

		return name.hashCode();
	}

	public void setIcon(ImageIcon icon) {
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

}
