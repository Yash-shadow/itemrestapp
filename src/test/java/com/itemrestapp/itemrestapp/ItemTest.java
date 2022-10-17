/**
 * 
 */
package com.itemrestapp.itemrestapp;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.dao.ItemDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.model.Item;

import io.swagger.annotations.Example;

// @SpringBootTest enables us to use autowiring  here 



@SpringBootTest
class ItemTest {
	
	@Autowired
	ItemDao itemdao;	
	
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void addtest() {
		Item itemExpected = new Item();
		
		itemExpected.setItemName("Coffee");
		itemExpected.setPrice(20);
		itemExpected.setQuantity(20);
		itemdao.save(itemExpected);
		
		Item itemactual = itemdao.findById(itemExpected.getItemId()).get();
		Assertions.assertEquals(itemExpected.getItemName(), itemactual.getItemName());
		
	}
	@Test
	void addtestcount() {
		Item item = new Item();
		
		item.setItemName("defaultx");
		item.setPrice(20);
		item.setQuantity(20);
		itemdao.save(item);
		
		Item item1 = new Item();
		item1.setItemName("defaultx");
		item1.setPrice(20);
		item1.setQuantity(20);
		itemdao.save(item1);
		int expected = 2;
		int actual = itemdao.countByItemName("defaultx");
		Assertions.assertEquals(expected,actual);
		
	}
	@Test
	void addtestfirstname() {
		Item item  = new Item();
		
		item.setItemName("damn");
		item.setPrice(20);
		item.setQuantity(20);
		itemdao.save(item);
		
		Item item1 = itemdao.findByItemName("damn");
		
		
		Assertions.assertTrue(item.toString().equals(item1.toString()));
	
		
	}
	@Test
	void deleteTest() {
		Item item  = new Item();
		
		item.setItemName("damns");
		item.setPrice(20);
		item.setQuantity(20);
		itemdao.save(item);
		itemdao.delete(item);
		
		Item item1 = itemdao.findByItemName("damns");
		
		
		Assertions.assertNull(item1);
		
		
	}
	
	//rest controller test
	@Test
    void test1() throws URISyntaxException, JsonProcessingException {
      
       
 
      
      
      RestTemplate template=new RestTemplate();
      final String url="http://localhost:8083/findbyId/1";
      URI uri=new URI(url);
      
      ResponseEntity<String> res=template.getForEntity(uri,String.class);
      Assertions.assertEquals(HttpStatus.OK,res.getStatusCode());
      
  }
	
	@Test
	public void addanitemtest() throws URISyntaxException 
	{
	    RestTemplate restTemplate = new RestTemplate();
	     
	    final String baseUrl = "http://localhost:8083/addanitem";
	    URI uri = new URI(baseUrl);
	    Item item = new Item();
		
		item.setItemName("fsdfsd");
		item.setPrice(20);
		item.setQuantity(20);
		itemdao.save(item);
		HttpHeaders headers=new HttpHeaders();
	     
		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     
		 HttpEntity<Item> entity=new HttpEntity<Item>(item,headers);
	 
		ResponseEntity<String> result = restTemplate.exchange("http://localhost:8083/addanitem",
                HttpMethod.POST,entity,String.class);
		System.out.println(result.getBody());
	     
	    //Verify request succeed
	    Assertions.assertEquals(202, result.getStatusCodeValue());
	   Assertions.assertEquals("item added successfully", result.getBody());
	}
	@Test
	public void getallitems() throws URISyntaxException 
	{
	    RestTemplate restTemplate = new RestTemplate();
	   
	    
		HttpHeaders headers=new HttpHeaders();
	     
		 headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	     
		 HttpEntity<List<Item>> entity=new HttpEntity<List<Item>>(headers);
	 
		ResponseEntity<List>  result = restTemplate.exchange("http://localhost:8083/getallitems",
                HttpMethod.GET,entity,List.class);
		System.out.println(result.getBody());
		
		List<Item> temp = itemdao.findAll();
		
		//Assertions.assertEquals(temp,result.getBody());
		
		//assertTrue(temp.equals(result.getBody()));
		
		Assertions.assertEquals(temp.toString(),result.getBody().toString());
		
	    //Verify request succeed
	    Assertions.assertEquals(200, result.getStatusCodeValue());
	// Assertions.assertEquals("item added successfully", result.getBody());
	 
	 		
	}


}
