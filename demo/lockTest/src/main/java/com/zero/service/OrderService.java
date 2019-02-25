package com.zero.service;

import com.zero.dao.SeckillGoodsMapper;
import com.zero.dao.SeckillOrderMapper;
import com.zero.pojo.SeckillGoods;
import com.zero.pojo.SeckillOrder;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import java.util.Random;

/**
 * @ClassName RestaruantService
 * @Description TODO
 * @Author 云析-路飞
 * @Date 2018/5/6 14:13
 * @Version 1.0
 */
@Service
public class OrderService {

    @Resource
    private SeckillOrderMapper seckillOrderMapper;

    @Resource
    private SeckillGoodsMapper seckillGoodsMapper;

    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * @method 方法描述:无锁方式抢购
     * @author 云析-路飞
     * @date 2018/5/20 18:05
     * @param
     * @return void
     */
    @Transactional
    public void seckillWithNoLock()throws  Exception{

        //库存>0
        SeckillGoods seckillGoods= seckillGoodsMapper.selectByPrimaryKey(1);
        if(null!=seckillGoods&&seckillGoods.getCount()<=0){

            System.out.println(Thread.currentThread().getName()+"商品卖光了！！！");
            return;
        }
        //库存-1 销量+1

        SeckillGoods seckillGoodsForUpdate= new SeckillGoods();
        seckillGoodsForUpdate.setCount(seckillGoods.getCount()-1);
        seckillGoodsForUpdate.setSale(seckillGoods.getSale()+1);
        int i=seckillGoodsMapper.updateByNoLock(seckillGoodsForUpdate, 1);

        if(i>0) {
            //创建订单
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setCustname(getCustName());
            seckillOrderMapper.insert(seckillOrder);
        }


    }


    /**
     * @method 方法描述:乐观锁方式抢购
     * @author 云析-路飞
     * @date 2018/5/20 18:05
     * @param
     * @return void
     */
    @Transactional
    public Integer seckillWithOptimistic()throws  Exception{

        //查询库存，如果库存大于0 则继续秒杀逻辑
        SeckillGoods seckillGoods= seckillGoodsMapper.selectByPrimaryKey(1);
        if(null!=seckillGoods&&seckillGoods.getCount()<=0){

            System.out.println(Thread.currentThread().getName()+" 乐观锁方式商品卖光了！！！当前时间="+System.currentTimeMillis());
            return -1;
        }
        //库存-1 销量+1
        Integer currentVersion= seckillGoods.getVersion();
        SeckillGoods seckillGoodsForUpdate= new SeckillGoods();
        seckillGoodsForUpdate.setVersion(currentVersion);
        seckillGoodsForUpdate.setCount(seckillGoods.getCount()-1);
        seckillGoodsForUpdate.setSale(seckillGoods.getSale()+1);
        int i=seckillGoodsMapper.updateByExampleSelective(seckillGoodsForUpdate,1, currentVersion);

        //当库存更新成功后创建订单
        if(i>0) {
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setCustname(getCustName());
            seckillOrderMapper.insert(seckillOrder);
            return 1;
        }else{
            //@todo 如果库存更新失败如何重试？
            return 0;
        }


    }




    /**
     * @method 方法描述:悲观锁方式秒杀
     * @author 云析-路飞
     * @date 2018/5/20 18:05
     * @param
     * @return void
     */
    @Transactional
    public void seckillWithPessimistic()throws  Exception{
        //悲观锁begin
        SqlSession sqlSession =sqlSessionFactory.openSession(false);
        sqlSession.getConnection().setAutoCommit(false);
        SeckillGoods seckillGoods= seckillGoodsMapper.selectByPrimaryKeyWithPessimistic(1);

        if(null!=seckillGoods&&seckillGoods.getCount()<=0){

            System.out.println(Thread.currentThread().getName()+" 悲观锁方式商品卖光了！！！当前时间="+System.currentTimeMillis());
            return;
        }
        //库存-1 销量+1

        SeckillGoods seckillGoodsForUpdate= new SeckillGoods();
        seckillGoodsForUpdate.setCount(seckillGoods.getCount()-1);
        seckillGoodsForUpdate.setSale(seckillGoods.getSale()+1);
        int i=seckillGoodsMapper.updateByNoLock(seckillGoodsForUpdate, 1);

        if(i>0) {
            //创建订单
            SeckillOrder seckillOrder = new SeckillOrder();
            seckillOrder.setCustname(getCustName());
            seckillOrderMapper.insert(seckillOrder);
        }
        sqlSession.getConnection().commit();

    }


    /**
     * @method 方法描述:使用redis原子操作保障原子性
     * @author 云析-路飞
     * @date 2018/6/5 15:53
     * @param
     * @return void
     */
    @Transactional
    public void seckillWithRedis(){
        // TODO: 2018/6/25



    }






    public static String getCustName() {
        String[] str ={"赵","钱","孙","李","周","吴","郑","王","冯","陈","褚","卫","蒋","沈","韩","杨","朱","秦","尤","许",
                "何","吕","施","张","孔","曹","严","华","金","魏","陶","姜","戚","谢","邹","喻","柏","水","窦"};

        Random r= new Random();
        int i = r.nextInt(39);
        return str[i];

    }


}
