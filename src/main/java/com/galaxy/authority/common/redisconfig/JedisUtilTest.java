package com.galaxy.authority.common.redisconfig;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.galaxy.authority.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@WebAppConfiguration
public class JedisUtilTest {/*
    
	//测试redis是否连接上
    JedisPool pool;
    Jedis jedis;
    @Before
    public void setUp() {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
    }
    
    //转换器json
  	Gson gson = new Gson();
  	
  	@Autowired
	private IRedisCache<String,test> cache;
  	
	@Test
    public void testSet(){
    	test tt =new test();
    	tt.setAge(20);
    	tt.setUsername("liuli");
    	//序列化
    	//jedis.set("test".getBytes(), SerializeUtil.serialize(tt));
    	//转json
    	//String s = gson.toJson(tt);
    	//jedis.set("test1",s);
    	cache.put("test2", tt);
    }
    @Test
    public void testGet(){
    	//反序列
    	//byte[] tt = jedis.get(("test").getBytes());
    	//test t=(test) SerializeUtil.unserialize(tt);
    	 //redisTemplate.opsForValue().get("test2");
        System.out.println(cache.get("test2"));
    }
    
*/}