package com.example.mq;

import javax.jms.JMSException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NamingException;
import java.util.Hashtable;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.mq.jms.MQQueue;
import com.ibm.mq.jms.MQTopicConnectionFactory;
import com.ibm.mq.jms.MQTopic;
import com.ibm.msg.client.wmq.WMQConstants;

public class MQBindingsGenerator {
    public static void main(String[] args) {
        try {
            // JNDI FSContext environment
            Hashtable<String, String> env = new Hashtable<>();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
            String providerUrl = System.getProperty("jndi.provider.url");
            // Ensure the provider URL is set
            if (providerUrl == null || providerUrl.isEmpty()) {
                System.err.println("ERROR: Please define the system property 'jndi.provider.url'. " +
                        "Example: -Djndi.provider.url=file:/your/custom/path");
                System.exit(1);
            }

            env.put(Context.PROVIDER_URL, providerUrl); // Path where .bindings will be created
            Context context = new InitialContext(env);

            // === Queue Connection Factory ===
            MQQueueConnectionFactory qcf = new MQQueueConnectionFactory();
            qcf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            qcf.setHostName("localhost");
            qcf.setPort(1414);
            qcf.setQueueManager("QM1");
            qcf.setChannel("DEV.ADMIN.SVRCONN");

            context.bind("myQueueConnectionFactory", qcf);

            // === Queue Destination ===
            MQQueue queue = new MQQueue("QUEUE1");
            context.bind("myQueue", queue);

            // === Topic Connection Factory ===
            MQTopicConnectionFactory tcf = new MQTopicConnectionFactory();
            tcf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            tcf.setHostName("localhost");
            tcf.setPort(1414);
            tcf.setQueueManager("QM1");
            tcf.setChannel("DEV.ADMIN.SVRCONN");

            context.bind("myTopicConnectionFactory", tcf);

            // === Topic Destination ===
            MQTopic topic = new MQTopic("TOPIC1");
            context.bind("myTopic", topic);

            System.out.println("All JMS objects bound successfully.");
        } catch (NameAlreadyBoundException e){
            System.err.println("ERROR: A JMS .binding file object with the same name is already bound. Please check.");
            e.printStackTrace();
            System.exit(1);
        } catch (NamingException | JMSException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
