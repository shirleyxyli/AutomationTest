Feature: 网页搜索

  Scenario Outline: 在百度里搜索<text>
    Given 在远程环境
    And 打开浏览器到baidu.com
    And 输入 "<text>"
    When 点击百度一下
    Then 检验搜索结果包含"<result>"
    Then 保存截图
    Then 退出浏览器

Examples:
    |text     | result |
    |google  | Google |
    |腾讯     |  腾讯  |