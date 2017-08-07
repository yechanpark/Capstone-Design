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
				// ������
				// ���� db���� ���ӵ��ִ� ��쿡�� �νĵ�
				if (Time.get(i).getTimeName().equals(time)){
					Time.get(i).addClassroom(classroom);
					break;

				// ������ �̹� �ִ� �̸��̶� db���� ���ӵ��� ���� �ǹ��̸� �߰���
				} else if (i == (Time.size() - 1)) {// ���������� ������ �߰�
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
