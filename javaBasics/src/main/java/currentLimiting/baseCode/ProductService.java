package currentLimiting.baseCode;

/**
 * 服务接口
 */
public interface ProductService {

    //商品列表
    void list() throws Exception;

    //商品详情
    void info(String name) throws Exception;
}
