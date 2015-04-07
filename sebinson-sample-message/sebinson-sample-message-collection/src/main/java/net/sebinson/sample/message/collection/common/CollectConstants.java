package net.sebinson.sample.message.collection.common;

public class CollectConstants {

    public static final int THREADS_JVM_AVAILABLEPROCESSORS = Runtime.getRuntime().availableProcessors();

    public static int       THREADS_SIZE_OBTAIN_MQ          = THREADS_JVM_AVAILABLEPROCESSORS * 50;
}
