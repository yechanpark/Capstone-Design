package DB;

import java.util.ArrayList;

public class InClass_Total_siganpyo {
	ArrayList<InClass_Building> Buildings = new ArrayList<InClass_Building>();
	
	public void addBuilding(String building, String week, String time, String classroom){
		if (Buildings.size() == 0) {//
			InClass_Building cb = new InClass_Building();
			cb.setBuildingName(building);
			cb.addweek(week, time, classroom);
			Buildings.add(cb);

		} else {

			for (int i = 0; i < Buildings.size(); i++) {
				// ������
				// ���� db���� ���ӵ��ִ� ��쿡�� �νĵ�
				if (Buildings.get(i).getBuildingName().equals(building)) {
					Buildings.get(i).addweek(week, time, classroom);
					break;

					// ������ �̹� �ִ� �̸��̶� db���� ���ӵ��� ���� �ǹ��̸� �߰���
				} else if (i == (Buildings.size() - 1)) {// ���������� ������ �߰�
					InClass_Building cb = new InClass_Building();
					cb.setBuildingName(building);
					cb.addweek(week, time, classroom);
					Buildings.add(cb);
					break;
				}
			}
		}
	}
	
	public int getBuldingSize(){
		return this.Buildings.size();
	}
	
	public InClass_Building getBuilding(int i){
		return this.Buildings.get(i);
	}
}
