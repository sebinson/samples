package net.sebinson.sample.message.collection.common;

public class CollectConstants {

    public static final int THREAD_JVM_AVAILABLE_PROCESSORS = Runtime.getRuntime().availableProcessors();

    public static int       THREAD_SIZE_RECEIIVE_MQ         = THREAD_JVM_AVAILABLE_PROCESSORS * 50;
}
