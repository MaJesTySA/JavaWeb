package code;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public class CaptchaCode {
    public static String drawImage(HttpServletResponse response){
        //1.拼接字符串的类
        StringBuilder builder=new StringBuilder();
        //产生4位验证码
        for (int i = 0; i <4 ; i++) {
            builder.append(randomChar());
        }
        String code=builder.toString();
        System.out.println(code);
        //2.定义图片的宽度和高度
        int width=120;
        int height=25;
        //建立bufferedImage对象，指定图像的长宽度和色彩
        BufferedImage bi=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        //3.获取graphics2d绘制对象，开始绘制验证码。图片的绘制，最终会注入到缓冲流中
        Graphics2D graph=bi.createGraphics();
        //4.设置字体和大小
        Font font=new Font("宋体",Font.PLAIN,20);
        Color color=new Color(0,255,255);
        graph.setFont(font);
        graph.setColor(color);
        graph.setBackground(new Color(0,0,0));
        //绘制形状，一般是矩形
        graph.clearRect(0,0,width,height);
        //创建字体对象
        FontRenderContext context=graph.getFontRenderContext();
        //计算文字位置
        Rectangle2D bounds=font.getStringBounds(code,context);
        //计算坐标和间距，将字体放在矩形的正中央
        double x=(width-bounds.getWidth())/2;
        double y=(height-bounds.getHeight())/2;
        double acsent=bounds.getY();
        double baseY=y-acsent;
        //在矩形中绘制字体
        graph.drawString(code,(int)x,(int)baseY);
        graph.dispose();
        //保存到输出流
        try {
            ImageIO.write(bi,"jpg",response.getOutputStream());
            //刷新响应流
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //用于验证码的对比和存储
        return code;
    }

    //算术表达式
    public static String drawImageVerificate(HttpServletResponse response){
        int width=100,height=30;
        BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_BGR);
        Graphics2D graph=image.createGraphics();
        Random random=new Random();
        //设置背景
        graph.setColor(getRandomColor(240,250));
        graph.setFont(new Font("微软雅黑",Font.PLAIN,22));
        graph.fillRect(0,0,width,height);
        //设置干扰线。绘制线条在图片中
        graph.setColor(getRandomColor(180,230));
        //100条
        for (int i = 0; i <100 ; i++) {
            int x=random.nextInt(width);
            int y=random.nextInt(height);
            int x1=random.nextInt(60);
            int y1=random.nextInt(60);
            graph.drawLine(x,y,x1,y1);
        }
        //算术表达式拼接
        int num1=(int)(Math.random()*10+1);
        int num2=(int)(Math.random()*10+1);
        int optor=random.nextInt(3);
        String optorstr="";
        int result=0;
        switch (optor){
            case 0 :optorstr="+";result=num1+num2;break;
            case 1 :optorstr="-";result=num1-num2;break;
            case 2 :optorstr="*";result=num1*num2;break;
        }
        //拼接算术表达式
        String calc=num1+" "+optorstr+" "+num2+" = ? ";
        graph.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
        //绘制表达式
        graph.drawString(calc,5,25);
        graph.dispose();
        try {
            ImageIO.write(image,"JPEG",response.getOutputStream());
            return String.valueOf(result);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Color getRandomColor(int fc,int bc){
        Random random=new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }

    private static char randomChar(){
        String string="QWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random random=new Random();
        return string.charAt(random.nextInt(string.length()));
    }

    public static void main(String[] args) {
        CaptchaCode.drawImage(null);
    }
}
