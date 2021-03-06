package io.zulia.client;

import io.zulia.fields.annotations.UniqueId;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class TestObj1 {

	@UniqueId
	private String field1;

	private int field2;

	private List<String> field3;

	private Set<Integer> field4;

	private Date field5;

	public TestObj1(String field1, int field2, List<String> field3, Set<Integer> field4, Date field5) {
		this.field1 = field1;
		this.field2 = field2;
		this.field3 = field3;
		this.field4 = field4;
		this.field5 = field5;
	}

	public String getField1() {
		return field1;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public int getField2() {
		return field2;
	}

	public void setField2(int field2) {
		this.field2 = field2;
	}

	public List<String> getField3() {
		return field3;
	}

	public void setField3(List<String> field3) {
		this.field3 = field3;
	}

	public Set<Integer> getField4() {
		return field4;
	}

	public void setField4(Set<Integer> field4) {
		this.field4 = field4;
	}

	public Date getField5() {
		return field5;
	}

	public void setField5(Date field5) {
		this.field5 = field5;
	}

	@Override
	public String toString() {
		return "TestObj1{" + "field1='" + field1 + '\'' + ", field2=" + field2 + ", field3=" + field3 + ", field4=" + field4 + '}';
	}
}
