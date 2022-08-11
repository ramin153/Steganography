public class Main {
    private static final String mySecretKey = "klasdjfakflsjdalkfdj";
    public static void main(String[] args) throws Exception {

//        AESDecode("i care you 2","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(AESEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop\\AES-test.png"));
//
//        DESDecode("i want to kill you 2","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//
//        System.out.println(DESEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//        "C:\\Users\\ramin\\Desktop\\DES-test.png"));

//        LSBEnhanceDecode("i want to die 5","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//        "C:\\Users\\ramin\\Desktop");
//
//        System.out.println(LSBEnhanceEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//        "C:\\Users\\ramin\\Desktop\\LSB-en-test.png"));
//
//        LSBEnhanceAESDecode("i care you but no much","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(LSBEnhanceAESEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop\\LSB-en-AES-test.png"));
//
//        LSBEnhanceDESDecode("i want to kill you 2 MSB","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//
//        System.out.println(LSBEnhanceDESEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//        "C:\\Users\\ramin\\Desktop\\LSB-en-DES-test.png"));




//        MSBDecode("i will kill myself","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(MSBEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop\\MSB-test.png"));


//        MSBAESDecode("i care you but no much 11","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(MSBAESEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop\\MSB-AES-test.png"));
//
//        MSBDESDecode("i want to kill you 2 MSB 11","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//
//        System.out.println(MSBDESEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//        "C:\\Users\\ramin\\Desktop\\MSB-DES-test.png"));


//        PVDDecode("my name is dark lord","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(PVDEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop\\PVD-test.png"));
//
//

//        PVDAESDecode("i care you but no much ","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(PVDAESEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop\\PVD-AES-test.png"));
//
//        PVDDESDecode("i want to kill you 2 PVD 11","C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//                "C:\\Users\\ramin\\Desktop");
//        System.out.println(PVDDESEncode("C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg",
//        "C:\\Users\\ramin\\Desktop\\PVD-DES-test.png"));


    }

    private static void LSBEnhanceDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSBEnhance(image).decode(code);
        image.writeImage("LSB-en-test","png",saveLocation);
    }

    private static String LSBEnhanceEncode(String rawLocation,String decodeLocation) throws Exception{
        PixelImage raw = new PixelImage(rawLocation);
        PixelImage image = new PixelImage(decodeLocation);


        return new LSBEnhance(image).encode( raw);
    }

    private static void AESDecode(String code,String location,String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSB(image).AESEncrypt(code,mySecretKey);
        image.writeImage("AES-test","png",saveLocation);

    }
    private static String AESEncode(String rawLocation,String decodeLocation) throws Exception{
        PixelImage raw = new PixelImage(rawLocation);
        PixelImage image = new PixelImage(decodeLocation);


         return new LSB (image).AESDecrypt( raw,mySecretKey);
    }

    private static void DESDecode(String code,String location,String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSB(image).DESEncrypt(code,mySecretKey);
        image.writeImage("DES-test","png",saveLocation);

    }
    private static String DESEncode(String rawLocation,String decodeLocation) throws Exception{
        PixelImage raw = new PixelImage(rawLocation);
        PixelImage image = new PixelImage(decodeLocation);


        return new LSB(image).DESDecrypt( raw,mySecretKey);
    }



    private static void LSBEnhanceAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSBEnhance(image).AESEncrypt(code,mySecretKey);
        image.writeImage("LSB-en-AES-test","png",saveLocation);

    }
    private static String LSBEnhanceAESEncode(String rawLocation, String decodeLocation) throws Exception{
        PixelImage raw = new PixelImage(rawLocation);
        PixelImage image = new PixelImage(decodeLocation);


        return new LSBEnhance(image).AESDecrypt( raw,mySecretKey);
    }

    private static void LSBEnhanceDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new LSBEnhance(image).DESEncrypt(code,mySecretKey);
        image.writeImage("LSB-en-DES-test","png",saveLocation);

    }
    private static String LSBEnhanceDESEncode(String rawLocation, String decodeLocation) throws Exception{
        PixelImage raw = new PixelImage(rawLocation);
        PixelImage image = new PixelImage(decodeLocation);


        return new LSBEnhance(image).DESDecrypt( raw,mySecretKey);
    }

    private static void MSBDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new MSB(image).decode(code);
        image.writeImage("MSB-test","png",saveLocation);
    }

    private static String MSBEncode(String rawLocation,String decodeLocation) throws Exception{
        PixelImage raw = new PixelImage(rawLocation);
        PixelImage image = new PixelImage(decodeLocation);


        return new MSB(image).encode(raw);
    }


    private static void MSBAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new MSB(image).AESEncrypt(code,mySecretKey);
        image.writeImage("MSB-AES-test","png",saveLocation);

    }
    private static String MSBAESEncode(String rawLocation, String decodeLocation) throws Exception{
        PixelImage raw = new PixelImage(rawLocation);
        PixelImage image = new PixelImage(decodeLocation);


        return new MSB(image).AESDecrypt( raw,mySecretKey);
    }

    private static void MSBDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new MSB(image).DESEncrypt(code,mySecretKey);
        image.writeImage("MSB-DES-test","png",saveLocation);

    }
    private static String MSBDESEncode(String rawLocation, String decodeLocation) throws Exception{
        PixelImage raw = new PixelImage(rawLocation);
        PixelImage image = new PixelImage(decodeLocation);


        return new MSB(image).DESDecrypt( raw,mySecretKey);
    }


    private static void PVDDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);

        new PVD(image).decode(code);
        image.writeImage("PVD-test","png",saveLocation);
    }

    private static String PVDEncode(String rawLocation,String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new PVD(image).encode(null);
    }



    private static void PVDAESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new PVD(image).AESEncrypt(code,mySecretKey);
        image.writeImage("PVD-AES-test","png",saveLocation);

    }
    private static String PVDAESEncode(String rawLocation, String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new PVD(image).AESDecrypt( null,mySecretKey);
    }

    private static void PVDDESDecode(String code, String location, String saveLocation) throws Exception{
        PixelImage image = new PixelImage(location);
        new PVD(image).DESEncrypt(code,mySecretKey);
        image.writeImage("PVD-DES-test","png",saveLocation);

    }
    private static String PVDDESEncode(String rawLocation, String decodeLocation) throws Exception{
        PixelImage image = new PixelImage(decodeLocation);


        return new PVD(image).DESDecrypt( null,mySecretKey);
    }

}
