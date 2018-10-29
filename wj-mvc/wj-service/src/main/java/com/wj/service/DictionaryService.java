package com.wj.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wj.common.config.ResultCode;
import com.wj.common.config.ResultTools;
import com.wj.common.exception.BusinessException;
import com.wj.common.utils.AnnotationUtils;
import com.wj.common.utils.Tools;
import com.wj.dao.repository.DictionaryRepository;
import com.wj.pojo.sys.Dictionary;
import com.wj.service.utils.BaseService;

@Service
public class DictionaryService extends BaseService{

	@Resource
	DictionaryRepository dictionaryRepository;

	public ResultTools findDictionaries() {
		List<Dictionary> dictionaries = dictionaryRepository.findAllOrderBySortAsc();
		List<Dictionary> list = initDictionary(dictionaries, "0");
		return ResultTools.SUCCESS(list);
	}

	public ResultTools save(Dictionary dictionary) throws BusinessException {
		dictionary.setId(null);
		validateInfo(dictionary);
		dictionary.setStatus(0);
		dictionaryRepository.save(dictionary);
		return ResultTools.SUCCESS();
	}

	public ResultTools update(Dictionary dictionary) throws BusinessException {
		if(dictionary.getId() == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");
		if(dictionaryRepository.countByIdAndStatus(dictionary.getId(),0)==0)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "信息不存在");

		validateInfo(dictionary);
		
		dictionary.setStatus(0);
		dictionaryRepository.save(dictionary);
		return ResultTools.SUCCESS();
	}

	private void validateInfo(Dictionary dictionary) throws BusinessException{

		AnnotationUtils.validateEdit(dictionary);

		if(dictionary.getId() == null){
			if(dictionaryRepository.countByTokenAndStatus(dictionary.getToken().trim(),0) > 0)
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "token已存在").throwBusinessException();
		}else{
			Dictionary temp = dictionaryRepository.findTop1ByTokenAndStatus(dictionary.getToken().trim(),0);
			if(temp != null && !temp.getId().equals(dictionary.getId()))
				ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "token已存在").throwBusinessException();
		}

	}

	public ResultTools delete(String id) {
		if(!Tools.isLong(id))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "字典信息不存在");

		Dictionary dictionary = dictionaryRepository.findTop1ByIdAndStatus(Long.parseLong(id),0);
		if(dictionary == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "字典信息不存在");

		dictionary.setStatus(1);
		dictionaryRepository.save(dictionary);
		return ResultTools.SUCCESS();
	}



}
