package datastructure;

/**
 * @Author heguitang
 * @Date 2019/3/8 14:57
 * @Version 1.0
 * @Desc 雪花算法
 */
public class SnowflakeTest {
    /**
     * 开始时间戳（2015-01-01）
     */
    private final long twepoch = 1420041600000L;

    private final long workerIdBits = 5L;

    private final long datacenterIdBits = 5L;

    private final long maxWorkerId = -1L ^ (-1L << workerIdBits);

    private final long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);

    private final long sequenceBits = 12L;

    private final long workerIdShift = sequenceBits;

    private final long datacenterIdShift = sequenceBits + workerIdBits;

    private final long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long workerId = 0L;

    private long datacenterId = 0L;

    private long sequence = 0L;

    private long lastTimestamp = -1L;


    public synchronized long nextId() {
        long timestamp = timeGen();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds"));
        }
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;

            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }
        lastTimestamp = timestamp;

        return ((timestamp - twepoch) << timestampLeftShift)
                | (datacenterId << datacenterIdShift)
                | (workerId << workerIdShift)
                | sequence;
    }


    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public void getorderId() {
        System.out.println("insert into order_id(id) values ('" + nextId() + " );");
    }

    public static void main(String[] args) {
        SnowflakeTest snowflakeTest = new SnowflakeTest();
        snowflakeTest.getorderId();
    }

}
