package com.taojin.iot.base.comm.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.web.multipart.MultipartFile;

/**
 * 类型:文件上传工具类
 * ============================================================================
 * 版权所有 2013-2016 无锡淘金网络科技有限公司，并保留所有权利。
 * ----------------------------------------------------------------------------
 * 提示：在未取得UcSkype商业授权之前，您不能将本软件应用于商业用途，否则UcSkype将保留追究的权力。
 * ----------------------------------------------------------------------------
 * 官方网站：http://www.ucskype.com/
 * ----------------------------------------------------------------------------
 * 下午5:09:48
 * author sjn
 * ============================================================================
 */
@Deprecated
public class FileUploadUtil {

	public enum FileType{
		JPG(".jpg"),
		PNG(".png"),
		GIF(".gif"),
		WAV(".wav"),
		DOC(".doc"),
		PDF(".pdf");
		private String fileSuffix;
		FileType(String fileSuffix){
			this.fileSuffix = fileSuffix;
		}
		
		public String getFileSuffix() {
			return fileSuffix;
		}
		
	}
	
	/**
	 * springMvc文件上传
	 * @param request
	 * @param files MultipartFile[]文件数组
	 * @param folderNames String[] 所存文件夹名   如{"a","b","c"}为a/b/c
	 * @param fileType  文件类型 枚举FileType  传空则为原文件类型
	 * @param isOriginalName  是否以原名存在,boolean  true 是  false 则为uuid随机字符串为名称
	 * @return json   成功：{"errcode":"0","errmsg":"上传成功","filePaths":"["文件所在路径/upload/****.abc","文件所在路径/upload/****.abc","文件所在路径/upload/****.abc"],"fileSizes":["2155","313","5641"]}文件大小单位为字节 
	 * 				      失败：{"errcode":"-1","errmsg":"错误信息"}
	 */
	public static JSONObject springMvcFileUploads(HttpServletRequest request, MultipartFile[] files,String [] folderNames,FileType fileType,boolean isOriginalName){
		JSONObject jsonResult = new JSONObject();
		if(files == null || files.length ==0){
			jsonResult.put("errcode","1");
			jsonResult.put("errmsg", "没有可上传文件");
			return jsonResult;
		}
		String requestPath = request.getSession().getServletContext().getRealPath("/");
		StringBuffer dirPath = new StringBuffer();
		dirPath.append("upload");
		if(folderNames != null && folderNames.length != 0){
			for(String dir :folderNames){
				dirPath.append("/");
				dirPath.append(dir);
			}
		}
		dirPath.append("/");
		File file = new File(requestPath+dirPath.toString());
		if(!file.exists() && !file.isDirectory()){
			boolean isCreate = file.mkdirs();
			if(!isCreate){
				jsonResult.put("errcode","-1");
				jsonResult.put("errmsg", "创建文件夹失败");
				return jsonResult;
			}
		}
		String path = requestPath+dirPath.toString();
		int len = files.length;
		List<Object> filePathList = new ArrayList<Object>();
		List<Object> fileSizeList = new ArrayList<Object>();
		try {
			if(fileType==null){
				for(int i=0;i<len;i++){
					String fileName="";
					if(isOriginalName){
						fileName = files[i].getOriginalFilename();
					}else{
						fileName = CommonUtil.getUUID();
						if(-1 != files[i].getOriginalFilename().lastIndexOf(".")){
							fileName = fileName+files[i].getOriginalFilename().substring(fileName.lastIndexOf(".")+1);
						}
					}
					files[i].transferTo(new File(path+fileName));
					filePathList.add("/"+dirPath.toString()+fileName);
					fileSizeList.add(files[i].getSize());
				}
			}else{
				for(int i=0;i<len;i++){
					String fileName="";
					if(isOriginalName){
						fileName = files[i].getOriginalFilename();
						if(-1 == files[i].getOriginalFilename().lastIndexOf(".")){//此文件没有后缀，补后缀
							fileName = fileName+".aaa";
						}
					}else{
						fileName = CommonUtil.getUUID()+".";
					}
					files[i].transferTo(new File(path+fileName.substring(0,fileName.lastIndexOf("."))+fileType.getFileSuffix()));
					filePathList.add("/"+dirPath.toString()+fileName.substring(0,fileName.lastIndexOf("."))+fileType.getFileSuffix());
					fileSizeList.add(files[i].getSize());
				}
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			jsonResult.put("errcode","-1");
			jsonResult.put("errmsg", "[异常]：上传失败");
			return jsonResult;
		} catch (IOException e) {
			e.printStackTrace();
			jsonResult.put("errcode","-1");
			jsonResult.put("errmsg", "[异常]：上传失败");
			return jsonResult;
		}
		jsonResult.put("errcode", "0");
		jsonResult.put("errmsg", "上传成功");
		
		jsonResult.put("filePaths",filePathList.toArray());
		jsonResult.put("fileSizes",fileSizeList.toArray());
		return jsonResult;
	}
	/**
	 * springMvc文件上传
	 * @param request
	 * @param files MultipartFile文件
	 * @param folderNames String[] 所存文件夹名   如{"a","b","c"}为a/b/c
	 * @param fileType  文件类型 枚举FileType  传空则为原文件类型
	 * @param isOriginalName  是否以原名存在,boolean  true 是  false 则为uuid随机字符串为名称
	 * @return json   成功：{"errcode":"0","errmsg":"上传成功","filePaths":"["文件所在路径/upload/****.abc","文件所在路径/upload/****.abc","文件所在路径/upload/****.abc"],"fileSizes":["2155","313","5641"]}文件大小单位为字节 
	 * 				      失败：{"errcode":"-1","errmsg":"错误信息"}
	 */
	public static JSONObject springMvcFileUpload(HttpServletRequest request, MultipartFile file,String [] folderNames,FileType fileType,boolean isOriginalName){
		JSONObject jsonResult = new JSONObject();
		if(file == null){
			jsonResult.put("errcode","1");
			jsonResult.put("errmsg", "没有可上传文件");
			return jsonResult;
		}
		String requestPath = request.getSession().getServletContext().getRealPath("/");
		StringBuffer dirPath = new StringBuffer();
		dirPath.append("upload");
		if(folderNames != null && folderNames.length != 0){
			for(String dir :folderNames){
				dirPath.append("/");
				dirPath.append(dir);
			}
		}
		dirPath.append("/");
		File fileDir = new File(requestPath+dirPath.toString());
		if(!fileDir.exists() && !fileDir.isDirectory()){
			boolean isCreate = fileDir.mkdirs();
			if(!isCreate){
				jsonResult.put("errcode","-1");
				jsonResult.put("errmsg", "创建文件夹失败");
				return jsonResult;
			}
		}
		String path = requestPath+dirPath.toString();
		String filePath="";
		Long fileSize;
		try {
			if(fileType==null){
				String fileName="";
				if(isOriginalName){
					fileName = file.getOriginalFilename();
				}else{
					fileName = CommonUtil.getUUID();
					if(-1 != file.getOriginalFilename().lastIndexOf(".")){
						fileName = fileName+file.getOriginalFilename().substring(fileName.lastIndexOf(".")+1);
					}
				}
				file.transferTo(new File(path+fileName));
				filePath = "/"+dirPath.toString()+fileName;
				fileSize = file.getSize();
			}else{
				String fileName="";
				if(isOriginalName){
					fileName = file.getOriginalFilename();
					if(-1 == file.getOriginalFilename().lastIndexOf(".")){//此文件没有后缀，补后缀
						fileName = fileName+".aaa";
					}
				}else{
					fileName = CommonUtil.getUUID()+".";
				}
				file.transferTo(new File(path+fileName.substring(0,fileName.lastIndexOf("."))+fileType.getFileSuffix()));
				filePath="/"+dirPath.toString()+fileName.substring(0,fileName.lastIndexOf("."))+fileType.getFileSuffix();
				fileSize=file.getSize();
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
			jsonResult.put("errcode","-1");
			jsonResult.put("errmsg", "[异常]：上传失败");
			return jsonResult;
		} catch (IOException e) {
			e.printStackTrace();
			jsonResult.put("errcode","-1");
			jsonResult.put("errmsg", "[异常]：上传失败");
			return jsonResult;
		}
		
		jsonResult.put("errcode", "0");
		jsonResult.put("errmsg", "上传成功");
		jsonResult.put("filePath",filePath);
		jsonResult.put("fileSize",fileSize);
		return jsonResult;
	}
	
	
}
