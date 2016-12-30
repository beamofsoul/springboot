package com.beamofsoul.springboot.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.beamofsoul.springboot.entity.dto.UserExtension;
import com.beamofsoul.springboot.management.util.Constants;
import com.beamofsoul.springboot.management.util.UserUtil;

@Controller
public class HomeController {

	@RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		UserExtension user = (UserExtension) SecurityContextHolder.getContext().getAuthentication().getDetails();
		UserUtil.saveCurrentUser(session, user);
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpSession session, Map<String, Object> map) {
		if (session.getAttribute(Constants.CURRENT_USER) != null)
			return "redirect:/index";
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(HttpSession session, Map<String, Object> map) {
		session.invalidate();
		return "login";
	}

	@RequestMapping(value = "/expired", method = RequestMethod.GET)
	public String expired(Map<String, Object> map) {
		map.put("expired", "该账号已经在其他地方重新登录");
		return "login";
	}

}
