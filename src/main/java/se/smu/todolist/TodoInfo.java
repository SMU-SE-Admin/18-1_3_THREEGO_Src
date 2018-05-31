package se.smu.todolist;

import java.io.Serializable;

public class TodoInfo implements Serializable {
	// 기본 변수 선언
	private Object importance;
	private Object tfSubject;
	private Object cbDeadMonth;
	private Object cbRDeadMonth;
	private Object cbState;
	private Object tfWTD;
	private Object memo;

	// 기본 변수의 Getter, Setter
	public Object getImportance() {
		return importance;
	}

	public void setImportance(Object importance) {
		this.importance = importance;
	}

	public Object getTfSubject() {
		return tfSubject;
	}

	public void setTfSubject(Object tfSubject) {
		this.tfSubject = tfSubject;
	}

	public Object getCbDeadMonth() {
		return cbDeadMonth;
	}

	public void setCbDeadMonth(Object cbDeadMonth) {
		this.cbDeadMonth = cbDeadMonth;
	}

	public Object getCbRDeadMonth() {
		return cbRDeadMonth;
	}

	public void setCbRDeadMonth(Object cbRDeadMonth) {
		this.cbRDeadMonth = cbRDeadMonth;
	}

	public Object getCbState() {
		return cbState;
	}

	public void setCbState(Object cbState) {
		this.cbState = cbState;
	}

	public Object getTfWTD() {
		return tfWTD;
	}

	public void setTfWTD(Object tfWTD) {
		this.tfWTD = tfWTD;
	}

	public Object getMemo() {
		return memo;
	}

	public void setMemo(Object memo) {
		this.memo = memo;
	}

	// 생성자
	TodoInfo(Object importance, Object tfSubject, Object cbDeadMonth, Object cbRDeadMonth, Object cbState, Object tfWTD,
			Object memo) {
		this.importance = importance;
		this.tfSubject = tfSubject;
		this.cbDeadMonth = cbDeadMonth;
		this.cbRDeadMonth = cbRDeadMonth;
		this.cbState = cbState;
		this.tfWTD = tfWTD;
		this.memo = memo;
	}

}
