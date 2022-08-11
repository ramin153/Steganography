import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PixelImage {
    private BufferedImage img;

    public PixelImage(String path) throws IOException {
        File file= new File(path);
        img = ImageIO.read(file);
    }

    public PixelImage(BufferedImage img) {
        this.img = img;
    }

    public void writeImage(String name,String format,String path) throws IOException {
        File outputfile = new File(path+"\\"+name+"."+format);
        ImageIO.write(img, format, outputfile);
    }

    public int getHeight(){
        return img.getHeight();
    }

    public int getWidth(){
        return img.getWidth();
    }

    public int getNumberOfPixel(){
        return getWidth() * getHeight();
    }

    public int getRGBPixelColor(int heightPos,int widthPos){


        return img.getRGB(widthPos,heightPos);
    }

    public void setRGBPixelColor(int heightPos,int widthPos,int rgbColor){
        img.setRGB(widthPos,heightPos,rgbColor);
    }

    public int getRedPixelColor(int heightPos,int widthPos){
        return new Color(getRGBPixelColor(heightPos,widthPos), true).getRed();
    }

    public void setRedPixelColor(int heightPos,int widthPos,int redColor){
        int rgbColor = new Color(redColor, getGreenPixelColor(heightPos,widthPos),
                getBluePixelColor(heightPos,widthPos)).getRGB();
        setRGBPixelColor(heightPos,widthPos,rgbColor);
    }

    public int getBluePixelColor(int heightPos,int widthPos){
        return new Color(getRGBPixelColor(heightPos,widthPos), true).getBlue();
    }

    public void setBluePixelColor(int heightPos,int widthPos,int blueColor){
        int rgbColor = new Color(getRedPixelColor(heightPos,widthPos),
                getGreenPixelColor(heightPos,widthPos), blueColor).getRGB();
        img.setRGB(widthPos,heightPos,rgbColor);
    }

    public int getGreenPixelColor(int heightPos,int widthPos){
        return new Color(getRGBPixelColor(heightPos,widthPos), true).getGreen();
    }

    public void setGreenPixelColor(int heightPos,int widthPos,int greenColor){
        int rgbColor = new Color(getRedPixelColor(heightPos,widthPos),
                greenColor, getBluePixelColor(heightPos,widthPos)).getRGB();
        img.setRGB(widthPos,heightPos,rgbColor);
    }

    public String getPixelInStringRGB(int height,int width)
    {
        return "red :"+ getRedPixelColor(height,width)+"  green:"+getGreenPixelColor(height,width)+"  blue:"+getBluePixelColor(height,width);
    }


    public void printImage(){
        for (int y = 0; y < getHeight(); y++){
            for (int x = 0; x < getWidth(); x++){
                System.out.println(getPixelInStringRGB(y,x));
            }
        }
    }
}
