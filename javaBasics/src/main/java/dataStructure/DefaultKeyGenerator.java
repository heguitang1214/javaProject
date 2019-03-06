////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package dataStructure;
//
//import com.google.common.base.Preconditions;
//import java.util.Calendar;
//
//public final class DefaultKeyGenerator {
//    public static final long EPOCH;
//    private static final long SEQUENCE_BITS = 12L;
//    private static final long WORKER_ID_BITS = 10L;
//    private static final long SEQUENCE_MASK = 4095L;
//    private static final long WORKER_ID_LEFT_SHIFT_BITS = 12L;
//    private static final long TIMESTAMP_LEFT_SHIFT_BITS = 22L;
//    private static final long WORKER_ID_MAX_VALUE = 1024L;
////    private static TimeService timeService = new TimeService();
//    private static long workerId;
//    private static int maxTolerateTimeDifferenceMilliseconds = 10;
//    private byte sequenceOffset;
//    private long sequence;
//    private long lastMilliseconds;
//
//    public DefaultKeyGenerator() {
//    }
//
//    public static void setWorkerId(long workerId) {
//        Preconditions.checkArgument(workerId >= 0L && workerId < 1024L);
//        workerId = workerId;
//    }
//
//    public static void setMaxTolerateTimeDifferenceMilliseconds(int maxTolerateTimeDifferenceMilliseconds) {
//        maxTolerateTimeDifferenceMilliseconds = maxTolerateTimeDifferenceMilliseconds;
//    }
//
//    public synchronized Number generateKey() {
//        long currentMilliseconds = timeService.getCurrentMillis();
//        if(this.waitTolerateTimeDifferenceIfNeed(currentMilliseconds)) {
//            currentMilliseconds = timeService.getCurrentMillis();
//        }
//
//        if(this.lastMilliseconds == currentMilliseconds) {
//            if(0L == (this.sequence = this.sequence + 1L & 4095L)) {
//                currentMilliseconds = this.waitUntilNextTime(currentMilliseconds);
//            }
//        } else {
//            this.vibrateSequenceOffset();
//            this.sequence = (long)this.sequenceOffset;
//        }
//
//        this.lastMilliseconds = currentMilliseconds;
//        return Long.valueOf(currentMilliseconds - EPOCH << 22 | workerId << 12 | this.sequence);
//    }
//
//    private boolean waitTolerateTimeDifferenceIfNeed(long currentMilliseconds) {
//        try {
//            if(this.lastMilliseconds <= currentMilliseconds) {
//                return false;
//            } else {
//                long timeDifferenceMilliseconds = this.lastMilliseconds - currentMilliseconds;
//                Preconditions.checkState(timeDifferenceMilliseconds < (long)maxTolerateTimeDifferenceMilliseconds, "Clock is moving backwards, last time is %d milliseconds, current time is %d milliseconds", new Object[]{Long.valueOf(this.lastMilliseconds), Long.valueOf(currentMilliseconds)});
//                Thread.sleep(timeDifferenceMilliseconds);
//                return true;
//            }
//        } catch (Throwable var5) {
//            throw var5;
//        }
//    }
//
//    private long waitUntilNextTime(long lastTime) {
//        long result;
//        for(result = timeService.getCurrentMillis(); result <= lastTime; result = timeService.getCurrentMillis()) {
//            ;
//        }
//
//        return result;
//    }
//
//    private void vibrateSequenceOffset() {
//        this.sequenceOffset = (byte)(~this.sequenceOffset & 1);
//    }
//
//    public static void setTimeService(TimeService timeService) {
//        timeService = timeService;
//    }
//
//    static {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2016, 10, 1);
//        calendar.set(11, 0);
//        calendar.set(12, 0);
//        calendar.set(13, 0);
//        calendar.set(14, 0);
//        EPOCH = calendar.getTimeInMillis();
//    }
//}
