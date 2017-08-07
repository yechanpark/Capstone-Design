package DB;

import java.util.ArrayList;

public class Siganpyo_info {
	ArrayList<String> week; // ����
	ArrayList<String> period;// ����
	ArrayList<String> classroom;// ���ǽ�
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
		// 0���� -> ����� 0���� ���� ( L112���ʱ��米������ )
		//10���� == �߰�1����
		
		//10���ô� 1���� 
		if ((int_period / 10) >= 1)
			int_period = (int_period % 10) + 1;
		
		period = Integer.toString(int_period);
		
		this.period.add(period);
	}

	public ArrayList<String> getPeriodArrlist() {
		return period;
	}

	public void setClassroom(String classroom) {
		// classroom�� "���а�(��)����102" �� ����

		// �ְ�
		if (classroom.split("\\(��\\)").length == 1) {
			this.setTimeslot("��");

		} else {// �߰�, ���а�(��)����102
			this.setTimeslot("��");

			// classroom = ���а�����102
			classroom = classroom.replaceAll("\\(��\\)", "");
		}

		// ����102 �� B102�� ���� �ǹ��̹Ƿ� "B102"�� ����
		if (classroom.split("����").length == 2)
			// classroom = ���а�B102
			classroom = classroom.replace("����", "B");

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
