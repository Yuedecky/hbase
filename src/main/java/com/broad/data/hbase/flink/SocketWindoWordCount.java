package com.broad.data.hbase.flink;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.util.Collector;

/**
 * @author : zhiyong.yue
 * @version : 1.0
 * @Date : 11:25 2018/10/31
 * @email : zhiyong.yue@qiaofang.local
 */
public class SocketWindoWordCount {

    public static void main(String[] args) throws Exception {
        final int port;
        try {
            final ParameterTool tool = ParameterTool.fromArgs(args);
            port = tool.getInt("port");
        } catch (Exception e) {
            System.err.println("No port specified. Please run 'SocketWindowWordCount --port <port>'");
            return;
        }
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //get input data by connecting env
        DataStream<String> textStream = env.socketTextStream("localhost", port, "\n");
        DataStream<WordWithCount> windowCount = textStream.flatMap(new FlatMapFunction<String, WordWithCount>() {
            @Override
            public void flatMap(String value, Collector<WordWithCount> collector) throws Exception {
                for (String val : value.split("\\s")) {

                    collector.collect(new WordWithCount(val, 1L));
                }
            }
        }).keyBy("word").timeWindow(Time.seconds(5), Time.seconds(1)).reduce(new ReduceFunction<WordWithCount>() {
            @Override
            public WordWithCount reduce(WordWithCount a, WordWithCount b) throws Exception {
                return new WordWithCount(a.word, a.count + b.count);
            }
        });
        windowCount.print().setParallelism(1);
        env.execute("Socket Window WordCount");
    }

    // Data type for words with count
    public static class WordWithCount {

        public String word;
        public long count;

        public WordWithCount() {
        }

        public WordWithCount(String word, long count) {
            this.word = word;
            this.count = count;
        }

        @Override
        public String toString() {
            return word + " : " + count;
        }
    }

}
