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
				// ������
				// ���� db���� ���ӵ��ִ� ��쿡�� �νĵ�
				if (Classroom.get(i).equals(classroom)){
					
					break;
				}

				// ������ �̹� �ִ� �̸��̶� db���� ���ӵ��� ���� �ǹ��̸� �߰���
				else if (i == (Classroom.size() - 1)) {// ���������� ������ �߰�
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
