package DB;

//���ǽ�
public class Classroom {
	//��~��
	int week = 6;
	
	//�ְ�0~9���� + �߰� 1~5����
	int times = 15;
	
	// 102ȣ "102"�� ����
	String classroom = new String();
	
	// ���ǽ� ��������
	String[][] period = new String[week][times];
	

	// ���ǽǸ� ����
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	// ���� ����
	public void addPeriod(String period, String timeslot, String week) {
		int weeks = 0;

		if (week.equals("��")) {
			weeks = 0;
		} else if (week.equals("ȭ")) {
			weeks = 1;
		} else if (week.equals("��")) {
			weeks = 2;
		} else if (week.equals("��")) {
			weeks = 3;
		} else if (week.equals("��")) {
			weeks = 4;
		} else if (week.equals("��")) {
			weeks = 5;
		}
		if (timeslot.equals("��"))
			this.period[weeks][Integer.parseInt(period) + 9] = Integer.toString(Integer.parseInt(period)+9);
		else
			this.period[weeks][Integer.parseInt(period)] = period;
	}
	// ���� ����
	public void addPeriod(String period, String week) {
		int weeks = 0;

		if (week.equals("��")) {
			weeks = 0;
		} else if (week.equals("ȭ")) {
			weeks = 1;
		} else if (week.equals("��")) {
			weeks = 2;
		} else if (week.equals("��")) {
			weeks = 3;
		} else if (week.equals("��")) {
			weeks = 4;
		} else if (week.equals("��")) {
			weeks = 5;
		}

		this.period[weeks][Integer.parseInt(period)] = period;
	}
	// ���ǽǿ� ���� �ð�ǥ ����Ʈ ���
	public void printPeriod() {
		for (int i = 0; i < week; i++) {
			if (i == 0)
				System.out.print("��/");
			else if (i == 1)
				System.out.print("ȭ/");
			else if (i == 2)
				System.out.print("��/");
			else if (i == 3)
				System.out.print("��/");
			else if (i == 4)
				System.out.print("��/");
			else if (i == 5)
				System.out.print("��/");

			for (int j = 0; j < times; j++) {
				System.out.print(period[i][j] + " ");
			}
			System.out.println("");
		}

	}

	// ���� ��Ʈ��2�ܹ迭 ��ȯ
	public String[][] getPeriod() {
		return this.period;
	}

	// ���ǽ��̸� ��ȯ
	public String getClassroomName() {
		return this.classroom;
	}
	
	public int getWeekSize(){
		return week;
	}
	
	public int getTimesSize(){
		return times;
	}
	
	public String getWeekString(int week){
		String weeks="";
		if (week == 0)
			weeks = "��";
		else if (week == 1)
			weeks = "ȭ";
		else if (week == 2)
			weeks = "��";
		else if (week == 3)
			weeks = "��";
		else if (week == 4)
			weeks = "��";
		else if (week == 5)
			weeks = "��";
		return weeks; 
	}
	
	public String getTimes(int week, int time){
		return period[week][time];
		
	}
}