package com.wj.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.wj.common.annotation.ValidateAuth;
import com.wj.common.annotation.ValidateIgnoreLogin;
import com.wj.common.config.ResultCode;
import com.wj.common.config.ResultTools;
import com.wj.controller.utils.BaseController;
import com.wj.service.CommonService;
import com.wj.service.utils.FileDto;

@RestController
@RequestMapping("common")
public class CommonController extends BaseController{

	@Resource
	CommonService commonService;
	
	@PostMapping("/get/address")
	@ValidateAuth(validate=false)
	public ResultTools address(@RequestParam(value="parent_code",defaultValue="0")String parentCode){
		return commonService.findAddress(parentCode);
	}
	
	@ValidateIgnoreLogin
	@GetMapping("encrypt/{value}")
	public ResultTools encrypt(@PathVariable("value")String value){
		return commonService.decrypt(value);
	}
	
	/**
	 * 图片上传
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@ValidateIgnoreLogin
	@PostMapping("upload/img")
	public ResultTools uploadImg(@RequestParam(value="file",required=false) MultipartFile file) throws Exception{
		
		if(file == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "未选择图片");
		
		FileDto fileDto = new FileDto();
		fileDto.setBytes(file.getBytes());
		fileDto.setContent_type(file.getContentType());
		fileDto.setOld_name(file.getOriginalFilename());
		
		return commonService.uploadImg(fileDto);
	}
	
	/**
	 * 普通文件上传
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@ValidateIgnoreLogin
	@PostMapping("upload/file")
	public ResultTools uploadFile(@RequestParam(value="file",required=false) MultipartFile file) throws Exception{
		
		if(file == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "未选择文件");
		
		FileDto fileDto = new FileDto();
		fileDto.setBytes(file.getBytes());
		fileDto.setContent_type(file.getContentType());
		fileDto.setOld_name(file.getOriginalFilename());
		
		return commonService.uploadFile(fileDto);
	
	}
	
	/**
	 * 修改头像
	 * @param file
	 * @return
	 * @throws Exception 
	 */
	@ValidateAuth(validate=false)
	@PostMapping("update_profile")
	public ResultTools updateProfile(@RequestParam(value="profile",defaultValue="") String profile){
		
		return commonService.updateProfile(profile,getUser());
		
	}
	
}
