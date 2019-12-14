package org.workshop.api.testing.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "testing")
public class TestingProperties {

    private Slack slack;
    private String pubSubTopic = "platformTests";
    private String pubSubSubscription = "platformTests";

    public TestingProperties() {
    }

    public Slack getSlack() {
        return slack;
    }

    public void setSlack(Slack slack) {
        this.slack = slack;
    }

    public String getPubSubTopic() {
        return pubSubTopic;
    }

    public void setPubSubTopic(String pubSubTopic) {
        this.pubSubTopic = pubSubTopic;
    }

    public String getPubSubSubscription() {
        return pubSubSubscription;
    }

    public void setPubSubSubscription(String pubSubSubscription) {
        this.pubSubSubscription = pubSubSubscription;
    }

    public static class Slack {
        private Boolean active;
        private String channel;
        private String webhook;

        public Slack() {
        }

        public Boolean getActive() {
            return active;
        }

        public String getChannel() {
            return channel;
        }

        public void setActive(Boolean active) {
            this.active = active;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }

        public String getWebhook() {
            return webhook;
        }

        public void setWebhook(String webhook) {
            this.webhook = webhook;
        }
    }

    public static class Brand {

        public Brand() {
        }

        private Map<String, String> endpointsByBrand;

        public Map<String, String> getEndpointsByBrand() {
            return endpointsByBrand;
        }

        public void setEndpointsByBrand(Map<String, String> endpointsByBrand) {
            this.endpointsByBrand = endpointsByBrand;
        }
    }

}
