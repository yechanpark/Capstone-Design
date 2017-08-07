package DB;

import java.util.ArrayList;

//전체시간표
public class Total_siganpyo {
	// 전체건물 모음
	ArrayList<ClassBuilding> Cbuilding = new ArrayList<ClassBuilding>();

	// 미래관 102 2 주 금
	public void addCbuilding(String building, String classroom, String period, String timeslot, String week) {

		// 미래관이 이미 있는지 확인
		// 처음 레코드면
		if (Cbuilding.size() == 0) {

			ClassBuilding cb = new ClassBuilding();
			cb.setBuildingName(building);
			cb.addClassroom(classroom, period, timeslot, week);
			Cbuilding.add(cb);

		} else {

			for (int i = 0; i < Cbuilding.size(); i++) {
				// 있으면
				// 현재 db에서 연속되있는 경우에만 인식됨
				if (Cbuilding.get(i).getBuildingName().equals(building)) {
					Cbuilding.get(i).addClassroom(classroom, period, timeslot, week);
					break;

					// 위에서 이미 있는 이름이라도 db에서 연속되지 않은 건물이면 추가됨
				} else if (i == (Cbuilding.size() - 1)) {// 마지막까지 없으면 추가
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

		// 미래관이 이미 있는지 확인
		// 처음 레코드면
		if (Cbuilding.size() == 0) {

			ClassBuilding cb = new ClassBuilding();
			cb.setBuildingName(building);
			cb.addClassroom(classroom, period, week);
			Cbuilding.add(cb);

		} else {

			for (int i = 0; i < Cbuilding.size(); i++) {
				// 있으면
				// 현재 db에서 연속되있는 경우에만 인식됨
				if (Cbuilding.get(i).getBuildingName().equals(building)) {
					Cbuilding.get(i).addClassroom(classroom, period, week);
					break;

					// 위에서 이미 있는 이름이라도 db에서 연속되지 않은 건물이면 추가됨
				} else if (i == (Cbuilding.size() - 1)) {// 마지막까지 없으면 추가
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

			// 공학관 여러개 나옴
			Cbuilding.get(i).printBuildingName();
			Cbuilding.get(i).printClassroomName();
			Cbuilding.get(i).printPeriod();
			System.out.println("-------------------------------");

		}
	}
}
