package cn.com.tx.aus.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.tx.galaxy.util.StringUtil;

/**
 * 用户属性
 *
 */
public class UserDo implements IAutoChange {

	//uid
	Integer uid;
	//昵称
	String nick;
	//头像
	String face;
	//照片
	String photo;
	//性别
	Integer sex;
	//生日
	Long birth;
	//学历
	//0 未设置
	//1 初中以下
	//2 高中/中专
	//3 大专 
	//4 本科
	//5 硕士及以上
	Integer edu;
	//省
	Integer province;
	//城市
	Integer city;
	//城市区
	Integer district;
	//身高
	Integer height;
	//体重
	Integer weight;
	//血型
	//1 A
	//2 B
	//3 AB
	//4 O
	Integer blood;
	//月收入
	//0 未设置
	//1 <2000
	//2 2000-5000
	//3 5000-10000
	//4 10000-20000
	//5 20000以上
	Integer income;
	//职业
	Integer job;
	//魅力部位
	Integer beauty;
	//是否有房
	Integer house;
	//接受异地恋
	Integer rmtLove;
	//喜欢的异性
	Integer like; 
	//婚前性行为
	Integer msex;
	//和父母同住
	Integer withFamily;
	//是否想要孩子
	Integer wantBaby;
	//婚姻状态
	Integer marry;
	//亮点
	Integer bright;
	//兴趣爱好
	Long interest;
	//个性特征
	Long personal;
	//0经度 1维度
	List<Double> xy;
	//内心独白
	String summary;
	//手机号
	String phone;
	//VIP
	//000000...[包月][VIP]
	Integer vip;
	//金豆
	Integer coin;
	//与机器人沟通次数
	Integer mo;
	//是否身份证校验
	Boolean vId;
	//是否手机号校验
	Boolean vPhone;
	//搜索阀值,主要由用户活跃度,积分,操作,奖励等综合统计而成
	Integer threshold;
	//当前流程 1 未审核头像 2 已审核头像 
	Integer flow;
	//最后登录时间
	Long lastLogin;
	//创建时间
	Long ctime;
	
	@Override
	public boolean hasChange() {
		return (null!=uid&&uid>0)
				&&(
						StringUtil.isNotBlank(nick)||StringUtil.isNotBlank(face)
						||null!=sex||(null!=birth&&birth>0)||null!=edu
						||null!=city||null!=province||null!=district||null!=height
						||null!=weight||null!=blood||null!=income||null!=job||null!=beauty
						||null!=house||null!=rmtLove||null!=like||null!=msex
						||null!=withFamily||null!=wantBaby||null!=marry||null!=interest
						||null!=personal||null!=summary||null!=xy
						||null!=lastLogin||null!=photo||null!=bright||null!=phone||null!=flow||null!=ctime
						||null!=vId||null!=vPhone
				);
	}
	@Override
	public Map<String, Object> changes() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotBlank(nick)) {
			map.put("nick", nick);
		}
		if(StringUtil.isNotBlank(face)){
			map.put("face", face);
		}
		if(null!=sex){
			map.put("sex", sex);
		}
		if (null!=birth) {
			map.put("birth", birth);
		}
		if (null!=edu) {
			map.put("edu", edu);
		}
		if (null!=province) {
			map.put("province", province);
		}
		if (null!=city) {
			map.put("city", city);
		}
		if (null!=district) {
			map.put("district", district);
		}
		if (null!=height) {
			map.put("height", height);
		}
		if (null!=weight) {
			map.put("weight", weight);
		}
		if (null!=blood) {
			map.put("blood", blood);
		}
		if (null!=income) {
			map.put("income", income);
		}
		if (null!=job) {
			map.put("job", job);
		}
		if (null!=beauty) {
			map.put("beauty", beauty);
		}
		if (null!=house) {
			map.put("house", house);
		}
		if (null!=rmtLove) {
			map.put("rmtLove", rmtLove);
		}
		if (null!=like) {
			map.put("like", like);
		}
		if (null!=msex) {
			map.put("msex", msex);
		}
		if (null!=withFamily) {
			map.put("withFamily", withFamily);
		}
		if (null!=wantBaby) {
			map.put("wantBaby", wantBaby);
		}
		if (null!=marry) {
			map.put("marry", marry);
		}
		if (null!=interest) {
			map.put("interest",interest );
		}
		if (null!=personal) {
			map.put("personal", personal);
		}
		if (null!=xy) {
			map.put("xy", xy);
		}
		if (null!=summary) {
			map.put("summary", summary);
		}
		if (null!=phone) {
			map.put("phone", phone);
		}
		if (null!=lastLogin) {
			map.put("lastLogin", lastLogin);
		}
		if (null!=photo) {
			map.put("photo", photo);
		}
		if (null!=bright) {
			map.put("bright", bright);
		}
		if (null!=flow) {
			map.put("flow", flow);
		}
		if (null!=ctime) {
			map.put("ctime", ctime);
		}
		if (null!=vId) {
			map.put("vId", vId);
		}
		if (null!=vPhone) {
			map.put("vPhone", vPhone);
		}
		return map;
	}
	public Integer getUid() {
		return uid;
	}
	public void setUid(Integer uid) {
		this.uid = uid;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getFace() {
		return face;
	}
	public void setFace(String face) {
		this.face = face;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Long getBirth() {
		return birth;
	}
	public void setBirth(Long birth) {
		this.birth = birth;
	}
	public Integer getEdu() {
		return edu;
	}
	public void setEdu(Integer edu) {
		this.edu = edu;
	}
	public Integer getProvince() {
		return province;
	}
	public void setProvince(Integer province) {
		this.province = province;
	}
	public Integer getCity() {
		return city;
	}
	public void setCity(Integer city) {
		this.city = city;
	}
	public Integer getDistrict() {
		return district;
	}
	public void setDistrict(Integer district) {
		this.district = district;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	public Integer getWeight() {
		return weight;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	public Integer getBlood() {
		return blood;
	}
	public void setBlood(Integer blood) {
		this.blood = blood;
	}
	public Integer getIncome() {
		return income;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}
	public Integer getJob() {
		return job;
	}
	public void setJob(Integer job) {
		this.job = job;
	}
	public Integer getBeauty() {
		return beauty;
	}
	public void setBeauty(Integer beauty) {
		this.beauty = beauty;
	}
	public Integer getHouse() {
		return house;
	}
	public void setHouse(Integer house) {
		this.house = house;
	}
	public Integer getRmtLove() {
		return rmtLove;
	}
	public void setRmtLove(Integer rmtLove) {
		this.rmtLove = rmtLove;
	}
	public Integer getLike() {
		return like;
	}
	public void setLike(Integer like) {
		this.like = like;
	}
	public Integer getMsex() {
		return msex;
	}
	public void setMsex(Integer msex) {
		this.msex = msex;
	}
	public Integer getWithFamily() {
		return withFamily;
	}
	public void setWithFamily(Integer withFamily) {
		this.withFamily = withFamily;
	}
	public Integer getWantBaby() {
		return wantBaby;
	}
	public void setWantBaby(Integer wantBaby) {
		this.wantBaby = wantBaby;
	}
	public Integer getMarry() {
		return marry;
	}
	public void setMarry(Integer marry) {
		this.marry = marry;
	}
	public Long getInterest() {
		return interest;
	}
	public void setInterest(Long interest) {
		this.interest = interest;
	}
	public Long getPersonal() {
		return personal;
	}
	public void setPersonal(Long personal) {
		this.personal = personal;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public Integer getVip() {
		return vip;
	}
	public void setVip(Integer vip) {
		this.vip = vip;
	}
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public Long getLastLogin() {
		return lastLogin;
	}
	public void setLastLogin(Long lastLogin) {
		this.lastLogin = lastLogin;
	}
	public Long getCtime() {
		return ctime;
	}
	public void setCtime(Long ctime) {
		this.ctime = ctime;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Integer getCoin() {
		return coin;
	}
	public void setCoin(Integer coin) {
		this.coin = coin;
	}
	public Integer getMo() {
		return mo;
	}
	public void setMo(Integer mo) {
		this.mo = mo;
	}
	public Integer getBright() {
		return bright;
	}
	public void setBright(Integer bright) {
		this.bright = bright;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getFlow() {
		return flow;
	}
	public void setFlow(Integer flow) {
		this.flow = flow;
	}
	public List<Double> getXy() {
		return xy;
	}
	public void setXy(List<Double> xy) {
		this.xy = xy;
	}
	public Boolean getVId() {
		return vId;
	}
	public void setVId(Boolean vId) {
		this.vId = vId;
	}
	public Boolean getVPhone() {
		return vPhone;
	}
	public void setVPhone(Boolean vPhone) {
		this.vPhone = vPhone;
	}
}
