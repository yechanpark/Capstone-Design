package Parser;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.FileSystemResource;

import DB.ClassBuilding;
import DB.ConnectionDAO;
import DB.Siganpyo_info;
import DB.Total_siganpyo;

public class Parse_isEmptyClass extends Parse {

	JSONArray jArray_buildings = new JSONArray();
	ResultSet rs;

	public Parse_isEmptyClass() throws IOException, ClassNotFoundException, SQLException {
		/* factory 정의, xml파일의 빈네임을 참조한다 */
		BeanFactory factory = new XmlBeanFactory(
				new FileSystemResource("C:/Users/Administrator/workspace/CapstoneDesign/WebContent/db.xml"));

		/* DB커넥션 설정 */
		ConnectionDAO connMaker = factory.getBean("connMaker", ConnectionDAO.class);
		Connection con = connMaker.getConn();
		Statement st = con.createStatement();
		rs = st.executeQuery("select Building, Classroom, Week, Time from Empty_Siganpyo"); // 쿼리문

		doParsetoJSON();
		rs.close();
		st.close();
		con.close();
	}

	private void doParsetoJSON(){
		Total_siganpyo ts = new Total_siganpyo();

		try {
			for (; rs.next();) {
				String building = rs.getString(1);
				String classroom = rs.getString(2);
				String week = rs.getString(3);
				String time = rs.getString(4);


				// 전체 빈 강의에 대한 강의정보 세팀
				ts.addCbuilding(building, classroom, time, week);

			}
			// 최종결과 출력
			ts.printSiganpyo();

		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		JSONArray jrr_buildings = null;
		JSONArray jrr_classrooms = null;
		JSONArray jrr_periods = null;
		JSONArray jrr_times = null;

		JSONObject job_building = null;
		JSONObject job_classroom = null;
		JSONObject job_week = null;
		JSONObject job_time = null;


		// 빌딩 횟수만큼
		jrr_buildings = new JSONArray();
		for (int building = 0; building < ts.getBuildingSize(); building++) {
			if(!(ts.getBuildingName(building).equals("진리관")))
				continue;
			job_building = new JSONObject();
			job_building.put("building", ts.getCbuilding(building).getBuildingName());

			// 각 빌딩 강의실 갯수 만큼
			jrr_classrooms = new JSONArray();
			for (int classroom = 0; classroom < ts.getCbuilding(building).getClassroomSize(); classroom++) {
				job_classroom = new JSONObject();
				job_classroom.put("classroom", ts.getCbuilding(building).getClassroom(classroom).getClassroomName());

				// 각 강의실 월~토
				jrr_periods = new JSONArray();
				for (int week = 0; week < ts.getCbuilding(building).getClassroom(classroom).getWeekSize(); week++) {
					job_week = new JSONObject();
					job_week.put("week", ts.getCbuilding(building).getClassroom(classroom).getWeekString(week));

					// 각 요일 0~15교시
					jrr_times = new JSONArray();
					for (int time = 0; time < ts.getCbuilding(building).getClassroom(classroom)
							.getTimesSize(); time++) {

						job_time = new JSONObject();
						if(ts.getCbuilding(building).getClassroom(classroom).getTimes(week, time) != null){
							job_time.put("time", ts.getCbuilding(building).getClassroom(classroom).getTimes(week, time));
							jrr_times.add(job_time);
						}

					} // 0~15교시 끝

					job_week.put("times", jrr_times);
					jrr_periods.add(job_week);
				} // 월~토 끝

				job_classroom.put("periods", jrr_periods);
				jrr_classrooms.add(job_classroom);
			} // 강의실갯수 끝

			job_building.put("classrooms", jrr_classrooms);
			jrr_buildings.add(job_building);
		} // 빌딩 끝

		jsonMain.put("buildings", jrr_buildings);
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return jsonMain;
	}

}
