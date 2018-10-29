package com.wj.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wj.common.annotation.ValidateAuth;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;
import com.wj.controller.utils.BaseController;
import com.wj.pojo.sys.Dictionary;
import com.wj.service.DictionaryService;

@RestController
@RequestMapping("dictionary")
public class DictionaryController extends BaseController{

	@Resource
	DictionaryService dictionaryService;
	
	@PostMapping("find")
	@ValidateAuth("/dictionary")
	public ResultTools findDictionaries(){
		return dictionaryService.findDictionaries();
	}
	
	@PostMapping("save")
	public ResultTools save(Dictionary dictionary) throws BusinessException{
		return dictionaryService.save(dictionary);
	}
	
	@PostMapping("update")
	public ResultTools update(Dictionary dictionary) throws BusinessException{
		return dictionaryService.update(dictionary);
	}
	
	@PostMapping("delete")
	public ResultTools delete(String id) throws BusinessException{
		return dictionaryService.delete(id);
	}
	
}
