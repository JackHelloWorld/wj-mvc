package com.wj.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.jasypt.encryption.StringEncryptor;
import org.springframework.stereotype.Service;

import com.wj.common.config.Const;
import com.wj.common.config.ResultCode;
import com.wj.common.config.ResultTools;
import com.wj.common.utils.SysTools;
import com.wj.common.utils.Tools;
import com.wj.dao.repository.AddressRepository;
import com.wj.dao.repository.SysUserRepository;
import com.wj.pojo.sys.Address;
import com.wj.pojo.sys.SysUser;
import com.wj.service.utils.BaseService;
import com.wj.service.utils.FileDto;

@Service
public class CommonService extends BaseService{

	@Resource
	AddressRepository addressRepository;
	
	@Resource
	SysUserRepository sysUserRepository;
	
	@Resource
	Const constData;
	
	@Resource
	StringEncryptor encryptor;
	
	public ResultTools findAddress(String parentCode) {
		List<Address> addresses = addressRepository.findByParentCode(parentCode);
		return ResultTools.SUCCESS(addresses);
	}

	/**
	 * 参数加密
	 * @param value 明文
	 * @return
	 */
	public ResultTools encrypt(String value) {

		if(Tools.notEmpty(value))
			return ResultTools.SUCCESS(encryptor.encrypt(value));
		
		return ResultTools.SUCCESS();
	}
	
	/**
	 * 参数解密
	 * @param value 密文
	 * @return
	 */
	public ResultTools decrypt(String value) {
		
		if(Tools.notEmpty(value))
			return ResultTools.SUCCESS(encryptor.decrypt(value));
		
		return ResultTools.SUCCESS();
	}

	public ResultTools uploadImg(FileDto fileDto) throws Exception {

		if(!fileDto.getContent_type().startsWith("image/"))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "请选择图片进行上传");
		
		String name = Tools.MD5(fileDto.getBytes()).concat(fileDto.getSuffix());
		String filePath = upload(fileDto.getBytes(), name);
		if(Tools.isEmpty(filePath))
			ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "图片上传失败").throwBusinessException();
		
		fileDto.setPath(filePath);
		fileDto.setName(name);
		fileDto.setService_path(constData.ftpImgPath);
		fileDto.setFull_path(constData.ftpImgPath.concat(filePath));
		fileDto.setUpload_time(new Date());
		return ResultTools.SUCCESS(fileDto);
	}

	public ResultTools uploadFile(FileDto fileDto) throws Exception {
		
		String name = Tools.MD5(fileDto.getBytes()).concat(fileDto.getSuffix());
		String filePath = upload(fileDto.getBytes(), name);
		if(Tools.isEmpty(filePath))
			ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "文件上传失败").throwBusinessException();
		
		fileDto.setPath(filePath);
		fileDto.setName(name);
		fileDto.setService_path(constData.ftpFilePath);
		fileDto.setFull_path(constData.ftpFilePath.concat(filePath));
		fileDto.setUpload_time(new Date());
		return ResultTools.SUCCESS(fileDto);
	
	}

	public ResultTools updateProfile(String profile, SysUser user) {
		if(Tools.isEmpty(profile))
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "无头像信息");
		SysUser sysUser = sysUserRepository.findTop1ByIdAndStatus(user.getId(), 0);
		if(sysUser == null)
			return ResultTools.DIY_ERROR(ResultCode.DataErrorCode, "您的信息有误,请重新登录");
		sysUser.setProfile(profile.trim());
		SysTools.getSession().setAttribute(constData.LOGIN_SESSION_USER, sysUser);
		sysUserRepository.save(sysUser);
		return ResultTools.SUCCESS();
	}

}
