package DB;

import java.util.ArrayList;

public class InClass_Time {
	String Time = new String();
	ArrayList<String> Classroom = new ArrayList<String>();

	public void addClassroom(String classroom) {
		if (Classroom.size() == 0) {
			Classroom.add(classroom);

		} else {

			for (int i = 0; i < Classroom.size(); i++) {
				// 있으면
				// 현재 db에서 연속되있는 경우에만 인식됨
				if (Classroom.get(i).equals(classroom)){
					
					break;
				}

				// 위에서 이미 있는 이름이라도 db에서 연속되지 않은 건물이면 추가됨
				else if (i == (Classroom.size() - 1)) {// 마지막까지 없으면 추가
					Classroom.add(classroom);
					break;
				}
			}
		}
	}
	
	public int getClassroomSize(){
		return Classroom.size();
	}
	public String getClassroomName(int i){
		return Classroom.get(i);
	}
	public void setTimeName(String time){
		this.Time = time;
	}
	public String getTimeName(){
		return this.Time;
	}
}
