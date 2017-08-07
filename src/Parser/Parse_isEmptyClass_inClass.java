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
import DB.InClass_Total_siganpyo;
import DB.Siganpyo_info;
import DB.Total_siganpyo;

public class Parse_isEmptyClass_inClass extends Parse {

	JSONArray jArray_buildings = new JSONArray();
	ResultSet rs;

	public Parse_isEmptyClass_inClass() throws IOException, ClassNotFoundException, SQLException {
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

	private void doParsetoJSON() throws SQLException{

		
		InClass_Total_siganpyo its = new InClass_Total_siganpyo();

		for (; rs.next();) {
			String building = rs.getString(1);
			String classroom = rs.getString(2);
			String week = rs.getString(3);
			String time = rs.getString(4);
			
			//if(building.equals("진리관"))
			its.addBuilding(building, week, time, classroom);
		}
		JSONObject job_building = new JSONObject();
		JSONObject job_week  = new JSONObject();
		JSONObject job_time = new JSONObject();
		JSONObject job_classroom = new JSONObject();
		
		JSONArray jrr_buildings = new JSONArray();
		JSONArray jrr_weeks = new JSONArray();
		JSONArray jrr_times = new JSONArray();
		JSONArray jrr_classrooms = new JSONArray();
		
		jrr_buildings = new JSONArray();
		for (int building = 0; building < its.getBuldingSize(); building++) {
			job_building = new JSONObject();
			job_building.put("building", its.getBuilding(building).getBuildingName());
			jrr_weeks = new JSONArray();
			
			for (int week = 0; week < its.getBuilding(building).getWeekSize(); week++) {
				job_week = new JSONObject();
				job_week.put("week", its.getBuilding(building).getWeek(week).getWeekName());
				jrr_times = new JSONArray();
				
				for (int time = 0; time < its.getBuilding(building).getWeek(week).getTimeSize(); time++) {
					job_time = new JSONObject();
					job_time.put("time", its.getBuilding(building).getWeek(week).getTime(time).getTimeName());
					jrr_classrooms = new JSONArray();
					
					for (int classroom = 0; classroom < its.getBuilding(building).getWeek(week).getTime(time)
							.getClassroomSize(); classroom++){
						job_classroom = new JSONObject();
						job_classroom.put("c", its.getBuilding(building).getWeek(week).getTime(time).getClassroomName(classroom));
						jrr_classrooms.add(job_classroom);
					}
					
					job_time.put("classrooms", jrr_classrooms);
					jrr_times.add(job_time);
				}
				job_week.put("times", jrr_times);
				jrr_weeks.add(job_week);
			}
			job_building.put("weeks", jrr_weeks);
			jrr_buildings.add(job_building);
		}

		jsonMain.put("buildings", jrr_buildings);
	}

	@Override
	public JSONObject getJSON() {
		// TODO Auto-generated method stub
		return jsonMain;
	}

}
