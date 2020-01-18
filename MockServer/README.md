# MockServer

## Component Diagram

<img src="https://plantuml-server.kkeisuke.app/png/bLLBZzem4BxpArmEBPNQZLhrrDEosMS5smY2gfwGIGPmwzYfuooBglxtZXCxE663s5nYZsU--UPbNnQQAbrcVF26YOINAPIAawtMUV4r21JTNMoOtfQhiW2LIA51w8j4Pi6C9LIbyf513Zm8ltoCE1LwCPq46IqqA1GjGvdbKXYBt3XuHqg1fXaAYDu2MKlEvOw93U5C0A4Y9QrZfWfSIOAk2PS9vNoF68ucENOmc8n_Vb_4oydDRRoSFTpTnuD11AgGOeJKYp83zUa36Iumb27-h-4fYC6G7Rxlwdt6yz2kZtJdUrnaovjdRVIreecUWtfY2THMbOHO4UeFZ8nYZ6fv9KkHKhKVqPoXybGclusUWIL_1mI_9gRH3bRBeZf4pHaw_m4hgsm5uvnLxZ2He2v-qIVgJjOb9-FeWHoEaKSDRwbShqOPUWzfigrecx-n_cPH3BKfpNDJdxcM2Xe0noyn9bxWtlO0DBGIhBOY7kOBTDQCmnKjm56wzVS6SQwnHoX7xuYKLYAdGvgqloLpLC_e4TsioVM1yxGhwo7-6lH3obiEl4fuMRPISj2BjmfeUWA_LcXXMniFDr8oWQ90weO-O0Oz5wJ7XyB62Jd3fUsYUkFZFfhaT-k07Ju3tfC23tHu7ySHkRk9CRjteCUSkxm-Y1IU4QSVvYX-LwOGTng8W1tU6ZQiDenBM3yJHwEusbTCWWtekivESf9RPn0iiN6QaaEpdnFP6H3-dN3246CY2yquMUELgvaKrhpDr9z_hAbcIIzjgzQ9j36QOO9FC6sPEOuls7HkWEw5qBYRI3HrHJjARiElrxvoxTd_gnlOUoHQTwzf5jFy9glU54LIEUVUi_72eCPXLD_BVqfiGJ8jDcva_8dhFYNdJSih4Cxgyi4bY1GVq_y0.png" >

Note: Async communication between threads which using Guava EventBus is not shown in above diagram

## Functions

* [x] 为每对生产者-消费者启动挡板服务。Startup diff MockServerThread, each thread serve for provider-consumer pair and via diff port
* [x] 能通过REST API查询TCP挡板服务端口。Port mapping could be get via HTTP GET via API, API doc could be found at root or http://localhost:8082/api.html
* [x] 支持Standalone单机模式，启动时文本获取契约信息，没有动态更新。When standalone mode, contract & schema would be load from file base and no dynamic update
* [x] REST API辅助工具：根据契约生成定长请求报文，根据接口定义解释定长报文。Also provided utils, said could generate request msg base on contract, and parse message base on schema.
* [x] 支持Platform平台模式，启动时从ContractService获取契约信息。 When non-standalone mode, contract & schema would be load from contract service HTTP, dynamic update is not implement yet.
* [x] Platform平台模式下，接收ContractService动态更新. Support dynamic update in platform mode 

#### TODO
* [ ] 预定义常用正则Validator。 Pre-define Regex Validator
* [ ] 预定义一个FuncValidator(如DateTime)。Pre-define a FuncValidator
* [ ] Platform模式下Rest请求ContractService需要添加重试机制. Enhance retry logic in platform mode
* [ ] 挡板服务启动时支持白名单、黑名单配置. Enhance with white list and black list config


## How to use

1. 准备好契约(/resources/contracts)和接口定义(/resource/schemas)
2. 配置application.properties, 选择所需要的模式（默认Standalone)
3. 启动MockServerMain (如为Platform模式需要先启动ContractService)
4. Rest查询/apis/mappings获取TCP端口列表
5. 指向对应端口进行正常挡板使用


