import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    private static final String mySecretKey = "klasdjfakflsjdalkfdj";
    public static void main(String[] args) throws Exception {

         String file = "C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\file.txt";
         String secretText = readFile(file);
         String message;

        String file2 = "C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\file2.txt";
        String secretText2 = readFile(file2);



//        LSBDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//        "C:\\Users\\ramin\\Desktop");
//        message = LSBEncode("C:\\Users\\ramin\\Desktop\\LSB-test.png");
//        writeFile(file+"message.txt",message);
//
//        AESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = AESEncode("C:\\Users\\ramin\\Desktop\\LSB-AES-test.png");
//        writeFile(file+"message-ase.txt",message);
//
//        DESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = DESEncode("C:\\Users\\ramin\\Desktop\\LSB-DES-test.png");
//        writeFile(file+"message-DSE.txt",message);


//        LSBEnhanceDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//        "C:\\Users\\ramin\\Desktop");
//
//        message = LSBEnhanceEncode(
//        "C:\\Users\\ramin\\Desktop\\LSB-en-test.png");
//        writeFile(file+"message.txt",message);
//
//
//        LSBEnhanceAESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = LSBEnhanceAESEncode(
//                "C:\\Users\\ramin\\Desktop\\LSB-en-AES-test.png");
//        writeFile(file+"message-ase.txt",message);
//
//
//        LSBEnhanceDESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = LSBEnhanceDESEncode(
//        "C:\\Users\\ramin\\Desktop\\LSB-en-DES-test.png");
//        writeFile(file+"message-DSE.txt",message);
//


//        MSBDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = MSBEncode(
//                "C:\\Users\\ramin\\Desktop\\MSB-test.png");
//        writeFile(file+"message.txt",message);
//
//        MSBAESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message =MSBAESEncode(
//                "C:\\Users\\ramin\\Desktop\\MSB-AES-test.png");
//        writeFile(file+"message-ASE.txt",message);
//
//        MSBDESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message =MSBDESEncode(
//        "C:\\Users\\ramin\\Desktop\\MSB-DES-test.png");
//        writeFile(file+"message-DSE.txt",message);

//        PVDDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message =PVDEncode(
//                "C:\\Users\\ramin\\Desktop\\PVD-test.png");
//        writeFile(file+"message.txt",message);
//
//
//        PVDAESDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message =PVDAESEncode(
//                "C:\\Users\\ramin\\Desktop\\PVD-AES-test.png");
//        writeFile(file+"message-ASE.txt",message);
//
//
//        PVDDESDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message =PVDDESEncode("C:\\Users\\ramin\\Desktop\\PVD-DES-test.png");
//        writeFile(file+"message-DSE.txt",message);



//        RPEDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop",1234);
//        message = RPEEncode(
//                "C:\\Users\\ramin\\Desktop\\RPE-test.png",1234);
//        writeFile(file+"message.txt",message);
//
//
//        RPEAESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop",1234);
//        message = RPEAESEncode(
//                "C:\\Users\\ramin\\Desktop\\RPE-AES-test.png",1234);
//        writeFile(file+"message-AES.txt",message);
//
//        RPEDESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop",1234);
//        message = RPEDESEncode(
//        "C:\\Users\\ramin\\Desktop\\RPE-DES-test.png",1234);
//        writeFile(file+"message-DES.txt",message);





//        EBDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = EBEncode(
//                "C:\\Users\\ramin\\Desktop\\EB-test.png");
//        writeFile(file+"message.txt",message);
//
//
//        EBAESDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = EBAESEncode(
//                "C:\\Users\\ramin\\Desktop\\EB-AES-test.png");
//        writeFile(file+"message-AES.txt",message);
//
//
//        EBDESDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = EBDESEncode(
//        "C:\\Users\\ramin\\Desktop\\EB-DES-test.png");
//        writeFile(file+"message-DES.txt",message);


//    BPCSDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = BPCSEncode(
//                "C:\\Users\\ramin\\Desktop\\BPCS-test.png");
//        writeFile(file+"message.txt",message);
//
//        BPCSAESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = BPCSAESEncode(
//                "C:\\Users\\ramin\\Desktop\\BPCS-AES-test.png");
//        writeFile(file+"message-AES.txt",message);
//
//        BPCSDESDecode(secretText,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        message = BPCSDESEncode(
//        "C:\\Users\\ramin\\Desktop\\BPCS-DES-test.png");
//        writeFile(file+"message-DES.txt",message);

        HSDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
                "C:\\Users\\ramin\\Desktop");
        System.out.println("three");
        message = HSEncode(
                "C:\\Users\\ramin\\Desktop\\HS-test.png");
        writeFile(file+"message.txt",message);
        System.out.println("here");

        HSAESDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
                "C:\\Users\\ramin\\Desktop");
        System.out.println("three");
        message = HSAESEncode(
                "C:\\Users\\ramin\\Desktop\\HS-AES-test.png");
        writeFile(file+"message-AES.txt",message);
        System.out.println("here");

        HSDESDecode(secretText2,"C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\bird.jpg",
                "C:\\Users\\ramin\\Desktop");
        System.out.println("three");
        message = HSDESEncode("C:\\Users\\ramin\\Desktop\\HS-DES-test.png");
        writeFile(file+"message-DES.txt",message);
        System.out.println("here");



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
