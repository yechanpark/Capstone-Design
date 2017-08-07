package DB;

import java.util.ArrayList;

public class Siganpyo_info {
	ArrayList<String> week; // 요일
	ArrayList<String> period;// 교시
	ArrayList<String> classroom;// 강의실
	ArrayList<String> timeslot;

	public Siganpyo_info() {
		week = new ArrayList<String>();
		period = new ArrayList<String>();
		classroom = new ArrayList<String>();
		timeslot = new ArrayList<String>();
	}

	public void setWeek(String week) {
		this.week.add(week);
	}

	public ArrayList<String> getWeekArrlist() {
		return week;
	}

	public void setPeriod(String period) {
		int int_period = Integer.parseInt(period); 
		// 0교시 -> 토요일 0교시 존재 ( L112기초교양교육과정 )
		//10교시 == 야간1교시
		
		//10교시는 1교시 
		if ((int_period / 10) >= 1)
			int_period = (int_period % 10) + 1;
		
		period = Integer.toString(int_period);
		
		this.period.add(period);
	}

	public ArrayList<String> getPeriodArrlist() {
		return period;
	}

	public void setClassroom(String classroom) {
		// classroom을 "공학관(야)지하102" 로 가정

		// 주간
		if (classroom.split("\\(야\\)").length == 1) {
			this.setTimeslot("주");

		} else {// 야간, 공학관(야)지하102
			this.setTimeslot("야");

			// classroom = 공학관지하102
			classroom = classroom.replaceAll("\\(야\\)", "");
		}

		// 지하102 와 B102는 같은 의미이므로 "B102"로 통합
		if (classroom.split("지하").length == 2)
			// classroom = 공학관B102
			classroom = classroom.replace("지하", "B");

		this.classroom.add(classroom);
	}

	public ArrayList<String> getClassroomArrlist() {
		return classroom;
	}

	public void setTimeslot(String timeslot) {
		this.timeslot.add(timeslot);
	}

	public ArrayList<String> getTimeslotArrlist() {
		return timeslot;
	}

	public int getSize() {
		return week.size();
	}

	public void printiganpyo() {
		for (int i = 0; i < week.size(); i++) {
			System.out.print(week.get(i) + " ");
			System.out.print(period.get(i) + " ");
			System.out.println(classroom.get(i));
			System.out.println(timeslot.get(i));
			System.out.println("---------------------");
		}

	}

}
