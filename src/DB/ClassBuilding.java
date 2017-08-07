package DB;

import java.util.ArrayList;

//건물하나
public class ClassBuilding {
	// 건물명(공학관 창의관 등)
	String building = new String();

	// 건물 안의 강의실 모음
	ArrayList<Classroom> Croom = new ArrayList<Classroom>();

	// 건물이름 세팅
	public void setBuildingName(String name) {
		this.building = name;
	}

	// 건물이름 반환
	public String getBuildingName() {
		return this.building;
	}

	// 강의실 이름출력
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

	// 건물 이름출력
	public void printBuildingName() {
		System.out.println(this.building);
	}

	// 강의실 있으면 해당 객체에 추가, 없으면 arraylist에 add()
	public void addClassroom(String classroom, String period, String timeslot, String week) {
		// 102호가 이미 있는지 확인
		// 처음에 없으면 추가
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

				} else if (i == (Croom.size() - 1)) {// 마지막까지 없으면 추가
					Classroom cr = new Classroom();
					cr.setClassroom(classroom);
					cr.addPeriod(period, timeslot, week);
					Croom.add(cr);
					break;
				}
			}

		}
	}
	// 강의실 있으면 해당 객체에 추가, 없으면 arraylist에 add()
	public void addClassroom(String classroom, String period, String week) {
		// 102호가 이미 있는지 확인
		// 처음에 없으면 추가
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

				} else if (i == (Croom.size() - 1)) {// 마지막까지 없으면 추가
					Classroom cr = new Classroom();
					cr.setClassroom(classroom);
					cr.addPeriod(period, week);
					Croom.add(cr);
					break;
				}
			}

		}
	}
	// 강의실 어레이리스트 반환
	public ArrayList<Classroom> getClassroomArr() {
		return this.Croom;
	}

	public Classroom getClassroom(int i) {
		return this.Croom.get(i);
	}
}
