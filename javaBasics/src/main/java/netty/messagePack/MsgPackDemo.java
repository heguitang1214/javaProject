package netty.messagePack;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.msgpack.MessagePack;

/**
 *
 * @author Tang
 * 测试Messagepack、Java序列化、JSON系列化的流大小
 *
 */
public class MsgPackDemo {

	public static void main(String[] args) {
		UserVO user = new UserVO();
		user.setId("1");
		user.setName("Five");

		MessagePack msgpack = new MessagePack();
		try {
			byte[] write = msgpack.write(user);
			//通过MessagePack序列化的流大小
			System.out.println(write);
			System.out.println(write.length);

			System.out.println(msgpack.read(write));

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream os = new ObjectOutputStream(out);
			os.writeObject(user);
			os.flush();
			os.close();
			byte[] byteArray = out.toByteArray();
			//通过Java序列化的流大小
			System.out.println(byteArray.length);

			byte[] bytes = user.toString().getBytes();
			//通过类JSON序列化的流大小
			System.out.println(bytes.length);

			UserVO read = msgpack.read(write, UserVO.class);
			System.out.println(read);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
