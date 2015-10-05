package cn.com.tx.aus.dao.enums;

import java.util.ArrayList;
import java.util.List;

import cn.com.tx.aus.dao.ISelector;
import cn.com.tx.aus.dao.SelectorDo;

public enum EduEnum implements ISelector {

	DEFAULT(0,"不想填写"),
	A(1,"初中"),
	B(2,"高中/中专"),
	C(3,"大专"),
	D(4,"本科"),
	E(5,"硕士及以上");
	
	public Integer key;
	public String value;
	
	private EduEnum(Integer key,String value){
		this.key = key;
		this.value = value;
	}
	
	public static EduEnum getEdu(Integer key){
		if (null==key) {
			return DEFAULT;
		}
		EduEnum[] edus = values();
		for(EduEnum edu:edus){
			if (edu.key.equals(key)) {
				return edu;
			}
		}
		return DEFAULT;
	}
	
	public static boolean isUndefine(Integer key){
		return null==key||0==key;
	}

	@Override
	public List<SelectorDo> getSelectors() {
		List<SelectorDo> selectors = new ArrayList<SelectorDo>();
		EduEnum[] edus = values();
		for(EduEnum edu:edus){
			if (edu!=DEFAULT) {
				selectors.add(new SelectorDo(edu.key, edu.value));
			}
		}
		return selectors;
	}
	
}
