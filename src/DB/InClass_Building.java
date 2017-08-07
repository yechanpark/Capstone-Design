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
				// ������
				// ���� db���� ���ӵ��ִ� ��쿡�� �νĵ�
				if (Week.get(i).getWeekName().equals(week)){
					Week.get(i).addTime(time, classroom);
					break;

				// ������ �̹� �ִ� �̸��̶� db���� ���ӵ��� ���� �ǹ��̸� �߰���
				} else if (i == (Week.size() - 1)) {// ���������� ������ �߰�
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
