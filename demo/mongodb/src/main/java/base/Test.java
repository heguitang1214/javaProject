package base;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @Author heguitang
 * @Date 2019/2/22 14:06
 * @Version 1.0
 * @Desc 基本测试
 */
public class Test {

    public static void main(String[] args) {
        //连接到MongoDB服务
        MongoClient mongoClient = new MongoClient("47.93.194.11", 27017);
        //连接到数据库
        MongoDatabase db = mongoClient.getDatabase("five");

        MongoCollection<Document> collection = db.getCollection("users");
        MongoCursor<Document> it = collection.find().iterator();
        while(it.hasNext()){
            Document next = it.next();
            System.out.println(next.get("favorites"));
        }

    }

}
