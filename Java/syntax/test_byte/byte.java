
class MainClass {
    public static void main(String args[]) {
        new WorkingClass().work();
    }
}
class WorkingClass {


    public void work() {
        byte byte_1000000 = (byte)0x80;
        byte byte_1000001 = (byte)0x81;
        byte byte_0111111 = (byte)0x7F;

        //byte_1000000: -128
        System.out.println("byte_1000000: " + byte_1000000);

        //byte_1000001: -127
        System.out.println("byte_1000001: " + byte_1000001);

        //byte_0111111: 127
        System.out.println("byte_0111111: " + byte_0111111);

        //byte is signed 
    }
}