//this code came from : https://stackoverflow.com/questions/5827809/canny-edge-detection-using-processing

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class CannyEdgeDetector {

    // statics

    private final static float GAUSSIAN_CUT_OFF = 0.005f;
    private final static float MAGNITUDE_SCALE = 100F;
    private final static float MAGNITUDE_LIMIT = 1000F;
    private final static int MAGNITUDE_MAX = (int) (MAGNITUDE_SCALE * MAGNITUDE_LIMIT);

    // fields

    private int height;
    private int width;
    private int picsize;
    private int[] data;
    private int[] magnitude;
    private BufferedImage sourceImage;
    private BufferedImage edgesImage;

    private float gaussianKernelRadius;
    private float lowThreshold;
    private float highThreshold;
    private int gaussianKernelWidth;
    private boolean contrastNormalized;

    private float[] xConv;
    private float[] yConv;
    private float[] xGradient;
    private float[] yGradient;

    // constructors

    /**
     * Constructs a new detector with default parameters.
     */

    public CannyEdgeDetector() {
        lowThreshold = 2.5f;
        highThreshold = 7.5f;
        gaussianKernelRadius = 2f;
        gaussianKernelWidth = 16;
        contrastNormalized = false;
    }

    public CannyEdgeDetector(BufferedImage sourceImage, float lowThreshold, float highThreshold,int gaussianKernelWidth) {
        this();
        this.sourceImage = sourceImage;
        this.lowThreshold = lowThreshold;
        this.highThreshold = highThreshold;
        this.gaussianKernelWidth = gaussianKernelWidth;

    }

    public int getNumberOfEdges()
    {
        int result = 0;
        for (int i : data)
            if (i == -1){

                result++;
            }
        return result;
    }

    public List<Integer[]> getEdges()
    {
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();
        for (int i =0;i < data.length;i++)
        {
            if (data[i] == -1)
                result.add(new Integer[]{i/width,i%width});

        }
        return result;
    }
    public BufferedImage getSourceImage() {
        return sourceImage;
    }


    public void setSourceImage(BufferedImage image) {
        sourceImage = image;
    }


    public BufferedImage getEdgesImage() {
        return edgesImage;
    }


    public void setEdgesImage(BufferedImage edgesImage) {
        this.edgesImage = edgesImage;
    }


    public float getLowThreshold() {
        return lowThreshold;
    }


    public void setLowThreshold(float threshold) {
        if (threshold < 0) throw new IllegalArgumentException();
        lowThreshold = threshold;
    }

    public float getHighThreshold() {
        return highThreshold;
    }


    public void setHighThreshold(float threshold) {
        if (threshold < 0) throw new IllegalArgumentException();
        highThreshold = threshold;
    }

    public int getGaussianKernelWidth() {
        return gaussianKernelWidth;
    }

    public void setGaussianKernelWidth(int gaussianKernelWidth) {
        if (gaussianKernelWidth < 2) throw new IllegalArgumentException();
        this.gaussianKernelWidth = gaussianKernelWidth;
    }

    public float getGaussianKernelRadius() {
        return gaussianKernelRadius;
    }

    public void setGaussianKernelRadius(float gaussianKernelRadius) {
        if (gaussianKernelRadius < 0.1f) throw new IllegalArgumentException();
        this.gaussianKernelRadius = gaussianKernelRadius;
    }

    public boolean isContrastNormalized() {
        return contrastNormalized;
    }

    public void setContrastNormalized(boolean contrastNormalized) {
        this.contrastNormalized = contrastNormalized;
    }

    // methods

    public void process() {
        width = sourceImage.getWidth();
        height = sourceImage.getHeight();
        picsize = width * height;
        initArrays();
        readLuminance();
        if (contrastNormalized) normalizeContrast();
        computeGradients(gaussianKernelRadius, gaussianKernelWidth);
        int low = Math.round(lowThreshold * MAGNITUDE_SCALE);
        int high = Math.round( highThreshold * MAGNITUDE_SCALE);
        performHysteresis(low, high);
        thresholdEdges();
        writeEdges(data);
    }

    // private utility methods

    private void initArrays() {
        if (data == null || picsize != data.length) {
            data = new int[picsize];
            magnitude = new int[picsize];

            xConv = new float[picsize];
            yConv = new float[picsize];
            xGradient = new float[picsize];
            yGradient = new float[picsize];
        }
    }
    private void computeGradients(float kernelRadius, int kernelWidth) {

        //generate the gaussian convolution masks
        float kernel[] = new float[kernelWidth];
        float diffKernel[] = new float[kernelWidth];
        int kwidth;
        for (kwidth = 0; kwidth < kernelWidth; kwidth++) {
            float g1 = gaussian(kwidth, kernelRadius);
            if (g1 <= GAUSSIAN_CUT_OFF && kwidth >= 2) break;
            float g2 = gaussian(kwidth - 0.5f, kernelRadius);
            float g3 = gaussian(kwidth + 0.5f, kernelRadius);
            kernel[kwidth] = (g1 + g2 + g3) / 3f / (2f * (float) Math.PI * kernelRadius * kernelRadius);
            diffKernel[kwidth] = g3 - g2;
        }

        int initX = kwidth - 1;
        int maxX = width - (kwidth - 1);
        int initY = width * (kwidth - 1);
        int maxY = width * (height - (kwidth - 1));

        //perform convolution in x and y directions
        for (int x = initX; x < maxX; x++) {
            for (int y = initY; y < maxY; y += width) {
                int index = x + y;
                float sumX = data[index] * kernel[0];
                float sumY = sumX;
                int xOffset = 1;
                int yOffset = width;
                for(; xOffset < kwidth ;) {
                    sumY += kernel[xOffset] * (data[index - yOffset] + data[index + yOffset]);
                    sumX += kernel[xOffset] * (data[index - xOffset] + data[index + xOffset]);
                    yOffset += width;
                    xOffset++;
                }

                yConv[index] = sumY;
                xConv[index] = sumX;
            }

        }

        for (int x = initX; x < maxX; x++) {
            for (int y = initY; y < maxY; y += width) {
                float sum = 0f;
                int index = x + y;
                for (int i = 1; i < kwidth; i++)
                    sum += diffKernel[i] * (yConv[index - i] - yConv[index + i]);

                xGradient[index] = sum;
            }

        }

        for (int x = kwidth; x < width - kwidth; x++) {
            for (int y = initY; y < maxY; y += width) {
                float sum = 0.0f;
                int index = x + y;
                int yOffset = width;
                for (int i = 1; i < kwidth; i++) {
                    sum += diffKernel[i] * (xConv[index - yOffset] - xConv[index + yOffset]);
                    yOffset += width;
                }

                yGradient[index] = sum;
            }

        }

        initX = kwidth;
        maxX = width - kwidth;
        initY = width * kwidth;
        maxY = width * (height - kwidth);
        for (int x = initX; x < maxX; x++) {
            for (int y = initY; y < maxY; y += width) {
                int index = x + y;
                int indexN = index - width;
                int indexS = index + width;
                int indexW = index - 1;
                int indexE = index + 1;
                int indexNW = indexN - 1;
                int indexNE = indexN + 1;
                int indexSW = indexS - 1;
                int indexSE = indexS + 1;

                float xGrad = xGradient[index];
                float yGrad = yGradient[index];
                float gradMag = hypot(xGrad, yGrad);

                //perform non-maximal supression
                float nMag = hypot(xGradient[indexN], yGradient[indexN]);
                float sMag = hypot(xGradient[indexS], yGradient[indexS]);
                float wMag = hypot(xGradient[indexW], yGradient[indexW]);
                float eMag = hypot(xGradient[indexE], yGradient[indexE]);
                float neMag = hypot(xGradient[indexNE], yGradient[indexNE]);
                float seMag = hypot(xGradient[indexSE], yGradient[indexSE]);
                float swMag = hypot(xGradient[indexSW], yGradient[indexSW]);
                float nwMag = hypot(xGradient[indexNW], yGradient[indexNW]);
                float tmp;

                if (xGrad * yGrad <= (float) 0 /*(1)*/
                        ? Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
                        ? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * neMag - (xGrad + yGrad) * eMag) /*(3)*/
                        && tmp > Math.abs(yGrad * swMag - (xGrad + yGrad) * wMag) /*(4)*/
                        : (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * neMag - (yGrad + xGrad) * nMag) /*(3)*/
                        && tmp > Math.abs(xGrad * swMag - (yGrad + xGrad) * sMag) /*(4)*/
                        : Math.abs(xGrad) >= Math.abs(yGrad) /*(2)*/
                        ? (tmp = Math.abs(xGrad * gradMag)) >= Math.abs(yGrad * seMag + (xGrad - yGrad) * eMag) /*(3)*/
                        && tmp > Math.abs(yGrad * nwMag + (xGrad - yGrad) * wMag) /*(4)*/
                        : (tmp = Math.abs(yGrad * gradMag)) >= Math.abs(xGrad * seMag + (yGrad - xGrad) * sMag) /*(3)*/
                        && tmp > Math.abs(xGrad * nwMag + (yGrad - xGrad) * nMag) /*(4)*/
                ) {
                    magnitude[index] = gradMag >= MAGNITUDE_LIMIT ? MAGNITUDE_MAX : (int) (MAGNITUDE_SCALE * gradMag);
                    //NOTE: The orientation of the edge is not employed by this
                    //implementation. It is a simple matter to compute it at
                    //this point as: Math.atan2(yGrad, xGrad);
                } else {
                    magnitude[index] = 0;
                }
            }
        }
    }

    private float hypot(float x, float y) {
        return (float) Math.hypot(x, y);
    }

    private float gaussian(float x, float sigma) {
        return (float) Math.exp(-(x * x) / (2f * sigma * sigma));
    }

    private void performHysteresis(int low, int high) {

        Arrays.fill(data, 0);

        int offset = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (data[offset] == 0 && magnitude[offset] >= high) {
                    follow(x, y, offset, low);
                }
                offset++;
            }
        }
    }

    private void follow(int x1, int y1, int i1, int threshold) {
        int x0 = x1 == 0 ? x1 : x1 - 1;
        int x2 = x1 == width - 1 ? x1 : x1 + 1;
        int y0 = y1 == 0 ? y1 : y1 - 1;
        int y2 = y1 == height -1 ? y1 : y1 + 1;

        data[i1] = magnitude[i1];
        for (int x = x0; x <= x2; x++) {
            for (int y = y0; y <= y2; y++) {
                int i2 = x + y * width;
                if ((y != y1 || x != x1)
                        && data[i2] == 0
                        && magnitude[i2] >= threshold) {
                    follow(x, y, i2, threshold);
                    return;
                }
            }
        }
    }

    private void thresholdEdges() {
        for (int i = 0; i < picsize; i++) {
            data[i] = data[i] > 0 ? -1 : 0xff000000;
        }
    }

    private int luminance(float r, float g, float b) {
        return Math.round(0.299f * r + 0.587f * g + 0.114f * b);
    }

    private void readLuminance() {
        int type = sourceImage.getType();
        if (type == BufferedImage.TYPE_INT_RGB || type == BufferedImage.TYPE_INT_ARGB) {
            int[] pixels = (int[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
            for (int i = 0; i < picsize; i++) {
                int p = pixels[i];
                int r = (p & 0xff0000) >> 16;
                int g = (p & 0xff00) >> 8;
                int b = p & 0xff;
                data[i] = luminance(r, g, b);
            }
        } else if (type == BufferedImage.TYPE_BYTE_GRAY) {
            byte[] pixels = (byte[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
            for (int i = 0; i < picsize; i++) {
                data[i] = (pixels[i] & 0xff);
            }
        } else if (type == BufferedImage.TYPE_USHORT_GRAY) {
            short[] pixels = (short[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
            for (int i = 0; i < picsize; i++) {
                data[i] = (pixels[i] & 0xffff) / 256;
            }
        } else if (type == BufferedImage.TYPE_3BYTE_BGR) {
            byte[] pixels = (byte[]) sourceImage.getData().getDataElements(0, 0, width, height, null);
            int offset = 0;
            for (int i = 0; i < picsize; i++) {
                int b = pixels[offset++] & 0xff;
                int g = pixels[offset++] & 0xff;
                int r = pixels[offset++] & 0xff;
                data[i] = luminance(r, g, b);
            }
        } else {
            throw new IllegalArgumentException("Unsupported image type: " + type);
        }
    }

    private void normalizeContrast() {
        int[] histogram = new int[256];
        for (int i = 0; i < data.length; i++) {
            histogram[data[i]]++;
        }
        int[] remap = new int[256];
        int sum = 0;
        int j = 0;
        for (int i = 0; i < histogram.length; i++) {
            sum += histogram[i];
            int target = sum*255/picsize;
            for (int k = j+1; k <=target; k++) {
                remap[k] = i;
            }
            j = target;
        }

        for (int i = 0; i < data.length; i++) {
            data[i] = remap[data[i]];
        }
    }

    private void writeEdges(int pixels[]) {
        if (edgesImage == null) {
            edgesImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        }
        edgesImage.getWritableTile(0, 0).setDataElements(0, 0, width, height, pixels);
    }

    public static void main(String[] args) throws Exception {
        String location = "C:\\Users\\ramin\\Desktop\\IMAGE STEGANOGRAPHY\\dog.jpg";
        String saveLocation =  "C:\\Users\\ramin\\Desktop\\testTest";
        BufferedImage image = ImageIO.read( new File(location));
        CannyEdgeDetector canny = new CannyEdgeDetector(image,1f,1f,2);

        canny.process();

        String format = "png";
        File outputFile = new File(saveLocation+"."+format);


        ImageIO.write(canny.edgesImage, format, outputFile);
        System.out.println(canny.getNumberOfEdges());
        System.out.println(canny.getEdges().size());
        System.out.println(canny.picsize);

        System.out.println(toBinary(Float.floatToIntBits(0.33f),32));
    }

    public static String toBinary(int n, int len)
    {
        String binary = "";
        for (long i = (1L << len - 1); i > 0; i = i / 2) {
            binary += (n & i) != 0 ? "1" : "0";
        }
        return binary;
    }


}