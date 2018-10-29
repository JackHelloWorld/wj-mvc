package com.wj.service.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wj.common.config.Const;
import com.wj.common.config.SerialNumberNoConfig;
import com.wj.common.utils.DatePattern;
import com.wj.common.utils.FtpUtils;
import com.wj.common.utils.Tools;
import com.wj.dao.repository.SerialNumberRepository;
import com.wj.pojo.sys.Dictionary;
import com.wj.pojo.sys.SerialNumber;
import com.wj.pojo.sys.SysResource;

public class BaseService {
	
	protected static final Logger logger = LoggerFactory.getLogger(BaseService.class);

	@Resource
	FtpUtils ftpUtils;
	
	@Resource
	SerialNumberRepository serialNumberRepository;
	
	@Resource
	protected Const constData;
	
	private static final Character[] DIC = new Character[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','1','2','3','4','5','6','7','8','9','0'};
	
	private static final Character[] DIC_NUMBER = new Character[]{'1','2','3','4','5','6','7','8','9','0'};

	protected static final ThreadLocal<Random> random = new ThreadLocal<Random>(){
		protected Random initialValue() {
			return new Random();
		};
	};

	/**
	 * 生成加密基数
	 * @param len 基数长度
	 * @return 结果字符串
	 */
	protected String generateEncry(int len){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < len; i++) {
			result.append(DIC[random.get().nextInt(36)]);
		}
		return result.toString();
	}
	
	/**
	 * 根据生成随机数字
	 * @param len 基数长度
	 * @return 结果字符串
	 */
	protected String generateNumber(int len){
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < len; i++) {
			result.append(DIC_NUMBER[random.get().nextInt(10)]);
		}
		return result.toString();
	}

	/**
	 * 生成编号
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	protected String generateNo(SerialNumberNoConfig config) {
		StringBuffer result = new StringBuffer(config.getPrefix());
		result.append(Tools.getDateToStr(DatePattern.YYYYMMDDD));
		serialNumberRepository.save(new SerialNumber(config.getColumnName()));
		Long count = serialNumberRepository.countByColumnName(config.getColumnName());
		result.append(String.format("%06d", count));
		return result.toString();
	}
	
	/**
	 * 生成企业编号
	 * @return
	 */
	@Transactional(rollbackFor = Exception.class)
	protected String generateCompanyNo() {
		serialNumberRepository.save(new SerialNumber(SerialNumberNoConfig.COMPANY_NO.getColumnName()));
		Long count = serialNumberRepository.countByColumnName(SerialNumberNoConfig.COMPANY_NO.getColumnName());
		String result = String.format("%06d", count);
		return result;
	}


	/**
	 * 文件上传,
	 * @param b byte数组
	 * @param fileName 文件名称
	 * @return 文件路径
	 */
	public String upload(byte[] b,String fileName){
		try {
			InputStream input = new ByteArrayInputStream(b);
			boolean result = ftpUtils.uploadFile(fileName, input);
			if(result)
				return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 处理资源信息
	 * @param sysResources
	 * @param parentId
	 * @return
	 */
	protected List<SysResource> initResource(List<SysResource> sysResources,Long parentId){
		List<SysResource> nodes = new ArrayList<>();
		for (SysResource sysResource : sysResources) {
			if(sysResource.getParentId().equals(parentId)){
				nodes.add(sysResource);
				List<SysResource> myNodes = initResource(sysResources,sysResource.getId());
				sysResource.setNodes(myNodes);
			}
		}
		return nodes;
	}
	
	/**
	 * 处理字典信息 
	 * @param dictionaries
	 * @param parentToken
	 * @return
	 */
	protected List<Dictionary> initDictionary(List<Dictionary> dictionaries,String parentToken){
		List<Dictionary> nodes = new ArrayList<>();
		for (Dictionary dictionary : dictionaries) {
			if(dictionary.getParentToken().equals(parentToken)){
				nodes.add(dictionary);
				List<Dictionary> myNodes = initDictionary(dictionaries,dictionary.getToken());
				dictionary.setNodes(myNodes);
			}
		}
		return nodes;
	}

}
