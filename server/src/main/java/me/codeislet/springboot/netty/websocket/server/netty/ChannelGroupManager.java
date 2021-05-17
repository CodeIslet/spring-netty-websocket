package me.codeislet.springboot.netty.websocket.server.netty;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.ImmediateEventExecutor;
import me.codeislet.springboot.netty.websocket.server.exception.AlreadyChannelGroupException;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author Code Islet
 * @since 0.1.0
 */
@Component
public class ChannelGroupManager {

    private final Map<String, ChannelGroup> channelGroups;

    public ChannelGroupManager() {
        this.channelGroups = new ConcurrentHashMap<>();
    }

    public ChannelGroup get(String name) {
        return channelGroups.get(name);
    }

    public ChannelGroup create(String name) throws AlreadyChannelGroupException {
        if (get(name) != null) {
            throw new AlreadyChannelGroupException(name + " channel group already exists.");
        }
        ChannelGroup group = new DefaultChannelGroup(ImmediateEventExecutor.INSTANCE);
        return channelGroups.put(name, group);
    }

    public ChannelGroup getOrCreate(String name) {
        ChannelGroup group = get(name);
        if (group == null) {
            try {
                group = create(name);
            } catch (AlreadyChannelGroupException e) {
                return get(name);
            }
        }
        return group;
    }

    public boolean removeChannelInGroup(String name, Channel channel) {
        ChannelGroup group = get(name);
        if (group != null) {
            return group.remove(channel);
        }
        return false;
    }

    public void removeChannelInAllGroups(Channel channel) {
        channelGroups.forEach((name, group) -> removeChannelInGroup(name, channel));
    }
}