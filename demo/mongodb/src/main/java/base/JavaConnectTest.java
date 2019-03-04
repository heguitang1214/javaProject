package base;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.Arrays;

/**
 * @Author Tang
 * @Date 2019/2/22 14:06
 * @Version 1.0
 * @Desc 基本测试
 *      使用Java来进行连接测试
 */
public class JavaConnectTest {

    public static void main(String[] args) {
        /*
            使用密码验证
         */
        MongoCredential createCredential =
                MongoCredential.createCredential("five", "hgt", "123456".toCharArray());
        //连接到MongoDB服务
        ServerAddress serverAddress = new ServerAddress("47.93.194.11", 27017);
        MongoClient mongoClient = new MongoClient(
                serverAddress, Arrays.asList(createCredential));

        /*
            不是使用密码验证
         */
        //连接到MongoDB服务
//        MongoClient mongoClient = new MongoClient("47.93.194.11", 27017);
        //连接到数据库
        MongoDatabase db = mongoClient.getDatabase("hgt");

        MongoCollection<Document> collection = db.getCollection("users");
        MongoCursor<Document> it = collection.find().iterator();
        while(it.hasNext()){
            Document next = it.next();
            System.out.println(next.get("favorites"));
        }
    }

}
