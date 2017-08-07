package DB;

import java.util.ArrayList;

//�ǹ��ϳ�
public class ClassBuilding {
	// �ǹ���(���а� â�ǰ� ��)
	String building = new String();

	// �ǹ� ���� ���ǽ� ����
	ArrayList<Classroom> Croom = new ArrayList<Classroom>();

	// �ǹ��̸� ����
	public void setBuildingName(String name) {
		this.building = name;
	}

	// �ǹ��̸� ��ȯ
	public String getBuildingName() {
		return this.building;
	}

	// ���ǽ� �̸����
	public void printClassroomName() {
		for (int i = 0; i < Croom.size(); i++)
			System.out.println(Croom.get(i).getClassroomName() + " ");

	}
	
	public int getClassroomSize(){
		return this.Croom.size();
	}

	public void printPeriod() {
		for (int i = 0; i < Croom.size(); i++) {
			Croom.get(i).printPeriod();
		}
	}

	// �ǹ� �̸����
	public void printBuildingName() {
		System.out.println(this.building);
	}

	// ���ǽ� ������ �ش� ��ü�� �߰�, ������ arraylist�� add()
	public void addClassroom(String classroom, String period, String timeslot, String week) {
		// 102ȣ�� �̹� �ִ��� Ȯ��
		// ó���� ������ �߰�
		if (Croom.size() == 0) {

			Classroom cr = new Classroom();
			cr.setClassroom(classroom);
			cr.addPeriod(period, timeslot, week);
			Croom.add(cr);

		} else {

			for (int i = 0; i < Croom.size(); i++) {

				if (Croom.get(i).getClassroomName().equals(classroom)) {
					Croom.get(i).addPeriod(period, timeslot, week);
					break;

				} else if (i == (Croom.size() - 1)) {// ���������� ������ �߰�
					Classroom cr = new Classroom();
					cr.setClassroom(classroom);
					cr.addPeriod(period, timeslot, week);
					Croom.add(cr);
					break;
				}
			}

		}
	}
	// ���ǽ� ������ �ش� ��ü�� �߰�, ������ arraylist�� add()
	public void addClassroom(String classroom, String period, String week) {
		// 102ȣ�� �̹� �ִ��� Ȯ��
		// ó���� ������ �߰�
		if (Croom.size() == 0) {

			Classroom cr = new Classroom();
			cr.setClassroom(classroom);
			cr.addPeriod(period, week);
			Croom.add(cr);

		} else {

			for (int i = 0; i < Croom.size(); i++) {

				if (Croom.get(i).getClassroomName().equals(classroom)) {
					Croom.get(i).addPeriod(period, week);
					break;

				} else if (i == (Croom.size() - 1)) {// ���������� ������ �߰�
					Classroom cr = new Classroom();
					cr.setClassroom(classroom);
					cr.addPeriod(period, week);
					Croom.add(cr);
					break;
				}
			}

		}
	}
	// ���ǽ� ��̸���Ʈ ��ȯ
	public ArrayList<Classroom> getClassroomArr() {
		return this.Croom;
	}

	public Classroom getClassroom(int i) {
		return this.Croom.get(i);
	}
}
