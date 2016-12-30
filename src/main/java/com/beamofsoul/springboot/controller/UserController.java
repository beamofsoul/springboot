package com.beamofsoul.springboot.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.beamofsoul.springboot.entity.User;
import com.beamofsoul.springboot.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {

	@Resource
	private UserService userService;
	
	@PreAuthorize("authenticated and hasPermission('user','user:add')")
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String userAdd(User user) {
		userService.createUser(user);
		return "redirect:/user/list";
	}
	
	@PreAuthorize("authenticated and hasPermission('user','user:add')")
	@RequestMapping(value = "/addAndReset", method = RequestMethod.POST)
	@ResponseBody
	public Boolean addAndReset(@RequestBody User user) {
		userService.createUser(user);
		return true;
	}
	
	@PreAuthorize("authenticated and hasPermission('user','user:list')")
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView userList() {
		return new ModelAndView("/user/list");
	}
	
	@RequestMapping(value = "/query", method = RequestMethod.GET)
	public ModelAndView query() {
		return new ModelAndView("/user/list");
	}
	
	@PreAuthorize("authenticated and hasPermission('user','user:list')")
	@RequestMapping(value = "/listByPage", method = RequestMethod.POST, produces="application/json")
	@ResponseBody
	public JSONObject userListByPage(@RequestBody Map<String,Integer> map) {
		Sort sort = new Sort(Direction.ASC,"id");
		Pageable pageable = new PageRequest(map.get("page"),map.get("size"),sort);
		JSONObject json = new JSONObject();
		json.put("pageableData", userService.findAll(pageable));
		return json;
	}
	
	@PreAuthorize("authenticated and hasPermission('user','user:add')")
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public ModelAndView userAdd() {
		return new ModelAndView("/user/add");
	}
	
	@PreAuthorize("authenticated and hasPermission('user','user:delete')")
	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject userDelete(@RequestBody String ids) {
		String[] idStringArray = ids.split(",");
		Long[] idLongArray = new Long[idStringArray.length];
		for (int i = 0; i < idStringArray.length; i++)
			idLongArray[i] = Long.valueOf(idStringArray[i]);
		userService.deleteUsers(idLongArray);
		JSONObject result = new JSONObject();
		result.put("status", true);
		return result;
	}
	
	@PreAuthorize("authenticated and hasPermission('user','user:update')")
	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject updateStatus(@RequestBody User user ) {
		JSONObject result = new JSONObject();
		result.put("status", userService.updateStatus(user.getId(),user.getStatus()));
		return result;
	}
	
	@RequestMapping(value = "/checkUsernameUnique", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject checkUsernameUnique(@RequestBody String username) {
		JSONObject result = new JSONObject();
		result.put("status", userService.checkUsernameUnique(username));
		return result;
	}

}
