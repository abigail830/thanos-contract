# MockServer

## Component Diagram

<img src="https://plantuml-server.kkeisuke.app/svg/bLLDZzem4BtdLxWSMYgr6xNggQU5BFjHmJQ2eAedP981t3fsQZkmgEf_xpXVc40GcqliySoRDyzZ-rORgaoUSkyD4p7F4yWL9njZCltLznNTtQoPsUJBN8EAfJ0Wp4qiKt_AOgeI-SmW0-u7Npw6d0epdupzb6e32asBGAQP53OYimdUaLnWQ8e6OZP0Lf9pkMDYJJWJGAX8oD4oKnf7ac1gmcLCETyZHasJd2RmneEVt-VHOZowZnRJfuV7oFD2K5gAFbBNUGhgqmVIcsCfFVpVmTQFm9BjlM_xVSRre1gV-CxsE4WNenTZ_OsYiPc1sh8OogZ2GYeJ-dlMHh56jHZAN2HKxViqO-WyaV5lwsTXoL-FuDVKrD_1Sg6BHVISOl8ViAoSAyCWOqKw51BKpI-wfVNAAkTa43wHmpBoAE4hAdVBVehP0nfl2jhsRwE_LIYMseHccTsVcP4A6e2QNsn3dCATwG6eLrbmjnLnCC_GMJ4EGwgXfdJlpYtYp62FK8xP4IafJBKFQMH_IsRedj8JkccZzO7pf6th8FuQz8FaHmcSdN1Khgpau1Tj5D3a0dxfS8HTnJYueP8nQ8tKBNr01PqKf2E7miO9ECDXrKNbnCLzjkAtzm4xV0gyGm87jFSOHI5v64MexWEO0UUrhayYWHV4wOOvgR_UfW1d1eY07TuQLLclWHbjaTemrtR7DxcvapjXbIhC9r2rzcV1NQXds3dDVXciMw5Q7ZtS5mhvQp1b_zMMYyMs3cWbs219oE4GNYFL5H3kNNKX46iYSyCuMU5JO9WKLVWnK_TUmbupBEwaNRcrAcs2fYZm1QP7OJN7Cp6jcwbzKJNfnX93wqqx4RVXrnbVf7RY_vK7otcyZju4sopsK5fLdTCTIbKdTvwpCnT858JbU_4dnnOa4xskZx9x4xIVkEjEyIiGhkfoxnP4We_yVm00.svg" >


## Functions

* [x] 为每对生产者-消费者启动挡板服务。Startup diff MockServerThread, each thread serve for provider-consumer pair and via diff port
* [x] 能通过REST API查询TCP挡板服务端口。Port mapping could be get via HTTP GET via API, API doc could be found at root or http://localhost:8082/api.html
* [x] 支持Standalone单机模式，启动时文本获取契约信息，没有动态更新。When standalone mode, contract & schema would be load from file base and no dynamic update
* [x] 支持Platform平台模式，启动时从ContractService获取契约信息。 When non-standalone mode, contract & schema would be load from contract service HTTP, dynamic update is not implement yet.
* [x] REST API辅助工具：根据契约生成定长请求报文，根据接口定义解释定长报文。Also provided utils, said could generate request msg base on contract, and parse message base on schema. 

### TODO
* [ ] 预定义常用正则Validator。 Pre-define Regex Validator
* [ ] 预定义一个FuncValidator(如DateTime)。Pre-define a FuncValidator
* [ ] Platform平台模式下，接收ContractService动态更新. Support dynamic update in platform mode
* [ ] Platform模式下Rest请求ContractService需要添加重试机制. Enhance retry logic in platform mode
* [ ] 挡板服务启动时支持白名单、黑名单配置. Enhance with white list and black list config


## How to use

1. 准备好契约(/resources/contracts)和接口定义(/resource/schemas)
2. 配置application.properties, 选择所需要的模式（默认Standalone)
3. 启动MockServerMain (如为Platform模式需要先启动ContractService)
4. Rest查询/apis/mappings获取TCP端口列表
5. 指向对应端口进行正常挡板使用


