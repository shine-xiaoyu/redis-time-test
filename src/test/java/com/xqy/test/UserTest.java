package com.xqy.test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.xqy.common.utils.DateUtil;
import com.xqy.common.utils.RandomUtil;
import com.xqy.common.utils.StringUtil;
import com.xqy.entity.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:redis.xml")
public class UserTest {
	@SuppressWarnings("rawtypes")
	@Autowired RedisTemplate redisTemplate;
	
	@Test
	public void testuitl() {
		System.out.println(StringUtil.getRandomLetter(RandomUtil.random(3, 20)));
		System.out.println(StringUtil.randomChineseName());
		System.out.println(StringUtil.getRandomPhoneNum());
		System.out.println(StringUtil.getRandomSex());
	}
	
	//测试Hash
	@SuppressWarnings("unchecked")
	@Test
	public void testHash() {
		HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 1; i <= 100000; i++) {
			String name = StringUtil.randomChineseName();
			String tel = StringUtil.getRandomPhoneNum();
			String sex =StringUtil.getRandomSex();
			String email = StringUtil.getRandomLetter(RandomUtil.random(3, 20))+"@qq.com";
			Date date = null;
			try {
				Date date1 = DateUtil.dateFormat.parse("1950-01-01");
				Date date2 = DateUtil.dateFormat.parse("2002-01-01");
				date = DateUtil.randomDate(date1,date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String birth = DateUtil.dateFormat.format(date);
			User user = new User(i, name, sex, tel, email, birth);
			map.put("m"+i, user.toString());
		}
		
		long start = System.currentTimeMillis();
		redisTemplate.opsForHash().putAll("usermap", map);
		long end = System.currentTimeMillis();
		
		
		
		System.out.println("Hash序列化方式");
		System.out.println("耗费用时："+(end-start)+"毫秒");
		System.out.println("保存了100000个对象");
		
	}
	
	
	//测试JSON
	@SuppressWarnings("unchecked")
	@Test
	public void testJSon() {
		ArrayList<User> list = new ArrayList<User>();
		for (int i = 1; i <= 100000; i++) {
			String name = StringUtil.randomChineseName();
			String tel = StringUtil.getRandomPhoneNum();
			String sex =StringUtil.getRandomSex();
			String email = StringUtil.getRandomLetter(RandomUtil.random(3, 20))+"@qq.com";
			Date date = null;
			try {
				Date date1 = DateUtil.dateFormat.parse("1950-01-01");
				Date date2 = DateUtil.dateFormat.parse("2002-01-01");
				date = DateUtil.randomDate(date1,date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String birth = DateUtil.dateFormat.format(date);
			User user = new User(i, name, sex, tel, email, birth);
			list.add(user);
		}
		
		long start = System.currentTimeMillis();
		redisTemplate.opsForList().leftPushAll("users", list.toArray());
		long end = System.currentTimeMillis();
		
		List list2 = redisTemplate.opsForList().range("users", 0, 3);
		for (Object a : list2) {
			System.out.println(a);
		}
		
		System.out.println("JSON序列化方式");
		System.out.println("耗费用时："+(end-start)+"毫秒");
		System.out.println("保存了100000个对象");
		
	}
	
	//测试JDK
	@SuppressWarnings("unchecked")
	@Test
	public void testJDK() {
		ArrayList<User> list = new ArrayList<User>();
		for (int i = 1; i <= 100000; i++) {
			String name = StringUtil.randomChineseName();
			String tel = StringUtil.getRandomPhoneNum();
			String sex =StringUtil.getRandomSex();
			String email = StringUtil.getRandomLetter(RandomUtil.random(3, 20))+"@qq.com";
			Date date = null;
			try {
				Date date1 = DateUtil.dateFormat.parse("1950-01-01");
				Date date2 = DateUtil.dateFormat.parse("2002-01-01");
				date = DateUtil.randomDate(date1,date2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String birth = DateUtil.dateFormat.format(date);
			User user = new User(i, name, sex, tel, email, birth);
			list.add(user);
		}
		
		long start = System.currentTimeMillis();
		redisTemplate.opsForList().leftPushAll("users", list.toArray());
		long end = System.currentTimeMillis();
		
		List list2 = redisTemplate.opsForList().range("users", 2, 6);
		for (Object a : list2) {
			System.out.println(a);
		}
		
		System.out.println("JDK序列化方式");
		System.out.println("耗费用时："+(end-start)+"毫秒");
		System.out.println("保存了100000个对象");
		
	}
	
}
