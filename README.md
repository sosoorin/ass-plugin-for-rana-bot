#  [ASS](https://github.com/wushuo894/ani-rss) Webhook 通知插件 for [Rana-Bot](https://github.com/sosoorin/RanaBot)

> 支持webhook配置后将ASS订阅的追番消息转发到群

## 配置：

1. 在你的ASS中配置Webhook: 
- **METHOD**: POST
- **URL**: `http://你的rana-bot地址:端口/rana-bot/ass/onUpdate`
- **Body**: onebot消息的JSONArray，以下为示例：
```
[
  {
    "type": "text",
    "data": {
      "text": "番剧【${title}】更新啦！"
    }
  },
  {
    "type": "image",
    "data": {
      "file": "${image}"
    }
  },
  {
    "type": "text",
    "data": {
      "text": "第${season}季 第${episode}/${totalEpisodeNumber}集 - ${bgmJpEpisodeTitle}（${bgmEpisodeTitle}）"
    }
  }
]
```

2. 在`rana-bot`中启用插件，并配置qq群