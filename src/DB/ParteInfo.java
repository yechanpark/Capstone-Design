package DB;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class ParteInfo {

	String date;
	ArrayList<String> menus;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMenu(int i) {
		return menus.get(i);
	}

	public void setMenus(String menus) {
		StringTokenizer st = new StringTokenizer(menus, " ");
		while (st.hasMoreTokens())
			this.menus.add(st.nextToken());
	}
	public int getMenuSize(){
		return menus.size();
	}
}
