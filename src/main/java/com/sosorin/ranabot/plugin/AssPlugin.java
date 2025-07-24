package com.sosorin.ranabot.plugin;

import com.sosorin.ranabot.annotation.RanaPlugin;
import com.sosorin.ranabot.entity.bot.IBot;
import com.sosorin.ranabot.entity.message.Message;
import com.sosorin.ranabot.http.ResponseModel;
import com.sosorin.ranabot.model.EventBody;
import com.sosorin.ranabot.model.PluginResult;
import com.sosorin.ranabot.util.MessageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author rana-bot
 * @since 2025/7/23  16:40
 */
@RanaPlugin(value = "assPlugin")
@RestController
@RequestMapping("/ass")
@Slf4j
public class AssPlugin extends AbstractPlugin {

    private static final Set<String> GROUP_ID_SET = new CopyOnWriteArraySet<>();

    @Autowired
    private IBot bot;

    public AssPlugin() {
        super("一个简单的番剧更新通知插件", "0.0.1", "RanaBot");
    }

    @Override
    public PluginResult handleEvent(IBot bot, EventBody eventBody) {
        return PluginResult.CONTINUE();
    }

    @Override
    public boolean canHandle(EventBody eventBody) {
        return false;
    }

    /**
     * 番剧更新通知
     * @param data ass的消息
     */
    @PostMapping("/onUpdate")
    public ResponseModel<?> onUpdate(@RequestBody String data) {
        if (this.isEnabled()) {
            List<Message> messages = MessageUtil.parseMessageArray(data);
            log.info("收到番剧更新消息: {}", messages);
            GROUP_ID_SET.forEach(groupId -> {
                bot.sendGroupMessage(groupId, messages);
            });
            return ResponseModel.SUCCESS();
        }
        return ResponseModel.FAIL();
    }

    /**
     * 设置插件参数
     *
     * @param params 插件参数
     * @return 是否设置成功
     */
    @Override
    public boolean setParams(Map<String, Object> params) {
        Object set = params.get("groupIdSet");
        if (set instanceof List<?>) {
            GROUP_ID_SET.clear();
            GROUP_ID_SET.addAll((List<String>) set);
        }
        return super.setParams(params);
    }

    /**
     * 获取插件参数
     *
     * @return 插件参数
     */
    @Override
    public Map<String, Object> getParams() {
        return Map.of("groupIdSet", GROUP_ID_SET);
    }

}
