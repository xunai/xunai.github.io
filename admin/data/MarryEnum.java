package cn.com.tx.aus.dao.enums;

import java.util.ArrayList;
import java.util.List;

import cn.com.tx.aus.dao.ISelector;
import cn.com.tx.aus.dao.SelectorDo;

public enum MarryEnum implements ISelector {

	DEFAULT(0,"不想填写"),
	A(1,"未婚"),
	B(2,"离异"),
	C(3,"丧偶");
	
	public Integer key;
	public String value;
	
	private MarryEnum(Integer key,String value){
		this.key = key;
		this.value = value;
	}
	
	public static MarryEnum getMarry(Integer key){
		if (null==key) {
			return DEFAULT;
		}
		MarryEnum[] edus = values();
		for(MarryEnum edu:edus){
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
		MarryEnum[] edus = values();
		for(MarryEnum edu:edus){
			if (edu!=DEFAULT) {
				selectors.add(new SelectorDo(edu.key, edu.value));
			}
		}
		return selectors;
	}
}
