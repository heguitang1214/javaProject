//package base;
//
//import static org.springframework.data.mongodb.core.query.Criteria.where;
//import static org.springframework.data.mongodb.core.query.Query.query;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import org.bson.types.ObjectId;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.domain.Sort.Direction;
//import org.springframework.data.domain.Sort.Order;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import ai.yunxi.sp.entity.Partner;
//import ai.yunxi.sp.entity.User;
//
///**
// *
// * @author 小五老师-云析学院
// * @createTime 2019年1月5日 下午3:33:47
// *
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring-servlet.xml"})
//public class FindTest {
//	@Autowired
//	private MongoTemplate mongo;
//
//	/***
//	 * db.users.find({'favorites.movies':'无双'})
//	 */
//	@Test
//	public void find1(){
//		Query query = query(where("favorites.movies").is("无双"));
//		List<User> find = mongo.find(query, User.class);
//		System.out.println(find.get(0).toString());
//	}
//
//	/***
//	 * db.users.find({'favorites.movies':['无双','钢铁侠','蝙蝠侠']},{'favorites.movies':1})
//	 */
//	@Test
//	public void find2(){
////		QueryBuilder queryObject = new QueryBuilder();
////		queryObject.and(new BasicDBObject("favorites.movies", "无双"));
////
////		DBObject fieldsObject = new BasicDBObject();
////		fieldsObject.put("username", "1");
////		fieldsObject.put("age", "1");
////
////		Query query = new BasicQuery(queryObject.get(), fieldsObject);
////		List<User> find = mongo.find(query, User.class);
////		System.out.println(find.get(0).toString());
//
//		Query query = query(where("favorites.movies").is("无双"));
//		query.fields().include("username").include("salary").include("favoritesssss");
//		List<User> find = mongo.find(query, User.class);
//		System.out.println(find.size());
//	}
//
//	/***
//	 * db.users.find({'favorites.movies':{'$all':['阿凡达','战狼']}},{'favorites.movies':1})
//	 */
//	@Test
//	public void find3(){
//		Query query = query(where("favorites.movies").all(Arrays.asList("阿凡达","战狼")));
//		query.fields();
//		List<User> find = mongo.find(query, User.class);
//		System.out.println(find.get(0).toString());
//	}
//
//	/***
//	 * db.users.find({},{"favorites.movies":{"$slice":[1,2]},"favorites":1})
//	 */
//	@Test
//	public void find4(){
//		Query query = new Query();
//		query.fields().slice("favorites.movies", 1, 2);
//		List<User> find = mongo.find(query, User.class);
//		for (User user : find) {
//			System.out.println(user.toString());
//		}
//	}
//
//	/***
//	 *  db.users.find({"favorites.cites":{$in:["东莞","东京"]}})
//	 */
//	@Test
//	public void find5(){
//		Query query = query(where("favorites.cites").in(Arrays.asList("东莞","东京")));
//		List<User> find = mongo.find(query, User.class);
//		System.out.println(find.get(0).toString());
//	}
//
//	/***
//	 * db.users.find({"comments":{"$elemMatch":{"movies":"倩女幽魂","content":"评论3"}}}).pretty()
//	 */
//	@Test
//	public void find6(){
//		Query query = query(where("comments").elemMatch(where("movies").is("倩女幽魂").and("content").is("评论3")));
//		List<User> find = mongo.find(query, User.class);
//		System.out.println(find.get(0).toString());
//	}
//
//	/***
//	 * sort()
//	 * skip()
//	 * limit()
//	 */
//	@Test
//	public void find7(){
////		List<Order> orders = new ArrayList<Order>();
////		orders.add(new Order(Direction.DESC, "length"));
//		Sort sort = new Sort(Direction.DESC, "length");
//		Query query = new Query();
//		query.with(sort).skip(1).limit(4);
//		List<User> find = mongo.find(query, User.class);
//		for (User user : find) {
//			System.out.println("user:"+user.getUsername()+"; length:"+user.getLength());
//		}
//	}
//
//	/***
//	 * DBRef
//	 * {
//	        "_id" : ObjectId("5c330881cf062e38286a8666"),
//	        "name" : "龙儿",
//	        "userid" : [
//	                DBRef("users", ObjectId("5c24d3255a78df1707cebbba")),
//	                DBRef("users", ObjectId("5c24d3255a78df1707cebbba"))
//	        ]
//	 * }
//	 */
//	@Test
//	public void find8(){
//
//		Query query = query(where("username").is("小五"));
//		User user = mongo.findOne(query, User.class);
//		query = query(where("username").is("路飞"));
//		User user2 = mongo.findOne(query, User.class);
//		System.out.println(user.toString());
//		System.out.println(user2.toString());
//		Partner partner = new Partner();
//		partner.setName("龙儿");
//		ArrayList<User> list = new ArrayList<User>();
//		list.add(user);
//		list.add(user2);
//		partner.setUserid(list);
//		mongo.insert(partner);
//
//		query = new Query();
//		List<Partner> find = mongo.find(query, Partner.class);
//		for (Partner obj : find) {
//			System.out.println(obj.getUserid());
//		}
//	}
//}
