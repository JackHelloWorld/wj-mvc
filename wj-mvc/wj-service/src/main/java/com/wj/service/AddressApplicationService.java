package com.wj.service;

import java.net.URL;

import javax.annotation.Resource;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wj.dao.repository.AddressRepository;
import com.wj.pojo.sys.Address;

@Service
@Transactional
public class AddressApplicationService {

	private static final Logger logger = LoggerFactory.getLogger(AddressApplicationService.class);

	@Resource
	AddressRepository addressRepository;

	public void run(){
		String url = "http://www.stats.gov.cn/tjsj/tjbz/tjyqhdmhcxhfdm/2017/";
		address1(url);
	}

	public void address1(String url){
		logger.debug("请求地址:"+url);
		try {
			Document document = Jsoup.parse(new URL(url),30*1000);
			Elements elements = document.select("table tr[class='provincetr'] a[href]");
			for (Element element : elements) {
				String href = element.attr("href");
				if(href.endsWith(".html")){
					String code = href.substring(0, href.length()-5);
					Address address = new Address();
					address.setCode(code+"0000000000");
					address.setCodeAcronym(code);
					address.setName(element.text());
					address.setParentCode("0");
					address.setLevel(1);
					addressRepository.saveAndFlush(address);
					address2(url.concat(href), code);
				}
			}
		} catch (Exception e) {
			logger.error("error url:"+url+",level:1,parentCode:0");
		}
	}

	public void address2(String url,String code){
		logger.debug("请求地址:"+url);
		try {
			Document document = Jsoup.parse(new URL(url),30*1000);
			Elements elements = document.select("table tr[class='citytr']");
			for (Element element : elements) {
				String href = element.select("a[href]").get(0).attr("href");
				Element element2 = element.select("td").get(1);
				String coded = element.select("td").get(0).text();
				Address address = new Address();
				address.setCode(coded);
				address.setCodeAcronym(coded.replaceAll("0+$", ""));
				address.setName(element2.text());
				address.setParentCode(code);
				address.setLevel(2);
				addressRepository.saveAndFlush(address);
				address3(url.substring(0, url.length()-5).concat("/").concat(href.split("/")[1]), coded.replaceAll("0+$", ""));
			}
		} catch (Exception e) {
			logger.error("error url:"+url+",level:2,parentCode:"+code);
		}
	}

	public void address3(String url,String code){
		logger.debug("请求地址:"+url);
		try {
			Document document = Jsoup.parse(new URL(url),30*1000);
			Elements elements = document.select("table tr[class='countytr']");
			for (Element element : elements) {
				Element element2 = element.select("td").get(1);
				String coded = element.select("td").get(0).text();
				Address address = new Address();
				address.setCode(coded);
				address.setCodeAcronym(coded.replaceAll("0+$", ""));
				address.setName(element2.text());
				address.setParentCode(code);
				address.setLevel(3);
				addressRepository.saveAndFlush(address);
				if(element.select("a[href]").size() > 0){
					String href = element.select("a[href]").get(0).attr("href");
					String[] urls = url.split("/");
					urls[urls.length - 1] = "";
					StringBuffer urlb = new StringBuffer();
					for (String string : urls) {
						urlb.append(string).append("/");
					}
					urlb.append(href);
					address4(urlb.toString(), coded.replaceAll("0+$", ""));
				}
			}
		} catch (Exception e) {
			logger.error("error url:"+url+",level:3,parentCode:"+code);
		}
	}

	public void address4(String url,String code){
		logger.debug("请求地址:"+url);
		try {
			Document document = Jsoup.parse(new URL(url),30*1000);
			Elements elements = document.select("table tr[class='towntr']");
			for (Element element : elements) {
				Element element2 = element.select("td").get(1);
				String coded = element.select("td").get(0).text();
				Address address = new Address();
				address.setCode(coded);
				address.setCodeAcronym(coded.replaceAll("0+$", ""));
				address.setName(element2.text());
				address.setParentCode(code);
				address.setLevel(4);
				addressRepository.saveAndFlush(address);
				if(element.select("a[href]").size() > 0){

					String href = element.select("a[href]").get(0).attr("href");
					String[] urls = url.split("/");
					urls[urls.length - 1] = "";
					StringBuffer urlb = new StringBuffer();
					for (String string : urls) {
						urlb.append(string).append("/");
					}
					urlb.append(href);
					//address5(urlb.toString(), coded.replaceAll("0+$", ""));

				}
			}
		} catch (Exception e) {
			logger.error("error url:"+url+",level:4,parentCode:"+code);
		}

	}

	public void address5(String url,String code){
		logger.debug("请求地址:"+url);
		try {
			Document document = Jsoup.parse(new URL(url),30*1000);
			Elements elements = document.select("table tr[class='villagetr']");
			for (Element element : elements) {
				Element element2 = element.select("td").get(2);
				String coded = element.select("td").get(0).text();
				Address address = new Address();
				address.setCode(coded);
				address.setCodeAcronym(coded.replaceAll("0+$", ""));
				address.setName(element2.text());
				address.setParentCode(code);
				address.setLevel(5);
				address.setCountryside(element.select("td").get(1).text());
				addressRepository.saveAndFlush(address);
			}
		} catch (Exception e) {
			logger.error("error url:"+url+",level:5,parentCode:"+code);
		}

	}


}
