package DB;

import java.util.ArrayList;

public class InClass_Week {
	String Week = new String();
	ArrayList<InClass_Time> Time = new ArrayList<InClass_Time>();
	
	public void addTime(String time, String classroom){
		if (Time.size() == 0) {
			InClass_Time sub_time = new InClass_Time();
			sub_time.setTimeName(time);
			sub_time.addClassroom(classroom);
			Time.add(sub_time);

		} else {

			for (int i = 0; i < Time.size(); i++) {
				// 있으면
				// 현재 db에서 연속되있는 경우에만 인식됨
				if (Time.get(i).getTimeName().equals(time)){
					Time.get(i).addClassroom(classroom);
					break;

				// 위에서 이미 있는 이름이라도 db에서 연속되지 않은 건물이면 추가됨
				} else if (i == (Time.size() - 1)) {// 마지막까지 없으면 추가
					InClass_Time sub_time = new InClass_Time();
					sub_time.setTimeName(time);
					sub_time.addClassroom(classroom);
					Time.add(sub_time);
					break;
				}
			}
		}
	}
	
	public InClass_Time getTime(int i){
		return this.Time.get(i);
	}
	
	public String getWeekName(){
		return this.Week;
	}
	
	public void setWeekName(String week){
		this.Week = week;
	}
	public int getTimeSize(){
		return this.Time.size();
	}
}
