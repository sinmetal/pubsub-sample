package org.sinmetal.pubsub.sample;

import com.google.cloud.pubsub.PubSub;
import com.google.cloud.pubsub.PubSubOptions;
import com.google.cloud.pubsub.Topic;
import com.google.cloud.pubsub.TopicInfo;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String... args) throws Exception {
		// Instantiates a client
		PubSub pubsub = PubSubOptions.defaultInstance().service();

		// The name for the new topic
		String topicName = "my-new-topic";

		// Creates the new topic
		Topic topic = pubsub.create(TopicInfo.of(topicName));

		System.out.printf("Topic %s created.%n", topic.name());
	}
}
