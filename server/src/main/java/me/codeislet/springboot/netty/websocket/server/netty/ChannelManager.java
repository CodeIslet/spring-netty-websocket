package me.codeislet.springboot.netty.websocket.server.netty;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Component
public class ChannelManager {

    private static final String CHANNEL_ID_ATTRIBUTE_KEY = "CHANNEL_ID:";

    private final Map<String, Channel> channels;

    public ChannelManager() {
        this.channels = new ConcurrentHashMap<>();
    }

    public Channel add(String id, Channel channel) {
        setChannelIdAttributeKey(channel, id);
        this.channels.put(id, channel);
        return channel;
    }

    public Channel get(String id) {
        return this.channels.get(id);
    }

    public Channel remove(String id) {
        return this.channels.remove(id);
    }

    public Channel remove(Channel channel) {
        String id = getChannelIdAttributeKey(channel);
        if (StringUtils.isNotEmpty(id)) {
            return channels.remove(id);
        }
        return null;
    }

    AttributeKey<String> channelAttributeKey() {
        return !AttributeKey.exists(CHANNEL_ID_ATTRIBUTE_KEY) ?
                AttributeKey.newInstance(CHANNEL_ID_ATTRIBUTE_KEY) : AttributeKey.valueOf(CHANNEL_ID_ATTRIBUTE_KEY);
    }

    private String getChannelIdAttributeKey(Channel channel) {
        return channel.attr(channelAttributeKey()).get();
    }

    private void setChannelIdAttributeKey(Channel channel, String id) {
        if (channel != null && id != null) {
            channel.attr(channelAttributeKey()).set(id);
        }
    }
}
