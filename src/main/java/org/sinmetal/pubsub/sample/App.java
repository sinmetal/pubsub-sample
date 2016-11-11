package org.sinmetal.pubsub.sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;
import java.util.stream.Stream;

import com.google.cloud.pubsub.Message;
import com.google.cloud.pubsub.PubSub;
import com.google.cloud.pubsub.PubSubOptions;
import com.google.cloud.pubsub.ReceivedMessage;
import com.google.cloud.pubsub.Subscription;
import com.google.cloud.pubsub.SubscriptionInfo;
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

		Topic topic = pubsub.getTopic(topicName);

		if (topic == null) {
			// Creates the new topic
			topic = pubsub.create(TopicInfo.of(topicName));

			System.out.printf("Topic %s created.%n", topic.name());

		}

		Message message = Message.of("私がpayloadだパート2！");
		String messageId = pubsub.publish(topicName, message);

		System.out.printf("Message %s publish.%n", messageId);

		String subName = "my-new-sub";

		Subscription sub = pubsub.getSubscription(subName);
		if (sub == null) {
			sub = pubsub.create(SubscriptionInfo.of(topicName, subName));
			
			System.out.printf("Subscription %s created.%n", sub.name());
		}

		Consumer<ReceivedMessage> consumer = ReceivedMessage -> receiveMessage(ReceivedMessage);
		Iterator<ReceivedMessage> messages = sub.pull(3);
		messages.forEachRemaining(consumer);
	}
	
	static void receiveMessage(ReceivedMessage msg) {
		System.out.printf("ReceivedMessage ID %s. playload %s%n", msg.id(), msg.payloadAsString());
		msg.ack();
	}
}
