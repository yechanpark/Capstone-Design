package DB;

import java.util.ArrayList;

//��ü�ð�ǥ
public class Total_siganpyo {
	// ��ü�ǹ� ����
	ArrayList<ClassBuilding> Cbuilding = new ArrayList<ClassBuilding>();

	// �̷��� 102 2 �� ��
	public void addCbuilding(String building, String classroom, String period, String timeslot, String week) {

		// �̷����� �̹� �ִ��� Ȯ��
		// ó�� ���ڵ��
		if (Cbuilding.size() == 0) {

			ClassBuilding cb = new ClassBuilding();
			cb.setBuildingName(building);
			cb.addClassroom(classroom, period, timeslot, week);
			Cbuilding.add(cb);

		} else {

			for (int i = 0; i < Cbuilding.size(); i++) {
				// ������
				// ���� db���� ���ӵ��ִ� ��쿡�� �νĵ�
				if (Cbuilding.get(i).getBuildingName().equals(building)) {
					Cbuilding.get(i).addClassroom(classroom, period, timeslot, week);
					break;

					// ������ �̹� �ִ� �̸��̶� db���� ���ӵ��� ���� �ǹ��̸� �߰���
				} else if (i == (Cbuilding.size() - 1)) {// ���������� ������ �߰�
					ClassBuilding cb = new ClassBuilding();
					cb.setBuildingName(building);
					cb.addClassroom(classroom, period, timeslot, week);
					Cbuilding.add(cb);
					break;
				}
			}
		}
	}
	public void addCbuilding(String building, String classroom,String period, String week) {

		// �̷����� �̹� �ִ��� Ȯ��
		// ó�� ���ڵ��
		if (Cbuilding.size() == 0) {

			ClassBuilding cb = new ClassBuilding();
			cb.setBuildingName(building);
			cb.addClassroom(classroom, period, week);
			Cbuilding.add(cb);

		} else {

			for (int i = 0; i < Cbuilding.size(); i++) {
				// ������
				// ���� db���� ���ӵ��ִ� ��쿡�� �νĵ�
				if (Cbuilding.get(i).getBuildingName().equals(building)) {
					Cbuilding.get(i).addClassroom(classroom, period, week);
					break;

					// ������ �̹� �ִ� �̸��̶� db���� ���ӵ��� ���� �ǹ��̸� �߰���
				} else if (i == (Cbuilding.size() - 1)) {// ���������� ������ �߰�
					ClassBuilding cb = new ClassBuilding();
					cb.setBuildingName(building);
					cb.addClassroom(classroom, period, week);
					Cbuilding.add(cb);
					break;
				}
			}
		}
	}
	public ClassBuilding getCbuilding(int i) {
		return this.Cbuilding.get(i);
	}
	
	public int getBuildingSize(){
		return this.Cbuilding.size();
	}
	public String getBuildingName(int i){
		return this.Cbuilding.get(i).getBuildingName();
	}

	public void printSiganpyo() {

		for (int i = 0; i < Cbuilding.size(); i++) {

			// ���а� ������ ����
			Cbuilding.get(i).printBuildingName();
			Cbuilding.get(i).printClassroomName();
			Cbuilding.get(i).printPeriod();
			System.out.println("-------------------------------");

		}
	}
}
