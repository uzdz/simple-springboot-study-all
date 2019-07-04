package com.uzdz.study.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName: ProtoTest
 * @Description: ProtoBuf 测试
 * @Author: Jet.Chen
 * @Date: 2019/5/8 9:55
 * @Version: 1.0
 **/
public class ProtoTest {

    public static void main(String[] args) {
        try {
            // 按照定义的数据结构，创建一个Person
            PersonTestProtos.Person.Builder personBuilder = PersonTestProtos.Person.newBuilder();
            personBuilder.setId(1);
            personBuilder.setName("asdsa啊哈哈哈");
            PersonTestProtos.Person xxg = personBuilder.build();

            // 将数据写到输出流，如网络输出流，这里就用ByteArrayOutputStream来代替
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            xxg.writeTo(output);

            // -------------- 分割线：上面是发送方，将数据序列化后发送 ---------------

            byte[] byteArray = output.toByteArray();

            String a = "sssbc";
            byte[] byteArra1y = a.getBytes();
            // -------------- 分割线：下面是接收方，将数据接收后反序列化 ---------------

            // 接收到流并读取，如网络输入流，这里用ByteArrayInputStream来代替
            ByteArrayInputStream input = new ByteArrayInputStream(byteArra1y);

            // 反序列化
            PersonTestProtos.Person xxg2 = PersonTestProtos.Person.parseFrom(input);
            System.out.println("ID:" + xxg2.getId());
            System.out.println("name:" + xxg2.getName());

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
