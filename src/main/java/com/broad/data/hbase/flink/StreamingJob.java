package com.broad.data.hbase.flink;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * @author : zhiyong.yue
 * @version : 1.0
 * @Date : 10:33 2018/10/31
 * @email : zhiyong.yue@qiaofang.local
 */
public class StreamingJob {
    public static void main(String[] args) throws Exception {

        // set up the streaming execution environment
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        /*
         * Here, you can start creating your execution plan for Flink.
         *
         * Start with getting some data from the environment, like
         *      env.readTextFile(textPath);
         *
         * then, transform the resulting DataStream<String> using operations
         * like
         *      .filter()
         *      .flatMap()
         *      .join()
         *      .coGroup()
         *
         * and many more.
         * Have a look at the programming guide for the Java API:
         *
         * http://flink.apache.org/docs/latest/apis/streaming/index.html
         *
         */

        // execute program
        env.execute("Flink Streaming Java API Skeleton");
    }
}
