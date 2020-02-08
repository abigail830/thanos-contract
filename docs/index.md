
# 灭霸契约

旨在为使用TCP和定长报文的系统\
提供如灭霸弹指般轻松的契约测试

| 
Target to help the systems which still using TCP & Fix-length message to implement Contract Test. 
 
---

### Background

##### 在内外部系统依赖关系比较复杂的场景下常遇到：

![](background.jpg "常见流程")

::: incremental

- 并行开发困难，经常需要等待联调测试
- 端到端测试才发现问题，重复翻修导致效率低
- 大量端到端测试集合成本高且不稳定

:::

------

### Taget

##### 灭霸契约让各方在开发初期就协定契约

![](target.jpg "目标流程")

::: incremental

* 在开发测试过程中以同一份契约规约生产者与消费者
* 以测试驱动更高效也更高质量地并行开发，减少端到端联调的压力。
* 这在依赖关系复杂的系统间尤为重要。

:::

------

:::::::::::::: {data-transition="fade-in slide-out"}

### Difference

| 现有成熟的契约测试框架SpringCloudContract和Pact提供了基于HTTP+JSON格式的契约测试框架
| 
| 但现实仍有不少的系统（如银行核心系统）依然徘徊在TCP+定长报文的场景之中，无法使用契约测试的概念去提高质量和减轻对端到端测试的压力
| 
| **而灭霸契约则提供TCP+定长报文的契约测试支持**

:::

------

:::::::::::::: {.columns highlight-style=espresso}
::: {.column width="50%"}

### Schema


``` yaml
name: 'schema1'
version: 'v1'
provider: 'provider'
request:
  - name: MSG_TYPE
    type: CHAR
    length: 1
    content: 'regex(0|1)'
response:
  - name: SUPPLEMENT
    type: CHAR
    length: 10
    content: 'regex([a-zA-Z0-9]{10})'
```

:::
::: {.column width="50%"}

### Contract 

``` yaml
name: 'contract1'
consumer: 'consumer1'
provider: 'provider'
version: 'v2'
schema:
  name: 'schema1'
  version: 'v1'
  provider: 'provider'
req:
  MSG_TYPE: '0'
  TRAN_ASYNC: '0'
  MESSAGE_TYPE: 'schema1'
  TCODE: '123456'
  ACCT_NO: '12345678901234567'
  CHOICE: '1'
res:
  SUPPLEMENT: 'SUPPLEMENT'
  MEMO: 'This is the expected memo for choice 1  '
```

:::
::::::::::::::



---



### More Reference

| 
| 

Demo环境 <http://thanos.saraqian.cn>

Github <https://github.com/abigail830/thanos-contract>

Dev by SaraQian [abigail830@163.com](mailto:abigail830@163.com)



| 

------

![让契约测试，如灭霸弹指般轻松 ^_^](header.jpg)



