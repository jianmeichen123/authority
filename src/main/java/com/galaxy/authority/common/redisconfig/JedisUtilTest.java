package com.galaxy.authority.common.redisconfig;

public class JedisUtilTest {
	
	/*@Autowired
    private JedisPool jedisPool;   
      
    public void set(String key, String value) throws Exception {  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            jedis.set(key, value);  
        } finally {  
            //返还到连接池  
            jedis.close();  
        }  
    }  
      
    public String get(String key) throws Exception  {  
  
        Jedis jedis = null;  
        try {  
            jedis = jedisPool.getResource();  
            return jedis.get(key);  
        } finally {  
            //返还到连接池  
            jedis.close();  
        }  
    }  
    
    public static void main(String[] args) {
    	JedisUtilTest test = new JedisUtilTest();
    	try {
			test.set("age", "20");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}*/
	    
    
	//测试redis是否连接上
    /*JedisPool pool;
    Jedis jedis;
    @Before
    public void setUp() {
        pool = new JedisPool(new JedisPoolConfig(), "127.0.0.1");
        jedis = pool.getResource();
    }
    @Test
    public void testSet(){
    	System.out.println(jedis.set("name", "lili"));
    }
    @Test
    public void testGet(){
        System.out.println(jedis.get("name"));
    }*/
    
}