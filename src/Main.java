import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    private static final String mySecretKey = "klasdjfakflsjdalkfdj";
    public static void main(String[] args) throws Exception {



//        LSBDecode("fuck","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//        "C:\\Users\\ramin\\Desktop");
//        System.out.println(LSBEncode("C:\\Users\\ramin\\Desktop\\LSB-test.png"));
//
//
//        AESDecode("i care you 333","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(AESEncode("C:\\Users\\ramin\\Desktop\\LSB-AES-test.png"));
//
//        DESDecode("i want to kill you 555","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(DESEncode("C:\\Users\\ramin\\Desktop\\LSB-DES-test.png"));

//        LSBEnhanceDecode("i want to die 5555","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//        "C:\\Users\\ramin\\Desktop");
//
//        System.out.println(LSBEnhanceEncode(
//        "C:\\Users\\ramin\\Desktop\\LSB-en-test.png"));
//
//        LSBEnhanceAESDecode("i care you but no much mmm","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(LSBEnhanceAESEncode(
//                "C:\\Users\\ramin\\Desktop\\LSB-en-AES-test.png"));
//
//        LSBEnhanceDESDecode("i want to kill you 2 MSB 1234","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//
//        System.out.println(LSBEnhanceDESEncode(
//        "C:\\Users\\ramin\\Desktop\\LSB-en-DES-test.png"));




//        MSBDecode("i will kill myself 345","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(MSBEncode(
//                "C:\\Users\\ramin\\Desktop\\MSB-test.png"));
//
//
//        MSBAESDecode("i care you but no much 3243","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(MSBAESEncode(
//                "C:\\Users\\ramin\\Desktop\\MSB-AES-test.png"));
//
//        MSBDESDecode("i want to kill you 2 MSB 234342","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//
//        System.out.println(MSBDESEncode(
//        "C:\\Users\\ramin\\Desktop\\MSB-DES-test.png"));


//        PVDDecode("my name is dark lord","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(PVDEncode(
//                "C:\\Users\\ramin\\Desktop\\PVD-test.png"));
//
//

//        PVDAESDecode("i care you but no much ","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(PVDAESEncode(
//                "C:\\Users\\ramin\\Desktop\\PVD-AES-test.png"));
//
//        PVDDESDecode("i want to kill you 2 PVD 11","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(PVDDESEncode(
//        "C:\\Users\\ramin\\Desktop\\PVD-DES-test.png"));




//        RPEDecode("my name is dark lord 2323","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop",1234);
//        System.out.println(RPEEncode(
//                "C:\\Users\\ramin\\Desktop\\RPE-test.png",1234));
//
//
//
//        RPEAESDecode("i care you but no much 999","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop",1234);
//        System.out.println(RPEAESEncode(
//                "C:\\Users\\ramin\\Desktop\\RPE-AES-test.png",1234));
//
//        RPEDESDecode("i want to kill you 2 RPE 11","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop",1234);
//        System.out.println(RPEDESEncode(
//        "C:\\Users\\ramin\\Desktop\\RPE-DES-test.png",1234));


//        onlyCheckDCTDOSENTHAVEERROR("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\test.jpg");
//        System.out.println("\n\n\nEND");




//        EBDecode("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec ultrices erat. Nunc non egestas ipsum, vitae sollicitudin neque. Donec nulla tellus, imperdiet in accumsan id, efficitur id lacus. Cras rutrum ligula tincidunt, aliquet nulla at, molestie mauris. Integer non bibendum neque. Donec eros justo, porttitor sit amet ante faucibus, suscipit venenatis ex. Duis blandit nunc et sem porttitor, eget porta felis dictum.\n" +
//                        "\n" +
//                        "Integer at ullamcorper erat, non consequat mauris. Aenean non lobortis neque, ut fermentum ante. Cras in enim auctor, hendrerit nibh sed, tincidunt augue. Fusce lobortis aliquet nibh, quis posuere sapien mattis non. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus vel turpis ac arcu maximus hendrerit. Aenean quis ultricies nisi. Maecenas ut felis sagittis, pulvinar nisl id, sodales neque. Maecenas ante nisl, blandit in viverra non, tincidunt et orci. Integer libero leo, ultricies luctus magna sit amet, molestie facilisis dui.\n" +
//                        "\n" +
//                        "Donec lorem sapien, accumsan nec mauris ac, pellentesque facilisis neque. Etiam eros lectus, dictum sit amet sem non, hendrerit auctor dolor. Sed auctor orci vitae aliquet pellentesque. Etiam quam purus, ultrices et ultrices in, accumsan at risus. In accumsan congue sem, eu accumsan magna porta vel. Mauris in erat vitae lectus convallis gravida sed in mi. Morbi tincidunt, sapien vitae semper consequat, sem urna pretium neque, consequat placerat enim sem iaculis quam. Cras leo ante, dignissim id tempus vel, ultrices sit amet mauris. Sed sodales sapien ac neque semper eleifend. Quisque molestie, nibh ut interdum fringilla, quam neque porta nisi, quis laoreet arcu dolor sed enim. Donec accumsan tincidunt nulla in euismod. Proin aliquam tincidunt mi, eget posuere ex venenatis eget.\n" +
//                        "\n" +
//                        "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec dignissim dignissim nisl quis sollicitudin. Praesent quis venenatis ipsum. Sed egestas mauris dapibus tincidunt efficitur. Pellentesque est lectus, tempor vitae fringilla non, vestibulum non quam. Donec id orci diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut sit amet nibh sit amet dui tempor auctor id ac augue. Mauris consequat maximus efficitur. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n" +
//                        "\n" +
//                        "Morbi luctus efficitur nulla nec auctor. Nulla volutpat finibus ligula, ut pellentesque lorem luctus vel. Vestibulum et varius lorem. Morbi ultrices gravida orci sit amet porta. Sed tristique semper tortor in convallis. Vestibulum sit amet consectetur lacus. Praesent mi risus, condimentum non odio ut, viverra eleifend arcu. Donec rhoncus tellus nibh. Duis euismod mattis justo ac cursus. Curabitur dictum, eros id facilisis fringilla, felis sapien molestie justo, in scelerisque neque lorem ac lectus. Donec sodales blandit risus, ut ornare mi. Nam facilisis augue lacus, a condimentum arcu posuere in. In fringilla tortor sit amet ullamcorper aliquet. Maecenas viverra blandit felis eget convallis." +
//                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec ultrices erat. Nunc non egestas ipsum, vitae sollicitudin neque. Donec nulla tellus, imperdiet in accumsan id, efficitur id lacus. Cras rutrum ligula tincidunt, aliquet nulla at, molestie mauris. Integer non bibendum neque. Donec eros justo, porttitor sit amet ante faucibus, suscipit venenatis ex. Duis blandit nunc et sem porttitor, eget porta felis dictum.\n" +
//                        "\n" +
//                        "Integer at ullamcorper erat, non consequat mauris. Aenean non lobortis neque, ut fermentum ante. Cras in enim auctor, hendrerit nibh sed, tincidunt augue. Fusce lobortis aliquet nibh, quis posuere sapien mattis non. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus vel turpis ac arcu maximus hendrerit. Aenean quis ultricies nisi. Maecenas ut felis sagittis, pulvinar nisl id, sodales neque. Maecenas ante nisl, blandit in viverra non, tincidunt et orci. Integer libero leo, ultricies luctus magna sit amet, molestie facilisis dui.\n" +
//                        "\n" +
//                        "Donec lorem sapien, accumsan nec mauris ac, pellentesque facilisis neque. Etiam eros lectus, dictum sit amet sem non, hendrerit auctor dolor. Sed auctor orci vitae aliquet pellentesque. Etiam quam purus, ultrices et ultrices in, accumsan at risus. In accumsan congue sem, eu accumsan magna porta vel. Mauris in erat vitae lectus convallis gravida sed in mi. Morbi tincidunt, sapien vitae semper consequat, sem urna pretium neque, consequat placerat enim sem iaculis quam. Cras leo ante, dignissim id tempus vel, ultrices sit amet mauris. Sed sodales sapien ac neque semper eleifend. Quisque molestie, nibh ut interdum fringilla, quam neque porta nisi, quis laoreet arcu dolor sed enim. Donec accumsan tincidunt nulla in euismod. Proin aliquam tincidunt mi, eget posuere ex venenatis eget.\n" +
//                        "\n" +
//                        "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec dignissim dignissim nisl quis sollicitudin. Praesent quis venenatis ipsum. Sed egestas mauris dapibus tincidunt efficitur. Pellentesque est lectus, tempor vitae fringilla non, vestibulum non quam. Donec id orci diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut sit amet nibh sit amet dui tempor auctor id ac augue. Mauris consequat maximus efficitur. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n" +
//                        "\n" +
//                        "Morbi luctus efficitur nulla nec auctor. Nulla volutpat finibus ligula, ut pellentesque lorem luctus vel. Vestibulum et varius lorem. Morbi ultrices gravida orci sit amet porta. Sed tristique semper tortor in convallis. Vestibulum sit amet consectetur lacus. Praesent mi risus, condimentum non odio ut, viverra eleifend arcu. Donec rhoncus tellus nibh. Duis euismod mattis justo ac cursus. Curabitur dictum, eros id facilisis fringilla, felis sapien molestie justo, in scelerisque neque lorem ac lectus. Donec sodales blandit risus, ut ornare mi. Nam facilisis augue lacus, a condimentum arcu posuere in. In fringilla tortor sit amet ullamcorper aliquet. Maecenas viverra blandit felis eget convallis." +
//                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec ultrices erat. Nunc non egestas ipsum, vitae sollicitudin neque. Donec nulla tellus, imperdiet in accumsan id, efficitur id lacus. Cras rutrum ligula tincidunt, aliquet nulla at, molestie mauris. Integer non bibendum neque. Donec eros justo, porttitor sit amet ante faucibus, suscipit venenatis ex. Duis blandit nunc et sem porttitor, eget porta felis dictum.\n" +
//                        "\n" +
//                        "Integer at ullamcorper erat, non consequat mauris. Aenean non lobortis neque, ut fermentum ante. Cras in enim auctor, hendrerit nibh sed, tincidunt augue. Fusce lobortis aliquet nibh, quis posuere sapien mattis non. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus vel turpis ac arcu maximus hendrerit. Aenean quis ultricies nisi. Maecenas ut felis sagittis, pulvinar nisl id, sodales neque. Maecenas ante nisl, blandit in viverra non, tincidunt et orci. Integer libero leo, ultricies luctus magna sit amet, molestie facilisis dui.\n" +
//                        "\n" +
//                        "Donec lorem sapien, accumsan nec mauris ac, pellentesque facilisis neque. Etiam eros lectus, dictum sit amet sem non, hendrerit auctor dolor. Sed auctor orci vitae aliquet pellentesque. Etiam quam purus, ultrices et ultrices in, accumsan at risus. In accumsan congue sem, eu accumsan magna porta vel. Mauris in erat vitae lectus convallis gravida sed in mi. Morbi tincidunt, sapien vitae semper consequat, sem urna pretium neque, consequat placerat enim sem iaculis quam. Cras leo ante, dignissim id tempus vel, ultrices sit amet mauris. Sed sodales sapien ac neque semper eleifend. Quisque molestie, nibh ut interdum fringilla, quam neque porta nisi, quis laoreet arcu dolor sed enim. Donec accumsan tincidunt nulla in euismod. Proin aliquam tincidunt mi, eget posuere ex venenatis eget.\n" +
//                        "\n" +
//                        "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec dignissim dignissim nisl quis sollicitudin. Praesent quis venenatis ipsum. Sed egestas mauris dapibus tincidunt efficitur. Pellentesque est lectus, tempor vitae fringilla non, vestibulum non quam. Donec id orci diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut sit amet nibh sit amet dui tempor auctor id ac augue. Mauris consequat maximus efficitur. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n" +
//                        "\n" +
//                        "Morbi luctus efficitur nulla nec auctor. Nulla volutpat finibus ligula, ut pellentesque lorem luctus vel. Vestibulum et varius lorem. Morbi ultrices gravida orci sit amet porta. Sed tristique semper tortor in convallis. Vestibulum sit amet consectetur lacus. Praesent mi risus, condimentum non odio ut, viverra eleifend arcu. Donec rhoncus tellus nibh. Duis euismod mattis justo ac cursus. Curabitur dictum, eros id facilisis fringilla, felis sapien molestie justo, in scelerisque neque lorem ac lectus. Donec sodales blandit risus, ut ornare mi. Nam facilisis augue lacus, a condimentum arcu posuere in. In fringilla tortor sit amet ullamcorper aliquet. Maecenas viverra blandit felis eget convallis." +
//                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec ultrices erat. Nunc non egestas ipsum, vitae sollicitudin neque. Donec nulla tellus, imperdiet in accumsan id, efficitur id lacus. Cras rutrum ligula tincidunt, aliquet nulla at, molestie mauris. Integer non bibendum neque. Donec eros justo, porttitor sit amet ante faucibus, suscipit venenatis ex. Duis blandit nunc et sem porttitor, eget porta felis dictum.\n" +
//                        "\n" +
//                        "Integer at ullamcorper erat, non consequat mauris. Aenean non lobortis neque, ut fermentum ante. Cras in enim auctor, hendrerit nibh sed, tincidunt augue. Fusce lobortis aliquet nibh, quis posuere sapien mattis non. Interdum et malesuada fames ac ante ipsum primis in faucibus. Phasellus vel turpis ac arcu maximus hendrerit. Aenean quis ultricies nisi. Maecenas ut felis sagittis, pulvinar nisl id, sodales neque. Maecenas ante nisl, blandit in viverra non, tincidunt et orci. Integer libero leo, ultricies luctus magna sit amet, molestie facilisis dui.\n" +
//                        "\n" +
//                        "Donec lorem sapien, accumsan nec mauris ac, pellentesque facilisis neque. Etiam eros lectus, dictum sit amet sem non, hendrerit auctor dolor. Sed auctor orci vitae aliquet pellentesque. Etiam quam purus, ultrices et ultrices in, accumsan at risus. In accumsan congue sem, eu accumsan magna porta vel. Mauris in erat vitae lectus convallis gravida sed in mi. Morbi tincidunt, sapien vitae semper consequat, sem urna pretium neque, consequat placerat enim sem iaculis quam. Cras leo ante, dignissim id tempus vel, ultrices sit amet mauris. Sed sodales sapien ac neque semper eleifend. Quisque molestie, nibh ut interdum fringilla, quam neque porta nisi, quis laoreet arcu dolor sed enim. Donec accumsan tincidunt nulla in euismod. Proin aliquam tincidunt mi, eget posuere ex venenatis eget.\n" +
//                        "\n" +
//                        "Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Donec dignissim dignissim nisl quis sollicitudin. Praesent quis venenatis ipsum. Sed egestas mauris dapibus tincidunt efficitur. Pellentesque est lectus, tempor vitae fringilla non, vestibulum non quam. Donec id orci diam. Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. Ut sit amet nibh sit amet dui tempor auctor id ac augue. Mauris consequat maximus efficitur. Interdum et malesuada fames ac ante ipsum primis in faucibus.\n" +
//                        "\n" +
//                        "Morbi luctus efficitur nulla nec auctor. Nulla volutpat finibus ligula, ut pellentesque lorem luctus vel. Vestibulum et varius lorem. Morbi ultrices gravida orci sit amet porta. Sed tristique semper tortor in convallis. Vestibulum sit amet consectetur lacus. Praesent mi risus, condimentum non odio ut, viverra eleifend arcu. Donec rhoncus tellus nibh. Duis euismod mattis justo ac cursus. Curabitur dictum, eros id facilisis fringilla, felis sapien molestie justo, in scelerisque neque lorem ac lectus. Donec sodales blandit risus, ut ornare mi. Nam facilisis augue lacus, a condimentum arcu posuere in. In fringilla tortor sit amet ullamcorper aliquet. Maecenas viverra blandit felis eget convallis." +
//                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed nec ultrices erat. Nunc non egestas ipsum, vitae sollicitudin neque. Donec nulla tellus, imperdiet in accumsan id, efficitur id lacus. Cras rutrum ligula tincidunt, aliquet nulla at, molestie mauris. Integer non bibendum neque. Donec eros justo, porttitor sit amet ante faucibus, suscipit venenatis ex. Duis blandit nunc et sem porttitor, eget porta felis dictum.\n" +
//                        "\n" +"fuck 23","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(EBEncode(
//                "C:\\Users\\ramin\\Desktop\\EB-test.png"));

//        EBAESDecode("i care you but no much 992131239","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(EBAESEncode(
//                "C:\\Users\\ramin\\Desktop\\EB-AES-test.png"));
//
//        EBDESDecode("i want to kill you 2 eb 13253251","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(EBDESEncode(
//        "C:\\Users\\ramin\\Desktop\\EB-DES-test.png"));


//    BPCSDecode("i am king of all dragon dragon night","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(BPCSEncode(
//                "C:\\Users\\ramin\\Desktop\\BPCS-test.png"));
//        BPCSAESDecode("i care you but no much 222222","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(BPCSAESEncode(
//                "C:\\Users\\ramin\\Desktop\\BPCS-AES-test.png"));
//
//        BPCSDESDecode("i want to kill you 2 BPCS 11111","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(BPCSDESEncode(
//        "C:\\Users\\ramin\\Desktop\\BPCS-DES-test.png"));
//
//
//    HSDecode("king of king ruler of woods an something i dont know .... spam it ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(HSEncode(
//                "C:\\Users\\ramin\\Desktop\\HS-test.png"));
//
//        HSAESDecode("i care you but no much 565656","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(HSAESEncode(
//                "C:\\Users\\ramin\\Desktop\\HS-AES-test.png"));
//
//        HSDESDecode("i want to kill you 2 HS 11111","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(HSDESEncode(
//        "C:\\Users\\ramin\\Desktop\\HS-DES-test.png"));
//
        String file = "C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\";


        checkUTF8(file+"file.txt",file+"result2.txt",
                "C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
                "C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY");
    }

    private static void checkUTF8(String readFile,String writeFile, String location,String writeLocation) throws Exception {
        PixelImage image = new PixelImage(location);
        String read = readFile(readFile);
        new LSB(image).decode(read);
        System.out.println(read);
        image.writeImage("LSB-utf8-test", "png", writeLocation);
        System.out.println();
        writeFile(writeFile,new LSB(new PixelImage(writeLocation+"\\LSB-utf8-test.png")).encode());
    }

    private static void LSBEnhanceDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSBEnhance(image).decode(code);
        image.writeImage("LSB-en-test","png",saveLocation);
    }

    private static String LSBEnhanceEncode(String decodeLocation) throws Exception{

        PixelImage image = new PixelImage(decodeLocation);


        return new LSBEnhance(image).encode( );
    }

    private static void LSBDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSB(image).decode(code);
        image.writeImage("LSB-test","png",saveLocation);
    }

    private static String LSBEncode(String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new LSB(image).encode( );
    }


    private static void AESDecode(String code,String location,String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSB(image).AESEncrypt(code,mySecretKey);
        image.writeImage("LSB-AES-test","png",saveLocation);

    }
    private static String AESEncode(String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


         return new LSB (image).AESDecrypt( mySecretKey);
    }

    private static void DESDecode(String code,String location,String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSB(image).DESEncrypt(code,mySecretKey);
        image.writeImage("LSB-DES-test","png",saveLocation);

    }
    private static String DESEncode(String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new LSB(image).DESDecrypt( mySecretKey);
    }



    private static void LSBEnhanceAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSBEnhance(image).AESEncrypt(code,mySecretKey);
        image.writeImage("LSB-en-AES-test","png",saveLocation);

    }
    private static String LSBEnhanceAESEncode( String decodeLocation) throws Exception{

        PixelImage image = new PixelImage(decodeLocation);


        return new LSBEnhance(image).AESDecrypt( mySecretKey);
    }

    private static void LSBEnhanceDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSBEnhance(image).DESEncrypt(code,mySecretKey);
        image.writeImage("LSB-en-DES-test","png",saveLocation);

    }
    private static String LSBEnhanceDESEncode( String decodeLocation) throws Exception{

        PixelImage image = new PixelImage(decodeLocation);


        return new LSBEnhance(image).DESDecrypt( mySecretKey);
    }

    private static void MSBDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new MSB(image).decode(code);
        image.writeImage("MSB-test","png",saveLocation);
    }

    private static String MSBEncode(String decodeLocation) throws Exception{

        PixelImage image = new PixelImage(decodeLocation);


        return new MSB(image).encode();
    }


    private static void MSBAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new MSB(image).AESEncrypt(code,mySecretKey);
        image.writeImage("MSB-AES-test","png",saveLocation);

    }
    private static String MSBAESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new MSB(image).AESDecrypt( mySecretKey);
    }

    private static void MSBDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new MSB(image).DESEncrypt(code,mySecretKey);
        image.writeImage("MSB-DES-test","png",saveLocation);

    }
    private static String MSBDESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new MSB(image).DESDecrypt( mySecretKey);
    }


    private static void PVDDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);

        new PVD(image).decode(code);
        image.writeImage("PVD-test","png",saveLocation);
    }

    private static String PVDEncode(String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new PVD(image).encode();
    }



    private static void PVDAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new PVD(image).AESEncrypt(code,mySecretKey);
        image.writeImage("PVD-AES-test","png",saveLocation);

    }
    private static String PVDAESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new PVD(image).AESDecrypt( mySecretKey);
    }

    private static void PVDDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new PVD(image).DESEncrypt(code,mySecretKey);
        image.writeImage("PVD-DES-test","png",saveLocation);

    }
    private static String PVDDESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new PVD(image).DESDecrypt( mySecretKey);
    }


    private static void RPEDecode(String code, String location, String saveLocation,int seed) throws Exception{
        PixelImage image = new PixelImage(location);

        new RandomPixelEmbedding(image).decode(code,seed);
        image.writeImage("RPE-test","png",saveLocation);


    }

    private static String RPEEncode(String decodeLocation,int seed) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new RandomPixelEmbedding(image).encode(seed);
    }



    private static void RPEAESDecode(String code, String location, String saveLocation,int seed) throws Exception{
        PixelImage image = new PixelImage(location);
        new RandomPixelEmbedding(image).AESEncrypt(code,mySecretKey,seed);
        image.writeImage("RPE-AES-test","png",saveLocation);

    }
    private static String RPEAESEncode( String decodeLocation,int seed) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new RandomPixelEmbedding(image).AESDecrypt( mySecretKey,seed);
    }

    private static void RPEDESDecode(String code, String location, String saveLocation,int seed) throws Exception{
        PixelImage image = new PixelImage(location);
        new RandomPixelEmbedding(image).DESEncrypt(code,mySecretKey,seed);
        image.writeImage("RPE-DES-test","png",saveLocation);

    }
    private static String RPEDESEncode( String decodeLocation,int seed) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new RandomPixelEmbedding(image).DESDecrypt( mySecretKey,seed);
    }



    private static void EBDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);

        new EdgeBased(image).decode(code);
        image.writeImage("EB-test","png",saveLocation);


    }

    private static String EBEncode(String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new EdgeBased(image).encode();
    }


    private static void EBAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new EdgeBased(image).AESEncrypt(code,mySecretKey);
        image.writeImage("EB-AES-test","png",saveLocation);

    }
    private static String EBAESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new EdgeBased(image).AESDecrypt( mySecretKey);
    }

    private static void EBDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new EdgeBased(image).DESEncrypt(code,mySecretKey);
        image.writeImage("EB-DES-test","png",saveLocation);

    }
    private static String EBDESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new EdgeBased(image).DESDecrypt( mySecretKey);
    }

    private static void BPCSDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);

        new BPCS(image,0.3).decode(code);
        image.writeImage("BPCS-test","png",saveLocation);


    }

    private static String BPCSEncode(String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new BPCS(image,0.3).encode();
    }

    private static void BPCSAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new BPCS(image,0.3).AESEncrypt(code,mySecretKey);
        image.writeImage("BPCS-AES-test","png",saveLocation);

    }
    private static String BPCSAESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new BPCS(image,0.3).AESDecrypt( mySecretKey);
    }

    private static void BPCSDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new BPCS(image,0.3).DESEncrypt(code,mySecretKey);
        image.writeImage("BPCS-DES-test","png",saveLocation);

    }
    private static String BPCSDESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new BPCS(image,0.3).DESDecrypt( mySecretKey);
    }


    private static void HSDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);

        new HistogramShift(image).decode(code);
        image.writeImage("HS-test","png",saveLocation);



    }

    private static String HSEncode(String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new HistogramShift(image).encode();
    }

    private static void HSAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new HistogramShift(image).AESEncrypt(code,mySecretKey);
        image.writeImage("HS-AES-test","png",saveLocation);

    }
    private static String HSAESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new HistogramShift(image).AESDecrypt( mySecretKey);
    }

    private static void HSDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new HistogramShift(image).DESEncrypt(code,mySecretKey);
        image.writeImage("HS-DES-test","png",saveLocation);

    }
    private static String HSDESEncode( String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new HistogramShift(image).DESDecrypt( mySecretKey);
    }

    private static String readFile(String filePath)
    {

        StringBuilder stringBuilder = new StringBuilder();
        try (Stream stream = Files.lines( Paths.get(filePath), StandardCharsets.UTF_8))
        {
            stream.forEach(s -> stringBuilder.append(s).append("\n"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private static void writeFile(String filePath,String text)
    {
        try (FileWriter fw = new FileWriter(new File(filePath), StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(fw)) {


            writer.append(text);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void onlyCheckDCTDOSENTHAVEERROR(String location) throws Exception {
        PixelImage image = new PixelImage(location);

        new DCT(image).decode("testetsssssssssssssssssssssssssssssssssssssss");

        new DCT(image).encode();
    }



}
