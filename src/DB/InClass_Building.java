package DB;

import java.util.ArrayList;

public class InClass_Building {
	String Building = new String();
	ArrayList<InClass_Week> Week = new ArrayList<InClass_Week>();
	
	public void setBuildingName(String building){
		this.Building = building;
	}
	public String getBuildingName(){
		return this.Building;
	}
	
	public void addweek(String week, String time, String classroom){
		if (Week.size() == 0) {
			InClass_Week sub_week = new InClass_Week();
			sub_week.setWeekName(week);
			sub_week.addTime(time, classroom);
			Week.add(sub_week);

		} else {

			for (int i = 0; i < Week.size(); i++) {
				// 있으면
				// 현재 db에서 연속되있는 경우에만 인식됨
				if (Week.get(i).getWeekName().equals(week)){
					Week.get(i).addTime(time, classroom);
					break;

				// 위에서 이미 있는 이름이라도 db에서 연속되지 않은 건물이면 추가됨
				} else if (i == (Week.size() - 1)) {// 마지막까지 없으면 추가
					InClass_Week sub_week = new InClass_Week();
					sub_week.setWeekName(week);
					sub_week.addTime(time, classroom);
					Week.add(sub_week);
					break;
				}
			}
		}
		
	}
	
	public InClass_Week getWeek(int i){
		return this.Week.get(i);
	}
	
	public int getWeekSize(){
		return this.Week.size();
	}
}
