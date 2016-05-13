Feature:  登录C端

  Scenario: 登录C端
    Given 在本地环境
    When C端web用"13249792789"登录"c_test"环境
    Then 登录成功
    And 退出浏览器