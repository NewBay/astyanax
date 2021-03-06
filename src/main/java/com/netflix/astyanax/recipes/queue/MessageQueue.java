package com.netflix.astyanax.recipes.queue;

import java.util.Map;

/**
 * Base interface for a distributed message queue.
 * 
 * Common use pattern
 * 
 *  MessageQueue queue = ...;
 *  Collection<Message> messages = queue.readMessages(10);
 *  for (Message message : messages) {
 *      try {
 *          // Do something with this message
 *      }
 *      finally {
 *          queue.ackMessage(message);
 *      }
 *  }
 *  
 * @author elandau
 *
 */
public interface MessageQueue {
    /**
     * Return the number of messages in the queue.  This is an estimate.
     * This is an expensive operation and should be used sparingly.
     * @return  Number of messages, including messages currently being processed
     */
    long getMessageCount() throws MessageQueueException;
    
    /**
     * Clear all messages in the queue
     * @throws MessageQueueException
     */
    void clearMessages() throws MessageQueueException;
    
    /**
     * Create the underlying storage for this queue
     * 
     * @throws MessageQueueException
     */
    void createQueue() throws MessageQueueException;

    /**
     * Get the counts for each shard in the queue.  This is an estimate.
     * This is an expensive operation and should be used sparingly.
     * @return
     * @throws MessageQueueException
     */
    Map<String, Integer> getShardCounts() throws MessageQueueException;
    
    /**
     * Create a consumer of the message queue.  The consumer will have it's own context
     * 
     * @return
     * @throws MessageQueueException
     */
    MessageConsumer createConsumer();

    /**
     * Create a producer of messages for this queue.
     * @return
     * @throws MessageQueueException
     */
    MessageProducer createProducer();
    
    /**
     * Return the queue's unique name
     * @return
     */
    String getName();
}
