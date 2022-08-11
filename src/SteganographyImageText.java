public interface SteganographyImageText {
    public  void decode( String code) throws Exception ;
    public  String encode(PixelImage rawImage);

    public  void DESEncrypt(String code,String secretKey) throws Exception ;
    public  void AESEncrypt(String code,String secretKey) throws Exception ;

    public  String DESDecrypt(PixelImage rawImage,String secretKey) throws Exception ;
    public String AESDecrypt(PixelImage rawImage,String secretKey) throws Exception ;
}
