package com.broad.data.hbase.flink;

import org.apache.flink.api.java.ExecutionEnvironment;

/**
 * @author : zhiyong.yue
 * @version : 1.0
 * @Date : 10:32 2018/10/31
 * @email : zhiyong.yue@qiaofang.local
 */
public class BatchJob {

    public static void main(String[] args) throws Exception {

        // set up the batch execution environment
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        /*
         * Here, you can start creating your execution plan for Flink.
         *
         * Start with getting some data from the environment, like
         *      env.readTextFile(textPath);
         *
         * then, transform the resulting DataSet<String> using operations
         * like
         *      .filter()
         *      .flatMap()
         *      .join()
         *      .coGroup()
         *
         * and many more.
         * Have a look at the programming guide for the Java API:
         *
         * http://flink.apache.org/docs/latest/apis/batch/index.html
         *
         * and the examples
         *
         * http://flink.apache.org/docs/latest/apis/batch/examples.html
         *
         */

        // execute program
        env.execute("Flink Batch Java API Skeleton");
    }
}
