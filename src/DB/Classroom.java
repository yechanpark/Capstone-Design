package DB;

//강의실
public class Classroom {
	//월~토
	int week = 6;
	
	//주간0~9교시 + 야간 1~5교시
	int times = 15;
	
	// 102호 "102"만 저장
	String classroom = new String();
	
	// 강의실 강의정보
	String[][] period = new String[week][times];
	

	// 강의실명 세팅
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	// 교시 세팅
	public void addPeriod(String period, String timeslot, String week) {
		int weeks = 0;

		if (week.equals("월")) {
			weeks = 0;
		} else if (week.equals("화")) {
			weeks = 1;
		} else if (week.equals("수")) {
			weeks = 2;
		} else if (week.equals("목")) {
			weeks = 3;
		} else if (week.equals("금")) {
			weeks = 4;
		} else if (week.equals("토")) {
			weeks = 5;
		}
		if (timeslot.equals("야"))
			this.period[weeks][Integer.parseInt(period) + 9] = Integer.toString(Integer.parseInt(period)+9);
		else
			this.period[weeks][Integer.parseInt(period)] = period;
	}
	// 교시 세팅
	public void addPeriod(String period, String week) {
		int weeks = 0;

		if (week.equals("월")) {
			weeks = 0;
		} else if (week.equals("화")) {
			weeks = 1;
		} else if (week.equals("수")) {
			weeks = 2;
		} else if (week.equals("목")) {
			weeks = 3;
		} else if (week.equals("금")) {
			weeks = 4;
		} else if (week.equals("토")) {
			weeks = 5;
		}

		this.period[weeks][Integer.parseInt(period)] = period;
	}
	// 강의실에 대한 시간표 리스트 출력
	public void printPeriod() {
		for (int i = 0; i < week; i++) {
			if (i == 0)
				System.out.print("월/");
			else if (i == 1)
				System.out.print("화/");
			else if (i == 2)
				System.out.print("수/");
			else if (i == 3)
				System.out.print("목/");
			else if (i == 4)
				System.out.print("금/");
			else if (i == 5)
				System.out.print("토/");

			for (int j = 0; j < times; j++) {
				System.out.print(period[i][j] + " ");
			}
			System.out.println("");
		}

	}

	// 교시 스트링2단배열 반환
	public String[][] getPeriod() {
		return this.period;
	}

	// 강의실이름 반환
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
			weeks = "월";
		else if (week == 1)
			weeks = "화";
		else if (week == 2)
			weeks = "수";
		else if (week == 3)
			weeks = "목";
		else if (week == 4)
			weeks = "금";
		else if (week == 5)
			weeks = "토";
		return weeks; 
	}
	
	public String getTimes(int week, int time){
		return period[week][time];
		
	}
}