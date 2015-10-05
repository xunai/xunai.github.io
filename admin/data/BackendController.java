package cn.com.tx.aus.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.tx.aus.conf.Aus;
import cn.com.tx.aus.dao.AccountDao;
import cn.com.tx.aus.dao.ChatDao;
import cn.com.tx.aus.dao.ReportDao;
import cn.com.tx.aus.dao.StatisticsDao;
import cn.com.tx.aus.dao.UserDao;
import cn.com.tx.aus.dao.UserSyncDao;
import cn.com.tx.aus.domain.ChatDo;
import cn.com.tx.aus.domain.FaceVerifyDo;
import cn.com.tx.aus.domain.IdCardVerifyDo;
import cn.com.tx.aus.domain.LoveTreeDo;
import cn.com.tx.aus.domain.ThresholdEnum;
import cn.com.tx.aus.domain.UserDo;
import cn.com.tx.aus.domain.UserSyncDo;
import cn.com.tx.aus.domain.enums.SysUid;
import cn.com.tx.aus.domain.enums.UserFlowEnum;
import cn.com.tx.aus.service.ChatService;
import cn.com.tx.aus.service.LoveTreeService;
import cn.com.tx.aus.service.UserService;

@Controller
@RequestMapping("/ba/1/ck/x/7u/1n/a/2i/sy/st/m")
public class BackendController extends BackendBaseController {

	@Resource
	private UserDao userDao;
	@Resource
	private AccountDao accountDao;
	@Resource
	private UserService userService;
	@Resource
	private LoveTreeService loveTreeService;
	@Resource
	private ChatService chatService;
	@Resource
	private UserSyncDao userSyncDao;
	@Resource
	private ChatDao chatDao;
	@Resource
	private StatisticsDao statisticsDao;
	@Resource
	private ReportDao reportDao;

	private static final int ROBOT_PAGE_SIZE = 20;

	/**
	 * 获取某个状态头像审核数量
	 * @param request
	 * @param response
	 * @param state
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fv/count")
	public String getFvsCount(HttpServletRequest request,
			HttpServletResponse response,
			byte state,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		return callbackparam+"("+userDao.getFaceVerifiesCountByState(state)+")";
	}
	
	/**
	 * 获取头像审核列表
	 * @param request
	 * @param response
	 * @param page
	 * @param state
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fv/list")
	public String getFvs(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false,defaultValue="0") int page,
			byte state,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		if (page<0) {
			return ajaxResult(callbackparam, null);
		}
		List<FaceVerifyDo> fvs = userDao.getFaceVerifiesByState(page, state);
		if (null!=fvs&&fvs.size()>0) {
			for(FaceVerifyDo fv:fvs){
				try {
					fv.setSex(userService.getUserByUid(fv.getUid()).getSex());
				} catch (Throwable e) {
				}
			}
		}
		return callbackparam+"("+fvs+")";
	}
	
	/**
	 * 根据id修改用户头像
	 * @param request
	 * @param response
	 * @param id
	 * @param state
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fv/update/id")
	public String updateFv(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam int id,
			@RequestParam byte state,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		if (id<1||(state!=2&&state!=3)) {
			return ajaxFailResult(callbackparam);
		}
		userService.updateFaceVerifyState(id, state);
		FaceVerifyDo fv = userService.getFaceVerify(id);
		if (null==fv) {
			return ajaxFailResult(callbackparam);
		}
		UserDo user = userService.getUserByUid(fv.getUid());
		if (state==2) {
			//通过
			if (null != user && user.getUid() > 4858000) {
				// 发系统结果通知
				ChatDo chat = new ChatDo();
				chat.setContent("头像审核通过！完善更多资料获得更高回复率喔~");
				chat.setCtime(System.currentTimeMillis());
				chat.setFuid(SysUid.FACE_VERIFY.uid);
				chat.setRead(false);
				chat.setTuid(user.getUid());
				chatService.addChat(chat);
			}
			if (null==user.getFlow()||UserFlowEnum.CHECKED.key!=user.getFlow()) {
				/**
				 * 头像审核只加一次权重
				 */
				userService.incUserThreshold(fv.getUid(), ThresholdEnum.V_FACE.value);
			}
			UserDo update = new UserDo();
			update.setUid(fv.getUid());
			update.setFlow(UserFlowEnum.CHECKED.key);
			userService.addOrUpdateUser(update);
		}else{
			//将用户头像修改为默认头像
			UserDo update = new UserDo();
			update.setUid(fv.getUid());
			update.setFlow(UserFlowEnum.UNCHECK.key);
			if (null!=user&&Integer.valueOf(1).equals(user.getSex())) {
				update.setFace(Aus.DEFAULT_MALE_FACE);
			}else{
				update.setFace(Aus.DEFAULT_FEMALE_FACE);
			}
			userService.addOrUpdateUser(update);
			UserSyncDo us = new UserSyncDo();
			us.setUid(fv.getUid());
			us.setUserSync(System.currentTimeMillis());
			userService.addOrUpdateUserSync(us);
			if (null != user && user.getUid() > 4858000) {
				// 发系统结果通知
				ChatDo chat = new ChatDo();
				chat.setContent("头像审核不通过！请上传一张本人真实照片。");
				chat.setCtime(System.currentTimeMillis());
				chat.setFuid(SysUid.FACE_VERIFY.uid);
				chat.setRead(false);
				chat.setTuid(user.getUid());
				chatService.addChat(chat);
			}
		}
		return ajaxSuccessResult(callbackparam);
	}
	
	/**
	 * 根据uid修改头像状态
	 * @param request
	 * @param response
	 * @param uid
	 * @param state
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/fv/update/uid")
	public String updateFvSimple(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam int uid,
			@RequestParam byte state,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		UserDo user = userService.getUserByUid(uid);
		if (state==2) {
			//通过
			if (null != user && user.getUid() > 4858000) {
				// 发系统结果通知
				ChatDo chat = new ChatDo();
				chat.setContent("头像审核通过！完善更多资料获得更高回复率喔~");
				chat.setCtime(System.currentTimeMillis());
				chat.setFuid(SysUid.FACE_VERIFY.uid);
				chat.setRead(false);
				chat.setTuid(user.getUid());
				chatService.addChat(chat);
			}
			if (null==user.getFlow()||UserFlowEnum.CHECKED.key!=user.getFlow()) {
				/**
				 * 头像审核只加一次权重
				 */
				userService.incUserThreshold(user.getUid(), ThresholdEnum.V_FACE.value);
			}
			UserDo update = new UserDo();
			update.setUid(uid);
			update.setFlow(UserFlowEnum.CHECKED.key);
			userService.addOrUpdateUser(update);
		}else{
			//将用户头像修改为默认头像
			UserDo update = new UserDo();
			update.setUid(uid);
			update.setFlow(UserFlowEnum.UNCHECK.key);
			if (null!=user&&Integer.valueOf(1).equals(user.getSex())) {
				update.setFace(Aus.DEFAULT_MALE_FACE);
			}else{
				update.setFace(Aus.DEFAULT_FEMALE_FACE);
			}
			userService.addOrUpdateUser(update);
			UserSyncDo us = new UserSyncDo();
			us.setUid(uid);
			us.setUserSync(System.currentTimeMillis());
			userService.addOrUpdateUserSync(us);
			if (null != user && user.getUid() > 4858000) {
				// 发系统结果通知
				ChatDo chat = new ChatDo();
				chat.setContent("头像审核不通过！请上传一张本人真实照片。");
				chat.setCtime(System.currentTimeMillis());
				chat.setFuid(SysUid.FACE_VERIFY.uid);
				chat.setRead(false);
				chat.setTuid(user.getUid());
				chatService.addChat(chat);
			}
		}
		return ajaxSuccessResult(callbackparam);
	}
	
	
	/**
	 * 身份证审核
	 */
	
	/**
	 * 获取某个状态身份证审核数量
	 * @param request
	 * @param response
	 * @param state
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/iv/count")
	public String getIvCount(HttpServletRequest request,
			HttpServletResponse response,
			byte state,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		return callbackparam+"("+userDao.getIdcardsCount(state)+")";
	}
	/**
	 * 获取指定状态身份证审核列表
	 * @param request
	 * @param response
	 * @param page
	 * @param state
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/iv/list")
	public String getivs(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required=false,defaultValue="0") int page,
			byte state,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		if (page<0) {
			return ajaxFailResult(callbackparam);
		}
		return ajaxResult(callbackparam,userDao.getIdcards(page,state));
	}
	
	/**
	 * 根据身份证审核id修改审核状态
	 * @param request
	 * @param response
	 * @param id
	 * @param state
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/iv/update/id")
	public String updateIv(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam int id,
			@RequestParam byte state,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		if (id<1||(state!=2&&state!=3)) {
			return ajaxFailResult(callbackparam);
		}
		userService.updateIdcardVerifyState(id, state);
		IdCardVerifyDo iv = userService.getIdcard(id);
		if (null==iv) {
			return ajaxFailResult(callbackparam);
		}
		UserDo user = userService.getUserByUid(iv.getUid());
		if (state==3) {
			//通过
			if (null != user && user.getUid() > 4858000) {
				// 发系统结果通知
				ChatDo chat = new ChatDo();
				chat.setContent("身份证认证通过！恭喜您获得图标展示及更多曝光。");
				chat.setCtime(System.currentTimeMillis());
				chat.setFuid(SysUid.IDCARD_VERIFY.uid);
				chat.setRead(false);
				chat.setTuid(user.getUid());
				chatService.addChat(chat);
			}
			if (null==user.getVId()||true!=user.getVId()) {
				/**
				 * 身份证审核只加一次权重
				 */
				userService.incUserThreshold(iv.getUid(), ThresholdEnum.V_IDCARD.value);
			}
			UserDo update = new UserDo();
			update.setUid(iv.getUid());
			update.setVId(true);
			userService.addOrUpdateUser(update);
			UserSyncDo us = new UserSyncDo();
			us.setUid(iv.getUid());
			us.setUserSync(System.currentTimeMillis());
			userService.addOrUpdateUserSync(us);
		}else{
			if (null != user && user.getUid() > 4858000) {
				// 发系统结果通知
				ChatDo chat = new ChatDo();
				chat.setContent("身份证认证失败，请上传身份证正面清晰照片。");
				chat.setCtime(System.currentTimeMillis());
				chat.setFuid(SysUid.IDCARD_VERIFY.uid);
				chat.setRead(false);
				chat.setTuid(user.getUid());
				chatService.addChat(chat);
			}
		}
		return ajaxSuccessResult(callbackparam);
	}
	
	/**
	 * 相亲树
	 */
	
	/**
	 * 获取相亲树
	 * @param request
	 * @param response
	 * @param state
	 * @param sex
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lt/list")
	public String getLoveTree(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, defaultValue = "0") int state,
			@RequestParam(required = false, defaultValue = "2") int sex,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam) {
		List<LoveTreeDo> lts = loveTreeService.getLoveTrees(state, sex,
					Long.MAX_VALUE, Integer.MAX_VALUE);
		return ajaxResult(callbackparam, lts); 
	}

	/**
	 * 更新相亲树状态
	 * @param request
	 * @param response
	 * @param id
	 * @param state
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/lt/up")
	public String updateLoveTreeState(HttpServletRequest request,
			HttpServletResponse response, 
			long id, 
			int state,
			@RequestParam(required=false,defaultValue="") String reason,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam) {
		System.out.println("in update loveTree");
		try {
			boolean rst = loveTreeService.updateLoveTreeState(id, state);
			if (rst) {
				try {
					LoveTreeDo lt = loveTreeService.getLoveTree(id);
					if (null != lt) {
						UserDo user = userService.getLoveTreeUser(lt.getUid());
						if (null != user && user.getUid() > 4858000) {
							// 发系统结果通知
							ChatDo chat = new ChatDo();
							chat.setContent((state == 1 ? "您的征友帖子审核成功" : "您的征友帖子审核由于"+reason+",审核失败,请调整后重新提交"));
							chat.setCtime(System.currentTimeMillis());
							chat.setFuid(SysUid.LOVE_TREE.uid);
							chat.setRead(false);
							chat.setTuid(lt.getUid());
							chatService.addChat(chat);
						}
					}
				} catch (Throwable e) {
					e.printStackTrace();
				}
			}
			return rst ? ajaxSuccessResult(callbackparam) : ajaxFailResult(callbackparam);
		} catch (Throwable e) {
			e.printStackTrace();
			return ajaxFailResult(callbackparam);
		}
	}

	/**
	 * 聊天 
	 */
	
	/**
	 * 获取待聊天联系人
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mo/all")
	public String getAllMoChats(HttpServletRequest request,
			HttpServletResponse response,
			int page,
			int size,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam){ 
		return ajaxResult(callbackparam, chatDao.getMos(page, size));
	}
	
	/**
	 * 获取单个聊天对象
	 * @param request
	 * @param response
	 * @param uid
	 * @param rid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mo/single")
	public String getMoChat(HttpServletRequest request,
			HttpServletResponse response,
			int uid,
			int rid,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam){ 
		List<ChatDo> chats1 = chatDao.getChats(uid, rid);
		List<ChatDo> chats2 = chatDao.getChats(rid, uid);
		List<ChatDo> all = new ArrayList<ChatDo>();
		if (null!=chats1) {
			all.addAll(chats1);
		}
		if (null!=chats2) {
			all.addAll(chats2);
		}
		Collections.sort(all, new Comparator<ChatDo>() {
			@Override
			public int compare(ChatDo o1, ChatDo o2) {
				return o1.getCtime()<o2.getCtime()?-1:1;
			}
		});
		Map<String, Object> rst = new HashMap<String, Object>();
		rst.put("chats", all);
		rst.put("user", userService.getUserByUid(uid));
		rst.put("robot", userService.getUserByUid(rid));
		return ajaxResult(callbackparam, rst);
	}
	
	/**
	 * 发送聊天信息
	 * @param request
	 * @param response
	 * @param uid
	 * @param rid
	 * @param content
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mo/send")
	public String readMo(HttpServletRequest request,
			HttpServletResponse response,
			int uid,
			int rid,
			String content,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		ChatDo chat = new ChatDo();
		chat.setFuid(rid);
		chat.setTuid(uid);
		chat.setRead(false);
		chat.setContent(content);
		chat.setCtime(System.currentTimeMillis());
		boolean rst = chatDao.addChatAndPush(chat);
		if (rst) {
			chatDao.setMoHasNew(uid, rid, false);
		}
		return rst?ajaxSuccessResult(callbackparam):ajaxFailResult(callbackparam);
	}
	
	/**
	 * 忽略某个用户聊天
	 * @param request
	 * @param response
	 * @param uid
	 * @param rid
	 * @param callbackparam
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/mo/ignore")
	public String delMo(HttpServletRequest request,
			HttpServletResponse response,
			int uid,
			int rid,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		chatDao.ignoreMo(uid, rid);
		return ajaxSuccessResult(callbackparam);
	}
	
	
	/**
	 * 建议反馈
	 */
	@ResponseBody
	@RequestMapping("/report/list")
	public String getReports(HttpServletRequest request,
			HttpServletResponse response,
			int page,
			int size,
			@RequestParam(required=false,value="callbackparam",defaultValue="callback") String callbackparam
			){
		return ajaxResult(callbackparam, reportDao.getReports(page, size));
	}
}
