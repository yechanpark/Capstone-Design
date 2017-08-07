package DB;

import java.util.ArrayList;

public class InClass_Total_siganpyo {
	ArrayList<InClass_Building> Buildings = new ArrayList<InClass_Building>();
	
	public void addBuilding(String building, String week, String time, String classroom){
		if (Buildings.size() == 0) {//
			InClass_Building cb = new InClass_Building();
			cb.setBuildingName(building);
			cb.addweek(week, time, classroom);
			Buildings.add(cb);

		} else {

			for (int i = 0; i < Buildings.size(); i++) {
				// 있으면
				// 현재 db에서 연속되있는 경우에만 인식됨
				if (Buildings.get(i).getBuildingName().equals(building)) {
					Buildings.get(i).addweek(week, time, classroom);
					break;

					// 위에서 이미 있는 이름이라도 db에서 연속되지 않은 건물이면 추가됨
				} else if (i == (Buildings.size() - 1)) {// 마지막까지 없으면 추가
					InClass_Building cb = new InClass_Building();
					cb.setBuildingName(building);
					cb.addweek(week, time, classroom);
					Buildings.add(cb);
					break;
				}
			}
		}
	}
	
	public int getBuldingSize(){
		return this.Buildings.size();
	}
	
	public InClass_Building getBuilding(int i){
		return this.Buildings.get(i);
	}
}
