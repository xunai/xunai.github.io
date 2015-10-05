package cn.com.tx.aus.dao.enums;

import java.util.ArrayList;
import java.util.List;

import cn.com.tx.aus.dao.ISelector;
import cn.com.tx.aus.dao.SelectorDo;

public enum JobEnum implements ISelector {

	DEFAULT(0,"不想填写"),
	A(1,"在校学生"),
	B(2,"军人"),
	C(3,"私营业主"),
	D(4,"企业职工"),
	E(5,"农业劳动者"),
	F(6,"政府机关/事业单位"),
	G(7,"其他");
	
	public Integer key;
	public String value;
	
	private JobEnum(Integer key,String value){
		this.key = key;
		this.value = value;
	}
	
	public static JobEnum getJob(Integer key){
		if (null==key) {
			return DEFAULT;
		}
		JobEnum[] edus = values();
		for(JobEnum edu:edus){
			if (edu.key.equals(key)) {
				return edu;
			}
		}
		return DEFAULT;
	}
	
	public boolean isUndefine(Integer key){
		return null==key||0==key;
	}

	@Override
	public List<SelectorDo> getSelectors() {
		List<SelectorDo> selectors = new ArrayList<SelectorDo>();
		JobEnum[] edus = values();
		for(JobEnum edu:edus){
			if (edu!=DEFAULT) {
				selectors.add(new SelectorDo(edu.key, edu.value));
			}
		}
		return selectors;
	}
}
