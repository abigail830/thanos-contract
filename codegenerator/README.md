# Thanos CodeGenerator

## How to use

1. 准备好契约(/resources/contracts)和接口定义(/resource/schemas)
2. 配置application.properties, 选择所需要的模式（暂时只支持Standalone，也是默认)
3. CodeGeneratorMain (如为Platform模式需要先启动ContractService)
4. Rest调用/apis/generate，并提供对应contract的key信息，获取junit代码案例
5. 把代码复制到所需的项目工程中，稍修改对应的port，并在setup方法中准备好代码启动该项目工程的TCP线程，即可运行junit案例

**Remark:** 
* 可外部覆盖的配置：
    * platformMode（true/false）
    * junit.basePath（路径信息如src/test/java）
    * 如若没有配置则默认使用application.properties中的值
* 建议在目标项目工程中，把启动服务的功能抽取到一个Base.java里面，那新添加Junit的时候只需要继承这个base。 具体可以参考MockServer模块中的src/test/IntegrationBase.Java


## Functions
* [x] 为所指定的契约生成Junit测试案例。Generate Junit sample code for specified contract
* [ ] 为所指定的契约生成接口测试案例。Generate Interface test sample code for specified contract

## Sample Junit

``` Java
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import java.io.*;
import java.net.Socket;

public class Consumer2ProviderTest {

    // TODO: Please update to suitable port
    private static final Integer PORT = 54321;
    private static final String HOST = "127.0.0.1";


    @Before
    public void setup() throws Exception {
        // TODO: Please startup the TCP service here
    }

    @Test
    public void test_case_2() throws Exception {
        // Given
        String request = "00schema2123456123456789012345672";
        // When
        String response = raiseRequest(request);
        // Then
        Assert.assertEquals("SUPPLEMENT", response.substring(0, 10));
        Assert.assertTrue(response.substring(10, 11).matches("A|B"));
        Assert.assertTrue(response.substring(11, 13).matches("\\d{2}"));
    }

    private String raiseRequest(String request) throws IOException {

        Socket socket = new Socket(HOST, PORT);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        bw.write(request);
        bw.flush();
        socket.shutdownOutput();

        //Read the return message
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String response = br.readLine();
        System.out.println("Response result ：" + response);
        socket.close();

        return response;
    }

}

```

