//package base;
//
//import static org.springframework.data.mongodb.core.query.Criteria.where;
//import static org.springframework.data.mongodb.core.query.Query.query;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.data.mongodb.core.FindAndModifyOptions;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//
///**
// * mongodb更新测试
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-servlet.xml"})
//public class UpdateTest {
//	@Autowired
//	private MongoTemplate mongo;
//
//	/***
//	 * db.product.update({name:'Java编程思想'},{$inc:{price:10}})
//	 * db.product.update({name:'Java编程思想'},{$set:{name:'Java编程思想4'}})
//	 * db.product.update({_id:ObjectId("5c3b27bd018fb7c551f2fb28")},{$unset:{author:1}})
//	 * db.product.update({name:"Java编程思想"},{$rename:{price:'pricx'}})
//	 * db.users.update({username:"路飞","comments.movies":"倩女幽魂"},{$set:{"comments.$.content":"评论xyz"}})
//    		db.users.update({username:"路飞","comments.movies":"倩女幽魂"},{"$set":{"comments.1.context":"评论xyz"}})    //修改数组指定下标内容
//	 *
//	 * db.product.update({name:"Java编程思想4"},{$push:{"tags":'new'}})
//	 * db.product.update({name:"Java编程思想4"},{$addToSet:{"tags":'new'}})
//	 * db.product.update({name:"Java编程思想4"},{$pop:{"tags":1}})
//	 * db.product.update({name:"Java编程思想4"},{$pull:{"tags":'new'}})
//	 * db.product.update({name:"Java编程思想4"},{$pullAll:{"tags":["Java","Introduction"]}})
//	 *
//	 *
//	 *   $each+$push -->
//		    db.product.update({name:"Java编程思想4"},{$push:{"tags":{$each:['x','y']}}})
//		  $each+$addToSet -->
//		    db.product.update({name:"Java编程思想4"},{$addToSet:{"tags":{$each:['x','y']}}})
//
//		 db.product.update({name:"Java编程思想4"},{$push:{"tags":{$each:['x','y'], $slice:2}}})
//
//		 db.product.update({name:"Java编程思想4"},{$push:{'temps':{$each:[{name:'suwei',age:18}],$slice:3, $sort:{age:1}}}})
//	 */
//
//	@Test
//	public void update8(){
//		Query query = query(where("name").is("Java编程思想"));
//		Update update = new Update().push("tags").sort(Direction.DESC).slice(3).each("x","y","z");
//		mongo.updateFirst(query, update, Product.class);
//	}
//
//	@Test
//	public void update7(){
//		Query query = query(where("name").is("Java编程思想"));
//		Update update = new Update().push("tags").slice(3).each("x","y","z");
//		mongo.updateFirst(query, update, Product.class);
//	}
//
//	@Test
//	public void update6(){
//		Query query = query(where("name").is("Java编程思想"));
//		Update update = new Update().push("tags").each("x","y","z");
//		mongo.updateFirst(query, update, Product.class);
//	}
//
//	@Test
//	public void update5(){
//		Query query = query(where("name").is("Java编程思想").and("comments.movies").is("倩女幽魂"));
//		Update update = new Update().set("comments.$.content", "评论xyz");
//		mongo.updateFirst(query, update, Product.class);
//	}
//	@Test
//	public void update4(){
//		Query query = query(where("name").is("Java编程思想"));
//		Update update = new Update().rename("author", "newauthor");
//		mongo.updateFirst(query, update, Product.class);
//	}
//	@Test
//	public void update3(){
//		Query query = query(where("name").is("Java编程思想"));
//		Update update = new Update().unset("author");
//		mongo.updateFirst(query, update, Product.class);
//	}
//	@Test
//	public void update2(){
//		Query query = query(where("name").is("Java编程思想"));
//		Update update = new Update().set("name", "Java编程思想4");
//		mongo.updateFirst(query, update, Product.class);
//	}
//	@Test
//	public void update1(){
//		Query query = query(where("name").is("Java编程思想"));
//		Update update = new Update().inc("price", 10);
//		mongo.updateFirst(query, update, Product.class);
//	}
//
//
//	@Test
//	public void findAndModifyTest(){
//		Query query = query(where("name").is("Java编程思想"));
//		Update update = new Update().inc("price", 10);
//		mongo.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), Product.class);
//	}
//}
