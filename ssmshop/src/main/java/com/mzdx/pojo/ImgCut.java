package com.mzdx.pojo;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
public class ImgCut {
    public static String imgCut(String srcImageFile, int x, int y, int desWidth,
                                int desHeight) throws IOException {
        Image img;
        ImageFilter cropFilter;
        // 读取源图像
        BufferedImage bi = ImageIO
                .read(new File(srcImageFile + "_src.jpg"));
        int srcWidth = bi.getWidth();  // 源图宽度
        int srcHeight = bi.getHeight();   // 源图高度
        //若原图大小大于切片大小，则进行切割
        if (srcWidth >= desWidth && srcHeight >= desHeight) {
            Image image = bi.getScaledInstance(srcWidth, srcHeight,
                    Image.SCALE_DEFAULT);
//          cropFilter = new CropImageFilter(x, y, desWidth, desHeight);

            //将图片按照一定比例显示出来，但是传递到后台的时候需要经历一个比例换算   此处的400和270与前台设定的一致
            int x1 = x*srcWidth/400;
            int y1 = y*srcHeight/270;
            int w1 = desWidth*srcWidth/400;
            int h1 = desHeight*srcHeight/270;

            cropFilter = new CropImageFilter(x1, y1, w1, h1);
            img = Toolkit.getDefaultToolkit().createImage(
                    new FilteredImageSource(image.getSource(), cropFilter));
//          BufferedImage tag = new BufferedImage(desWidth, desHeight,
//                  BufferedImage.TYPE_INT_RGB);

            BufferedImage tag = new BufferedImage(w1, h1,BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(img, 0, 0, null);
            g.dispose();
            // 输出文件
            ImageIO.write(tag, "JPEG", new File(srcImageFile + "_photo.jpg"));

        }
        return srcImageFile.substring(srcImageFile.lastIndexOf("/")+1) + "_photo.jpg";
    }

}
