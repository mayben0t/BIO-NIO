package nio;

import java.nio.IntBuffer;
import java.nio.channels.Channel;

public class Maa {

    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

        for(int i=0;i<intBuffer.capacity();i++){
            intBuffer.put(i*2);
        }

//        intBuffer.flip();

        intBuffer.position(1);
        intBuffer.limit(3);

        System.out.println(intBuffer.hasArray());
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }

    }
}
