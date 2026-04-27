package bean;

import java.io.Serializable;

public class Subject implements Serializable {

	private String cd;      // 科目コード（3桁の文字列）
	private String name;    // 科目名
	private School school;  // 学校情報

	// ゲッターとセッター（データの出し入れ口）
	public String getCd() {
		return cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
}